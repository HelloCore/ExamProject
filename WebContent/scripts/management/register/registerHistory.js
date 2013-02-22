application.page='genericManagement';
registerHistory = {};
registerHistory.rows = 20;
registerHistory.page = 1;
registerHistory.lastPage = 1;
registerHistory.sectionId = '';
registerHistory.sectionName = '';
registerHistory.sectionYear = '';
registerHistory.sectionSemester = '';
registerHistory.courseCode = '';
registerHistory.orderBy = '';
registerHistory.order = 'asc';


registerHistory.getGrid = function(){
	$("#registerHistoryTable").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/register/history.html',
		type: 'POST',
		data: {
			method: 'getHistoryTable'
			,rows: registerHistory.rows
			,page: registerHistory.page
			,orderBy: registerHistory.orderBy
			,order: registerHistory.order
			,courseId: $("#courseId").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#registerHistoryTable tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#registerHistoryTable tbody");
			applicationScript.calPaging(data,registerHistory);
			$("#registerHistoryTable").unblock();
		},
		error: function(data){
			$("#registerHistoryTable").unblock();
			applicationScript.resolveError(data.responseText);
		}
	});
};

registerHistory.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	registerHistory.orderBy = "";
	registerHistory.order = "asc";
	registerHistory.getGrid();
};

registerHistory.initCourseComboBox = function(callback){
	$("#s2id_courseId").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?",function(){
		$(this).select2();
		$("#s2id_courseId").unblock();
		if(typeof(callback)=='function'){
			callback();
		}
	});
};

$(document).ready(function(){
	$("#courseId").select2();
	registerHistory.initCourseComboBox(function(){
		registerHistory.getDefaultGrid();
		applicationScript.setUpGrid(registerHistory);
	});
	$("#filterButton").click(function(){
		registerHistory.getGrid();
	});
});