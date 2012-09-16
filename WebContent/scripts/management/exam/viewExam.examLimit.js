viewExam.examLimit = {};
viewExam.examLimit.examLimit = '';
viewExam.examLimit.backupExamLimit = '';

viewExam.examLimit.checkDirty = function(){
 var isDirty = false;
 if(viewExam.examLimit.examLimit != viewExam.examLimit.backupExamLimit){
	 isDirty = true;
 }
 return isDirty;
};

viewExam.examLimit.validateExamLimit = function(){
	var haveError = false;
	$("#examLimitGroup").removeClass('success').removeClass('error');
	$("#examLimitError").remove();
	viewExam.examLimit.examLimit = parseInt($("#examLimit").val(),10);
	if(isNaN(viewExam.examLimit.examLimit) ||viewExam.examLimit.examLimit < 1 || viewExam.examLimit.examLimit >=999){
		haveError = true;
		$("#examLimitGroup").addClass('error');
		$('<label class="generate-label error" id="examLimitError">กรุณากรอกจำนวนครั้งในการสอบให้ถูกต้อง</label>').insertAfter('#editExamLimitButtonGroup');
	}else{
		$("#examLimitGroup").addClass('success');
	}
	return !haveError;
};

viewExam.examLimit.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.examLimit.backupExamLimit = viewExam.examLimit.examLimit;
	$(".normal-exam-limit-button").hide();
	$(".edit-exam-limit-button").show();
	$("#examLimit").val(viewExam.examLimit.examLimit).attr("disabled",false).focus().select();

	$("#tab1").unblock();
};
viewExam.examLimit.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.examLimit.examLimit = viewExam.examLimit.backupExamLimit;
	$(".normal-exam-limit-button").show();
	$(".edit-exam-limit-button").hide();
	$("#examLimit").val(viewExam.examLimit.backupExamLimit+" ครั้ง").attr("disabled",true);
	$("#examLimit").closest('.control-group').removeClass('success').removeClass('error');
	$("#examLimitError").remove();
	delete viewExam.examLimit.backupExamLimit;
	$("#tab1").unblock();
};


$(document).ready(function(){
	viewExam.examLimit.examLimit = application.exam.examLimit;
	$("#editExamLimitButton").click(function(){ viewExam.examLimit.beginEdit(); });
	$("#cancelExamLimitButton").click(function(){ viewExam.examLimit.cancelEdit(); });
	$("#examLimit").numeric({ decimal: false, negative: false }).keyup(function(){ viewExam.examLimit.validateExamLimit(); });
	$("#saveExamLimitButton").click(function(){
		if(viewExam.examLimit.validateExamLimit()){
			if(viewExam.examLimit.checkDirty()){
				$("#genericModalBody").text("คุณต้องการแก้ไขจำนวนครั้งในการสอบใช่หรือไม่ ?");
				$("#confirmGenericModal").modal('show');
				$("#confirmGenericButton").attr("onClick","editExamLimit()");
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.examLimit.cancelEdit();
			}
		}
	});
});

editExamLimit = function(){
	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editExamLimit'
			,examId: application.exam.examId
			,examLimit: viewExam.examLimit.examLimit
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			application.exam.examLimit = viewExam.examLimit.examLimit;

			$("#examLimit").val(viewExam.examLimit.examLimit+" ครั้ง").attr("disabled",true);
			$("#examLimit").closest('.control-group').removeClass('success').removeClass('error');
			$("#examLimitError").remove();
			
			delete viewExam.examLimit.backupExamLimit;
			$(".normal-exam-limit-button").show();
			$(".edit-exam-limit-button").hide();
			
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