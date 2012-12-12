<%@ page language="java" import="java.util.*" pageEncoding="GBk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>
			新增部门
		</title>
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<SCRIPT language="javascript">
		<!--
			
			
			function SaveData(){
			
				if(document.all("txtDeptName").value=="")
				{
					alert("请输入部门名称！");
					document.all("txtDeptName").focus();
					return;
				}
				
				Form1.submit();
				window.close();
			}
			
			
			
			function CloseMe(){
				
				window.close();
			}
		//-->
		</SCRIPT>
	</HEAD>
	<body class="PopupWinBody1">
		<form id="Form1" method="post" action="<%=request.getContextPath()%>/newdept.do">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0" align="left"
				background="images/bg_top.gif" border="0">
				<TR>
					<td width="20">
                    <IMG src="images/TopMenu/icon_arrow.gif" width="20" height="20"></td>
					<TD style="WIDTH: 125" >&nbsp;&nbsp;部门信息
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
											<td style="HEIGHT: 35px" align="right" width="100" height="35">部门名称：</td>
											<td  align="left"  width="374px" >
												<input type="text" name="deptname" id="txtDeptName" size="50" Class="Text"  >
											</td>
											
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">管理人：</td>
											<td style="HEIGHT: 35px" align="left" >
											 	<Select name="selMember" Class="Text" Width="335">
											 		<option value="0">请选择管理人</option>
											 		<option value="1">员工A</option>
											 		<option value="2">员工B</option>
											 	</Select>
											 </td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">电     话：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="telephone" id="txtTel" Class="Text" Width="100%">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">手     机：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text"  name="mobile" id="txtMobile" Class="Text" Width="100%">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">传     真：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="facsimile" id="txtFax" Class="Text" Width="100%">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">备      注：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="remark" id="txtRemark" size="50" Class="Text" Width="100%">
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