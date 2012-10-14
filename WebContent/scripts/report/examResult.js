application.page='report';
$(document).ready(function(){	
	$("#examUsedTime").text(applicationScript.secondsToTime(application.examUsedTime));
});
