package com.lvg.action;

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
	
	public String CheckLoginAction(){
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
		
		userManage=new UserManage(GetConnection.getConn());
		
		
		if(userManage.loginCheck("fz", "fz123", 1)!=-1){
			user=(UserInfo) userManage.getUserInfo(userManage.loginCheck("fz", "fz123", 1));
			user.setState((byte) 0);
			session.put("user", user);
			session.put("username", user.getUserName());
			session.put("lvg_login", true);
			return SUCCESS;
		}else{
			message="用户名或者密码错误";
			this.addActionMessage(message);
			return INPUT;
		}
		
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
	
	
}
