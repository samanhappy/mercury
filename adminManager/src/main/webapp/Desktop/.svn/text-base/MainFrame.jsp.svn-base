<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
<HEAD>
<TITLE>PushMail服务器维护系统</TITLE>
</HEAD>
<frameset id="fraMain" border="0" frameSpacing="0" rows="74,*,25"
	frameBorder="no">
	<frame id="TopMenu" name="TopMenu" src="Desktop/MainTopMenu.jsp"
		noResize scrolling="no">
	<frameset id="Bottom" border="0" frameSpacing="0" frameBorder="no"
		cols="115,*">
		<frame id="fraLeft" name="fraLeft" noResize
			src="Desktop/BackLeftMenu.jsp?authority=<s:property value="authority" />"
			scrolling="no">
		<frame id="Main" name="Main"
			src="Desktop/Main.jsp?username=<s:property value="username"/>"
			scrolling="auto">
	</frameset>
	<frame id="BottomMenu" src="Desktop/MainBottomBar.html"
		name="BottomMenu" noResize scrolling="no">
</frameset>
</HTML>
