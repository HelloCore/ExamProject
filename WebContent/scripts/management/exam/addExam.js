application.page='examManagement';

addExam = {};
addExam.examHeader;
addExam.courseId;
addExam.courseCode;
addExam.startDate;
addExam.endDate;
addExam.minQuestion;
addExam.maxQuestion;
addExam.examLimit;
addExam.questionGroupId;
addExam.sectionData;
addExam.sectionId;
addExam.questionGroupData;
addExam.examSequence;

addExam.deleteData = function(){
	delete addExam.examHeader;
	delete addExam.courseId;
	delete addExam.courseCode;
	delete addExam.startDate;
	delete addExam.endDate;
	delete addExam.minQuestion;
	delete addExam.maxQuestion;
	delete addExam.examLimit;
	delete addExam.questionGroupId;
	delete addExam.sectionData;
	delete addExam.sectionId;
	delete addExam.questionGroupData;
	delete addExam.examSequence;
};
addExam.dateToString = function(date){
	return Globalize.format( date, "yyyy-MM-dd HH:mm:ss");
};

addExam.initFunction = function(){
	$(".error").removeClass("error");
	$(".success").removeClass("success");
	$("#examHeader").val('');
	$("#minQuestion").val('');
	$("#maxQuestion").val('');
	$("#startTime").val('00:00');
	$("#endTime").val('00:00');
	$('input:checkbox:checked').attr('checked',false);
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
	todayStr = Globalize.format( today, "dd/MM/yyyy");
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
	if(!addExam.validateExamLimit()){
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
	if(!addExam.validateSecondPerQuestion()){
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

addExam.globalCalDate = function(dateStr,timeStr){
	var dateTimeStr = dateStr+' '+timeStr;
	return Globalize.parseDate( dateTimeStr, "dd/MM/yyyy HH:mm");
};

addExam.validateDate = function(){
	$("#startDateGroup").removeClass('success').removeClass('error');
	$("#endDateGroup").removeClass('success').removeClass('error');
	$("#dateError").remove();
	var haveError = false;
	if($("#useStartDate").is(":checked") && $("#useEndDate").is(":checked")){
		//var startDate = addExam.calStartDate();
		var startDate = addExam.globalCalDate($("#startDate").val(),$("#startTime").val());
		var endDate = addExam.globalCalDate($("#endDate").val(),$("#endTime").val());
		addExam.startDate = startDate;
		addExam.endDate = endDate;
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
		if($("#useStartDate").is(":checked")){
			addExam.startDate = addExam.globalCalDate($("#startDate").val(),$("#startTime").val());
		}else if ($("#useEndDate").is(":checked")){
			addExam.endDate =  addExam.globalCalDate($("#endDate").val(),$("#endTime").val());
		}
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
	}else if (parseInt($("#minQuestion").val(),10)>parseInt($("#maxQuestion").val(),10)){
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


addExam.validateExamLimit = function(){
	var haveError = false;
	$("#examLimitGroup").removeClass("success").removeClass("error");
	$("#examLimitError").remove();
	if($("#examLimit").val().length ==0){
		$("#examLimitGroup").addClass('error');
		$('<label class="generate-label error" id="examLimitError">กรุณากรอกจำนวนครั้งในการสอบ</label>').insertAfter('#examLimit');
		haveError = true;
	}else if ($("#examLimit").val() == 0){
		$("#examLimitGroup").addClass('error');
		$('<label class="generate-label error" id="examLimitError">กรุณากรอกจำนวนครั้งในการสอบให้ถูกต้อง</label>').insertAfter('#examLimit');
		haveError = true;
	}else{
		$("#examLimitGroup").addClass('success');
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

addExam.validateSecondPerQuestion = function(){
	 $(".question-group-second").removeClass("success").removeClass("error");

	var haveError = false;
	$(".question-group-second").each(function(){
		if($(this).val().length==0 || $(this).val() == 0){
			haveError = true;
			$(this).addClass("error");
		}else{
			$(this).addClass("success");
		}
	});
	return !haveError;
};

$(document).ready(function(){
	addExam.initFunction();
	$("#tab1NextButton").click(function(){
		if(addExam.validateTab1()){
			addExam.examHeader = $("#examHeader").val();
			addExam.courseId = $("#courseId").val();
			addExam.courseCode = $("#courseId option:selected").text();
			if(!$("#useStartDate").is(":checked")){
				addExam.startDate = '';
			}
			if(!$("#useEndDate").is(":checked")){
				addExam.endDate = '';
			}
			addExam.minQuestion = $("#minQuestion").val();
			addExam.maxQuestion = $("#maxQuestion").val();
			addExam.examLimit = $("#examLimit").val();
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
			addExam.sectionData = [];
			var newNumber =0;
			$.each(addExam.sectionId,function(index,value){
				addExam.sectionData[newNumber] = {};
				addExam.sectionData[newNumber].sectionId = value;
				newNumber ++;
			});
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
			var numCount =0,questionGroupId;
			$('.input-percent').each(function(){
				questionGroupId = $(this).attr('questionGroupId');
				addExam.questionGroupData[numCount] = {};
				addExam.questionGroupData[numCount].questionGroupId = questionGroupId;
				addExam.questionGroupData[numCount].questionGroupName = $(this).attr('questionGroupName');
				addExam.questionGroupData[numCount].questionPercent = $(this).val();
				addExam.questionGroupData[numCount].secondPerQuestion = $('#question-group-'+questionGroupId+'-second').val();
				addExam.questionGroupData[numCount].ordinal = (numCount+1);
				numCount++;
			});
			addExam.examSequence = $("input[name=examSequence]:checked").val();

			addExam.tab4Data();
			$("#tab3").hide();
			$("#tab4").show();
		}
	});
	$("#tab4BackButton").click(function(){
		$("#tab4").hide();
		$("#tab3").show();
	});
	$("#tab4NextButton").click(function(){
		$(this).button('loading');
		$("#tab4BackButton").button('loading');
		addExam.sendData();
	});
	
	$( "#questionGroupSort" ).sortable({
		placeholder: "placeholder"
	}).disableSelection();
	$("#examHeader").keyup(function(){addExam.validateExamHeader();});
	$(".date-check").change(function(){addExam.validateDate();});
	$("#startDate").on('changeDate',function(){addExam.validateDate();});
	$("#endDate").on('changeDate',function(){addExam.validateDate();});
	$(".num-question").numeric().keyup(function(){addExam.validateNumQuestion();});
	$("#examLimitGroup").numeric().keyup(function(){addExam.validateExamLimit();});
	$("#questionGroupId").chosen().change(function(){addExam.validateQuestionGroup();});
	$("#sectionId").chosen().change(function(){addExam.validateSectionId();});
	
	$("#orderHolder").on('keyup','#questionGroupSort li .input-percent',function(){ addExam.validateQuestionPercent(); })
		.on('keyup','#questionGroupSort li .question-group-second',function(){ addExam.validateSecondPerQuestion(); });
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
				newData += '<option value="'+value.sectionId+'"'
								+' sectionYear="'+value.sectionYear+'"'
								+' sectionSemester="'+value.sectionSemester+'"'
								+' sectionName="'+value.sectionName+'"'
								+'>เทอม '+value.sectionSemester+' ปี '+value.sectionYear+' ['+value.sectionName+']</option>';
				
			});
			$("#sectionId").empty().html(newData).trigger("liszt:updated");
			$("#sectionId_chzn").unblock();
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
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
		str+= '<li>กลุ่มคำถาม '
				+'<span class="input-small uneditable-input">'
					+groupName
				+'</span>'
				+' เปอร์เซ็นต์คำถาม <input type="text" class="input-xmini input-percent" value="0" id="question-group-'+value+'-percent" questionGroupId="'+value+'" questionGroupName="'+groupName+'"> %'
				+' เวลาสอบ <input type="text" class="input-xmini have-popover question-group-second" value="60" id="question-group-'+value+'-second" rel="popover" data-content="ให้เวลาสอบ...วินาที ต่อ 1 ข้อ" data-original-title="คำอธิบาย"> วินาที '
				+'</li>';
	});
	$("#questionGroupSort").empty().html(str);
	$(".input-percent,.question-group-second").numeric({ decimal: false, negative: false });
	$(".have-popover").popover();
};

addExam.tab4Data = function(){
	$("#courseIdConfirm").text(addExam.courseCode);
	$("#examHeaderConfirm").text(addExam.examHeader);
	if(addExam.startDate == ''){
		$("#startDateConfirm").text("ไม่กำหนด");
	}else{
		$("#startDateConfirm").text(Globalize.format(new Date(addExam.startDate),'dd-MM-yyyy HH:mm'));
	}
	if(addExam.endDate == ''){
		$("#endDateConfirm").text("ไม่กำหนด");
	}else{
		$("#endDateConfirm").text(Globalize.format(new Date(addExam.endDate),'dd-MM-yyyy HH:mm'));
	}
	$("#rangeQuestionConfirm").text(addExam.minQuestion + ' ถึง ' + addExam.maxQuestion+ ' ข้อ');
	$("#examLimitConfirm").text(addExam.examLimit);
	if(addExam.examSequence == 0){
		$("#examSequenceConfirm").text("สุ่ม");
	}else{
		$("#examSequenceConfirm").text("เรียงตามลำดับ");
	}
	$(".question-group-table tbody").empty();
	$.each(addExam.questionGroupData,function(index,value){
		$(".question-group-table tbody").append('<tr><td>'+value.ordinal+'</td><td>'+value.questionGroupName+'</td><td>'+value.questionPercent+'</td><td>'+value.secondPerQuestion+'</td></tr>');
	});

	$(".section-table tbody").empty();
	var sectionOption;
	$.each(addExam.sectionId,function(index,value){
		sectionOption = $('#sectionId option[value='+value+']');
		
		$(".section-table tbody").append('<tr>'
										+'<td>'+sectionOption.attr('sectionSemester')+'</td>'
										+'<td>'+sectionOption.attr('sectionYear')+'</td>'
										+'<td>'+sectionOption.attr('sectionName')+'</td>'
									+'</tr>');
	});
};

addExam.convertQuestionGroupData = function(questionGroupData){
	var number = 0;
	var newData = [];
	$.each(questionGroupData,function(index,value){
		newData[number] = {};
		newData[number].questionGroupId = value.questionGroupId;
		newData[number].questionPercent = value.questionPercent;
		newData[number].secondPerQuestion = value.secondPerQuestion;
		newData[number].ordinal = value.ordinal;
		number++;
	});
	return newData;
};

addExam.sendData = function(){
	var parameter = {};
	parameter.method = 'addExam';
	parameter.examHeader = addExam.examHeader;
	if(addExam.startDate != ''){
		parameter.startDate = addExam.dateToString(addExam.startDate);
	}
	if(addExam.endDate != ''){
		parameter.endDate = addExam.dateToString(addExam.endDate);
	}
	parameter.courseId = addExam.courseId;
	parameter.minQuestion = addExam.minQuestion;
	parameter.maxQuestion = addExam.maxQuestion;
	parameter.examLimit = addExam.examLimit;
	parameter.sectionData = JSON.stringify(addExam.sectionData);
	parameter.questionGroupData = JSON.stringify(addExam.convertQuestionGroupData(addExam.questionGroupData));
	parameter.examSequence = addExam.examSequence;
	
	$.ajax({
		url: application.contextPath + '/management/exam/add.html',
		type: 'POST',
		data: parameter,
		success: function(){
			applicationScript.saveComplete();
			$("#tab4BackButton").button('reset');
			$("#tab4NextButton").button('reset');
			$("#tab4").hide();
			$("#tab1").show();
			addExam.deleteData();
			addExam.initFunction();
		},
		error : function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#tab4BackButton").button('reset');
			$("#tab4NextButton").button('reset');
		}
	});
};
