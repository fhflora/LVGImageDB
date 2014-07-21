package com.imagedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.postgresql.Driver;

import com.imagedb.struct.ConnInfo;


/** 数据库管理类
 * @author		杨冲
 * @version		1.00 25 June 2014 */
public class DatabaseManage extends ImageDatabase {  
    private ConnInfo connInfo;		// 数据库连接信息
       
    public DatabaseManage() {
    	connInfo = new ConnInfo();
        
        try {
        	Driver driver = new org.postgresql.Driver();
        	DriverManager.registerDriver(driver);			// 注册PostgreSQL数据库驱动
		} catch (Exception e) {
        	strMessage = new StringBuffer(e.getMessage());
			e.printStackTrace();
		}
    }
    
    /** 连接数据库 
     * @param connInfoTemp 数据库连接信息
     * @return true：数据库连接成功<br>false：数据库连接失败*/
    public boolean connectDB(ConnInfo connInfoTemp) {
        try {
        	String strURL = "jdbc:postgresql://" + connInfoTemp.strHost + ":"
        					+ connInfoTemp.nPort + "/" + connInfoTemp.strDbName;
            hConnection = DriverManager.getConnection(strURL, connInfoTemp.strUser,
            										  connInfoTemp.strPassword);
            connInfo = connInfoTemp;
            
            strMessage = new StringBuffer("Succeed to establish a connection.");           
            return true;
        } catch (Exception  ex) {
            strMessage = new StringBuffer(ex.getMessage());
            return false;
        }
    }
    
    /** 获取数据库连接信息 */
    public ConnInfo getConnInfo() {
        return connInfo;
    }
    
    /** 获取数据库连接句柄 */
    public Connection getConnHandle() {
        return hConnection;     
    }
    
	/** 断开数据库连接 */
	public boolean closeConnect() {
		try {
			hConnection.close();
			strMessage = new StringBuffer("Succeed to close the connection.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
    
    /** 创建一个数据库 
     * @param strDbName 要创建的数据库名称
     * @return true：数据库创建成功<br>false：数据库创建失败 */
	public boolean createDB(String strDbName) {
		try {
			StringBuffer strQuery = new StringBuffer("CREATE DATABASE \"");
			strQuery.append(strDbName);
			strQuery.append("\"");

			// 执行SQL语句
			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			String strDBNameOld = connInfo.strDbName;
			hConnection.close();

			// 连接新数据库
			connInfo.strDbName = strDbName;
			connectDB(connInfo);

			boolean bIsInit = initializeDB();		// 初始化新数据库
			
			hConnection.close();
			connInfo.strDbName = strDBNameOld;
			connectDB(connInfo);

			// 判断新数据库初始化是否成功
			if (bIsInit) {
				strMessage = new StringBuffer("Succeed to create the database");
				return true;
			} else {
				deleteDB(strDbName);		// 初始化失败时须删掉新建的数据库				
				return false;
			}	
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
    
    /** 初始化一个数据库 */
    private boolean initializeDB() {
        try {
        	hConnection.setAutoCommit(false);		// 关闭数据库的自动提交任务
            Statement command = hConnection.createStatement();

            // 该表用来存储表的ID和名称
            StringBuffer strQuery = new StringBuffer("create table \"00000\" ( \"ID\" ");
            strQuery.append("integer PRIMARY KEY, \"Name\" varchar(256) )");
            command.executeUpdate(strQuery.toString());

            // 该表用来存储表的序列号（新添加的表或者图像的ID）
            strQuery = new StringBuffer("create table \"00001\" ( \"ID\" ");
            strQuery.append("integer PRIMARY KEY, \"SerialNum\" integer )");
            command.executeUpdate(strQuery.toString());
           
            // 该表用来存储客户端用户信息
            strQuery = new StringBuffer("create table \"00002\" ( \"ID\" serial PRIMARY KEY, ");
            strQuery.append("\"UserName\" varchar(16), \"Password\" varchar(16), \"UserType\" smallint, ");
            strQuery.append("\"Permission\" smallint, \"RealName\" varchar(32), \"Tel\" varchar(16), ");
            strQuery.append("\"Email\" varchar(64), \"Created\" varchar(32), \"CreateTime\" date, ");
            strQuery.append("\"State\" smallint, \"LastTime\" date, \"LoginIP\" varchar(32), \"Remark\" text )");
            command.executeUpdate(strQuery.toString());
            
            // 普通图像总表
            strQuery = new StringBuffer("create table \"10001\" ( \"ID\" integer PRIMARY KEY,");
            strQuery.append(" \"TableName\" varchar(256), \"Remark\" text )");
            command.executeUpdate(strQuery.toString());

            // 超大图像总表
            strQuery = new StringBuffer("create table \"10002\" ( \"ID\" integer PRIMARY KEY,");
            strQuery.append(" \"TableName\" varchar(256), \"Remark\" text )");
            command.executeUpdate(strQuery.toString());
            
            // 全景图像总表
            strQuery = new StringBuffer("create table \"10003\" ( \"ID\" integer PRIMARY KEY,");
            strQuery.append(" \"TableName\" varchar(256), \"Remark\" text )");
            command.executeUpdate(strQuery.toString());
            
            // 航片图像总表
            strQuery = new StringBuffer("create table \"10004\" ( \"ID\" integer PRIMARY KEY,");
            strQuery.append(" \"TableName\" varchar(256), \"Remark\" text )");
            command.executeUpdate(strQuery.toString());       

            // 表的初始化工作
            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (0,'数据库表ID与Name对照表')");
            command.executeUpdate(strQuery.toString());

            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (1,'序列号记录表')");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (2,'客户端用户信息表')");
            command.executeUpdate(strQuery.toString());

            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (10001,'普通图像')");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (10002,'超大图像')");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (10003,'全景图像')");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00000\" (\"ID\", \"Name\") values (10004,'航片图像')");
            command.executeUpdate(strQuery.toString());

            strQuery = new StringBuffer("insert into \"00001\" (\"ID\", \"SerialNum\") values (10001,0)");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00001\" (\"ID\", \"SerialNum\") values (10002,0)");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00001\" (\"ID\", \"SerialNum\") values (10003,0)");
            command.executeUpdate(strQuery.toString());
            
            strQuery = new StringBuffer("insert into \"00001\" (\"ID\", \"SerialNum\") values (10004,0)");
            command.executeUpdate(strQuery.toString());
            
            // 提交上面的命令
            hConnection.commit();
            hConnection.setAutoCommit(true);
            command.close();

            strMessage = new StringBuffer("Succeed to initialize the database.");
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
    
	/** 例举出所有数据库的名称 
	 * @param listDbName 数据库名称列表
	 * @return true：数据库名称获取成功<br>false：数据库名称获取失败 */
	public boolean listDB(LinkedList<String> listDbName) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"datname\" from pg_database");
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			while (drResult.next()) {
				listDbName.add(drResult.getString("datname"));
			}

			drResult.close();
			command.close();

			strMessage = new StringBuffer("Succeed to list databases' names.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
    
	/** 删除一个数据库 
     * @param strDbName 要删除的数据库名称
     * @return true：数据库删除成功<br>false：数据库删除失败 */
	public boolean deleteDB(String strDbName) {
		try {
			StringBuffer strQuery = new StringBuffer("DROP DATABASE \"");
			strQuery.append(strDbName);
			strQuery.append("\"");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to delete the database.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
    
	/** 添加一个数据库用户 
	 * @param strUserName 数据库端用户名
	 * @param strPassword 用户密码
	 * @param nPermission 用户权限
	 * @return true：数据库用户创建成功<br>false：数据库用户创建失败 */
	public boolean addUser(String strUserName, String strPassword, int nPermission) {
		try {
			StringBuffer strQuery = new StringBuffer("CREATE USER \"");
			strQuery.append(strUserName);
			strQuery.append("\" WITH PASSWORD '");
			strQuery.append(strPassword);
			strQuery.append("' NOCREATEDB NOCREATEUSER");

			Statement command = hConnection.createStatement();
			command.executeUpdate(strQuery.toString());
			command.close();

			strMessage = new StringBuffer("Succeed to add the user.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
    
    /** 例举出所有数据库用户的名称 
     * @param listUserName 数据库用户列表
     * @return true：数据库用户名称获取成功<br>false：数据库用户名称获取失败 */
	public boolean listUser(LinkedList<String> listUserName) {
		try {
			StringBuffer strQuery = new StringBuffer("select \"rolname\" from pg_authid");
			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());

			while (drResult.next()) {
				listUserName.add(drResult.getString("rolname"));
			}

			drResult.close();
			command.close();

			strMessage = new StringBuffer("Succeed to list users' names.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

    /** 删除一个数据库用户 
     * @param strUserName 要删除的数据库用户名称
     * @return true：数据库用户删除成功<br>false：数据库用户删除失败 */
	public boolean deleteUser(String strUserName) {
		try {
			StringBuffer strQuery = new StringBuffer("DROP USER \"");
			strQuery.append(strUserName);
			strQuery.append("\"");

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
}
