application.page='genericManagement';
registerHistory = {};
registerHistory.rows = 20;
registerHistory.page = 1;
registerHistory.lastPage = 1;
registerHistory.sectionId = '';
registerHistory.sectionName = '';
registerHistory.sectionYear = '';
registerHistory.sectionSemester = '';
registerHistory.courseCode = '';
registerHistory.orderBy = '';
registerHistory.order = 'asc';


registerHistory.getGrid = function(){
	$("#registerHistoryTable").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/register/history.html',
		type: 'POST',
		data: {
			method: 'getHistoryTable'
			,rows: registerHistory.rows
			,page: registerHistory.page
			,orderBy: registerHistory.orderBy
			,order: registerHistory.order
			,courseId: $("#courseId").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#registerHistoryTable tbody").empty();
			var strHtml,labelAccept= '<span class="label label-success"><i class="icon-ok icon-white"></i> Accept</span>'
				,labelReject = '<span class="label label-important"><i class="icon-ban-circle icon-white"></i> Reject</span>';
		for(keyArray in data.records){
				strHtml = '<tr>'+
							'<td>'+Globalize.format( new Date(data.records[keyArray].requestDate),"dd-MM-yyyy HH:mm:ss")+'</td>'+
							'<td>'+Globalize.format( new Date(data.records[keyArray].processDate),"dd-MM-yyyy HH:mm:ss")+'</td>'+
							'<td>'+data.records[keyArray].studentId+'</td>'+
							'<td>'+data.records[keyArray].firstName+' '+data.records[keyArray].lastName+'</td>'+
							'<td>'+data.records[keyArray].courseCode+'</td>'+
							'<td>'+data.records[keyArray].sectionName+' เทอม '+data.records[keyArray].sectionSemester+'/'+data.records[keyArray].sectionYear+'</td>'+
							'<td>';
				if(data.records[keyArray].status == 1){
					strHtml+=labelAccept;
				}else if(data.records[keyArray].status == 2){
					strHtml+= labelReject;
				}
				strHtml += '</td>'+
							'<td>'+data.records[keyArray].verifyBy+'</td>';
				$("#registerHistoryTable tbody").append(strHtml);
			}
			var startRecord = (((registerHistory.rows)*(registerHistory.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords);
			}
			registerHistory.lastPage = data.totalPages;
			applicationScript.setPagination(registerHistory.page,registerHistory.lastPage);
			$("#registerHistoryTable").unblock();
		},
		statusCode: {
			401: function(){
				alert("Session Timeout");
				window.location = application.contextPath+'/main/login.html?target=/management/register/hostory.html';
			}
		}
	});
};

registerHistory.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	registerHistory.orderBy = "";
	registerHistory.order = "asc";
	registerHistory.getGrid();
};

changePage = function(page){
	if(registerHistory.page != page){
		registerHistory.page = page;
		registerHistory.getGrid();
	}
};

registerHistory.initCourseComboBox = function(callback){
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?",function(){
		$(this).trigger("liszt:updated");
		$("#courseId_chzn").unblock();
		if(typeof(callback)=='function'){
			callback();
		}
	});
};

$(document).ready(function(){
	$("#courseId").chosen();
	registerHistory.initCourseComboBox(function(){
		registerHistory.getDefaultGrid();
		$("#prevPageButton").click(function(e){
			e.preventDefault();
			if(registerHistory.page > 1){
				registerHistory.page--;
				registerHistory.getGrid();
			}
		});
		$("#nextPageButton").click(function(e){
			e.preventDefault();
			if(registerHistory.page < registerHistory.lastPage){
				registerHistory.page++;
				registerHistory.getGrid();
			}
		});
	});
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(registerHistory.orderBy == myId){
			if(registerHistory.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				registerHistory.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				registerHistory.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			registerHistory.orderBy = myId;
			registerHistory.order = "asc";
		}
		registerHistory.getGrid();
	});
});