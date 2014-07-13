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
}
