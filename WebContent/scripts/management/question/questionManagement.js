application.page='examManagement';
questionManagement = {};
questionManagement.rows = 5;
questionManagement.page = 1;
questionManagement.lastPage = 1;
questionManagement.questionId = null;

questionManagement.getDefaultGrid = function(){
	$('.search-active').removeClass('search-active').addClass('search-inactive');
	$("#questionText").val('');
	questionManagement.getGrid();
};

questionManagement.getGrid = function(){
	$("#questionDivHolder").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/question.html',
		type: 'POST',
		data: {
			method: 'getQuestionTable'
			,rows: $("#pageSize").val()
			,page: questionManagement.page
			,courseId: $("#courseId").val()
			,questionGroupId: $("#questionGroupId").val()
			,questionText: $("#questionText").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#questionDivHolder table").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#questionDivHolder");
			applicationScript.calPaging(data,questionManagement);
			$("#questionDivHolder").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
			$("#questionDivHolder").unblock();
		}
	});
};

questionManagement.getQuestionGroupComboBox = function(callback){
	$("#questionGroupId").load(application.contextPath+"/management/questionGroupComboBox.html",{courseId:$("#courseId").val(),optionAll:true},function(){
		$(this).select2();
		$("#filterPanel").unblock();
		if(typeof(callback)=='function'){
			callback();
		}
	});
	
};
questionManagement.initFunction = function(){
	$("#filterPanel").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2();
		questionManagement.getQuestionGroupComboBox(function(){
			questionManagement.getDefaultGrid();
			applicationScript.setUpGrid(questionManagement);
			
		});
	});
};

questionManagement.search = function(){
	questionManagement.page =1;
	questionManagement.getGrid();
	$('.search-inactive').addClass('search-active').removeClass('search-inactive');
	if($("#questionText").val().length==0){
		$('.search-active').removeClass('search-active').addClass('search-inactive');
	}
};

$(document).ready(function(){
	$("#courseId").select2().change(function(){
		$("#s2id_questionGroupId").block(application.blockOption);
		questionManagement.getQuestionGroupComboBox(function(){
			$("#s2id_questionGroupId").unblock();
		});
	});
	$("#questionGroupId").select2();
	questionManagement.initFunction();
	$("#refreshButton").click(function(){
		questionManagement.getDefaultGrid();
	});
	$("#addButton").click(function(){
		window.location = application.contextPath+'/management/question/add.html';
	});
	$("#searchButton").click(function(){
		questionManagement.search();
	});
	$("#questionText").keypress(function(event){
		 if ( event.which == 13 ) {
		     event.preventDefault();
		     questionManagement.search();
		 }
	});
	$("#deleteButton").click(function(){
		var thisButton = $(this);
		thisButton.button('loading');
		$.ajax({
			url: application.contextPath + '/management/question.html',
			type: 'POST',
			data: {
				method: 'deleteQuestion',
				questionId: questionManagement.questionId
			},
			success: function(){
				thisButton.button('reset');
				applicationScript.deleteCompleteTH();
				$("#confirmDelete").modal('hide');
				questionManagement.getGrid();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.resolveError(data.responseText);
				$("#confirmDelete").modal('hide');
			}
		});
	});

	var $window = $(window);
	$("#leftBar").affix({
		offset: {
			top: function () { return $window.width() <= 980 ? 207 : 167; }
		}
	});
});

viewQuestion = function(questionId){
	$("#questionId").val(questionId);
	$("#viewQuestionForm").submit();
};

deleteQuestion = function(questionId){
	questionManagement.questionId = questionId;
	$("#confirmDelete").modal('show');
};