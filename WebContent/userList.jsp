<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.lvg.entity.User" 
            import="java.util.*"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
  List<User> userList=(List<User>)session.getAttribute("userList");
  request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum"); //获得当前页数的字符串
	
	int pageNum = 1; 
	
	//把当前页数的字符串转化为数字，如果转化失败，则设置当前页数为 1 ，即首页
	try{
		pageNum = Integer.parseInt(strPageNum);
	} catch (Exception e){
		pageNum = 1; 
	}
	int currentlyPage =pageNum; //设置当前页数
	int countRow=userList.size();//获得记录的总行数
	 final int PAGEROW = 10;//设置单页显示记录数
	 int countPage=0;  //总页数计数变量
	 if(countRow % PAGEROW==0){
		 countPage=countRow % PAGEROW;
	 }
	 else{
		 countPage= (countRow % PAGEROW) + 1;
	 }
	//如果当前页数小于 0 或大于总页数，则把当前页重新设为 1  ，即首页
	 if (pageNum<=0 || pageNum>countPage){
			pageNum = 1; 
		}
	List<User> subUserList=userList.subList(pageNum*PAGEROW ,pageNum*PAGEROW-9 );//从userList中获取10条记录，存入到subUserList中
	if(subUserList!=null && subUserList.size()>0) { 
    	%>
    		<table border="1" align="center">
    		<tr>
    		<th align="center">用户名</th>
    		<th align="center">真实姓名</th>
    		<th align="center">用户类型</th>
    		<th align="center">电话</th>
    		<th align="center">邮箱</th>
    		<th align="center">用户权限</th>
    		<th align="center">登录状态</th>
    		<th align="center">上次登录时间</th>
    		<th align="center">编辑</th>
    		<th align="center">删除</th>
    		</tr>
    	<%
    			for (int i=0; i<subUserList.size(); i++) {
    				User user=subUserList.get(i);
    	%>
    		<tr>
    			<td width="200"><%=user.getUserName() %></td>
    			<td width="100"><%=user.getRealName() %></td>
    			<td width="50"><%=user.getUserType() %></td>
    			<td width="50"><%=user.getTel() %></td>
    			<td width="200"><%=user.getEmail()%></td>
    			<td width="100"><%=user.getPermission() %>
    			<td width="100"><%=user.getState() %></td>
    			<td width="200"><%=user.getLastTime() %></td>
    			<td width="100"><a href="userInfo.jsp?userID=<%=user.getUserID() %>>">编辑 </a></td>
    			<td width="100"><a href="delUser.jsp?userID=<%=user.getUserID() %>>">删除 </a></td>
    		</tr>
    	<%
    			}
    	%>
    		<tr>
    		<td colspan="6" align="center">
    	<% 
    		if(currentlyPage==1){
    	%>
    		共<%=countPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
    		当前第<%=currentlyPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
    		首页&nbsp;&nbsp;&nbsp;&nbsp;
    		上一页&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=<%=currentlyPage+1 %>">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=<%=countPage %>">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    	<%
    		}else if(currentlyPage==countPage){
    	%>
    		共<%=countPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
    		当前第<%=currentlyPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=1">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=<%=currentlyPage-1 %>">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    		下一页&nbsp;&nbsp;&nbsp;&nbsp;
    		尾页&nbsp;&nbsp;&nbsp;&nbsp;
    	<%
    		}else{
    	%>
    		共<%=countPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
    		当前第<%=currentlyPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=1">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=<%=currentlyPage-1 %>">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=<%=currentlyPage+1 %>">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    		<a href="userList.jsp?pageNum=<%=countPage %>">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    	<%
    		}
    	%>
    		</td>
    		</tr>
    		</table>
    	<%
    		}else{
    	%>
    		<h2 align="center">对不起，没有相应的信息……</h2>
    	<%
    		}
    	%>
</body>
</html>