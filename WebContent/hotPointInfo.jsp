<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

</style>

</head>

<div>
	<div>
		<h2>标注信息</h2>
	</div>

	<table>

		<tr>
			<td id="title">标注编号</td>
			<td width="150" ><label>ID123</label>
			</td>
		</tr>
		<tr>
			<td id="title">标注名称</td>
			<td width="150" id="tdEdit1"><label >图片标注1</label>
			</td>
			<td>
				<button class="btn btn-small" value="编辑" id="submit" onclick="clickSubmit('#tdEdit1')" type="button">编辑</button>
			</td>
		</tr>
		<tr>
			<td id="title">标注类型</td>
			<td width="150" id="tdEdit2"><label >图片标注</label>
			</td>
			<td>
				<button class="btn btn-small" value="编辑" id="submit" onclick="clickSubmit('#tdEdit2')" type="button">编辑</button>
			</td>
		</tr>
		<tr>
			<td id="title">坐标位置</td>
			<td width="150"  id="tdEdit3"><label>(1000,500)</label>
			</td>
			<td>
				<button class="btn btn-small" value="编辑" id="submit" onclick="clickSubmit('#tdEdit3')" type="button">编辑</button>
			</td>

		</tr>
		<tr>
			<td id="title">备注信息</td>
			<td width="150"></td>
		</tr>
	</table>
</div>

<form role="form">
	<div class="form-group">
		<textarea class="form-control" rows="5" style="width:90%;"></textarea>
	</div>
</form>
<div id="offset">
<a class="btn btn-large" href="#">确认修改</a>
<a class="btn btn-large" href="#">取消</a>
</div>
<script type="text/javascript">
						
						function clickSubmit(id){
							var changeVal=$(id);
							 $(changeVal).removeAttr("disabled");		
						}
</script>
						