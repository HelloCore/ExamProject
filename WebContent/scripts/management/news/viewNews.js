application.page='genericManagement';

viewNews = {};

viewNews.checkDirty = function(){
	var isDirty = false;
	if($("#courseId").val() != $("#oldCourseId").val()){
		isDirty = true;
	}else if ($("#newsHeader").val() != $("#oldNewsHeader").val()){
		isDirty = true;
	}else if ($("#newsContent").val() != $("#oldNewsContent").val()){
		isDirty = true;
	}
	return isDirty;
};

$(document).ready(function(){
	$("#courseId").chosen();
	$(".ckeditorarea").ckeditor();
	$("#cancelButton").click(function(){
		window.location = application.contextPath+'/management/news.html';
	});
	$("#editButton").click(function(){
		if(viewNews.checkDirty()){
			$("#confirmEditNewsModal").modal('show');
		}else{
			applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
		}
	});
	$("#confirmEditButton").click(function(){
		var thisButton = $(this).button('loading');
		$('body').block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/management/news/view.html',
			type: 'POST',
			data: {
				method: 'editNews',
				newsId: $("#newsId").val(),
				courseId: $("#courseId").val(),
				newsHeader: $("#newsHeader").val(),
				newsContent: $("#newsContent").val()
			},
			success: function(){
				$("#oldNewsHeader").val($("#newsHeader").val());
				$("#oldNewsContent").val($("#newsContent").val());
				$("#oldCourseId").val($("#courseId").val());
				applicationScript.saveComplete();
				thisButton.button('reset');
				$("#confirmEditNewsModal").modal('hide');
				$("body").unblock();
			},
			error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#confirmEditNewsModal").modal('hide');
				thisButton.button('reset');
				$("body").unblock();
			}
		});
	});
});