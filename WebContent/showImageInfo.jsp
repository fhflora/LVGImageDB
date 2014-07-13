<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<style>
.menuLeft {
	width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
    background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	
}
table{
width:100%;
}
textarea{
width:80%;}

</style>
</head>

<div class="menuLeft">
	<div>
		<h2>影像信息</h2>
	</div>
			
					<table>
						<tr>
							<td>图像编号</td>
							<td><label name="userName" class="t" id="test">ID123</label>
							</td>
						</tr>
						<tr>
							<td>图像名称</td>
							<td><label name="userName" class="t" id="test">洞窟1</label>
							</td>
							<td>
								<button class="btn btn-small" value="编辑"  id="submit">编辑 </button> 
							</td>
						</tr>
						<tr>
							<td>采集时间</td>
							<td><label name="userName" class="t" id="test">2014/6</label>
							</td>
							<td>
								<button class="btn btn-small" value="编辑"  id="submit">编辑 </button> 
							</td>
						</tr>
						<tr>
							<td>描述信息</td>
							<td>
							<textarea rows="3"></textarea>
							</td>
							<td>
								<button class="btn btn-small" value="编辑"  id="submit">编辑 </button> 
							</td>
						</tr>
						<tr>
							<td>图像格式</td>
							<td><label name="userName" class="t" id="test">TBM</label>
							</td>
						</tr>
						<tr>
						<tr>
							<td>图像大小</td>
							<td><label name="userName" class="t" id="test">500M</label>
							</td>
						</tr>
						<tr>
						<tr>
							<td>入库时间</td>
							<td><label name="userName" class="t" id="test">2014/6/28</label>
							</td>
						</tr>
						<tr>
					</table> 

</div>