application.page='genericManagement';

application.currentSection = {};

application.checkDirty = function(){
	var isDirty = false;
	if(application.currentSection.sectionId){
		if($("#sectionName").val() != application.currentSection.sectionName){
			isDirty = true;
		}else if ($("#courseId").val() != application.currentSection.courseId){
			isDirty = true;
		}
	}else{
		isDirty = true;
	}
	return isDirty;
};

$(document).ready(function(){
	application.mainGrid = $("#sectionGrid").coreGrid({
		url: application.contextPath + '/management/section.html',
		data: {
			method: 'getSectionTable'
		},
		filter: {
			sectionIdSearch : '',
			sectionNameSearch : '',
			sectionYearSearch : '',
			sectionSemesterSearch : '',
			courseCodeSearch : '',
		},
		orderBy: 'sectionName',
		tmpl: '#recordTemplate'
	}).data("plugin_coreGrid");
	
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){$(this).chosen();});
	
	$("#refreshButton").click(function(){
		$("#sectionNameSearch").val('');
		$("#sectionYearSearch").val('');
		$("#sectionSemesterSearch").val('');
		$("#courseCodeSearch").val('');
		application.mainGrid.loadDefault();
	});

	$('#searchButton').click(function(){
		application.mainGrid.search({
			sectionNameSearch : $("#sectionNameSearch").val(),
			sectionYearSearch : $("#sectionYearSearch").val(),
			sectionSemesterSearch : $("#sectionSemesterSearch").val(),
			courseCodeSearch : $("#courseCodeSearch").val()
		});
		$("#searchSectionModal").modal('hide');
	});
	
	$("#addButton").click(function(e){
		e.preventDefault();
		application.currentSection = {};
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
			if(application.checkDirty()){
				$(form).ajaxSubmit({
					type:'post',
					url: application.contextPath + '/management/section/save.html',
					success: function(){
						$('#sectionModal').modal('hide');
						applicationScript.saveCompleteTH();
						application.mainGrid.load();
						$("#saveButton").button('reset');
					},
					error: function(data){
						$('#sectionModal').modal('hide');
						applicationScript.errorAlertWithStringTH(data.responseText);
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
				sectionId: application.sectionId
			},
			success: function(){
				$("#confirmDelete").modal('hide');
				applicationScript.deleteCompleteTH();
				application.mainGrid.load();
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


deleteSection = function(sectionId){
	$("#confirmDelete").modal();
	application.sectionId = sectionId;
};

editSection = function(sectionId,masterSectionId){
	var optionText = $("#course-code-"+sectionId).text();
	$("#courseId option").filter(function() {
	    return $(this).text() == optionText; 
	}).attr('selected', true);
	$("#courseId").trigger("liszt:updated");
	
	application.currentSection = {
		sectionId:	sectionId,
		masterSectionId: masterSectionId,
		sectionName: $("#section-name-"+sectionId).text(),
		sectionYear: $("#section-year-"+sectionId).text(),
		sectionSemester: $("#section-semester-"+sectionId).text(),
		courseId: $("#courseId").val(),
		status: $("#status-"+sectionId).val()
	};
	
	application.sectionId = sectionId;
	$('#sectionForm').validate().resetForm();
	$('#sectionForm .control-group').removeClass('success').removeClass('error');
	$("#sectionId").val(sectionId);
	$("#sectionName").val(application.currentSection.sectionName);
	$("#sectionYear").text(application.currentSection.sectionYear);
	$("#sectionSemester").text(application.currentSection.sectionSemester);
	$("#masterSectionId").val(masterSectionId);
	if(application.currentSection.status==0){
		$("#statusInActive").attr("checked",true);
	}else{
		$("#statusActive").attr("checked",true);		
	}
	$("#sectionModal h3").text("แก้ไข Section");
	$("#sectionModal").modal('show');
};
