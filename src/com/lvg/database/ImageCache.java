package com.lvg.database;

import java.io.*;
import java.sql.*;

import com.imagedb.ImageDatabase;
import com.imagedb.ImageManage;
import com.imagedb.ThumbManage;

public class ImageCache {

	private  Connection conn=null;
	private Statement stat;
	private ResultSet rs;
	private ThumbManage thumbManage;
	private String database;
	
	/**
	 * cache初始化 访问cache表根据页面选择的数据库，访问相应的cache表
	 * @param conn 数据库连接
	 * @param databaseName 数据库名
	 */
	public ImageCache(Connection conn,String databaseName){
		this.conn=conn;
		this.database=databaseName;
		if(!this.isCacheTableExist()){
			this.createCacheTable();
		}
	}
	
	/**
	 * 判断cache表是否存在
	 * @return 存在返回 true
	 * 		         表不存在返回false
	 */
	public boolean isCacheTableExist(){
		
		if (conn!=null) {
			try {
				stat=conn.createStatement();
				String sql="Select * from sqlite_master where type='table' and name='"+database+"'";
				rs = stat.executeQuery(sql);
				return rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 为数据库创建表
	 * @return 创建成功返回true 失败返回false
	 */
	public boolean createCacheTable(){
		
		if (conn!=null) {
			try {
				stat=conn.createStatement();
				String sql="create table "+database+" ( imageID long, imageData Blob,visit int);";
				stat.executeUpdate(sql);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 在表中插入一条数据，访问次数为1	
	 * @param imageID 插入的图片id
	 * @return 创建成功返回 true 失败返回false
	 */
	public boolean insertRow(long imageID){
		
		thumbManage=new ThumbManage(GetConnection.getConn(database));
		byte[] imageData=thumbManage.getThumbData(imageID);
		
		try {
			stat=conn.createStatement();
			String sql="insert into "+database+" (imageID,imageData,visit) values ('"+imageID+"',?,1);";
			
			PreparedStatement command=conn.prepareStatement(sql);
			command.setBytes(1, imageData);
			command.executeUpdate();
			command.close();
			GetConnection.closeAll();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断图片是否在缓存中
	 * @param imageID  图片ID
	 * @return 存在返回true 不存在返回false
	 */
	public boolean isImageExist(long imageID){
		
		try {
			stat=conn.createStatement();
			String sql="select * from "+database+" where imageID="+imageID+";";
			rs = stat.executeQuery(sql);
			return rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 从sqlite中获取图片数 据
	 * @param imageID 图片ID
	 * @return 返回图片byte数组
	 */
	public byte[] getImage(long imageID){
		
		try {
			stat=conn.createStatement();
			String sql="select * from "+database+" where imageID="+imageID+";";
			rs = stat.executeQuery(sql);
			byte[] dataBuffer=null;
			
			if (rs.next()) {
				dataBuffer=rs.getBytes("imageData");
				String update="update "+database+" set visit=visit+1 where imageID ="+imageID+";";
				stat.execute(update);
				stat.close();
				return dataBuffer;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
