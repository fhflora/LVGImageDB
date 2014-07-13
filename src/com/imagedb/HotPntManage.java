package com.imagedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.imagedb.struct.HotPntInfo;
import com.imagedb.struct.ImageInfo;

/** 图像热点管理类
 * @author		杨冲
 * @version		1.00 3 July 2014 */
public class HotPntManage extends ImageDatabase {

	public HotPntManage(Connection ConnTemp) {
		hConnection = ConnTemp;
	}
	
	/** 创建一张图像的热点信息表 
	 * @param nImageID 要创建热点信息表的图像的ID
	 * @return true：热点信息表创建成功<br>false：热点信息表创建失败 */
	public boolean createHotPntTab(long nImageID) {
		try {			
			StringBuffer strQuery = new StringBuffer("create table \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\"(\"ID\" serial PRIMARY KEY, \"FlagType\"");
			strQuery.append(" integer, \"FlagData\" bytea, \"LayerNum\" integer, ");
			strQuery.append("\"FlagName\" varchar(256), \"XPos\" float, \"YPos\"");
			strQuery.append(" float, \"Remark\" text)");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to create the table of flag information.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	/** 在图像上添加一个热点
	 * @param nImageID 热点所在图像的ID
	 * @param hotPntInfo 要添加热点的信息
	 * @param hotPntData 要添加的热点数据
	 * @return true：图像热点添加成功<br>false：图像热点添加失败 */
	public boolean addHotPnt(long nImageID, HotPntInfo hotPntInfo, byte[] hotPntData) {
		boolean isAutoCommit = true;
		try {
			ImageManage imageManage = getImageManage((int) (nImageID >> 56), hConnection);
			ImageInfo imageInfo = imageManage.getImageInfo(nImageID);
			
			if (null == imageInfo) {
				return false;
			}
			
			isAutoCommit = hConnection.getAutoCommit();			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
				
			if (!imageInfo.bFlagInfo) {
				imageInfo.bFlagInfo = true;
				
				// 修改图像信息并创建热点信息表
				if (!imageManage.setImageInfo(nImageID, imageInfo) 
					|| !createHotPntTab(nImageID)) {
					hConnection.rollback();
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					return false;
				}
			}
			
			// 添加热点
			StringBuffer strQuery = new StringBuffer("insert into \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\"(\"FlagType\", \"FlagData\", \"FlagName\", ");
			strQuery.append("\"LayerNum\", \"XPos\", \"YPos\", \"Remark\") values (");
			strQuery.append(hotPntInfo.nType);
			strQuery.append(",?,'");
			strQuery.append(hotPntInfo.strName);
			strQuery.append("',");
			strQuery.append(hotPntInfo.nLayerNum);
			strQuery.append(",");
			strQuery.append(hotPntInfo.dPosX);
			strQuery.append(",");
			strQuery.append(hotPntInfo.dPosY);
			strQuery.append(",'");
			strQuery.append(hotPntInfo.strRemark);
			strQuery.append("')");
			
			PreparedStatement command = hConnection.prepareStatement(strQuery.toString());
			command.setBytes(1, hotPntData);
			command.executeUpdate();
			
			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			command.close();		
			strMessage = new StringBuffer("Succceed to add the hot point.");
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
	
	/** 删除图像上的一个热点
	 * @param nImageID 热点所在图像的ID
	 * @param nHotPntID 要删除热点的ID
	 * @return true：图像热点删除成功<br>false：图像热点删除失败 */
	public boolean deleteHotPnt(long nImageID, int nHotPntID) {
		try {		
			StringBuffer strQuery = new StringBuffer("delete from \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\" where \"ID\" = ");
			strQuery.append(nHotPntID);
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to delete the hot point.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 删除图像上所有的热点
	 * @param nImageID 热点所在图像的ID
	 * @return true：图像热点删除成功<br>false：图像热点删除失败 */
	public boolean deleteHotPnt(long nImageID) {
		try {		
			StringBuffer strQuery = new StringBuffer("truncate \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\"");
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to delete all hot points.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 修改图像上热点的信息
	 * @param nImageID 热点所在图像的ID
	 * @param nHotPntID 要删除的热点ID
	 * @param hotPntInfo 修改后的热点信息
	 * @return true：热点信息修改成功<br>false：热点信息修改失败 */
	public boolean setHotPntInfo(long nImageID, int nHotPntID, HotPntInfo hotPntInfo) {
		try {			
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\" set \"LayerNum\" = ");
			strQuery.append(hotPntInfo.nLayerNum);
			strQuery.append(", \"XPos\" = ");
			strQuery.append(hotPntInfo.dPosX);
			strQuery.append(", \"YPos\" = ");
			strQuery.append(hotPntInfo.dPosY);
			strQuery.append(", \"FlagName\" = '");
			strQuery.append(hotPntInfo.strName);
			strQuery.append("', \"Remark\" = '");
			strQuery.append(hotPntInfo.strRemark);
			strQuery.append("' where \"ID\" = ");
			strQuery.append(nHotPntID);

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to set the information of the hot point.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 获取图像上热点的信息
	 * @param nImageID 热点所在图像的ID
	 * @param nHotPntID 热点的ID
	 * @return 成功：返回热点信息<br>失败：返回 null 值 */
	public HotPntInfo getHotPntInfo(long nImageID, int nHotPntID) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\" where \"ID\" = ");
			strQuery.append(nHotPntID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			HotPntInfo hotPntInfo = new HotPntInfo();

			if (!drResult.next()) {
				hotPntInfo = null;
				strMessage = new StringBuffer("The hot point is not exist in the image.");
			} else {
				hotPntInfo.nID = nHotPntID;
				hotPntInfo.nImageID = nImageID;
				hotPntInfo.dPosX = drResult.getDouble("XPos");
				hotPntInfo.dPosY = drResult.getDouble("YPos");		
				hotPntInfo.nType = drResult.getInt("FlagType");
				hotPntInfo.strName = drResult.getString("FlagName");
				hotPntInfo.strRemark = drResult.getString("Remark");
				hotPntInfo.nLayerNum = drResult.getInt("LayerNum");
				
				strMessage = new StringBuffer("Succeed to get the information of the hot point.");
			}

			drResult.close();
			command.close();
			return hotPntInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	
	/** 获取一张图像上所有热点的信息
	 * @param nImageID 热点所在图像的ID
	 * @param hotPntsInfo 所有热点的信息 
	 * @return true：获取热点信息成功<br>false：获取热点信息失败 */
	public boolean getHotPntInfo(long nImageID, LinkedList<HotPntInfo> hotPntsInfo) { 
		try {			
			StringBuffer strQuery = new StringBuffer("select \"ID\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\"");

			Statement command = hConnection.createStatement();			
			ResultSet drResult = command.executeQuery(strQuery.toString());
			
			HotPntInfo hotPntInfo = new HotPntInfo();
			int nHotPntID = -1;

			while (drResult.next()) {
				nHotPntID = drResult.getInt("ID");
				hotPntInfo = getHotPntInfo(nImageID, nHotPntID);
				hotPntsInfo.add(hotPntInfo);
			}

			drResult.close();
			command.setFetchSize(0);
			command.close();
			
			strMessage = new StringBuffer("Succceed to get all hot points' ");
			strMessage.append("information of the image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 修改图像上热点数据
	 * @param nImageID 热点所在图像的ID
	 * @param nHotPntID 热点的ID
	 * @param hotPntData 修改后的热点数据
	 * @return true：热点数据修改成功<br>false：热点数据修改失败 */
	public boolean setHotPntData(long nImageID, int nHotPntID, byte[] hotPntData) {
		try {			
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\" set \"FlagData\" = ? where \"ID\" = ");
			strQuery.append(nHotPntID);

			PreparedStatement command = hConnection.prepareStatement(strQuery.toString());
			command.setBytes(1, hotPntData);
			command.executeUpdate();
			command.close();

			strMessage = new StringBuffer("Succeed to set the data of the hot point.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 获取图像上热点数据
	 * @param nImageID 热点所在图像的ID
	 * @param nHotPntID 热点的ID
	 * @return 成功：返回存储热点数据的数组<br>失败：返回 null 值 */
	public byte[] getHotPntData(long nImageID, int nHotPntID) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"FlagData\" from \"");
			strQuery.append(nImageID);
			strQuery.append("_FlagInfo\" where \"ID\" = ");
			strQuery.append(nHotPntID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			byte[] dataBuffer = null;

			if (!drResult.next()) {
				strMessage = new StringBuffer("The hot point is not exist in the image.");
			} else {
				dataBuffer = drResult.getBytes("FlagData");
				strMessage = new StringBuffer("Succeed to get the data of the hot point.");
			}

			drResult.close();
			command.close();
			return dataBuffer;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}	
}
