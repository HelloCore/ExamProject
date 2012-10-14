application.page="report";

examReport = {};
examReport.rows = 20;
examReport.page = 1;
examReport.lastPage = 1;
examReport.orderBy = 'endDate';
examReport.order = 'desc';

examReport.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#endDateHeader").addClass('current-sort')
	.removeClass('sort-asc')
	.removeClass('sort-desc')
	.removeClass('sort-both')
	.addClass('sort-desc');
	examReport.orderBy = "endDate";
	examReport.order = "desc";
	examReport.getGrid();
};
changePage = function(page){
	if(examReport.page != page){
		examReport.page = page;
		examReport.getGrid();
	}
};

examReport.getGrid = function(){
	$("#examTable").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath + '/management/exam.html',
		type: 'POST',
		data: {
			method: 'getExamTable'
			,rows: $("#pageSize").val()
			,page: examReport.page
			,courseId: $("#courseId").val()
			,order :examReport.order
			,orderBy : examReport.orderBy
		},
		dataType: 'json',
		success: function(data,status){
			$("#examTable tbody").empty();
			var strHtml,startDateStr,endDateStr;
			for(keyArray in data.records){
				if(data.records[keyArray].startDate == null){
					startDateStr = "ไม่กำหนด";
				}else{
					startDateStr = Globalize.format(new Date(data.records[keyArray].startDate),'dd-MM-yyyy HH:mm');
				}
				if(data.records[keyArray].endDate == null){
					endDateStr = "ไม่กำหนด";
				}else{
					endDateStr = Globalize.format(new Date(data.records[keyArray].endDate),'dd-MM-yyyy HH:mm');
				}
				strHtml = '<tr>'
							+'<td>'+data.records[keyArray].courseCode+'</td>'
							+'<td>'+data.records[keyArray].examHeader+'</td>'
							+'<td>'+startDateStr+'</td>'
							+'<td>'+endDateStr+'</td>'
							+'<td>'+data.records[keyArray].minQuestion+' ถึง '+data.records[keyArray].maxQuestion+' ข้อ</td>'
							+'<td><button class="btn btn-info" onclick="viewExamReport('+data.records[keyArray].examId+')"><i class="icon-zoom-in icon-white"></i> ดูผลการสอบ</button></td>'
						+'</tr>';
				$("#examTable tbody").append(strHtml);
			}
			var startRecord = (((examReport.rows)*(examReport.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}			
			examReport.lastPage = data.totalPages;
			applicationScript.setPagination(examReport.page,examReport.lastPage);
			$("#examTable").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#examTable").unblock();
		}
	});	
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?optionAll=1",function(){
		$(this).chosen();
		examReport.getDefaultGrid();
	});
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(examReport.page > 1){
			examReport.page--;
			examReport.getGrid();
		}
	});
	$("#refreshButton").click(function(){
		examReport.getGrid();
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
		if(myId == 'exam'){
			myId = 'examHeader';
		}
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

viewExamReport = function(examId){
	$("#examId").val(examId);
	$("#viewExamReportForm").submit();
};