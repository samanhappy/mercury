<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>机房列表</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="Js/prototype1.4.js"></script>
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<SCRIPT language="javascript">
		<!--
			function CreateRoom(){
				jscomNewOpenBySize('<%=request.getContextPath()%>/addroomjsp.do','NewRoom',600,350);
			}
			function UpdateRoom(id){
				jscomNewOpenBySize('<%=request.getContextPath()%>/updateRoomJsp.do','UpdateRoom',600,350);
			}
			function RefreshForm(){
				var txtRoomName = document.all('txtRoomName').value;
				var txtDeptName = document.all('txtDeptName').value;
				var txtMgrName = document.all('txtMgrName').value;
				var txtAddress = document.all('txtAddress').value;
				jscomNewOpenBySize('<%=request.getContextPath()%>/queryroom.do?txtRoomName='
					+txtRoomName+'&txtDeptName='+txtDeptName+'&txtMgrName='+txtMgrName+'&txtAddress='+txtAddress,
					'RefreshForm',600,350);
			}
			function DeleteRoom(){
				
				var sSelectedIDs=jcomGetAllSelectedRecords("roomids");
				
				if(sSelectedIDs=="")
				{
					alert("请选择记录！");
					return;
				}
				if(!window.confirm("您确定要删除机房" + sSelectedIDs + "吗？" )) return;		
				frmCommon.submit();
			}
			
			function f(){
				var deptname = document.all('txtDeptName').value;
				new Ajax.Request("GetUsersServlet?flag=query&deptname="+deptname,{method:"POST",onComplete:change});
			}
			
			function change(v){
  				document.all('message').innerHTML = v.responseText;
  			}
			
		//-->
		</SCRIPT>
	</HEAD>
	<BODY>

		<form id="frmCommon"
			action="<%=request.getContextPath()%>/deleteRoom.do">
			<!-- 中部表格 -->
			<table height="350" cellSpacing="0" cellPadding="0" width="80%"
				border="0">
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									机房列表
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">

							<TR height="50">
								<td nowrap>
									机房名称：
								</td>
								<td nowrap width="100">
									<input class="text" name="txtRoomName" id="txtRoomName"
										type="text" style="WIDTH: 100%" name="txtRoomName">
								</td>
								<td noWrap>
									&nbsp;&nbsp;所属部门：
								</td>
								<td>
									<SELECT id="txtDeptName" name="txtDeptName" onchange="f();">
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
									&nbsp;&nbsp;负责人：
								</td>
								<td>
									<div id="message">
										<SELECT id="txtMgrName" name="txtMgrName">
											<OPTION value="" selected>
												-所有-
											</OPTION>
										</SELECT>
									</div>
								</td>
								<td nowrap>
									&nbsp;&nbsp;地址：
								</td>
								<td nowrap width="100">
									<input class="text" id="txtAddress" type="text"
										style="WIDTH: 100%" name="txtAddress">
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
									机房名称
								</td>
								<td width="5">
									<IMG height="18" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="60"
									background="images/b2_3.gif">
									负责人
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									所属部门
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									地址
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="300"
									background="images/b2_3.gif">
									备注
								</td>
								<td width="1">
									<IMG height="18" src="images/b2_5.gif" width="1">
								</td>
							</tr>
							<logic:iterate id="room" name="roomlist">
								<tr bgColor="white">
									<td height="5">
										<input type="checkbox" name="roomids" id="roomids"
											value="${room.roomid}">
									</td>
									<td vAlign="bottom">
										<a href="<%=request.getContextPath()%>/updateRoomJsp.do?roomid=${room.roomid}">${room.roomname}</a>
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${room.mgrname}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${room.deptname}
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										${room.address}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${room.remark}
									</td>
									<td width="1"></td>
								</tr>
							</logic:iterate>
						</table>
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>

								<td height="30" vAlign="bottom">
									<INPUT class="popupbutton" onclick="CreateRoom()" type="button"
										value="新建">
									&nbsp;
									<INPUT class="popupbutton" onclick="DeleteRoom()" type="button"
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

