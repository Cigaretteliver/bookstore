<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'admin.jsp' starting page</title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="keyword1,keyword2,keyword3">
	<meta name="description" content="this is my page">
	<link rel="stylesheet" type="text/css" href="scripts/bootstrap.min.css">
	<script type="text/javascript" src="scripts/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
  </head>
  
  <body>
  	<div class="container"> 
  		<h4>后台登录</h4>
  		<form action="adminservlet/login" method="post">
  			用户名：<input type="text" name="name"><br>
  			密码:<input type="password" name="pwd"><br>
  			<input type="submit" value="登录">
  		</form>
  	</div>
  </body>
</html>
