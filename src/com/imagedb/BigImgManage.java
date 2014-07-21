package com.imagedb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import com.imagedb.struct.BmpHead;
import com.imagedb.struct.ImageInfo;
import com.imagedb.struct.TnkImgBlockHead;
import com.imagedb.struct.TnkInfoBlockAssit;
import com.imagedb.struct.TnkTbmFileHead;


/** 超大图像管理类
 * @author		杨冲
 * @version		1.00 3 July 2014 */
public class BigImgManage extends ImageManage {
	
	/** tbm文件信息数据结构 */
	protected static final int TBM_HEAD_SIZE = 528;		// tnkTbmFileHead的大小
	protected static final int BLOCK_ASSIT_SIZE = 88;	// tnkInfoBlockAssit的大小
	protected static final int BLOCK_HEAD_SIZE = 92;	// tnkImgBlockHead的大小
	protected static final int BMP_HEAD_SIZE = 54;		// bmp图像头文件大小
	protected static final int TBM_HEAD_ID = -111;		// tnkTbmFileHead存入数据库后的ID
	protected static final int BLOCK_ASSIT_ID = -222;	// tnkInfoBlockAssit存入数据库后的ID
	protected static final int TBM_INDEX_ID = -333;		// tbm文件中block索引值块存入数据库后的ID
	
	public BigImgManage(Connection ConnTemp) {
		super(ConnTemp);
	}
			
	/** 获取一个超大图像的数据块 
	 * @param nImageID 数据库块所在图像的ID
	 * @param nBlockID 数据块的ID
	 * @param nHasHead 数据块是否包含头文件
	 * @return 成功：返回存放数据的 byte 数组<br>失败：返回 null 值 */
	public byte[] getBlockData(long nImageID, int nBlockID, int nType) {
		try {
			if (nType > 2 || nType < 0) {
				strMessage = new StringBuffer("The type of the block is wrong.");
				return null;
			}
			
			StringBuffer strQuery = new StringBuffer("select \"BlockData\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_Block\" where \"ID\" = ");
			strQuery.append(nBlockID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			byte[] dataBuffer = null;

			if (!drResult.next()) {
				strMessage = new StringBuffer("The block is not exist in the image.");
			} else {
				byte[] bufferTemp = drResult.getBytes("BlockData");				
				
				if (nBlockID >= 0) {
					int nHeadSize = 0;

					if (1 == nType) {
						nHeadSize = BLOCK_HEAD_SIZE;
					} else if (2 == nType) {
						nHeadSize = BLOCK_HEAD_SIZE + BMP_HEAD_SIZE;
					} 
					
					int nCopySize = bufferTemp.length - nHeadSize;				
					dataBuffer = new byte[nCopySize];
					System.arraycopy(bufferTemp, nHeadSize, dataBuffer, 0, nCopySize);
				} else {
					dataBuffer = bufferTemp;
				}
			}

			drResult.close();
			command.close();
			
			strMessage = new StringBuffer("Succeed to get the block data of the big image.");
			return dataBuffer;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	
	/** 获取一个超大图像的第nLayer层图像 
	 * @param nImageID 数据库块所在图像的ID
	 * @param nLayer 金字塔层数
	 * @return 成功：返回图像<br>失败：返回 null 值 
	 * @throws IOException */
	public BufferedImage getImage(long nImageID, int nLayer) throws IOException {
		byte[] tbmHead = getBlockData(nImageID, BigImgManage.TBM_HEAD_ID, 0);
		TnkTbmFileHead tbmFileHead = BigImgManage.parseTbmFileHead(tbmHead, true);
		
		double nRatio = Math.pow(2, tbmFileHead.nLayerCount - nLayer -1);		
		int nLayerHeight  = (int) (tbmFileHead.nHeight / nRatio);
		int nLayerWidth	= (int) (tbmFileHead.nWidth / nRatio);	
		
		int nRowCount = getRowCount(nLayer, tbmFileHead);
		int nColCount = getColCount(nLayer, tbmFileHead);
		
		int nX;
		int nY;
		int nCode;		
		byte[] data;
		BufferedImage inputImage;
		ByteArrayInputStream inputStream;	
		BufferedImage outputImage = new BufferedImage(nLayerWidth, nLayerHeight, BufferedImage.TYPE_3BYTE_BGR);
	
		for (int nRow = 0; nRow < nRowCount; nRow++) {
			for (int nCol = 0; nCol < nColCount; nCol++) {
				nX = nCol * tbmFileHead.nTileSize;
				nY = nLayerHeight - nRow * tbmFileHead.nTileSize;
				
				nCode = ImageManage.getQueryCode(nLayer, nRow, nCol);
				data = getBlockData(nImageID, nCode, 1);
				
				inputStream = new ByteArrayInputStream(data);
				inputImage = ImageIO.read(inputStream);
				
				outputImage.getGraphics().drawImage(inputImage, nX, nY - inputImage.getHeight(), inputImage.getWidth(), 
													inputImage.getHeight(), null);				
			}
		}
		
		return outputImage;
	}

	@Override
	public boolean createImageTable(int nTableID) {
		try {
        	StringBuffer strQuery = new StringBuffer("create table \"");
            strQuery.append(nTableID);
            strQuery.append("\"(\"ID\" bigint PRIMARY KEY, \"ImageName\" varchar(256), ");
            strQuery.append("\"AcquireTime\" date, \"InputTime\" date, \"ImageFormat\" ");
            strQuery.append("varchar(8), \"ImageSize\" integer, \"ImageData\" varchar(256), ");
            strQuery.append("\"Thumbnail\" bytea, \"FlagInfo\" boolean, \"KeyDescribe\" text)");
            
            Statement command = hConnection.createStatement();
            command.executeUpdate(strQuery.toString());
            command.close();

            strMessage = new StringBuffer("Succeed to create the table of big image.");
            return true;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return false;
        }
	}

	@Override
	public boolean replaceImage(long nImageID, String strImagePath) {
		boolean isAutoCommit = true;
		try {
			File file = new File(strImagePath);
			String strFileName = file.getName();
			String strExte = strFileName.substring(strFileName.lastIndexOf(".") + 1);
			int nImageType = getImageType(strExte.toLowerCase());

			// 判断新图像格式是否支持
			if (2 != nImageType) {
				strMessage = new StringBuffer("The format of the input image is not supported.");
				return false;
			}
		
			// 获取旧图像信息
			ImageInfo imageInfo = getImageInfo(nImageID);
			if (null == imageInfo) {
				return false;
			}
			
			imageInfo.nImageSize = (int) (file.length() / 1024);
			isAutoCommit = hConnection.getAutoCommit();	
			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
					
			// 删除旧图像的Block表，创建新图像的Block表，修改新图像的信息
			if (!deleteBlockTab(nImageID) || !createBlockTab(nImageID, strImagePath) 
				|| !setImageInfo(nImageID, imageInfo)) {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return false;
			}
		
			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}

			strMessage = new StringBuffer("Succeed to replace the image.");
			return true;
		} catch (Exception ex) {
			try {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteImage(long nImageID) {
		boolean isAutoCommit = true;
		try {
			isAutoCommit = hConnection.getAutoCommit();		
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			// 删除图像子表中的图像信息，并删除图像块表
			if(!super.deleteImage(nImageID) || !deleteBlockTab(nImageID)) {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return false;
			}

			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			strMessage = new StringBuffer("Succeed to delete the big image.");
			return true;
		} catch (Exception ex) {
			try {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean exportImage(long nImageID, String strImagePath) {
		boolean isAutoCommit = true;
		try {
			isAutoCommit = hConnection.getAutoCommit();			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			// 向文件中写入头文件信息TnkTbmFileHead
			StringBuffer strQuery = new StringBuffer("select \"BlockData\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_Block\" where \"ID\" = ");
			strQuery.append(TBM_HEAD_ID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			drResult.next();
			byte[] dataBuffer = drResult.getBytes("BlockData");

			FileOutputStream imageFile = new FileOutputStream(strImagePath);
			imageFile.write(dataBuffer);
			drResult.close();

			// 向文件中写入数据块帮助信息TnkInfoBlockAssit
			strQuery = new StringBuffer("select \"BlockData\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_Block\" where \"ID\" = ");
			strQuery.append(BLOCK_ASSIT_ID);

			drResult = command.executeQuery(strQuery.toString());
			drResult.next();

			dataBuffer = drResult.getBytes("BlockData");
			imageFile.write(dataBuffer);
			drResult.close();

			// 向文件中写入数据块索引信息
			strQuery = new StringBuffer("select \"BlockData\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_Block\" where \"ID\" = ");
			strQuery.append(TBM_INDEX_ID);

			drResult = command.executeQuery(strQuery.toString());
			drResult.next();

			dataBuffer = drResult.getBytes("BlockData");
			imageFile.write(dataBuffer);
			drResult.close();

			// 按顺序向文件中写入数据块
			strQuery = new StringBuffer("select \"BlockData\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_Block\" where \"ID\" >= 0");

			command.setFetchSize(1);
			drResult = command.executeQuery(strQuery.toString());

			while (drResult.next()) {
				dataBuffer = drResult.getBytes("BlockData");
				imageFile.write(dataBuffer);
			}

			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			imageFile.close();
			drResult.close();
			
			command.setFetchSize(0);
			command.close();
			
			strMessage = new StringBuffer("Succeed to export the big image.");
			return true;
		} catch (Exception ex) {
			try {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	@Override
	public long addImage(ImageInfo imageInfoTemp) {
		boolean isAutoCommit = true;
		try {
			File file = new File(imageInfoTemp.strFilePath);
			String strFileName = file.getName();
			String strExte = strFileName.substring(strFileName.lastIndexOf(".") + 1);
			int nImageType = getImageType(strExte.toLowerCase());

			// 判断添加的图像格式是否支持
			if (2 != nImageType) {
				strMessage = new StringBuffer("The format of the input image is not supported.");
				return -1;
			}

			// 获取图像的序列号
			int nSerialNum = getSerialNum(imageInfoTemp.nTableID);
			if (nSerialNum == -1) {
				return -1;
			} else if (nSerialNum > Math.pow(2, 31) - 10) {
				strMessage = new StringBuffer("The serial number of this table exceeds ");
				strMessage.append("the upper limit, please add the image to another table.");
				return -1;
			}

			// 修改图像信息
			long nImageID = ((long) imageInfoTemp.nTableID << 32) + nSerialNum;
			long nImageSize = file.length() / 1024;
			
			String strAcquireTime = String.format("%tF",imageInfoTemp.dtAcquireTime);
			String strInputTime = String.format("%tF",imageInfoTemp.dtInputTime);
			String strImageName = imageInfoTemp.strImageName;
			String strDescInfo = imageInfoTemp.strDescInfo;
			String strBlocksTable = Long.toString(nImageID) + "_Block";

			StringBuffer strQuery = new StringBuffer("insert into \"");
			strQuery.append(imageInfoTemp.nTableID);
			strQuery.append("\"(\"ID\",\"ImageName\",\"AcquireTime\",");
			strQuery.append("\"InputTime\",\"ImageFormat\",\"ImageSize\",");
			strQuery.append("\"ImageData\",\"FlagInfo\",\"KeyDescribe\") values(");
			strQuery.append(nImageID);
			strQuery.append(",'");
			strQuery.append(strImageName);
			strQuery.append("','");
			strQuery.append(strAcquireTime);
			strQuery.append("','");
			strQuery.append(strInputTime);
			strQuery.append("','");
			strQuery.append(strExte);
			strQuery.append("',");
			strQuery.append(nImageSize);
			strQuery.append(",'");
			strQuery.append(strBlocksTable);
			strQuery.append("','false','");
			strQuery.append(strDescInfo);
			strQuery.append("')");

			isAutoCommit = hConnection.getAutoCommit();			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			ThumbManage thumbManage = new ThumbManage(hConnection);

			 
			
			
			// 创建图像数据块表，修改图像所在子表的序列号
			if (!createBlockTab(nImageID, imageInfoTemp.strFilePath) 
				|| !setSerialNum(imageInfoTemp.nTableID) 
				|| !thumbManage.setThumbData(nImageID, thumbManage.createTbmThumb(nImageID))) {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				
				command.close();
				return -1;
			}
			
			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			command.close();
			strMessage = new StringBuffer("Succeed to add the big image.");
			return nImageID;
		} catch (Exception ex) {
			try {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			strMessage = new StringBuffer(ex.getMessage());
			return -1;
		}
	}
	
	/** 创建一个大图像块表 
	 * @param nImageID 图像在数据库中的ID
	 * @param strFilePath 图像在本地磁盘上的路径
	 * @return true：表创建成功<br>false：表创建失败 */
	protected boolean createBlockTab(long nImageID, String strFilePath) {
		boolean isAutoCommit = true;
        try {
        	isAutoCommit = hConnection.getAutoCommit();
        	
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
        	
        	File file = new File(strFilePath);
            FileInputStream imageFileIn = new FileInputStream(file);
            DataInputStream imageDataIn = new DataInputStream(imageFileIn);
            
            // 创建数据库表
            StringBuffer strQuery = new StringBuffer("create table \"");
            strQuery.append(nImageID);
            strQuery.append("_Block");
            strQuery.append("\"(\"ID\" integer PRIMARY KEY,\"BlockData\" bytea)");
            
            PreparedStatement command = hConnection.prepareStatement(strQuery.toString());
            command.executeUpdate();

            // 向表中写入文件头TnkTbmFileHead
            byte[] dataBuffer = new byte[TBM_HEAD_SIZE];
            imageDataIn.readFully(dataBuffer, 0, TBM_HEAD_SIZE);          
            TnkTbmFileHead tbmFileHeader = parseTbmFileHead(dataBuffer, true);           

            strQuery = new StringBuffer("insert into \"");
            strQuery.append(nImageID);
            strQuery.append("_Block\"(\"ID\", \"BlockData\") values(?, ?)");

            command = hConnection.prepareStatement(strQuery.toString());
            command.setInt(1, TBM_HEAD_ID);
            command.setBytes(2, dataBuffer);
            command.executeUpdate();

            // 向表中写入数据块帮助信息TnkInfoBlockAssit
            dataBuffer = new byte[BLOCK_ASSIT_SIZE];
            imageDataIn.readFully(dataBuffer, 0, BLOCK_ASSIT_SIZE);
            
            TnkInfoBlockAssit tnkBlockAssit = parseBlockAssit(dataBuffer, true);           

            command.setInt(1, BLOCK_ASSIT_ID);
            command.setBytes(2, dataBuffer);
            command.executeUpdate();
            
            // 向表中写入数据块索引信息
            int nIndexSize = 4*tnkBlockAssit.nBlockNum*2;
            dataBuffer = new byte[nIndexSize];
            imageDataIn.readFully(dataBuffer, 0, nIndexSize);

            command.setInt(1, TBM_INDEX_ID);
            command.setBytes(2, dataBuffer);
            command.executeUpdate();

            // 按顺序向表中写入数据块
            TnkImgBlockHead tnkBlockHead;
            int nBlockSize = (int) (tbmFileHeader.nNumColors / 8 * Math.pow(tbmFileHeader.nTileSize, 2) 
            				 	    + BLOCK_HEAD_SIZE + BMP_HEAD_SIZE);
            dataBuffer = new byte[nBlockSize];
//            byte[] dataBuffer1 = new byte[nBlockSize - BLOCK_HEAD_SIZE];
            
            for (int i = 0; i < tnkBlockAssit.nVlidBlkNum; i++) {
            	imageDataIn.readFully(dataBuffer, 0, nBlockSize);                
            	tnkBlockHead = parseBlockHead(dataBuffer, true);
            	
//            	System.arraycopy(dataBuffer, 92, dataBuffer1, 0, dataBuffer1.length);
//            	BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(dataBuffer1));
            	
//            	int nWidth = inputImage.getWidth();
 //           	int nHeight = inputImage.getHeight();
            	
            	// 对源图像进行重采样
//    			BufferedImage dstBufferedImage = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_3BYTE_BGR);
 //   			dstBufferedImage.getGraphics().drawImage(inputImage, 0, 0, nWidth, nHeight, null);
    			
    			// 输出缩略图
//    			ByteArrayOutputStream outputImage = new ByteArrayOutputStream();
//    			ImageIO.write(dstBufferedImage, "jpg", outputImage);
            	
                command.setInt(1, tnkBlockHead.nImgBlockID);
                command.setBytes(2, dataBuffer);
                command.executeUpdate();               
            }
            
            imageDataIn.close();
            imageFileIn.close();
            
            if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
            
            command.close();
            strMessage = new StringBuffer("Succeed to create the block table.");
            return true;
		} catch (Exception ex) {
			try {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
    }
	
	/** 删除一个大图像块表 
	 * @param nImageID 图像在数据库中的ID
	 * @return true：表删除成功<br>false：表删除失败 */
	protected boolean deleteBlockTab(long nImageID) {
		try {
			StringBuffer strQuery = new StringBuffer("drop table \"");
			strQuery.append(nImageID);
			strQuery.append("_Block\"");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();
			
			strMessage = new StringBuffer("Succeed to delete the block table.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 把数组解析成TnkTbmFileHead */
	protected static TnkTbmFileHead parseTbmFileHead(byte[] dataBuffer, boolean bLittle) {
    	TnkTbmFileHead tbmFileHeader = new TnkTbmFileHead();
    	
    	byte[] strTemp = new byte[4];
    	System.arraycopy(dataBuffer, 28, strTemp, 0, 4);   	
    	tbmFileHeader.nTileSize = byteArrayToInt(strTemp, bLittle);
    	
    	System.arraycopy(dataBuffer, 32, strTemp, 0, 4);   	
    	tbmFileHeader.nNumColors = byteArrayToInt(strTemp, bLittle);
    	
    	System.arraycopy(dataBuffer, 88, strTemp, 0, 4);   	
    	tbmFileHeader.nWidth = byteArrayToInt(strTemp, bLittle);
    	
    	System.arraycopy(dataBuffer, 92, strTemp, 0, 4);   	
    	tbmFileHeader.nHeight = byteArrayToInt(strTemp, bLittle);
    	
    	int	nLength = Math.max(tbmFileHeader.nWidth, tbmFileHeader.nHeight);
		int	nNumLayer = (int) (Math.log((double)nLength / (double)tbmFileHeader.nTileSize) / Math.log(2.0) + 1);
		double dNumLayer = Math.log((double)nLength / (double)tbmFileHeader.nTileSize) / Math.log(2.0) + 1;

		if (dNumLayer > (double) nNumLayer) {
			nNumLayer += 1;
		}
    	
		tbmFileHeader.nLayerCount = nNumLayer;
    	return tbmFileHeader;
    }
    
	/** 把数组解析成TnkInfoBlockAssit */	 
	protected static TnkInfoBlockAssit parseBlockAssit(byte[] dataBuffer, boolean bLittle) {
    	TnkInfoBlockAssit tnkBlockAssit = new TnkInfoBlockAssit();
    	
    	byte[] strTemp = new byte[4];
    	System.arraycopy(dataBuffer, 0, strTemp, 0, 4);   	
    	tnkBlockAssit.nBlockNum = byteArrayToInt(strTemp, bLittle); 
    	
    	System.arraycopy(dataBuffer, 4, strTemp, 0, 4);   	
    	tnkBlockAssit.nVlidBlkNum = byteArrayToInt(strTemp, bLittle);  
    	
    	System.arraycopy(dataBuffer, 8, strTemp, 0, 4);   	
    	tnkBlockAssit.nInfoBlockSize = byteArrayToInt(strTemp, bLittle);  
    	
    	return tnkBlockAssit;
    }
	
    /** 把数组解析成TnkImgBlockHead */
	protected static TnkImgBlockHead parseBlockHead(byte[] dataBuffer, boolean bLittle) {
    	TnkImgBlockHead tnkBlockHead = new TnkImgBlockHead();
    	
    	byte[] strTemp = new byte[4];
    	System.arraycopy(dataBuffer, 0, strTemp, 0, 4);   	
    	tnkBlockHead.nImgBlockSize = byteArrayToInt(strTemp, bLittle);   
    	
    	System.arraycopy(dataBuffer, 4, strTemp, 0, 4);   	
    	tnkBlockHead.nImgBlockID = byteArrayToInt(strTemp, bLittle);   
    	
    	System.arraycopy(dataBuffer, 8, strTemp, 0, 4);   	
    	tnkBlockHead.nImgLocalID = byteArrayToInt(strTemp, bLittle);   
    	
    	return tnkBlockHead;
    }
	
	/** 把数组解析成Bmp格式的文件头 */
	protected static BmpHead parseBmpHead(byte[] dataBuffer, boolean bLittle) {
		BmpHead bmpHead = new BmpHead();
    	
    	byte[] strTemp = new byte[4];
    	System.arraycopy(dataBuffer, 4, strTemp, 0, 4);   	
    	bmpHead.nWidth = byteArrayToInt(strTemp, bLittle);   
    	
    	System.arraycopy(dataBuffer, 8, strTemp, 0, 4);   	
    	bmpHead.nHeight = byteArrayToInt(strTemp, bLittle);   
    	
    	System.arraycopy(dataBuffer, 14, strTemp, 0, 2);
    	strTemp[2] = 0;
    	strTemp[3] = 0;
    	bmpHead.nColorNum = byteArrayToInt(strTemp, bLittle);   
    	
    	return bmpHead;
    }
	
	/** 创建bmp格式头文件 */
	protected static byte[] createBmpHead(int nWidth, int nHeight, int nColorNum) {
    	byte[] bmpHead = new byte[54];
    	
    	bmpHead[0] = 'B';
    	bmpHead[1] = 'M';
    	
    	int nBmpSize = 54 + (nWidth * nColorNum + 31) / 32 * 4 * nHeight;
    	System.arraycopy(intToByteArray(nBmpSize, true), 0, bmpHead, 2, 4);
    	System.arraycopy(intToByteArray(0, true), 0, bmpHead, 6, 4);
    	System.arraycopy(intToByteArray(54, true), 0, bmpHead, 10, 4);
    	System.arraycopy(intToByteArray(40, true), 0, bmpHead, 14, 4);
    	System.arraycopy(intToByteArray(nWidth, true), 0, bmpHead, 18, 4);
    	System.arraycopy(intToByteArray(nHeight, true), 0, bmpHead, 22, 4);
    	System.arraycopy(intToByteArray(1, true), 0, bmpHead, 26, 2);
    	System.arraycopy(intToByteArray(nColorNum, true), 0, bmpHead, 28, 2);
    	System.arraycopy(intToByteArray(0, true), 0, bmpHead, 30, 4);
    	System.arraycopy(intToByteArray(nBmpSize - 54, true), 0, bmpHead, 34, 4);
    	System.arraycopy(intToByteArray(0, true), 0, bmpHead, 38, 4);
    	System.arraycopy(intToByteArray(0, true), 0, bmpHead, 42, 4);
    	System.arraycopy(intToByteArray(0, true), 0, bmpHead, 46, 4);
    	System.arraycopy(intToByteArray(0, true), 0, bmpHead, 50, 4);
    	
    	return bmpHead;
    }
	
	/** 获取第 nLayerNum 层的每行的图像块个数 */
	protected static int getColCount(int nLayerNum, TnkTbmFileHead tbmFileHead) {
		int	nRatio = (int) Math.pow(2, tbmFileHead.nLayerCount - nLayerNum - 1);	
		int	nWidthLayer	= tbmFileHead.nWidth / nRatio;			
		return (int) Math.ceil(nWidthLayer * 1.0 / tbmFileHead.nTileSize);
    }	
	
	/** 获取第 nLayerNum 层的每列的图像块个数 */
	protected static int getRowCount(int nLayerNum, TnkTbmFileHead tbmFileHead) {
		int	nRatio = (int) Math.pow(2, tbmFileHead.nLayerCount - nLayerNum - 1);	
		int	nHeightLayer = tbmFileHead.nHeight / nRatio;			
		return (int) Math.ceil(nHeightLayer * 1.0 / tbmFileHead.nTileSize);
    }
}
