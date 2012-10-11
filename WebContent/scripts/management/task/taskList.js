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
			var strHtml;
			$("#taskListTable tbody").empty();
			for(keyArray in data){
				strHtml = '<tr>'
							+'<td>'+data[keyArray][1]+'</td>'
							+'<td>'+data[keyArray][2]+'</td>'
							+'<td>'+Globalize.format(new Date(data[keyArray][3]),'dd-MM-yyyy HH:mm')+'</td>'
							+'<td>'+data[keyArray][4]+'</td>'
							+'<td>'+data[keyArray][5]+'</td>'
							+'<td><button class="btn btn-info" onClick="evaluateTask('+data[keyArray][0]+')"><i class="icon-edit icon-white"></i> ตรวจการบ้าน</button></td>'
						+'</tr>';
				$("#taskListTable tbody").append(strHtml);
			}
			$("#taskListTable").unblock();
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#taskListTable").unblock();
		}
	});
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).chosen().change(function(){
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
