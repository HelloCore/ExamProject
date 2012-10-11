application.page="task";

evaluateWork = function(workId){
	$("#evaluateWorkId").val(workId);
	$("#evaluateForm").submit();
};

reEvaluateWork = function(workId){
	$("#evaluateWorkId").val(workId);
	$("#evaluateForm").submit();
};