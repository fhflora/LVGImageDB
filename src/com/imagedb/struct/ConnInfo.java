package com.imagedb.struct;

/** ��ݿ�������Ϣ */
public class ConnInfo {
    public int nPort;        		// �˿ں�
	public String strHost;        	// ����IP��ַ
    public String strUser;        	// �û���
    public String strDbName;     	// ��ݿ����
    public String strPassword;    	// ����
  
    public ConnInfo() {
    	nPort = 5432;
    	strUser = "postgres";
    	strHost = "localhost";
    	strDbName = "postgres";
    	strPassword = "968132";
    }
}
