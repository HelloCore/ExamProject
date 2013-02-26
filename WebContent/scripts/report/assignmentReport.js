application.page="report";

assignmentReport = {};
assignmentReport.rows = 20;
assignmentReport.page = 1;
assignmentReport.lastPage = 1;
assignmentReport.orderBy = 'endDate';
assignmentReport.order = 'desc';

assignmentReport.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#endDateHeader").removeClass('sort-asc sort-desc sort-both').addClass('sort-desc current-sort');
	assignmentReport.orderBy = "endDate";
	assignmentReport.order = "desc";
	assignmentReport.getGrid();
};

assignmentReport.getGrid = function(){
	$("#assignmentTable").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/management/task.html',
		type: 'POST',
		data: {
			method: 'getTaskTable'
			,rows: $("#pageSize").val()
			,page: assignmentReport.page
			,orderBy: assignmentReport.orderBy
			,order: assignmentReport.order
			,courseId: $("#courseId").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#assignmentTable tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#assignmentTable tbody");
			applicationScript.calPaging(data,assignmentReport);
			$("#assignmentTable").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
			$("#assignmentTable").unblock();
		}
	});	
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?optionAll=1",function(){
		$(this).select2().change(function(){
			assignmentReport.getGrid();
		});
		assignmentReport.getDefaultGrid();
		applicationScript.setUpGrid(assignmentReport);
	});
	$("#refreshButton").click(function(){
		assignmentReport.getGrid();
	});
});

viewResult = function(taskId){
	$("#taskId").val(taskId);
	$("#viewAssignmentResultForm").submit();
};