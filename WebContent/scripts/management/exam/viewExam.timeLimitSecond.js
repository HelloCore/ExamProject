viewExam.editing;
viewExam.timeLimitSecond = {};
viewExam.timeLimitSecond.timeLimitSecond = null;
viewExam.timeLimitSecond.backupTimeLimitSecond = null;



viewExam.timeLimitSecond.checkDirty = function(){
	var isDirty = false;
	
	if(viewExam.timeLimitSecond.backupTimeLimitSecond != viewExam.timeLimitSecond.stringToSecond($("#editTimeLimitSecond").val())){
		isDirty = true;	
	}
	
	return isDirty;
};


viewExam.timeLimitSecond.validateTimeLimitSecond = function(){
	var haveError = false;
	$("#examTimeLimitSecondGroup").removeClass('success').removeClass('error').removeClass('warning');
	$("#examTimeLimitSecondError").remove();
	
	if($("#editTimeLimitSecond").val() == "00:00" ){
		console.log(false);
		$("#examTimeLimitSecondGroup").addClass('error');
		$('<label class="generate-label error" id="examTimeLimitSecondError">กรุณากำหนดเวลาสอบ</label>').insertAfter('#editTimeLimitSecondButtonGroup');
		haveError = true;
	}
	
	if(!haveError){
		$("#examTimeLimitSecondGroup").addClass('success');
	}
	return !haveError;
};

viewExam.timeLimitSecond.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.timeLimitSecond.backupTimeLimitSecond = viewExam.timeLimitSecond.timeLimitSecond;
	$(".normal-time-limit-second-button").hide();
	$(".edit-time-limit-second-button").show();

	$("#editTimeLimitSecond").val($("#examTimeLimitSecond").val());
	$("#tab1").unblock();
};

viewExam.timeLimitSecond.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.timeLimitSecond.timeLimitSecond = viewExam.timeLimitSecond.backupTimeLimitSecond;
	$(".normal-time-limit-second-button").show();
	$(".edit-time-limit-second-button").hide();

	$("#editTimeLimitSecond").closest('.control-group').removeClass('success').removeClass('error').removeClass('warning');
	$("#examTimeLimitSecondError").remove();
	delete viewExam.timeLimitSecond.backupTimeLimitSecond;
	$("#tab1").unblock();
};

viewExam.timeLimitSecond.stringToSecond = function(str){
	var hour = parseInt(str.substring(0,2),10);
	var min = parseInt(str.substring(3,5),10);
	return ((hour*60*60)+(min*60));
};

viewExam.timeLimitSecond.secondToString = function(second){
	var hourSec = 3600,minSec = 60,tempSec = second;
	var strHour = Math.floor(tempSec / hourSec);
	tempSec -= (strHour*hourSec);
	var strMin = Math.floor(tempSec / minSec);

	if (strHour <= 9){
		strHour = "0"+strHour;
	}
	
	if (strMin <= 9){
		strMin = "0"+strMin;
	}
	return strHour+":"+strMin;
};

$(document).ready(function(){
	if(application.exam.isCalScore){
		viewExam.timeLimitSecond.timeLimitSecond = application.exam.timeLimitSecond;
		$("#examTimeLimitSecond").val(viewExam.timeLimitSecond.secondToString(viewExam.timeLimitSecond.timeLimitSecond));
		
		$("#editTimeLimitSecond").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'})
			.change(function(){ viewExam.timeLimitSecond.validateTimeLimitSecond(); });
		
		$("#editTimeLimitSecondButton").click(function(e){
			e.preventDefault();
			viewExam.timeLimitSecond.beginEdit();
		});
		$("#cancelTimeLimitSecondButton").click(function(e){
			e.preventDefault();
			viewExam.timeLimitSecond.cancelEdit();
		});
		
		$("#saveTimeLimitSecondButton").click(function(e){
			e.preventDefault();
			if(viewExam.timeLimitSecond.validateTimeLimitSecond()){
				if(viewExam.timeLimitSecond.checkDirty()){
					$("#genericModalBody").text("คุณต้องการแก้ไขเวลาสอบใช่หรือไม่ ?");
					$("#confirmGenericModal").modal('show');
					$("#confirmGenericButton").attr("onClick","editTimeLimitSecond()");
				}else{
					applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
					viewExam.timeLimitSecond.cancelEdit();
				}
			}
		});
	}
});

editTimeLimitSecond = function(){
	var timeLimitSecond = viewExam.timeLimitSecond.stringToSecond($("#editTimeLimitSecond").val());
	
	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editTimeLimitSecond'
			,examId: application.exam.examId
			,timeLimitSecond : timeLimitSecond
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			viewExam.timeLimitSecond.timeLimitSecond = timeLimitSecond;
			application.exam.timeLimitSecond = timeLimitSecond;
			$("#examTimeLimitSecond").val(viewExam.timeLimitSecond.secondToString(timeLimitSecond));

			$("#examTimeLimitSecondGroup").removeClass('success').removeClass('error').removeClass('warning');
			$("#examTimeLimitSecondError").remove();
			
			$(".normal-time-limit-second-button").show();
			$(".edit-time-limit-second-button").hide();
			
			delete viewExam.timeLimitSecond.backupTimeLimitSecond;
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
