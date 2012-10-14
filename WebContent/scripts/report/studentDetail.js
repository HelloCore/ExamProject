application.page="report";

viewExamReport = function(examResultId){
	$("#examResultId").val(examResultId);
	$("#viewExamReportForm").submit();
};