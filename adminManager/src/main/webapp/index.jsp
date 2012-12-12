<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<title>PushMail服务器运行维护系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<!--
BODY {
	COLOR: #000000
}

.font10 {
	font-family: "新宋体", "宋体", "黑体", "Arial Narrow", "Verdana";
	font-size: 10pt;
	text-decoration: none;
}

a:link {
	text-decoration: none
}

a:visited {
	text-decoration: none
}

a:hover {
	text-decoration: underline;
	color: #99FFFF
}

INPUT.smallInput {
	BORDER-RIGHT: 1px solid;
	BORDER-TOP: 1px solid;
	BACKGROUND: #ffffff;
	BORDER-LEFT: 1px solid;
	BORDER-BOTTOM: 1px solid;
	HEIGHT: 16px;
}
-->
</style>
<script type="text/javascript">
<!--
	function MM_swapImgRestore() { //v3.0
		var i, x, a = document.MM_sr;
		for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
			x.src = x.oSrc;
	}

	function MM_preloadImages() { //v3.0
		var d = document;
		if (d.images) {
			if (!d.MM_p)
				d.MM_p = new Array();
			var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
			for (i = 0; i < a.length; i++)
				if (a[i].indexOf("#") != 0) {
					d.MM_p[j] = new Image;
					d.MM_p[j++].src = a[i];
				}
		}
	}

	function MM_findObj(n, d) { //v4.01
		var p, i, x;
		if (!d)
			d = document;
		if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
			d = parent.frames[n.substring(p + 1)].document;
			n = n.substring(0, p);
		}
		if (!(x = d[n]) && d.all)
			x = d.all[n];
		for (i = 0; !x && i < d.forms.length; i++)
			x = d.forms[i][n];
		for (i = 0; !x && d.layers && i < d.layers.length; i++)
			x = MM_findObj(n, d.layers[i].document);
		if (!x && d.getElementById)
			x = d.getElementById(n);
		return x;
	}

	function MM_swapImage() { //v3.0
		var i, j = 0, x, a = MM_swapImage.arguments;
		document.MM_sr = new Array;
		for (i = 0; i < (a.length - 2); i += 3)
			if ((x = MM_findObj(a[i])) != null) {
				document.MM_sr[j++] = x;
				if (!x.oSrc)
					x.oSrc = x.src;
				x.src = a[i + 2];
			}
	}
	function InitForm() {
		MM_preloadImages('images/btn_login_hot.gif');
		try {
			document.all("txtAccount").focus();
		} catch (e) {
		}
	}

	function Register() {
		window.location = "user/UserRegister.aspx";
	}
	function ForgetPassword() {
		var account = Form1.txtAccount.value;
		if (account == "" || account == null) {
			alert('请输入帐号，以便找回您的密码');
			return;
		}
		document.all("hidCommand").value = "SearchPassWord";
		Form1.submit();
		return false;
	}
	function Login() {
		var account = Form1.txtAccount.value;
		var password = Form1.txtPassword.value;
		if (account == "" || account == null) {
			alert('请输入帐号!');
			document.all("txtAccount").focus();
			return false;
		} else if (password == "" || password == null) {
			alert('请输入密码!');
			document.all("txtPassword").focus();
			return false;
		}
		document.all("username").value = account;
		document.all("password").value = password;
		Form1.submit();
		return false;
	}
	function EnterDown() {
		if (event.keyCode == 13)
			Login();
	}
//-->
</script>
<style type="text/css">
<!--
a:link {
	color: #006699;
}

a:visited {
	color: #006699;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}
-->
</style>
</HEAD>
<body leftmargin="0" topmargin="0" onload="InitForm()"
	onkeydown="EnterDown()">
	<s:form id="Form1" action="login">
		<s:hidden id="username" name="username"></s:hidden>
		<s:hidden id="password" name="password"></s:hidden>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<table width="100%" height="100" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/pic_01.gif" width="325" height="95" /></td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="325"><img src="images/pic_02.gif" width="325"
											height="194" /></td>
										<td valign="top" background="images/bg.gif">
											<table width="100%" height="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td valign="top"><img src="images/pic_03.gif"
														height="82" /></td>
												</tr>
												<tr>
													<td height="112" valign="middle"
														background="images/pic_04.gif">
														<table width="260" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tr>
																<td width="179">
																	<table width="180" border="0" align="center"
																		cellpadding="0" cellspacing="0">
																		<tr>
																			<td width="50" class="font10"
																				style="padding-top: 3; padding-left: 0; padding-right: 0; padding-top: 3">
																				<div align="left">
																					帐 号 <strong>:</strong>
																				</div>
																			</td>
																			<td width="100">
																				<div align="center">
																					<input name="txtAccount" id="txtAccount"
																						class="smallInput" type="text"
																						style="WIDTH: 100px; HEIGHT: 20px" tabindex="1" />
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2" height="5"></td>
																		</tr>
																		<tr>
																			<td width="50" class="font10"
																				style="padding-top: 3; padding-left: 0; padding-right: 0; padding-top: 3">
																				<div align="left">
																					密 码 <strong>:</strong>
																				</div>
																			</td>
																			<td width="100">
																				<div align="center">
																					<input type="password" name="txtPassword"
																						id="txtPassword" class="smallInput" type="text"
																						style="WIDTH: 100px; HEIGHT: 20px" tabindex="2" />
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2" height="5"></td>
																		</tr>
																		<tr>
																			<td class="font10"
																				style="padding-top: 3; padding-left: 0; padding-right: 0; padding-top: 3">
																			</td>
																			<td class="font10" width="100" height="20">
																				<div>
																					<s:property value="message"></s:property>
																				</div>
																			</td>
																		</tr>
																	</table>
																</td>
																<td width="101"><a href="#" onclick="Login()"
																	onmouseout="MM_swapImgRestore()"
																	onmouseover="MM_swapImage('Image6', '', 'images/btn_login_hot.gif', 0)">
																		<img src="images/btn_login.gif" name="Image6"
																		width="42" height="44" border="0" id="Image6" />
																</a>
																	<div style="height: 25"></div></td>
															</tr>
															<tr>
																<td height="10" colspan="2"></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
										<td width="237" background="images/pic_05.gif">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!--		</form>-->
	</s:form>
	<script language="javascript">
		var sPopupMessage = "";
		if (sPopupMessage != "")
			alert(sPopupMessage);
	</script>
</body>
</html>
