application.page='genericManagement';

sectionManagement = {};
sectionManagement.rows = 5;
sectionManagement.page = 1;
sectionManagement.lastPage = 1;
sectionManagement.sectionId = '';
sectionManagement.sectionName = '';
sectionManagement.sectionYear = '';
sectionManagement.sectionSemester = '';
sectionManagement.courseCode = '';
sectionManagement.orderBy = 'sectionName';
sectionManagement.order = 'asc';
sectionManagement.currentSection = {};

sectionManagement.checkDirty = function(){
	var isDirty = false;
	if(sectionManagement.currentSection.sectionId){
		if($("#sectionName").val() != sectionManagement.currentSection.sectionName){
			isDirty = true;
		}else if ($("#courseId").val() != sectionManagement.currentSection.courseId){
			isDirty = true;
		}
	}else{
		isDirty = true;
	}
	return isDirty;
};

sectionManagement.getGrid = function(){
	$("#sectionGrid").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/section.html',
		type: 'POST',
		data: {
			method: 'getSectionTable'
			,rows: sectionManagement.rows
			,page: sectionManagement.page
			,orderBy: sectionManagement.orderBy
			,order: sectionManagement.order
			,sectionNameSearch: sectionManagement.sectionName
			,sectionYearSearch : sectionManagement.sectionYear
			,sectionSemesterSearch: sectionManagement.sectionSemester
			,courseCodeSearch : sectionManagement.courseCode
		},
		dataType: 'json',
		success: function(data,status){
			$("#sectionGrid tbody tr").remove();
			$("#recordTemplate").tmpl(data.records).appendTo("#sectionGrid tbody");
			applicationScript.calPaging(data,sectionManagement);
			$("#sectionGrid").unblock();
		},
		error:function(data){
			applicationScript.resolveError(data.responseText);
			$("#sectionGrid").unblock();
		}
	});
};

sectionManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc sort-desc').addClass('sort-both');
	sectionManagement.orderBy = "";
	sectionManagement.order = "asc";
	sectionManagement.getGrid();
};

$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){$(this).select2();});
	sectionManagement.getDefaultGrid();
	applicationScript.setUpGrid(sectionManagement);
	
	$("#refreshButton").click(function(){
		sectionManagement.sectionName = '';
		sectionManagement.sectionYear = '';
		sectionManagement.sectionSemester = '';
		sectionManagement.courseCode = '';
		$("#sectionNameSearch").val('');
		$("#sectionYearSearch").val('');
		$("#sectionSemesterSearch").val('');
		$("#courseCodeSearch").val('');
		sectionManagement.getDefaultGrid();
	});
	
	$('#searchButton').click(function(){
		sectionManagement.page =1;
		sectionManagement.sectionName = $("#sectionNameSearch").val();
		sectionManagement.sectionYear = $("#sectionYearSearch").val();
		sectionManagement.sectionSemester = $("#sectionSemesterSearch").val();
		sectionManagement.courseCode = $("#courseCodeSearch").val();
		sectionManagement.getGrid();
		$("#searchSectionModal").modal('hide');
	});
	
	$("#addButton").click(function(e){
		e.preventDefault();
		sectionManagement.currentSection = {};
		$("#sectionModal input:text").val('');
		$("#sectionModal h3").text("เพิ่ม Section");
		$('#sectionForm').validate().resetForm();
		$("#statusActive").attr("checked",true);
		$('#sectionForm .control-group').removeClass('success').removeClass('error');
		$("#masterSectionId").val(application.masterSectionId);
		$("#sectionYear").text(application.sectionYear);
		$("#sectionSemester").text(application.sectionSemester);
		$("#sectionId").val('');
		$("#sectionModal").modal('show');
	});
	
	$('#sectionForm').validate({
		rules: {
			sectionName: {
				rangelength: [3,4],
		        required: true
			},
			courseId: {
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
			if(sectionManagement.checkDirty()){
				$(form).ajaxSubmit({
					type:'post',
					url: application.contextPath + '/management/section/save.html',
					success: function(){
						$('#sectionModal').modal('hide');
						applicationScript.saveCompleteTH();
						sectionManagement.getGrid();
						$("#saveButton").button('reset');
					},
					error: function(data){
						$('#sectionModal').modal('hide');
						applicationScript.resolveError(data.responseText);
						$("#saveButton").button('reset');
					}
				});
			}else{
				$('#sectionModal').modal('hide');
				applicationScript.alertNoDataChange();
				$("#saveButton").button('reset');
			}
		}
	});
	
	$("#saveButton").click(function(e){
		e.preventDefault();
		if ($("#sectionForm").valid()){
			$("#saveButton").button('loading');
			$("#sectionForm").submit();		
		}
	});
	
	$("#deleteButton").click(function(e){
		$("#deleteButton").button('loading');
		e.preventDefault();
		$.ajax({
			url: application.contextPath + '/management/section/delete.html',
			type: 'POST',
			data: {
				sectionId: sectionManagement.sectionId
			},
			success: function(){
				$("#confirmDelete").modal('hide');
				applicationScript.deleteCompleteTH();
				sectionManagement.getGrid();
				$("#deleteButton").button('reset');
			},
			error: function(data){
				$("#confirmDelete").modal('hide');
				applicationScript.resolveError(data.responseText);
				$("#deleteButton").button('reset');
			}
		});
	});
	
	$('.numeric').numeric();
});

deleteSection = function(sectionId){
	$("#confirmDelete").modal();
	sectionManagement.sectionId = sectionId;
};

editSection = function(sectionId,masterSectionId){
	var optionText = $("#course-code-"+sectionId).text();
	$("#courseId option").filter(function() {
	    return $(this).text() == optionText; 
	}).attr('selected', true);
	$("#courseId").select2();
	
	sectionManagement.currentSection = {
		sectionId:	sectionId,
		masterSectionId: masterSectionId,
		sectionName: $("#section-name-"+sectionId).text(),
		sectionYear: $("#section-year-"+sectionId).text(),
		sectionSemester: $("#section-semester-"+sectionId).text(),
		courseId: $("#courseId").val(),
		status: $("#status-"+sectionId).val()
	};
	
	sectionManagement.sectionId = sectionId;
	$('#sectionForm').validate().resetForm();
	$('#sectionForm .control-group').removeClass('success').removeClass('error');
	$("#sectionId").val(sectionId);
	$("#sectionName").val(sectionManagement.currentSection.sectionName);
	$("#sectionYear").text(sectionManagement.currentSection.sectionYear);
	$("#sectionSemester").text(sectionManagement.currentSection.sectionSemester);
	$("#masterSectionId").val(masterSectionId);
	if(sectionManagement.currentSection.status==0){
		$("#statusInActive").attr("checked",true);
	}else{
		$("#statusActive").attr("checked",true);		
	}
	$("#sectionModal h3").text("แก้ไข Section");
	$("#sectionModal").modal('show');
};