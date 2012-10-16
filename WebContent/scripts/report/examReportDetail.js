application.page="report";

examReportDetail = {};
examReportDetail.rows = 20;
examReportDetail.page = 1;
examReportDetail.lastPage = 1;
examReportDetail.orderBy = 'studentId';
examReportDetail.order = 'asc';

examReportDetail.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#studentIdHeader").addClass('current-sort')
	.removeClass('sort-asc')
	.removeClass('sort-desc')
	.removeClass('sort-both')
	.addClass('sort-desc');
	examReportDetail.orderBy = "studentId";
	examReportDetail.order = "asc";
	examReportDetail.getGrid();
};
changePage = function(page){
	if(examReportDetail.page != page){
		examReportDetail.page = page;
		examReportDetail.getGrid();
	}
};
//
examReportDetail.getGrid = function(){
	$("#examReportDetailTable").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/report/examDetail.html',
		type: 'POST',
		data: {
			method: 'getExamDetailTable'
			,rows: $("#pageSize").val()
			,page: examReportDetail.page
			,examId: application.examId
			,order :examReportDetail.order
			,orderBy : examReportDetail.orderBy
		},
		dataType: 'json',
		success: function(data,status){
			$("#examReportDetailTable tbody").empty();
			var strHtml;
			for(keyArray in data.records){
				strHtml = '<tr>'
							+'<td>'+data.records[keyArray].studentId+'</td>'
							+'<td>'+data.records[keyArray].prefixNameTh+' '+data.records[keyArray].firstName+' '+ data.records[keyArray].lastName +'</td>'
							+'<td>'+Globalize.format(new Date(data.records[keyArray].examStartDate),'dd-MM-yyyy HH:mm')+'</td>'
							+'<td>'+data.records[keyArray].examCount+'</td>'
							+'<td>'+Globalize.format(new Date(data.records[keyArray].examCompleteDate),'dd-MM-yyyy HH:mm')+'</td>'
							+'<td>'+applicationScript.secondsToTime(data.records[keyArray].examUsedTime)+'</td>'
							+'<td>'+data.records[keyArray].examScore+' / '+data.records[keyArray].numOfQuestion+' ข้อ</td>'
							+'<td><button class="btn btn-info" onclick="viewExamReport('+data.records[keyArray].examResultId+')"><i class="icon-zoom-in icon-white"></i> ดูผลการสอบ</button></td>'
						+'</tr>';
				$("#examReportDetailTable tbody").append(strHtml);
			}
			var startRecord = (((examReportDetail.rows)*(examReportDetail.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}			
			examReportDetail.lastPage = data.totalPages;
			applicationScript.setPagination(examReportDetail.page,examReportDetail.lastPage);
			$("#examReportDetailTable").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#examReportDetailTable").unblock();
		}
	});	
};

$(document).ready(function(){
	examReportDetail.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(examReportDetail.page > 1){
			examReportDetail.page--;
			examReportDetail.getGrid();
		}
	});
	$("#refreshButton").click(function(){
		examReportDetail.getGrid();
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(examReportDetail.page < examReportDetail.lastPage){
			examReportDetail.page++;
			examReportDetail.getGrid();
		}
	});
	$("#pageSize").change(function(){
		examReportDetail.page = 1;
		examReportDetail.rows = $(this).val();
		examReportDetail.getGrid();
	});
	
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(examReportDetail.orderBy == myId){
			if(examReportDetail.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				examReportDetail.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				examReportDetail.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			examReportDetail.orderBy = myId;
			examReportDetail.order = "asc";
		}
		examReportDetail.getGrid();
	});
});

viewExamReport = function(examResultId){
	$("#examResultId").val(examResultId);
	$("#viewExamReportForm").submit();
};