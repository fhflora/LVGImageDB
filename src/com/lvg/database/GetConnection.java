package com.lvg.database;

import java.sql.*;

public class GetConnection {
	
	protected static Connection conn = null;//连接字符串
	
	private static final String DRIVER = "org.postgresql.Driver";//加载数据库驱动的字符串
	private static String URL;//连接数据库的字符串
	private static final String USERNAME = "postgres";//数据库用户名
	private static final String PASSWORD = "968132";//数据库用户密码
	
	/**
	 * 
	 * @param databaseName 连接的数据库名称
	 * @return
	 */
	public static Connection getConn(String databaseName) {
		Connection conn = null;
		URL="jdbc:postgresql://localhost:5432/"+databaseName;
		try {
			Class.forName(DRIVER);//加载数据库驱动
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);//连接数据库
			System.out.println("数据库连接成功");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

		}
		
		return conn;
	}
	
	/**
	 * 释放数据库资源
	 */
	public static void closeAll(){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn = null;
			System.out.println("释放资源");
		}
	}

}