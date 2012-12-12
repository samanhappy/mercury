<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>
			新增角色
		</title>
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<SCRIPT language="javascript">
		<!--
			
			
			function SaveData(){
			
				if(document.all("rolename").value=="")
				{
					alert("请输入角色名称！");
					document.all("rolename").focus();
					return;
				}else if(document.all("modulename").value=="0")
				{
					alert("请选择所属模块！");
					document.all("modulename").focus();
					return;
				}
				
				Form1.submit();
			}
			
			
			
			function CloseMe(){
				
				window.close();
			}
		//-->
		</SCRIPT>
	</HEAD>
	<body class="PopupWinBody1">
		<form id="Form1" method="post" runat="server" action="<%=request.getContextPath()%>/newRole.do">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0" align="left"
				background="images/bg_top.gif" border="0">
				<TR>
					<td width="20">
                    <IMG src="images/TopMenu/icon_arrow.gif" width="20" height="20"></td>
					<TD style="WIDTH: 125" noWrap>&nbsp;&nbsp;角色信息
					</TD>
					<TD align="right" width="552"></TD>
					<td width="284"></td>
				</TR>
			</TABLE>
			　<p>
			<br>
			</p>
			<table width="260" border="0" cellpadding="0" cellspacing="1" bgcolor="#d0d0d0" align="center">
				<tr>
					<td bgcolor="#ffffff" width="657">
						<table  border="0" cellpadding="0" cellspacing="4" bgcolor="#f2f9fb">
							<tr>
								<td bgcolor="#ffffff" style="PADDING-RIGHT:10px;PADDING-LEFT:10px;PADDING-TOP:0px" width="482">
									<table  cellSpacing="0" cellPadding="0" width="474" align="center">
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">角色名称：</td>
											<td  align="left"  width="374px" >
												<input type="text" name="rolename" id="rolename" size="50" Class="Text" >
											</td>
											
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">所属模块：</td>
											<td style="HEIGHT: 35px" align="left" >
											 	<Select name="modulename" id="modulename" Class="Text" Width="335">
											 		<option value="0">-请选择-</option>
											 		<logic:iterate id="modulename" name="modulenamelist">
											 			<option value="${modulename}">${modulename}</option>
											 		</logic:iterate>
											 	</Select>
											 </td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" id="txtRemark" size="50" Class="Text" Width="100%">
											</td>
										</tr>
									</table>
									<P align="center"><INPUT class="popupbutton" id="btnSave" onclick="javascript:SaveData()" type="button" value="保存">&nbsp;<INPUT class="popupbutton" id="btnClose" onclick="javascript:CloseMe()" type="button" value="关闭" name="btnClose">
									</P>
									<P align="center"></P>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</form>
	</body>
</HTML>
