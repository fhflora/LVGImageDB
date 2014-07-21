package com.lvg.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.imagedb.struct.UserInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.imagedb.UserManage;
import com.lvg.database.*;

public class UserAction extends ActionSupport implements SessionAware{
	public UserInfo user;
	private Map<String,Object> session;
	private String certCode;
	private UserManage userManage;
	private List<UserInfo> userInfoList;
	private String delUserIDs;
	private int userID;
	private String message;
	
	/**
	 * 用户登录action
	 * @return
	 */
	public String userLogin(){
		String  message="验证码错误，请重新输入！";
		if(!certCode.equals(ServletActionContext.getRequest().getSession().getAttribute("rand")))
		{
			this.addActionMessage(message);
			return INPUT;
		}
		if(user.getUserName().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		if(user.getPassword().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		//添加访问数据库语句  验证用户
		
		userManage=new UserManage(GetConnection.getConn("Test1"));
		
		if(userManage.loginCheck(user.getUserName(), user.getPassword(), user.getUserType())!=-1){
			user=(UserInfo) userManage.getUserInfo(userManage.loginCheck(user.getUserName(), user.getPassword(), user.getUserType()));

			user.setState((byte) 0);
			userManage.setUserInfo(user);
			session.put("user", user);
			session.put("username", user.getUserName());
			session.put("lvg_login", true);
			session.put("permission",user.getPermission());
			GetConnection.closeAll();
			return SUCCESS;
		}else{
			message="用户名或者密码错误";
			this.addActionMessage(message);
			GetConnection.closeAll();
			return INPUT;
		}
	}
	
	/**
	 * 管理员登录action
	 * @return
	 */
	public String managerLogin(){
		String  message="验证码错误，请重新输入！";
		if(!certCode.equals(ServletActionContext.getRequest().getSession().getAttribute("rand")))
		{
			this.addActionMessage(message);
			return INPUT;
		}
		if(user.getUserName().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		if(user.getPassword().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		//添加访问数据库语句  验证用户
		
		userManage=new UserManage(GetConnection.getConn("Test1"));
		
		if(userManage.loginCheck(user.getUserName(), user.getPassword(), user.getUserType())!=-1){
			user=(UserInfo) userManage.getUserInfo(userManage.loginCheck(user.getUserName(), user.getPassword(), user.getUserType()));

			user.setState((byte) 0);
			userManage.setUserInfo(user);
			session.put("user", user);
			session.put("username", user.getUserName());
			session.put("lvg_login", true);
			session.put("permission",user.getPermission());
			GetConnection.closeAll();
			return SUCCESS;
		}else{
			message="用户名或者密码错误";
			this.addActionMessage(message);
			GetConnection.closeAll();
			return INPUT;
		}
	}
	/**
	 * 退出函数 修改用户状态
	 * @return
	 */
	public String logoutAction(){
		
		userManage=new UserManage(GetConnection.getConn("Test1"));
		user=(UserInfo)session.get("user");
		user.setState((byte) 1);
		if(userManage.setUserInfo(user)){
			GetConnection.closeAll();
			return SUCCESS;
		}else{
			GetConnection.closeAll();
			session.clear();
			return INPUT;
		}
	}
	
	/**
	 * 获取用户信息Action
	 * @return
	 */
	public String userInfoList(){
		
		userManage=new UserManage(GetConnection.getConn("Test1"));
		System.out.println("连接用户数据库");
		userManage.getAllUserInfo((LinkedList<UserInfo>) userInfoList);
		System.out.println("===="+userInfoList.size());
		GetConnection.closeAll();
		return SUCCESS;
	}
	
	/**
	 * 新建一个用户Action
	 * @return
	 */
	public String insertUserInfo(){
		Date date = new Date(new java.util.Date().getTime());
		user.setCreateTime(date);
		userManage=new UserManage(GetConnection.getConn("Test1"));
		userManage.addUse(user);
		userID=userManage.getUserID(user.getUserName());
		GetConnection.closeAll();
		return SUCCESS;
	}
	
	/**
	 * 修改一个用户Action
	 * @return
	 */
	public String modifyUserInfo(){
		System.out.println("Modify------"+1);
		userManage=new UserManage(GetConnection.getConn("Test1"));
		userManage.setUserInfo(user);
		System.out.println("Modify------"+2);
		GetConnection.closeAll();
		return SUCCESS;
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String deleteUserInfo(){
		userManage=new UserManage(GetConnection.getConn("Test1"));
		userManage.deleteUser(userID);
		GetConnection.closeAll();
		return SUCCESS;
	}
	
	/**
	 * 删除多个用户Action
	 * @return
	 */
	public String deleteUsersInfo(){
		userManage=new UserManage(GetConnection.getConn("Test1"));
		String[] ids = delUserIDs.split("\\s{1,}");
		for(String id:ids){
			Boolean delete= userManage.deleteUser(Integer.parseInt(id));
			if(delete==false){
				message+="用户ID："+id+"删除失败\r\n";
			}else if(delete==true){
				message+="用户ID："+id+"删除成功\r\n";
			}
		}
		GetConnection.closeAll();
		return SUCCESS;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	
	public UserInfo getUserInfo() {
		return user;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.user = userInfo;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}
	
	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}
	
	public String getDelUserIDs() {
		return delUserIDs;
	}
	
	public void setDelUserIDs(String delUserIDs) {
		this.delUserIDs = delUserIDs;
	}
}
