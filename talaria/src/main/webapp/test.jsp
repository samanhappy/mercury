<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-1.4.2.js"></script>
</head>
<body>
<script>




var xmlDocument = "<?xml version='1.0' encoding='UTF-8'?><pushMail><uid>1</uid></pushMail>"; 


$.ajax({ 
	  url: "http://127.0.0.1:8080/talaria/LongPullServlet", //本例中，url为genXML.php
	  type: 'post', 
	  data: xmlDocument,
	  dataType: 'xml', //指定数据类型
	  error: function(){ 
	    alert('Error loading XML document'); }, //错误提示
	  success: function(xml){
		alert($(xml).find('uid').text());
	  }
	});
	</script>
</body>
</html>