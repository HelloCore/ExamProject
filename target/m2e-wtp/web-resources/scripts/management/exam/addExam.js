addExam = {};
addExam.initToday = function(){
	var today = new Date();
	var date = today.getDate();
	var month = today.getMonth() +1;
	var year = today.getFullYear();
	var todayStr = null;
	if(date<10){ todayStr = "0"+date; }else{ todayStr = date; }
	if(month<10){ todayStr += "/0"+month; }else{ todayStr += "/"+month; }
	todayStr += "/"+year;
	
	$("#startDate").val(todayStr).datepicker();
	$("#endDate").val(todayStr).datepicker();

	$("#startTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'});
	$("#endTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'});
};

addExam.validateTab1 = function(){
	$('.generate-label').remove();
	$('.error').removeClass('error');
	var validatePass = true;
	validatePass = (validatePass == addExam.validateExamHeader());
	validatePass = (validatePass == addExam.validateDate());
	validatePass = (validatePass ==addExam.validateNumQuestion());
	validatePass = (validatePass== addExam.validateTestCount());
	return validatePass;
};

addExam.validateExamHeader = function(){
	$("#examHeaderError").remove();
	$("#examHeader").closest('.control-group').removeClass('success').removeClass('error');
	var haveError = false;
	if($("#examHeader").val().length <5 ||$("#examHeader").val().length >30 ){
		$('<label for="examHeader" class="generate-label error" id="examHeaderError">กรุณากรอกหัวข้อ มีความยาว 5 - 30 ตัวอักษร</label>').insertAfter('#examHeader');
		$("#examHeader").closest('.control-group').addClass('error');
		haveError = true;
	}else{
		$("#examHeader").closest('.control-group').addClass('success');
	}
	return !haveError;
};

addExam.validateDate = function(){
	$("#startDateGroup").removeClass('success').removeClass('error');
	$("#endDateGroup").removeClass('success').removeClass('error');
	$("#dateError").remove();
	var haveError = false;
	if($("#useStartDate").is(":checked") && $("#useEndDate").is(":checked")){
		var startDateStr = $("#startDate").val();
		var startTimeStr = $("#startTime").val();
		var startDay = startDateStr.substr(0,2);
		var startMonth = startDateStr.substr(3,2);
		var startYear = startDateStr.substr(6,4);
		var startHour = startTimeStr.substr(0,2);
		var startMin = startTimeStr.substr(3,2);
		
		var endDateStr = $("#endDate").val();
		var endTimeStr = $("#endTime").val();
		var endDay = endDateStr.substr(0,2);
		var endMonth = endDateStr.substr(3,2);
		var endYear = endDateStr.substr(6,4);
		var endHour = endTimeStr.substr(0,2);
		var endMin = endTimeStr.substr(3,2);
		

		var startDate = new Date(startYear,startMonth,startDay,startHour,startMin);
		var endDate = new Date(endYear,endMonth,endDay,endHour,endMin);
		if(startDate>=endDate){
			$("#startDateGroup").addClass('error');
			$("#endDateGroup").addClass('error');
			$('<label class="generate-label error" id="dateError">วันหมดเขตสอบต้องอยู่หลังวันเริ่มสอบ</label>').insertAfter('#endTimeIcon');
			haveError = true;
		}else{
			$("#startDateGroup").addClass('success');
			$("#endDateGroup").addClass('success');
		}
	}else{
		$("#startDateGroup").addClass('success');
		$("#endDateGroup").addClass('success');
	}
	return !haveError;
};

addExam.validateNumQuestion = function(){
	var haveError = false;
	$("#numQuestionGroup").removeClass("success").removeClass("error");
	$("#numQuestionError").remove();
	if($("#minQuestion").val().length ==0 || $("#maxQuestion").val().length ==0){
		$("#numQuestionGroup").addClass("error");
		$('<label class="generate-label error" id="numQuestionError">กรุณากรอกจำนวนข้อสอบ</label>').insertAfter('#maxQuestion');
		haveError = true;
	}else if ($("#minQuestion").val()>$("#maxQuestion").val()){
		$("#numQuestionGroup").addClass("error");
		$('<label class="generate-label error" id="numQuestionError">กรุณากรอกจำนวนข้อสอบให้ถูกต้อง</label>').insertAfter('#maxQuestion');
		haveError = true;
	}else{
		$("#numQuestionGroup").addClass("success");
	}
	return !haveError;
};

addExam.validateQuestionGroup = function(){
	var haveError = false;
	$("#questionGroup").removeClass("success").removeClass("error");
	$("#questionGroupError").remove();
	if(!$("#questionGroupId").val()){
		$("#questionGroup").addClass("error");
		$('<label class="generate-label error" id="questionGroupError">กรุณาเลือกกลุ่มคำถาม</label>').insertAfter('#questionGroupId_chzn');
		haveError = true;
	}else{
		$("#questionGroup").addClass("success");
	}
	return !haveError;
};


addExam.validateTestCount = function(){
	var haveError = false;
	$("#testCountGroup").removeClass("success").removeClass("error");
	$("#testCountError").remove();
	if($("#testCount").val().length ==0){
		$("#testCountGroup").addClass('error');
		$('<label class="generate-label error" id="testCountError">กรุณากรอกจำนวนครั้งในการสอบ</label>').insertAfter('#testCount');
		haveError = true;
	}else if ($("#testCount").val() == 0){
		$("#testCountGroup").addClass('error');
		$('<label class="generate-label error" id="testCountError">กรุณากรอกจำนวนครั้งในการสอบให้ถูกต้อง</label>').insertAfter('#testCount');
		haveError = true;
	}else{
		$("#testCountGroup").addClass('success');
	}
	return !haveError;
};

addExam.validateSectionId = function(){
	var haveError = false;
	$("#sectionGroup").removeClass("success").removeClass("error");
	$("#sectionError").remove();
	if(!$("#sectionId").val()){
		$("#sectionGroup").addClass("error");
		$('<label class="generate-label error" id="sectionError">กรุณาเลือกกลุ่มคำถาม</label>').insertAfter('#sectionId_chzn');
		haveError = true;
	}else{
		$("#sectionGroup").addClass("success");
	}
	return !haveError;
};

addExam.validateQuestionPercent = function(){
	$("#totalPercent").removeClass("success").removeClass("error");
	
	var haveError = false;
	var percent = 0;
	$('.input-percent').each(function(){
		percent += parseInt($(this).val());
	});
	$("#totalPercent").text(parseInt(percent));
	if(percent != 100){
		$("#totalPercent").addClass("error");
		haveError = true;
	}else{
		$("#totalPercent").addClass("success");
	}
	return !haveError;
};

$(document).ready(function(){
	$("#courseId").chosen();
	addExam.initToday();
	$( "#testSort" ).sortable({
		placeholder: "placeholder"
	}).disableSelection();
	$("#tab1NextButton").click(function(){
//		if(addExam.validateTab1){
//			$("#tab1").hide();
//			$("#tab2").show();
//		}
		$("#tab1").hide();
		$("#tab2").show();
	});
	
	$("#examHeader").keyup(function(){addExam.validateExamHeader();});
	$(".date-check").change(function(){addExam.validateDate();});
	$("#startDate").on('changeDate',function(){addExam.validateDate();});
	$("#endDate").on('changeDate',function(){addExam.validateDate();});
	$(".num-question").numeric().keyup(function(){addExam.validateNumQuestion();});
	$("#testCountGroup").numeric().keyup(function(){addExam.validateTestCount();});
	$("#questionGroupId").chosen().change(function(){addExam.validateQuestionGroup();});
	$("#sectionId").chosen().change(function(){addExam.validateSectionId();});
	$('.input-percent').numeric().keyup(function(){
		addExam.validateQuestionPercent();
	});
});