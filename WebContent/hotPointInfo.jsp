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
width:55%;}

</style>

</head>

<div>
	<div>
		<h2>标注信息</h2>
	</div>

	<table>

		<tr>
			<td id="title">标注编号</td>
			<td width="150" ><label name="userName" class="t" id="test">ID123</label>
			</td>
		</tr>
		<tr>
			<td id="title">标注名称</td>
			<td width="150" id="tdEdit1"><label name="userName" class="t" id="test">图片标注1</label>
			</td>
			<td>
				<button class="btn btn-small" value="编辑" id="submit" onclick="clickSubmit('#tdEdit1')">编辑</button>
			</td>
		</tr>
		<tr>
			<td id="title">标注类型</td>
			<td width="150" id="tdEdit2"><label name="userName" class="t" id="test">图片标注</label>
			</td>
			<td>
				<button class="btn btn-small" value="编辑" id="submit" onclick="clickSubmit('#tdEdit2')">编辑</button>
			</td>
		</tr>
		<tr>
			<td id="title">坐标位置</td>
			<td width="150"  id="tdEdit3"><label name="userName" class="t" id="test">(1000,500)</label>
			</td>
			<td>
				<button class="btn btn-small" value="编辑" id="submit" onclick="clickSubmit('#tdEdit3')">编辑</button>
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
<script type="text/javascript">
						
						function clickSubmit(id){
							var tempTd=$(id);
							   //1.获取td的文本值
							var tdText = tempTd.val();//html()函数针对非value属性，val()针对value属性
							   //2.清空td的文本值
							tempTd.empty();//也可以使用empty()
							var tempInput=$("<input>");
							tempInput.attr("value",tdText);
							tempInput.keyup(function(event){
								var myEvent=event||window.event;
								var kcode=myEvent.keyCode;
								if(kcode==13){//按回车
									var val=tempIput.val();
									temTd.html(val);
								    tempTd.click(clickSubmit(id));			
								}							
							});
							tempTd.append(tempInput);
							var valueNode=tempInput.get(0);
							valueNode.select();
							tempTd.unbind("click"); 
						}
</script>
						