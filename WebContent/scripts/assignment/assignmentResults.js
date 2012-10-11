application.page = "assignment";

assignmentResult = {};
assignmentResult.rows = 10;
assignmentResult.page = 1;
assignmentResult.lastPage = 1;
assignmentResult.orderBy = '';
assignmentResult.order = 'asc';

assignmentResult.getGrid = function(){
	$("#assignmentResultGrid").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/assignment/result.html',
		type: 'POST',
		data: {
			method: 'getAssignmentResultTable'
			,rows: assignmentResult.rows
			,page: assignmentResult.page
			,orderBy: assignmentResult.orderBy
			,order: assignmentResult.order
		},
		dataType: 'json',
		success: function(data,status){
			$("#assignmentResultGrid tbody").empty();
			var strHtml,sendDate,evaluateDate;
			for(keyArray in data.records){
				sendDate = Globalize.format(new Date(data.records[keyArray].sendDate),'dd-MM-yyyy HH:mm');
				
				usedTimeStr = applicationScript.secondsToTime(data.records[keyArray].examUsedTime);
				strHtml = '<tr>'
								+ '<td>'+sendDate+'</td>'
								+ '<td>'+data.records[keyArray].courseCode+'</td>'
								+ '<td>'+data.records[keyArray].assignmentTaskName+'</td>';
				
				if(data.records[keyArray].status == 0){
					strHtml += '<td><span class="label label-warning"><i class="icon-refresh icon-white"></i> รอตรวจ</span></td>'
							+ '<td></td>'
							+ '<td></td>';
				}else{
					evaluateDate = Globalize.format(new Date(data.records[keyArray].evaluateDate),'dd-MM-yyyy HH:mm');
					strHtml += '<td><span class="label label-success"><i class="icon-ok icon-white"></i> ตรวจแล้ว</span></td>'
							+ '<td>'+data.records[keyArray].score+'/'+data.records[keyArray].maxScore+'</td>'
							+ '<td>'+ evaluateDate +'</td>';
				}
				strHtml+= '</tr>';
				$("#assignmentResultGrid tbody").append(strHtml);
			}
			var startRecord = (((assignmentResult.rows)*(assignmentResult.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}
			assignmentResult.lastPage = data.totalPages;
			applicationScript.setPagination(assignmentResult.page,assignmentResult.lastPage);
			$("#assignmentResultGrid").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithString(data.responseText);
		}
	});
};


assignmentResult.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$("#sendDateHeader").addClass('current-sort')
		.removeClass('sort-asc')
		.removeClass('sort-desc')
		.removeClass('sort-both')
		.addClass('sort-desc');
	assignmentResult.orderBy = "";
	assignmentResult.order = "desc";
	assignmentResult.getGrid();
};

changePage = function(page){
	if(assignmentResult.page != page){
		assignmentResult.page = page;
		assignmentResult.getGrid();
	}
};

$(document).ready(function(){	
	assignmentResult.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(assignmentResult.page > 1){
			assignmentResult.page--;
			assignmentResult.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(assignmentResult.page < assignmentResult.lastPage){
			assignmentResult.page++;
			assignmentResult.getGrid();
		}
	});
	$("#pageSize").change(function(){
		assignmentResult.page = 1;
		assignmentResult.rows = $(this).val();
		assignmentResult.getGrid();
	});

	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(assignmentResult.orderBy == myId){
			if(assignmentResult.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				assignmentResult.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				assignmentResult.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			assignmentResult.orderBy = myId;
			assignmentResult.order = "asc";
		}
		assignmentResult.getGrid();
	});
});
