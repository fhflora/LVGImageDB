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
			} ,{
				display:'创建者',
				name:'createBy',
				sortable:false,
				align:'center'
			},
			{
				display:'备注',
				name:'remark',
				sortable:false,
				align:'left'
			}],
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
                        	alert(msg);
                            $("#flex1").flexReload();  
                            
                        },  
                        error: function(msg){  
                            alert(msg);  
                        }  
                 });  
            }  
       }  
    }else if (com=='添加'){  
    	document.getElementById('aid_new').click();
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
	if(userName==""){
		$("#keytipsUserName_new").html("*用户名不能为空");
		return;
	}
	var realName=$("#realName_new").val();
	if(realName==""){
		$("#keytipsRealName_new").html("*真实姓名不能为空");
		return;
	}
	var remark=$("#remark_new").val();
	var permission=0;
	var checkboxId="input[name='permission_new']:checked";
	$(checkboxId).each(function(){
		permission+=parseInt($(this).attr('value'));		
	});

	var password = $("#password_new").val();
	if(password==""){
		$("#keytipsPassword_new").html("*密码不能为空");
		return;
	}
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
	if(userName==""){
		$("#keytipsUserName_"+userID).html("*用户名不能为空");
		return;
	}
	var  realName= $("#realName_"+userID).val();
	if(realName==""){
		$("#keytipsRealName_"+userID).html("*真实姓名不能为空");
		return;
	}
	var  password= $("#password_"+userID).val();
	if(password==""){
		$("#keytipsPassword_"+userID).html("*密码不能为空");
		return;
	}
	var userType = $("#userType_"+userID).val();
	var tel = $("#tel_"+userID).val();
	var email = $("#email_"+userID).val();
	var permission=0;
	var checkboxId="input[name='checkboxPermission_"+userID+"']:checked";
	$(checkboxId).each(function(){
		permission+=parseInt($(this).attr('value'));		
	});

	var remark=$("#remark_"+userID).val();
	
	var data={"userInfo.userID":userID,"userInfo.userName":userName,"userInfo.realName":realName,
			"userInfo.password":password,"userInfo.userType":userType,
			"userInfo.tel":tel,"userInfo.email":email,
			"userInfo.permission":permission,"userInfo.remark":remark};
	$.ajax({  
        type: "POST",  
        url: "modifyUserInfoAjax.action", 
        data:data,
        dataType:"json",  
        success: function(json){  
        	var msg = json.message;
        	if(msg=='success'){
        		alert("用户信息修改成功");  
        	}
        	else
            	alert("用户信息修改失败");
        	tb_remove();
        	$("#flex1").flexReload();
        },  
        error: function(msg){  
            alert(msg);  
        }  
 });  
}
