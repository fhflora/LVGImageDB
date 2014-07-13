<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="com.lvg.entity.User"/>
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
    <!-- Manage css -->
    <link href="css/manage.css" rel="stylesheet">
    
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/boot-business.js"></script>
    <style type="text/css">
    td{
     width:25%;
     height:50px;
    }
    input{
    width:80%;
    border: 1px solid #cccccc;
    }
    .row table{
    text-align:center;
    }
    </style>
</head>
<body>
<!-- 页面顶端导航和搜索框 -->
  <header>
       <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
          <div class="container">
            <a href="management.jsp" class="brand brand-bootbus">LVY</a>
  	    	<a href="management.jsp" class="brand" id="title">影像数据库系统管理</a>
            </div>
          </div>
      </div>  
</header>
<%
User user=new User();
%>
<div class="content">
	<div class="container">
	    <div class="row">
   		  <div class="page-header">
   		  <h2>账户信息</h2>
   	      </div>
	<form>
	<table style="width:70%;margin-left:15%;">
	<tr> 
		<td>用户ID：</td>
		<td><%=user.getUserID() %></td>
	</tr>
	<tr> 
		<td>用户名:</td>
		<td colspan="2">
			<input type="text" name="userName"  id="test"  value="<%=user.getUserName() %>" />
			</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button> 
		</td>
	</tr>
	<tr>
		<td>真实姓名：</td>
		<td colspan="2">
			<input type="text" name="realName"  id="test1" value="<%=user.getRealName() %>">
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button>
		</td>
	</tr>
	<tr>
		<td>密码：</td>
		<td colspan="2">
			<input name="password" type="password"  value="<%=user.getPassword()%>">
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button>
		</td>
	</tr>
	<tr>
		<td>电话：</td>
		<td colspan="2">
			<input name="telNo" type="text"  values="<%=user.getTel()%>" disabled>
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button>
		</td>
	</tr>
	<tr>
		<td>邮箱:</td>
		<td colspan="2">
		<input type="text" name="email"  values="<%=user.getEmail()%>" disabled>
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button>
		</td>
	</tr>
	<tr>
		<td>使用权限</td>
		<td colspan="2"> 浏览影像    增删影像</td>
	 	<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button>
		</td>
	 </tr>
	<tr>
			<td>创建人: </td>
			<td><%=user.getCreatedBy() %></td>
	
	 		<td> 创建时间：</td>
		    <td><%=user.getCreateTime() %></td>
	
	</tr>
	<tr>
		 <td>登录状态：</td>
		 <td><%=user.getState() %> </td>
		 <td>上次登录IP：</td>
		 <td><%=user.getLoginIP() %></td>
	</tr>
	<tr>
		<td>备注：</td>
		<td colspan="2">
		<textarea name="remark" style="width:80%;">
		<%=user.getRemark() %>
		</textarea>
		</td>
		<td>
			<button class="btn btn-small" value="编辑"  id="submit">编辑 </button>
		</td>
	</tr>
	<tr>
	<td colspan="3" style="text-align:right;">
	<input type="submit" value="提交修改">
	</td>
	</tr>
	</table>
	</form>
	</div>
  </div>
</div>
<script type="text/javascript"> 
var test = document.getElementById("test"); 
var mysubmit = document.getElementById("submit"); 
save.onclick = function() 
{ 
 test.readOnly = true; 
 test.className = "t"; 
  
} 
mysubmit.onclick = function() 
{ 
 test.readOnly =false; 
 test.className = "s"; 
 
} 
</script> 
</body>
</html>