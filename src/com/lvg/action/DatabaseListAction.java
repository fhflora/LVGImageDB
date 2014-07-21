package com.lvg.action;

import java.util.LinkedList;
import com.opensymphony.xwork2.ActionSupport;
import com.imagedb.DatabaseManage;

@SuppressWarnings("serial")
public class DatabaseListAction  extends ActionSupport{

	private LinkedList<String> listDbName=new LinkedList<String>();


	
	/**
	 * 返回数据库列表
	 * @return 成功返回success
	 * 		        失败返回 input
	 */
	public String showDatabaseListAjax() {
		
		DatabaseManage databaseManage=new DatabaseManage();
		if(databaseManage.connectDB(databaseManage.getConnInfo())){
			databaseManage.listDB(listDbName);
			return SUCCESS;
		}
		return INPUT;
	}
	
	public LinkedList<String> getListDbName() {
		return listDbName;
	}

	public void setListDbName(LinkedList<String> listDbName) {
		this.listDbName = listDbName;
	}
}
