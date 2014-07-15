<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.page import="com.lvg.bean.GetImage" />
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>影像数据库系统</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap responsive -->
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<!-- Font awesome - iconic font with IE7 support -->
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/font-awesome-ie7.css" rel="stylesheet">
<!-- Bootbusiness theme -->
<link href="css/boot-business.css" rel="stylesheet">
<!-- Manage css -->
<link href="css/manage.css" rel="stylesheet">
<!-- Jquery DataTable -->
<link href="css/DT-bootstrap.css" rel="stylesheet">



<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/boot-business.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/DT-bootstrap.js"></script>
<script type="text/javascript" src="js/dataTable.js"></script>


<style>
.btn {
	margin-top: 10px;
}
</style>
</head>
<body>
	<!-- 页面顶端导航和搜索框 -->
	<header>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a href="management.jsp" class="brand brand-bootbus">LVY</a> <a
					href="management.jsp" class="brand" id="title" >影像数据库系统管理</a>
			</div>
		</div>
	</div>
	</header>

	<div class="content">
		<div class="container">
			<div class="row">
				<span class="span2"> <jsp:include page="newUser.jsp" />
				</span>
				<div
					style="height: 600px; width: 1px; border-left: 2px #BDB0B0 solid; float: left; margin-left: 20px;"></div>
				<span class="span9">
					<button class="btn btn-large" type="submit" name="submit">新建账号</button>
					<table class="table table-striped table-hover table-bordered datatable" style="margin-top: 10px;" id="Userlist">
						<thead>
							<tr>
								<th >序号</th>
								<th >用户名</th>
								<th >真实姓名</th>
								<th >用户类型</th>
								<th style="width:12%; text-align:center;">电话</th>
								<th style="width:15%; text-align:center;">邮箱</th>
								<th >用户权限</th>
								<th>登陆状态</th>
								<th >上次登陆时间</th>
								<th style="width:12%; text-align:center;" >操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>flo</td>
								<td>fuh</td>
								<td>0</td>
								<td>1507</td>
								<td>6666@qq.</td>
								<td style="text-align: center;">1</td>
								<td>login</td>
								<td>2014/7/15</td>
								<td style="text-align: center;"><a href='#' onclick=''
									>删除</a>&nbsp;<a href='#' onclick=''>修改</a></td>
							</tr>
							
						</tbody>
					</table>
					
				</span>
			</div>
		</div>
	</div>
</body>
</html>