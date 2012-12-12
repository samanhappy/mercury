<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>角色列表</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="Js/prototype1.4.js"></script>
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<SCRIPT language="javascript">
		<!--
			function CreateRole(){
				jscomNewOpenBySize('<%=request.getContextPath()%>/newRoleJsp.do','NewRole',600,250);
			}
			function UpdateRole(id){
				jscomNewOpenBySize('<%=request.getContextPath()%>/configRoleJsp.do?roleid='+id,'ConfigureRole',600,350);
			}
			
			function DeleteRole(){
				
				var sSelectedIDs=jcomGetAllSelectedRecords("roleids");
				
				if(sSelectedIDs=="")
				{
					alert("请选择记录！");
					return;
				}
				if(!window.confirm("您确定要删除角色" + sSelectedIDs + "吗？" )) 
					return;
				frmCommon.submit();
			}
			
			
		//-->
		</SCRIPT>
	</HEAD>
	<BODY>

		<form id="frmCommon" action="<%=request.getContextPath()%>/deleteRole.do">
			<!-- 中部表格 -->
			<table height="350" cellSpacing="0" cellPadding="0" width="80%"
				border="0">
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									角色列表
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="20" background="images/b2_3.gif" height="18">
									<IMG height="18" src="images/b2_6.gif" width="1">
								</td>
								<td vAlign="bottom" align="center" background="images/b2_3.gif" width="50">
									角色名称
								</td>
								<td width="5">
									<IMG height="18" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="80"
									background="images/b2_3.gif">
									所属模块
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									对应权限
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
							<logic:iterate id="role" name="rolelist">
								<tr bgColor="white" height="22">
									<td height="5">
										<input type="checkbox" name="roleids" id="roleids"
											value="${role.roleid}">
									</td>
									<td vAlign="bottom">
										${role.rolename}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${role.modulename}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${role.popedoms}
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center" width="80">
										<INPUT class="popupbutton" onclick="UpdateRole(${role.roleid})"
											type="button" value="配置">
									</td>
									<td width="1"></td>
								</tr>
							</logic:iterate>

						</table>
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>

								<td height="30" vAlign="bottom">
									<INPUT class="popupbutton" onclick="CreateRole()" type="button"
										value="新建">
									&nbsp;
									<INPUT class="popupbutton" onclick="DeleteRole()" type="button"
										value="删除">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>

