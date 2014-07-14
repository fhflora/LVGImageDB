<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/boot-business.js"></script>
    <style>
    .content {
    margin-top:150px;
  		padding: 80px 0;
  		background-color:#CECECE;
	}

    .form-signin {
	  width:300px;
	  padding: 19px 29px 29px;
	  margin: 0 auto 20px;
	  background-color: #fff;
	  border: 1px solid #e5e5e5;
	  -webkit-border-radius: 5px;
	     -moz-border-radius: 5px;
	          border-radius: 5px;
	  -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
	     -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
	          box-shadow: 0 1px 2px rgba(0,0,0,.05);
	}
    .form-sigin-heading{
	
	}
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
	  margin-bottom: 10px;
	}
	#certCode{
	  font-size: 16px;
	  height: auto;
	  width:100px;
	  margin-bottom: 15px;
	  padding: 7px 9px;
	}
	.form-signin input[type="text"],
	.form-signin input[type="password"] {
	  font-size: 16px;
	  height: auto;
	  margin-bottom: 15px;
	  padding: 7px 9px;
	} 
    </style>
    <script type="text/javascript">
    	function loadimage(){ 
		document.getElementById("randImage").src = "image.jsp?"+Math.random(); 
		} 
    </script>
</head>
<body>

<<<<<<< HEAD
<div class="content" id="contentIndex">
  <div class="container">
    <div class="row">
      <span class="span8">
                  <h3> 影像数据库系统</h3>
                  <p>......  PS:对系统的介绍</p>
      </span>
      <span class="span4">
     	 <form action="CheckLoginAction.action" method="post" class="form-signin" onsubmit="return loadimage()">
		    <h2 class="form-sigin-heading">LVG</h2>
		   	 <!-- 错误信息显示 -->
		    <div>
			    <ul>
			    <s:iterator value="actionMessages" var="message">
				    <li>
				    	<font color="red"><s:property value='message' /></font>
				    </li>
			    </s:iterator>
			    </ul>
		    </div>
		    <div class="control-group">
		      <div class="controls">
				<input type="text" name="user.userName" class="input-block-level" placeholder="用户名" required>
				<br>
			  </div>
			</div>
			<div class="control-group">
				<input type="password" name="user.password" class="input-block-level" placeholder="密码" required>
				<br>
			</div>
			<div class="control-group">
				<label class="radio inline">
					<input type="radio" name="user.userType" value="manager">
					 管理员
				</label>
				<label class="radio inline">
					<input type="radio" name="type" value="user" checked="checked">
					用户
				</label>
			</div>
				<br>
			 <div class="control-group">
				<input id="certCode" type="text"  name="certCode" class="input-large" required title="请输入验证码">
				<img src="image.jsp" border="0"  name="randImage" id="randImage" style="cursor:pointer;height:auto;" 
				onclick="this.src='CheckCode.jsp?ran='+Math.random()" />
			<a href="javascript:loadimage();"><font class=pt95>看不清，请点我</font></a>
			</div>
				<button class="btn btn-large" type="submit" name="登陆">提 交</button>
			</div>
	 </form>
      </span>
 	  </div>
	</div>
</div>
=======
<a href="javascript:loadimage();"><font class=pt95>看不清点我</font></a>
<br>
<input type="submit" name="登陆">
<div><s:debug></s:debug></div>
</form>
>>>>>>> origin/master
</body>
</html>