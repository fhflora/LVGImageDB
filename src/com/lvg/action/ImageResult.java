package com.lvg.action;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class ImageResult implements Result{

	@Override
	public void execute(ActionInvocation ai) throws Exception {
		// TODO Auto-generated method stub
		ImageListAction action=(ImageListAction)ai.getAction();
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setHeader("Cash", "no cash");
		response.setContentType(action.getContentType());
		response.getOutputStream().write(action.showImageData());
		response.getOutputStream().flush();
	}

}
