<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
  <div class="content">
    <div class="container">
		<div class="row">
        <form class="main form-horizontal" onsubmit="return check()" action="main/active"
        onsubmit="return check();">
        <fieldset>
            <legend>企业信用信息平台开通</legend>
            <div class="control-group">
                <label class="control-label">
                    密码</label>
                <div class="controls">
                    <input id="pwd" name="pwd" type="password" placeholder="必填项" class="input-xlarge"
                        required onchange="checkPasswords()">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                    确认密码</label>
                <div class="controls">
                    <input id="pwd1" name="pwd1" type="password" placeholder="必填项" class="input-xlarge"
                        required onchange="checkPasswords()">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                    单位名称</label>
                <div class="controls">
                    <input name="dept" type="text" placeholder="必填项，单位全称" class="input-xlarge" required>
                </div>
            </div>
            <legend>管理员信息</legend>
            <div class="control-group">
                <label class="control-label">
                    性别</label>
                <div class="controls">
                    <label class="radio">
                        <input type="radio" value="男" name="sex" checked="checked">
                        男
                    </label>
                    <label class="radio">
                        <input type="radio" value="女" name="sex">
                        女
                    </label>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                    姓名</label>
                <div class="controls">
                    <input name="name" type="text" placeholder="必填项，实名" class="input-xlarge" required>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                    办公电话</label>
                <div class="controls">
                    <input name="tel" type="text" placeholder="必填项" class="input-xlarge" required>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="input01">
                    移动电话</label>
                <div class="controls">
                    <input name="phone" type="text" placeholder="必填项" class="input-xlarge" required>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                    邮箱</label>
                <div class="controls">
                    <input name="email" type="text" placeholder="必填项" class="input-xlarge" required pattern="^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$"
                        title="邮箱正确格式：xxx@xxx.xxx">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                    地址</label>
                <div class="controls">
                    <input name="addr" type="text" placeholder="必填项" class="input-xlarge" required>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                </label>
                <div class="controls">
                    <button class="btn" type="submit" id="ok">
                        开 通</button>
                    <button class="btn" type="reset">
                        重 置</button>
                </div>
            </div>
        </fieldset>
        </form>
    </div>
	</div>
  </div>
    <script>
        function checkPasswords() {
            var passl = document.getElementById("pwd");
            var pass2 = document.getElementById("pwd1");
            if (passl.value != pass2.value)
                passl.setCustomValidity("两次密码必须输入一致！");
            else
                passl.setCustomValidity('');
        }

        function check() {
            document.getElementById('ok').disabled = 'disabled';
        }
    </script>
</body>
</html>