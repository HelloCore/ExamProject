application.page='examManagement';
viewQuestion = {};
viewQuestion.numOfAnswer = 0;
viewQuestion.numOfCorrectAnswer = 0;
viewQuestion.numOfFoolAnswer = 0;
viewQuestion.oldCourseCode = '';
viewQuestion.oldQuestionGroupName = '';
viewQuestion.saveQuestionButton = {};
viewQuestion.currentAnswerId = null;
viewQuestion.isEdit = false;

viewQuestion.oldCourseId = '';
viewQuestion.oldQuestionGroupId = '';
viewQuestion.oldQuestionText = '';
viewQuestion.oldAnswerText = '';
viewQuestion.oldAnswerScore = '';

viewQuestion.addAnswer = function(){
	$.ajax({
		url: application.contextPath+'/management/question/view.html'
		,type: 'POST'
		,data: {
			method: 'addAnswer',
			questionId: $("#questionId").val(),
			answerText: $("#answerText").val(),
			answerScore: $("input[name=answerScore]:checked").val()
		},success: function(data){
			var answerScoreStr = 'ไม่ใช่';
			if(data.answerScore>0){
				answerScoreStr = 'ใช่';
			}
			$("#saveAnswerButton").button('reset');
			$('#answerTabContent').append('<div class="tab-pane active" id="answer-'+data.answerId+'">'
											+'<div id="answer-panel-'+data.answerId+'">'+data.answerText+'</div>'
											+'<div class="pagination-centered">'
												+'<font>เป็นคำตอบที่ถูกต้อง : </font>'
												+'<font id="answer-score-text-'+data.answerId+'">'+answerScoreStr+'</font>'
											+'</div>'
											+'<div class="pagination-centered button-group">'
												+'<input type="hidden" id="answer-score-'+data.answerId+'" value="'+data.answerScore+'">'
												+'<button class="btn btn-info" onclick="editAnswer('+data.answerId+')"><i class="icon-edit icon-white"></i> Edit</button>'
												+'&nbsp;'
												+'<button class="btn btn-danger" onclick="deleteAnswer('+data.answerId+')"><i class="icon-trash icon-white"></i> Delete</button>'
											+'</div>'
										+'</div>');
			$('#answerTabNav').append('<li id="answer-menu-'+data.answerId+'"><a href="#answer-'+data.answerId+'" data-toggle="tab">Answer</a></li>');
			$('#answerTabNav a:last').tab('show');
			applicationScript.saveComplete();
			$("#editAnswerModal").modal('hide');
			viewQuestion.calAnswer();
		},error: function(){
			$("#saveAnswerButton").button('reset');
			applicationScript.errorAlert();
			$("#editAnswerModal").modal('hide');
		}
	});
};

viewQuestion.checkAnswerDirty = function(){
	var isDirty = false;
	if(viewQuestion.oldAnswerText != $("#answerText").val()){
		isDirty = true;
	}else if (viewQuestion.oldAnswerScore != $("input[name=answerScore]:checked").val()){
		isDirty = true;
	}
	return isDirty;
};

viewQuestion.editAnswer = function(){
	if(viewQuestion.checkAnswerDirty()){
		$.ajax({
			url: application.contextPath+'/management/question/view.html'
			,type: 'POST'
			,data: {
				method: 'editAnswer',
				answerId: viewQuestion.currentAnswerId,
				answerText: $("#answerText").val(),
				answerScore: $("input[name=answerScore]:checked").val()
			},success: function(){
				$("#saveAnswerButton").button('reset');
				
				$("#answer-panel-"+viewQuestion.currentAnswerId).html($("#answerText").val());
				
				if($("input[name=answerScore]:checked").val()==0){
					$("#answer-score-text-"+viewQuestion.currentAnswerId).text('ไม่ใช่');
					$("#answer-score-"+viewQuestion.currentAnswerId).val(0);
				}else{
					$("#answer-score-text-"+viewQuestion.currentAnswerId).text('ใช่');
					$("#answer-score-"+viewQuestion.currentAnswerId).val(1);
				}
	
				applicationScript.saveComplete();
				$("#editAnswerModal").modal('hide');
				viewQuestion.calAnswer();
			},error: function(){
				$("#saveAnswerButton").button('reset');
				applicationScript.errorAlert();
				$("#editAnswerModal").modal('hide');
			}
		});
	}else{
		applicationScript.successAlertWithStringHeader('No data change.','Save Complete');

		$("#saveAnswerButton").button('reset');
		$("#editAnswerModal").modal('hide');
	}
};
viewQuestion.calAnswer = function(){
	viewQuestion.numOfAnswer = 0;
	viewQuestion.numOfCorrectAnswer = 0;
	viewQuestion.numOfFoolAnswer = 0;
	$('input[id^=answer-score]').each(function(){
		viewQuestion.numOfAnswer++;
		if($(this).val()<1){
			viewQuestion.numOfFoolAnswer++;
		}else{
			viewQuestion.numOfCorrectAnswer++;
		}
	});
	$("#numOfCorrectAnswer").text(viewQuestion.numOfCorrectAnswer);
	$("#numOfFoolAnswer").text(viewQuestion.numOfFoolAnswer);
};
viewQuestion.disableComboBox = function(refresh){
	$("#courseId").attr("disabled",true);
	$("#questionGroupId").attr("disabled",true);
	if(refresh){
		viewQuestion.refreshComboBox();
	}
};
viewQuestion.enableComboBox = function(refresh){
	$("#courseId").attr("disabled",false);
	$("#questionGroupId").attr("disabled",false);
	if(refresh){
		viewQuestion.refreshComboBox();
	}
};
viewQuestion.refreshComboBox = function(){
	$("#courseId").trigger("liszt:updated");
	$("#questionGroupId").trigger("liszt:updated");
};
viewQuestion.initQuestionGroupComboBox = function(questionGroupName){
	$("#questionGroupId").load(application.contextPath+"/management/questionGroupComboBox.html",{courseId:$("#courseId").val()},function(){
		if(questionGroupName){
			$("#questionGroupId option").filter(function() {
			    return $(this).text() == questionGroupName; 
			}).attr('selected', true);
		}
		$(this).trigger("liszt:updated");
		$('.parent-holder').unblock();
	});
};
viewQuestion.initComboBoxWithValue = function(courseCode,questionGroupName){
	$('.parent-holder').block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$("#courseId option").filter(function() {
		    return $(this).text() == courseCode; 
		}).attr('selected', true);
		$(this).trigger("liszt:updated");
		viewQuestion.initQuestionGroupComboBox(questionGroupName);
	});
};

$(document).ready(function(){
	viewQuestion.saveQuestionButton = $("#saveQuestionButton");
	$('#answerTabNav a:first').tab('show');
	$('#questionGroupId').chosen();
	$('#courseId').chosen().change(function(){
		$('.parent-holder').block(application.blockOption);
		viewQuestion.initQuestionGroupComboBox(null);
	});
	viewQuestion.calAnswer();
	$(".ckeditorText").ckeditor();
	$("#editQuestionButton").click(function(){
		viewQuestion.oldQuestionText = $(".question-panel").html();
		$("#questionText").val($(".question-panel").html());
		$("#editQuestionModal").modal('show');
	});
	$("#editParentButton").click(function(){
		$(this).hide();
		$("#saveParentButton").show();
		$("#cancelParentButton").show();
		viewQuestion.enableComboBox(false);
		viewQuestion.oldCourseCode = $("#courseId option:selected").text();
		viewQuestion.oldQuestionGroupName = $("#questionGroupId option:selected").text();
		viewQuestion.initComboBoxWithValue(viewQuestion.oldCourseCode,viewQuestion.oldQuestionGroupName);
		viewQuestion.oldCourseId = $("#courseId").val();
		viewQuestion.oldQuestionGroupId = $("#questionGroupId").val();		
	});
	$("#cancelParentButton").click(function(){
		viewQuestion.disableComboBox(false);
		viewQuestion.initComboBoxWithValue(viewQuestion.oldCourseCode,viewQuestion.oldQuestionGroupName);
		$("#saveParentButton").hide();
		$("#cancelParentButton").hide();
		$("#editParentButton").show();
	});
	$("#saveParentButton").click(function(){
		if(viewQuestion.oldCourseId != $("#courseId").val() 
				|| viewQuestion.oldQuestionGroupId != $("#questionGroupId").val()){
			$('.parent-holder').block(application.blockOption);
			$.ajax({
				url: application.contextPath+'/management/question/view.html'
				,type: 'POST'
				,data: {
					method: 'editQuestionParent'
					,courseId: $("#courseId").val()
					,questionGroupId: $("#questionGroupId").val()
					,questionId: $("#questionId").val()
				},success: function(){
					viewQuestion.disableComboBox(true);
					$('.parent-holder').unblock();
					applicationScript.saveComplete();
					$("#saveParentButton").hide();
					$("#cancelParentButton").hide();
					$("#editParentButton").show();
				},error: function(){
					$('.parent-holder').unblock();
					applicationScript.errorAlert();
				}
			});
		}else{
			applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
			$("#saveParentButton").hide();
			$("#cancelParentButton").hide();
			$("#editParentButton").show();
			viewQuestion.disableComboBox(true);
		}
	});
	viewQuestion.saveQuestionButton.click(function(){
		viewQuestion.saveQuestionButton.button('loading');
		if($("#questionText").val().length==0){
			applicationScript.errorAlertWithStringTH("กรุณากรอกคำถาม");
			viewQuestion.saveQuestionButton.button('reset');
		}else{
			if( viewQuestion.oldQuestionText != $("#questionText").val()){
				$("#questionHolder").block(application.blockOption);
				$.ajax({
					url: application.contextPath+'/management/question/view.html'
					,type: 'POST'
					,data: {
						method: 'editQuestionText'
						,questionId: $("#questionId").val()
						,questionText: $("#questionText").val()
					},success: function(){
						viewQuestion.saveQuestionButton.button('reset');
						applicationScript.saveComplete();
						$("#editQuestionModal").modal("hide");
						$(".question-panel").html($("#questionText").val());
						$("#questionHolder").unblock();
					},error: function(){
						viewQuestion.saveQuestionButton.button('reset');
						applicationScript.errorAlert();
						$("#editQuestionModal").modal("hide");
						$("#questionHolder").unblock();
					}
				});
			}else{
				viewQuestion.saveQuestionButton.button('reset');
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				$("#editQuestionModal").modal("hide");
			}
		}
	});
	$("#deleteAnswerButton").click(function(){
		$(this).button('loading');
		$.ajax({
			url: application.contextPath+'/management/question/view.html'
			,type: 'POST'
			,data: {
				method: 'deleteAnswer',
				answerId: viewQuestion.currentAnswerId
			},success: function(){
				applicationScript.deleteComplete();
				$("#deleteAnswerButton").button('reset');
				$("#confirmDelete").modal('hide');
				$("#answer-"+viewQuestion.currentAnswerId).remove();
				$("#answer-menu-"+viewQuestion.currentAnswerId).remove();
				$('#answerTabNav a:first').tab('show');
				viewQuestion.calAnswer();
			},error: function(){
				applicationScript.errorAlert();
				$("#deleteAnswerButton").button('reset');
				$("#confirmDelete").modal('hide');
			}
		});
	});
	$("#addAnswerButton").click(function(){
		viewQuestion.isEdit = false;
		$("#answerId").val('');
		$("#answerText").val('');
		$("#answerScoreFool").attr('checked',true).attr('disabled',false);
		$("#answerScoreCorrect").attr('disabled',false);
		$("#editAnswerModal .modal-header h3").text("Add Answer").closest('.modal').modal('show');
	});
	$("#saveAnswerButton").click(function(){
		if($("#answerText").val().length==0){
			applicationScript.errorAlertWithStringTH("กรุณากรอกคำตอบ");
		}else{
			$("#saveAnswerButton").button('loading');
			if(viewQuestion.isEdit){
				viewQuestion.editAnswer();
			}else{
				viewQuestion.addAnswer();
			}
		}
	});
});

editAnswer = function(answerId){
	viewQuestion.currentAnswerId = answerId;
	viewQuestion.isEdit = true;
	viewQuestion.currentAnswerScore =  $("#answer-score-"+answerId).val();
	$("#answerId").val(answerId);
	$("#answerText").val($('#answer-panel-'+answerId).text());
	
	viewQuestion.oldAnswerText =  $("#answerText").val();
	viewQuestion.oldAnswerId = answerId;
	viewQuestion.oldAnswerScore = viewQuestion.currentAnswerScore;
	
	if(viewQuestion.currentAnswerScore==0){
		$("#answerScoreFool").attr('checked',true);
	}else{
		$("#answerScoreCorrect").attr('checked',true);
	}
	if((viewQuestion.currentAnswerScore == 0 && $("#numOfFoolAnswer").text() <=3)||(viewQuestion.currentAnswerScore==1 && $("#numOfCorrectAnswer").text() <= 1)){
		$("input[name=answerScore]").attr('disabled',true);
	}else{
		$("input[name=answerScore]").attr('disabled',false);
	}
	$("#editAnswerModal .modal-header h3").text("Edit Answer").closest('.modal').modal('show');
};

deleteAnswer = function(answerId){
	viewQuestion.currentAnswerId = answerId;
	viewQuestion.currentAnswerScore =  $("#answer-score-"+answerId).val();
	if(viewQuestion.currentAnswerScore == 0 && $("#numOfFoolAnswer").text() <=3){
		applicationScript.errorAlertWithStringTH("ไม่สามารถลบคำตอบได้ เนื่องจากต้องมีคำตอบหลอกอย่างน้อย 3 คำตอบ");
	}else if (viewQuestion.currentAnswerScore==1 && $("#numOfCorrectAnswer").text() <= 1){
		applicationScript.errorAlertWithStringTH("ไม่สามารถลบคำตอบได้ เนื่องจากต้องมีคำตอบที่ถูกต้องอย่างน้อย 1 คำตอบ");
	}else{
		$("#confirmDelete").modal('show');
	}
};