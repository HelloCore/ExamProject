
application.page="report";

customReport = {};
customReport.getSectionComboBox = function(){
	$.ajax({
		url: application.contextPath + '/management/sectionComboBox.html',
		type: 'POST',
		data: {
			group: 1,
			courseId: $("#courseId").val(),
			dontCheckStatus: 1
		},
		dataType: 'json',
		beforeSend: function(){
			$("#s2id_sectionId").block(application.blockOption);
		},
		success: function(data,status){
			$("#sectionId optgroup").remove();
			$("#sectionTemplate").tmpl(data).appendTo("#sectionId");
			$("#sectionId").select2();
			$("#s2id_sectionId").unblock();
			if(data.length > 0){
				$("#choiceBody").show();
				$("#errorSection").hide();
				customReport.loadData();
			}else{
				$("#s2id_sectionId").block(application.blockOption);
				$("#errorSection").show();
				$("#choiceBody").hide();
			}
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
};

customReport.loadData = function(){
	$("#choiceBody").block(application.blockOption);
	var counter = 0;
	$.ajax({
		url : application.contextPath +'/report/customReport.html',
		data : {
			method: 'getExamList',
			sectionId: $("#sectionId").val()
		},
		type :'POST',
		dataType: 'json',
		success: function(data){
			
//			var strHtml,startDateStr,endDateStr;
//			for(keyArray in data){
//				if(data[keyArray].startDate == null){
//					startDateStr = "ไม่กำหนด";
//				}else{
//					startDateStr = Globalize.format(new Date(data[keyArray].startDate),'dd-MM-yyyy HH:mm');
//				}
//				if(data[keyArray].endDate == null){
//					endDateStr = "ไม่กำหนด";
//				}else{
//					endDateStr = Globalize.format(new Date(data[keyArray].endDate),'dd-MM-yyyy HH:mm');
//				}
//				strHtml = '<li>'
//								+'<label for="exam'+data[keyArray].examId+'" style="display:inline-block;">'
//									+'<input type="checkbox" id="exam'+data[keyArray].examId+'" name="examId[]" value="'+data[keyArray].examId+'" title="'+data[keyArray].examHeader+'" maxScore="'+data[keyArray].maxScore+'">&nbsp;&nbsp;'
//									+data[keyArray].examHeader
//									+' เริ่มสอบ '+startDateStr
//									+' หมดเขตสอบ '+endDateStr
//									+' คะแนนเต็ม '+data[keyArray].maxScore+'คะแนน'
//								+' </label>'
//							+'</li>';
//				$("#examChoice").append(strHtml);
//			}
			$("#examChoice li").remove();
			if(data.length ==0){
				$("#examChoice").append('<div class="alert alert-warning" style="width:300px;"><strong><i class="fam-error"></i> ขออภัย</strong> ไม่พบข้อมูลการสอบ</div>');
			}else{
				$("#examChoiceTemplate").tmpl(data).appendTo("#examChoice");
			}
			counter++;
			if(counter==2){
				$("#choiceBody").unblock();
			}
			$(".exam-score").tooltip();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
	$.ajax({
		url : application.contextPath +'/report/customReport.html',
		data : {
			method: 'getAssignmentList',
			sectionId: $("#sectionId").val()
		},
		type :'POST',
		dataType: 'json',
		success: function(data){
			$("#assignmentChoice li").remove();
			if(data.length ==0){
				$("#assignmentChoice").append('<div class="alert alert-warning" style="width:300px;"><strong><i class="fam-error"></i> ขออภัย</strong> ไม่พบข้อมูล Assignment</div>');
			}else{
				$("#assignmentChoiceTemplate").tmpl(data).appendTo("#assignmentChoice");
			}
			
			counter++;
			if(counter==2){
				$("#choiceBody").unblock();
			}
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
};

customReport.validateData = function(){
	var isValid = true,
		examIdLength = $('input[name^=examId]:checked').length,
		assignmentIdLength = $('input[name^=assignmentId]:checked').length;
	if( examIdLength+assignmentIdLength  ==0){
		applicationScript.errorAlertWithStringTH("กรุณาเลือกการสอบ หรือ assignment อย่างน้อย 1 อย่าง");
		isValid = false;
	}
	return isValid;
};

customReport.calDataAndSubmit = function(){
	var examData,assignmentData,
		examIdLength = $('input[name^=examId]:checked').length,
		assignmentIdLength = $('input[name^=assignmentId]:checked').length;
		if(examIdLength > 0){
			examData = [];
			$('input[name^=examId]:checked').each(function(){
				examData[examData.length] = {
					examId : parseInt($(this).val(),10),
					maxScore : $(this).attr('maxScore'),
					examHeader : $(this).attr('title')
				};
			});
		}
		if(assignmentIdLength > 0){
			assignmentData = [];
			$('input[name^=assignmentId]:checked').each(function(){
				assignmentData[assignmentData.length] = {
					assignmentTaskId: $(this).val(),
					assignmentTaskName: $(this).attr('title'),
					maxScore: $(this).attr('maxScore')
				};
			});
		}
		$("#reportCourseId").val($("#courseId").val());
		$("#reportSectionId").val($("#sectionId").val());
		$("#examData").val(JSON.stringify(examData));
		$("#assignmentData").val(JSON.stringify(assignmentData));
		$("#scoreChoice").val($("input[name=scoreType]:checked").val());
		$("#viewCustomReportForm").submit();
};

$(document).ready(function(){
	$("#courseId,#sectionId").select2();
	$("#s2id_courseId").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2();
		$("#s2id_courseId").unblock();
		customReport.getSectionComboBox();
	}).change(function(){
		customReport.getSectionComboBox();
	});
	$("#sectionId").change(function(){
		customReport.loadData();
	});
	$("#viewReportButton").click(function(){
		if(customReport.validateData()){
			$("#viewReportConfirmModal").modal('show');
		}
	});
	$("#viewReportConfirmButton").click(function(){
		customReport.calDataAndSubmit();
	});
});