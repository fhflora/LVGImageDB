<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建用户表单</title>
</head>
<body>
<%--创建用户的表单 --%>
<form name="userInfo" action="addUser" method="post">
 <h4 class="form-sigin-heading">用户信息</h4>
    <div class="control-group">
      <div class="controls">
		<input type="text" name="userName" class="input-block-level" placeholder="用户名" required>
	  </div>
	</div>
 	<div class="control-group">
      <div class="controls">
		<input type="text" name="realName" class="input-block-level" placeholder="真实姓名" required>
	  </div>
	</div>
	<div class="control-group">
      <div class="controls">
		<input type="password" name="password" class="input-block-level" placeholder="密码" required>
	  </div>
	</div>
	<div class="control-group">
      <div class="controls">
		<input type="password" name="rePassword" class="input-block-level" placeholder="密码确认" required onchange="checkPasswords()">
	  </div>
	</div>
	<div class="control-group">
		<label class="control-label">
			用户类型
		</label>
		<div class="controls">
		<select class="selectpicker" name="userType" style="width:100%;">
		<option value="user">普通用户</option>
		<option value="manager">管理员</option>
		</select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">
			用户权限
		</label>
		<div class="controls">
		 <label class="checkbox inline">
			<input type="checkbox" name="userRight" value="browse">浏览影像
		</label>
		<label class="checkbox inline">
			<input type="checkbox" name="userRight" value="addanddel">增删影像
		</label>	
		<div class="controls">
			<label class="checkbox inline">
				<input type="checkbox" name="userRight" value="edit">编辑影像
			</label>
			<label class="checkbox inline">	
				<input type="checkbox" name="userRight " value="export"> 导出影像
			</label>
		</div>
		</div>
	</div>
	<div class="control-group">
		<div class="controls">
			<input type="text" name="userTel" class="input-block-level" placeholder="电话" value="电话">
		</div>
	</div>
	<div class="control-group">
		<div class="controls">
			<input type="text" name="userTel" class="input-block-level" placeholder="邮箱" value="邮箱">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">
		备注
		</label>
		<div class="controls">
			<textarea name="remark" rows="3" class="input-block-level">
			</textarea>
		</div>
	</div>
	<button class="btn btn-large" type="submit" name="submit">创建用户</button>
	<button class="btn btn-large" type="reset"  name="reset"  value="取消">取消</button>

</fieldset> 
</form>
</body>
</html>