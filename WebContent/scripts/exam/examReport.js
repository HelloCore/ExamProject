application.page='exam';

examReport = {};
examReport.rows = 10;
examReport.page = 1;
examReport.lastPage = 1;
examReport.orderBy = 'examCompleteDate';
examReport.order = 'asc';

examReport.getGrid = function(){
	$("#examReportGrid").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/exam/examReport.html',
		type: 'POST',
		data: {
			method: 'getExamReport'
			,rows: examReport.rows
			,page: examReport.page
			,orderBy: examReport.orderBy
			,order: examReport.order
		},
		dataType: 'json',
		success: function(data,status){
			$("#examReportGrid tbody").empty();
			var strHtml,completeDateStr,usedTimeStr;
			for(keyArray in data.records){
				completeDateStr = Globalize.format(new Date(data.records[keyArray].examCompleteDate),'dd-MM-yyyy HH:mm');
				usedTimeStr = applicationScript.secondsToTime(data.records[keyArray].examUsedTime);
				strHtml = '<tr>'
								+ '<td>'+completeDateStr+'</td>'
								+ '<td>'+data.records[keyArray].courseCode+'</td>'
								+ '<td>'+data.records[keyArray].examHeader+'</td>'
								+ '<td>'+data.records[keyArray].examCount+'</td>'
								+ '<td>'+data.records[keyArray].numOfQuestion+' ข้อ</td>'
								+ '<td>'+usedTimeStr+'</td>'
								+ '<td>'+data.records[keyArray].examScore+'</td>'
							+'</tr>';
				$("#examReportGrid tbody").append(strHtml);
			}
			var startRecord = (((examReport.rows)*(examReport.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}
			examReport.lastPage = data.totalPages;
			applicationScript.setPagination(examReport.page,examReport.lastPage);
			$("#examReportGrid").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithString(data.responseText);
		}
	});
};


examReport.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$("#examCompleteDateHeader").addClass('current-sort')
		.removeClass('sort-asc')
		.removeClass('sort-desc')
		.removeClass('sort-both')
		.addClass('sort-desc');
	examReport.orderBy = "examCompleteDate";
	examReport.order = "desc";
	examReport.getGrid();
};

changePage = function(page){
	if(examReport.page != page){
		examReport.page = page;
		examReport.getGrid();
	}
};

$(document).ready(function(){	
	examReport.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(examReport.page > 1){
			examReport.page--;
			examReport.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(examReport.page < examReport.lastPage){
			examReport.page++;
			examReport.getGrid();
		}
	});
	$("#pageSize").change(function(){
		examReport.page = 1;
		examReport.rows = $(this).val();
		examReport.getGrid();
	});

	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(examReport.orderBy == myId){
			if(examReport.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				examReport.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				examReport.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			examReport.orderBy = myId;
			examReport.order = "asc";
		}
		examReport.getGrid();
	});
});
