
$(document).ready(function(){	
	$('.havePopover').popover();
	$("#createExamButton").click(function(e){
		e.preventDefault();
		$('#createExamForm').submit();
	});
	$('#createExamForm').validate({
		rules: {
			numOfQuestion: {
		        required: true,
		        number:true,
				range: [0,999]
			}
		},
	    highlight: function(label) {
	    	$(label).closest('.control-group').removeClass('success').addClass('error');
	    },
	    success: function(label) {
	    	label
	    		.text('OK!').addClass('valid')
	    		.closest('.control-group').removeClass('error').addClass('success');
	    },
		submitHandler: function(form) {
			$("#createExamButton").button('loading');
			$(form).ajaxSubmit({
				type:'post',
				url: application.contextPath + '/exam/doExam.html',
				clearForm: false,
				success: function(data){
					$("#createExamModal").modal('show');
					$("body").block(application.blockOption);
					$("#examResultId").val(data);
					$("#doExamForm").submit();
				},
				error: function(a){
					$("#createExamButton").button('reset');
					$("#createExamModal").modal('hide');
					applicationScript.errorAlertWithString(a.responseText);
				}
			});
		}
	});
});

createExam = function(examId){
	var button = $('#do-exam-'+examId);
		minQuestion = button.attr('min-question'),
		maxQuestion = button.attr('max-question');
	$("#examId").val(examId);
	$("#examCount").val(button.attr('exam-count'));
	$("#numOfQuestion").val('').attr('placeHolder',minQuestion+' - '+maxQuestion);
	$("#numOfQuestion").rules("remove","range");
	$("#numOfQuestion").rules("add",{
		range:[minQuestion,maxQuestion],
		
		messages: {
			range: "ต้องมีจำนวนคำถาม {0} ถึง {1} ข้อ"
		}
	});
	$('#createExamForm').validate().resetForm();
	$('#createExamForm .control-group').removeClass('success').removeClass('error');
	$("#createExamModal").modal('show');
};

continueExam = function(examResultId){
	$("#continue-exam-"+examResultId).button('loading');
	$("#incomplete-table").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/exam/selectExam.html',
		type: 'POST',
		data: {
			method:'validateExamResult',
			examResultId:examResultId
		},
		success: function(){
			$("#examResultId").val(examResultId);
			$("#doExamForm").submit();
		},
		error : function(a){
			applicationScript.errorAlertWithString(a.responseText);
			$("#continue-exam-"+examResultId).button('reset');
			$("#incomplete-table").unblock();
		}
	});
};

sendExam = function(examResultId){
	$("#send-exam-"+examResultId).button('loading');
	$("#incomplete-table").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/exam/selectExam.html',
		type: 'POST',
		data: {
			method:'sendExam',
			examResultId:examResultId
		},
		success: function(isExpired){
			if(isExpired==true){
				applicationScript.errorAlertWithStringHeader("คุณส่งข้อสอบช้ากว่าที่กำหนด การสอบนี้คุณได้ 0 คะแนน","ส่งข้อสอบช้า");
				$("#send-exam-"+examResultId).button('reset');
				$("#exam-incomplete-"+examResultId).remove();
				$("#incomplete-table").unblock();
			}else{
				applicationScript.saveComplete();
				$("#send-exam-"+examResultId).button('reset');
				$("#incomplete-table").unblock();
				
				// redirect to showResult
			}
		},
		error : function(a){
			applicationScript.errorAlertWithString(a.responseText);
			$("#send-exam-"+examResultId).button('reset');
		}
	});
};