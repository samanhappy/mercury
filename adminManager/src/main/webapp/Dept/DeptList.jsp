<%@ page language="java" import="java.util.*" pageEncoding="GBk"%>
<%@include file="../common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<title>�����б�</title>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<LINK href="Css/Common.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="Js/Common.js"></script>
		<script language="javascript" src="Js/BusinessFunction.js"></script>
		<SCRIPT language="javascript">
		<!--
			function CreateDept(){
				jscomNewOpenBySize('<%=request.getContextPath()%>/newdeptpage.do','NewDept',600,350);
			}
			function UpdateDept(id){
				jscomNewOpenBySize('<%=request.getContextPath()%>/info.do?deptid='+id,'UpdateDept',600,350);
			}
			function DeleteDept(){
				
				var sSelectedIDs=jcomGetAllSelectedRecords("chkSelect");
				
				if(sSelectedIDs=="")
				{
					alert("��ѡ���¼��");
					return;
				}
				if(!window.confirm("��ȷ��Ҫɾ������" + sSelectedIDs + "��" )) return;
				
				
				frmCommon.submit();
			}
			
			
			
		//-->
		</SCRIPT>
	</HEAD>
	<BODY>

		<form id="frmCommon" action="<%=request.getContextPath()%>/deleteDept.do">
			<!-- �в���� -->
			<table height="350" cellSpacing="0" cellPadding="0" width="90%"
				border="0" >
				<tr>
					<td vAlign="top">

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="113" height="20" class="TableTitle">
									�����б�
								</td>
								<td background="images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="1" background="/images/b2_3.gif" height="18">
									<IMG height="18" src="/images/b2_6.gif" >
								</td>
								<td vAlign="bottom" background="/images/b2_3.gif" width="150">
									��������
								</td>
								<td width="5">
									<IMG height="18" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="60"
									background="/images/b2_3.gif">
									������
								</td>
								<td width="5">
									<IMG height="16" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="/images/b2_3.gif">
									�绰
								</td>
								<td width="5">
									<IMG height="16" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="/images/b2_3.gif">
									�ֻ�
								</td>
								<td width="5">
									<IMG height="16" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="/images/b2_3.gif">
									����
								</td>
								<td width="1">
									<IMG height="16" src="/images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="300"
									background="/images/b2_3.gif">
									��ע
								</td>
								<td width="1">
									<IMG height="18" src="/images/b2_5.gif" width="1">
								</td>
							</tr>

							<logic:iterate id="dept" name="deptList" indexId="index">
								<tr bgColor="white">
									<td height="5">
										<input type="checkbox" name="chkSelect" id="chkSelect"
											value="${dept.deptid}">
									</td>
									<td vAlign="bottom">
										<a href="#" onClick="javascript:UpdateDept(${dept.deptid})"><bean:write
												name="dept" property="deptname" /> </a>
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										<bean:write name="dept" property="mgrname" />
									</td>
									<td width="5"></td>
									<td vAlign="bottom">
										<bean:write name="dept" property="telephone" />
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										<bean:write name="dept" property="mobile" />
									</td>
									<td width="5"></td>
									<td vAlign="bottom" align="center">
										<bean:write name="dept" property="facsimile" />
									</td>
									<td width="1"></td>
									<td vAlign="bottom">
										<bean:write name="dept" property="remark" />
									</td>
									<td width="1"></td>

								</tr>
							</logic:iterate>

						</table>
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>

								<td height="30" vAlign="bottom">
									<INPUT class="popupbutton" onclick="CreateDept()" type="button"
										value="�½�">
									&nbsp;
									<INPUT class="popupbutton" onclick="DeleteDept()" type="button"
										value="ɾ��">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
