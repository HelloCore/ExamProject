application.page="task";

taskManagement = {};
taskManagement.rows = 5;
taskManagement.page = 1;
taskManagement.lastPage = 1;
taskManagement.taskId = null;


taskManagement.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#taskName").val('');
	taskManagement.getGrid();
};

taskManagement.getGrid = function(){
	$("#taskDivHolder").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/task.html',
		type: 'POST',
		data: {
			method: 'getTaskTable'
			,rows: $("#pageSize").val()
			,page: taskManagement.page
			,courseId: $("#courseId").val()
			,taskName: $("#taskName").val()
			,isComplete: 1
		},
		dataType: 'json',
		success: function(data,status){
			$("#taskDivHolder table").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#taskDivHolder");
			applicationScript.calPaging(data,taskManagement);
			$("#taskDivHolder").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
			$("#taskDivHolder").unblock();
		}
	});
};


taskManagement.initFunction = function(){
	$("#courseBox").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2();
		$("#courseBox").unblock();
		taskManagement.getDefaultGrid();
		applicationScript.setUpGrid(taskManagement);
		
	});
};



$(document).ready(function(){
	$("#courseId").select2();
	taskManagement.initFunction();

	$("#refreshButton").click(function(){
		taskManagement.getDefaultGrid();
	});

	$("#searchButton").click(function(){
		taskManagement.page =1;
		taskManagement.getGrid();
		$('.search-inactive').addClass('search-active').removeClass('search-inactive');
		if($("#taskName").val().length==0){
			$('.search-active').removeClass('search-active').addClass('search-inactive');
		}
	});
	

	var $window = $(window);
	$("#leftBar").affix({
		offset: {
			top: function () { return $window.width() <= 980 ? 207 : 167; }
		}
	});
	$("#confirmDeleteTaskButton").click(function(){
		var thisButton = $(this).button('loading');
		$.ajax({
			url: application.contextPath + '/management/task.html',
			type: 'POST',
			data: {
				method: 'deleteTask',
				taskId: taskManagement.taskId
			},
			success: function(){
				thisButton.button('reset');
				applicationScript.deleteCompleteTH();
				$("#confirmDeleteTaskModal").modal('hide');
				taskManagement.getGrid();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.resolveError(data.responseText);
				$("#confirmDeleteTaskModal").modal('hide');
			}
		});
	});
	$("#evaluateCompleteButton").click(function(){
		var thisButton = $(this).button('loading');
		$.ajax({
			url: application.contextPath + '/management/task.html',
			type: 'POST',
			data: {
				method: 'evaluateComplete',
				taskId: taskManagement.taskId
			},
			success: function(){
				thisButton.button('reset');
				applicationScript.saveCompleteTH();
				$("#evaluateCompleteModal").modal('hide');
				taskManagement.getGrid();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.resolveError(data.responseText);
				$("#evaluateCompleteModal").modal('hide');
			}
		});
		
	});
});

deleteTask = function(taskId){
	taskManagement.taskId = taskId;
	$("#confirmDeleteTaskModal").modal('show');
};

viewTask = function(taskId){
	$("#viewTaskId").val(taskId);
	$("#viewTaskForm").submit();
};
evaluateComplete = function(taskId){
	taskManagement.taskId = taskId;
	$("#evaluateCompleteModal").modal('show');
};