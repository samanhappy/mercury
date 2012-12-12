<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<!--
.font10 {
	font-family: "宋体", "Arial";
	font-size: 13pt;
	text-decoration: none;
}
-->
</style>
<title>Insert title here</title>
</head>
<body>
	<table border="1" align="center" class="font10" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>服务器</th>
				<th>配置</th>
				<th>状态</th>
				<th>间隔时间</th>
				<th>重试次数</th>
				<th>连接限制</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="servers">
				<tr>
					<td><s:property value="name" /></td>
					<td style="word-break:break-all;" width="800px"><s:property value="inPath" /></td>
					<td><s:property value="status" /></td>
					<td><s:property value="intervaltime" /></td>
					<td><s:property value="retrytime" /></td>
					<td><s:property value="connections" /></td>
					<td><s:a action="modifyConfig">
							<s:param name="id" value="id"></s:param>
							修改
						</s:a> <s:a action="deleteConfig">
							<s:param name="id" value="id"></s:param>
							删除
						</s:a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>