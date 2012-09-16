viewExam.editing;
viewExam.endDate = {};
viewExam.endDate.endDate = null;
viewExam.endDate.backupEndDate = null;
viewExam.endDate.nowToday = new Date();

viewExam.endDate.toEndDate = function(callback){
	var endDateStr;
	endDateStr = $("#editEndDate").val() +" "+$("#editEndTime").val();
	viewExam.endDate.endDate = Globalize.parseDate( endDateStr, "dd/MM/yyyy HH:mm");
	if(typeof(callback) == 'function'){
		callback();
	}
};

viewExam.endDate.checkDirty = function(){
	var isDirty = false;
	if(viewExam.endDate.endDate && viewExam.endDate.backupEndDate){
		isDirty = viewExam.endDate.endDate.getTime() != viewExam.endDate.backupEndDate.getTime();
	}else if(viewExam.endDate.endDate || viewExam.endDate.backupEndDate ){ 
		isDirty = true;
	}
	return isDirty;
};

viewExam.endDate.validateEndDate = function(){
	var haveError = false,haveWarning = false;;
	$("#endDateGroup").removeClass('success').removeClass('error').removeClass('warning');
	$("#endDateError").remove();
	$("#endDateWarning").remove();
	if($("#useEndDate").is(":checked")){
		viewExam.endDate.toEndDate(function(){
			if(application.exam.startDate){
				if(viewExam.endDate.endDate<=application.exam.startDate){
					$("#endDateGroup").addClass('error');
					$('<label class="generate-label error" id="endDateError">วันหมดเขตสอบต้องอยู่หลังวันเริ่มสอบ</label>').insertAfter('#editEndDateButtonGroup');
					haveError = true;
				}
			}else if(viewExam.endDate.endDate <= viewExam.endDate.nowToday){
				$("#endDateGroup").addClass('warning');
				$('<label class="generate-label warning" id="endDateWarning">การสอบนี้หมดเขตแล้ว ?</label>').insertAfter('#editEndDateButtonGroup');
				haveWarning = true;
			}
		});
	}else{
		viewExam.endDate.endDate = null;
	}
	if(!haveWarning && !haveError){
		$("#endDateGroup").addClass('success');
	}
	return !haveError;
};
viewExam.endDate.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.endDate.backupEndDate = viewExam.endDate.endDate;
	$(".normal-end-date-button").hide();
	$(".edit-end-date-button").show();

	if(viewExam.endDate.endDate){
		$("#editEndDate").val(Globalize.format(viewExam.endDate.endDate,'dd/MM/yyyy')).datepicker('update');
		$("#editEndTime").val(Globalize.format(viewExam.endDate.endDate,'HH:mm'));
		$("#useEndDate").attr('checked',true);
	}else{
		$("#editEndDate").val(Globalize.format(viewExam.endDate.nowToday,'dd/MM/yyyy')).datepicker('update');
		$("#editEndTime").val("00:00");
		$("#useEndDate").attr('checked',false);
	}
	$("#tab1").unblock();
};
viewExam.endDate.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.endDate.endDate = viewExam.endDate.backupEndDate;
	$(".normal-end-date-button").show();
	$(".edit-end-date-button").hide();

	if(viewExam.endDate.endDate){
		$("#endDate").val(Globalize.format(viewExam.endDate.endDate,'dd-MM-yyyy HH:mm'));
	}else{
		$("#endDate").val("ไม่กำหนด");
	}
	
	$("#editEndDate").closest('.control-group').removeClass('success').removeClass('error').removeClass('warning');
	$("#editEndDateError").remove();
	$("#editEndDateWarning").remove();
	delete viewExam.endDate.backupEndDate;
	$("#tab1").unblock();
};


$(document).ready(function(){
	viewExam.endDate.endDate = application.exam.endDate;

	if(viewExam.endDate.endDate){
		$("#endDate").val(Globalize.format(viewExam.endDate.endDate,'dd-MM-yyyy HH:mm'));
	}else{
		$("#endDate").val("ไม่กำหนด");
	}
	$("#editEndDate").datepicker().on('changeDate',function(){ viewExam.endDate.validateEndDate(); });
	$("#editEndTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'})
		.change(function(){ viewExam.endDate.validateEndDate(); });
	
	$("#editEndDateButton").click(function(e){
		e.preventDefault();
		viewExam.endDate.beginEdit();
	});
	$("#cancelEndDateButton").click(function(e){
		e.preventDefault();
		viewExam.endDate.cancelEdit();
	});
	$("#saveEndDateButton").click(function(e){
		e.preventDefault();
		if(viewExam.endDate.validateEndDate()){
			if(viewExam.endDate.checkDirty()){
				$("#genericModalBody").text("คุณต้องการแก้ไขวันหมดเขตสอบใช่หรือไม่ ?");
				$("#confirmGenericModal").modal('show');
				$("#confirmGenericButton").attr("onClick","editEndDate()");
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.endDate.cancelEdit();
			}
		}
	});
	
});

editEndDate = function(){
	var endDate = Globalize.format(viewExam.endDate.endDate,'yyyy-MM-dd HH:mm:ss');
	if(endDate == null){
		endDate = '';
	}
	
	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editEndDate'
			,examId: application.exam.examId
			,endDateStr: endDate
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			application.exam.endDate = viewExam.endDate.endDate;

			$("#endDateGroup").removeClass('success').removeClass('error').removeClass('warning');
			$("#endDateError").remove();
			$("#endDateWarning").remove();
			if(viewExam.endDate.endDate){
				$("#endDate").val(Globalize.format(viewExam.endDate.endDate,'dd-MM-yyyy HH:mm'));
			}else{
				$("#endDate").val("ไม่กำหนด");
			}
			$(".normal-end-date-button").show();
			$(".edit-end-date-button").hide();
			delete viewExam.endDate.backupEndDate;
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