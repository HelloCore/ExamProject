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
	}else{
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
			$("#questionGroupGrid tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#questionGroupGrid tbody");
			applicationScript.calPaging(data,questionGroupManagement);
			$("#questionGroupGrid").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
			$("#questionGroupGrid").unblock();
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

$(document).ready(function(){

	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){$(this).select2();});
	questionGroupManagement.getDefaultGrid();

	applicationScript.setUpGrid(questionGroupManagement);
	
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
		$("#questionGroupModal h3").text("เพิ่มบทเรียน");
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
						applicationScript.saveCompleteTH();
						questionGroupManagement.getGrid();
						$("#saveButton").button('reset');
					},
					error: function(data){
						$('#questionGroupModal').modal('hide');
						applicationScript.resolveError(data.responseText);
						$("#saveButton").button('reset');
					}
				});
			}else{
				$('#questionGroupModal').modal('hide');
				applicationScript.alertNoDataChange();
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
				applicationScript.deleteCompleteTH();
				questionGroupManagement.getGrid();
				$("#deleteButton").button('reset');
			},
			error: function(data){
				$("#confirmDelete").modal('hide');
				applicationScript.errorAlertWithStringTH(data.responseText);
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
	
	$("#questionGroupModal h3").text("แก้ไขบทเรียน");
	$("#questionGroupModal").modal('show');
};