<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="com.imagedb.struct.UserInfo"/>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>影像数据库系统</title> 

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap responsive -->
    <link href="css/bootstrap-responsive.min.css" rel="stylesheet">
    <!-- Font awesome - iconic font with IE7 support --> 
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/font-awesome-ie7.css" rel="stylesheet">
    <!-- Bootbusiness theme -->
    <link href="css/boot-business.css" rel="stylesheet">
    <!-- main theme -->
    <link href="css/main.css" rel="stylesheet">
    <!-- showImage -->
    <link href="css/showImage.css" rel="stylesheet">
    
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/boot-business.js"></script>
</head>
<body>
<!-- 页面顶端导航和搜索框 -->
  <header>
  	  <!-- Start: Navigation wrapper -->
       <jsp:include page="header.jsp"/>
      <!-- End: Navigation wrapper -->   
  </header>
  <%
UserInfo user=new UserInfo();
user=(UserInfo)request.getSession().getAttribute("userInfo");
%>
<div class="content">
	<div class="container">
	    <div class="row">
   		  <div class="page-header">
   		  <h2>账户信息</h2>
   	      </div>
	<form action="modifyUserInfoClient.action" method="post">
	<table style="width:70%;margin-left:15%;">
	<tr> 
		<td>用户ID：</td>
		
		<td><input type="text" name="userInfo.nID" value="<%=user.getUserID()%>"readonly="readonly"/></td>
	</tr>
	<tr> 
		<td>用户名:</td>
		<td colspan="2">
			<input type="text" name="userInfo.strUserName"  id="test1"  value="<%=user.getUserName() %>" readonly="readonly" />
			</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="username" onclick="setvalue('#test1')"  type="button">编辑 </button> 
		</td>
	</tr>
	<tr>
		<td>真实姓名：</td>
		<td colspan="2">
			<input type="text" name="userInfo.strRealName"  id="test2" value="<%=user.getRealName() %>" readonly="readonly">
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit" type="button" onclick="setvalue('#test2')" >编辑 </button>
		</td>
	</tr>
	<tr>
		<td>密码：</td>
		<td colspan="2">
			<input name="userInfo.strPassword" type="password" id="test3" value="<%=user.getPassword()%>" readonly="readonly">
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit" type="button" onclick="setvalue('#test3')" >编辑 </button>
		</td>
	</tr>
	<tr>
		<td>电话：</td>
		<td colspan="2">
			<input name="userInfo.strPhone" type="text" id="test4"  value="<%=user.getTel()%>" readonly="readonly">
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit" type="button" onclick="setvalue('#test4')">编辑 </button>
		</td>
	</tr>
	<tr>
		<td>邮箱:</td>
		<td colspan="2">
		<input type="text" name="userInfo.strEmail" id="test5" value="<%=user.getEmail()%>" readonly="readonly">
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit"  type="button" onclick="setvalue('#test5')">编辑 </button>
		</td>
	</tr>
	
	
	<tr>
			<td>上次登录时间 </td>
			<td><%=user.getLastTime() %></td>
	
	
	</tr>
	
	<tr>
	<td colspan="3" style="text-align:right;">
	
<button class="btn btn-large" type="submit">提交修改</button>


	</td>
	</tr>
	</table>
	</form>
	</div>
  </div>
</div>

<script type="text/javascript">
						
						function setvalue(id){
							var changeVal=$(id);
							 $(changeVal).removeAttr("readonly");																	
						}
</script>
						
</body>
</html>