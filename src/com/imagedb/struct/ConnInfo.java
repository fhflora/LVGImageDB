package com.imagedb.struct;

/** 数据库连接信息 */
public class ConnInfo {
    public int nPort;        		// 端口号
	public String strHost;        	// 主机IP地址
    public String strUser;        	// 用户名
    public String strDbName;     	// 数据库名称
    public String strPassword;    	// 密码
  
    public ConnInfo() {
    	nPort = 5432;
    	strUser = "postgres";
    	strHost = "192.168.2.103";
    	strDbName = "postgres";
    	strPassword = null;
    }
}
