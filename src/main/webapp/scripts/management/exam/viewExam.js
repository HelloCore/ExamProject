application.page='examManagement';
viewExam = {};

$(document).ready(function(){

	if(application.exam.startDate){
		$("#startDate").val(Globalize.format(new Date(application.exam.startDate),'dd-MM-yyyy HH:mm'));
	}else{
		$("#startDate").val("ไม่กำหนด");
	}
	if(application.exam.endDate == ''){
		$("#endDate").val(Globalize.format(new Date(application.exam.endDate),'dd-MM-yyyy HH:mm'));
	}else{
		$("#endDate").val("ไม่กำหนด");
	}
});