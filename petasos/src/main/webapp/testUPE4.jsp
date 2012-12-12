<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="scripts/jquery-1.4.2.js"></script>
<script type="text/javascript">
	$(function() {
		HTTPLink();
	});

	function HTTPLink() {
		$("#info").val($("#info").val() + "\r\n" + "正在请求Http长链接......");
		var ajax;
        //开始初始化XMLHttpRequest对象
        if(window.XMLHttpRequest) { //Mozilla 浏览器
                ajax = new XMLHttpRequest();
                if (ajax.overrideMimeType) {//设置MiME类别
                        ajax.overrideMimeType("text/xml");
                }
        }
        else if (window.ActiveXObject) { // IE浏览器
                try {
                        ajax = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                        try {
                                ajax = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (e) {}
                }
        }
        if (!ajax) { // 异常，创建对象实例失败
                alert("不能创建XMLHttpRequest对象实例.");
        }
              
              
	var url = "http://192.168.20.210:9080/talaria/LongPullServlet";           
	//通过Post方式打开连接
	ajax.open("POST", url, true);
	//发送POST数据
	ajax.send("<?xml version='1.0' encoding='UTF-8'?><pushMail><uid>4</uid></pushMail>");
	//获取执行状态
	ajax.onreadystatechange = function() {
  	//如果执行状态成功，那么就把返回信息写到指定的层里
  	if (ajax.readyState == 4 && ajax.status == 200) {
   		$("#info").val($("#info").val() + "\r\n" + ajax.responseText);
   		HTTPLink();
  	}
 	};

	}
	</script>
<title>测试UPE</title>
</head>
<body>
	<textarea rows="50" cols="120" id="info" name="info" readonly="readonly">
	</textarea>
</body>
</html>