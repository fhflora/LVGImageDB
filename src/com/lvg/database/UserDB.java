package com.lvg.database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.lvg.entity.User;
/**
 * 按条件查询用户
 * @param userName 用户名 userType 用户类型 UerRight 用户权限 userState用户登录状态
 * @return userList 用户列表
 */

public class UserDB  {
	@SuppressWarnings("null")
	public static List<User> searchUser(String userName,String userType,String userRight[],String userState)
			throws SQLException{
		List<User> userList=null;//从数据库中查询的User放入UsrList中
		String sql="";//查询的上去了语句
		Connection conn=GetConnection.getConn();
		Statement stat=conn.createStatement();
		ResultSet rs=stat.executeQuery(sql);
		if(rs!=null){
			whlie(rs.next());{
				User user=new User();
				user.setUserName(rs.getString("userName"));  
		        user.setRealName(rs.getString("realName"));  
		        user.setEmail(rs.getString("email"));  
		        user.setUserType(rs.getByte("user_password"));  //从数据库中获取User的各个属性，并对其初始化
		        userList.add(user);
			}
		}
		
		return userList;
	}
	private static void whlie(boolean next) {
		// TODO Auto-generated method stub
		
	}
	public boolean addUser(String userName,String realName,String password,String userType,String[] userRight,
			String userTel,String userEmail,String remark){
		
		
		return true;//插入成功 ，插入失败return false
	}

}
