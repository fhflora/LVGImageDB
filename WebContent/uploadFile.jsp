<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
%>

<html>
  <head>
  </head>
  
  <body>
  	 <s:head/>
	 <h1>用户上传页面</h1>
	 <s:form action="uploadFile" method="post" enctype="multipart/form-data">
	 	<s:file name="uploadfile.file" label="请选择上传的图片"></s:file>
	 	<s:submit value="上传"/>
	 </s:form>
  </body>
</html>
