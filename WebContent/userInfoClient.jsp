<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.page import="com.imagedb.struct.UserInfo" />
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
td {
	width: 25%;
	height: 50px;
}

input {
	width: 80%;
	border: 1px solid #cccccc;
}

.row table {
	text-align: center;
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
					href="management.jsp" class="brand" id="title">影像数据库系统管理</a>
			</div>
		</div>
	</div>
	</header>
	<%
		UserInfo user = new UserInfo();
	%>
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="page-header">
					<h2>我的账户</h2>
				</div>
				<form>
					<table style="width: 70%; margin-left: 15%;">
						<tr>
							<td>用户ID：</td>
							<td><%=user.getUserID()%></td>
						</tr>
						<tr>
							<td>用户名:</td>
							<td colspan="2"><input type="text" name="userName"
								id="test1" value="<%=user.getUserName()%>" disabled /></td>
							<td>
								<button class="btn btn-small" value="编辑" id="username"
									onclick="setvalue('#test1')" type="button">编辑</button>
							</td>
						</tr>
						<tr>
							<td>真实姓名：</td>
							<td colspan="2"><input type="text" name="realName"
								id="test2" value="<%=user.getRealName()%>" disabled></td>
							<td>
								<button class="btn btn-small" value="编辑" id="submit"
									type="button" onclick="setvalue('#test2')">编辑</button>
							</td>
						</tr>
						<tr>
							<td>密码：</td>
							<td colspan="2"><input name="password" type="password"
								id="test3" value="<%=user.getPassword()%>" disabled></td>
							<td>
								<button class="btn btn-small" value="编辑" id="submit"
									type="button" onclick="setvalue('#test3')">编辑</button>
							</td>
						</tr>
						<tr>
							<td>电话：</td>
							<td colspan="2"><input name="telNo" type="text" id="test4"
								value="<%=user.getTel()%>" disabled></td>
							<td>
								<button class="btn btn-small" value="编辑" id="submit"
									type="button" onclick="setvalue('#test4')">编辑</button>
							</td>
						</tr>
						<tr>
							<td>邮箱:</td>
							<td colspan="2"><input type="text" name="email" id="test5"
								value="<%=user.getEmail()%>" disabled></td>
							<td>
								<button class="btn btn-small" value="编辑" id="submit"
									type="button" onclick="setvalue('#test5')">编辑</button>
							</td>
						</tr>
						<tr>
							<td>使用权限</td>
							<td colspan="2">
							<label style="display: none;"> 浏览影像</label> 
							<label style="display: 1;"> 增删影像 </label> 
							<label style="display: none;"> 编辑影像 </label> 
							<label style="display: none;"> 导出影像 </label></td>
	
						</tr>
						
						<tr>
							
							<td>上次时间：</td>
							<td><%=user.getLastTime()%></td>
						</tr>
						
						<tr>
							<td colspan="3" style="text-align: right;">
							<button type="submit" class="btn btn-default">保存修改</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function setvalue(id) {
			var changeVal = $(id);
			$(changeVal).removeAttr("disabled");
		}
	</script>

</body>
</html>