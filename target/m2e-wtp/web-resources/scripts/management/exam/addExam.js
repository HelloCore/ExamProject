addExam = {};
addExam.examHeader;
addExam.courseId;
addExam.startDate;
addExam.endDate;
addExam.minQuestion;
addExam.maxQuestion;
addExam.testCount;
addExam.questionGroupId;
addExam.sectionId;
addExam.questionGroupData;
addExam.examSequence;


addExam.initFunction = function(){
	$("#courseId").chosen();
	addExam.initCourseComboBox();
	addExam.initToday();
};
addExam.initCourseComboBox = function(){
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#courseId_chzn").unblock();
	});
};
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
	var validatePass = true;
	if(!addExam.validateExamHeader()){
		validatePass = validatePass && false;
	}
	if(!addExam.validateDate()){
		validatePass = validatePass && false;
	}
	if(!addExam.validateNumQuestion()){
		validatePass = validatePass && false;
	}
	if(!addExam.validateTestCount()){
		validatePass = validatePass && false;
	}
	return validatePass;
};
addExam.validateTab2 = function(){
	var validatePass = true;
	if(!addExam.validateQuestionGroup()){
		validatePass = validatePass && false;
	}
	if(!addExam.validateSectionId()){
		validatePass = validatePass && false;
	}
	return validatePass;
};
addExam.validateTab3 = function(){
	var validatePass = true;
	if(!addExam.validateQuestionPercent()){
		validatePass = validatePass && false;
	}
	return validatePass;
};

addExam.validateExamHeader = function(){
	$("#examHeaderError").remove();
	$("#examHeader").closest('.control-group').removeClass('success').removeClass('error');
	var haveError = false;
	if($("#examHeader").val().length <5 ||$("#examHeader").val().length >30 ){
		$('<label for="examHeader" class="generate-label error" id="examHeaderError">กรุณากรอกหัวข้อ มีความยาว 5 - 30 ตัวอักษร</label>').insertAfter('#examHeader');
		$("#examHeader").closest('.control-group').addClass('error');
		return false;
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
			addExam.startDate = startDate;
			addExam.endDate = endDate;
			
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
		$('<label class="generate-label error" id="sectionError">กรุณา Section ที่มีสิทธิ์สอบ</label>').insertAfter('#sectionId_chzn');
		haveError = true;
	}else{
		$("#sectionGroup").addClass("success");
	}
	return !haveError;
};

addExam.validateQuestionPercent = function(){
	$("#totalPercent").removeClass("success").removeClass("error");
	
	var haveError = false;
	var percent = 0,nowValue;
	$('.input-percent').each(function(){
		$(this).removeClass('error');
		nowValue = parseInt($(this).val(),10);
		percent+=nowValue;
		if(nowValue==0){
			$(this).addClass('error');
			haveError = true;
		}
	});
	
	$("#totalPercent").text(parseInt(percent),10);
	if(percent != 100 || haveError){
		$("#totalPercent").addClass("error");
		haveError = true;
	}else{
		$("#totalPercent").addClass("success");
	}
	return !haveError;
};

$(document).ready(function(){
	addExam.initFunction();
	$("#tab1NextButton").click(function(){
		if(addExam.validateTab1()){
			addExam.examHeader = $("#examHeader").val();
			addExam.courseId = $("#courseId").val();
			if(!$("#useStartDate").is(":checked")){
				addExam.startDate = '';
			}
			if(!$("#useEndDate").is(":checked")){
				addExam.endDate = '';
			}
			addExam.minQuestion = $("#minQuestion").val();
			addExam.maxQuestion = $("#maxQuestion").val();
			addExam.testCount = $("#testCount").val();
			$("#tab1").hide();
			$("#tab2").show();
			addExam.tab2Data();
		}
	});
	$("#tab2BackButton").click(function(){
		$("#tab2").hide();
		$("#tab1").show();
	});
	$("#tab2NextButton").click(function(){
		if(addExam.validateTab2()){
			addExam.questionGroupId = $("#questionGroupId").val();
			addExam.sectionId = $("#sectionId").val();
			$("#tab2").hide();
			$("#tab3").show();
			addExam.tab3Data();
		}
	});
	$("#tab3BackButton").click(function(){
		$("#tab3").hide();
		$("#tab2").show();
	});
	$("#tab3NextButton").click(function(){
		if(addExam.validateTab3()){
			addExam.questionGroupData = [];
			var numCount =0;
			$('.input-percent').each(function(){
				addExam.questionGroupData[numCount] = {};
				addExam.questionGroupData[numCount].questionGroupId = $(this).attr('questionGroupId');
				addExam.questionGroupData[numCount].questionGroupName = $(this).attr('questionGroupName');
				addExam.questionGroupData[numCount].questionPercent = $(this).val();
				addExam.questionGroupData[numCount].ordinal = (numCount+1);
				numCount++;
			});
			addExam.examSequence = $("input[name=examSequence]:checked").val();
			
			$("#tab3").hide();
			$("#tab4").show();
		}
	});
	
	$( "#questionGroupSort" ).sortable({
		placeholder: "placeholder"
	}).disableSelection();
	$("#examHeader").keyup(function(){addExam.validateExamHeader();});
	$(".date-check").change(function(){addExam.validateDate();});
	$("#startDate").on('changeDate',function(){addExam.validateDate();});
	$("#endDate").on('changeDate',function(){addExam.validateDate();});
	$(".num-question").numeric().keyup(function(){addExam.validateNumQuestion();});
	$("#testCountGroup").numeric().keyup(function(){addExam.validateTestCount();});
	$("#questionGroupId").chosen().change(function(){addExam.validateQuestionGroup();});
	$("#sectionId").chosen().change(function(){addExam.validateSectionId();});
	$('.input-percent').numeric().live('keyup',function(){
		addExam.validateQuestionPercent();
	});
});

addExam.tab2Data = function(){
	$("#sectionId_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/sectionComboBox.html',
		type: 'POST',
		data: {
			courseId: addExam.courseId
		},
		dataType: 'json',
		success: function(data,status){
			var newData = '',nowSemester=null,nowYear=null,isFirst=true;
			$.each(data,function(index,value){
				if( nowSemester != value.sectionSemester || nowYear != value.sectionYear){
					nowSemester = value.sectionSemester;
					nowYear = value.sectionYear;
					if(!isFirst){
						newData+= '</optgroup>';
					}else{
						isFirst = false;
					}
					newData += '<optgroup label="เทอม '+nowSemester+' ปี '+nowYear+'">';
				}
				newData += '<option value="'+value.sectionId+'">เทอม '+value.sectionSemester+' ปี '+value.sectionYear+' ['+value.sectionName+']</option>';
			});
			$("#sectionId").empty().html(newData).trigger("liszt:updated");
			$("#sectionId_chzn").unblock();
		}
	});
	$("#questionGroupId_chzn").block(application.blockOption);
	$("#questionGroupId").load(application.contextPath+"/management/questionGroupComboBox.html",{courseId:addExam.courseId},function(){
		$(this).trigger("liszt:updated");
		$("#questionGroupId_chzn").unblock();
	});
};

addExam.tab3Data = function(){
	var str = '',groupName= '';
	$.each(addExam.questionGroupId,function(index,value){
		groupName = $("#questionGroupId option[value="+value+"]").text();
		str+= '<li>กลุ่มคำถาม <span class="input-small uneditable-input">'+groupName
				+'</span> เปอร์เซ็นต์คำถาม <input type="text" class="input-mini input-percent" value="0" id="question-group-'+value+'-percent" questionGroupId="'+value+'" questionGroupName="'+groupName+'"> %</li>';
	});
	$("#questionGroupSort").empty().html(str);
};
