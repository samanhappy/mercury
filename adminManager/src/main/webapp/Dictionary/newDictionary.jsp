<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>
			�����ֵ�����
		</title>
		<LINK href="../Css/Common.css" type="text/css" rel="stylesheet">
		<SCRIPT language="javascript">
		<!--
			
			
			function addDic(){
			
				if(document.all("dictypename").value=="")
				{
					alert("�������ֵ��������ƣ�");
					document.all("dictypename").focus();
					return false;
				}
				
				if(document.all("notation").value=="")
				{
					alert("������ע�ͣ�");
					document.all("notation").focus();
					return false;
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
		<form id="Form1" action="../addDic.do" method="post" runat="server">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0" align="left"
				background="../images/bg_top.gif" border="0">
				<TR>
					<td width="20">
                    <IMG src="../images/TopMenu/icon_arrow.gif" width="20" height="20"></td>
					<TD style="WIDTH: 125" noWrap>&nbsp;&nbsp;��Ա��Ϣ
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
											<td style="HEIGHT: 35px" align="right" width="100" height="35">�ֵ��������ƣ�</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="dictypename" name="dictypename" Class="Text" size="50" Width="100%">
											</td>
										</tr>
											<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">��&nbsp;��&nbsp;&nbsp;ģ&nbsp;�飺</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<select id="modulename" name="modulename">
												  <option value="">-ѡ��ģ��-</option>
												  <option value="���Ź���" >-���Ź���-</option>
												  <option value="��������" >-��������-</option>
												  <option value="��Ա����" >-��Ա����-</option>
												  <option value="Ȩ�޹���" >-Ȩ�޹���-</option>
												  <option value="��־����" >-��־����-</option>
												  <option value="�ֵ����" >-�ֵ����-</option>
												</select>
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">ע&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ͣ�</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="notation" name="notation" size="50" Class="Text" Width="100%">
											</td>
										</tr>
									</table>
									<P align="center"><INPUT class="popupbutton" id="btnSave" onclick="javascript:addDic()" type="button" value="����">&nbsp;<INPUT class="popupbutton" id="btnClose" onclick="javascript:CloseMe()" type="button" value="�ر�" name="btnClose">
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