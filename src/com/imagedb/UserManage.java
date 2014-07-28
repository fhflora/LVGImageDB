package com.imagedb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import com.imagedb.struct.UserInfo;


/** 客户端用户管理类
 * @author		杨冲
 * @version		1.00 4 July 2014 */
public class UserManage extends ImageDatabase {
	
	public UserManage(Connection ConnTemp) {
		hConnection = ConnTemp;
	}
	
	/** 添加一个客户端用户
	 * @param userInfo 用户信息
	 * @return true：用户添加成功<br>false：用户添加失败 */
	public boolean addUse(UserInfo userInfo) {
		try {
			if (1 == userIsExist(userInfo.strUserName)) {
				String strCreateTime = String.format("%tF",userInfo.createTime);
				String strLastLoginTime = String.format("%tF",userInfo.lastLoginTime);
				
				StringBuffer strQuery = new StringBuffer("insert into \"00002\" (");
				strQuery.append("\"UserName\", \"Password\", \"UserType\", ");
	            strQuery.append("\"Permission\", \"RealName\", \"Tel\", \"Email\",");
	            strQuery.append(" \"Created\", \"CreateTime\", \"State\", ");
	            strQuery.append("\"LastTime\", \"LoginIP\", \"Remark\") values ('");
				strQuery.append(userInfo.strUserName);
				strQuery.append("','");
				strQuery.append(userInfo.strPassword);
				strQuery.append("',");
				strQuery.append(userInfo.nType);
				strQuery.append(",");
				strQuery.append(userInfo.nPermission);
				strQuery.append(",'");
				strQuery.append(userInfo.strRealName);
				strQuery.append("','");
				strQuery.append(userInfo.strPhone);
				strQuery.append("','");
				strQuery.append(userInfo.strEmail);
				strQuery.append("','");
				strQuery.append(userInfo.strCreated);
				strQuery.append("','");
				strQuery.append(strCreateTime);
				strQuery.append("',");
				strQuery.append(userInfo.nState);
				strQuery.append(",'");
				strQuery.append(strLastLoginTime);
				strQuery.append("','");
				strQuery.append(userInfo.strLastLoginIP);
				strQuery.append("','");
				strQuery.append(userInfo.strRemark);
				strQuery.append("')");
				
				Statement command = hConnection.createStatement();
				command.executeUpdate(strQuery.toString());
				command.close();
				
				strMessage = new StringBuffer("Succceed to add the user.");
				return true;
			} else {
				return false;
			}			
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/**
	 * 通过登陆状态获取用户信息
	 * @param userName
	 * @return
	 */
	public UserInfo getUserInfoByState(byte state){
		UserInfo userInfo = new UserInfo();
		try{
			StringBuffer strQuery=new StringBuffer("select * from \"00002\" where \"State\" = ");
			strQuery.append(state);
			
			
			Statement command = hConnection.createStatement();
			System.out.println(strQuery.toString());
			ResultSet drResult = command.executeQuery(strQuery.toString());
			
			if(!drResult.next()){
				userInfo=null;
				strMessage=new StringBuffer("The user is not exist.");
			}else {
				userInfo.nID = drResult.getInt("ID");
				userInfo.createTime = new Date(drResult.getDate("CreateTime").getTime());
				userInfo.lastLoginTime = new Date(drResult.getDate("LastTime").getTime());			
				userInfo.nPermission = drResult.getByte("Permission");
				userInfo.nState = state;
				userInfo.nType = drResult.getByte("UserType");
				userInfo.strCreated = drResult.getString("Created");
				userInfo.strEmail = drResult.getString("Email");
				userInfo.strLastLoginIP = drResult.getString("LoginIP");
				userInfo.strPassword = drResult.getString("Password");
				userInfo.strPhone = drResult.getString("Tel");
				userInfo.strRealName = drResult.getString("RealName");
				userInfo.strRemark = drResult.getString("Remark");
				userInfo.strUserName = drResult.getString("UserName");
								
				strMessage = new StringBuffer("Succeed to get the information of the user.");
			}

			drResult.close();
			command.close();
			return userInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
		
	}
	
	/**
	 * 通过用户名获取用户信息
	 * @param userName
	 * @return
	 */
	public UserInfo getUserInfoByUserName(String userName){
		UserInfo userInfo = new UserInfo();
		try{
			StringBuffer strQuery=new StringBuffer("select * from \"00002\" where \"UserName\" = ");
			strQuery.append("'"+userName+"'");
			
			
			Statement command = hConnection.createStatement();
			System.out.println(strQuery.toString());
			ResultSet drResult = command.executeQuery(strQuery.toString());
			
			if(!drResult.next()){
				userInfo=null;
				strMessage=new StringBuffer("The user is not exist.");
			}else {
				userInfo.nID = drResult.getInt("ID");
				userInfo.createTime = new Date(drResult.getDate("CreateTime").getTime());
				userInfo.lastLoginTime = new Date(drResult.getDate("LastTime").getTime());			
				userInfo.nPermission = drResult.getByte("Permission");
				userInfo.nState = drResult.getByte("State");
				userInfo.nType = drResult.getByte("UserType");
				userInfo.strCreated = drResult.getString("Created");
				userInfo.strEmail = drResult.getString("Email");
				userInfo.strLastLoginIP = drResult.getString("LoginIP");
				userInfo.strPassword = drResult.getString("Password");
				userInfo.strPhone = drResult.getString("Tel");
				userInfo.strRealName = drResult.getString("RealName");
				userInfo.strRemark = drResult.getString("Remark");
				userInfo.strUserName = userName;
								
				strMessage = new StringBuffer("Succeed to get the information of the user.");
			}

			drResult.close();
			command.close();
			return userInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	/** 获取一个用户的信息
	 * @param nUserID 用户的编号
	 * @return 成功：返回用户信息<br>失败：返回  null 值 */
	public UserInfo getUserInfo(int nUserID) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"00002\" where \"ID\" = ");
			strQuery.append(nUserID);
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			UserInfo userInfo = new UserInfo();
			
			if (!drResult.next()) {
				userInfo = null;
				strMessage = new StringBuffer("The user is not exist.");				
			}else {
				userInfo.nID = nUserID;
				userInfo.createTime = new Date(drResult.getDate("CreateTime").getTime());
				userInfo.lastLoginTime = new Date(drResult.getDate("LastTime").getTime());			
				userInfo.nPermission = drResult.getByte("Permission");
				userInfo.nState = drResult.getByte("State");
				userInfo.nType = drResult.getByte("UserType");
				userInfo.strCreated = drResult.getString("Created");
				userInfo.strEmail = drResult.getString("Email");
				userInfo.strLastLoginIP = drResult.getString("LoginIP");
				userInfo.strPassword = drResult.getString("Password");
				userInfo.strPhone = drResult.getString("Tel");
				userInfo.strRealName = drResult.getString("RealName");
				userInfo.strRemark = drResult.getString("Remark");
				userInfo.strUserName = drResult.getString("UserName");
								
				strMessage = new StringBuffer("Succeed to get the information of the user.");
			}

			drResult.close();
			command.close();
			return userInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}
	
	/** 获取一个用户的信息
	 * @param strKeywords 关键字（默认值为用户名）
	 * @param nPermission 用户权限
	 * @param nUserType 用户类型
	 * @return 成功：返回用户信息<br>失败：返回  null 值 */
	public UserInfo getUserInfo(String strKeywords, int nPermission, int nUserType, int nState) {
		try {
			StringBuffer strQuery = new StringBuffer("select * from \"00002\" where ");
			
			if (null == strKeywords) {
				if (-1 == nPermission) {
					if (-1 == nUserType) {
						if (-1 == nState) {
							strMessage = new StringBuffer("查询条件不能为空！");
							return null;
						} else {
							strQuery.append(" \"State\" = ");
							strQuery.append(nState);
						}						
					} else {
						strQuery.append(" \"UserType\" = ");
						strQuery.append(nUserType);
						
						if (-1 != nState) {
							strQuery.append(" and \"State\" = ");
							strQuery.append(nState);
						}
					}				
				} else {
					strQuery.append(" \"Permission\" = ");
					strQuery.append(nPermission);
					
					if (-1 != nUserType) {
						strQuery.append(" and \"UserType\" = ");
						strQuery.append(nUserType);
					}
					
					if (-1 != nState) {
						strQuery.append(" and \"State\" = ");
						strQuery.append(nState);
					}
				}
			} else {
				strQuery.append(" \"UserName\" = '");
				strQuery.append(strKeywords);
				strQuery.append("'");
				
				if (-1 != nPermission) {
					strQuery.append(" and \"Permission\" = ");
					strQuery.append(nPermission);
				}
				
				if (-1 != nUserType) {
					strQuery.append(" and \"UserType\" = ");
					strQuery.append(nUserType);
				}
				
				if (-1 != nState) {
					strQuery.append(" and \"State\" = ");
					strQuery.append(nState);
				}
			}
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			UserInfo userInfo = new UserInfo();
			
			if (!drResult.next()) {
				userInfo = null;
				strMessage = new StringBuffer("The user is not exist.");				
			}else {
				userInfo = getUserInfo(drResult.getInt("ID"));								
				strMessage = new StringBuffer("Succeed to get the information of the user.");
			}

			drResult.close();
			command.close();
			return userInfo;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	} 
	
	/** 获取所有用户的信息
	 * @param listUserInfo 用户信息列表
	 * @return true：用户信息获取成功<br>false：用户信息获取失败 */
	public boolean getAllUserInfo(LinkedList<UserInfo> listUserInfo) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"ID\" from \"00002\"");
			UserInfo userInfo = new UserInfo();
			int nUserID = -1;
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());			
			
			while(drResult.next()) {
				nUserID = drResult.getInt("ID");
				userInfo = getUserInfo(nUserID);
				listUserInfo.add(userInfo);
			}

			drResult.close();
			command.close();
			
			strMessage = new StringBuffer("Succceed to get all users' information.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 修改一个用户的信息
	 * @param userInfo 修改后的用户信息
	 * @return true：用户信息修改成功<br>false：用户信息修改失败 */
	public boolean setUserInfo(UserInfo userInfo) {
		try {
			String strCreateTime = String.format("%tF",userInfo.createTime);
			String strLastLoginTime = String.format("%tF",userInfo.lastLoginTime);

			// 修改图像信息
			StringBuffer strQuery = new StringBuffer("update \"00002\" set \"UserName\" = '");
			strQuery.append(userInfo.strUserName);
			strQuery.append("', \"Password\" = '");
			strQuery.append(userInfo.strPassword);
			strQuery.append("', \"UserType\" = ");
			strQuery.append(userInfo.nType);
			strQuery.append(", \"Permission\" = ");
			strQuery.append(userInfo.nPermission);
			strQuery.append(", \"RealName\" = '");
			strQuery.append(userInfo.strRealName);
			strQuery.append("', \"Tel\" = '");
			strQuery.append(userInfo.strPhone);
			strQuery.append("', \"Email\" = '");
			strQuery.append(userInfo.strEmail);
			strQuery.append("', \"Created\" = '");
			strQuery.append(userInfo.strCreated);
			strQuery.append("', \"CreateTime\" = '");
			strQuery.append(strCreateTime);
			strQuery.append("', \"State\" = ");
			strQuery.append(userInfo.nState);
			strQuery.append(", \"LastTime\" = '");
			strQuery.append(strLastLoginTime);
			strQuery.append("', \"LoginIP\" = '");
			strQuery.append(userInfo.strLastLoginIP);
			strQuery.append("', \"Remark\" = '");
			strQuery.append(userInfo.strRemark);
			strQuery.append("' where \"ID\" = ");
			strQuery.append(userInfo.nID);

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to set the information of the user.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 删除一个用户
	 * @param nUserID 用户的编号
	 * @return true：用户删除成功<br>false：用户删除失败 */
	public boolean deleteUser(int nUserID) {
		try {		
			StringBuffer strQuery = new StringBuffer("delete from \"00002\"");
			strQuery.append(" where \"ID\" = ");
			strQuery.append(nUserID);
			
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to delete the user.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 删除所有用户 */
	public boolean deleteAllUser() {
		try {		
			StringBuffer strQuery = new StringBuffer("TRUNCATE \"00002\"");		
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to delete all users.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
	
	/** 判断一个用户是否存在 
	 * @param strUserName 要判断的用户名称
	 * @return 1：用户不存在<br>0：用户存在<br>-1：命令运行错误 */
	public int userIsExist(String strUserName) {
		try {		
			StringBuffer strQuery = new StringBuffer("select * from \"00002\" ");
			strQuery.append("where \"UserName\" = '");
			strQuery.append(strUserName);
			strQuery.append("'");
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nResult = 0;
			
			if (drResult.next()) {
				strMessage = new StringBuffer("The user is exist.");
			} else {
				strMessage = new StringBuffer("The user is not exist.");
				nResult = 1;
			}
			
			drResult.close();
			command.close();
			return nResult;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return -1;
		}
	}
	
	/** 获取用户在数据库中的ID 
	 * @param strUserName 用户名称
	 * @return 获取成功返回用户的ID，获取失败返回-1。*/
	public int getUserID(String strUserName) {
		try {		
			StringBuffer strQuery = new StringBuffer("select * from \"00002\" ");
			strQuery.append("where \"UserName\" = '");
			strQuery.append(strUserName);
			strQuery.append("'");
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nUserID = -1;
			
			if (drResult.next()) {
				nUserID = drResult.getInt("UserName");
				System.out.println("------------"+nUserID);
				strMessage = new StringBuffer("Succeed to get the user's ID.");
			} else {
				System.out.println("------------>"+nUserID);
				strMessage = new StringBuffer("The user is not exist.");
			}
			
			drResult.close();
			command.close();
			return nUserID;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			System.out.println("------------>");
			return -1;
		}
	}
	
	/** 用户登录检查
	 * @param strUserName 用户名
	 * @param strPassword 密码
	 * @param nUserType 用户类型
	 * @return 成功：返回用户的编号<br>失败：返回 -1 */
	public int loginCheck(String strUserName, String strPassword, int nUserType) {
		try {		
			StringBuffer strQuery = new StringBuffer("select * from \"00002\" ");
			strQuery.append("where \"UserName\" = '");
			strQuery.append(strUserName);
			strQuery.append("' and \"Password\" = '");
			strQuery.append(strPassword);
			strQuery.append("' and \"UserType\" = ");
			strQuery.append(nUserType);
			
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			int nUserID = -1;
			
			if (drResult.next()) {
				strMessage = new StringBuffer("Succeed to login.");
				nUserID = drResult.getInt("ID");
			} else {
				strMessage = new StringBuffer("Failed to login.");
			}
			
			drResult.close();
			command.close();
			return nUserID;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return -1;
		}
	}	
}
