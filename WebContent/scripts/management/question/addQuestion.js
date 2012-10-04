application.page='examManagement';
addQuestion = {};
addQuestion.tabCounter = 5;

addQuestion.setDefaultForm = function(){
	addQuestion.clearTextBox();
	$('#questionTabNav a:first').tab('show');
};

addQuestion.clearTextBox = function(){
	$("#questionTextArea").val("");
	$("#solveTextArea").val("");
	$("textarea[name^=answerTextArea]").each(function(){
		$(this).val("");
	});
	$("input[id^=answerScoreFool]:radio").each(function(){
		$(this).attr("checked",true);
	});
	addQuestion.calAnswer();
};

addQuestion.validateForm = function(){
	var haveError = false;
	if($("#courseId").val() ==0 || $("#questionGroupId").val()==0)
	{
		applicationScript.errorAlertWithStringTH("คุณยังไม่ได้เลือกวิชาหรือกลุ่มคำถาม");
		haveError = true;
	}else if ($("#questionTextArea").val().length==0){
		applicationScript.errorAlertWithStringTH("กรุณากรอกคำถาม");
		haveError = true;
	}else{
		var errorId=null;
		var index = 1;
		$("textarea[name^=answerTextArea]").each(function(){
			if($(this).val().length==0)
			{
				haveError=true;
				errorId=index;	
			}
			index++;
		});
		if(haveError==true)
		{	
			applicationScript.errorAlertWithStringTH("กรุณากรอกคำตอบให้ครบทุกข้อ");
			$('#questionTabNav a:eq('+errorId+')').tab('show');
		}else
		{
			if(parseInt($("#numOfCorrectAnswer").val(), 10) < 1)
			{
				applicationScript.errorAlertWithStringTH("ต้องมีคำตอบที่ถูกต้องอย่างน้อย 1 ข้อ");
				haveError = true;
			}else if (parseInt($("#numOfFoolAnswer").val(), 10) < 3)
			{
				applicationScript.errorAlertWithStringTH("ต้องมีคำตอบหลอกอย่างน้อย 3 ข้อ");
				haveError = true;
			}
		}
	}
	return !haveError;
};


addQuestion.calFormString = function(){
	var number = 0;
	var obj = [];
	var location = 0;
	var id;
	$('textarea[name^=answerTextArea]').each(function(){
		id = $(this).attr('id');
		location = id.substring(14,id.length);
		obj[number]={
			answerText: $('#answerTextArea'+location).val(),
			answerScore: parseInt($('input[name=answerScore'+location+']:checked').val())
		};
		number++;
	});
	return JSON.stringify(obj);
};


addQuestion.calAnswer= function()
{
	var correctAnswer = 0;
	var foolAnswer = 0;
	
	$("input[name^=answerScore]:checked").each(function(){
		if( $(this).val() == 0)
		{
			foolAnswer++;
		}else{
			correctAnswer++;
		}
	});
	
	if(correctAnswer>0)
	{
		$(".numOfCorrectAnswerText").css("color","#5ab602");
	}else
	{
		$(".numOfCorrectAnswerText").css("color","red");
	}
	
	if(foolAnswer>=3)
	{
		$(".numOfFoolAnswerText").css("color","#5ab602");
	}else
	{
		$(".numOfFoolAnswerText").css("color","red");
	}
	
	$("#numOfCorrectAnswer").val(correctAnswer);
	$("#numOfFoolAnswer").val(foolAnswer);
};

addQuestion.initFunction = function(){
	addQuestion.calAnswer();
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#questionGroupId").load(application.contextPath+"/management/questionGroupComboBox.html",{courseId:$("#courseId").val()},function(){
			$(this).trigger("liszt:updated");
		});
	});
	$(".ckeditorarea").ckeditor();
};

$(document).ready(function(){
	$("#courseId").chosen();
	$("#questionGroupId").chosen();
	addQuestion.initFunction();
	$("input[name^=answerScore]:radio").on("change",function(){
		addQuestion.calAnswer();
	});
	$("#addAnswer").click(function(e){
		e.preventDefault();
		$('#questionTabNav li.active').removeClass('active');
		$('#questionTabContent div.active').removeClass('active');
		
		$('#questionTabContent').append('<div class="tab-pane active" id="answer-tab'+addQuestion.tabCounter+'">'
											+'<table class="table-center">'
												+'<tr>'
													+'<td colspan="2"><textarea id="answerTextArea'+addQuestion.tabCounter+'" name="answerTextArea'+addQuestion.tabCounter+'"></textarea></td>'
												+'</tr>'
												+'<tr>'
													+'<td align="right" width="130">เป็นคำตอบที่ถูกต้อง&nbsp;:&nbsp;</td>'
													+'<td align="left">'
														+'<input type="radio" id="answerScoreCorrect'+addQuestion.tabCounter+'" name="answerScore'+addQuestion.tabCounter+'" value="1">'
														+'<label for="answerScoreCorrect'+addQuestion.tabCounter+'">&nbsp;ใช่&nbsp;</label>'
														+'<input type="radio" id="answerScoreFool'+addQuestion.tabCounter+'" name="answerScore'+addQuestion.tabCounter+'" value="0" checked="checked">'
														+'<label for="answerScoreFool'+addQuestion.tabCounter+'">&nbsp;ไม่ใช่&nbsp;</label>'
													+'</td>'
												+'</tr>'
											+'</table>'
										+'</div>');
		$('#questionTabNav').append('<li id="answer-tab-content'+addQuestion.tabCounter+'"><a href="#answer-tab'+addQuestion.tabCounter+'" data-toggle="tab" >Answer <button class="close" onClick="closeTab('+addQuestion.tabCounter+')">×</button></a></li>');
//		$('#answerTextArea'+addQuestion.tabCounter).ckeditor(ckOptions);
		$('#answerTextArea'+addQuestion.tabCounter).ckeditor();
		$('#questionTabNav a:last').tab('show');
		addQuestion.tabCounter++;
		addQuestion.calAnswer();
	});
	$("#createQuestionButton").click(function(e){
		e.preventDefault();
		if(addQuestion.validateForm()){
			$("#confirmModal").modal('show');
		}
	});
	$("#confirmButton").click(function(e){
		var thisButton = $(this);
		thisButton.button('loading');
		$.ajax({
			url: application.contextPath+'/management/question/add.html'
			,data: {
				courseId: $("#courseId").val()
				,questionGroupId: $("#questionGroupId").val()
				,questionText: $("#questionTextArea").val()
				,answerStr: addQuestion.calFormString()
			}
			,type: 'POST'
			,success: function(){
				thisButton.button('reset');
				$('#confirmModal').modal('hide');
				addQuestion.setDefaultForm();
				applicationScript.saveComplete();
			}
			,error: function(data){
				thisButton.button('reset');
				$('#confirmModal').modal('hide');
				applicationScript.errorAlertWithStringTH(data.responseText);
			}
		});
	});
});

closeTab = function(tabNumber){
	if($('#answer-tab'+tabNumber).hasClass('active')){
		$('#questionTabNav a:first').tab('show');
	}
	$('#answer-tab'+tabNumber).remove();
	$('#answer-tab-content'+tabNumber).remove();
	addQuestion.calAnswer();
	
};