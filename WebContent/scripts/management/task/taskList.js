application.page="task";

taskList = {};
taskList.getGrid = function(){
	$("#taskListTable").block(application.blockOption);
	$.ajax({
		url:application.contextPath +'/management/task/taskList.html',
		type:'POST',
		data: {
			method: 'getTaskList',
			courseId: $("#courseId").val()
		},
		success: function(data){
			$("#taskListTable tbody tr").remove();
			$("#recordTemplate").tmpl(data).appendTo("#taskListTable tbody");
			$("#taskListTable").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
			$("#taskListTable").unblock();
		}
	});
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2().change(function(){
			taskList.getGrid();
		});
		taskList.getGrid();
	});
	$("#refeshButton").click(function(){
		taskList.getGrid();
	});
});

evaluateTask = function(taskId){
	$("#taskId").val(taskId);
	$("#sendTaskForm").submit();
};
