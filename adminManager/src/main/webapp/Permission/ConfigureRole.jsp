<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>
<HTML>
	<HEAD>
		<title>角色配置</title>
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<SCRIPT language="javascript">
		<!--
						
			function SaveData(){
				if(document.all("rolename").value=="")
				{
					alert("请输入角色名称！");
					document.all("rolename").focus();
					return;
				}	
				Form1.submit();
			}
		</SCRIPT>
	</HEAD>
	<body class="PopupWinBody1">
		<form id="Form1" method="post" runat="server" action="<%=request.getContextPath()%>/configRole.do">
			<input type="hidden" name="roleid" value="${role.roleid}">
			<input type="hidden" name="modulename" value="${role.modulename}">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0"
				align="left" background="images/bg_top.gif" border="0">
				<TR>
					<td width="20">
						<IMG src="images/TopMenu/icon_arrow.gif" width="20" height="20">
					</td>
					<TD style="WIDTH: 125" noWrap>
						&nbsp;&nbsp;角色配置
					</TD>
					<TD align="right" width="552"></TD>
					<td width="284"></td>
				</TR>
			</TABLE>
			
			<p>
				<br>
				<br><br>
				<br>
			</p>
			<table width="260" border="0" cellpadding="0" cellspacing="1"
				bgcolor="#d0d0d0" align="center">
				<tr>
					<td bgcolor="#ffffff" width="657">
						<table border="0" cellpadding="0" cellspacing="4"
							bgcolor="#f2f9fb">
							<tr>
								<td bgcolor="#ffffff"
									style="PADDING-RIGHT:10px;PADDING-LEFT:10px;PADDING-TOP:0px"
									width="482">
									<table cellSpacing="0" cellPadding="0" width="474"
										align="center">
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												角色名称：
											</td>
											<td align="left" width="374px">
												<input type="text" name="rolename" id="rolename"
													size="50" Class="Text" value="${role.rolename}">
											</td>

										</tr>
										<tr>
											<td height="15" colspan="2">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="1" background="images/brokenline.gif"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>

											<td style="HEIGHT: 35px" align="center" colSpan="2">
												<font color="blue">权限配置</font>
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												${role.modulename}：
											</td>
											<td style="HEIGHT: 35px" align="left">
												<table>
													<logic:iterate id="popedom" name="popedoms">
														<tr>
															<td>
																<input type="checkbox" name="pope" id="pope"
																	value="${popedom.popedom}">
																	${popedom.popedom}
															</td>
														</tr>
													</logic:iterate>
												</table>
											</td>
										</tr>
										<tr>
											<td height="15" colspan="4">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="1" background="images/brokenline.gif"></td>
													</tr>
												</table>
											</td>
										</tr>

									</table>
									<P align="center">
										<INPUT class="popupbutton" id="btnSave" onclick="SaveData()"
											type="button" value="保存">
										&nbsp;&nbsp;
										<INPUT class="popupbutton" id="btnClose" onclick="CloseMe()"
											type="button" value="关闭" name="btnClose">
									</P>
									<P align="center"></P>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<logic:iterate id="pope" name="popelist">
			<input type="hidden" name="popelist" id="popelist" value="${pope}">
		</logic:iterate>												
		<script type="text/javascript">
			var popes = document.all('pope');
			var popelist = document.all('popelist');
			if(popelist != null){		
				for(var j = 0; j < popelist.length; j++){
					for(var i = 0; i < popes.length; i++){
						if(popelist[j].value == popes[i].value)
							popes[i].checked = true;
					}
				}
			}
			
		</script>
	</body>
</HTML>
