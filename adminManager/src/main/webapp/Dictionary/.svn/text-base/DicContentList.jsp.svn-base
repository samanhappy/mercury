<%@ page language="java" import="java.util.*" pageEncoding="GBk"%>
<%@include file="../common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>字典内容列表</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<SCRIPT language="javascript">
		<!--
			function CreateDept(){
				jscomNewOpenBySize('<%=request.getContextPath()%>/dicType.do','NewDept',600,350);
			}
			function UpdateDept(id){
				jscomNewOpenBySize('<%=request.getContextPath()%>/dicCInfo.do?dicCid='+id,'UpdateDept',600,350);
			}
			function DeleteDept(){
				
				var sSelectedIDs=jcomGetAllSelectedRecords("chkSelect");
				
				if(sSelectedIDs=="")
				{
					alert("请选择记录！");
					return;
				}
				if(!window.confirm("您确定要删除部门" + sSelectedIDs + "吗？" )) return;
				
				
				frmCommon.submit();
			}
			
			
			
		//-->
		</SCRIPT>
	</HEAD>
	<BODY>

		<form id="frmCommon" action="<%=request.getContextPath()%>/deleteDicC.do">
			<!-- 中部表格 -->
			<table height="350" cellSpacing="0" cellPadding="0" width="80%"
				border="0" >
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									字典内容列表
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="20" background="/images/b2_3.gif" height="18">
									<IMG height="18" src="/images/b2_6.gif" width="1">
								</td>
								<td width="200" vAlign="bottom" background="/images/b2_3.gif">
									字典名称
								</td>
								<td width="5">
									<IMG height="18" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="/images/b2_3.gif">
									字典类型
								</td>
								<td width="5">
									<IMG height="16" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="500"
									background="/images/b2_3.gif">
									注释
								</td>
								<td width="1">
									<IMG height="18" src="/images/b2_5.gif" width="1">
								</td>
							</tr>

							 <logic:iterate id="dicContent" name="dicContentList" indexId="index">
								<tr bgColor="white">
									<td height="5">
										<input type="checkbox" name="chkSelect" id="chkSelect"
											value="${dicContent.dicid}">
									</td>
									<td vAlign="bottom">
										<a href="#" onClick="javascript:UpdateDept(${dicContent.dicid})"><bean:write
												name="dicContent" property="dicname" /> </a>
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										<bean:write name="dicContent" property="dictypename" />
									</td>
									<td width="1"></td>
									<td vAlign="bottom" align="center">
										<bean:write name="dicContent" property="notation" />
									</td>
									<td width="1"></td>

								</tr>
							</logic:iterate>  

						</table>
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>

								<td height="30" vAlign="bottom">
									<INPUT class="popupbutton" onclick="CreateDept()" type="button"
										value="新建">
									&nbsp;
									<INPUT class="popupbutton" onclick="DeleteDept()" type="button"
										value="删除">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
