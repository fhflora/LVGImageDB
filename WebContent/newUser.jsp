<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--创建用户的表单 --%>
<form name="userInfo" action="addUser" method="post">
       
用户名：<input type="text" name="userName" size="20">
<br>
真实姓名<input type="text" name="realName"  size="20">
<br>
密码<input type="password" name="password"  size="20">
<br>
密码确认<input type="password" name="rePassword"  size="20">
<br>
用户类型：
<select name="userType">
<option value="user">普通用户</option>
<option value="manager">管理员</option>
</select>
<br>
用户权限：
<br>
<input type="checkbox" name="userRight" value="browse">浏览影像

<input type="checkbox" name="userRight" value="addanddel">增删影像
<br>

<input type="checkbox" name="userRight" value="edit">编辑影像

<input type="checkbox" name="userRight " value="export"> 导出影像
<br>
<input type="text" name="userTel" value="电话"size="20">
<br>
<input type="text" name="userEmail" value="邮箱" size="20"> 
<br>
备注：
<br>
<textarea name="remark" rows="10" cols="30">

</textarea>
<br>
<input type="submit" name="submit" value="创建账户">
<input type="reset"  name="reset"  value="取消">


</form>
</body>
</html>