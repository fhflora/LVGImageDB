<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- <%@ taglib prefix="s" uri="/struts-tags" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>影像上传</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- Bootstrap responsive -->
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<!-- Font awesome - iconic font with IE7 support -->
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/font-awesome-ie7.css" rel="stylesheet">
<!-- Bootbusiness theme -->
<link href="css/boot-business.css" rel="stylesheet">
<!-- main theme -->
<link href="css/main.css" rel="stylesheet">
<!--datetimepicker -->
<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">



<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/boot-business.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
	src="js/bootstrap-datetimepicker.zh-CN.js"></script>



<style type="text/css">
.menuLeft {
	width: 230px;
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

.control-btn {
	margin-top: 10px;
	text-align: center;
}

.control-btn button {
	width: 20%;
}

#upload {
	margin-left: 25%;
}
</style>
</head>
<body>
	<header>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<div class="row">
					<div class="span2">
						<a href="createImage.jsp" class="brand brand-bootbus">LVY</a>
					</div>
					<div class="span3 offset7">
						<a href="createImage.jsp" class="brand" id="title">影像上传</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</header>

	<div class="container">
		<div class="row" style="margin-top: 50px;">
			<span class="span3">
				<div class="menuLeft">
					<div>
						<h2>影像信息</h2>
					</div>
					<form role="form" action="">
						<div class="form-group">
							<input type="text" class="form-control" id="name"
								placeholder="图像名称">
						</div>

					</form>
					<form role="form" action="">
						<div class="form-group">

							<select class="for-control">
								<option value="">航片图</option>
								<option value="">全景图</option>
								<option value="">超大图</option>
							</select>

						</div>

					</form>

					<div class="control-group">

						<div class="controls input-append date form_date"
							data-date="yyyy/mm/dd" data-date-format="yyyy/mm/dd"
							data-link-field="dtp_input2" data-link-format="yyyy/mm/dd">
							<input size="16" type="text" value="" readonly placeholder="采集时间"
								style="width: 65%;"> <span class="add-on"><i
								class="icon-remove"></i></span> <span class="add-on"><i
								class="icon-th"></i></span>
						</div>
						<input type="hidden" id="dtp_input2" value="" /><br />
					</div>
					<script type="text/javascript">
						$('.form_date').datetimepicker({
							language : 'zh-CN',
							weekStart : 1,
							todayBtn : 1,
							autoclose : 1,
							todayHighlight : 1,
							startView : 2,
							minView : 2,
							forceParse : 0
						});
					</script>

					<form role="form" action="">
						<div class="form-group">
							<label for="name">描述信息</label>
							<textarea class="form-control" rows="3"></textarea>

						</div>

					</form>
					<a class="btn btn-large" href="#" id="upload">上传影像</a>

				</div>
			</span> <span class="span8 offset1"> <img src="img/images/1.jpg"
				class="img-polaroid" alt="">
			</span>
		</div>
	</div>

</body>
</html>