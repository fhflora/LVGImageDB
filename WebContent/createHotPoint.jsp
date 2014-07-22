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
input{
	width:70%;
}
#offset{
margin-left:35%;}
.hotpnt3{
width:30px;
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
			<td><input type="text" class="form-control" id="name"
								placeholder="标注名称" />
			</td>
			
		</tr>
		<tr>
			<td id="title">标注类型</td>
			<td><input type="text" id="hotpnt2"  value="<%=hotPnt.getnType()%>" disabled />
			</td>
			
		</tr>
		<tr>
			<td id="title">坐标位置</td>
			<td width=50;>(<input  type="text" class="hotpnt3"  value="<%=hotPnt.getdPosX()%>" disabled />,
			<input type="text" class="hotpnt3"  value="<%=hotPnt.getdPosY()%>" disabled />)</td>
		
		</tr>
		<tr>
			<td id="title">备注信息</td>
			<td width="150"></td>
		</tr>
	</table>
</div>
<div >
		<textarea class="form-control" rows="5" style="width:90%;"><%=hotPnt.getStrRemark()%></textarea>
</div>

<div id="offset">

<button type="submit" class="btn btn-default">确认修改</button>

</div>
</form>
