<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
  <HEAD>
		<title>MainTop</title>
		<LINK href="../Css/TopMenu.css" type="text/css" rel="stylesheet">
		<script language="javascript">
		<!--
			function MM_swapImgRestore() { //v3.0
				 var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
			}

			function MM_preloadImages() { //v3.0
				var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
				var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
				if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
			}

			function MM_findObj(n, d) { //v4.01
				var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
				d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
				if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
					for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
				if(!x && d.getElementById) x=d.getElementById(n); return x;
			}

			function MM_swapImage() { //v3.0
				var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
				if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
			}
			
			function gotoURL(url){
				window.open(url,'Main','') ;
			}

			function quit(){
				top.window.close() ;
			}

			function closeIt(){
				try{
					event.returnValue = "【你确认要退出本系统吗？】";
				}
				catch(e){
				};
			}

			var oDetectNewMessage = "";
			var intRetryCounter = 0;
			//oDetectNewMessage = setInterval(detectNewMessage,30000);

			function isNewMessageLoaded(){
				try{
					if (top.Message.isLoaded()){
						return true;
					}
				}
				catch(e){
				}
				return false;
			}

			function resetDetectMessage(intInterval){
				clearInterval(oDetectNewMessage);
				oDetectNewMessage = setInterval(detectNewMessage,intInterval);
			}

			function logoutUser(){
				if(!confirm("您确定要注销吗？")) return;
				top.location="<%=request.getContextPath()%>/index.do";
				
<%--				if(IsGoToOther==false)--%>
<%--				{--%>
<%--				--%>
<%--					document.all("hidCommand").value="LogOut";--%>
<%--					Form1.submit();--%>
<%--				}--%>
				
				//top.location="../index.aspx";
				
			}

			function popNewMsgCenter(bIsPop){

			}
				
			var objWinClientService;
			var objWinJXC;
			function gotoClientService(){
				if(objWinClientService!=null){
					if(objWinClientService.closed != true){
						objWinClientService.focus();
						return;
					}
				}
				objWinClientService = window.open( '../gotoClientService.asp?TimeID=' + Math.random() , 'ClientHelp' , 'height='+ new String(screen.availHeight-28) + ',top=0,left=0,width=' + new String(screen.availWidth-10) + ',location=no,menubar=no,resizable=no,toolbar=no,scrollbars=yes');
				objWinClientService.focus();
			}
			
			var IsGoToOther=false;
			function GoToMain()
			{
				top.Main.location="Main.jsp";
				Form1.HidClientHelpUrl.value="";	
			}
			
			function GoToBackupFrame()
			{
				IsGoToOther=true;
				top.TopMenu.location="BackTopMenu.html";
				top.fraLeft.location="BackLeftMenu.html";
				top.Bottom.cols = '115,*';
				top.Main.location="BackMain.aspx";
			}
			
			function CloseSystem()
			{
				if(IsGoToOther==false)
				{
					document.all("hidCommand").value="CloseSystem";
					document.all("Form1").submit();
				}
			}
			function helpsearch()
			{
				var clienturl;
				clienturl=Form1.HidClientHelpUrl.value;
				if(clienturl=="")
				{
					window.open('../Help/client/index.html');
				}
				else
				{
					window.open(clienturl);
				}
				Form1.submit();
			}
		//-->
		</script>
</HEAD>
	<body onunload="CloseSystem()">
		<form id="Form1" method="post">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="3" rowspan="4" bgcolor="#36a9ce"></td>
					<td height="74" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="670" height="74" background="../images/top_01.gif" style="padding-top:0;padding-left:0;padding-right:0;padding-top:0"><img src="../images/top_01.gif" width="670" height="74" /></td>
								<td valign="top" background="../images/top_02.gif"> 
									<table width="264" border="0" align="right" cellpadding="0" cellspacing="0">
										<tr>
											<td width="28" height="33" background="../images/topicobg_02.gif"><div align="left"><img src="../images/topicobg_01.gif" width="28" height="33"></div>
											</td>
											<td background="../images/topicobg_02.gif" class="font9">
												<table border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
														<td nowrap>
															<a href="#" onclick="javascript:GoToMain();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image3','','../images/ico_home_hot.gif',1)">
																<img src="../images/ico_home.gif" name="Image3" width="45" height="19" border="0" id="Image3"></a>
														</td>
														
														
														<td width="5"></td>
														<td nowrap>
															<a href="#" onclick="javascript:logoutUser();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image6','','../images/ico_exit_hot.gif',1)">
																<img src="../images/ico_exit.gif" name="Image6" width="48" height="19" border="0" id="Image6"></a>
														</td>
														<td width="5"></td>
													</tr>
												</table>
											</td>
										</tr>
									</table></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<INPUT id="hidCommand" style="WIDTH: 32px; HEIGHT: 22px" type="hidden" size="1" name="hidCommand">
				<INPUT id="HidClientHelpUrl" style="WIDTH: 32px; HEIGHT: 22px" type="hidden" size="1" name="HidClientHelpUrl">
										
		</form>
	</body>
</HTML>
