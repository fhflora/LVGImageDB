package com.lvg.interceptor;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginActionInterceptor extends MethodFilterInterceptor {

	private Map<String, Object> session;

	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		System.out.println("MethodInteceptor开始拦截");

		String result ;
		session = invocation.getInvocationContext().getSession();
		if(session==null)
			System.out.println("session2是空的");
		else
			System.out.println("session2不是空的");
		if(!session.containsKey("lvg_login") || session.get("lvg_login").equals("false")){
			return "input";
		}
		else{
			System.out.println(session.get("lvg_login").toString());
			result = invocation.invoke();		
		}
		result = invocation.invoke();
		System.out.println("MethodInteceptor结束拦截");
		return result;
	}

}
