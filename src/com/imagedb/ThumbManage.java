package com.imagedb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;

import com.imagedb.struct.BmpHead;
import com.imagedb.struct.TnkTbmFileHead;


/** 缩略图管理类
 * @author		杨冲
 * @version		1.00 1 July 2014 */
public class ThumbManage extends ImageDatabase {
	private int nThumbHeight;		// 产生的缩略图的高度（以像素为单位）

	public ThumbManage(Connection ConnTemp) {
		hConnection = ConnTemp;
		nThumbHeight = 512;
	}
	
	public void setThumbHeight(int nHeight) {
		nThumbHeight = nHeight;
	}
	
	public int getThumbHeight() {
		return nThumbHeight;
	}
	
	/** 创建一张普通图像的缩略图
	 * @param inputStream 源图像数据流
	 * @return 成功：返回存放缩略图的 byte 型数组<br>失败：返回 null 值 */
	public byte[] createNormImgThumb(InputStream inputStream) {
		try {			
			
			// 读入源图像
			BufferedImage inputImage = ImageIO.read(inputStream);
			int nSrcHeight = inputImage.getHeight();
			int nSrcWidth = inputImage.getWidth();
			
			// 计算缩略图的大小
			int nDstHeight = nThumbHeight;
			int nDstWidth = nDstHeight * nSrcWidth / nSrcHeight;
			
			// 对源图像进行重采样
			BufferedImage dstBufferedImage = new BufferedImage(nDstWidth, nDstHeight, BufferedImage.TYPE_3BYTE_BGR);
			dstBufferedImage.getGraphics().drawImage(inputImage, 0, 0, nDstWidth, nDstHeight, null);
			
			// 输出缩略图
			ByteArrayOutputStream outputImage = new ByteArrayOutputStream();
			ImageIO.write(dstBufferedImage, "jpg", outputImage);
			
			strMessage = new StringBuffer("Succeed to create the thumbnail.");
			return outputImage.toByteArray();			
		} catch (IOException e) {
			e.printStackTrace();
			strMessage = new StringBuffer(e.getMessage());
			return null;
		}
	}
	
	/** 创建一张 tbm 图像的缩略图
	 * @param inputStream 源图像在数据库中的ID
	 * @return 成功：返回存放缩略图的 byte 型数组<br>失败：返回 null 值 */
	public byte[] createTbmThumb(long nImageID) {
		try {			
			BigImgManage bigImgManage = new BigImgManage(hConnection);
			
			// 获取tbm文件头信息
			byte[] buffer = bigImgManage.getBlockData(nImageID, BigImgManage.TBM_HEAD_ID, 0);
			TnkTbmFileHead tbmFileHead = BigImgManage.parseTbmFileHead(buffer, true);
			
			// 计算从金字塔的 nNumLayer 层产生缩略图
			int nPixelSize = tbmFileHead.nNumColors / 8;		
			int nBlockHeight = (int) (nThumbHeight * tbmFileHead.nTileSize * 
								 	Math.pow(2, tbmFileHead.nLayerCount - 1) / tbmFileHead.nHeight);
			int nNumLayer =	(int) (Math.log(nBlockHeight * 1.0 / tbmFileHead.nTileSize) / Math.log(2.0) + 1);
			
			// 该层有效数据块的行数和列数
			int nRowSize	= BigImgManage.getRowCount(nNumLayer, tbmFileHead);
			int nColSize	= BigImgManage.getColCount(nNumLayer, tbmFileHead);
			
			// 计算源图像的高度和宽度
			int nCode = ImageManage.getQueryCode(nNumLayer, nRowSize - 1, nColSize - 1);
			buffer = bigImgManage.getBlockData(nImageID, nCode, 1);
			
			byte[] bmpHeadBuffer = new byte[40];
			System.arraycopy(buffer, 14, bmpHeadBuffer, 0, 40);
 			BmpHead bmpHead = BigImgManage.parseBmpHead(bmpHeadBuffer, true);
			
			int nSrcHeight = (nRowSize - 1) * tbmFileHead.nTileSize + bmpHead.nHeight;
			int nSrcWidth = (nColSize - 1) * tbmFileHead.nTileSize + bmpHead.nWidth ;
			
			// 定义存放源图像的 byte 型数组并初始化
			int nWidthByteSize = (tbmFileHead.nNumColors / 8 * nSrcWidth + 3) / 4 * 4;
			byte[] srcImage = new byte[nWidthByteSize * nSrcHeight];
			
			for (int i = 0; i < srcImage.length; i++) {
				srcImage[i] = (byte) 255;
			}
			
			int nStartPos = 0;			
			int nBlockCol = 0;
			int nBlockRow = 0;
			
			// 将该层金字塔中有效的数据块复制到源图像数组中
			for (int nRow = 0; nRow < nRowSize; nRow ++) {
				for (int nCol = 0; nCol < nColSize; nCol ++) {				
					nCode = ImageManage.getQueryCode(nNumLayer, nRow, nCol);
					buffer = bigImgManage.getBlockData(nImageID, nCode, 2);				
					nStartPos = (nCol * nPixelSize + nRow * nWidthByteSize) * tbmFileHead.nTileSize;			
					
					if (nColSize - 1 == nCol) {
						nBlockCol = nSrcWidth - nCol * tbmFileHead.nTileSize;
						if (nRowSize - 1 != nRow) {
							nBlockRow = tbmFileHead.nTileSize;
						} else {
							nBlockRow = nSrcHeight - nRow * tbmFileHead.nTileSize;
						}
					} else {
						nBlockCol = tbmFileHead.nTileSize;
						if (nRowSize - 1 != nRow) {
							nBlockRow = tbmFileHead.nTileSize;
						} else {
							nBlockRow = nSrcHeight - nRow * tbmFileHead.nTileSize;
						}
					}
					
					mergeImage(srcImage, nStartPos, nSrcWidth, buffer, 0, nBlockCol,
							   nBlockCol, nBlockRow, nPixelSize);	
				}
			}
			
			// 添加bmp文件头信息产生bmp图像文件并生成对应的缩略图
			byte[] bmpBuffer = new byte[54 + srcImage.length];
			System.arraycopy(BigImgManage.createBmpHead(nSrcWidth, nSrcHeight,
							 tbmFileHead.nNumColors), 0, bmpBuffer, 0, 54);
			System.arraycopy(srcImage, 0, bmpBuffer, 54, srcImage.length);

    		return createNormImgThumb(new ByteArrayInputStream(bmpBuffer));				
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = new StringBuffer(e.getMessage());
			return null;
		}
	}
	
	/** 获取一个图像的缩略图数据 
	 * @param nImageID 图像在数据库中的ID
	 * @return 成功：返回存放缩略图的 byte 数组<br>失败：返回 null 值 */
	public byte[] getThumbData(long nImageID) {
		try {
			String strTableName = Integer.toString(ImageManage.getTableID(nImageID));
			StringBuffer strQuery = new StringBuffer("select \"Thumbnail\" from \"");
			strQuery.append(strTableName);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			byte[] dataBuffer = null;

			if (!drResult.next()) {
				strMessage = new StringBuffer("The image is not exist in the database.");
			} else {
				strMessage = new StringBuffer("Succeed to get the thumbnail of the image.");
				dataBuffer = drResult.getBytes("Thumbnail");
			}
	
			drResult.close();
			command.close();	
			return dataBuffer;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	
	/** 修改一个图像的缩略图数据 
	 * @param nImageID 图像在数据库中的ID
	 * @param cThumbData 修改后的缩略图数据
	 * @return true：缩略图数据修改成功<br>false：缩略图数据修改失败 */
	public boolean setThumbData(long nImageID, byte[] cThumbData) {
		try {
			String strTableName = Integer.toString(ImageManage.getTableID(nImageID));
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(strTableName);
			strQuery.append("\" set \"Thumbnail\" = ? where \"ID\" = ");
			strQuery.append(nImageID);

			PreparedStatement command = hConnection.prepareStatement(strQuery.toString());
			command.setBytes(1, cThumbData);
			command.executeUpdate();
			command.close();

			strMessage = new StringBuffer("Succeed to set the thumbnail of the image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}	
	
	/** 合并两张图像
	 * @param dstImage 存放目标图像的数组
	 * @param nDstStart 目标图像粘贴位置起点
	 * @param nDstWidth 目标图像的宽度
	 * @param srcImage 存放源图像的数组
	 * @param nSrcStart 源图像复制位置起点
	 * @param nSrcWidth 源图像的宽度
	 * @param nSrcCol 源图像要复制的列数
	 * @param nSrcRow 源图像要复制的行数
	 * @param nPixelSize 每个像素所占字节数
	 * @return 存放目标图像的 byte 型数组 */
	public static byte[] mergeImage(byte[] dstImage, int nDstStart, int nDstWidth,
									byte[] srcImage, int nSrcStart, int nSrcWidth,  
									int nSrcCol, int nSrcRow, int nPixelSize) {
		int nDstPos = 0;
		int nSrcPos = 0;
		
		// 按行复制数组
		for (int i = 0; i < nSrcRow; i++) {
			nSrcPos = nSrcStart + (nPixelSize * nSrcWidth + 3) / 4 * 4 * i;
			nDstPos = nDstStart + (nDstWidth * nPixelSize + 3) / 4 * 4 * i;
			System.arraycopy(srcImage, nSrcPos, dstImage, nDstPos, nSrcCol * nPixelSize);
		}
		
		return dstImage;		
	}
	
	/** 导出数据库中的一张图像的缩略图 
	 * @param nImageID 要导出缩略图的图像ID
	 * @param strImagePath 导出缩略图在本地磁盘的存放路径（完整路径）
	 * @return true：缩略图导出成功<br>false：缩略图导出失败 */
	public boolean exportThumb(long nImageID, String strImagePath) {
		try {
			byte[] dataBuffer = getThumbData(nImageID);			
			if (null == dataBuffer) {
				return false;
			}
			
			FileOutputStream imageFile = new FileOutputStream(strImagePath);
			imageFile.write(dataBuffer);
			imageFile.close();

			strMessage = new StringBuffer("Succeed to export the thumbnail.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
}
