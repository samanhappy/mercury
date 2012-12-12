
	function SelectDept(DeptType,ParentID,DeptID)
	{
		if(DeptID==null)
			DeptID="";
		if(DeptType==null)
			DeptType="1";
			
		jscomNewOpenBySize("../User/SelectDept.aspx?DeptType="+DeptType+"&ParentID="+ParentID+"&SelectDeptID="+DeptID,"SelectDept",600,480);
		
		/*
		var returnValue=showModalDialog("../User/SelectDept.aspx?DeptType="+DeptType+"&ParentID="+ParentID+"&SelectDeptID="+DeptID,"","dialogWidth:600px;dialogHeight:480px;status:no;scrollbars=no");
		if(typeof(returnValue)=='undefined')
			returnValue="||";
		var arrReturnValue=returnValue.split('|');
		if(arrReturnValue.length==3)
			GetReturnedSelectedDept(arrReturnValue[0],arrReturnValue[1],arrReturnValue[2]);
		*/
	}

	function ViewUserDetail(Account_ID)
	{
		jscomNewOpenBySize("../User/UserInfo.aspx?AccountID="+Account_ID,"UserInfo",600,480);
	}
	
	function ShowSearchPanel()
	{
		if(document.all("tbSearchPanel").style.display=="block")
			document.all("tbSearchPanel").style.display="none";
		else
			document.all("tbSearchPanel").style.display="block";
	}
	
	function ShowRightFrameSearchPanel()
	{
		if(parent.fraRight.document.all("tbSearchPanel").style.display=="block")
			parent.fraRight.document.all("tbSearchPanel").style.display="none";
		else
			parent.fraRight.document.all("tbSearchPanel").style.display="block";
	}
	
	function CallRightFrameAddNew(AddNewFunction)
	{
		var AddNewFunction="doAddNew";
		if(AddNewFunction!=null)
			AddNewFunction=AddNewFunction;
			
		eval("parent.fraRight."+AddNewFunction+"()");
	}
	
	function jscomSelectOneUser()
	{
		jscomNewOpenBySize("../User/SelectUser.aspx","SelectUser",650,450);
	}
	
	function gotoURL(url){
		window.open(url,'Main','') ;
	}
	
	function ViewUserExamPaper(Exam_User_Grade_ID)
	{
		jscomNewOpenBySize("../Exam/ExamUserPaperPreview.aspx?Exam_User_Grade_ID="+Exam_User_Grade_ID,"ExamUserPaperPreview",750,550);
	}
	
	function SelectAllowUsers()
	{
		
		jscomNewOpenBySize("../Modules/ParameterPasser.aspx?ParameterName=AllowUserIDs&GetValueFunctionName=GetSelectedUserIDs&RedirectURL=../Modules/RightFrame.aspx?FrameType=SelectAllowUser","SelectAllowUserFrame",850,550);
	}
	
	function SelectFolder(FolderType,ParentID,SelectedID,IsMultiSelect)
	{
		if(!IsMultiSelect)
			IsMultiSelect=false;
		if(IsMultiSelect==false)
			IsMultiSelect="False";
		else
			IsMultiSelect="True";
		jscomNewOpenByFixSize("../Folder/SelectFolder.aspx?IsMultiSelect="+IsMultiSelect+"&FolderType="+FolderType+"&ParentID="+ParentID+"&SelectedID="+SelectedID,"SelectFolder",500,380);
	}
	function SelectFolders(ParentID,SelectedID,IsMultiSelect)
	{
		if(!IsMultiSelect)
			IsMultiSelect=false;
		if(IsMultiSelect==false)
			IsMultiSelect="False";
		else
			IsMultiSelect="True";
		jscomNewOpenByFixSize("../Train/SelectFolder.aspx?IsMultiSelect="+IsMultiSelect+"&ParentID="+ParentID+"&SelectedID="+SelectedID,"SelectFolder",500,380);
	}
	//双击表格时操作的函数
	function DoActionWhenDBLClickRow(GridName,ItemIndex)
	{
		eval(document.all(GridName+"_hlDefaultOpen"+ItemIndex).href);
	}
	
	function jcomSelectOneContent(Content_Type)
	{
		if(Content_Type==null)
			Content_Type="";
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType=SelectOneContent&ContentType="+Content_Type,"RightFrame",790,550);
	}
	
	function jcomSelectContents(Content_Type)
	{
		if(Content_Type==null)
			Content_Type="";
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType=SelectContents&FixContentType="+Content_Type,"RightFrame",790,550);
	}
	
	function jcomSelectAllRecords(SelectAllName,SelectName)
	{
		if(!SelectAllName)
			SelectAllName="chkSelectAll";
		if(!SelectName)
			SelectName="chkSelect";
		if(document.all(SelectName)==null) return "";	
		var checked=document.all(SelectAllName).checked;
		var sSelectedIDs="";
		if(typeof(document.all(SelectName).length) == 'undefined')
		{
			document.all(SelectName).checked=checked;
			sSelectedIDs=document.all(SelectName).value;
			return sSelectedIDs;
		}
		else
		{
			for (i=0; i < document.all(SelectName).length; i ++) 
			{
				document.all(SelectName)[i].checked=checked;
				sSelectedIDs=sSelectedIDs+document.all(SelectName).value+",";
			}
		}
		if(sSelectedIDs!="") sSelectedIDs=sSelectedIDs.substr(0,sSelectedIDs.length-1);
		return sSelectedIDs;
	}
	
	function jcomGetFirstSelectedRecord(SelectName)
	{
		if(!SelectName)
			SelectName="chkSelect";
			
			if(document.all(SelectName)==null)
				return "";
			
			var sSelectIDs="";
			if(typeof(document.all(SelectName).length)=='undefined')
			{
				if (document.all(SelectName).checked == true){
					sSelectIDs=document.all(SelectName).value;
					return sSelectIDs;
				}
				else
				{
					return "";
				}
			}
	
			var nCount =document.all(SelectName).length;
			var i;	
			for (i=0; i < nCount; i ++) 
			{
				if (document.all(SelectName)[i].checked == true){
					return document.all(SelectName)[i].value;
				} 				
			} 
			return "";
	}
	
	function jcomGetAllSelectedRecords(SelectName)
	{
		
		if(!SelectName)
			SelectName="chkSelect";
			
			
		if(document.all(SelectName)==null)
			return "";
			
		var sSelectIDs="";
		if(typeof(document.all(SelectName).length)=='undefined')
		{
			if (document.all(SelectName).checked == true){
				sSelectIDs=document.all(SelectName).value;
				return sSelectIDs;
			}
			else
			{
				return "";
			}
		}
		
		var nCount = document.all(SelectName).length;
		for (var i=0; i < nCount; i ++) 
		{
			if (document.all(SelectName)[i].checked == true){
				sSelectIDs=sSelectIDs+document.all(SelectName)[i].value+",";
			} 				
		}
		if (sSelectIDs!="")
		{
			sSelectIDs=sSelectIDs.substr(0,sSelectIDs.length-1);
		}
		return sSelectIDs;
	}
	
	function jcomSelectPaper(Exam_Type)
	{
		if(Exam_Type==null)
			Exam_Type="";
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType=SelectPaper&ExamType="+Exam_Type,"RightFrame",790,550);
	}
	
	function jscomSelectPolicy(Exam_Type)
	{
		if(Exam_Type==null)
			Exam_Type="";
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType=SelectPolicy&ExamType="+Exam_Type,"RightFrame",790,550);
	}
	
	function jscomSelectExam(ExamType)
	{
		var FrameType="";
		if(ExamType=="1")
			FrameType="SelectExam";
		else if(ExamType=="2")
			FrameType="SelectJob";
		else if(ExamType=="3")
			FrameType="SelectExe";
		else if(ExamType=="4")
			FrameType="SelectCompetition";
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType="+FrameType,"RightFrame",790,550);
	}
	
	function jscomSelectExamTempl(ExamType)
	{
		var FrameType="";
		if(ExamType=="8")
			FrameType="SelectExamTempl";
		else if(ExamType=="9")
			FrameType="SelectJobTempl";
		else if(ExamType=="10")
			FrameType="SelectExerciseTempl";
		else if(ExamType=="11")
			FrameType="SelectCompetitionTempl";
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType="+FrameType,"RightFrame",790,550);
	}
	
	function jscomSelectJob()
	{
		jscomNewOpenBySize("../Modules/RightFrame.aspx?FrameType=SelectJob","RightFrame",790,550);
	}
	
	function jscomExportTableToWord(tableName)
	{
		if(document.all(tableName).rows.length==0)
		{
			alert("没有内容可导！");
			return;
		}

		var oWord;
		try{
			oWord = new ActiveXObject("Word.Application"); // Get a new workbook.
		}catch(e)
		{
			alert("无法调用Office对象，请确保您的机器已安装了Office并已将本系统的站点名加入到IE的信任站点列表中！");
			return;
		}
		var oDocument = oWord.Documents.Add();
		var oDocument = oWord.ActiveDocument; 
		//oDocument.Paragraphs.Add();
		oDocument.Paragraphs.Last.Alignment = 1;
		oDocument.Paragraphs.Last.Range.Bold = true;
		oDocument.Paragraphs.Last.Range.Font.Size = 16;
		oDocument.Paragraphs.Last.Range.Font.name = "宋体";
		oDocument.Paragraphs.Last.Range.InsertAfter(document.all("lblTitle").innerText);

		oDocument.Paragraphs.Add();
		oDocument.Paragraphs.Last.Alignment = 2;
		oDocument.Paragraphs.Last.Range.Bold = false;
		oDocument.Paragraphs.Last.Range.Font.Size = 12;
		oDocument.Paragraphs.Last.Range.Font.name = "宋体";
		oDocument.Paragraphs.Last.Range.InsertAfter(document.all("lblSubTitle").innerText);
		
		var table = document.all(tableName);
		var nRows = table.rows.length; 
		var nCols = table.rows(0).cells.length;
		for (i=0;i<nRows;i++)
		{
			nCol=0;
			nCols=table.rows(i).cells.length;
			for (j=0;j<nCols;j++) 
			{ 
				if(table.rows(i).cells(j))
				{
					oDocument.Paragraphs.Add();
					oDocument.Paragraphs.Last.Alignment = 0;
					oDocument.Paragraphs.Last.Range.Bold =false;
					if(j==0)
						oDocument.Paragraphs.Last.Range.Font.Size = 14;
					else
						oDocument.Paragraphs.Last.Range.Font.Size = 10;
					oDocument.Paragraphs.Last.Range.Font.name = "宋体";
					oDocument.Paragraphs.Last.Range.InsertAfter(table.rows(i).cells(j).innerText);
				}
				nCol=nCol+1;
			} 
		}
		oWord.Visible = true;
	}
	
	//没有Folder_ID可以传-1
	function jscomAddExam(Exam_Type,Folder_ID)
	{
		jscomNewOpenByFixSize("../Exam/ExamInfoNew.aspx?ExamType="+Exam_Type+"&FolderID="+Folder_ID,"ExamInfo",800,600);
	}
	
	function jscomShowConfirm(message)
	{
		var returnValue=showModalDialog('../Modules/MessageBox.aspx',message,"dialogWidth:350px;dialogHeight:240px;status:no;scrollbars=no");
		if(typeof(returnValue)=='undefined')
			returnValue=0;
		return returnValue;
	}
	
	var sShowMessageWindowArgument="";
	function jscomShowMessageWindow(title,message,width,height)
	{

		sShowMessageWindowArgument=title+"|-|"+message;
		if(width==null)
			width="500";
			
		if(height==null)
			height="400";
		
		var url="../Modules/ShowMessage.aspx";
		
        var tt,w,left,top;
		left=(screen.width-width)/2;
		if(left<0){ left=0;}

		top=(screen.height-60-height)/2;
		if(top<0){ top=0;}

        tt="toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=yes,";
        tt=tt+"width="+width+",height="+height+",left="+left+",top="+top;
	    w=window.open(url,"MessageWindow",tt);
		try{
			w.focus();
		}catch(e){}
		
	}
	
	//给上一个含数jscomShowMessageWindow作参数回去的接口
	function GetsShowMessageWindowArgument()
	{
		return sShowMessageWindowArgument;
	}
			
	function jscomShowMessageDialog(title,message,width,height)
	{
		if(width==null)
			width="500";
			
		if(height==null)
			height="400";
		showModelessDialog('../Modules/ShowMessage.aspx',title+"|-|"+message,"dialogWidth:"+width+"px;dialogHeight:"+height+"px;status:no;scrollbars=auto");
	}
	
	function jscomCheckUserAnswer(Content_ID,Standard_Answer)
	{
		var Content_Type=document.all("hidContentType"+Content_ID).value;
		var arrUserAnswer=document.all("Answer"+Content_ID).value;
		var userAnswer="";
		if(typeof(document.all("Answer"+Content_ID).length) == 'undefined')
		{
			userAnswer=document.all("Answer"+Content_ID).value;
		}
		else
		{
			for(var j=0;j<document.all("Answer"+Content_ID).length;j++)
			{
				if(typeof(document.all("Answer"+Content_ID)[j].checked) == 'undefined')
				{
					userAnswer=userAnswer+"|"+document.all("Answer"+Content_ID)[j].value;
				}
				else
				{
					if(document.all("Answer"+Content_ID)[j].checked==true) userAnswer=userAnswer+"|"+document.all("Answer"+Content_ID)[j].value;
				}
			}
			if (userAnswer!="") userAnswer=userAnswer.substr(1);
		}
		if(userAnswer=="")
			return "";
		else
		{
			if(jscomCompireAnswer(userAnswer,Standard_Answer)==true)
				return "True";
			else
				return "False";
		}
		
	}
	
	function jscomCompireAnswer(Answer1,Answer2)
	{
		var arrAnswer1=Answer1.split("|");
		var arrAnswer2=Answer2.split("|");
		if(arrAnswer1.length!=arrAnswer2.length)
			return false;
			
		for(var i=0;i<arrAnswer1.length;i++)
		{
			if(("|"+Answer2+"|").indexOf("|"+arrAnswer1[i]+"|")==-1)
				return false;
		}
		return true;
	}
	
	function jscomShowCourseware(Courseware_Type,sEntrance,httpRoot)
	{
			var strResult="";
			switch(Courseware_Type)
			{
				case 1:
					if(sEntrance !=null && sEntrance.indexOf("http")>-1)
					{
						strResult = sEntrance;
					}
					else
					{
						if(sEntrance!=null) strResult=httpRoot+"/"+sEntrance; 
					}
					jscomShowNormalCourseware(strResult);
					break;
				case 2:
					jscomShowSCORMCourseware(httpRoot);
					break;
				case 3:
					jscomShowACIICourseware(httpRoot);
					break;
			}
	}
	
	function jscomShowNormalCourseware(URL)
	{
		jscomNewOpenMaxWindow(URL,"Courseware",screen.width,screen.height-100);
	}
	function jscomShowSCORMCourseware(URL)
	{
		jscomNewOpenMaxWindow(URL,"Courseware",screen.width,screen.height-100);
	}
	function jscomShowACIICourseware(URL)
	{
		jscomNewOpenMaxWindow(URL,"Courseware",screen.width,screen.height-100);
	}
	
	function jscomLearningNormalCourseware(Train_User_Grade_ID,Lesson_ID,Courseware_ID,URL)
	{
		jscomNewOpenMaxWindow("../Train/LearningFrame.aspx?Train_User_Grade_ID="+Train_User_Grade_ID+"&Lesson_ID="+Lesson_ID+"&Courseware_ID="+Courseware_ID+"&URL="+URL,"Courseware",screen.width,screen.height-100);
	}
	function jscomLearningSCORMCourseware(Train_User_Grade_ID,Lesson_ID,Courseware_ID,URL)
	{
		jscomNewOpenMaxWindow(URL,"Courseware",screen.width,screen.height-100);
	}
	function jscomLearningACIICourseware(Train_User_Grade_ID,Lesson_ID,Courseware_ID,URL)
	{
		jscomNewOpenMaxWindow(URL,"Courseware",screen.width,screen.height-100);
	}
	
	function jscomOpenUserLesson(TrainID,LessonID)
	{
		if(Content_Type==null)
			Content_Type="";
		jscomNewOpenMaxWindow("../Modules/RightFrame.aspx?FrameType=UserLesson&TrainID="+TrainID+"&LessonID="+LessonID,"RightFrame");
	}