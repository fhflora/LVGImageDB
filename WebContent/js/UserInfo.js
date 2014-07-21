var userCounts=0;
$(document).ready(function(){
	showUserInfoList();
});
function showUserInfoList(){
	var url='userInfoList.action';
	$.ajax({
		url:url,
		type:'POST',
		dataType:'json',
		success:function(json){
			var data=json.userInfoList;
			userCounts=data.length;
		},
		error:function(){
			alert("信息加载失败,请联系管理员");
		}
	});
}