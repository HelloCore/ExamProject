application.page='examManagement';
questionGroupManagement = {};
questionGroupManagement.rows = 5;
questionGroupManagement.page = 1;
questionGroupManagement.questionGroupId = '';
questionGroupManagement.questionGroupName = '';
questionGroupManagement.courseCode = '';
questionGroupManagement.orderBy = 'questionGroupId';
questionGroupManagement.order = 'asc';

questionGroupManagement.currentQuestionGroup = {};

questionGroupManagement.checkDirty = function(){
	var isDirty = false;
	if(questionGroupManagement.currentQuestionGroup.questionGroupId){
		if($("#questionGroupName").val() != questionGroupManagement.currentQuestionGroup.questionGroupName){
			isDirty = true;
		}else if($("#courseId").val() != questionGroupManagement.currentQuestionGroup.courseId){
			isDirty = true;
		}
		console.log("No");
	}else{
		console.log("No2");
		isDirty = true;
	}
	return isDirty;
};

questionGroupManagement.getGrid = function(){
	$("#questionGroupGrid").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/questionGroup.html',
		type: 'POST',
		data: {
			method: 'getQuestionGroupTable'
				,rows: questionGroupManagement.rows
				,page: questionGroupManagement.page
				,orderBy: questionGroupManagement.orderBy
				,order: questionGroupManagement.order
				,questionGroupNameSearch: questionGroupManagement.questionGroupName
				,courseCodeSearch : questionGroupManagement.courseCode
		},
		dataType: 'json',
		success: function(data,status){
			$("#questionGroupGrid tbody").empty();
			var strHtml ;
			for(keyArray in data.records){
				strHtml = '<tr>'+
							'<td id="question-group-id-'+data.records[keyArray].questionGroupId+'">'+data.records[keyArray].questionGroupId+'</td>'+
							'<td id="question-group-name-'+data.records[keyArray].questionGroupId+'">'+data.records[keyArray].questionGroupName+'</td>'+
							'<td id="course-code-'+data.records[keyArray].questionGroupId+'">'+data.records[keyArray].courseCode+'</td>'+
							'<td>'+
								'<button class="btn btn-info" onClick="editQuestionGroup('+data.records[keyArray].questionGroupId+')"><i class="icon-edit icon-white"></i> Edit</button> '+
								'<button class="btn btn-danger" onClick="deleteQuestionGroup('+data.records[keyArray].questionGroupId+')"><i class="icon-trash icon-white"></i> Delete</button> '+
							'</td>'+
							'</tr>';
				$("#questionGroupGrid tbody").append(strHtml);
			}
			var startRecord = (((questionGroupManagement.rows)*(questionGroupManagement.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 Records ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			}
			questionGroupManagement.lastPage = data.totalPages;

			applicationScript.setPagination(questionGroupManagement.page,questionGroupManagement.lastPage);
			
			$("#questionGroupGrid").unblock();
		},
		statusCode: {
			401: function(){
				alert("Session Timeout");
				window.location = application.contextPath+'/main/login.html?target=/management/questionGroup.html';
			}
		}
	});
};

questionGroupManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$("#questionGroupIdHeader").addClass('current-sort')
		.removeClass('sort-asc')
		.removeClass('sort-desc')
		.removeClass('sort-both')
		.addClass('sort-asc');
	questionGroupManagement.orderBy = "questionGroupId";
	questionGroupManagement.order = "asc";
	questionGroupManagement.getGrid();
};

changePage = function(page){
	if(questionGroupManagement.page != page){
		questionGroupManagement.page = page;
		questionGroupManagement.getGrid();
	}
};
$(document).ready(function(){

	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){$(this).chosen();});
	questionGroupManagement.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(questionGroupManagement.page > 1){
			questionGroupManagement.page--;
			questionGroupManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(questionGroupManagement.page < questionGroupManagement.lastPage){
			questionGroupManagement.page++;
			questionGroupManagement.getGrid();
		}
	});
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(questionGroupManagement.orderBy == myId){
			if(questionGroupManagement.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				questionGroupManagement.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				questionGroupManagement.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			questionGroupManagement.orderBy = myId;
			questionGroupManagement.order = "asc";
		}
		questionGroupManagement.getGrid();
	});
	$("#pageSize").change(function(){
		questionGroupManagement.rows = $(this).val();
		questionGroupManagement.getGrid();
	});
	$("#refreshButton").click(function(){
		questionGroupManagement.questionGroupName = '';
		questionGroupManagement.courseCode = '';
		$("#questionGroupNameSearch").val('');
		$("#courseCodeSearch").val('');
		questionGroupManagement.getDefaultGrid();
	});
	$('#searchButton').click(function(){
		questionGroupManagement.page =1;
		questionGroupManagement.questionGroupName = $("#questionGroupNameSearch").val();
		questionGroupManagement.courseCode = $("#courseCodeSearch").val();
		questionGroupManagement.getGrid();
		$("#searchQuestionGroupModal").modal('hide');
	});
	$("#addButton").click(function(e){
		e.preventDefault();
		questionGroupManagement.currentQuestionGroup = {};
		$("#questionGroupModal input").val('');
		$("#questionGroupModal h3").text("Add Question Group");
		$('#questionGroupForm').validate().resetForm();
		$('#questionGroupForm .control-group').removeClass('success').removeClass('error');
		$("#questionGroupModal").modal('show');
	});
	$('#questionGroupForm').validate({
		rules: {
			questionGroupName: { required: true, rangelength: [5,50] },
			courseId: {
				required:true
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

			if(questionGroupManagement.checkDirty()){
				$(form).ajaxSubmit({
					type:'post',
					url: application.contextPath + '/management/questionGroup/save.html',
					clearForm: true,
					success: function(){
						$('#questionGroupModal').modal('hide');
						applicationScript.saveComplete();
						questionGroupManagement.getGrid();
						$("#saveButton").button('reset');
					},
					error: function(){
						$('#questionGroupModal').modal('hide');
						applicationScript.errorAlert();
						$("#saveButton").button('reset');
					}
				});
			}else{
				$('#questionGroupModal').modal('hide');
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				$("#saveButton").button('reset');
				
			}
		}
	});
	
	$("#saveButton").click(function(e){
		e.preventDefault();
		if ($("#questionGroupForm").valid()){
			$("#saveButton").button('loading');
			$("#questionGroupForm").submit();		
		}
	});
	
	$("#deleteButton").click(function(e){
		$("#deleteButton").button('loading');
		e.preventDefault();
		$.ajax({
			url: application.contextPath + '/management/questionGroup/delete.html',
			type: 'POST',
			data: {
				questionGroupId: questionGroupManagement.questionGroupId
			},
			success: function(){
				$("#confirmDelete").modal('hide');
				applicationScript.deleteComplete();
				questionGroupManagement.getGrid();
				$("#deleteButton").button('reset');
			},
			error: function(){
				$("#confirmDelete").modal('hide');
				applicationScript.errorAlert();
				$("#deleteButton").button('reset');
			}
		});
	});
	
	$('.numeric').numeric();
});

deleteQuestionGroup = function(questionGroupId){
	$("#confirmDelete").modal();
	questionGroupManagement.questionGroupId = questionGroupId;
};

editQuestionGroup = function(questionGroupId){

	var optionText = $("#course-code-"+questionGroupId).text();
	$("#courseId option").filter(function() {
	    return $(this).text() == optionText; 
	}).attr('selected', true);

	$("#courseId").trigger("liszt:updated");
	
	
	questionGroupManagement.currentQuestionGroup = {
		questionGroupId : questionGroupId,
		questionGroupName : $("#question-group-name-"+questionGroupId).text(),
		courseId : $("#courseId").val()
	};
	
	$('#questionGroupForm').validate().resetForm();
	$('#questionGroupForm .control-group').removeClass('success').removeClass('error');
	questionGroupManagement.questionGroupId = questionGroupId;
	
	$("#questionGroupId").val(questionGroupId);
	$("#questionGroupName").val(questionGroupManagement.currentQuestionGroup.questionGroupName);
	
	$("#questionGroupModal h3").text("Edit QuestionGroup");
	$("#questionGroupModal").modal('show');
};