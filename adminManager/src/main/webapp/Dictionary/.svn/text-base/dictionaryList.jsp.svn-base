<%@ page language="java" import="java.util.*" contentType="text/html;charset=gbk"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>人员列表</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<script type="text/javascript" src="Js/prototype1.4.js"></script>
		<script type="text/javascript" src="prototype1.4.js"></script>
		<SCRIPT language="javascript">

			function CreateDic(){
				jscomNewOpenBySize('Dictionary/newDictionary.jsp','newDictionary',600,400);
			}
			function UpdateDic(id){
				jscomNewOpenBySize('Dictionary/updateDictionary.jsp?dicid='+id,'updateDictionary',600,400);
			}
			
			function DeleteDic(){
				
				var sSelectedIDs=jcomGetAllSelectedRecords("chkSelect");
				
				if(sSelectedIDs=="")
				{
					alert("请选择记录！");
					return false;
				}
				if(!window.confirm("您确定要删除人员" + sSelectedIDs + "吗？" )) return;
				
				  //location.href="deleteDic.do?dicid=" + sSelectedIDs ;
				  frmCommon.submit();
			}
			
		</SCRIPT>
	</HEAD>
	<BODY>
		<form action="deleteDic.do" id="frmCommon" method="post">
			<!-- 中部表格 -->
			<table height="350" cellSpacing="0" cellPadding="0" width="80%"
				border="0">
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									人员列表
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>
					
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="20" background="images/b2_3.gif" height="18">
									<IMG height="18" src="images/b2_6.gif" width="1">
								</td>
								<td vAlign="bottom" background="images/b2_3.gif" width="100">
									ID
								</td>
								<td width="5">
									<IMG height="18" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="60"
									background="images/b2_3.gif">
									字典名称
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="200"
									background="images/b2_3.gif">
									字典类型
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="images/b2_3.gif">
									操作
								</td>
								<td width="5">
									<IMG height="16" src="images/b2_4.gif" width="5">
								</td>
								
								<td width="1">
									<IMG height="18" src="images/b2_5.gif" width="1">
								</td>
							</tr>
						</table>
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>

								<td height="30" vAlign="bottom" align="right">
									<INPUT class="popupbutton" onclick="CreateDic()"
										type="button" value="新建">
									&nbsp;
									<INPUT class="popupbutton" onclick="DeleteDic()"
										type="button" value="删除">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
