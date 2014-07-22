package com.lvg.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.imagedb.UserManage;
import com.imagedb.struct.UserInfo;
import com.lvg.database.GetConnection;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 分页Action
 * 
 * @author bobo
 *
 */
public class PaginationAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List rows; // page列表
	public Integer page; //  page列表的当前页
	public Integer total; //  page列表的总数
	public Integer rp; // 一页显示多少个
	public String sortname; // 按sortname排序
	public String sortorder; // desc或者asc
	
	private UserManage userManage;
	
	public String UserListPagination() {
		List<UserInfo> userInfoList = new LinkedList<UserInfo>();
		userManage=new UserManage(GetConnection.getConn("Test1"));
		userManage.getAllUserInfo((LinkedList<UserInfo>) userInfoList);
		rows = new ArrayList();
		total = userInfoList.size();
		int serial = (page - 1) * rp + 1;
		for (int i = (page - 1) * rp; i < page * rp; i++) {
			if (i >= userInfoList.size())
				break;
			Map cellMap = new HashMap();
			cellMap.put("id", userInfoList.get(i).getUserID());
			List<Object> cellList = new ArrayList<Object>();
			cellList.add(Integer.toString(serial));
			ConvertUserInfoToList(userInfoList.get(i), cellList);
			cellMap.put("cell", cellList);
			serial++;
			rows.add(cellMap);
		}
		GetConnection.closeAll();
		return SUCCESS;
	}

	public List<Object> ConvertUserInfoToList(UserInfo userInfo,
			List<Object> cellList) {
		cellList.add(userInfo.getUserID());
		cellList.add(userInfo.getUserName());
		cellList.add(userInfo.getRealName());
		if(userInfo.getUserType()==1){
			cellList.add("用户");
		}else{
			cellList.add("管理员");
		}
		cellList.add(userInfo.getTel());
		cellList.add(userInfo.getEmail());
		cellList.add(userInfo.getPermission());
		
		if(userInfo.getState()==0){
			cellList.add("已登录");
		}else{
			cellList.add("未登录");
		}
		
		cellList.add(userInfo.getLastTime());
		return cellList;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRp() {
		return rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
}
