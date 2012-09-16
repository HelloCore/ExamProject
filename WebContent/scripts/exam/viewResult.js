application.page='exam';
$(document).ready(function(){	
	$("#examUsedTime").text(applicationScript.secondsToTime(application.examUsedTime));
});
