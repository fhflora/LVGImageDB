<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>影像数据库系统</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/font-awesome-ie7.css" rel="stylesheet">
<link href="css/boot-business.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/boot-business.js"></script>

<script type="text/javascript">
var index=0;
var count=25;
var type=1;
var databaseList;
var database="Test1";
$(document).ready(function(){
	var lvg_login=<%=session.getAttribute("lvg_login")%>;
	if(lvg_login==null || lvg_login=="" || lvg_login==false)
		location.href="index.jsp";
	LoadDataBaseInfo();
	LoadImage(database,type);
	$(window).scroll(function() {
        
        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
        	LoadImage(database,type);  
        }
    });   
});

function LoadDataBaseInfo(){
	var url="DatabaseListAjax.action";
	$.ajax({
		url:url,
		type:'POST',
		dataType:"json",
		success:function (data){
			var len=data.listDbName.length;
			$('#databaseList').attr("length",'0');
			for(var i=0;i<len;i++){
				$("#databaseList").append(
					$('<option value='+data.listDbName[i]+'>'+data.listDbName[i]+'</option>'));
			}
			
		},
		error:function(){
			alert("数据库列表获取失败，请联系管理员");
		}
		
	});
}

	/*设置显示的图片类型
	 * 1:普通图像
	 * 2：超大图像
	 * 3：全景图像
	 * 4：航片图像
	 */
function setImageType(type){
	$("#imglist").html("");
	this.type=type;
	index=0;
	LoadImage(database,type);
}
//设置显示的图片数据库
function setDatabase(){
	databaseList=document.getElementById("databaseList");
	database=databaseList.options[databaseList.selectedIndex].value;
	index=0;
	$("#imglist").html("");
	LoadImage(database,type);
}
	
function LoadImage(database,type){
	var url="ImageListAction.action";
	var data = {"index":index,"count":count,"type":type,"database":database};
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		dataType:"json",
		success : function(json) {
			var srcImgRowList = json;
			if(srcImgRowList.length==0){
				alert("没有更多的图片显示");
				return;
			}
			for(var i=0 ; i<srcImgRowList.length ; i++){
				var srcImgRow = srcImgRowList[i];
				var divStr="<div class='row-fluid'>";
				for(var j=0 ; j<srcImgRow.length;j++){
					var ShowImage = srcImgRow[j];
					var spanStr="<span class='span{width}''>"+
                				"<a href='#'><img src='{src}' class='imgInSpan{width}'></a>"+
                    			"</span>";
                    spanStr = replaceAll(spanStr,"{width}",ShowImage.srcImageWidth);
                    spanStr = replaceAll(spanStr,"{src}",ShowImage.srcImagePath);
           
                	divStr+=spanStr;
				}
				
				$("#imglist").append(divStr+"</div>");
			}
			index = index+count;
		},
		error : function() {
			alert("加载图片失败，请联系管理员");
		}
	});
}
	/**
	 * 替换字符串中所有
	 * @param obj	原字符串
	 * @param str1	替换规则
	 * @param str2	替换成什么
	 * @return	替换后的字符串
	 */
	function replaceAll(obj,str1,str2){       
		  var result  = obj.replace(eval("/"+str1+"/gi"),str2);   
		  return result;
	} 
</script>
<style type="text/css">
.imglist img{
	height:256px;
}
</style>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<body>
	<!-- 页面顶端导航和搜索框 -->
	<header> <jsp:include page="header.jsp" /> </header>

	<div class="content">
		<div class="container">
			<div class="page-header">
				<ul class="list-inline">
					<li>
						<h3>数据库列表</h3>
					</li>
					<li>
						<select name="databaseList" id="databaseList" style="width:150px;" onChange="setDatabase()"></select>
					</li>
					<li style="margin-left: 20px; margin-top: 20px;"><a onclick="setImageType(1);">普通图像</a></li>
					<li>
						<div
							style="height: 19px; width: 1px; border-left: 2px #BDB0B0 solid; margin: 20px 10px 0px 10px;"></div>
					</li>
					<li style=" margin-top: 20px;"><a onclick="setImageType(2);">超大图像</a></li>
					
					<li>
						<div
							style="height: 19px; width: 1px; border-left: 2px #BDB0B0 solid; margin: 20px 10px 0px 10px;"></div>
					</li>
					<li style="margin-top: 20px;"><a onclick="setImageType(3);">全景图像</a></li>
					<li>
						<div
							style="height: 19px; width: 1px; border-left: 2px #BDB0B0 solid; margin: 20px 10px 0px 10px;"></div>
					</li>
					<li style="margin-top: 20px;" ><a onclick="setImageType(4);">航片图</a></li>
					
				</ul>
			</div>
		</div>
 		<div> 
 	</div> 
		<!-- show images:现在只是本地图片  -->
	 	<div class="container" id="imglist">		
		 
		</div>
</body>
</html>