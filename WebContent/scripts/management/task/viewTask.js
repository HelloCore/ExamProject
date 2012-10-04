application.page="task";

viewTask = {};

viewTask.checkDirty = function(){
	var isDirty = false;
	if(application.courseId != $("#courseId").val()){
		isDirty = true;
	}else if (application.sectionIdStr != '[' + $("#sectionId").val().toString() +']'){
		isDirty = true;
	}else if (application.taskName != $("#taskName").val()){
		isDirty = true;
	}else if ($("#oldTaskDesc").val() != $("#taskDesc").val()){
		isDirty = true;
	}else if (application.startDate.getTime() != Globalize.parseDate( $("#startDate").val() +' '+ $("#startTime").val(), "dd/MM/yyyy HH:mm").getTime()){
		isDirty = true;
	}else if (application.endDate.getTime() != Globalize.parseDate( $("#endDate").val() +' '+ $("#endTime").val(), "dd/MM/yyyy HH:mm").getTime()){
		isDirty = true;
	}else if (application.numOfFile != $("#numOfFile").val()){
		isDirty = true;
	}else if (application.limitFileSizeKb != $("#limitFileSizeKb").val()){
		isDirty = true;
	}else if (application.maxScore != $("#maxScore").val()){
		isDirty = true;
	}
	return isDirty;
};

viewTask.allValidate = function(){
	var isValid = true;
	if(!viewTask.validateCourseId()){ isValid = false; }
	if(!viewTask.validateSectionId()){ isValid = false; }
	if(!viewTask.validateTaskName()){ isValid = false; }
	if(!viewTask.validateTaskDesc()){ isValid = false; }
	if(!viewTask.validateDate()){ isValid = false; }
	if(!viewTask.validateNumOfFile()){ isValid = false; }
	if(!viewTask.validateLimitFileSizeKb()){ isValid = false; }
	if(!viewTask.validateMaxScore()){ isValid = false; }
	return isValid;
};

viewTask.validateCourseId = function(){
	var isValid = true,courseIdGroup = $("#courseIdGroup").removeClass('error').removeClass('success');
	$("#courseIdError").remove();
	if($("#courseId").val().length == 0){
		isValid = false;
		$('<label class="generate-label error" id="courseIdError">กรุณาเลือกวิชา</label>').insertAfter('#courseId_chzn');
		courseIdGroup.addClass('error');
	}else{
		courseIdGroup.addClass('success');
	}
	return isValid;
};

viewTask.validateSectionId = function(){
	var isValid = true,sectionIdGroup = $("#sectionIdGroup").removeClass('error').removeClass('success');
	$("#sectionIdError").remove();
	if(!$("#sectionId").val() || $("#sectionId").val().length == 0){
		isValid = false;
		$('<label class="generate-label error" id="sectionIdError">กรุณาเลือก Section</label>').insertAfter('#sectionId_chzn');
		sectionIdGroup.addClass('error');
	}else{
		sectionIdGroup.addClass('success');
	}
	return isValid;
};

viewTask.validateTaskName = function(){
	var isValid = true,taskNameGroup = $("#taskNameGroup").removeClass('error').removeClass('success');
	$("#taskNameError").remove();
	if($("#taskName").val().length < 4 || $("#taskName").val().length > 50){
		isValid = false;
		taskNameGroup.addClass('error');
		$('<label class="generate-label error" id="taskNameError">หัวข้องานต้องมีความยาว 4 ถึง 50 ตัวอักษร</label>').insertAfter('#taskName');
	}else{
		taskNameGroup.addClass('success');
	}
	return isValid;
};

viewTask.validateTaskDesc = function(){
	var isValid = true,taskDescGroup = $("#taskDescGroup").removeClass('error').removeClass('success');
	$("#taskDescError").remove();
	if($("#taskDesc").val().length < 4 || $("#taskDesc").val().length > 100){
		isValid = false;
		taskDescGroup.addClass('error');
		$('<label class="generate-label error" id="taskDescError">รายละเอียดงานต้องมีความยาว 4 ถึง 100 ตัวอักษร</label>').insertAfter('#taskDesc');
	}else{
		taskDescGroup.addClass('success');
	}
	return isValid;
};

viewTask.validateDate = function(){
	var startDate = Globalize.parseDate( $("#startDate").val() +' '+ $("#startTime").val(), "dd/MM/yyyy HH:mm"),
		endDate = Globalize.parseDate( $("#endDate").val() +' '+ $("#endTime").val(), "dd/MM/yyyy HH:mm"),isValid = true;
	$("#dateError").remove();
	if(startDate >= endDate){
		isValid = false;
		$("#startDateGroup").addClass('error');
		$("#endDateGroup").addClass('error');
		$('<label class="generate-label error" id="dateError">วันหมดเขตสอบต้องอยู่หลังวันเริ่มสอบ</label>').insertAfter('#endTimeIcon');
	}else{
		$("#startDateGroup").addClass('success');
		$("#endDateGroup").addClass('success');
	}
	return isValid;
};

viewTask.validateNumOfFile = function(){
	var isValid = true,numOfFileGroup = $("#numOfFileGroup").removeClass('error').removeClass('success');
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

viewTask.validateLimitFileSizeKb = function(){
	var isValid = true,limitFileSizeGroup = $("#limitFileSizeGroup").removeClass('error').removeClass('success');
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

viewTask.validateMaxScore = function(){
	var isValid = true,maxScoreGroup = $("#maxScoreGroup").removeClass('error').removeClass('success');
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

viewTask.initFunction = function(){
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$("#courseId option[value="+application.courseId+"]").attr('selected',true);
		$(this).trigger("liszt:updated");
		$("#courseId_chzn").unblock();
		viewTask.loadSectionBox();
	});
};

viewTask.setSectionData = function(){
	var isFirst=true;
	application.sectionIdStr = '[';
	$.each(application.sectionId,function(index,value){
		isFirst ? isFirst=false : application.sectionIdStr += ',';
		application.sectionIdStr += value.sectionId;
		$("#sectionId option[value="+value.sectionId+"]").attr('selected',true);
	});
	application.sectionIdStr += ']';
};

viewTask.loadSectionBox =function(){
	$("#sectionId_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/sectionComboBox.html',
		type: 'POST',
		data: {
			courseId: $("#courseId").val()
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
			var isFirst = true;
			viewTask.setSectionData();
			$("#sectionId").trigger('liszt:updated');
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
		}
	});
};

$(document).ready(function(){
	$("#courseId").chosen().change(function(){
		viewTask.validateCourseId();
		viewTask.loadSectionBox();
	});
	$("#sectionId").chosen().change(function(){
		viewTask.validateSectionId();
	});
	viewTask.initFunction();
	$("#taskName").keyup(function(){viewTask.validateTaskName();});
	$("#taskDesc").keyup(function(){viewTask.validateTaskDesc();});
	$(".date-check").change(function(){viewTask.validateDate();});
	$("#startDate").datepicker().on('changeDate',function(){viewTask.validateDate();});
	$("#endDate").datepicker().on('changeDate',function(){viewTask.validateDate();});
	$("#startTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'});
	$("#endTime").timepicker({showMeridian:false,showSeconds:false,defaultTime:'value'});
	$("#numOfFile").numeric().keyup(function(){viewTask.validateNumOfFile();});
	$("#limitFileSizeKb").numeric().keyup(function(){viewTask.validateLimitFileSizeKb();});
	$("#maxScore").numeric().keyup(function(){viewTask.validateMaxScore();});
	$("#editTaskConfirmButton").click(function(){
		var startDate = Globalize.parseDate( $("#startDate").val() +' '+ $("#startTime").val(), "dd/MM/yyyy HH:mm"),
		endDate = Globalize.parseDate( $("#endDate").val() +' '+ $("#endTime").val(), "dd/MM/yyyy HH:mm"),
		thisButton = $(this).button('loading');
		$("#mainForm").block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/management/task/view.html',
			type: 'POST',
			data: {
				method: 'editTask',
				taskId: $("#taskId").val(),
				oldSectionStr: application.sectionIdJSON,
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
				$("#editTaskConfirmModal").modal('hide');
				thisButton.button('reset');
				applicationScript.successAlertWithStringHeader("กรุณารอซักครู่ ระบบจะทำการโหลดข้อมูลใหม่","บันทึกสำเร็จ");
				setTimeout('$("#refreshForm").submit()',1000);
			},
			error : function(data){
				thisButton.button('reset');
				$("#editTaskConfirmModal").modal('hide');
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#mainForm").unblock();
			}
		});
	});
	$("#editTaskButton").click(function(){
		if(viewTask.allValidate()){
			if(viewTask.checkDirty()){
				$("#editTaskConfirmModal").modal('show');
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
			}
		}
	});
});