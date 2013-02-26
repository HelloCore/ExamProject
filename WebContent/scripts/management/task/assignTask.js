application.page="task";

assignTask = {};

assignTask.allValidate = function(){
	var isValid = true;
	if(!assignTask.validateCourseId()){ isValid = false; }
	if(!assignTask.validateSectionId()){ isValid = false; }
	if(!assignTask.validateTaskName()){ isValid = false; }
	if(!assignTask.validateTaskDesc()){ isValid = false; }
	if(!assignTask.validateDate()){ isValid = false; }
	if(!assignTask.validateNumOfFile()){ isValid = false; }
	if(!assignTask.validateLimitFileSizeKb()){ isValid = false; }
	if(!assignTask.validateMaxScore()){ isValid = false; }
	return isValid;
};

assignTask.validateCourseId = function(){
	var isValid = true,courseIdGroup = $("#courseIdGroup").removeClass('error success');
	$("#courseIdError").remove();
	if($("#courseId").val().length == 0){
		isValid = false;
		$('<label class="generate-label error" id="courseIdError"><i class="fam-exclamation"></i> กรุณาเลือกวิชา</label>').insertAfter('#s2id_courseId');
		courseIdGroup.addClass('error');
	}else{
		courseIdGroup.addClass('success');
	}
	return isValid;
};

assignTask.validateSectionId = function(){
	var isValid = true,sectionIdGroup = $("#sectionIdGroup").removeClass('error success');
	$("#sectionIdError").remove();
	if(!$("#sectionId").val() || $("#sectionId").val().length == 0){
		isValid = false;
		$('<label class="generate-label error" id="sectionIdError"><i class="fam-exclamation"></i> กรุณาเลือก Section</label>').insertAfter('#s2id_sectionId');
		sectionIdGroup.addClass('error');
	}else{
		sectionIdGroup.addClass('success');
	}
	return isValid;
};

assignTask.validateTaskName = function(){
	var isValid = true,taskNameGroup = $("#taskNameGroup").removeClass('error success');
	$("#taskNameError").remove();
	if($("#taskName").val().length < 4 || $("#taskName").val().length > 50){
		isValid = false;
		taskNameGroup.addClass('error');
		$('<label class="generate-label error" id="taskNameError"><i class="fam-exclamation"></i> หัวข้องานต้องมีความยาว 4 ถึง 50 ตัวอักษร</label>').insertAfter('#taskName');
	}else{
		taskNameGroup.addClass('success');
	}
	return isValid;
};

assignTask.validateTaskDesc = function(){
	var isValid = true,taskDescGroup = $("#taskDescGroup").removeClass('error success');
	$("#taskDescError").remove();
	if($("#taskDesc").val().length < 4 || $("#taskDesc").val().length > 100){
		isValid = false;
		taskDescGroup.addClass('error');
		$('<label class="generate-label error" id="taskDescError"><i class="fam-exclamation"></i> รายละเอียดงานต้องมีความยาว 4 ถึง 100 ตัวอักษร</label>').insertAfter('#taskDesc');
	}else{
		taskDescGroup.addClass('success');
	}
	return isValid;
};

assignTask.validateDate = function(){
	var startDate = Globalize.parseDate( $("#startDate").val() +' '+ $("#startTime").val(), "dd/MM/yyyy HH:mm"),
		endDate = Globalize.parseDate( $("#endDate").val() +' '+ $("#endTime").val(), "dd/MM/yyyy HH:mm"),isValid = true;
	$("#dateError").remove();
	$("#startDateGroup").removeClass('error success');
	$("#endDateGroup").removeClass('error success');
	if(startDate >= endDate){
		isValid = false;
		$("#startDateGroup").addClass('error');
		$("#endDateGroup").addClass('error');
		$('<label class="generate-label error" id="dateError"><i class="fam-exclamation"></i> วันหมดเขตสอบต้องอยู่หลังวันเริ่มสอบ</label>').insertAfter('#endTimeIcon');
	}else{
		$("#startDateGroup").addClass('success');
		$("#endDateGroup").addClass('success');
	}
	return isValid;
};

assignTask.validateNumOfFile = function(){
	var isValid = true,numOfFileGroup = $("#numOfFileGroup").removeClass('error success');
	$("#numOfFileError").remove();
	if($("#numOfFile").val().length == 0){
		isValid = false;
		numOfFileGroup.addClass('error');
		$('<label class="generate-label error" id="numOfFileError">กรุณากรอกจำนวนไฟล์</label>').insertAfter('#numOfFile');
	}else if ($("#numOfFile").val() > 10 || $("#numOfFile").val() < 1){
		isValid = false;
		numOfFileGroup.addClass('error');
		$('<label class="generate-label error" id="numOfFileError">อัพโหลดได้ไม่เกิน 10 ไฟล์ต่อ 1 งาน</label>').insertAfter('#numOfFile');	
	}else{
		numOfFileGroup.addClass('success');
	}
	return isValid;
};

assignTask.validateLimitFileSizeKb = function(){
	var isValid = true,limitFileSizeGroup = $("#limitFileSizeGroup").removeClass('error success');
	$("#limitFileSizeKbError").remove();
	if($("#limitFileSizeKb").val().length == 0){
		isValid = false;
		limitFileSizeGroup.addClass('error');
		$('<label class="generate-label error" id="limitFileSizeKbError">กรุณากรอกขนาดไฟล์</label>').insertAfter('#limitFileSizeInput');
	}else if ($("#limitFileSizeKb").val() > 204800 || $("#limitFileSizeKb").val() < 1){
		isValid = false;
		limitFileSizeGroup.addClass('error');
		$('<label class="generate-label error" id="limitFileSizeKbError">สามารถอัพโหลดได้ไม่เกิน 200MB (204800KB) ต่อ 1 งาน</label>').insertAfter('#limitFileSizeInput');	
	}else{
		limitFileSizeGroup.addClass('success');
	}
	return isValid;
};

assignTask.validateMaxScore = function(){
	var isValid = true,maxScoreGroup = $("#maxScoreGroup").removeClass('error success');
	$("#maxScoreError").remove();
	if($("#maxScore").val().length == 0){
		isValid = false;
		maxScoreGroup.addClass('error');
		$('<label class="generate-label error" id="maxScoreError">กรุณากรอกคะแนนเต็ม</label>').insertAfter('#maxScore');
	}else if ($("#maxScore").val() < 1 || $("#maxScore").val() > 10000){
		isValid = false;
		maxScoreGroup.addClass('error');
		$('<label class="generate-label error" id="maxScoreError">คะแนนเต็มควรอยู่ระหว่าง 1 ถึง 10000</label>').insertAfter('#maxScore');	
	}else{
		maxScoreGroup.addClass('success');
	}
	return isValid;
};


assignTask.initToday = function(){
	var today = new Date(),
		tomorrow = new Date(today.getTime() + (24 * 60 * 60 * 1000)),
		todayStr = Globalize.format( today, "dd/MM/yyyy"),
		tomorrowStr = Globalize.format( tomorrow, "dd/MM/yyyy");
	$("#startDate").val(todayStr).datepicker();
	$("#endDate").val(tomorrowStr).datepicker();

	$("#startTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'});
	$("#endTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'});
};

assignTask.initFunction = function(){
	$("#s2id_courseId").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2();
		$("#s2id_courseId").unblock();
		assignTask.loadSectionBox();
	});
};

assignTask.loadSectionBox =function(){
	$("#s2id_sectionId").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/sectionComboBox.html',
		type: 'POST',
		data: {
			group: 1,
			courseId: $("#courseId").val()
		},
		dataType: 'json',
		success: function(data,status){
			console.log(data);
			$("#sectionId optgroup").remove();
			$("#sectionTemplate").tmpl(data).appendTo("#sectionId");
			$("#s2id_sectionId").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
			$("#s2id_sectionId").unblock();
		}
	});
};

$(document).ready(function(){
	assignTask.initToday();
	$("#courseId").select2().change(function(){
		assignTask.validateCourseId();
		assignTask.loadSectionBox();
	});
	$("#sectionId").select2().change(function(){
		assignTask.validateSectionId();
	});
	assignTask.initFunction();
	$("#taskName").keyup(function(){assignTask.validateTaskName();});
	$("#taskDesc").keyup(function(){assignTask.validateTaskDesc();});
	$(".date-check").change(function(){assignTask.validateDate();});
	$("#startDate").on('changeDate',function(){assignTask.validateDate();});
	$("#endDate").on('changeDate',function(){assignTask.validateDate();});
	$("#numOfFile").numeric({ decimal: false, negative: false }).keyup(function(){assignTask.validateNumOfFile();});
	$("#limitFileSizeKb").numeric({ decimal: false, negative: false }).keyup(function(){assignTask.validateLimitFileSizeKb();});
	$("#maxScore").numeric({ negative: false }).keyup(function(){assignTask.validateMaxScore();});
	
	$("#assignTaskConfirmButton").click(function(){
		var startDate = Globalize.parseDate( $("#startDate").val() +' '+ $("#startTime").val(), "dd/MM/yyyy HH:mm"),
		endDate = Globalize.parseDate( $("#endDate").val() +' '+ $("#endTime").val(), "dd/MM/yyyy HH:mm"),
		thisButton = $(this).button('loading');
		$("#mainForm").block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/management/task/assign.html',
			type: 'POST',
			data: {
				method: 'assignTask',
				courseId: $("#courseId").val(),
				sectionIdStr: '[' + $("#sectionId").val().toString() +']',
				taskName: $("#taskName").val(),
				taskDesc: $("#taskDesc").val(),
				startDate:  Globalize.format( startDate, "yyyy-MM-dd HH:mm:ss"),
				endDate:  Globalize.format( endDate, "yyyy-MM-dd HH:mm:ss"),
				numOfFile: $("#numOfFile").val(),
				limitFileSizeKb: $("#limitFileSizeKb").val(),
				maxScore: $("#maxScore").val()
			},
			success:function(){
				$("#assignTaskConfirmModal").modal('hide');
				thisButton.button('reset');
				applicationScript.saveCompleteTH();
				assignTask.initToday();
				$("#sectionId option:selected").attr('selected',false);
				$("#sectionId").select2();
				$("#taskName").val('');
				$("#taskDesc").val('');
				$("#startTime").val('00:00');
				$("#endTime").val('00:00');
				$("#numOfFile").val('1');
				$("#limitFileSizeKb").val('1024');
				$("#maxScore").val('100');
				$("#mainForm").unblock();
				$('.success').removeClass('success');
				$('.error').removeClass('error');
			},
			error : function(data){
				thisButton.button('reset');
				$("#assignTaskConfirmModal").modal('hide');
				applicationScript.resolveError(data.responseText);
				$("#mainForm").unblock();
			}
		});
	});
	$("#assignTaskButton").click(function(){
		if(assignTask.allValidate()){
			$("#assignTaskConfirmModal").modal('show');
		}
	});
});