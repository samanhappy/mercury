<%@ page language="java" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>
			���Ļ���
		</title>
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="Js/prototype1.4.js"></script>
		<SCRIPT language="javascript">
		<!--
				
			function SaveData(){
				if(document.all("roomname").value=="")
				{
					alert("������������ƣ�");
					document.all("roomname").focus();
					return;
				}else if(document.all("deptname").value=="0")
				{
					alert("��ѡ���������ţ�");
					document.all("deptname").focus();
					return;
				}else if(document.all("mgrname").value=="0")
				{
					alert("��ѡ�����ˣ�");
					document.all("mgrname").focus();
					return;
				}			
				Form1.submit();
			}
	
			function CloseMe(){			
				window.close();
			}
			
			function f(){
				var deptname = document.all('deptname').value;
				new Ajax.Request("GetUsersServlet?deptname="+deptname,{method:"POST",onComplete:change});
			}
			
			function change(v){
  				document.all('message').innerHTML = v.responseText;
  			}
  			
  			function f1(){
				var deptname = document.all('deptname').value;
				new Ajax.Request("GetUsersServlet?deptname="+deptname,{method:"POST",onComplete:change1});
			}
			
			function change1(v){
  				document.all('message').innerHTML = v.responseText;
  				var mgrname = document.all('mgrname');
				mgrname.value = document.all('mname').value;
  			}
		//-->
		</SCRIPT>
	</HEAD>
	<body class="PopupWinBody1">
		<form id="Form1" method="post" runat="server" action="<%=request.getContextPath()%>/updateRoom.do">
			<input type="hidden" name="roomid" value="${room.roomid}">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0" align="left"
				background="images/bg_top.gif" border="0">
				<TR>
					<td width="20">
                    <IMG src="images/TopMenu/icon_arrow.gif" width="20" height="20"></td>
					<TD style="WIDTH: 125" noWrap>&nbsp;&nbsp;������Ϣ
					</TD>
					<TD align="right" width="552"></TD>
					<td width="284"></td>
				</TR>
			</TABLE>
			��<p>
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
											<td style="HEIGHT: 35px" align="right" width="100" height="35">�������ƣ�</td>
											<td  align="left"  width="374px" >
												<input type="text" name="roomname" id="roomname" size="50" Class="Text" value="${room.roomname}">
											</td>
											
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">�������ţ�</td>
											<td style="HEIGHT: 35px" align="left" >
											 	<Select name="deptname" Class="Text" Width="335" onchange="f();">
											 		<option value="0">��ѡ����</option>
											 		<logic:iterate id="dept" name="deptlist">
											 			<option value="${dept.deptname}">${dept.deptname}</option>
											 		</logic:iterate>
											 	</Select>
											 </td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">�����ˣ�</td>
											<td style="HEIGHT: 35px" align="left" >
											 	<div id="message">
											 	<Select name="mgrname" id="mgrname" Class="Text" Width="335">
											 		<option value="0">��ѡ������</option>
											 	</Select>
											 	</div>
											 </td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">��     ַ��</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="address" id="address" value="${room.address}" Class="Text" size="50" Width="100%">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">��      ע��</td>
											<td style="HEIGHT: 35px" align="left" >
												<input type="text" name="remark" id="remark" value="${room.remark}" size="50" Class="Text" Width="100%">
											</td>
										</tr>
									</table>
									<P align="center"><INPUT class="popupbutton" id="btnSave" onclick="javascript:SaveData()" type="button" value="����">&nbsp;<INPUT class="popupbutton" id="btnClose" onclick="javascript:CloseMe()" type="button" value="�ر�" name="btnClose">
									</P>
									<P align="center"></P>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" name="dname" value="${room.deptname}">
			<input type="hidden" name="mname" value="${room.mgrname}">
			<script type="text/javascript">
				var deptname = document.all('deptname');
				deptname.value = document.all('dname').value;
				f1();
			</script>
		</form>
	</body>
</HTML>