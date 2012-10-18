application.page="report";

assignmentReport = {};
assignmentReport.rows = 20;
assignmentReport.page = 1;
assignmentReport.lastPage = 1;
assignmentReport.orderBy = 'endDate';
assignmentReport.order = 'desc';

assignmentReport.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#endDateHeader").addClass('current-sort')
	.removeClass('sort-asc')
	.removeClass('sort-desc')
	.removeClass('sort-both')
	.addClass('sort-desc');
	assignmentReport.orderBy = "endDate";
	assignmentReport.order = "desc";
	assignmentReport.getGrid();
};
changePage = function(page){
	if(assignmentReport.page != page){
		assignmentReport.page = page;
		assignmentReport.getGrid();
	}
};

assignmentReport.getGrid = function(){
	$("#assignmentTable").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/management/task.html',
		type: 'POST',
		data: {
			method: 'getTaskTable'
			,rows: $("#pageSize").val()
			,page: assignmentReport.page
			,orderBy: assignmentReport.orderBy
			,order: assignmentReport.order
			,courseId: $("#courseId").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#assignmentTable tbody").empty();
			var strHtml,statusStr;
			for(keyArray in data.records){
				if(data.records[keyArray].isEvaluateComplete){
					statusStr = '<span class="label label-success"><i class="icon-ok icon-white"></i> ตรวจเสร็จแล้ว</span>';
				}else{
					statusStr = '<span class="label label-warning"><i class="icon-pencil icon-white"></i> ยังตรวจไม่เสร็จ</span>';
				}

				strHtml = '<tr>'
							+'<td>'+data.records[keyArray].courseCode+'</td>'
							+'<td>'+data.records[keyArray].taskName+'</td>'
							+'<td>'+Globalize.format(new Date(data.records[keyArray].startDate),'dd-MM-yyyy HH:mm')+'</td>'
							+'<td>'+Globalize.format(new Date(data.records[keyArray].endDate),'dd-MM-yyyy HH:mm')+'</td>'
							+'<td style="text-align:right;">'+data.records[keyArray].maxScore+'</td>'
							+'<td>'+statusStr+'</td>'
							+'<td><button class="btn btn-info" onclick="viewResult('+data.records[keyArray].taskId+')"><i class="icon-zoom-in icon-white"></i> ดูรายละเอียด</button></td>'
						+'</tr>';
							
				$("#assignmentTable tbody").append(strHtml);
			}
			var startRecord = (((assignmentReport.rows)*(assignmentReport.page-1))+1);
			applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
			assignmentReport.lastPage = data.totalPages;
			applicationScript.setPagination(assignmentReport.page,assignmentReport.lastPage);
			$("#assignmentTable").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#assignmentTable").unblock();
		}
	});	
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?optionAll=1",function(){
		$(this).chosen();
		assignmentReport.getDefaultGrid();
	});
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(assignmentReport.page > 1){
			assignmentReport.page--;
			assignmentReport.getGrid();
		}
	});
	$("#refreshButton").click(function(){
		assignmentReport.getGrid();
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(assignmentReport.page < assignmentReport.lastPage){
			assignmentReport.page++;
			assignmentReport.getGrid();
		}
	});
	$("#pageSize").change(function(){
		assignmentReport.page = 1;
		assignmentReport.rows = $(this).val();
		assignmentReport.getGrid();
	});
	
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(assignmentReport.orderBy == myId){
			if(assignmentReport.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				assignmentReport.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				assignmentReport.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			assignmentReport.orderBy = myId;
			assignmentReport.order = "asc";
		}
		assignmentReport.getGrid();
	});
});

viewResult = function(taskId){
	$("#taskId").val(taskId);
	$("#viewAssignmentResultForm").submit();
};