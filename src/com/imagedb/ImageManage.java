package com.imagedb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import com.imagedb.struct.ImageInfo;


/** 图像管理基类
 * @author		杨冲
 * @version		1.00 1 July 2014 */
public abstract class ImageManage extends ImageDatabase {
	
	public ImageManage(Connection ConnTemp) {
		hConnection = ConnTemp;
	}
	
	/** 获取一个表格的序列号 
     * @param nTableID 要获取序列号的表格ID
     * @return 成功：返回表格的序列号<br>失败：返回 -1 */
    protected int getSerialNum(int nTableID) {
        try {
            StringBuffer strQuery = new StringBuffer("select \"SerialNum\" from \"00001\" where \"ID\" = ");
            strQuery.append(nTableID);

            // 定义执行查询的命令句柄和查询的结果集
            Statement command = hConnection.createStatement();
            ResultSet drResult = command.executeQuery(strQuery.toString());
            int nSerialNum = -1;
            
            if (drResult.next()) {
            	nSerialNum = drResult.getInt("SerialNum");		// 获取序列号
            	strMessage = new StringBuffer("Succeed to get the serial number.");
            } else {
            	strMessage = new StringBuffer("The table's ID is wrong.");
            }
                     
            // 关闭命令句柄和结果集
            drResult.close();
            command.close();        	            
            return nSerialNum;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return -1;
        }
    }
    
    /** 修改一个表格的序列号 
     * @param nTableID 要修改序列号的表格ID
     * @return true：表格的序列号修改成功<br>false：表格的序列号修改失败 */
    protected boolean setSerialNum(int nTableID) {
        try {
            StringBuffer strQuery = new StringBuffer("update \"00001\" set ");
            strQuery.append("\"SerialNum\" = \"SerialNum\" + 1 where \"ID\" = ");
            strQuery.append(nTableID);

            // 执行查询语句
            Statement command = hConnection.createStatement();
            command.executeUpdate(strQuery.toString());
            command.close();

            // 设置命令反馈信息
            strMessage = new StringBuffer("Succeed to set the serial number of the table.");
            return true;
        } catch (Exception ex) {
            strMessage = new StringBuffer(ex.getMessage());          
            return false;
        }
    }
	
	/** 通过图像的ID获取图像所在表的数据库端ID（名称）*/
    protected static int getTableID(long nImageID) {
        return (int) (nImageID >> 32);
    }
	
	/** 查询四叉树编码从0开始 */
	protected static int getQueryCode(int nLay, int nRow, int nCol) {
        if (nRow < Math.pow(2, nLay) && nRow < Math.pow(2, nLay)) {
            int nRowLen = getBitLength(nRow);
            int nColLen = getBitLength(nCol);

            if (nRowLen < nColLen) {
                nRowLen = nColLen;
            }

            int nTemp;
            int nCode = nCol & 0x01 | (nRow & 0x01) << 1;

            for (int i = 1; i < nRowLen; i++) {
                nCol = nCol >> 1;
                nRow = nRow >> 1;
                nTemp = nCol & 0x01 | (nRow & 0x01) << 1;
                nCode = nTemp << (2 * i) | nCode;
            }

            nCode = nCode + (int)(Math.pow(4, nLay) - 1) / 3;
            return nCode;
        } else {
            return -1;
        }
    }

	/** 获取变量的位长 */
	protected static int getBitLength(int nNum) {
        int nLen = 0;

        while (nNum != 0) {
            nLen++;
            nNum = nNum >> 1;
        }

        return nLen;
    }
    
	/** byte数组转换成int类型 */
	protected static int byteArrayToInt(byte[] bytes, boolean bLittle) {
    	int nValue = 0;
    	int nShift = 0;
    	
    	if(bLittle) {  		
    		for(int i = 0; i < 4; i++) {
    			nShift = i*8;
    			nValue += (bytes[i] & 0x000000FF) << nShift;
    		}
    	} else {   		
    		for(int i = 0; i < 4; i++) {
    			nShift = (3 - i) * 8;
    			nValue += (bytes[i] & 0x000000FF) << nShift;
    		}
    	}
    	
    	return nValue;
    }
	
	/** int类型转换成byte数组 */
	protected static byte[] intToByteArray(int nNum, boolean bLittle) {
    	byte[] num = new byte[4];
    	
    	if(bLittle) {  		
    		num[0] = (byte) ((nNum << 24) >> 24);
    		num[1] = (byte) ((nNum << 16) >> 24);
    		num[2] = (byte) ((nNum << 8) >> 24);
    		num[3] = (byte) (nNum >> 24);
    	} else {   		
    		num[3] = (byte) ((nNum << 24) >> 24);
    		num[2] = (byte) ((nNum << 16) >> 24);
    		num[1] = (byte) ((nNum << 8) >> 24);
    		num[0] = (byte) (nNum >> 24);
    	}
    	
    	return num;
    }
	
	/** 通过图像的扩展名获取图像类型ID */
	protected static int getImageType(String strExte) {
		if (strExte.equals("bmp")) {
			return 1;
		} else if (strExte.equals("gif")) {
			return 1;
		} else if (strExte.equals("jpg")) {
			return 1;
		} else if (strExte.equals("jpeg")) {
			return 1;
		} else if (strExte.equals("png")) {
			return 1;
		} else if (strExte.equals("wbmp")) {
			return 1;
		} else if (strExte.equals("tbm")) {
			return 2;
		} else if (strExte.equals("pom")) {
			return 3;
		} else {
			return 0;
		}
    }
			
	/** 判断一个图像表在数据库中是否存在 
     * @param strTableName 要判断表的客户端名称
     * @return 0：表在数据库中存在<br>1：表在数据库中不存在<br>-1：命令运行错误 */
    public int tableIsExist(String strTableName) {
        try {
            StringBuffer strQuery = new StringBuffer("select * from \"00000\" where \"Name\" = '");
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
	
    /** 在数据库中新建一个图像子表
     * @param strTableName 表的客户端名称
     * @param strDescInfo 表的描述信息
     * @param nTableType 表的类型
     * @return true：图像子表创建成功<br>false：图像子表创建失败 */
	public boolean createTable(String strTableName, String strDescInfo, int nTableType) {
		boolean isAutoCommit = true;
		try {
			if (1 == tableIsExist(strTableName)) {
				int nTopTableID = 10000 + nTableType;
				int nSerialNum = getSerialNum(nTopTableID); // 获取序列号

				if (nSerialNum == -1) {
					return false;
				} else if (nSerialNum > Math.pow(2, 24) - 10) {
					strMessage = new StringBuffer("The serial number of tables exceeds");
					strMessage.append(" the upper limit, please create a new database.");
					return false;
				}

				isAutoCommit = hConnection.getAutoCommit();				
				if (isAutoCommit) {
					hConnection.setAutoCommit(false);
				}
				
				int nTableID = (nTableType << 24) + nSerialNum; 				// 新建图像子表的ID
				ImageManage imageManageTemp = getImageManage(nTableType, hConnection);		// 获取图像管理对象			
				Statement command = hConnection.createStatement();

				// 创建表格
				if (!imageManageTemp.createImageTable(nTableID)) {
					hConnection.rollback();
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					return false;
				}
				
				// 在图像总表中插入新创建的子表
				StringBuffer strQuery = new StringBuffer("insert into \"");
				strQuery.append(String.valueOf(nTopTableID));
				strQuery.append("\"(\"ID\",\"TableName\",\"Remark\") values (");
				strQuery.append(nTableID);
				strQuery.append(",'");
				strQuery.append(strTableName);
				strQuery.append("','");
				strQuery.append(strDescInfo);
				strQuery.append("')");	
				command.executeUpdate(strQuery.toString());

				// 修改系统表（表ID与表名称对照表）
				strQuery = new StringBuffer("insert into \"00000\"( \"ID\",");
				strQuery.append(" \"Name\") values (");
				strQuery.append(nTableID);
				strQuery.append(",'");
				strQuery.append(strTableName);
				strQuery.append("')");
				command.executeUpdate(strQuery.toString());

				// 修改序列号记录表
				strQuery = new StringBuffer("insert into \"00001\"( \"ID\",");
				strQuery.append(" \"SerialNum\") values (");
				strQuery.append(nTableID);
				strQuery.append(",0)");
				command.executeUpdate(strQuery.toString());

				if (!setSerialNum(nTopTableID)) {
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
				
				command.close();
				strMessage = new StringBuffer("Succeed to create the table.");
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
    
    /** 清空数据库中的一个图像表 
     * @param nTableID 要清空的图像表ID
     * @return true：图像表清空成功<br>false：图像表清空失败  */
	public boolean clearTable(int nTableID) {
		boolean isAutoCommit = true;
		try {
			isAutoCommit = hConnection.getAutoCommit();				
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			if (nTableID < Math.pow(2, 24)) {
				if (nTableID < 10000) {
					strMessage = new StringBuffer("Cannot clear a system table.");
					return false;
				}

				Hashtable<Integer, String> hashTableID = new Hashtable<Integer, String>();

				// 获取图像总表中所有图像子表的ID
				if (!getChildTablesID(nTableID, hashTableID)) {
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					return false;
				}

				// 清空图像总表（删除该图像总表中所有的图像子表）
				for (Iterator<Integer> it = hashTableID.keySet().iterator(); it.hasNext();) {
					if (!deleteTable(Integer.parseInt(it.next().toString()))) {
						hConnection.rollback();
						if (isAutoCommit) {
							hConnection.setAutoCommit(isAutoCommit);
						}
						return false;
					}
				}
			} else {
				Hashtable<Long, String> hashImageID = new Hashtable<Long, String>();

				// 获取图像子表中所有图像的ID
				if (!getImagesID(nTableID, hashImageID)) {
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					return false;
				}

				int nTableType = nTableID >> 24;
				ImageManage imageManageTemp = getImageManage(nTableType, hConnection);			

				// 删除该图像子表中所有的图像
				for (Iterator<Long> it = hashImageID.keySet().iterator(); it.hasNext();) {
					if (!imageManageTemp.deleteImage(it.next())) {
						hConnection.rollback();
						if (isAutoCommit) {
							hConnection.setAutoCommit(isAutoCommit);
						}
						return false;
					}
				}

				// 修改序列号记录表
				StringBuffer strQuery = new StringBuffer("update \"00001\" set ");
				strQuery.append("\"SerialNum\" = 0 where \"ID\" = ");
				strQuery.append(nTableID);

				Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());
				command.close();
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
    
	/** 删除数据库中的一个图像子表 
	 * @param nTableID 要删除图像子表的ID
	 * @return true：图像子表删除成功<br>false：图像子表删除失败 */
	public boolean deleteTable(int nTableID) {
		boolean isAutoCommit = true;
		try {
			isAutoCommit = hConnection.getAutoCommit();				
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			if (nTableID < Math.pow(2, 24)) {
				strMessage = new StringBuffer("Cannot delete a system table.");
				return false;
			}

			// 清空该图像子表
			if (!clearTable(nTableID)) {
				hConnection.rollback();
				if (isAutoCommit) {
					hConnection.setAutoCommit(isAutoCommit);
				}
				return false;
			}
			
			// 修改系统表（表ID与表名称对照表）
			StringBuffer strQuery = new StringBuffer("delete from \"00000\" where \"ID\" = ");
			strQuery.append(nTableID);
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());

			// 修改序列号记录表
			strQuery = new StringBuffer("delete from \"00001\" where \"ID\" = ");
			strQuery.append(nTableID);
			command.executeUpdate(strQuery.toString());

			// 修改该图像子表所在的图像总表
			String strTopImgTab = Integer.toString(getTableID(nTableID));
			strQuery = new StringBuffer("delete from \"");
			strQuery.append(strTopImgTab);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nTableID);
			command.executeUpdate(strQuery.toString());

			// 删除该图像子表
			strQuery = new StringBuffer("drop table \"");
			strQuery.append(nTableID);
			strQuery.append("\"");
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
				hConnection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
        
	/** 通过表的客户端名称获取图像表的数据库端ID（名称） 
	 * @param strTableName 表在客户端的名称
	 * @return 成功：返回表在数据库端的ID<br>失败：返回-1 */
	public int getTableID(String strTableName) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"ID\" from \"00000\" where \"Name\" = '");
			strQuery.append(strTableName);
			strQuery.append("'");

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nTableID = -1;

			if (drResult.next()) {
				nTableID = drResult.getInt("ID");
				strMessage = new StringBuffer("Succeed to get the table's ID.");				
			} else {
				strMessage = new StringBuffer("The table is not existed in the database. ");
			}
			
			drResult.close();
			command.close();
			return nTableID;			
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return -1;
		}
	}

    /** 通过子表的数据库端ID（名称）获取子表所在总表的数据库端ID（名称） */
    public int getTableID(int nTableID) {
    	return (nTableID >> 24) + 10000; 
    }
    
	/** 通过表的数据库端的ID（名称）获取表的客户端名称 
	 * @param nTableID 表在数据库中的ID
	 * @return 成功：返回表在客户端的名称<br>失败：返回 null 值*/
	public String getTableName(int nTableID) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"Name\" from \"00000\" where \"ID\" = ");
			strQuery.append(nTableID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			String strTableName = null;

			if (drResult.next()) {
				strTableName = drResult.getString("Name");			
				strMessage = new StringBuffer("Succeed to get the table's name.");
			} else {
				strMessage = new StringBuffer("The table is not existed in the database. ");
			}
			
			drResult.close();
			command.close();
			return strTableName;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
    
	/** 修改表的客户端名称 
	 * @param nTableID 要修改的表在数据库中的ID
	 * @param strTabNewName 修改后的表在客户端的名称
	 * @return true：表的客户端名称修改成功<br>false：表的客户端名称修改失败*/
	public boolean setTableName(int nTableID, String strTabNewName) {
		boolean isAutoCommit = true;
		try {
			if (1 == tableIsExist(strTabNewName)) {				
				isAutoCommit = hConnection.getAutoCommit();					
				if (isAutoCommit) {
					hConnection.setAutoCommit(false);
				}
												
				// 修改系统表（表ID与表Name对照表）
				StringBuffer strQuery = new StringBuffer("update \"00000\" set \"Name\" = '");
				strQuery.append(strTabNewName);
				strQuery.append("' where \"ID\" = ");
				strQuery.append(nTableID);
				
				Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());

				// 判断该表是否为图像子表，如果是则须修改该子表所在的图像总表
				if (nTableID > Math.pow(2, 24) - 10) {
					String strTopImgTab = Integer.toString(getTableID(nTableID));
					strQuery = new StringBuffer("update \"");
					strQuery.append(strTopImgTab);
					strQuery.append("\" set \"TableName\" = '");
					strQuery.append(strTabNewName);
					strQuery.append("' where \"ID\" = ");
					strQuery.append(nTableID);
					command.executeUpdate(strQuery.toString());
				}

				if (isAutoCommit) {
					hConnection.commit();
					hConnection.setAutoCommit(isAutoCommit);
				}
				
				command.close();
				strMessage = new StringBuffer("Succeed to set the table's name.");
				return true;
			} else {
				return false;
			}			
		} catch (Exception ex) {
			try {
				hConnection.rollback();
				hConnection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 获取图像子表的备注信息 
     * @param nTableID 图像子表的ID
     * @return 图像子表的备注信息 */
	public String getTableRemark(int nTableID) {
		try {
			String strTopImgTab = Integer.toString(getTableID(nTableID));		// 获取图像总表的名称
			StringBuffer strQuery = new StringBuffer("select \"Remark\" from \"");
			strQuery.append(strTopImgTab);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nTableID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			String strTableRemark = null;

			if (drResult.next()) {
				strTableRemark = drResult.getString("Remark");
				strMessage = new StringBuffer("Succeed to get the table's remark.");
			} else {
				strMessage = new StringBuffer("The table's ID is wrong.");
			}
			 
			drResult.close();
			command.close();		
			return strTableRemark;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
    
	/** 修改表的备注信息 
	 * @param nTableID 要修改的表在数据库中的ID
	 * @param strTableRemark 修改后表的备注信息
	 * @return true：表的备注信息修改成功<br>false：表的备注信息修改失败 */
	public boolean setTableRemark(int nTableID, String strTableRemark) {
		try {
			String strTopImgTab = Integer.toString(getTableID(nTableID));
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(strTopImgTab);
			strQuery.append("\" set \"Remark\" = '");
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
    
	/** 获取所有图像总表的ID与名称 
	 * @param nTopTabID 存放图像总表的ID与名称
	 * @return true：获取成功<br>false：获取失败 */
	public boolean getTopTablesID(Hashtable<Integer, String> nTopTabID) {
		try {
			int nID = 0;				// 表的ID
			String strName = null;		// 表的名称

			StringBuffer strQuery = new StringBuffer("select \"ID\", \"Name\" from \"00000\" ");
			strQuery.append("where \"ID\" > 10000 and \"ID\" < 16777216");

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			while (drResult.next()) {
				nID = drResult.getInt("ID");
				strName = drResult.getString("Name");
				nTopTabID.put(nID, strName);
			}

			drResult.close();
			command.close();

			strMessage = new StringBuffer("Succeed to get ID of top tables.");
			return true;
		} catch (Exception ex) {
			nTopTabID = null;
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	/** 获取图像总表中所有图像子表的ID与名称 
	 * @param nTopTabID 图像总表在数据库中的ID
	 * @param nChildTabID 存放图像子表的ID与名称
	 * @return　true：获取成功<br>false：获取失败 */
	public boolean getChildTablesID(int nTopTabID, Hashtable<Integer, String> nChildTabID) {
		try {
			int nID = 0;				// 表的ID
			String strName = null;		// 表的名称

			StringBuffer strQuery = new StringBuffer("select \"ID\", \"TableName\" from \"");
			strQuery.append(nTopTabID);
			strQuery.append("\"");

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

	/** 获取图像子表中所有图像的ID与名称 
	 * @param nTableID 图像子表在数据库中的ID
	 * @param nImageID 存放图像的ID与名称
	 * @return true：获取成功<br>false：获取失败 */
	public boolean getImagesID(int nTableID, Hashtable<Long, String> nImageID) {
		try {
			long nID = 0; // 表的ID
			String strName = null; // 表的名称
			
			StringBuffer strQuery = new StringBuffer("select \"ID\", \"ImageName\" from \"");
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
	
	/** 获取图像信息 
	 * @param nImageID 要获取信息的图像ID
	 * @return 成功：返回图像信息<br>失败：返回 null 值 */
	public ImageInfo getImageInfo(long nImageID) {
		try {
			ImageInfo imageInfo = new ImageInfo();
			String strTableName = Integer.toString(getTableID(nImageID)); 		// 图像所在表的名称

			StringBuffer strQuery = new StringBuffer("select * from \"");
			strQuery.append(strTableName);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			if (!drResult.next()) {
				imageInfo = null;
				strMessage = new StringBuffer("The image is not exist in the database.");
			} else {
				imageInfo.strImageName = drResult.getString("ImageName");
				imageInfo.dtAcquireTime = new Date(drResult.getDate("AcquireTime").getTime());
				imageInfo.dtInputTime = new Date(drResult.getDate("InputTime").getTime());
				imageInfo.strDescInfo = drResult.getString("KeyDescribe");
				imageInfo.nImageSize = drResult.getInt("ImageSize");
				imageInfo.strImageFormat = drResult.getString("ImageFormat");
				imageInfo.bFlagInfo = drResult.getBoolean("FlagInfo");
				imageInfo.nTableID = Integer.parseInt(strTableName);
				imageInfo.nImageID = nImageID;
				
				strMessage = new StringBuffer("Succeed to get the information of the image.");
			}

			drResult.close();
			command.close();
			return imageInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}	
    
	/** 修改图像信息 
	 * @param nImageID 要修改信息的图像ID
	 * @param newImageInfo 修改后的图像信息
	 * @return true：图像信息修改成功<br>false：图像信息修改失败 */
	public boolean setImageInfo(long nImageID, ImageInfo newImageInfo) {
		try {
			String strTableName = Integer.toString(getTableID(nImageID));		// 图像所在表的名称
			String strAcquireTime = String.format("%tF", newImageInfo.dtAcquireTime);
			String strInputTime = String.format("%tF", newImageInfo.dtInputTime);

			// 修改图像信息
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(strTableName);
			strQuery.append("\" set \"ImageName\" = '");
			strQuery.append(newImageInfo.strImageName);
			strQuery.append("', \"AcquireTime\" = '");
			strQuery.append(strAcquireTime);
			strQuery.append("', \"InputTime\" = '");
			strQuery.append(strInputTime);
			strQuery.append("', \"ImageSize\" = ");
			strQuery.append(newImageInfo.nImageSize);
			strQuery.append(", \"KeyDescribe\" = '");
			strQuery.append(newImageInfo.strDescInfo);
			strQuery.append("', \"ImageFormat\" = '");
			strQuery.append(newImageInfo.strImageFormat);
			strQuery.append("', \"FlagInfo\" = '");
			strQuery.append(newImageInfo.bFlagInfo);
			strQuery.append("' where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to set the information of the image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 删除数据库中的一张图像 
	 * @param nImageID 要删除的图像在数据库中的ID
	 * @return true：图像删除成功<br>false：图像删除失败 */
	public boolean deleteImage(long nImageID) {
		boolean isAutoCommit = true;
		try {
			
			// 获取图像信息
			ImageInfo imageInfoTemp = getImageInfo(nImageID);
			if (null == imageInfoTemp) {
				return false;
			}
			
			isAutoCommit = hConnection.getAutoCommit();			
			if (isAutoCommit) {
				hConnection.setAutoCommit(false);
			}
			
			String strTableName = Integer.toString(getTableID(nImageID));		
			Statement command = hConnection.createStatement();
			
			// 删除图像子表中的图像信息
			StringBuffer strQuery = new StringBuffer("delete from \"");
			strQuery.append(strTableName);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);
			command.executeUpdate(strQuery.toString());
			
			// 判断图像是否存在标注信息，若存在则须删除图像的标注信息
			if (imageInfoTemp.bFlagInfo) {
				HotPntManage hotPntManage = new HotPntManage(hConnection);			
				if (!hotPntManage.deleteHotPnt(nImageID)) {
					hConnection.rollback();
					if (isAutoCommit) {
						hConnection.setAutoCommit(isAutoCommit);
					}
					
					command.close();
					return false;
				}
			}
	
			if (isAutoCommit) {
				hConnection.commit();
				hConnection.setAutoCommit(isAutoCommit);
			}
			
			command.close();
			strMessage = new StringBuffer("Succeed to delete the image.");
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
	
	/** 创建一个存储图像的子表 
	 * @param nTableID 表在数据库中的ID
	 * @return true：表创建成功<br>false：表创建失败 */
	public abstract boolean createImageTable(int nTableID);
	
	/** 向数据库中添加一张图像 
	 * @param imageInfoTemp 要添加图像的信息
	 * @return 成功：返回所添加图像的ID<br>失败：返回-1; */
	public abstract long addImage(ImageInfo imageInfoTemp);
	
	/** 替换数据库中的一张图像 
	 * @param nImageID 被替换图像的ID
	 * @param strImagePath 新图像在本地磁盘的路径
	 * @return true：图像替换成功<br>false：图像替换失败 */
	public abstract boolean replaceImage(long nImageID, String strImagePath);
		
	/** 导出数据库中的一张图像 
	 * @param nImageID 要导出的图像在数据库中的ID
	 * @param strImagePath 导出图像在本地磁盘的存放路径（完整路径）
	 * @return true：图像导出成功<br>false：图像导出失败 */
	public abstract boolean exportImage(long nImageID, String strImagePath);
}
