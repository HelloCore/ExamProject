viewExam.examSequence = {};
viewExam.examSequence.examSequence = null;
viewExam.examSequence.backupExamSequence = null;

viewExam.examSequence.updateValue = function(callback){
	var nowValue = $("input[name=examSequence]:checked").val();
	
	if( nowValue == 0){
		viewExam.examSequence.examSequence = false;
	}else if( nowValue == 1){
		viewExam.examSequence.examSequence = true;
	}
	
	if(typeof(callback) == 'function'){
		callback();
	}
};


viewExam.examSequence.checkDirty = function(){
	var isDirty = false;
	if(viewExam.examSequence.examSequence != viewExam.examSequence.backupExamSequence){
		isDirty = true;
	}
	return isDirty;
};

viewExam.examSequence.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.examSequence.backupExamSequence = viewExam.examSequence.examSequence;
	
	if(viewExam.examSequence.examSequence){
		$("#examSequence").attr('checked',true);
	}else{
		$("#examRandom").attr('checked',true);
	}
	$(".normal-exam-sequence-button").hide();
	$(".edit-exam-sequence-button").show();
	
	$("#tab1").unblock();
};

viewExam.examSequence.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.examSequence.examSequence = viewExam.examSequence.backupExamSequence;
	
	$(".normal-exam-sequence-button").show();
	$(".edit-exam-sequence-button").hide();
	
	delete viewExam.examSequence.backupExamSequence;
	$("#tab1").unblock();
};


$(document).ready(function(){
	viewExam.examSequence.examSequence = application.exam.examSequence;

	if(viewExam.examSequence.examSequence){
		$("#examSequenceStr").val("เรียงตามลำดับ");
	}else{
		$("#examSequenceStr").val("สุ่ม");
	}
	
	$("#editExamSequenceButton").click(function(){ viewExam.examSequence.beginEdit(); });
	$("#cancelExamSequenceButton").click(function(){ viewExam.examSequence.cancelEdit(); });
	$("#saveExamSequenceButton").click(function(){ 
		viewExam.examSequence.updateValue(function(){
			if(viewExam.examSequence.checkDirty()){
				$("#genericModalBody").text("คุณต้องการแก้ไขการเรียงคำถามใช่หรือไม่ ?");
				$("#confirmGenericModal").modal('show');
				$("#confirmGenericButton").attr("onClick","editExamSequence()");
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.examSequence.cancelEdit();
			}
		});
	});
});

editExamSequence = function(){

	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editExamSequence'
			,examId: application.exam.examId
			,examSequence: $("input[name=examSequence]:checked").val()
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			
			if(viewExam.examSequence.examSequence){
				$("#examSequenceStr").val("เรียงตามลำดับ");
			}else{
				$("#examSequenceStr").val("สุ่ม");
			}

			$(".normal-exam-sequence-button").show();
			$(".edit-exam-sequence-button").hide();
			
			delete viewExam.examSequence.backupExamSequence;
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
