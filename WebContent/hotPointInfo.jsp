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
	width:55%;
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
		<h2>标注信息</h2>
	</div>
<form action="">
	<table>

		<tr>
			<td id="title">标注编号</td>
			<td><%=hotPnt.getnImageID()%></td>
		</tr>
		<tr>
			<td id="title">标注名称</td>
			<td><input type="text" id="hotpnt1"  value="<%=hotPnt.getStrName()%>" disabled />
			</td>
			<td>
				<button class="btn btn-small" value="编辑" onclick="setvalue('#hotpnt1')"  type="button">编辑 </button>  
			</td>
		</tr>
		<tr>
			<td id="title">标注类型</td>
			<td><input type="text" id="hotpnt2"  value="<%=hotPnt.getnType()%>" disabled />
			</td>
			<td>
				<button class="btn btn-small" value="编辑" onclick="setvalue('#hotpnt2')"  type="button">编辑 </button>  
			</td>
		</tr>
		<tr>
			<td id="title">坐标位置</td>
			<td width=50;>(<input  type="text" class="hotpnt3"  value="<%=hotPnt.getdPosX()%>" disabled />,
			<input type="text" class="hotpnt3"  value="<%=hotPnt.getdPosY()%>" disabled />)</td>
			
			<td>
				<button class="btn btn-small" value="编辑" onclick="setvalue('.hotpnt3')"  type="button">编辑 </button> 
			</td>

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
<script type="text/javascript">
						
						function setvalue(id){
							var changeVal=$(id);
							 $(changeVal).removeAttr("disabled");		
						}
</script>
						