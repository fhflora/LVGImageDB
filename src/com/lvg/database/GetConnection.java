package com.lvg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetConnection {
	
	protected Connection conn = null;//连接字符串
	protected PreparedStatement ps = null;//预编译并存储 SQL 指令
	protected ResultSet rs = null;//查询结果集
	
	private static final String DRIVER = "org.postgresql.Driver";//加载数据库驱动的字符串
	private static final String URL = "jdbc:postgresql://localhost:5432/ImageDB";//连接数据库的字符串
	private static final String USERNAME = "postgres";//数据库用户名
	private static final String PASSWORD = "superhero";//数据库用户密码
	
	/**
	 * 获得数据库连接
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);//加载数据库驱动
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);//连接数据库
			System.out.print("数据库连接成功");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	/*
	 * 释放数据库资源
	 */
	public void closeAll(){
		try {
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn = null;
			ps = null;
			rs = null;
		}
	}

}