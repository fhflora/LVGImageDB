package com.imagedb.struct;

import java.util.Date;


/** 用户信息 */
public class UserInfo {
	public int nID;						// 用户的编号	
	public byte nType;					// 用户类型
	public byte nState;					// 登录状态
	public byte nPermission;			// 用户权限
	
	public String strPhone;				// 电话
	public String strEmail;				// 邮箱
	public String strRemark;			// 备注信息
	public String strCreated;			// 创建人
	public String strUserName;			// 用户名
	public String strPassword;			// 密码
	public String strRealName;			// 用户真实姓名
	public String strLastLoginIP;		// 上次登录IP
	
	public Date createTime;				// 创建时间
	public Date lastLoginTime;			// 上次登陆时间
	
	public UserInfo() {
		nID = -1;
		nType = -1;		
		nState = -1;
		nPermission = -1;
		
		strPhone = null;
		strEmail = null;
		strRemark = null;
		strCreated = null;
		strUserName = null;
		strPassword = null;
		strRealName = null;
		strLastLoginIP = null;
		
		createTime = null;
		lastLoginTime = null;
	}
	public UserInfo(int userID){
		this.nID=userID;
	}
	public int getUserID() {
		return nID;
	}
	public void setUserID(int userID) {
		this.nID = userID;
	}
	public String getUserName() {
		return strUserName;
	}
	public void setUserName(String userName) {
		this.strUserName = userName;
	}
	public String getPassword() {
		return strPassword;
	}
	public void setPassword(String password) {
		this.strPassword = password;
	}
	public byte getUserType() {
		return nType;
	}
	public void setUserType(byte userType) {
		this.nType = userType;
	}
	public byte getPermission() {
		return this.nPermission;
	}
	public void setPermission(byte permissoin) {
		this.nPermission = permissoin;
	}
	public String getRealName() {
		return strRealName;
	}
	public void setRealName(String realName) {
		this.strRealName = realName;
	}
	public String getTel() {
		return strPhone;
	}
	public void setTel(String tel) {
		this.strPhone = tel;
	}
	public String getEmail() {
		return strEmail;
	}
	public void setEmail(String email) {
		this.strEmail = email;
	}
	public String getCreatedBy() {
		return strCreated;
	}
	public void setCreatedBy(String createdBy) {
		this.strCreated = createdBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 登录为 0  未登录为1
	 * @return
	 */
	public byte getState() {
		return nState;
	}
	public void setState(byte state) {
		this.nState = state;
	}
	public Date getLastTime() {
		return lastLoginTime;
	}
	public void setLastTime(Date strDate) {
		this.lastLoginTime = strDate;
	}
	public String getLoginIP() {
		return strLastLoginIP;
	}
	public void setLoginIP(String loginIP) {
		this.strLastLoginIP = loginIP;
	}
	public String getRemark() {
		return strRemark;
	}
	public void setRemark(String remark) {
		this.strRemark = remark;
	}
	

	
}
