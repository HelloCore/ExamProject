application.page="task";

backFunction = function(){
	$("#sendTaskForm").submit();
};

$(document).ready(function(){
	$("#score").numeric({ negative: false });
	$("#evaluateButton").click(function(){
		if($("#score").val() > application.maxScore){
			applicationScript.errorAlertWithStringTH('ไม่สามารถให้คะแนนมากกว่าคะแนนเต็มได้');
		}else{
			$("#evaluateTaskConfirmModal").modal('show');
		}
	});
	$("#evaluateTaskConfirmButton").click(function(){
		$("#evaluateForm").submit();
	});
});