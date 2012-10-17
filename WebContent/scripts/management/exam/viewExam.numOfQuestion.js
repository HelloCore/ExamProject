viewExam.numOfQuestion = {};
viewExam.numOfQuestion.minQuestion = null;
viewExam.numOfQuestion.backupMinQuestion = null;

viewExam.numOfQuestion.maxQuestion = null;
viewExam.numOfQuestion.backupMaxQuestion = null;

viewExam.numOfQuestion.checkDirty = function(){
	var isDirty = false;
	if (viewExam.numOfQuestion.minQuestion != viewExam.numOfQuestion.backupMinQuestion
		|| viewExam.numOfQuestion.maxQuestion != viewExam.numOfQuestion.backupMaxQuestion){
		isDirty = true;
	}
	return isDirty;
};

viewExam.numOfQuestion.validateNumOfQuestion = function(){
	var haveError = false;
	if(application.exam.isCalScore){
		$("#maxQuestion").val($("#minQuestion").val());
	}
	viewExam.numOfQuestion.minQuestion = parseInt($("#minQuestion").val(),10);
	viewExam.numOfQuestion.maxQuestion = parseInt($("#maxQuestion").val(),10);
	$("#numOfQuestionGroup").removeClass('success').removeClass('error');
	$("#numOfQuestionError").remove();
	if(viewExam.numOfQuestion.minQuestion > viewExam.numOfQuestion.maxQuestion){
		haveError = true;
		$("#numOfQuestionGroup").addClass('error');
		$('<label class="generate-label error" id="numOfQuestionError">กรุณากรอกจำนวนคำถามให้ถูกต้อง</label>').insertAfter('#editNumOfQuestionButtonGroup');
	}else{
		$("#numOfQuestionGroup").addClass('success');
	}
	return !haveError;
};

viewExam.numOfQuestion.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.numOfQuestion.backupMinQuestion = viewExam.numOfQuestion.minQuestion;
	viewExam.numOfQuestion.backupMaxQuestion = viewExam.numOfQuestion.maxQuestion;
	$("#minQuestion").val(viewExam.numOfQuestion.minQuestion);
	$("#maxQuestion").val(viewExam.numOfQuestion.maxQuestion);
	$(".normal-num-of-question-button").hide();
	$(".edit-num-of-question-button").show();
	$("#tab1").unblock();
};

viewExam.numOfQuestion.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.numOfQuestion.minQuestion = viewExam.numOfQuestion.backupMinQuestion;
	viewExam.numOfQuestion.maxQuestion = viewExam.numOfQuestion.backupMaxQuestion;
	delete viewExam.numOfQuestion.backupMinQuestion;
	delete viewExam.numOfQuestion.backupMaxQuestion;
	$(".normal-num-of-question-button").show();
	$(".edit-num-of-question-button").hide();
	$("#numOfQuestionGroup").removeClass('success').removeClass('error');
	$("#numOfQuestionError").remove();
	$("#tab1").unblock();
};

$(document).ready(function(){
	viewExam.numOfQuestion.minQuestion = application.exam.minQuestion;
	viewExam.numOfQuestion.maxQuestion = application.exam.maxQuestion;
	if(application.exam.isCalScore){
		$("#maxQuestion").attr('disabled',true);
	}
	$("#editNumOfQuestionButton").click(function(){ viewExam.numOfQuestion.beginEdit(); });
	$("#cancelNumOfQuestionButton").click(function(){ viewExam.numOfQuestion.cancelEdit(); });
	$(".input-num-of-question").numeric({ decimal: false, negative: false }).keyup(function(){ viewExam.numOfQuestion.validateNumOfQuestion(); });
	
	$("#saveNumOfQuestionButton").click(function(){
		if(viewExam.numOfQuestion.validateNumOfQuestion()){
			if(viewExam.numOfQuestion.checkDirty()){
				$("#genericModalBody").text("คุณต้องการแก้ไขจำนวนคำถามใช่หรือไม่ ?");
				$("#confirmGenericModal").modal('show');
				$("#confirmGenericButton").attr("onClick","editNumOfQuestion()");
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.numOfQuestion.cancelEdit();
			}
		}
	});
});

editNumOfQuestion = function(){
	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editNumOfQuestion'
			,examId: application.exam.examId
			,minQuestion: viewExam.numOfQuestion.minQuestion
			,maxQuestion: viewExam.numOfQuestion.maxQuestion
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			application.exam.minQuestion = viewExam.numOfQuestion.minQuestion;
			application.exam.maxQuestion = viewExam.numOfQuestion.maxQuestion;
			
			$("#numOfQuestion").val(application.exam.minQuestion+" ถึง "+application.exam.maxQuestion+" ข้อ");
			$("#numOfQuestionGroup").removeClass('success').removeClass('error');
			$("#numOfQuestionError").remove();
			
			$(".normal-num-of-question-button").show();
			$(".edit-num-of-question-button").hide();
			
			delete viewExam.numOfQuestion.backupMinQuestion;
			delete viewExam.numOfQuestion.backupMaxQuestion;
			$("#confirmGenericButton").button('reset');
			$("#confirmGenericModal").modal('hide');
			$("#tab1").unblock();
		}
		,error: function(){
			applicationScript.errorAlert();
			$("#tab1").unblock();
			$("#confirmGenericButton").button('reset');
			$("#confirmGenericModal").modal('hide');
		}
	});
};
