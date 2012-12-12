<%@ page language="java" import="java.util.*" pageEncoding="GBk"%>
<%@include file="../common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>
			更新部门
		</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
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
			function AddNew(){
			
				jscomNewOpenBySize('<%=request.getContextPath()%>/newdeptpage.do','NewDept',600,350);
				window.close();
			}
		//-->
		</SCRIPT>
	</HEAD>
	<body class="PopupWinBody1">
		<form id="Form1" action="<%=request.getContextPath()%>/updateDept.do?deptid=${dept.deptid }" method="post" onsubmit="true">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0" align="left"
				background="images/bg_top.gif" border="0">
				<TR>
					<td width="20">
                    <IMG src="images/TopMenu/icon_arrow.gif" width="20" height="20"></td>
					<TD style="WIDTH: 125" noWrap>&nbsp;&nbsp;部门信息
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
												<input type="text" name="deptname" id="txtDeptName" size="50" Class="Text" value="${dept.deptname}">
											</td>
											
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">管理人：</td>
											<td style="HEIGHT: 35px" align="left" >
											 	<select name="select" Class="Text" Width="335">
														<logic:iterate id="user" name="userList">
															<option value="${user.username}">
																${user.username}
															</option>
														</logic:iterate>
													</select>
											 </td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">电     话：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="telephone"  id="txtTel" Class="Text" Width="100%" value="${dept.telephone}">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">手     机：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="mobile"  id="txtMobile" Class="Text" Width="100%" value="${dept.mobile}">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">传     真：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="facsimile"  id="txtFax" Class="Text" Width="100%" value="${dept.facsimile}">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">备      注：</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text"  name="remark" id="txtRemark" size="50" Class="Text" Width="100%" value="${dept.remark}">
											</td>
										</tr>
									</table>
									<P align="center">
									<INPUT class="popupbutton" id="btnSave" onclick="SaveData()" type="button" value="保存">&nbsp;
									<INPUT class="popupbutton" id="btnNew" onclick="AddNew()" type="button" value="新增">&nbsp;
									<INPUT class="popupbutton" id="btnClose" onclick="CloseMe()" type="button" value="关闭" name="btnClose">
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