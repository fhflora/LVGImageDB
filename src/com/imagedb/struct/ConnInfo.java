package com.imagedb.struct;

/** 锟斤拷菘锟斤拷锟斤拷锟斤拷锟较�*/
public class ConnInfo {
	public int nPort; // 锟剿口猴拷
	public String strHost; // 锟斤拷锟斤拷IP锟斤拷址
	public String strUser; // 锟矫伙拷锟斤拷
	public String strDbName; // 锟斤拷菘锟斤拷锟斤拷
	public String strPassword; // 锟斤拷锟斤拷

	public ConnInfo() {
		nPort = 5432;
		strUser = "postgres";
		// strHost = "localhost";
		strHost = "localhost";
		strDbName = "postgres";
		strPassword = "968132";
	}
}
