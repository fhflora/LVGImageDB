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
	private int userID;
	private static final long serialVersionUID = 1L;
	public List rows; // page列表
	public Integer page; //  page列表的当前页
	public Integer total; //  page列表的总数
	public Integer rp; // 一页显示多少个
	public String sortname; // 按sortname排序
	public String sortorder; // desc或者asc
	public String qtype;
	public String query;
	private UserManage userManage;
	
	
	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	
	public String UserListPagination() {
		List<UserInfo> userInfoList = new LinkedList<UserInfo>();
		UserInfo userInfo=new UserInfo();
		userManage=new UserManage(GetConnection.getConn("Test1"));
		
		System.out.println("qtype:"+qtype+" value:"+query);
		if(query.isEmpty() || query.trim().equals(""))
			userManage.getAllUserInfo((LinkedList<UserInfo>) userInfoList);
		else if(qtype.equals("userID")){
			userInfo=userManage.getUserInfo(Integer.parseInt(query));
			if(userInfo==null){
				userInfoList.clear();
			}
			else{
				userInfoList.add(userInfo);
			}
		}else if(qtype.equals("userName")){
			userInfo=userManage.getUserInfoByUserName(query.toString());
			if(userInfo==null){
				userInfoList.clear();
			}
			else{
				userInfoList.add(userInfo);
			}
		}else if(qtype.equals("realName")){
			userInfoList=userManage.getUserInfoByRealName(query.toString());	
			
			
		}else if(qtype.equals("state")){
			//已登录为0   未登录为1
			byte state=1;
			if(query.equals("已登录")){
				state=0;
			}
			userInfoList=userManage.getUserInfoByState(state);	
		}else if(qtype.equals("userType")){
			//用户为1 管理员为0
			byte userType=1;
			if(query.equals("管理员")){
				userType=0;
			}
			userInfoList=userManage.getUserInfoByUserType(userType);	
		}
		
		rows = new ArrayList();
		total = userInfoList.size();
		System.out.println("total:"+total);
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
		cellList.add(this.showPermission(userInfo.getPermission()));
		
		if(userInfo.getState()==0){
			cellList.add("已登录");
		}else{
			cellList.add("未登录");
		}
		
		cellList.add(userInfo.getLastTime());
		cellList.add(userInfo.getCreatedBy());
		cellList.add(userInfo.getRemark());
		return cellList;
	}
	public String showPermission(int permission){
		String strPermission="";
		
		if((permission&1)!=0){
			strPermission+="导入";
		}
		if((permission&2)!=0){
			strPermission+=" 删除 ";
		}
		if((permission&4)!=0){
			strPermission+=" 编辑 ";
		}
		if((permission&8)!=0){
			strPermission+=" 导出";
		}
		return strPermission;
		
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
