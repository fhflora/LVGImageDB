<%@page import="com.imagedb.struct.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<!-- Jquery DataTable -->
<link href="css/ManageSubject.css" rel="stylesheet">
<link href="css/thickbox.css" rel="stylesheet">
<link href="css/flexigrid.css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/boot-business.js"></script>
<script type="text/javascript" src="js/thickbox-compressed.js"></script>
<script type="text/javascript" src="js/flexigrid.js"></script>
<script type="text/javascript" src="js/management.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getUserInfoPageList();
	$("#search").click(function(){
		var userID = $("#userID").val();
		alert(userID);
		$("#flex1").flexOptions({qtype:'userID', query:userID});定义好搜索内容和条件
		$('#flex1').flexReload();重新loading数据
		
	});
});
</script>
<style>
.btn {
	margin-top: 10px;
}
.flexigrid{
margin-top:50px;
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
	
	<div class="content">
		<div class="container">
			<div class="row">
				<span class="span2">
				 <jsp:include page="search.jsp"></jsp:include>
				</span>
				<div style="height: 600px; float: left; margin-left: 20px;margin-top:200px;"></div>
				
				<table id="flex1" style="display: none">
				</table>

			</div>
		</div>
	</div>

	<s:iterator value="userInfoList" id="userInfo">

		<a id="aid_<s:property value='#userInfo.userID' />"
			style="display: none"
			href="#TB_inline?height=400&width=580&inlineId=div<s:property value='#userInfo.userID' />&modal=true"
			class="thickbox">修改</a>

		<div id="div<s:property value='#userInfo.userID' />"
			style="display: none">

			<input name='userInfo.userID' value='<s:property value="#userInfo.userID" />' type="hidden" /> 
			
			<span class='sub-spp'>用户名</span>
			<span class='sub-keywords'></span>
			<input id='userName_<s:property value="#userInfo.userID" />' value='<s:property value="#userInfo.userName" />' type='text' /> 
			<span class='sub-keytips' id='keytipsUserName_<s:property value="#userInfo.userID"/>'></span></br>
			
			<span class='sub-spp'>真实姓名</span>
			<span class='sub-keywords'></span>
			<input id='realName_<s:property value="#userInfo.userID" />' value='<s:property value="#userInfo.realName" />' type='text' /> 
			<span class='sub-keytips' id='keytipsRealName_<s:property value="#userInfo.userID"/>'></span></br>
			
			<span class='sub-spp'>密码</span>
			<span class='sub-keywords'> </span>
			<input id='password_<s:property value="#userInfo.userID" />' value='<s:property value="#userInfo.password"/>' type='text' />
			<span class='sub-keytips' id='keytipsPassword_<s:property value="#userInfo.userID"/>'> </span> </br>
			
			<span class='sub-spp'>用户类型</span>
			<span class='sub-keywords'> </span>
			
			<s:if test="#userInfo.userType==1" >
				<select name="selectUserType" id='userType_<s:property value="#userInfo.userID" />'>
				<option value="1" selected>用户</option>
				<option value="0" >管理员</option>
				</select>
			 </s:if> 
			<s:else>
				<select name="selectUserType">
				<option value="1" >用户</option>
				<option value="0" selected >管理员</option>
				</select>
			</s:else>
			</br>
		
			<span class='sub-spp'>电话</span>
			<span class='sub-keywords'></span>
			<input id='tel_<s:property value="#userInfo.userID" />' value='<s:property value="#userInfo.tel"/>' type='text' />
			<span class='sub-keytips'></span> 

			</br>
			
			<span class='sub-spp'>邮箱</span>
			<span class='sub-keywords'></span>
			<input id='email_<s:property value="#userInfo.userID" />'
				value='<s:property value="#userInfo.email"/>' type='text' />
			</br>
			
			<span class='sub-spp'>使用权限</span>
			<span class='sub-keywords'></span>
			<%-- <input id='email_<s:property value="#userInfo.userID" />'> --%>
				
				<label class="checkbox inline">
				<s:if test="(#userInfo.permission&1)==1" >
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=1 checked>导入影像
				</s:if>
				<s:else>
				<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=1>导入影像
				</s:else>
				</label>
				<label class="checkbox inline">
				<s:if test="(#userInfo.permission&2)==2" >
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=2 checked>删除影像
				</s:if>
				<s:else>
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=2>删除影像
				</s:else>
				</label>	
				<label class="checkbox inline">
				<s:if test="(#userInfo.permission&4)==4" >
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=4 checked>编辑影像
				</s:if>
				<s:else>
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=4 >编辑影像
				</s:else>
				</label>
				<label class="checkbox inline">	
				<s:if test="(#userInfo.permission&8)==8" >
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=8 checked> 导出影像
				</s:if>
				<s:else>
					<input type="checkbox" name='checkboxPermission_<s:property value="#userInfo.userID" />' value=8> 导出影像
				</s:else>
				</label>
				</br>
				
				<span class='sub-spp'>备注</span>
					<span class='sub-keywords'></span>
				<textarea  id='remark_<s:property value="#userInfo.userID" />' rows="2"><s:property value="#userInfo.remark"/></textarea>
				</br>
			
			<div>
				<span class='sub-spp'></span><span class='sub-keywords'></span> 
				<input
					class="btn btn-primary btn-sm" type="submit" value="修改"
					onclick="modifyUserInfo(<s:property value='#userInfo.userID' />)">
					&nbsp;&nbsp;&nbsp;&nbsp;
					
				<input type="button" class="btn btn-primary btn-sm"
					onclick="tb_remove()" value="关闭" />
			</div>

		</div>
	</s:iterator>


	<a id="aid_new"
		href="#TB_inline?height=350&width=610&inlineId=div_new&modal=true"
		class="thickbox" style="display: none">添加</a>

	<div id="div_new" style="display: none">
		
			<span class='sub-spp'>用户名</span>
			<span class='sub-keywords'></span>
			<input id="userName_new" type='text' /> 
			<span class='sub-keytips' id='keytipsUserName_new'></span></br>
			
			<span class='sub-spp'>真实姓名</span>
			<span class='sub-keywords'></span>
			<input id='realName_new' type='text' /> 
			<span class='sub-keytips' id='keytipsRealName_new'></span></br>
			
			<span class='sub-spp'>密码</span>
			<span class='sub-keywords'> </span>
			<input id='password_new' type='text' />
			<span class='sub-keytips' id='keytipsPassword_new'> </span> </br>
			
			<span class='sub-spp'>用户类型</span>
			<span class='sub-keywords'> </span>
			
				<select name="selectUserType" id='userType_new'>
				<option value="1" selected>用户</option>
				<option value="0" >管理员</option>
				</select>
			</br>
		
			<span class='sub-spp'>电话</span>
			<span class='sub-keywords'></span>
			<input id='tel_new' type='text' />
			<span class='sub-keytips'></span> 
			</br>
			
			<span class='sub-spp'>邮箱</span>
			<span class='sub-keywords'></span>
			<input id='email_new' type='text' />
			</br>
			
			<span class='sub-spp'>使用权限</span>
			<span class='sub-keywords'></span>
				<label class="checkbox inline">
					<input type="checkbox" name='checkboxPemission_new' value=1>导入影像
				</label>
				<label class="checkbox inline">
					<input type="checkbox" name='checkboxPemission_new' value=2>删除影像
				</label>	
				<label class="checkbox inline">
					<input type="checkbox" name='checkboxPemission_new' value=4>编辑影像
				</label>
				<label class="checkbox inline">	
					<input type="checkbox" name='checkboxPemission_new' value=8> 导出影像
				</label>
				</br>
				
				<span class='sub-spp'>备注</span>
					<span class='sub-keywords'></span>
				<textarea  id='remark_new' rows="2"></textarea>
				</br>
		<div>
			<span class='sub-spp'></span><span class='sub-keywords'></span> 
			<input class="btn btn-primary btn-sm" type="submit" value="添加"
				onclick="insertUserInfo()">&nbsp;&nbsp;&nbsp;&nbsp; <input
				type="button" class="btn btn-primary btn-sm" onclick="tb_remove()"
				value="关闭" />
		</div>

	</div>
	
	
</body>
</html>