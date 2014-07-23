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
<link href="css/manage.css" rel="stylesheet">
<!-- Jquery DataTable -->
<link href="css/DT-bootstrap.css" rel="stylesheet">
<link href="css/ManageSubject.css" rel="stylesheet">
<link href="css/thickbox.css" rel="stylesheet">
<link href="css/flexigrid.css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/boot-business.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/DT-bootstrap.js"></script>
<script type="text/javascript" src="js/thickbox-compressed.js"></script>
<script type="text/javascript" src="js/flexigrid.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getUserInfoPageList();
});
function getUserInfoPageList() {
	
	$("#flex1").flexigrid(
		{
			url : 'UserListPagination',
			dataType : 'json',
			colModel : [ {
				display : '序号',
				name : 'serial',
				/* width : 80, */
				sortable : false,
				align : 'center',
				hide : false
			}, {
				display : '用户ID',
				name : 'userID',
				/* width : 80, */
				sortable : false,
				align : 'center',
				hide : true
			}, {
				display : '用户名',
				name : 'userName',
				/* width : 80, */
				sortable : false,
				align : 'center',
				hide : false
			}, {
				display : '真实姓名',
				name : 'realName',
				/* width : 80, */
				sortable : false,
				align : 'center'
			}, {
				display : '用户类型',
				name : 'userType',
				/* width : 80, */
				sortable : false,
				align : 'center'
			}, {
				display : '电话',
				name : 'tel',
				/* width : 80, */
				sortable : false,
				align : 'center'
			}, {
				display : '邮箱',
				name : 'email',
				/* width : 100, */
				sortable : false,
				align : 'center'
			}, {
				display : '用户权限',
				name : 'permission',
				/* width : 70, */
				sortable : false,
				align : 'center'
			} , {
				display : '登录状态',
				name : 'state',
				/* width : 70, */
				sortable : false,
				align : 'center'
			} , {
				display : '上次登录时间',
				name : 'lastTime',
				/* width : 70, */
				sortable : false,
				align : 'center'
			} ],
			buttons : [  
			            {name: '添加', bclass: 'add', onpress : toolbar},  
			            {name: '删除', bclass: 'delete', onpress : toolbar},  
			            {name: '修改', bclass: 'modify', onpress : toolbar},                
			            {separator: true}  
			            ], 
			sortname : "serial",
			sortorder : "desc",
			usepager: true,                //是否分页
			title: '用户列表',    
			procmsg: '正在处理数据,请等待...',    
			rpOptions: [2,5,10,15,20,25,30,40],             //可选择设定的每页结果数     
			useRp: true,          //是否使用分面    
			rp: 5,                       
			showTableToggleBtn: true,    
			width: 'auto',    
			height: 'auto',    
			nomsg: '不存在记录!',     
			pagestat: '显示{from}至{to} 条  共{total} 条',                       
			errormsg:'连接失败,请重试',     
			showToggleBtn: false,    
			autoload: true              //不允许自动加载 
		});
}

function toolbar(com,grid){  
    if (com=='删除'){  
       
        if($('.trSelected',grid).length==0){  
            alert("请选择要删除的数据");  
        }else{  
            if(confirm('是否删除这 ' + $('.trSelected',grid).length + ' 条记录吗?')){  
                 var  ids ="";  
                 for(var i=0;i<$('.trSelected',grid).length;i++){  
                	//var userId = $('.trSelected',grid).find("td").eq(1).text();
                    //ids+=$('.trSelected',grid).find("td").eq(1).text()+" ";//获取id  
                    ids+=$('.trSelected',grid)[i].childNodes[1].innerText+" ";
                 }  
                 alert(ids);
                 $.ajax({  
                        type: "POST",  
                        url: "deleteUserInfoAjax.action?delUserIDs="+ids,  
                        dataType:"json",  
                        success: function(json){  
                        	var msg = json.message;
                        	alert(msg)
                            $("#flex1").flexReload();  
                            
                        },  
                        error: function(msg){  
                            alert(msg);  
                        }  
                 });  
            }  
       }  
    }else if (com=='添加'){  
    	//document.getElementById('aid_new').click();
       $(".span3").html("");
       $(".span3").append("<jsp:include page='newUser.jsp'></jsp:include>");
    }else if (com=='修改'){  
        
        if($(".trSelected").length==1){  
        	var userId = $('.trSelected',grid).find("td").eq(1).text();
        	document.getElementById('aid_'+userId).click();
        }else if($(".trSelected").length>1){  
            alert("请选择一个修改,不能同时修改多个");  
        }else if($(".trSelected").length==0){  
            alert("请选择一个您要修改的记录")  ;
        }  
           
    }  
}  
function insertUserInfo(){
	var userName = $("#userName_new").val();
	var realName=$("#realName_new").val();
	var remark=$("#remark_new").val();
	var permission=$("#permission_new").val();
	var password = $("#password_new").val();
	var userType = $("#userType_new").val();
	var tel = $("#tel_new").val();
	var email = $("#email_new").val();
	var data={"userInfo.realName":realName,"userInfo.permission":permission,"userInfo.remark":remark,"userInfo.userName":userName,"userInfo.password":password,"userInfo.userType":userType,"userInfo.tel":tel,"userInfo.email":email};
	$.ajax({  
        type: "POST",  
        url: "insertUserInfoAjax.action", 
        data:data,
        dataType:"json",  
        success: function(json){  
        	var msg = json.message;
        	if(msg=='success'){
        		alert("用户添加成功");
        		$("#flex1").flexReload();  
        	}
        	else
            	alert("用户添加失败");
        	tb_remove();
        	$("#userName_new").val("");
        	$("#realName_new").val("");
        	$("#remark_new").val("");
        	$("#permission_new").val("");
        	$("#password_new").val("");
        	$("#userType_new").val("");
        	$("#tel_new").val("");
        	$("#email_new").val("");
        },  
        error: function(msg){  
            alert(msg);  
        }  
 });  
}
function modifyUserInfo(userID){
	var userName = $("#userName_"+userID).val();
	var password = $("#password_"+userID).val();
	var userType = $("#userType_"+userID).val();
	var tel = $("#tel_"+userID).val();
	var email = $("#email_"+userID).val();
	
	var data={"userInfo.userID":userID,"userInfo.userName":userName,"userInfo.password":password,"userInfo.userType":userType,"userInfo.tel":tel,"userInfo.email":email};
	$.ajax({  
        type: "POST",  
        url: "modifyUserInfoAjax.action", 
        data:data,
        dataType:"json",  
        success: function(json){  
        	var msg = json.message;
        	if(msg=='success'){
        		alert("用户ID:"+userID+"信息修改成功");
        		$("#flex1").flexReload();  
        	}
        	else
            	alert("用户ID:"+userID+"信息修改失败");
        	tb_remove();
        },  
        error: function(msg){  
            alert(msg);  
        }  
 });  
}
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

				<span class="span3">
				<jsp:include page="search.jsp"></jsp:include>
				</span>
				<span class="span9">
				<table id="flex1" style="display: none">
				</table>
				</span>
			</div>
		</div>
	</div>
	
	<s:iterator value="userInfoList" id="userInfo">

		<a id="aid_<s:property value='#userInfo.userID' />"
			style="display: none"
			href="#TB_inline?height=300&width=610&inlineId=div<s:property value='#userInfo.userID' />&modal=true"
			class="thickbox">修改</a>

		<div id="div<s:property value='#userInfo.userID' />"
			style="display: none">

			<input name='userInfo.userID'
				value='<s:property value="#userInfo.userID" />' type="hidden" /> <span
				class='sub-spp'>用户名</span><span class='sub-keywords'></span><input
				id='userName_<s:property value="#userInfo.userID" />'
				value='<s:property value="#userInfo.userName" />' type='text' /> </br>
			<span class='sub-spp'>密码</span><span class='sub-keywords'></span><input
				id='password_<s:property value="#userInfo.userID" />'
				value='<s:property value="#userInfo.password"/>' type='text' /><span
				class='sub-keytips'> 多个关键词请用空格分离，至少出现一个 </span> </br>
			<span class='sub-spp'>用户类型</span><span class='sub-keywords'></span><input
				id='userType_<s:property value="#userInfo.userID" />'
				value='<s:property value="#userInfo.userType"/>' type='text' /><span
				class='sub-keytips'> 多个关键词请用空格分离，没有则不填 </span> </br>
			<span class='sub-spp'>电话</span><span class='sub-keywords'></span><input
				id='tel_<s:property value="#userInfo.userID" />'
				value='<s:property value="#userInfo.tel"/>' type='text' /><span
				class='sub-keytips'> 多个关键词请用空格分离，没有则不填 </span> </br>
			<span class='sub-spp'>邮箱</span><span class='sub-keywords'></span><input
				id='email_<s:property value="#userInfo.userID" />'
				value='<s:property value="#userInfo.email"/>' type='text' /><span
				class='sub-keytips'> 多个关键词请用空格分离，没有则不填 </span> </br>
			</br>
			<div>
				<span class='sub-spp'></span><span class='sub-keywords'></span> <input
					class="btn btn-primary btn-sm" type="submit" value="修改"
					onclick="modifyUserInfo(<s:property value='#userInfo.userID' />)">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn btn-primary btn-sm" onclick="tb_remove()" value="关闭" />
			</div>

		</div>
	</s:iterator>


	<a id="aid_new"
		href="#TB_inline?height=350&width=610&inlineId=div_new&modal=true"
		class="thickbox" style="display: none">添加</a>

	<div id="div_new" style="display: none">

		<span class='sub-spp'>用户名</span><span class='sub-keywords'></span><input
			id='userName_new' value='' type='text' /> </br>
		<span class='sub-spp'>密码</span><span class='sub-keywords'></span><input
			id='password_new' value='' type='text' /><span class='sub-keytips'>
			多个关键词请用空格分离，至少出现一个 </span> </br>
		<span class='sub-spp'>用户类型</span><span class='sub-keywords'></span><input
			id='userType_new' value='' type='text' /><span class='sub-keytips'>
			多个关键词请用空格分离，没有则不填 </span> </br>
		<span class='sub-spp'>电话</span><span class='sub-keywords'></span><input
			id='tel_new' value='' type='text' /><span class='sub-keytips'>
			多个关键词请用空格分离，没有则不填 </span> </br>
		<span class='sub-spp'>邮箱</span><span class='sub-keywords'></span><input
			id='email_new' value='' type='text' /><span class='sub-keytips'>
			多个关键词请用空格分离，没有则不填 </span> </br> <span class='sub-spp'>真实姓名</span><span
			class='sub-keywords'></span><input id='realName_new' value=''
			type='text' /> </br> <span class='sub-spp'>用户权限</span><span
			class='sub-keywords'></span><input id='permission_new' value=''
			type='text' /> </br> <span class='sub-spp'>备注</span><span
			class='sub-keywords'></span><input id='remark_new' value=''
			type='text' />
		<div>
			<span class='sub-spp'></span><span class='sub-keywords'></span> <input
				class="btn btn-primary btn-sm" type="submit" value="添加"
				onclick="insertUserInfo()">&nbsp;&nbsp;&nbsp;&nbsp; <input
				type="button" class="btn btn-primary btn-sm" onclick="tb_remove()"
				value="关闭" />
		</div>

	</div>
</body>
</html>