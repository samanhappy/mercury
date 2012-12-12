<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--<jsp:directive.page import="com.cmcc.emgr.domain.TUser"/>-->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>
			更改人员
		</title>
		<LINK href="../Css/Common.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="Js/prototype1.4.js"></script>
		<script type="text/javascript" src="Member/prototype1.4.js"></script>
		<script type="text/javascript" src="prototype1.4.js"></script>
		<SCRIPT language="javascript">
		<!--
			
			
			function SaveData(){
			
				if(document.all("userid").value=="")
				{
					alert("请输入登录名！");
					document.all("userid").focus();
					return;
				}
				
				if(document.all("username").value=="")
				{
					alert("请输入真实姓名！");
					document.all("username").focus();
					return;
				}
				
				if(document.all("password").value=="")
				{
					alert("请输入密码！");
					document.all("password").focus();
					return;
				}
				
				if(document.all("passwordConfirm").value != document.all("password").value)
				{
					alert("两次输入的密码不同，请重新输入！");
					document.all("password").focus();
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
		<form id="Form1" action="../updateUser.do" method="post" runat="server">
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