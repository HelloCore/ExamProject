application.page='management';
questionManagement = {};
questionManagement.initFunction = function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#questionGroupId").load(application.contextPath+"/management/questionGroupComboBox.html",{courseId:$("#courseId").val()},function(){
			$(this).trigger("liszt:updated");
		});
	});
};

$(document).ready(function(){
	$("#courseId").chosen();
	$("#questionGroupId").chosen();
	questionManagement.initFunction();
	$("#questionText").hover(function(){
		$('.search-inactive').addClass('search-active').removeClass('search-inactive');
	},function(){
		$('.search-active').removeClass('search-active').addClass('search-inactive');
	});
});

