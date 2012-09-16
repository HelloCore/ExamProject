application.page='examManagement';
examManagement = {};
examManagement.rows = 5;
examManagement.page = 1;
examManagement.lastPage = 1;
examManagement.examId = null;

examManagement.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#examHeader").val('');
	examManagement.getGrid();
};
changePage = function(page){
	if(examManagement.page != page){
		examManagement.page = page;
		examManagement.getGrid();
	}
};

examManagement.initFunction = function(){
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?optionAll=true",function(){
		$(this).trigger("liszt:updated");
		examManagement.getDefaultGrid();
		$("#courseId_chzn").unblock();
	});
};

examManagement.getGrid = function(){
	$("#examDivHolder").empty();
	$("#examDivHolder").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/management/exam.html',
		type: 'POST',
		data: {
			method: 'getExamTable'
			,rows: $("#pageSize").val()
			,page: examManagement.page
			,examHeader : $("#examHeader").val()
			,courseId: $("#courseId").val()
		},
		dataType: 'json',
		success: function(data,status){
			var strHtml,startDateStr,endDateStr;
			for(keyArray in data.records){
				if(data.records[keyArray].startDate == null){
					startDateStr = "ไม่กำหนด";
				}else{
					startDateStr = Globalize.format(new Date(data.records[keyArray].startDate),'dd-MM-yyyy HH:mm');
				}
				if(data.records[keyArray].endDate == null){
					endDateStr = "ไม่กำหนด";
				}else{
					endDateStr = Globalize.format(new Date(data.records[keyArray].endDate),'dd-MM-yyyy HH:mm');
				}
				
				strHtml = '<table class="table table-bordered exam-table pagination-centered">'
								+'<tr>'
									+'<td colspan="3">หัวข้อ : '+data.records[keyArray].examHeader+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td class="sub-column1">วิชา : '+data.records[keyArray].courseCode+'</td>'
									+'<td class="sub-column2">วันเริ่มสอบ : '+startDateStr+'</td>'
									+'<td class="sub-column3">'
										+'<button class="btn btn-info" onClick="viewExam('+data.records[keyArray].examId+')"><i class="icon-edit icon-white"></i> View</button> '
									+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td class="sub-column1">จำนวนคำถาม : '+data.records[keyArray].minQuestion+' ถึง '+data.records[keyArray].maxQuestion+' ข้อ</td>'
									+'<td class="sub-column2">วันหมดเขตสอบ : '+endDateStr+'</td>'
									+'<td class="sub-column3">'
										+'<button class="btn btn-danger" onClick="deleteExam('+data.records[keyArray].examId+')"><i class="icon-trash icon-white"></i> Delete</button>'
									+'</td>'
								+'</tr>'
							+'</table>';
				$("#examDivHolder").append(strHtml);
			}
			var startRecord = (((examManagement.rows)*(examManagement.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}			
			examManagement.lastPage = data.totalPages;
			applicationScript.setPagination(examManagement.page,examManagement.lastPage);
			$("#examDivHolder").unblock();
		},
		statusCode: {
			401: function(){
				alert("Session Timeout");
				window.location = application.contextPath+'/main/login.html?target=/management/exam.html';
				$("#examDivHolder").unblock();
			}
		}
	});	
};


$(document).ready(function(){
	$("#courseId").chosen();
	examManagement.initFunction();
	$("#refreshButton").click(function(){
		examManagement.getDefaultGrid();
	});
	$("#addButton").click(function(){
		window.location = application.contextPath+'/management/exam/add.html';
	});

	$("#searchButton").click(function(){
		examManagement.page =1;
		examManagement.getGrid();
		$('.search-inactive').addClass('search-active').removeClass('search-inactive');
		if($("#examHeader").val().length==0){
			$('.search-active').removeClass('search-active').addClass('search-inactive');
		}
	});

	$("#pageSize").change(function(){
		examManagement.rows = $(this).val();
		examManagement.getGrid();
	});
	

	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(examManagement.page > 1){
			examManagement.page--;
			examManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(examManagement.page < examManagement.lastPage){
			examManagement.page++;
			examManagement.getGrid();
		}
	});

	$("#deleteButton").click(function(){
		var thisButton = $(this);
		thisButton.button('loading');
		$.ajax({
			url: application.contextPath + '/management/exam.html',
			type: 'POST',
			data: {
				method: 'deleteExam',
				examId: examManagement.examId
			},
			success: function(){
				thisButton.button('reset');
				applicationScript.deleteComplete();
				$("#confirmDelete").modal('hide');
				examManagement.getGrid();
			},
			error: function(){
				thisButton.button('reset');
				applicationScript.errorAlert();
				$("#confirmDelete").modal('hide');
			}
		});
	});
	
});


deleteExam = function(examId){
	examManagement.examId = examId;
	$("#confirmDelete").modal('show');
};

viewExam = function(examId){
	$("#examId").val(examId);
	$("#viewExamForm").submit();
};