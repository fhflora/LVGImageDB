package com.imagedb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.imagedb.struct.ImageInfo;


/** 航空图像管理类
 * @author		杨冲
 * @version		1.00 3 July 2014 */
public class AerialImgManage extends BigImgManage {

	public AerialImgManage(Connection ConnTemp) {
		super(ConnTemp);
	}

	@Override
	public ImageInfo getImageInfo(long nImageID) {
		try {
			String strTableName = Integer.toString(getTableID(nImageID)); // 图像所在表的名称
			ImageInfo imageInfo = super.getImageInfo(nImageID);
			
			if (null == imageInfo) {
				return null;
			}
			
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(strTableName);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			drResult.next();

			imageInfo.dResolution = drResult.getDouble("Resolution");
			imageInfo.dLeftBottomX = drResult.getDouble("LeftBottomX");
			imageInfo.dLeftBottomY = drResult.getDouble("LeftBottomY");

			drResult.close();
			command.close();
			return imageInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
    
	@Override
	public boolean setImageInfo(long nImageID, ImageInfo newImageInfo) {
		boolean isAutoCommit = true;		
		try {			
			isAutoCommit = hConnection.getAutoCommit();
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
					
			if (super.setImageInfo(nImageID, newImageInfo)) {
				String strTableName = Integer.toString(getTableID(nImageID));
				
				// 修改图像信息
				StringBuffer strQuery = new StringBuffer("update \"");
				strQuery.append(strTableName);
				strQuery.append("\" set \"Resolution\" = ");
				strQuery.append(newImageInfo.dResolution);
				strQuery.append(", \"LeftBottomX\" = ");
				strQuery.append(newImageInfo.dLeftBottomX);
				strQuery.append(", \"LeftBottomY\" = ");
				strQuery.append(newImageInfo.dLeftBottomY);
				strQuery.append(" where \"ID\" = ");
				strQuery.append(nImageID);

				Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());
				
				if (isAutoCommit) {
					hConnection.commit();
					hConnection.setAutoCommit(isAutoCommit);
				}
				
				command.close();
				return true;
			} else {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return false;
			}	
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
	public boolean createImageTable(int nTableID) {
		try {
        	StringBuffer strQuery = new StringBuffer("create table \"");
            strQuery.append(nTableID);
            strQuery.append("\"(\"ID\" bigint PRIMARY KEY, \"ImageName\" varchar(256), ");
            strQuery.append("\"AcquireTime\" date, \"InputTime\" date, \"ImageFormat\" ");
            strQuery.append("varchar(8), \"ImageSize\" integer, \"ImageData\" varchar(256), ");
            strQuery.append("\"Thumbnail\" bytea, \"FlagInfo\" boolean, \"Resolution\" float, ");
            strQuery.append("\"LeftBottomX\" float, \"LeftBottomY\" float, \"KeyDescribe\" text)");
            
            Statement command = hConnection.createStatement();
            command.executeUpdate(strQuery.toString());
            command.close();

            strMessage = new StringBuffer("Succeed to create the table of aerial image.");
            return true;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return false;
        }
	}
	
	@Override
	public long addImage(ImageInfo imageInfoTemp) {
		boolean isAutoCommit = true;
		try {
			long nImageID = super.addImage(imageInfoTemp);
			isAutoCommit = hConnection.getAutoCommit();
			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
						
			if (-1 != nImageID) {
				String strTableName = Integer.toString(getTableID(nImageID));
				
				// 修改图像信息
				StringBuffer strQuery = new StringBuffer("update \"");
				strQuery.append(strTableName);
				strQuery.append("\" set \"Resolution\" = ");
				strQuery.append(imageInfoTemp.dResolution);
				strQuery.append(", \"LeftBottomX\" = ");
				strQuery.append(imageInfoTemp.dLeftBottomX);
				strQuery.append(", \"LeftBottomY\" = ");
				strQuery.append(imageInfoTemp.dLeftBottomY);
				strQuery.append(" where \"ID\" = ");
				strQuery.append(nImageID);

				Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());
				
				if (isAutoCommit) {
					hConnection.commit();
					hConnection.setAutoCommit(isAutoCommit);
				}
				
				command.close();
				strMessage = new StringBuffer("Succeed to add the aerial image.");
				return nImageID;
			} else {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return -1;
			}
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
}
