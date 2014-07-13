package com.lvg.entity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
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
		request.setCharacterEncoding("UTF-8") ; 
		String userName=request.getParameter("userName");
		String realName=request.getParameter("realName");
		String password=request.getParameter("password");
		String rePassword=request.getParameter("rePassword");    
		String userType=request.getParameter("userType");
	    String userRight[]=request.getParameterValues("userRight");//这时接收到的就是一个数组了
	   if(userRight==null){
	    	System.out.print("传参失败");
	    }else{
	    for(int i=0;i<userRight.length;i++){
	  System.out.println(userRight[i]);
	   }
	    }
		//String browse =request.getParameter("browse");
		//String edit =request.getParameter("edit");
		//String export =request.getParameter("export");
		//String addanddel =request.getParameter("addanddel");
		//System.out.print(browse);
		//System.out.print(edit);
		//System.out.print(export);
		//System.out.print(addanddel);
		//String[] userRight=request.getParameterValues("userRight");
	}

}
