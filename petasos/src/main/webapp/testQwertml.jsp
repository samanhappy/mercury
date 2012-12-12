<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试协议</title>
<script type="text/javascript" src="scripts/jquery-1.4.2.js"></script>
<script type="text/javascript">
$(function() {
	$("#send").click(function(){
		$("#receiveXml").val("正在执行请求，请稍后。。。");
		$.ajax({
			url : "qwertml.do?f=" + escape(new Date()), 
			type : "post",
			data : {xml : $("#sendXml").val()}, 
			success : function(datas, textStatus){
				$("#receiveXml").val(datas);
			}
		});
	});
});
</script>
</head>
<body>

	<form action="qwertml.do">
		请将发送的协议内容粘贴在下面：<br />
		<textarea rows="15" cols="120" id="sendXml" name="sendXml">
			
		</textarea>
		<br />
		<input type="button" value="发送协议" id="send"/>
		<br />
		<label id="info"></label>
		返回的协议内容：<br />
		<textarea rows="15" cols="120" id="receiveXml" name="receiveXml" readonly="readonly">
		</textarea>
	</form>
	

</body>
</html>