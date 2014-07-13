package com.lvg.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.lvg.entity.User;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements SessionAware{
	public User user;
	private Map<String,Object> session;
	private String certCode;
	
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
		if(user.getUserName().equals("admin")&&user.getPassword().equals("admin")){
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
	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	
	
}
