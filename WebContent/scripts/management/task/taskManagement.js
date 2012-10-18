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
	$("#taskDivHolder").empty();
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
			var strHtml ;
			for(keyArray in data.records){
				strHtml = '<table class="table table-bordered exam-table pagination-centered">'
								+'<tr>'
									+'<td colspan="2">'+data.records[keyArray].taskName+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td colspan="2"><pre>'+data.records[keyArray].taskDesc+'</pre></td>'
								+'</tr>'
								+'<tr>'
									+'<td>วันเริ่มส่งงาน: '+Globalize.format(new Date(data.records[keyArray].startDate),'dd-MM-yyyy HH:mm')+'</td>'
									+'<td>หมดเขตส่ง: '+Globalize.format(new Date(data.records[keyArray].endDate),'dd-MM-yyyy HH:mm')+'</td>'
								+'</tr>'
								+'<tr>'	
									+'<td>วิชา: '+data.records[keyArray].courseCode+'</td>'
									+'<td>ขนาดไฟล์: '+data.records[keyArray].limitFileSizeKb+'KB</td>'
								+'</tr>'
								+'<tr>'
									+'<td>จำนวนไฟล์: '+data.records[keyArray].numOfFile+' ไฟล์</td>'
									+'<td>คะแนนเต็็ม: '+data.records[keyArray].maxScore+' คะแนน</td>'
								+'</tr>'
								+'<tr>'
									+'<td>สั่งโดย: '+data.records[keyArray].firstName+' '+data.records[keyArray].lastName+'</td>'
									+'<td>สั่งเมื่อ: '+Globalize.format(new Date(data.records[keyArray].createDate),'dd-MM-yyyy HH:mm')+'</td>'
								+'</tr><tr>'
									+'<td colspan="2" style="text-align:center;">'
										+' <button class="btn btn-primary" onClick="evaluateComplete('+data.records[keyArray].taskId+')"><i class="icon-ok icon-white"></i> ตรวจเสร็จแล้ว</button>'
										+' <button class="btn btn-info" onClick="viewTask('+data.records[keyArray].taskId+')"><i class="icon-edit icon-white"></i> ดูรายละเอียด</button>'
										+' <button class="btn btn-danger" onClick="deleteTask('+data.records[keyArray].taskId+')"><i class="icon-trash icon-white"></i> ลบ</button>'
									+'</td>'
								+'</tr>'
							+'</table>';
				$("#taskDivHolder").append(strHtml);
			}
			var startRecord = (((taskManagement.rows)*(taskManagement.page-1))+1);
			applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
			taskManagement.lastPage = data.totalPages;
			
			applicationScript.setPagination(taskManagement.page,taskManagement.lastPage);

			$("#taskDivHolder").unblock();
		},
		error: function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#taskDivHolder").unblock();
		}
	});
};


taskManagement.initFunction = function(){
	$("#courseBox").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#courseBox").unblock();
		taskManagement.getDefaultGrid();
	});
};


changePage = function(page){
	if(taskManagement.page != page){
		taskManagement.page = page;
		taskManagement.getGrid();
	}
};

$(document).ready(function(){
	$("#courseId").chosen();
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
	
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(taskManagement.page > 1){
			taskManagement.page--;
			taskManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(taskManagement.page < taskManagement.lastPage){
			taskManagement.page++;
			taskManagement.getGrid();
		}
	});

	$("#pageSize").change(function(){
		taskManagement.page = 1;
		taskManagement.rows = $(this).val();
		taskManagement.getGrid();
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
				applicationScript.deleteComplete();
				$("#confirmDeleteTaskModal").modal('hide');
				taskManagement.getGrid();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.errorAlertWithStringTH(data.responseText);
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
				applicationScript.saveComplete();
				$("#evaluateCompleteModal").modal('hide');
				taskManagement.getGrid();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.errorAlertWithStringTH(data.responseText);
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