package com.imagedb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.imagedb.struct.ImageInfo;


/** 普通图像管理类
 * @author		杨冲
 * @version		1.00 2 July 2014 */
public class NormImgManage extends ImageManage {

	public NormImgManage(Connection ConnTemp) {
		super(ConnTemp);
	}
	
	/** 获取图像数据
	 * @param nImageID 要获取数据的图像在数据库中的ID
	 * @return 成功：返回存放图像数据的 byte 型数组<br>失败：返回 null 值 */
	public byte[] getImageData(long nImageID) {
		try {
			String strTableName = Integer.toString(getTableID(nImageID));		// 图像所在表格的名称
			StringBuffer strQuery = new StringBuffer("select \"ImageData\" from \"");
			strQuery.append(strTableName);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			byte[] dataBuffer = null;

			if (!drResult.next()) {
				strMessage = new StringBuffer("The image is not exist in the database.");
			} else {
				dataBuffer = drResult.getBytes("ImageData");
				strMessage = new StringBuffer("Succeed to get the data of the normal image.");
			}

			drResult.close();
			command.close();
			return dataBuffer;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean createImageTable(int nTableID) {
		try {
			StringBuffer strQuery = new StringBuffer("create table \"");
			strQuery.append(nTableID);
			strQuery.append("\"(\"ID\" bigint PRIMARY KEY, \"ImageName\" varchar(256),");
			strQuery.append("\"AcquireTime\" date, \"InputTime\" date, \"ImageFormat\" ");
			strQuery.append("varchar(8), \"ImageSize\" integer, \"ImageData\" bytea, ");
			strQuery.append("\"Thumbnail\" bytea, \"FlagInfo\" boolean, \"KeyDescribe\" text)");
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to create the table of normal image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	@Override
	public long addImage(ImageInfo imageInfo) {
		boolean isAutoCommit = true;
		try {
			File file = new File(imageInfo.strFilePath);
			String strFileName = file.getName();		// 获取图像名称
			String strExte = strFileName.substring(strFileName.lastIndexOf(".") + 1);	// 获取图像格式
			int nImageType = getImageType(strExte.toLowerCase());

			if (1 != nImageType) {
				strMessage = new StringBuffer("The format of the input image is not supported.");
				return -1;
			}
		
			// 获取图像子表的序列号
			int nSerialNum = getSerialNum(imageInfo.nTableID);		
			if (-1 == nSerialNum) {
				return -1;
			} else if (nSerialNum > Math.pow(2, 31) - 10) {
				strMessage = new StringBuffer("The serial number of this table exceeds ");
				strMessage.append("the upper limit, please add the image to another table.");
				return -1;
			}

			long nImageID = ((long) imageInfo.nTableID << 32) + nSerialNum;
			long nImageSize = file.length() / 1024;
			
			// 判断图像大小是否超过上限（1GB）
			if (nImageSize / 1024 > 1000) {
				strMessage = new StringBuffer("The size of the image exceeds the upper limit(1Gb).");
				return -1;
			}

			// 添加图像
			String strAcquireTime = String.format("%tF",imageInfo.dtAcquireTime);
			String strInputTime = String.format("%tF", imageInfo.dtInputTime);
			String strImageName = imageInfo.strImageName;
			String strDescInfo = imageInfo.strDescInfo;	

			StringBuffer strQuery = new StringBuffer("insert into \"");
			strQuery.append(imageInfo.nTableID);
			strQuery.append("\"(\"ID\", \"ImageName\", \"AcquireTime\", ");
			strQuery.append("\"InputTime\", \"ImageFormat\", \"ImageSize\", ");
			strQuery.append("\"ImageData\", \"FlagInfo\", ");
			strQuery.append("\"KeyDescribe\") values(");
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
			strQuery.append(",?,'false','");
			strQuery.append(strDescInfo);
			strQuery.append("')");

			isAutoCommit = hConnection.getAutoCommit();			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			PreparedStatement command = hConnection.prepareStatement(strQuery.toString());
			FileInputStream imageFile = new FileInputStream(file);			
			command.setBinaryStream(1, imageFile, file.length());
			command.executeUpdate();
			imageFile.close();
						
			ThumbManage tumbManage = new ThumbManage(hConnection);
			imageFile = new FileInputStream(file);
			byte[] buffer = tumbManage.createNormImgThumb(imageFile);
			boolean bSetThumb = tumbManage.setThumbData(nImageID, buffer);				
			
			if (!bSetThumb) {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				
				command.close();
				return -1;
			}
			
			// 修改图像子表的序列号
			if (!setSerialNum(imageInfo.nTableID)) {
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
			strMessage = new StringBuffer("Succeed to add the normal image");
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

	@Override
	public boolean replaceImage(long nImageID, String strImagePath) {
		try {	
			File file = new File(strImagePath);
			String strFileName = file.getName();
			String strExte = strFileName.substring(strFileName.lastIndexOf(".") + 1);
			int nImageType = getImageType(strExte.toLowerCase());

			// 判断新图像是否为普通图像
			if (1 != nImageType) {
				strMessage = new StringBuffer("The format of the input image is not supported.");
				return false;
			}

			String strTableName = Integer.toString(getTableID(nImageID));
			long nImageSize = file.length() / 1024;

			// 判断新图像大小是否超过上限（1GB）
			if (nImageSize / 1024 > 1000) {
				strMessage = new StringBuffer("The size of the image exceeds the upper limit(1Gb).");
				return false;
			}

			// 获取新图像数据
			byte[] dataBuffer = new byte[(int) file.length()];
			FileInputStream imageFile = new FileInputStream(strImagePath);
			imageFile.read(dataBuffer);
			imageFile.close();

			// 添加新图像
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(strTableName);
			strQuery.append("\" set \"ImageFormat\" = '");
			strQuery.append(strExte);
			strQuery.append("', \"ImageSize\" = ");
			strQuery.append(nImageSize);
			strQuery.append(", \"ImageData\" = ? where \"ID\" = ");
			strQuery.append(nImageID);

			PreparedStatement command = hConnection.prepareStatement(strQuery.toString());
			command.setBytes(1, dataBuffer);
			command.executeUpdate();
			command.close();

			strMessage = new StringBuffer("Succeed to replace the image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean exportImage(long nImageID, String strImagePath) {
		try {
			byte[] dataBuffer = getImageData(nImageID);
			
			if (null == dataBuffer) {
				return false;
			}
			
			FileOutputStream imageFile = new FileOutputStream(strImagePath);
			imageFile.write(dataBuffer);
			imageFile.close();

			strMessage = new StringBuffer("Succeed to export the image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
}
