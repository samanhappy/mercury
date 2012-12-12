<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>人员列表</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<SCRIPT language="javascript">
		<!--
			
			function ConfigureMemberRole(id){
				jscomNewOpenBySize('<%=request.getContextPath()%>/configMemberRoleJsp.do?userid='+id,'ConfigureMemberRole',600,350);
			}
			
			function RefreshForm(){
				var userid = document.all('userid').value;
				var username = document.all('username').value;
				var deptname = document.all('deptname').value;
				jscomNewOpenBySize('<%=request.getContextPath()%>/queryMember.do?userid='
					+userid+'&username='+username+'&deptname='+deptname,
					'RefreshForm',600,350);
			}
		//-->
		</SCRIPT>
	</HEAD>
	<BODY>

		<form id="frmCommon">
			<!-- 中部表格 -->
			<table height="350" cellSpacing="0" cellPadding="0" width="80%"
				border="0">
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									人员列表
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>
								<td width="73"></td>
							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">

							<TR height="50">
								<td nowrap>
									登录名：
								</td>
								<td nowrap width="100">
									<input class="text" id="userid" type="text" style="WIDTH: 100%"
										name="userid">
								</td>
								<td nowrap>
									&nbsp;&nbsp;姓名：
								</td>
								<td nowrap width="100">
									<input class="text" id="username" type="text"
										style="WIDTH: 100%" name="username">
								</td>
								<td noWrap>
									&nbsp;&nbsp;所属部门：
								</td>
								<td>
									<SELECT id="deptname" name="deptname">
										<OPTION value="" selected>
											-所有-
										</OPTION>
										<logic:iterate id="dept" name="deptlist">
											<OPTION value="${dept.deptname}">
												${dept.deptname}
											</OPTION>
										</logic:iterate>
									</SELECT>
								</td>
								<td noWrap>
									&nbsp;&nbsp;
									<input class="button" id="btnSearch" type="button" value="查询"
										name="btnSearch" onclick="RefreshForm()">
								</td>
								<td width="100%"></td>
							</TR>

						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="20" background="images/b2_3.gif" height="18">
									<IMG height="18" src="images/b2_6.gif" width="1">
								</td>
								<td vAlign="bottom" background="images/b2_3.gif" width="100">
									登录名
								</td>
								<td width="5">
									<IMG height="18" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									姓名
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="180"
									background="images/b2_3.gif">
									所属部门
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="360"
									background="images/b2_3.gif">
									当前角色
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td width="60" background="images/b2_3.gif" height="18">
									<IMG height="18" src="images/b2_6.gif" width="1">
								</td>
								<td width="1">
									<IMG height="18" src="images/b2_5.gif" width="1">
								</td>
							</tr>
							<logic:iterate id="user" name="userlist">
								<tr bgColor="white">
									<td height="5"></td>
									<td vAlign="bottom">
										${user.userid}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${user.username}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${user.deptname}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${user.roles}
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center" width="60">
										<INPUT class="popupbutton"
											onclick="ConfigureMemberRole(${user.userid})" type="button"
											value="配置">
									</td>
									<td width="1"></td>
								</tr>
							</logic:iterate>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>

