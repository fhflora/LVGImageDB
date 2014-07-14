<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
      <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
          <div class="container">
            <a href="home.jsp" class="brand brand-bootbus">LVY</a>
  	    	<a href="home.jsp" class="brand lblHeader">浏览影像</a>
  	    	<a href="#" class="brand lblHeader">上传</a>
            <!-- Below button used for responsive navigation -->
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <!-- Start: Primary navigation -->
            <div class="nav-collapse ">        
              <ul class="nav pull-right">
               <li>
               	<form class="form-inline" role="form">
                 <div class="form-group" id="inputSearch">	               			
					<input type="search" class="form-control" id="searchBox" placeholder="输入图像键字或描述">
					<a class="btn pull-right" id="iconSearch" href="#"><i class="icon-search"></i></a>				    
			     </div>
			     </form>
               </li>
               <li class="dropdown" style="margin-left:10px; width:100px">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown">用户名<b class="caret"></b></a>
                	<ul class="dropdown-menu">
                		<li class="nav-header" style="width:200px;height:100px;">
 							<h4>欢迎回来:<%=session.getAttribute("username")%></h4>
 						</li>
                		<li class="nav-header"></li>
                		<li class="pull-right"><button class="btn" onclick="window.location.href='userInfoClient.jsp'">用户信息</button>
                		  <button class="btn" onclick="window.location.href='index.jsp'">安全退出</button>
                		</li>
                	</ul>
               </li> 
              </ul>
            </div>
          </div>
        </div>
      </div>