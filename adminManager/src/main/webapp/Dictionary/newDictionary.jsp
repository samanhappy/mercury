<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>
			新增字典类型
		</title>
		<LINK href="../Css/Common.css" type="text/css" rel="stylesheet">
		<SCRIPT language="javascript">
		<!--
			
			
			function addDic(){
			
				if(document.all("dictypename").value=="")
				{
					alert("请输入字典类型名称！");
					document.all("dictypename").focus();
					return false;
				}
				
				if(document.all("notation").value=="")
				{
					alert("请输入注释！");
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
					<TD style="WIDTH: 125" noWrap>&nbsp;&nbsp;人员信息
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
											<td style="HEIGHT: 35px" align="right" width="100" height="35">字典类型名称：</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="dictypename" name="dictypename" Class="Text" size="50" Width="100%">
											</td>
										</tr>
											<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">所&nbsp;属&nbsp;&nbsp;模&nbsp;块：</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<select id="modulename" name="modulename">
												  <option value="">-选择模块-</option>
												  <option value="部门管理" >-部门管理-</option>
												  <option value="机房管理" >-机房管理-</option>
												  <option value="人员管理" >-人员管理-</option>
												  <option value="权限管理" >-权限管理-</option>
												  <option value="日志管理" >-日志管理-</option>
												  <option value="字典管理" >-字典管理-</option>
												</select>
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100" height="35">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;释：</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="notation" name="notation" size="50" Class="Text" Width="100%">
											</td>
										</tr>
									</table>
									<P align="center"><INPUT class="popupbutton" id="btnSave" onclick="javascript:addDic()" type="button" value="保存">&nbsp;<INPUT class="popupbutton" id="btnClose" onclick="javascript:CloseMe()" type="button" value="关闭" name="btnClose">
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