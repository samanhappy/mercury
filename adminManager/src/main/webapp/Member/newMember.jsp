<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>������Ա</title>
		<LINK href="../Css/Common.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="prototype1.4.js"></script>
		<SCRIPT language="javascript">
		
		<!--
			
			
			function SaveData(){
			
				if(document.all("userid").value=="")
				{
					alert("�������¼����");
					document.all("userid").focus();
					return;
				}
				
				if(document.all("username").value=="")
				{
					alert("��������ʵ������");
					document.all("username").focus();
					return;
				}
				
				if(document.all("password").value=="")
				{
					alert("���������룡");
					document.all("password").focus();
					return;
				}
				if(document.all("password").value.length<6)
				{
					alert("������ڼ򵥣����������룡");
					document.all("password").focus();
					return;
				}
				if(document.all("passwordConfirm").value != document.all("password").value)
				{
					alert("������������벻ͬ�����������룡");
					document.all("password").focus();
					return;
				}
				
					if(document.all("telephone").value!=0 && !document.all("telephone").value.match(/^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/)){
				    alert("��������ȷ��ʽ�ĵ绰���룡");
				    document.all("telephone").focus();
				    return;
				}
					if(document.all("mobile").value.length!=0 && document.all("mobile").value.length!=9){
				    alert("��������ȷ��ʽ���ֻ����룡");
				    document.all("mobile").focus();
				    return;
				}
					if(document.all("email").value!=0 &&!document.all("email").value.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/)){
				    alert("��������ȷ��ʽ�������ַ��");
				    document.all("email").focus();
				    return;
				}
				
				
				Form1.submit();
			}
			
			
			
			function CloseMe(){
				
				window.close();
			}
			function dept(){
               var url = "/boco/AjaxServlet";
               new Ajax.Request(url,{method:"post",onComplete:process});
             } 
            
		    function process(v){
		      var dom = v.responseXml;
		      var deptnames = dom.getElementsByTagName("deptname");
		      for(var i=0;i<deptnames.length;i++){
		        var option = new Option();
		        option.text = deptnames[i].firstChild.nodeValue;
		        option.value = deptnames[i].firstChild.nodeValue;
		        document.all.deptname.options.add(option);
		      }
		      
		    }	
			
		//-->
		</SCRIPT>
	</HEAD>
	<body class="PopupWinBody1" onload="dept()">
		<form id="Form1" action="../addUser.do" method="post" runat="server">
			<TABLE style="HEIGHT: 32px" cellSpacing="0" cellPadding="0"
				align="left" background="../images/bg_top.gif" border="0">
				<TR>
					<td width="20">
						<IMG src="../images/TopMenu/icon_arrow.gif" width="20" height="20">
					</td>
					<TD style="WIDTH: 125" noWrap>
						&nbsp;&nbsp;��Ա��Ϣ
					</TD>
					<TD align="right" width="552"></TD>
					<td width="284"></td>
				</TR>
			</TABLE>
			<p>
				<br><br><br><br>
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
												��&nbsp;¼&nbsp;����
											</td>
											<td align="left">
												<input type="text" name="userid" id="userid" size="20"
													Class="Text">
											</td>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��ʵ������
											</td>
											<td align="left">
												<input type="text" name="username" id="username" size="20"
													Class="Text">
											</td>

										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��&nbsp;&nbsp;&nbsp;&nbsp;�룺
											</td>
											<td align="left">
												<input type="password" name="password" id="password"
													size="20" Class="Text">
											</td>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												ȷ�����룺
											</td>
											<td align="left">
												<input type="password" name="passwordConfirm"
													id="passwordConfirm" size="20" Class="Text">
											</td>

										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												�������ţ�
											</td>
											<td style="HEIGHT: 35px" align="left">
												<Select name="deptname" id="deptname" Class="Text" Width="335">
													<option value="">
														-��ѡ����-
													</option>
												</Select>
											</td>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��Ա���ͣ�
											</td>
											<td style="HEIGHT: 35px" align="left">
												<Select name="usertype" Class="Text" Width="335">
													<option value="">
														-��ѡ��-
													</option>
													<option value="һ���û�">
														һ���û�
													</option>
													<option value="����Ա">
														����Ա
													</option>
												</Select>
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��&nbsp;&nbsp;&nbsp;&nbsp;����
											</td>
											<td align="left">
												<input type="text" name="telephone" id="telephone" size="20"
													Class="Text">
											</td>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��&nbsp;&nbsp;&nbsp;&nbsp;����
											</td>
											<td align="left">
												<input type="text" name="mobile" id="mobile" size="20"
													Class="Text">
											</td>

										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��&nbsp;&nbsp;&nbsp;&nbsp;�棺
											</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="facsimile" Class="Text" size="20"
													Width="100%">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												�����ʼ���
											</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="email" Class="Text" size="50"
													Width="100%">
											</td>
										</tr>
										<tr>
											<td style="HEIGHT: 35px" align="right" width="100"
												height="35">
												��&nbsp;&nbsp;&nbsp;&nbsp;ע��
											</td>
											<td style="HEIGHT: 35px" align="left" colSpan="3">
												<input type="text" id="remark" size="50" Class="Text"
													Width="100%">
											</td>
										</tr>
									</table>
									<P align="center">
										<INPUT class="popupbutton" id="btnSave"
											onclick="javascript:SaveData()" type="button" value="����">
										&nbsp;
										<INPUT class="popupbutton" id="btnClose"
											onclick="javascript:CloseMe()" type="button" value="�ر�"
											name="btnClose">
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
