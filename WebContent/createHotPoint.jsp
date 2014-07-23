<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.page import="com.imagedb.struct.HotPntInfo"/>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>	
<head>
<style>
#title {
	width: 60px;
}

td {
	text-align: center;
}

tr {
	height: 50px;
}
#pointName{
	width:80%;
	margin-right:-20%;
}
#offset{
margin-left:35%;}

.hotpnt3{
width:30px;
}

select{
width:88%;
margin-right:-20%;
}

#pointData{
margin-top:-3%;
margin-left:1%;
}

#pointInfo{
margin-top:9%;
}

</style>

</head>
<%
HotPntInfo hotPnt=new HotPntInfo();
%>
<div>
	<div>
		<h2>创建标注</h2>
	</div>
<form>
	<table>

		
		<tr>
			<td id="title">标注名称</td>
			<td><input type="text" class="form-control" id="pointName"
								placeholder="标注名称" />
			</td>
			
		</tr>
		<tr>
			<td id="title">标注类型</td>
			<td ><select class="for-control">
								<option value="">标注类型1</option>
								<option value="">标注类型2</option>
								<option value="">标注类型3</option>
							</select>
			</td>
			
		</tr>
	</table>
</div>
<div id="pointData">标记数据</div>
<input id="lefile" type="file" style="display:none;">  
<!-- 浏览文件夹 -->
<div class="input-append">  
    <input id="photoCover" class="input-large" type="text" style="width:65%;">  
    <a class="btn" onclick="$('input[id=lefile]').click();">浏览</a>  
</div> 

<div id="pointInfo">备注信息</div>
<div >
		<textarea class="form-control" rows="5" style="width:85%;"><%=hotPnt.getStrRemark()%></textarea>
</div>

<div id="offset">

<button type="submit" class="btn btn-default">创建标注</button>
<button type="submit" class="btn btn-default">取消</button>

</div>
</form>
<script type="text/javascript">  
$('input[id=lefile]').change(function() {  
$('#photoCover').val($(this).val());  
});  
</script>  
