<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="Search">
<table border=0 align="left">
<tr><td>用户名</td>
<td><input type="text" name="userName" > </td> 
 </tr>
 <tr>
 <td>用户类型</td>
 <td><select name="userType">
<option value="allUser">所有用户</option>
<option value="manager">管理员</option>
<option value="ordinaryUser">普通用户</option>
</select></td>
 </tr>
 <tr>
 <td>用户权限</td>
 </tr>
 <tr>
 <td><input type="checkbox" name="userRight" value="browse">浏览影像</td>
 <td><input type="checkbox" name="userRight" value="addanddel">增删影像</td>
 
 </tr>
 <tr>
 <td><input type="checkbox" name="userRight" value="edit">编辑影像</td>
 <td><input type="checkbox" name="userRight " value="export"> 导出影像</td>
 </tr>
 <tr>
 <td>登录状态</td>
 <td><select name="userState">
<option value="online">在线</option>
<option value="offline">不在线</option>

</select></td>
 </tr>
 <tr>
 <td></td>
 <td><input type="submit" value="搜索"></td>
 </tr>
</table>
</form>

</body>
</html>