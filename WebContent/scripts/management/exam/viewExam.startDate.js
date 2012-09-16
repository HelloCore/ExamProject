viewExam.editing;
viewExam.startDate = {};
viewExam.startDate.startDate = null;
viewExam.startDate.backupStartDate = null;

viewExam.startDate.toStartDate = function(callback){
	var startDateStr;
	startDateStr = $("#editStartDate").val() +" "+$("#editStartTime").val();
	viewExam.startDate.startDate = Globalize.parseDate( startDateStr, "dd/MM/yyyy HH:mm");
	if(typeof(callback) == 'function'){
		callback();
	}
};

viewExam.startDate.checkDirty = function(){
	var isDirty = false;
	if(viewExam.startDate.startDate && viewExam.startDate.backupStartDate){
		isDirty = viewExam.startDate.startDate.getTime() != viewExam.startDate.backupStartDate.getTime();
	}else if(viewExam.startDate.startDate || viewExam.startDate.backupStartDate ){ 
		isDirty = true;
	}
	return isDirty;
};

viewExam.startDate.validateStartDate = function(){
	var haveError = false;
	$("#startDateGroup").removeClass('success').removeClass('error');
	$("#startDateError").remove();
	if($("#useStartDate").is(":checked") && application.exam.endDate){
		viewExam.startDate.toStartDate(function(){
			if(viewExam.startDate.startDate>=application.exam.endDate){
				$("#startDateGroup").addClass('error');
				$('<label class="generate-label error" id="startDateError">วันเริ่มสอบต้องอยู่ก่อนวันหมดเขตสอบ</label>').insertAfter('#editStartDateButtonGroup');
				haveError = true;
			}else{
				$("#startDateGroup").addClass('success');
			}
		});
	}else if ($("#useStartDate").is(":checked") ){
		viewExam.startDate.toStartDate();
		$("#startDateGroup").addClass('success');
	}else{
		viewExam.startDate.startDate = null;
		$("#startDateGroup").addClass('success');
	}
	return !haveError;
};
viewExam.startDate.beginEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.startDate.backupStartDate = viewExam.startDate.startDate;
	$(".normal-start-date-button").hide();
	$(".edit-start-date-button").show();

	if(viewExam.startDate.startDate){
		$("#editStartDate").val(Globalize.format(viewExam.startDate.startDate,'dd/MM/yyyy')).datepicker('update');
		$("#editStartTime").val(Globalize.format(viewExam.startDate.startDate,'HH:mm'));
		$("#useStartDate").attr('checked',true);
	}else{
		$("#editStartDate").val(Globalize.format(new Date(),'dd/MM/yyyy')).datepicker('update');
		$("#editStartTime").val("00:00");
		$("#useStartDate").attr('checked',false);
	}
	$("#tab1").unblock();
};
viewExam.startDate.cancelEdit = function(){
	$("#tab1").block(application.blockOption);
	viewExam.startDate.startDate = viewExam.startDate.backupStartDate;
	$(".normal-start-date-button").show();
	$(".edit-start-date-button").hide();

	if(viewExam.startDate.startDate){
		$("#startDate").val(Globalize.format(viewExam.startDate.startDate,'dd-MM-yyyy HH:mm'));
	}else{
		$("#startDate").val("ไม่กำหนด");
	}
	
	$("#editStartDate").closest('.control-group').removeClass('success').removeClass('error');
	$("#editStartDateError").remove();
	delete viewExam.startDate.backupStartDate;
	$("#tab1").unblock();
};


$(document).ready(function(){
	viewExam.startDate.startDate = application.exam.startDate;

	if(viewExam.startDate.startDate){
		$("#startDate").val(Globalize.format(viewExam.startDate.startDate,'dd-MM-yyyy HH:mm'));
	}else{
		$("#startDate").val("ไม่กำหนด");
	}
	$("#editStartDate").datepicker().on('changeDate',function(){ viewExam.startDate.validateStartDate(); });
	$("#editStartTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'})
		.change(function(){ viewExam.startDate.validateStartDate(); });
	
	$("#editStartDateButton").click(function(e){
		e.preventDefault();
		viewExam.startDate.beginEdit();
	});
	$("#cancelStartDateButton").click(function(e){
		e.preventDefault();
		viewExam.startDate.cancelEdit();
	});
	$("#saveStartDateButton").click(function(e){
		e.preventDefault();
		if(viewExam.startDate.validateStartDate()){
			if(viewExam.startDate.checkDirty()){
				$("#genericModalBody").text("คุณต้องการแก้ไขวันเริ่มสอบใช่หรือไม่ ?");
				$("#confirmGenericModal").modal('show');
				$("#confirmGenericButton").attr("onClick","editStartDate()");
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.startDate.cancelEdit();
			}
		}
	});
	
});

editStartDate = function(){
	var startDate = Globalize.format(viewExam.startDate.startDate,'yyyy-MM-dd HH:mm:ss');
	if(startDate == null){
		startDate = '';
	}
	
	$("#tab1").block(application.blockOption);
	$("#confirmGenericButton").button('loading');
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editStartDate'
			,examId: application.exam.examId
			,startDateStr: startDate
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			application.exam.startDate = viewExam.startDate.startDate;

			$("#startDateGroup").removeClass('success').removeClass('error');
			$("#startDateError").remove();
			if(viewExam.startDate.startDate){
				$("#startDate").val(Globalize.format(viewExam.startDate.startDate,'dd-MM-yyyy HH:mm'));
			}else{
				$("#startDate").val("ไม่กำหนด");
			}
			
			$("#startDateError").remove();
			$(".normal-start-date-button").show();
			$(".edit-start-date-button").hide();
			delete viewExam.startDate.backupStartDate;
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

//editHeader = function(){
//	$("#tab1").block(application.blockOption);
//	$("#confirmGenericButton").button('loading');
//	$.ajax({
//		url: application.contextPath+'/management/exam/view.html'
//		,data: {
//			method: 'editExamHeader'
//			,examId: application.exam.examId
//			,examHeader: $("#examHeader").val()
//		}
//		,type: 'POST'
//		,success: function(data){
//			applicationScript.saveComplete();
//			viewExam.examHeader.examHeader = $("#examHeader").val();
//			$("#examHeader").attr("disabled",true).closest('.control-group').removeClass('success').removeClass('error');
//			$("#examHeaderError").remove();
//			$(".normal-header-button").show();
//			$(".edit-header-button").hide();
//			delete viewExam.examHeader.backupExamHeader;
//			$("#confirmGenericButton").button('reset');
//			$("#confirmGenericModal").modal('hide');
//			$("#tab1").unblock();
//		}
//		,error: function(){
//			applicationScript.errorAlert();
//			$("#tab1").unblock();
//			$("#confirmGenericButton").button('reset');
//			$("#confirmGenericModal").modal('hide');
//		}
//	});
//};