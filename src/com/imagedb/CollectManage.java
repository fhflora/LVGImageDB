package com.imagedb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;

import com.imagedb.struct.ImageInfo;

/** 用户收藏管理类
 * @author		杨冲
 * @version		1.00 11 July 2014 */
public class CollectManage extends ImageDatabase {
	
	public static void main(String[] args) throws IOException {

    }
	
	public CollectManage(Connection ConnTemp) {
		hConnection = ConnTemp;
	}
	
	/** 判断收藏总表是否存在
	 * @param nUserID 用户的ID
	 * @return 0：收藏总表存在 <br>1：收藏总表不存在 */
	public int tableIsExist(int nUserID) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\"");
			
			Statement command = hConnection.createStatement();
			command.executeQuery(strQuery.toString());
			command.close();
			
			strMessage = new StringBuffer("The table is existed in the database.");
			return 0;
		} catch (Exception e) {
			strMessage = new StringBuffer(e.getMessage());
			return 1;
		}
	}
	
	/** 判断收藏子表是否存在
	 * @param nUserID 用户的ID
	 * @param strTableName 子表的客户端名称
	 * @return 0：收藏子表存在 <br>1：收藏子表不存在 <br>-1：命令运行错误*/
	public int tableIsExist(int nUserID, String strTableName) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\" where \"TableName\" = '");
			strQuery.append(strTableName);
			strQuery.append("'");
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nExist = 0;           

            if (drResult.next()) {
                strMessage = new StringBuffer("The table is existed in the database.");       
            } else {
                strMessage = new StringBuffer("The table is not existed in the database.");               
                nExist = 1;
            }
            
            drResult.close();
            command.close();
            return nExist;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return -1;
        }
	}
	
	/** 判断图像在收藏子表是否存在
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @param nImageID 图像的ID
	 * @return 0：图像在收藏子表中存在 <br>1：图像在收藏子表中不存在 <br>-1：命令运行错误*/
	public int imageIsExist(int nUserID, int nTableID, long nImageID) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nExist = 0;
			
            if (drResult.next()) {
                strMessage = new StringBuffer("The image is existed in the table.");       
            } else {
                strMessage = new StringBuffer("The image is not existed in the table.");               
                nExist = 1;
            }
            
            drResult.close();
            command.close();
            return nExist;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return -1;
        }
	}
	
	/** 通过收藏子表的客户端名称获取收藏子表的ID
	 * @param nUserID 用户的ID
	 * @param strTableName 收藏子表在客户端的名称
	 * @return 成功：返回表在数据库端的ID<br>失败：返回-1 */
	public int getTableID(int nUserID, String strTableName) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\" where \"TableName\" = '");
			strQuery.append(strTableName);
			strQuery.append("'");
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nTableID = -1;
			
			if (drResult.next()) {
				nTableID = drResult.getInt("ID");
				strMessage = new StringBuffer("Succeed to get the table's ID."); 
			} else {
				strMessage = new StringBuffer("The table is not existed in the database.");   
			}
			
            drResult.close();
            command.close();                       
            return nTableID;           
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return -1;
        }
	}
	
	/** 获取收藏子表的客户端名称
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @return 成功：返回表在客户端的名称<br>失败：返回 null 值 */
	public String getTableName(int nUserID, int nTableID) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\" where \"ID\" = ");
			strQuery.append(nTableID);
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			String strTableName = null;
			
			if (drResult.next()) {
				strTableName = drResult.getString("TableName");
				strMessage = new StringBuffer("Succeed to get the table's name."); 
			} else {
				strMessage = new StringBuffer("The table is not existed in the database.");   
			}
			
            drResult.close();
            command.close();                       
            return strTableName;          
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return null;
        }
	}
	
	/** 修改收藏子表的客户端名称
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @param strTableName 修改后收藏子表的名称
	 * @return true：子表在客户端的名称修改成功<br>false：子表在客户端的名称修改失败 */
	public boolean setTableName(int nUserID, int nTableID, String strTableName) {
		try {
			if (1 == tableIsExist(nUserID, strTableName)) {
				StringBuffer strQuery = new StringBuffer("update \"");
				strQuery.append(nUserID);
				strQuery.append("_Top\" set \"TableName\" = '");
				strQuery.append(strTableName);
				strQuery.append("' where \"ID\" = ");
				strQuery.append(nTableID);
				
				Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());
	            command.close();   
	            
	            strMessage = new StringBuffer("Succeed to set the table's name.");
	            return true;    
			} else {
				return false;
			}			        
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return false;
        }
	}
	
	/** 获取收藏子表的备注信息 
	 * @param nUserID 用户的ID
     * @param nTableID 收藏子表的ID
     * @return 成功：返回收藏子表的备注信息 <br>失败：返回 null 值*/
	public String getTableRemark(int nUserID, int nTableID) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"Remark\" from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\" where \"ID\" = ");
			strQuery.append(nTableID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			String strTableRemark = null;
			
			if (drResult.next()) {
				strTableRemark = drResult.getString("Remark");
				strMessage = new StringBuffer("Succeed to get the table's remark."); 
			} else {
				strMessage = new StringBuffer("The table is not existed in the database.");   
			}

			drResult.close();
			command.close();
			return strTableRemark;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
    
	/** 修改收藏子表的备注信息 
	 * @param nUserID 用户的ID
	 * @param nTableID 要修改的表在数据库中的ID
	 * @param strTableRemark 修改后表的备注信息
	 * @return true：表的备注信息修改成功<br>false：表的备注信息修改失败 */
	public boolean setTableRemark(int nUserID, int nTableID, String strTableRemark) {
		try {
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\" set \"Remark\" = '");
			strQuery.append(strTableRemark);
			strQuery.append("' where \"ID\" = ");
			strQuery.append(nTableID);

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to set the table's remark.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 创建一个收藏总表
	 * @param nUserID 用户的ID
	 * @return true：收藏总表创建成功<br>false：收藏总表创建失败 */
	public boolean createTable(int nUserID) {
		try {
			StringBuffer strQuery = new StringBuffer("create table \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\" (\"ID\" serial PRIMARY KEY,");
            strQuery.append("\"TableName\" varchar(256), \"Remark\" text )");          
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());						
            command.close();

            strMessage = new StringBuffer("Succceed to create the table.");
            return true;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return false;
        }
	}

	/** 创建一个收藏子表
	 * @param nUserID 用户的ID
	 * @param strTableName 收藏子表的客户端名称
	 * @param strRemark 收藏子表的备注信息
	 * @return true：收藏子表创建成功<br>false：收藏子表创建失败 */
	public boolean createTable(int nUserID, String strTableName, String strRemark) {
		boolean isAutoCommit = true;
		try {
			if (1 == tableIsExist(nUserID, strTableName)) {
				isAutoCommit = hConnection.getAutoCommit();				
				if (isAutoCommit) {
					hConnection.setAutoCommit(false);
				}
				
				// 向收藏总表中插入收藏子表的记录
				StringBuffer strQuery = new StringBuffer("insert into \"");
				strQuery.append(nUserID);
				strQuery.append("_Top\" (\"TableName\", \"Remark\") values ('");
	            strQuery.append(strTableName); 
	            strQuery.append("', '");
	            strQuery.append(strRemark);
	            strQuery.append("')");        
				
	            Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());
				
				// 获取收藏子表的ID
				int nTableID = getTableID(nUserID, strTableName);
				if (-1 == nTableID) {
					hConnection.rollback();
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					return false;
				}
	            
				// 创建收藏子表
				strQuery = new StringBuffer("create table \"");
				strQuery.append(nUserID);
				strQuery.append("_Top_");
				strQuery.append(nTableID);
				strQuery.append("\" (\"ID\" bigint PRIMARY KEY, \"ImageName\" varchar(256))");      						
				command.executeUpdate(strQuery.toString());	
				
				if (isAutoCommit) {
					hConnection.commit();
					hConnection.setAutoCommit(isAutoCommit);
				}

	            command.close();
	            strMessage = new StringBuffer("Succceed to create the table.");
	            return true;
			} else {
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
	
	/** 清空一个收藏总表 
     * @param nUserID 用户的ID
     * @return true：收藏总表清空成功<br>false：收藏总表清空失败  */
	public boolean clearTable(int nUserID) {
		boolean isAutoCommit = true;
		try {
			Hashtable<Integer, String> hashTableID = new Hashtable<Integer, String>();
			isAutoCommit = hConnection.getAutoCommit();	
			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}

			// 获取收藏总表中所有收藏子表的ID
			if (!getTablesID(nUserID, hashTableID)) {
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return false;
			}

			// 清空收藏总表（删除该收藏总表中所有的收藏子表）
			for (Iterator<Integer> it = hashTableID.keySet().iterator(); it.hasNext();) {
				if (!deleteTable(nUserID, Integer.parseInt(it.next().toString()))) {
					hConnection.rollback();
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					return false;
				}
			}

			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			strMessage = new StringBuffer("Succeed to clear the table.");
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
	
	/** 清空一个收藏子表 
     * @param nUserID 用户的ID
     * @param nTableID 收藏子表的ID
     * @return true：收藏子表清空成功<br>false：收藏子表清空失败  */
	public boolean clearTable(int nUserID, int nTableID) {
		try {
			StringBuffer strQuery = new StringBuffer("truncate \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\"");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();
			
			strMessage = new StringBuffer("Succeed to clear the table.");
			return true;
		} catch (Exception e) {
			strMessage = new StringBuffer(e.getMessage());
			return false;
		}
	}
	
	/** 删除一个收藏子表 
     * @param nUserID 用户的ID
     * @param nTableID 收藏子表的ID
     * @return true：收藏子表删除成功<br>false：收藏子表删除失败  */
	public boolean deleteTable(int nUserID, int nTableID) {
		try {
			StringBuffer strQuery = new StringBuffer("drop table \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\"");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();
			
			strMessage = new StringBuffer("Succeed to delete the table.");
			return true;
		} catch (Exception e) {
			strMessage = new StringBuffer(e.getMessage());
			return false;
		}
	}
	
	/** 删除一个收藏总表 
     * @param nUserID 用户的ID
     * @return true：收藏总表删除成功<br>false：收藏总表删除失败  */
	public boolean deleteTable(int nUserID) {
		boolean isAutoCommit = true;
		try {
			isAutoCommit = hConnection.getAutoCommit();				
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			if (!clearTable(nUserID)) {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return false;
			}
			
			StringBuffer strQuery = new StringBuffer("drop table \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\"");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			
			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			command.close();			
			strMessage = new StringBuffer("Succeed to delete the table.");
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
	
	/** 向收藏子表中添加一张图像
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @param nImageID 图像的ID
	 * @return true：图像添加成功<br>false：图像添加失败 */
	public boolean addImage(int nUserID, int nTableID, long nImageID) {
		try {
			if (1 == imageIsExist(nUserID, nTableID, nImageID)) {
				ImageManage imageManage = ImageDatabase.getImageManage((int) (nImageID >> 56), hConnection);
				ImageInfo imageInfo = imageManage.getImageInfo(nImageID);
				
				if (null == imageInfo) {
					return false;
				} else {
					StringBuffer strQuery = new StringBuffer("insert into \"");
					strQuery.append(nUserID);
					strQuery.append("_Top_");
					strQuery.append(nTableID);
					strQuery.append("\" (\"ImageName\", \"ID\") values ('");
		            strQuery.append(imageInfo.strImageName); 
		            strQuery.append("', ");
		            strQuery.append(nImageID);
		            strQuery.append(")");
		            
		            Statement command = hConnection.createStatement();
					command.executeUpdate(strQuery.toString());
					
					strMessage = new StringBuffer("Succceed to add the image.");
		            return true;
				}
			} else {
				return false;
			}			
		} catch (Exception e) {
			strMessage = new StringBuffer(e.getMessage());
			return false;
		}
	}
	
	/** 从收藏子表中移除一张图像
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @param nImageID 图像的ID
	 * @return true：图像移除成功<br>false：图像移除失败 */
	public boolean removeImage(int nUserID, int nTableID, long nImageID) {
		try {
			StringBuffer strQuery = new StringBuffer("delete from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());

			strMessage = new StringBuffer("Succceed to remove the image.");
			return true;
		} catch (Exception e) {
			strMessage = new StringBuffer(e.getMessage());
			return false;
		}
	}
	
	/** 获取收藏子表中一张图像的名称
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @param nImageID 图像的ID
	 * @return 成功：返回图像名称<br>失败：返回 null 值 */
	public String getImageName(int nUserID, int nTableID, long nImageID) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			String strImageName = null;

			if (drResult.next()) {
				strImageName = drResult.getString("ImageName");
				strMessage = new StringBuffer("Succeed to get the image's name.");
			} else {
				strMessage = new StringBuffer("The image is not exist in the table.");
			}
			
			drResult.close();
			command.close();			
			return strImageName;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	
	/** 修改收藏子表中一张图像的名称
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表的ID
	 * @param nImageID 图像的ID
	 * @param strImageNmae 修改后图像的名称
	 * @return true：图像名称修改成功<br>false：图像名称修改失败*/
	public boolean setImageName(int nUserID, int nTableID, long nImageID, String strImageNmae) {
		try {
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\" set \"ImageName\" = '");
			strQuery.append(strImageNmae);
			strQuery.append("' where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to set the image's name.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 获取收藏总表中所有收藏子表的ID与名称 
	 * @param nUserID 用户的ID
	 * @param nChildTabID 存放收藏子表的ID与名称
	 * @return　true：获取成功<br>false：获取失败 */
	public boolean getTablesID(int nUserID, Hashtable<Integer, String> nChildTabID) {
		try {
			int nID = 0;				// 表的ID
			String strName = null;		// 表的名称

			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top\"");

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			while (drResult.next()) {
				nID = drResult.getInt("ID");
				strName = drResult.getString("TableName");
				nChildTabID.put(nID, strName);
			}

			drResult.close();
			command.close();
		
			strMessage = new StringBuffer("Succeed to get ID of child tables.");
			return true;
		} catch (Exception ex) {
			nChildTabID = null;	
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	/** 获取收藏子表中所有图像的ID与名称 
	 * @param nUserID 用户的ID
	 * @param nTableID 收藏子表在数据库中的ID
	 * @param nImageID 存放图像的ID与名称
	 * @return true：获取成功<br>false：获取失败 */
	public boolean getImagesID(int nUserID, int nTableID, Hashtable<Long, String> nImageID) {
		try {
			long nID = 0; // 表的ID
			String strName = null; // 表的名称
			
			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(nUserID);
			strQuery.append("_Top_");
			strQuery.append(nTableID);
			strQuery.append("\"");

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			while (drResult.next()) {
				nID = drResult.getLong("ID");
				strName = drResult.getString("ImageName");
				nImageID.put(nID, strName);
			}

			drResult.close();
			command.close();
			
			strMessage = new StringBuffer("Succeed to get ID of images.");
			return true;
		} catch (Exception ex) {
			nImageID = null;
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}       
}
