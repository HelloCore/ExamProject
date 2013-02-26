application.page="report";
studentReport = {};
studentReport.page = 1;
studentReport.rows = 50;
studentReport.lastPage = 1;
studentReport.orderBy = 'username';
studentReport.order = 'asc';

studentReport.loadCourseSectionIdBox = function(callback){
	$("#s2id_courseSectionId").block(application.blockOption);
	$.ajax({
		url: application.contextPath+ "/management/courseSectionComboBox.html",
		type: "GET",
		dataType: 'json',
		success: function(data){
			$("#courseSectionId optgroup").remove();
			$("#courseSectionTemplate").tmpl(data).appendTo("#courseSectionId");
			$("#courseSectionId").select2();
			if(typeof(callback)=='function'){
				callback();
			}
			$("#s2id_courseSectionId").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
};
studentReport.getGrid = function(){
	$("#studentTable").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/report/student.html',
		type: 'POST',
		data: {
			method: 'getStudentTable'
			,rows: $("#pageSize").val()
			,page: studentReport.page
			,sectionId : $("#courseSectionId").val()
			,orderBy: studentReport.orderBy
			,order: studentReport.order
		},
		dataType: 'json',
		success: function(data,status){
			$("#studentTable tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#studentTable tbody");
			applicationScript.calPaging(data,studentReport);
			$("#studentTable").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
			$("#studentTable").unblock();
		}
	});
};

studentReport.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc sort-desc').addClass('sort-both');
	$('#usernameHeader').addClass('current-sort sort-asc');
	studentReport.orderBy = "username";
	studentReport.order = "asc";
	studentReport.getGrid();
};


$(document).ready(function(){
	applicationScript.setUpGrid(studentReport);
	$("#courseSectionId").select2().change(function(){
		studentReport.getGrid();
	});
	studentReport.loadCourseSectionIdBox(function(){
		studentReport.getDefaultGrid();
	});
	$("#refreshButton").click(function(){
		studentReport.getDefaultGrid();
	});
});

viewDetail = function(studentId){
	var selected = $("#courseSectionId option:selected");
	$("#studentId").val(studentId);
	$("#courseId").val(selected.attr('courseId'));
	$("#sectionId").val(selected.val());
	$("#viewStudentDetailForm").submit();
};