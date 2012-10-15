application.page="report";

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){$(this).chosen();});
});