
application.page="report";

customReport = {};
customReport.getSectionComboBox = function(){
	$("#sectionId_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/sectionComboBox.html',
		type: 'POST',
		data: {
			courseId: $("#courseId").val(),
			dontCheckStatus: 1
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
			customReport.loadData();
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
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
			$("#examChoice").empty();
			
			var strHtml,startDateStr,endDateStr;
			if(data.length ==0){
				$("#examChoice").append('<div class="alert alert-warning" style="width:300px;"><strong>คำเตือน</strong>'
									+'ไม่พบข้อมูลการสอบ'
								+'</div>');
			}
			for(keyArray in data){
				if(data[keyArray].startDate == null){
					startDateStr = "ไม่กำหนด";
				}else{
					startDateStr = Globalize.format(new Date(data[keyArray].startDate),'dd-MM-yyyy HH:mm');
				}
				if(data[keyArray].endDate == null){
					endDateStr = "ไม่กำหนด";
				}else{
					endDateStr = Globalize.format(new Date(data[keyArray].endDate),'dd-MM-yyyy HH:mm');
				}
				strHtml = '<li>'
								+'<label for="exam'+data[keyArray].examId+'" style="display:inline-block;">'
									+'<input type="checkbox" id="exam'+data[keyArray].examId+'" name="examId[]" value="'+data[keyArray].examId+'" title="'+data[keyArray].examHeader+'" maxScore="'+data[keyArray].maxScore+'">&nbsp;&nbsp;'
									+data[keyArray].examHeader
									+' เริ่มสอบ '+startDateStr
									+' หมดเขตสอบ '+endDateStr
									+' คะแนนเต็ม '+data[keyArray].maxScore+'คะแนน'
								+' </label>'
							+'</li>';
				$("#examChoice").append(strHtml);
			}
			counter++;
			if(counter==2){
				$("#choiceBody").unblock();
			}
			$(".exam-score").tooltip();
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
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
			$("#assignmentChoice").empty();
			if(data.length ==0){
				$("#assignmentChoice").append('<div class="alert alert-warning" style="width:300px;"><strong>คำเตือน</strong>'
									+'ไม่พบข้อมูล Assignment'
								+'</div>');
			}
			var strHtml,statusStr;
			for(keyArray in data){
				
				if(data[keyArray].isEvaluateComplete){
					statusStr = '<span class="label label-success"><i class="icon-ok icon-white"></i> ตรวจเสร็จแล้ว</span>';
				}else{
					statusStr = '<span class="label label-warning"><i class="icon-pencil icon-white"></i> ยังตรวจไม่เสร็จ</span>';
				}
				
				strHtml = '<li>'
								+'<label for="assignment'+data[keyArray].assignmentTaskId+'">'
									+'<input maxScore="'+data[keyArray].maxScore+'" title="'+data[keyArray].assignmentTaskName+'" type="checkbox" id="assignment'+data[keyArray].assignmentTaskId+'" name="assignmentId[]" value="'+data[keyArray].assignmentTaskId+'">&nbsp;&nbsp;'
									+data[keyArray].assignmentTaskName
									+' เริ่มส่ง '+Globalize.format(new Date(data[keyArray].startDate),'dd-MM-yyyy HH:mm')
									+' หมดเขตส่ง '+Globalize.format(new Date(data[keyArray].endDate),'dd-MM-yyyy HH:mm')
									+' สถานะ '+statusStr
									+' คะแนนเต็ม '+data[keyArray].maxScore
									+' คะแนน'
								+'</label>'
						+'</li>';
				$("#assignmentChoice").append(strHtml);
			}
			
			counter++;
			if(counter==2){
				$("#choiceBody").unblock();
			}
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
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
	$("#courseId,#sectionId").chosen();
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#courseId_chzn").unblock();
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