application.page="report";

examReportDetail = {};
examReportDetail.rows = 20;
examReportDetail.page = 1;
examReportDetail.lastPage = 1;
examReportDetail.orderBy = 'studentId';
examReportDetail.order = 'asc';

examReportDetail.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#studentIdHeader").removeClass('sort-asc sort-desc sort-both').addClass('current-sort sort-desc');
	examReportDetail.orderBy = "studentId";
	examReportDetail.order = "asc";
	examReportDetail.getGrid();
};

examReportDetail.getGrid = function(){
	$("#examReportDetailTable").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/report/examDetail.html',
		type: 'POST',
		data: {
			method: 'getExamDetailTable'
			,rows: $("#pageSize").val()
			,page: examReportDetail.page
			,examId: application.examId
			,order :examReportDetail.order
			,orderBy : examReportDetail.orderBy
		},
		dataType: 'json',
		success: function(data,status){

			$("#examReportDetailTable tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#examReportDetailTable tbody");
			applicationScript.calPaging(data,examReportDetail);
			$("#examReportDetailTable").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
			$("#examReportDetailTable").unblock();
		}
	});	
};

$(document).ready(function(){
	examReportDetail.getDefaultGrid();
	applicationScript.setUpGrid(examReportDetail);
	$("#refreshButton").click(function(){
		examReportDetail.getGrid();
	});
});

viewExamReport = function(examResultId){
	$("#examResultId").val(examResultId);
	$("#viewExamReportForm").submit();
};