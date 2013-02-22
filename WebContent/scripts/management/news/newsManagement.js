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
			$("#newsGrid tbody tr").empty();
			$("#recordTemplate").tmpl(data.records).appendTo("#newsGrid tbody");
			applicationScript.calPaging(data,newsManagement);
			$("#newsGrid").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
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
	applicationScript.setUpGrid(newsManagement);
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


	$('#searchButton').click(function(){
		newsManagement.page =1;
		newsManagement.courseCode = $("#courseCodeSearch").val();
		newsManagement.newsHeader = $("#newsHeaderSearch").val();
		newsManagement.newsContent = $("#newsContentSearch").val();
		newsManagement.getGrid();
		$("#searchNewsModal").modal('hide');
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
				applicationScript.resolveError(data.responseText);
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