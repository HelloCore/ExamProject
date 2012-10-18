application.page='genericManagement';
newsManagement = {};
newsManagement.rows = 5;
newsManagement.page = 1;
newsManagement.lastPage = 1;
newsManagement.orderBy = 'updateDate';
newsManagement.order = 'desc';
newsManagement.courseCode = '';
newsManagement.newsHeader = '';
newsManagement.newsContent = '';

newsManagement.currentNews = null;

newsManagement.getGrid = function(){
	$("#newsGrid").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/news.html',
		type: 'POST',
		data: {
			method: 'getNewsTable'
			,rows: newsManagement.rows
			,page: newsManagement.page
			,orderBy: newsManagement.orderBy
			,order: newsManagement.order
			,courseCodeSearch: newsManagement.courseCode
			,newsHeaderSearch : newsManagement.newsHeader
			,newsContentSearch: newsManagement.newsContent
		},
		dataType: 'json',
		success: function(data,status){
			$("#newsGrid tbody").empty();
			var strHtml,courseCodeStr;
			for(keyArray in data.records){
				if(data.records[keyArray].courseCode){
					courseCodeStr = data.records[keyArray].courseCode;
				}else{
					courseCodeStr = 'ไม่มี';
				}
				strHtml = '<tr>'+
							'<td>'+Globalize.format(new Date(data.records[keyArray].updateDate),'dd-MM-yyyy HH:mm:ss')+'</td>'+
							'<td>'+courseCodeStr+'</td>'+
							'<td>'+data.records[keyArray].newsHeader+'</td>'+
							'<td>'+data.records[keyArray].firstName+' '+data.records[keyArray].lastName+'</td>'+
							'<td>'+
								'<button class="btn btn-info" onClick="viewNews('+data.records[keyArray].newsId+')"><i class="icon-edit icon-white"></i> แก้ไข</button>'+
								' <button class="btn btn-danger" onClick="deleteNews('+data.records[keyArray].newsId+')"><i class="icon-trash icon-white"></i> ลบ</button>'+
							'</td>'+
						'</tr>';
				
				$("#newsGrid tbody").append(strHtml);
			}
			var startRecord = (((newsManagement.rows)*(newsManagement.page-1))+1);
			applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
			
			newsManagement.lastPage = data.totalPages;
			applicationScript.setPagination(newsManagement.page,newsManagement.lastPage);
			$("#newsGrid").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#newsGrid").unblock();
		}
	});
};

newsManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	newsManagement.orderBy = "updateDate";
	newsManagement.order = "desc";
	newsManagement.getGrid();
};

$(document).ready(function(){
	newsManagement.getDefaultGrid();
	$("#addButton").click(function(){
		window.location = application.contextPath+ '/management/news/add.html';
	});
	$("#refreshButton").click(function(){
		newsManagement.courseCode = '';
		newsManagement.newsHeader = '';
		newsManagement.newsContent = '';
		$("#courseCodeSearch").val('');
		$("#newsHeaderSearch").val('');
		$("#newsContentSearch").val('');
		newsManagement.getDefaultGrid();
	});
	$("#pageSize").change(function(){
		newsManagement.page = 1;
		newsManagement.rows = $(this).val();
		newsManagement.getGrid();
	});

	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		myId = myId=='news' ? 'newsHeader' : myId;
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(newsManagement.orderBy == myId){
			if(newsManagement.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				newsManagement.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				newsManagement.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			newsManagement.orderBy = myId;
			newsManagement.order = "asc";
		}
		newsManagement.getGrid();
	});

	$('#searchButton').click(function(){
		newsManagement.page =1;
		newsManagement.courseCode = $("#courseCodeSearch").val();
		newsManagement.newsHeader = $("#newsHeaderSearch").val();
		newsManagement.newsContent = $("#newsContentSearch").val();
		newsManagement.getGrid();
		$("#searchNewsModal").modal('hide');
	});
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(newsManagement.page > 1){
			newsManagement.page--;
			newsManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(newsManagement.page < newsManagement.lastPage){
			newsManagement.page++;
			newsManagement.getGrid();
		}
	});
	$("#deleteButton").click(function(){
		var thisButton = $(this).button('loading');
		$("body").block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/management/news.html',
			type: 'POST',
			data: {
				method: 'deleteNews',
				newsId: newsManagement.currentNews
			},
			success: function(){
				$("#confirmDelete").modal('hide');
				applicationScript.deleteComplete();
				$("body").unblock();
				thisButton.button('reset');
				newsManagement.getGrid();
			},
			error: function(data){
				$("#confirmDelete").modal('hide');
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("body").unblock();
				thisButton.button('reset');
			}
		});
	});
});

viewNews = function(newsId){
	$("#newsId").val(newsId);
	$("#editNewsForm").submit();
};

deleteNews = function(newsId){
	newsManagement.currentNews = newsId;
	$("#confirmDelete").modal('show');
	
};