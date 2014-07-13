package com.lvg.entity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

/**
 * Servlet implementation class LoginModule
 */
@WebServlet("/LoginModule")
public class LoginModule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginModule() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//从index网页中的form表单中获取参数
		String account =request.getParameter("account");//获取账号
		String password =request.getParameter("password");//获取密码
		String inputCertCode=request.getParameter("certCode");//获取用户输入的验证码
		HttpSession session = request.getSession();
		String createdCertCode=(String) session.getAttribute("certCode");//获取系统产生的验证码
		if(inputCertCode==createdCertCode)	{//判断验证码是否正确，如果正确则验证用户名密码
			
			System.out.println(account+password);
		}
		else{                         //如果验证码错误，弹出提示重新输入
		System.out.println("您输入的确认码和系统产生的不一致，请重新输入。");

		}
		
		
		
		
	}

}
