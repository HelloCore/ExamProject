viewExam.editing;
viewExam.examHeader = {};
viewExam.examHeader.examHeader = '';
viewExam.examHeader.backupExamHeader = '';
viewExam.examHeader.checkDirty = function(){
	var isDirty = false; 
	if($("#examHeader").val() != viewExam.examHeader.backupExamHeader){
		isDirty = true;
	}
	return isDirty;
};
viewExam.examHeader.validateExamHeader = function(){
	$("#examHeader").closest('.control-group').removeClass('success').removeClass('error');
	$("#examHeaderError").remove();
	if($("#examHeader").val().length <5 ||$("#examHeader").val().length >30 ){
		$('<label for="examHeader" class="generate-label error" id="examHeaderError">กรุณากรอกหัวข้อ มีความยาว 5 - 30 ตัวอักษร</label>').insertAfter('#cancelExamHeaderButton');
		$("#examHeader").closest('.control-group').addClass('error');
	}else{
		$("#examHeader").closest('.control-group').addClass('success');
	}
};
viewExam.examHeader.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.examHeader.backupExamHeader = viewExam.examHeader.examHeader;
	$(".normal-header-button").hide();
	$(".edit-header-button").show();
	$("#examHeader").attr("disabled",false).focus().select();

	$("#tab1").unblock();
};
viewExam.examHeader.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.examHeader.examHeader = viewExam.examHeader.backupExamHeader;
	$(".normal-header-button").show();
	$(".edit-header-button").hide();
	$("#examHeader").val(viewExam.examHeader.backupExamHeader).attr("disabled",true);
	$("#examHeader").closest('.control-group').removeClass('success').removeClass('error');
	$("#examHeaderError").remove();
	delete viewExam.examHeader.backupExamHeader;
	$("#tab1").unblock();
};


$(document).ready(function(){
	viewExam.examHeader.examHeader = application.exam.examHeader;
	$("#editExamHeaderButton").click(function(e){
		e.preventDefault();
		viewExam.examHeader.beginEdit();
	});
	$("#cancelExamHeaderButton").click(function(e){
		e.preventDefault();
		viewExam.examHeader.cancelEdit();
	});
	$("#saveExamHeaderButton").click(function(e){
		e.preventDefault();
		if($("#examHeader").val().length >= 5 && $("#examHeader").val().length <=30 ){
			if(viewExam.examHeader.checkDirty()){
				$("#genericModalBody").text("คุณต้องการแก้ไขหัวข้อการสอบใช่หรือไม่ ?");
				$("#confirmGenericModal").modal('show');
				$("#confirmGenericButton").attr("onClick","editHeader()");
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.examHeader.cancelEdit();
			}
		}
	});
	
	$("#examHeader").keyup(function(){ viewExam.examHeader.validateExamHeader(); });
});

editHeader = function(){
	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editExamHeader'
			,examId: application.exam.examId
			,examHeader: $("#examHeader").val()
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			viewExam.examHeader.examHeader = $("#examHeader").val();
			application.exam.examHeader =viewExam.examHeader.examHeader ;
			
			$("#examHeader").attr("disabled",true).closest('.control-group').removeClass('success').removeClass('error');
			$("#examHeaderError").remove();
			$(".normal-header-button").show();
			$(".edit-header-button").hide();
			delete viewExam.examHeader.backupExamHeader;
			$("#confirmGenericButton").button('reset');
			$("#confirmGenericModal").modal('hide');
			$("#tab1").unblock();
		}
		,error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#tab1").unblock();
			$("#confirmGenericButton").button('reset');
			$("#confirmGenericModal").modal('hide');
		}
	});
};