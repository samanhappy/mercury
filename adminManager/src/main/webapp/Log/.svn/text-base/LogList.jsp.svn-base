<%@ page language="java" pageEncoding="GBK"%>
<%@include file="../common.jsp"%>
<HTML>
	<HEAD>
		<title>日志查询</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<script type="text/javascript" src="Js/prototype1.4.js"></script>
		<SCRIPT language="javascript">
		<!--
			
			
			function f(){
				var modulename = document.all('modulename').value;
				new Ajax.Request("GetOperationsServlet?modulename="+modulename,{method:"POST",onComplete:change});
			}
			
			function change(v){
  				document.all('message').innerHTML = v.responseText;
  			}
  			
  			function RefreshForm(){
				var logid = document.all('logid').value;
				var mgrname = document.all('mgrname').value;
				var modulename = document.all('modulename').value;
				var popedom = document.all('popedom').value;
				jscomNewOpenBySize('<%=request.getContextPath()%>/queryLog.do?logid='
					+logid+'&mgrname='+mgrname+'&modulename='+modulename+'&popedom='+popedom,
					'RefreshForm',600,350);
			}
		//-->
		</SCRIPT>
	</HEAD>
	<BODY>

		<form id="frmCommon">
			<!-- 中部表格 -->
			<table height="350" cellSpacing="0" cellPadding="0" width="80%"
				border="0">
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									日志列表
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">

							<TR height="50">
								<td nowrap>
									日志ID：
								</td>
								<td nowrap width="100">
									<input class="text" id="logid"  type="text"
										style="WIDTH: 100%" name="logid">
								</td>
								<td nowrap>
									&nbsp;&nbsp;管理员：
								</td>
								<td nowrap width="100">
									<input class="text" id="mgrname" type="text"
										style="WIDTH: 100%" name="mgrname">
								</td>
								<td noWrap>
									&nbsp;&nbsp;访问的模块：
								</td>
								<td>
									<SELECT id="modulename" name="modulename" onchange="f();">
										<OPTION value="" selected>
											-所有-
										</OPTION>
										<logic:iterate id="modulename" name="modulelist">
											<OPTION value="${modulename}">
												${modulename}
											</OPTION>
										</logic:iterate>
									</SELECT>
								</td>
								<td noWrap>
									&nbsp;&nbsp;进行的操作：
								</td>
								<td>
									<div id="message">
										<Select name="popedom" id="popedom" Class="Text" Width="335">
											<option value="">
												-所有-
											</option>
										</Select>
									</div>
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
								<td vAlign="bottom" background="images/b2_3.gif" width="60">
									日志ID
								</td>
								<td width="5">
									<IMG height="18" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									管理员ID
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									管理员姓名
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									访问的模块
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									进行的操作
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									操作时间
								</td>
								<td width="1">
									<IMG height="18" src="images/b2_5.gif" width="1">
								</td>
							</tr>
							<logic:iterate id="log" name="loglist">
								<tr bgColor="white" height="22">
									<td height="5"></td>
									<td vAlign="bottom">
										${log.logid}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${log.mgrid}
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										${log.mgrname}
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										${log.modulename}
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										${log.operation}
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										${log.date}
									</td>
									<td width="5"></td>
								</tr>
							</logic:iterate>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>

