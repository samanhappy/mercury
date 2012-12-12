<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<HTML>
	<HEAD>
		<title>首页 </title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		
		<style type="text/css">
			.font9 { FONT-SIZE: 9pt; FONT-FAMILY: "新宋体", "宋体", "黑体", "Arial Narrow", "Verdana"; TEXT-DECORATION: none }
			A:link { COLOR: #000000; TEXT-DECORATION: none }
			A:visited { COLOR: #000000; TEXT-DECORATION: none }
			A:hover { COLOR: #0033cc; TEXT-DECORATION: none }
		</style>
		
		

	</HEAD>
	<body bgColor="#f2f9fb">
		
		<form id="Form1" method="post" >
			<table height="100%" cellSpacing="0" cellPadding="0" width="100%" border="0">
				<tr>
					<td vAlign="top" height="450">
						<!-- 下面的内容可以修改 -->
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="10">&nbsp;</td>
								<td vAlign="top">
									<!-- 中间 -->
									<table cellSpacing="0" cellPadding="0" width="100%" border="0">
										<tr>
											<td height="18" style="HEIGHT: 18px"></td>
										</tr>
										<tr>
											<td noWrap width="25%">
												<div align="left">&nbsp;欢迎您:<span id="username"></span>!</div>
											</td>
											<td noWrap align="center" width="55%">
												<P align="left"><font color="#ff9900"><strong>今天是: <%=new Date()%><asp:label id="lblDateTime"  enableviewstate="False"></asp:label>&nbsp;<span id="liveclock"></span>&nbsp;
															<asp:label id="lblWeek"  enableviewstate="False"></asp:label></P>
<!--												</STRONG></FONT></td>-->
											<td noWrap align="center" width="15%"></td>
										</tr>
										<tr>
											<td colSpan="4" height="12"><INPUT id="hidCommand" style="WIDTH: 32px; HEIGHT: 21px" type="hidden" size="1" name="hidCommand"
													><INPUT id="hidCommandPara" style="WIDTH: 32px; HEIGHT: 21px" type="hidden" size="1" name="hidCommandPara"
													></td>
										</tr>
									</table>
									
								</td>
								<td width="10">&nbsp;</td>
								
							</tr>
						</table>
						<!-- 上面的内容可以修改 --></td>
				</tr>
				<TR>
					<TD vAlign="top" height="100%"></TD>
				</TR>
			</table>
		</form>
		<!-- #include file="../Js/FootFunction.js" -->
	</body>
	<script type="text/javascript">
		<!--
	    	var LocString=String(window.document.location.href);  
	    	function getQueryStr(str){  
	       		var rs = new RegExp("(^|)"+str+"=([^\&]*)(\&|$)","gi").exec(LocString), tmp;
	       		if(tmp=rs){  
	            	return tmp[2];  
	        	} 
	        	return "";  
	    	}
	    	 document.getElementById("username").innerHTML = getQueryStr("username"); 
	  	//-->
		</script>
</HTML>

