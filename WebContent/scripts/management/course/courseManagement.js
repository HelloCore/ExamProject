application.page='courseManagement';
courseManagement = {};
courseManagement.rows = 5;
courseManagement.page = 1;
courseManagement.lastPage = 1;
courseManagement.courseCode = '';
courseManagement.courseName = '';
courseManagement.orderBy = 'courseCode';
courseManagement.order = 'asc';
courseManagement.currentCourse = {};

courseManagement.checkDirty = function(){
	var isDirty = false;
	if(courseManagement.currentCourse.courseId){
		if($("#courseName").val() != courseManagement.currentCourse.courseName){
			isDirty = true;
		}else if ($("#courseCode").val() != courseManagement.currentCourse.courseCode){
			isDirty = true;
		}
	}else{
		isDirty = true;
	}
	return isDirty;
};

courseManagement.getGrid = function(){
	$("#courseGrid").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/course.html',
		type: 'POST',
		data: {
			method: 'getCourseTable'
			,rows: courseManagement.rows
			,page: courseManagement.page
			,orderBy: courseManagement.orderBy
			,order: courseManagement.order
			,courseCode: courseManagement.courseCode
			,courseName: courseManagement.courseName
		},
		dataType: 'json',
		success: function(data,status){
			$("#courseGrid tbody").empty();
			var strHtml;
			for(keyArray in data.records){
				strHtml = '<tr>'
								+'<td id="course-code-'+data.records[keyArray].courseId+'">'+data.records[keyArray].courseCode+'</td>'
								+'<td id="course-name-'+data.records[keyArray].courseId+'">'+data.records[keyArray].courseName+'</td>'
								+'<td>'
									+'<button class="btn btn-success btn-in-table" onClick="viewCourse('+data.records[keyArray].courseId+')"><i class="icon-zoom-in icon-white"></i> View</button> '
									+'<button class="btn btn-info btn-in-table" onClick="editCourse('+data.records[keyArray].courseId+')"><i class="icon-edit icon-white"></i> Edit</button> '
									+'<button class="btn btn-danger btn-in-table" onClick="deleteCourse('+data.records[keyArray].courseId+')"><i class="icon-trash icon-white"></i> Delete</button> '
								+'</td>'
							+'</tr>';
				$("#courseGrid tbody").append(strHtml);
			}
			var startRecord = (((courseManagement.rows)*(courseManagement.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords);
			}
			courseManagement.lastPage = data.totalPages;
			applicationScript.setPagination(courseManagement.page,courseManagement.lastPage);
			$("#courseGrid").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#courseGrid").unblock();
		}
	});
};
//
courseManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$("#courseCodeHeader").addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc').addClass('sort-asc');
	
	courseManagement.orderBy = "courseCode";
	courseManagement.order = "asc";
	courseManagement.getGrid();
};

changePage = function(page){
	if(courseManagement.page != page){
		courseManagement.page = page;
		courseManagement.getGrid();
	}
};

$(document).ready(function(){
	courseManagement.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(courseManagement.page > 1){
			courseManagement.page--;
			courseManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(courseManagement.page < courseManagement.lastPage){
			courseManagement.page++;
			courseManagement.getGrid();
		}
	});
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(courseManagement.orderBy == myId){
			if(courseManagement.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				courseManagement.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				courseManagement.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			courseManagement.orderBy = myId;
			courseManagement.order = "asc";
		}
		courseManagement.getGrid();
	});
	$("#pageSize").change(function(){
		courseManagement.page = 1;
		courseManagement.rows = $(this).val();
		courseManagement.getGrid();
	});
	$("#refreshButton").click(function(){
		courseManagement.courseCode = '';
		courseManagement.courseName = '';
		$("#courseCodeSearch").val('');
		$("#courseNameSearch").val('');
		courseManagement.getDefaultGrid();
	});
	$('#searchButton').click(function(){
		courseManagement.page =1;
		courseManagement.courseCode = $("#courseCodeSearch").val();
		courseManagement.courseName = $("#courseNameSearch").val();
		courseManagement.getGrid();
		$("#searchCourseModal").modal('hide');
	});
	$("#addButton").click(function(e){
		e.preventDefault();
		courseManagement.currentCourse = {};
		$("#courseModal input:text").val('');
		$('#courseForm').validate().resetForm();
		$('#courseForm .control-group').removeClass('success').removeClass('error');
		$("#courseModal").modal('show');
	});
	$('#courseForm').validate({
		rules: {
			courseCode: {
				rangelength: [3,6],
		        required: true
			},
			courseName: {
				rangelength: [5,100],
		        required: true
			}
		},
	    highlight: function(label) {
	    	$(label).closest('.control-group').removeClass('success').addClass('error');
	    },
	    success: function(label) {
	    	label
	    		.text('OK!').addClass('valid')
	    		.closest('.control-group').removeClass('error').addClass('success');
	    },
		submitHandler: function(form) {
			if(courseManagement.checkDirty()){
				$(form).ajaxSubmit({
					type:'post',
					url: application.contextPath + '/management/course/save.html',
					clearForm: true,
					success: function(){
						$('#courseModal').modal('hide');
						applicationScript.saveComplete();
						courseManagement.getGrid();
						$("#saveButton").button('reset');
					},
					error: function(data){
						$('#courseModal').modal('hide');
						applicationScript.errorAlertWithStringTH(data.responseText);
						$("#saveButton").button('reset');
					}
				});
			}else{
				$('#courseModal').modal('hide');
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				$("#saveButton").button('reset');
			}
		}
	});
	
	$("#saveButton").click(function(e){
		e.preventDefault();
		if ($("#courseForm").valid()){
			$("#saveButton").button('loading');
			$("#courseForm").submit();		
		}
	});
	
	$("#deleteButton").click(function(e){
		$("#deleteButton").button('loading');
		e.preventDefault();
		$.ajax({
			url: application.contextPath + '/management/course/delete.html',
			type: 'POST',
			data: {
				courseId: courseManagement.courseId
			},
			success: function(){
				$("#confirmDelete").modal('hide');
				applicationScript.deleteComplete();
				courseManagement.getGrid();
				$("#deleteButton").button('reset');
			},
			error: function(data){
				$("#confirmDelete").modal('hide');
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#deleteButton").button('reset');
			}
		});
	});
	
});

deleteCourse = function(courseId){
	$("#confirmDelete").modal();
	courseManagement.courseId = courseId;
};

editCourse = function(courseId){
	courseManagement.currentCourse = {
		courseId: courseId,
		courseCode: $("#course-code-"+courseId).text(),
		courseName: $("#course-name-"+courseId).text()
	};
	
	courseManagement.courseId = courseId;
	$('#courseForm').validate().resetForm();
	$('#courseForm .control-group').removeClass('success').removeClass('error');
	$("#courseId").val(courseId);
	$("#courseCode").val(courseManagement.currentCourse.courseCode);
	$("#courseName").val(courseManagement.currentCourse.courseName);
	$("#courseModal h3").text("Edit Course");
	$("#courseModal").modal('show');
};

viewCourse = function(courseId){
	$("#viewCourseId").val(courseId);
	$("#viewDetailCourseForm").submit();
};