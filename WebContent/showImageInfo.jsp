<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="com.imagedb.struct.ImageInfo"/>
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
<style>
.menuLeft {
	width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
    background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	
} 
table{
width:100%;
}
textarea{
width:80%;}
input{
width:50%;
}
tr{
height:10px;
}
</style>
</head>
<body>
<!-- 页面顶端导航和搜索框 -->
  <header>
  	  <!-- Start: Navigation wrapper -->
       <jsp:include page="header.jsp"/>
      <!-- End: Navigation wrapper --> 
     
  </header>
<%
ImageInfo image=new ImageInfo();
%>
<div class="container">
      <div class="row" style="margin-top: 50px;">
	    <span class="span4">
	    	<div class="menuLeft">
	<div>
		<h2>影像信息</h2>
	</div>
			
					<table>
						<tr>
							<td>图像编号</td>
							<td><%=image.getnImageID()%></td>
						</tr>
						<tr>
							<td>图像名称</td>
							<td><input type="text" id="image1"  value="<%=image.getStrImageName()%>" disabled />
							</td>
							<td>
								<button class="btn btn-small" value="编辑" onclick="setvalue('#image1')"  type="button">编辑 </button>  
							</td>
						</tr>
						<tr>
							<td>采集时间</td>
							<td><input type="text" id="image2"  value="<%=image.getDtAcquireTime()%>" disabled />
							</td>
							<td>
								<button class="btn btn-small" value="编辑" onclick="setvalue('#image2')"  type="button">编辑 </button>
							</td>
						</tr>
						<tr>
							<td>描述信息</td>
							<td>
							<textarea rows="3"><%=image.getStrDescInfo() %></textarea>
							</td>
							<td>
								<button class="btn btn-small" value="编辑"  id="submit" onclick="clickSubmit('#tdEdit3')">编辑 </button> 
							</td>
						</tr>
						<tr>
							<td>图像格式</td>
							<td><label name="userName" class="t" id="test">TBM</label>
							</td>
						</tr>
						<tr>
						<tr>
							<td>图像大小</td>
							<td><label name="userName" class="t" id="test">500M</label>
							</td>
						</tr>
						<tr>
						<tr>
							<td>入库时间</td>
							<td><label name="userName" class="t" id="test">2014/6/28</label>
							</td>
						</tr>
						<tr>
					</table> 

</div>
	    </span>
	    
	    <span class="span8">
	    <img src="img/images/1.jpg" class="img-polaroid">
	    </span>
	   </div>
</div>

<script type="text/javascript">
						
						function setvalue(id){
							var changeVal=$(id);
							 $(changeVal).removeAttr("disabled");	
						}
</script>
</body>			
</html>			