<%@page import="org.lanqiao.weixin.utils.WxUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'uploadToWx.jsp' starting page</title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="keyword1,keyword2,keyword3">
	<meta name="description" content="this is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
   	<form action="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=<%=WxUtils.getAccessToken() %>&type=image" method="post" enctype="multipart/form-data">
   		<input type="file" name="media"><input type="submit">
   	</form>
  </body>
</html>
