<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- <%@ taglib prefix="s" uri="/struts-tags" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>影像上传</title>

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

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/boot-business.js"></script>
<style>
.control-btn{
	margin-top: 10px;
	 text-align:center;
	 
	}
.control-btn button{
 	width:20%;
}
</style>
<body>
	<header> <jsp:include page="header.jsp" /> </header>
	<div >
	<ul class="list-inline">
 			<li style="margin-left: 43%; margin-top: 100px"><h2>把影像拖放到这里</h2></li>
 	</ul>
 	<ul class="list-inline">
 			<li style="margin-left: 49%; margin-top: 10px"><h2>或</h2></li>
 	</ul>
	</div>
	<div class="control-btn">
						<button class="btn btn-large" type="submit" name="选择影像" ><h2>选择影像</h2></button>
						</div>
</body>
</html>