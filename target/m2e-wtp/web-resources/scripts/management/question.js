application.page='management';
questionManagement = {};
questionManagement.rows = 5;
questionManagement.page = 1;
questionManagement.lastPage = 1;
questionManagement.questionId = null;

questionManagement.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#questionText").val('');
	questionManagement.getGrid();
};

questionManagement.getGrid = function(){
	$("#questionDivHolder").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/question.html',
		type: 'POST',
		data: {
			method: 'getQuestionTable'
			,rows: $("#pageSize").val()
			,page: questionManagement.page
			,courseId: $("#courseId").val()
			,questionGroupId: $("#questionGroupId").val()
			,questionText: $("#questionText").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#questionDivHolder").empty();
			var strHtml ;
			for(keyArray in data.records){
				strHtml = '<table class="table table-bordered question-table">'
								+'<tr>'
									+'<td colspan="2">'
										+data.records[keyArray].questionText
									+'</td>'
								+'</tr><tr>'
									+'<td class="sub-column">วิชา : '+data.records[keyArray].courseCode+'</td>'
									+'<td class="sub-column">มีจำนวนคำตอบที่ถูกต้อง '+data.records[keyArray].numOfAnswer+' คำตอบ</td>'
								+'</tr><tr>'
									+'<td class="sub-column">กลุ่มคำถาม : '+data.records[keyArray].questionGroupName+'</td>'
									+'<td class="sub-column">'
										+'<button class="btn btn-info" onclick="viewQuestion('+data.records[keyArray].questionId+')"><i class="icon-edit icon-white"></i> View </button> '
										+'<button class="btn btn-danger" onclick="deleteQuestion('+data.records[keyArray].questionId+')"><i class="icon-trash icon-white"></i> Delete</button>'
									+'</td>'
								+'</tr>'
							+'</table>';
				$("#questionDivHolder").append(strHtml);
			}
			var startRecord = (((questionManagement.rows)*(questionManagement.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}			
			questionManagement.lastPage = data.totalPages;
			questionManagement.setPagination();
			$("#questionDivHolder").unblock();
		},
		statusCode: {
			401: function(){
				alert("Session Timeout");
				window.location = application.contextPath+'/main/login.html?target=/management/question.html';
			}
		}
	});
};

questionManagement.initFunction = function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#questionGroupId").load(application.contextPath+"/management/questionGroupComboBox.html",{courseId:$("#courseId").val()},function(){
			$(this).trigger("liszt:updated");
			questionManagement.getDefaultGrid();
		});
	});
};
questionManagement.setPagination = function(){
	var sClass;
	var first = (questionManagement.page-2);
	if( first < 1){ first = 1;} 
	var last = (questionManagement.page+2);
	if( last > questionManagement.lastPage){ last = questionManagement.lastPage;} 
	
	$('.grid-pagination li:gt(0)').filter(':not(:last)').remove();

	for(var i=first;i<=last;i++){
		sClass = (i==questionManagement.page) ? 'class="active"' :'';
		$('<li '+sClass+'><a href="#">'+i+'</a></li>')
			.insertBefore( $('.grid-pagination li:last')[0] )
			.bind('click',function(e){
				e.preventDefault();
				changePage($(this).text());
			});
	}
	
	if ( questionManagement.page == 1 ) {
		$('.grid-pagination li:first').addClass('disabled');
	} else {
		$('.grid-pagination li:first').removeClass('disabled');
	}

	if ( questionManagement.page == questionManagement.lastPage ) {
		$('.grid-pagination li:last').addClass('disabled');
	} else {
		$('.grid-pagination li:last').removeClass('disabled');
	}
};

changePage = function(page){
	if(questionManagement.page != page){
		questionManagement.page = page;
		questionManagement.getGrid();
	}
};

$(document).ready(function(){
	$("#courseId").chosen();
	$("#questionGroupId").chosen();
	questionManagement.initFunction();
	$("#refreshButton").click(function(){
		questionManagement.getDefaultGrid();
	});
	$("#addButton").click(function(){
		window.location = application.contextPath+'/management/question/add.html';
	});
	$("#searchButton").click(function(){
		questionManagement.page =1;
		questionManagement.getGrid();
		$('.search-inactive').addClass('search-active').removeClass('search-inactive');
		if($("#questionText").val().length==0){
			$('.search-active').removeClass('search-active').addClass('search-inactive');
		}
	});
	$("#pageSize").change(function(){
		questionManagement.rows = $(this).val();
		questionManagement.getGrid();
	});
	$("#deleteButton").click(function(){
		var thisButton = $(this);
		thisButton.button('loading');
		$.ajax({
			url: application.contextPath + '/management/question.html',
			type: 'POST',
			data: {
				method: 'deleteQuestion',
				questionId: questionManagement.questionId
			},
			success: function(){
				thisButton.button('reset');
				applicationScript.deleteComplete();
				$("#confirmDelete").modal('hide');
				questionManagement.getGrid();
			},
			error: function(){
				thisButton.button('reset');
				applicationScript.errorAlert();
				$("#confirmDelete").modal('hide');
			}
		});
	});
});

viewQuestion = function(questionId){
	
};

deleteQuestion = function(questionId){
	questionManagement.questionId = questionId;
	$("#confirmDelete").modal('show');
};