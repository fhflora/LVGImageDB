<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="com.lvg.bean.GetImage"/>
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

    <div class="content">
    	<div class="container">
     		 <div class="row">
    			<span class="span3">
    				<jsp:include page="newUser.jsp"/>
    			</span>
    			<div style="height:600px;width: 1px;border-left: 2px #BDB0B0 solid;float:left;margin-left:20px;"></div>
    			<span class="span8">
    			
    			</span>
    	</div>	
    </div>
   </div>
</body>
</html>