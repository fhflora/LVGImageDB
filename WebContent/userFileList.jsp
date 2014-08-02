<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>

<html>
  <head>
  	<link rel="stylesheet" href="<%=path%>/css/button.css" type="text/css" />	
  	
  </head>
  
  <body>
  	 <script type="text/javascript" language="javascript">
  		function accessCommands(_dirPath){
  			var dirPath = encodeURIComponent(_dirPath);
  			var cmdUrl = "user_fileList.action?userCurrentDir="+dirPath;
  			window.location.href=cmdUrl;
  		}
  		function accessHome(){		
  			var userHome = encodeURIComponent("<s:property value='#session.userHome' />");
  			var cmdUrl = "user_fileList.action?userCurrentDir="+userHome	;	
  			window.location.href=cmdUrl;
  		}
  		function downloadCommands(_targetPath){
  			
  			var targetPath =  encodeURIComponent(_targetPath);
  			//var targetPath = encodeURIComponent(encodeURIComponent(_targetPath));
  			var cmdUrl = "downloadFile.action?downloadFile="+targetPath	;
  			alert("下载：" +targetPath);
  			window.location.href=cmdUrl;
  		}
  		function backCommands(){
  			var folderPath = encodeURIComponent("<s:property value='userCurrentDir' />");
  			window.location.href="folder_back.action?folderPath="+folderPath;
  		}
  		function deleteFileCommands(targetPath){
  			var folderPath = encodeURIComponent(targetPath);
  			var userCurrentDir = encodeURIComponent("<s:property value='userCurrentDir' />");
  			window.location.href="folder_delFile.action?folderPath=" + folderPath+"&userCurrentDir="+userCurrentDir;
  		}
  		function deleteDirCommands(){
  			var folderPath = encodeURIComponent("<s:property value='userCurrentDir' />");
  			window.location.href="folder_delete.action?folderPath="+folderPath;
  		}
  		function clearCommands(){
  			var folderPath = encodeURIComponent("<s:property value='userCurrentDir' />");
  			window.location.href="folder_clear.action?folderPath="+folderPath;
  		}
  		function createCommands(){
  			
  		}

  	</script>
	 <br><p align="center">您好 ${sessionScope.username} ，下面是您的文件列表</p><br><br>

	<div align="center">
	<table >
	 	<tr>
	 		<td align="center">
	 			<s:form action="uploadFile" method="post" enctype="multipart/form-data" theme="simple">
			 		<!-- style="display:none";  -->
			 		<input type="text" style="display:none" name="userCurrentDir" value="<s:property value='userCurrentDir'/>"/>		
			 		<font color="red">请选择上传的文件</font>: <s:file name="user.file" label="请选择上传的文件" cssClass="pbtnf1" /> 
			 		<s:submit value="上传"  onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'" cssClass="pbtnf1" /> 
			 		<br><br>
			 	</s:form>
			 	<br><br>
	 		</td>
	 	</tr>
	 </table>
	</div>
	 
	 <table align="center" width="80%">
	 	<tr>
	 		<td width="60%" align="left"><input type="button" value="后退" onclick="backCommands();" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="pbtnf1" />
	 									<input type="button" value="前进" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="pbtnf1" />
	 									<input type="button" value="首页" onclick="accessHome();" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="pbtnf1" />
	 		</td>
	 		<td align="right">			 	
			 	<input type="button" value="清空"  onclick="clearCommands();" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'" class="pbtnf1" />
	 		</td>						
	 		<td ><input type="button" value="新建" onclick="createCommands()" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="pbtnf1" /></td>
	 		<td ><input type="button" value="删除" onclick="deleteDirCommands()" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="pbtnf1" /></td>
	 		
	 	</tr>
	 	<s:iterator var="dir" value="dirNameList">
		 	<tr>
		 		<td width="60%" align="left" >
		 			<img src="<%=path%>/image/dir/dir_p4.JPG"> <s:property value="%{[0].dirName}"/> 
		 		</td>
		 		
		 		<td align="right">
		 			 <button type="button" class="btn21" onclick="downloadCommands('<s:property value="%{[0].dirPath}"/>');" onmouseover="this.style.backgroundPosition='left -40px'" onmouseout="this.style.backgroundPosition='left top'" ></button><br />
    			</td>
    			<td>	
    				<input type="button" value="删除" onclick="deleteCommands('<s:property value="%{[0].dirPath}"/>');" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="btn2 pbtn1" />
    			</td>
    			
    			<td>    					    				    					    
    			    <input type="submit" value="进入" onclick="accessCommands('<s:property value="%{[0].dirPath}"/>');" onmouseover="this.style.borderColor='#86c6f7'" onmouseout="this.style.borderColor='#dcdcdc'" class="btnf3 pbtn1" /> 
		 		</td>		 				 		
		 	</tr>
	 	</s:iterator>
	  	<s:iterator var="file" value="fileNameList">
		 	<tr>
		 		<td width="60%" align="left">
		 			 <img src="<%=path%>/image/file/file.jpg"> <s:property value="%{[0].fileName}"/> </a>
		 		</td>
		 		<td align="right">
		 			 <button type="button" class="btn21" onclick="downloadCommands('<s:property value="%{[0].filePath}"/>');" onmouseover="this.style.backgroundPosition='left -40px'" onmouseout="this.style.backgroundPosition='left top'" ></button><br />
    			</td>
    			<td>	
    				<input type="button" value="删除" onclick="deleteFileCommands('<s:property value="%{[0].filePath}"/>');" onmouseover="this.style.borderColor='#f76b00'" onmouseout="this.style.borderColor='#dcdcdc'"  class="btn2 pbtn1" />
    			</td>
    			<td>	
    				<input type="button" value="打开" onmouseover="this.style.borderColor='#86c6f7'" onmouseout="this.style.borderColor='#dcdcdc'" class="btn3 pbtn1" />
		 		</td>
		 	</tr>
	 	</s:iterator>
	 </table>
	 
  </body>
</html>
