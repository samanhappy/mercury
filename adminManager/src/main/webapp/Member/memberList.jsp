<%@ page language="java" import="java.util.*" contentType="text/html;charset=gbk"%>
<!--<jsp:directive.page import="com.cmcc.emgr.domain.TUser"/>-->
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
		<script type="text/javascript" src="Member/prototype1.4.js"></script>
		<script type="text/javascript" src="prototype1.4.js"></script>
		<SCRIPT language="javascript">

			function CreateMember(){
				jscomNewOpenBySize('Member/newMember.jsp','NewMember',600,400);
			}
			function UpdateMember(id){
				jscomNewOpenBySize('Member/updateMember.jsp','UpdateMember',600,400);
			}
			
			function DeleteMember(){
				
				var sSelectedIDs=jcomGetAllSelectedRecords("chkSelect");
				
				if(sSelectedIDs=="")
				{
					alert("请选择记录！");
					return false;
				}
				if(!window.confirm("您确定要删除人员" + sSelectedIDs + "吗？" )) return;
 					 frmCommon.submit();				
			}
			
			function conditionSearch(){
			    
			      var id = document.getElementById("userid").value;
			      var name = document.getElementById("username").value;
			      var dept =document.getElementById("deptname").value;
			      var type = document.getElementById("usertype").value;
			      var url = "/boco/findUserList.do?flag=after&userid="+id+"&username="+name+"&deptname="+dept+"&usertype="+type;
			      
			     location.href = url;
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
	     
		</SCRIPT>
	</HEAD>
	<BODY onload="dept()">
		<form action="deleteUser.do" method="post" id="frmCommon">
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
								<td background="../images/b2_1.gif">
									&nbsp;
								</td>

							</tr>
						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">

							<TR height="50">
								<td nowrap>
									登录名：
								</td>
								<td nowrap width="100">
									<input class="text" id="userid" type="text"
										style="WIDTH: 100%" name="userid">
								</td>
								<td nowrap>
									&nbsp;&nbsp;姓名：
								</td>
								<td nowrap width="100">
									<input class="text" id="username" type="text"
										style="WIDTH: 100%" name="username">
								</td>
								<td noWrap>
									&nbsp;&nbsp;所属部门：
								</td>
								<td>
									<SELECT id="deptname" name="deptname">
										<OPTION value="">
											-所有-
										</OPTION>
									</SELECT>
								</td>
								<td noWrap>
									&nbsp;&nbsp;人员类型：
								</td>
								<td>
									<SELECT id="usertype" name="usertype">
									  <OPTION value="">
											-所有-
										</OPTION>
										<OPTION value="管理员">
											管理员
										</OPTION>
										<OPTION value="一般用户">
											一般用户
										</OPTION>
									</SELECT>
								</td>

								<td noWrap>
									&nbsp;&nbsp;
									<input class="button" id="btnSearch" type="button" value="查询"
										name="btnSearch" onclick="conditionSearch()">
								</td>
								<td width="100%"></td>
							</TR>

						</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td width="20" background="../images/b2_3.gif" height="18">
									<IMG height="18" src="../images/b2_6.gif" width="1">
								</td>
								<td vAlign="bottom" background="../images/b2_3.gif" width="100">
									登录名
								</td>
								<td width="5">
									<IMG height="18" src="../images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="60"
									background="../images/b2_3.gif">
									姓名
								</td>
								<td width="5">
									<IMG height="16" src="../images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="200"
									background="../images/b2_3.gif">
									所属部门
								</td>
								<td width="5">
									<IMG height="16" src="../images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="../images/b2_3.gif">
									负责人
								</td>
								<td width="5">
									<IMG height="16" src="../images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="100"
									background="../images/b2_3.gif">
									联系电话
								</td>
								<td width="5">
									<IMG height="16" src="../images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="200"
									background="../images/b2_3.gif">
									电子邮件
								</td>
								<td width="5">
									<IMG height="16" src="../images/b2_4.gif" width="5">
								</td>
								<td vAlign="bottom" align="center" width="80"
									background="../images/b2_3.gif">
									人员类型
								</td>
								<td width="1">
									<IMG height="18" src="../images/b2_5.gif" width="1">
								</td>
							</tr>
							<%
							   List list = (List)session.getAttribute("userList");
						        if(list != null){
							     for(int i=0;i<list.size();i++){
							        
							      //TUser t = (TUser)list.get(i);
							     
							  %>
							<tr bgColor="white">
							
								<td width="1"></td>
							 <%
							   }
							   }
							 %>
							</tr>
						</table>
						<table cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>

								<td height="30" vAlign="bottom">
									<INPUT class="popupbutton" onclick="CreateMember()"
										type="button" value="新建">
									&nbsp;
									<INPUT class="popupbutton" onclick="DeleteMember()"
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
