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
	public UserInfo userInfo;
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
		if(userInfo.getUserName().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		if(userInfo.getPassword().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		//添加访问数据库语句  验证用户
		
		userManage=new UserManage(GetConnection.getConn("Test1"));
		
		if(userManage.loginCheck(userInfo.getUserName(), userInfo.getPassword(), userInfo.getUserType())!=-1){
			userInfo=(UserInfo) userManage.getUserInfo(userManage.loginCheck(userInfo.getUserName(), userInfo.getPassword(), userInfo.getUserType()));

			userInfo.setState((byte) 0);
			userManage.setUserInfo(userInfo);
			session.put("userInfo", userInfo);
			session.put("username", userInfo.getUserName());
			session.put("lvg_login", true);
			session.put("permission",userInfo.getPermission());
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
		userInfoList=new LinkedList<UserInfo>();
		userManage=new UserManage(GetConnection.getConn("Test1"));
		userManage.getAllUserInfo((LinkedList<UserInfo>)userInfoList);
		String  message="验证码错误，请重新输入！";
		if(!certCode.equals(ServletActionContext.getRequest().getSession().getAttribute("rand")))
		{
			this.addActionMessage(message);
			return INPUT;
		}
		if(userInfo.getUserName().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		if(userInfo.getPassword().isEmpty()){
			this.addFieldError("userName", "用户名不能为空");
			return INPUT;
		}
		//添加访问数据库语句  验证用户
		
		userManage=new UserManage(GetConnection.getConn("Test1"));
		
		if(userManage.loginCheck(userInfo.getUserName(), userInfo.getPassword(), userInfo.getUserType())!=-1){
			userInfo=(UserInfo) userManage.getUserInfo(userManage.loginCheck(userInfo.getUserName(), userInfo.getPassword(), userInfo.getUserType()));

			userInfo.setState((byte) 0);
			userManage.setUserInfo(userInfo);
			session.put("userInfo", userInfo);
			session.put("username", userInfo.getUserName());
			session.put("lvg_login", true);
			session.put("permission",userInfo.getPermission());
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
		userInfo=(UserInfo)session.get("userInfo");
		userInfo.setState((byte) 1);
		if(userManage.setUserInfo(userInfo)){
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
		Date sqlDate=new Date(new java.util.Date().getTime());  
		userInfo.setCreateTime(sqlDate);
		UserInfo manager=(UserInfo)session.get("userInfo");
		userInfo.setCreatedBy(manager.getUserName());
		userInfo.setState((byte)1);
		userInfo.setLastTime(sqlDate);
		userManage=new UserManage(GetConnection.getConn("Test1"));
		userManage.addUse(userInfo);
		System.out.println(userManage.getExceMessage());
		GetConnection.closeAll();
		this.setMessage("success");
		return SUCCESS;
	}
	
	/**
	 * 修改一个用户Action
	 * @return
	 */
	public String modifyUserInfo(){

		userManage=new UserManage(GetConnection.getConn("Test1"));
		UserInfo tempUser=userManage.getUserInfo(userInfo.nID);
		
		tempUser.setUserName(userInfo.getUserName());
		tempUser.setRealName(userInfo.getRealName());
		tempUser.setPassword(userInfo.getPassword());
		tempUser.setUserType(userInfo.getUserType());
		tempUser.setEmail(userInfo.getEmail());
		tempUser.setTel(userInfo.getTel());
		tempUser.setRemark(userInfo.getRemark());
		tempUser.setPermission(userInfo.getPermission());
		userManage.setUserInfo(tempUser);
		System.out.println(userManage.getExceMessage());
		GetConnection.closeAll();
		this.setMessage("success");
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
		this.setMessage("用户删除成功");
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
		return userInfo;
	}
	public void setUser(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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
