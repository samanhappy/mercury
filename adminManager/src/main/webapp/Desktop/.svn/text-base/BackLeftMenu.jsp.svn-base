<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
<HEAD>
<title></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<LINK href="../Css/LeftMenu.css" rel="stylesheet">
<script language="javascript" src="../Js/Common.js"></script>
<script language="javascript">
<!--
	var subMenu = new Array(4);
	subMenu[0] = 'undefine';
	subMenu[1] = 'undefine';
	subMenu[2] = 'undefine';
	subMenu[3] = 'undefine';

	function gotoURL(szURL) {
		if (szURL == "")
			return;
		parent.Main.location.href = szURL;
<%--					window.open(szURL,'Main','') ;--%>
	}

	function ResetMenuStatus(object) {
		var MenuLevel = 1;
		if (object != null) {
			var oldClassName = object.className;
			if (oldClassName.indexOf("1") > -1)
				MenuLevel = 1;
			else if (oldClassName.indexOf("2") > -1)
				MenuLevel = 2;
			else if (oldClassName.indexOf("3") > -1)
				MenuLevel = 3;
		}
		//全部菜单取消焦点
		for ( var i = 0; i < document.all.length; i++) {
			if (document.all(i).className.indexOf("Selected") > -1)
				document.all(i).className = document.all(i).className.substr(8,
						document.all(i).className.length - 8);
		}

		if (MenuLevel == 1) {
			//收回全部大菜单下的子菜单
			for ( var i = 1; i <= 5; i++) {
				try {
					eval("document.all.SubMenu" + i
							+ ".className = 'SubMenuLayerHidden'");
					eval("document.all.ArrowLayer" + i
							+ "Right.style.display = 'none'");
					eval("document.all.ArrowLayer" + i
							+ "Down.style.display = ''");
				} catch (e) {
				}
			}
		}
	}

	function SelectMenu(object, szURL) {
		ResetMenuStatus(object);
		if (object.className.indexOf("Selected") == -1)
			object.className = "Selected" + object.className;

		parent.Main.location.href = szURL;
	}

	function resizeMenu() {
		if (Menu.style.display == '') {
			Menu.style.display = 'none';
			resizeIcon_Show.style.display = 'none';
			resizeIcon_Hidden.style.display = '';
			top.Bottom.cols = '7,*';
		} else {
			Menu.style.display = '';
			resizeIcon_Show.style.display = '';
			resizeIcon_Hidden.style.display = 'none';
			top.Bottom.cols = '115,*';
		}
	}
//-->
</script>
</HEAD>
<body>
	<table bgcolor="#F2F9FB" height="100%" cellSpacing="0" cellPadding="0"
		border="0">

		<tr vAlign="top">
			<td width="3" rowspan="4" bgcolor="#36a9ce"></td>
			<td id="Menu" width="108">
				<div id="divMainMenu" style="Z-INDEX: 1000; OVERFLOW: auto">
					<table class="MenuLevel1" onclick="spreadMenu(this,1);"
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>配置管理 <span id="ArrowLayer1Down"> <img
									src="../images/LeftMenu/IconArrowDown.gif" border="0"
									WIDTH="10" HEIGHT="10"></span> <span id="ArrowLayer1Right"
								style="DISPLAY: none"><img
									src="../images/LeftMenu/IconArrowRight.gif" border="0"
									WIDTH="10" HEIGHT="10"></span>
							</td>
						</tr>
					</table>
					<div id="SubMenu1" class="SubMenuLayerHidden">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/selectReceiveConfigList.action')">
								<td>查看接收配置</td>
							</tr>
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/selectSendConfigList.action')">
								<td>查看发送配置</td>
							</tr>
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/addReceiveConfig.action')">
								<td>增加接收配置</td>
							</tr>
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/addSendConfig.action')">
								<td>增加发送配置</td>
							</tr>
						</table>
					</div>
					<table class="MenuLevel1" onclick="spreadMenu(this,2);"
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>机房管理 <span id="ArrowLayer2Down"> <img
									src="../images/LeftMenu/IconArrowDown.gif" border="0"
									WIDTH="10" HEIGHT="10"></span><span id="ArrowLayer2Right"
								style="DISPLAY: none"><img
									src="../images/LeftMenu/IconArrowRight.gif" border="0"
									WIDTH="10" HEIGHT="10"></span>
							</td>
						</tr>
					</table>
					<div id="SubMenu2" class="SubMenuLayerHidden">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/getRoomList.do')">
								<td>机房列表</td>
							</tr>
						</table>
					</div>
					<table class="MenuLevel1" onclick="spreadMenu(this,3);"
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>人员管理 <span id="ArrowLayer3Down"> <img
									src="../images/LeftMenu/IconArrowDown.gif" border="0"
									WIDTH="10" HEIGHT="10"></span><span id="ArrowLayer3Right"
								style="DISPLAY: none"><img
									src="../images/LeftMenu/IconArrowRight.gif" border="0"
									WIDTH="10" HEIGHT="10"></span>
							</td>
						</tr>
					</table>
					<div id="SubMenu6" class="SubMenuLayerHidden">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/findUserList.do?flag=main')">
								<td>人员信息</td>
							</tr>
						</table>
					</div>
					<table class="MenuLevel1" onclick="spreadMenu(this,4);"
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>日志管理 <span id="ArrowLayer4Down"> <img
									src="../images/LeftMenu/IconArrowDown.gif" border="0"
									WIDTH="10" HEIGHT="10"></span><span id="ArrowLayer4Right"
								style="DISPLAY: none"><img
									src="../images/LeftMenu/IconArrowRight.gif" border="0"
									WIDTH="10" HEIGHT="10"></span>
							</td>
						</tr>
					</table>
					<div id="SubMenu7" class="SubMenuLayerHidden">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/showLogList.do')">
								<td>日志查询</td>
							</tr>
						</table>
					</div>
					<table class="MenuLevel1" onclick="spreadMenu(this,5);"
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>权限管理 <span id="ArrowLayer5Down"> <img
									src="../images/LeftMenu/IconArrowDown.gif" border="0"
									WIDTH="10" HEIGHT="10"></span><span id="ArrowLayer5Right"
								style="DISPLAY: none"><img
									src="../images/LeftMenu/IconArrowRight.gif" border="0"
									WIDTH="10" HEIGHT="10"></span>
							</td>
						</tr>
					</table>
					<div id="SubMenu8" class="SubMenuLayerHidden">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/getRoleList.do')">
								<td>角色管理</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/configMemberJsp.do')">
								<td>配置用户</td>
							</tr>
						</table>
					</div>


					<table class="MenuLevel1" onclick="spreadMenu(this,6);"
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>字典管理 <span id="ArrowLayer6Down"> <img
									src="../images/LeftMenu/IconArrowDown.gif" border="0"
									WIDTH="10" HEIGHT="10"></span><span id="ArrowLayer5Right"
								style="DISPLAY: none"><img
									src="../images/LeftMenu/IconArrowRight.gif" border="0"
									WIDTH="10" HEIGHT="10"></span>
							</td>
						</tr>
					</table>
					<div id="SubMenu9" class="SubMenuLayerHidden">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/findDic.do')">
								<td>字典类型管理</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="MenuLevel2"
								onclick="javascript:SelectMenu(this,'<%=request.getContextPath()%>/allDicList.do')">
								<td>字典内容管理</td>
							</tr>
						</table>
					</div>
				</div> <INPUT id="hidVersionType" type="hidden" size="1"
				name="hidVersionType">
			</td>
			<td width="7" valign="middle" align="center"
				background="../Images/LeftMenu/ResizeBg.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					height="100%">
					<TBODY>
						<tr height="50%">
							<td valign="top">
								<!--<span id="resizeIcon_Up" style="CURSOR:hand" onclick="javascript:menuMoveUp()" title="向上移动">
								<img src="../images/LeftMenu/Resize_Hidden.gif" WIDTH="7" HEIGHT="48"></span>-->
							</td>
						<tr>
							<td><span id="resizeIcon_Show" style="CURSOR: hand"
								onclick="javascript:resizeMenu()" title="隐藏菜单"> <img
									src="../images/LeftMenu/Resize_Hidden.gif" WIDTH="7"
									HEIGHT="48"></span><span id="resizeIcon_Hidden"
								style="DISPLAY: none; CURSOR: hand"
								onclick="javascript:resizeMenu()" title="显示菜单"><img
									src="../images/LeftMenu/Resize_Show.gif" WIDTH="7" HEIGHT="48"></span>
							</td>
						</tr>
						<tr height="50%">
							<td valign="bottom">
								<!--
								<span id="resizeIcon_Up" style="CURSOR:hand" onclick="javascript:menuMoveDown()" title="向上移动">
								<img src="../images/LeftMenu/Resize_Hidden.gif" WIDTH="7" HEIGHT="48"></span>-->
							</td>
						</tr>
					</TBODY>
				</table>
			</td>
		</tr>
	</table>
	<script LANGUAGE="javascript">
	<!--
		function spreadMenu(object, n) {
			var szSubMenuClassName = eval("document.all.SubMenu" + n
					+ ".className");

			ResetMenuStatus(object);
			try {
				if (szSubMenuClassName == 'SubMenuLayerHidden') {
					eval("document.all.SubMenu" + n
							+ ".className = 'SubMenuLayer'");
					eval("document.all.ArrowLayer" + n
							+ "Down.style.display = 'none'");
					eval("document.all.ArrowLayer" + n
							+ "Right.style.display = ''");
				} else {
					eval("document.all.SubMenu" + n
							+ ".className = 'SubMenuLayerHidden'");
					eval("document.all.ArrowLayer" + n
							+ "Down.style.display = ''");
					eval("document.all.ArrowLayer" + n
							+ "Right.style.display = 'none'");
				}

				if (SubMenu1.scrollHeight > 440 && screen.availHeight <= 600) {
					SubMenu1.style.height = 440;
					SubMenu1.style.overflowY = 'scroll';
				}
			} catch (e) {
			}
		}

		function spreadSubMenu(n) {

			var szSubMenuClassName = eval("document.all.SubMenu" + n
					+ ".className");
			var arrMenuNum = n.split("_");

			for ( var i = 1; i < 5; i++) {
				try {
					eval("document.all.SubMenu" + arrMenuNum[0] + "_" + i
							+ ".className = 'SubMenuLayerHidden'");
					eval("document.all.ArrowLayer" + arrMenuNum[0] + "_" + i
							+ "Right.style.display = 'none'");
					eval("document.all.ArrowLayer" + arrMenuNum[0] + "_" + i
							+ "Down.style.display = ''");
				} catch (e) {
				}
			}

			if (szSubMenuClassName == 'SubMenuLayerHidden') {
				eval("document.all.SubMenu" + n + ".className = 'SubMenuLayer'");
				eval("document.all.ArrowLayer" + n
						+ "Down.style.display = 'none'");
				eval("document.all.ArrowLayer" + n + "Right.style.display = ''");
			} else {
				eval("document.all.SubMenu" + n
						+ ".className = 'SubMenuLayerHidden'");
				eval("document.all.ArrowLayer" + n + "Down.style.display = ''");
				eval("document.all.ArrowLayer" + n
						+ "Right.style.display = 'none'");
			}

			if (SubMenu1.scrollHeight > 440 && screen.availHeight <= 600) {
				SubMenu1.style.height = 440;
				SubMenu1.style.overflowY = 'scroll';
			}

		}

		function menuMoveUp() {
			if (typeof (document.all("divMainMenu").top) == 'undefined')
				document.all("divMainMenu").top = 0;
			document.all("divMainMenu").top = document.all("divMainMenu").top - 10;
			document.all("divMainMenu").style.top = document.all("divMainMenu").top;
			alert(document.all("divMainMenu").style.top);
		}

		function menuMoveDown() {
			if (typeof (document.all("divMainMenu").top) == 'undefined')
				document.all("divMainMenu").top = 0;
			document.all("divMainMenu").top = document.all("divMainMenu").top + 10;
			document.all("divMainMenu").style.top = document.all("divMainMenu").top;
		}

		var LocString = String(window.document.location.href);

		function getQueryStr(str) {
			var rs = new RegExp("(^|)" + str + "=([^\&]*)(\&|$)", "gi")
					.exec(LocString), tmp;
			if (tmp = rs) {
				return tmp[2];
			}
			return "";
		}

		function getElementsByClassName(tagName, className) {
			var classElements = [], allElements = document
					.getElementsByTagName(tagName);
			for ( var i = 0; i < allElements.length; i++) {
				if (allElements[i].className == className) {
					classElements[classElements.length] = allElements[i];
				}
			}
			return classElements;
		}

		var authority = getQueryStr("authority");
		var menus = getElementsByClassName("table", "MenuLevel1");

		for ( var i = 0; i < menus.length; i++) {
			try {
				if (authority.charAt(i) == 0) {
					menus[i].style.display = 'none';
				}
			} catch (e) {
			}
		}
	//-->
	</script>
	<script for="document" event="oncontextmenu" language="javascript">
		// 			if (!event.ctrlKey) return false;
	</script>
</body>
</HTML>

