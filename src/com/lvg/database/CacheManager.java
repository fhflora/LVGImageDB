package com.lvg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CacheManager {

	protected static Connection conn = null;
	/**
	 * 连接Sqlite缓存
	 * @return
	 */
	public static Connection getSqliteConn(){
		try {
			Class.forName("org.sqlite.JDBC");
			conn=DriverManager.getConnection("jdbc:sqlite://c:/lvgimagedb.db");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}
	}
}
