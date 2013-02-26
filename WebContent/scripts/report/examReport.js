application.page="report";

examReport = {};
examReport.rows = 20;
examReport.page = 1;
examReport.lastPage = 1;
examReport.orderBy = 'endDate';
examReport.order = 'desc';

examReport.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#endDateHeader").removeClass('sort-asc sort-desc sort-both').addClass('sort-desc current-sort');
	examReport.orderBy = "endDate";
	examReport.order = "desc";
	examReport.getGrid();
};
examReport.getGrid = function(){
	$("#examTable").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/management/exam.html',
		type: 'POST',
		data: {
			method: 'getExamTable'
			,rows: $("#pageSize").val()
			,page: examReport.page
			,courseId: $("#courseId").val()
			,order :examReport.order
			,orderBy : examReport.orderBy
		},
		dataType: 'json',
		success: function(data,status){
			$("#examTable tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#examTable tbody");
			applicationScript.calPaging(data,examReport);
			$("#examTable").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
			$("#examTable").unblock();
		}
	});	
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?optionAll=1",function(){
		$(this).select2();
		examReport.getDefaultGrid();
		applicationScript.setUpGrid(examReport,function(myId){
			if(myId == 'exam'){
				myId = 'examHeader';
			}
			return myId;
		});
	});
	
	$("#refreshButton").click(function(){
		examReport.getGrid();
	});
	
});

viewExamReport = function(examId){
	$("#examId").val(examId);
	$("#viewExamReportForm").submit();
};
viewExamGraph = function(examId){
	$("#examGraphId").val(examId);
	$("#viewExamGraphForm").submit();
};