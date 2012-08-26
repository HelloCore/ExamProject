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
sectionManagement.orderBy = 'sectionId';
sectionManagement.order = 'asc';

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
			$("#sectionGrid tbody").empty();
			var strHtml ;
			for(keyArray in data.records){
				strHtml = '<tr>'+
//							'<td id="section-id-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionId+'</td>'+
							'<td id="section-name-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionName+'</td>'+
							'<td id="section-year-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionYear+'</td>'+
							'<td id="section-semester-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionSemester+'</td>'+
							'<td id="course-code-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].courseCode+'</td>'+
							'<td>'+
								'<button class="btn btn-info" onClick="editSection('+data.records[keyArray].sectionId+')"><i class="icon-edit icon-white"></i> Edit</button> '+
								'<button class="btn btn-danger" onClick="deleteSection('+data.records[keyArray].sectionId+')"><i class="icon-trash icon-white"></i> Delete</button> '+
							'</td>'+
							'</tr>';
				$("#sectionGrid tbody").append(strHtml);
			}
			var startRecord = (((sectionManagement.rows)*(sectionManagement.page-1))+1);
			if(data.totalRecords==0){
				$("#gridInfo").text('Record 0 - 0 of 0 ');		
			}else{
				$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords);
			}
			sectionManagement.lastPage = data.totalPages;
			applicationScript.setPagination(sectionManagement.page,sectionManagement.lastPage);
			$("#sectionGrid").unblock();
		},
		statusCode: {
			401: function(){
				alert("Session Timeout");
				window.location = application.contextPath+'/main/login.html?target=/management/section.html';
			}
		}
	});
};

sectionManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$("#sectionIdHeader").addClass('current-sort')
		.removeClass('sort-asc')
		.removeClass('sort-desc')
		.removeClass('sort-both')
		.addClass('sort-asc');
	sectionManagement.orderBy = "sectionId";
	sectionManagement.order = "asc";
	sectionManagement.getGrid();
};

changePage = function(page){
	if(sectionManagement.page != page){
		sectionManagement.page = page;
		sectionManagement.getGrid();
	}
};
$(document).ready(function(){
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){$(this).chosen();});
	sectionManagement.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(sectionManagement.page > 1){
			sectionManagement.page--;
			sectionManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(sectionManagement.page < sectionManagement.lastPage){
			sectionManagement.page++;
			sectionManagement.getGrid();
		}
	});
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(sectionManagement.orderBy == myId){
			if(sectionManagement.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				sectionManagement.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				sectionManagement.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			sectionManagement.orderBy = myId;
			sectionManagement.order = "asc";
		}
		sectionManagement.getGrid();
	});
	$("#pageSize").change(function(){
		sectionManagement.rows = $(this).val();
		sectionManagement.getGrid();
	});
	$("#refreshButton").click(function(){
		sectionManagement.sectionName = '';
		sectionManagement.sectionYear = '';
		sectionManagement.sectionSemester = '';
		sectionManagement.courseCode = '';
		$("#sectionNameSearch").val('');
		$("#sectionYearSearch").val('');
		$("#sectionSemesterSearch").val('');
		$("#courseCodeSearch").val('');
//		$('#searchButton').removeClass('ui-state-active-search');
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
		$("#sectionModal input").val('');
		$("#sectionModal h3").text("Add Section");
		$('#sectionForm').validate().resetForm();
		$('#sectionForm .control-group').removeClass('success').removeClass('error');
		$("#sectionModal").modal('show');
	});
	$("div.modal").modal({
		backdrop: 'static',
		keyboard: true,
		show:false
	});
	$('#sectionForm').validate({
		rules: {
			sectionName: {
				rangelength: [3,4],
		        required: true
			},
			sectionYear: {
				range: [2555,2565],
		        required: true
			},
			sectionSemester: {
				range: [1,3],
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
			$(form).ajaxSubmit({
				type:'post',
				url: application.contextPath + '/management/section/save.html',
				clearForm: true,
				success: function(){
					$('#sectionModal').modal('hide');
					applicationScript.saveComplete();
					sectionManagement.getGrid();
					$("#saveButton").button('reset');
				},
				error: function(){
					$('#sectionModal').modal('hide');
					applicationScript.errorAlert();
					$("#saveButton").button('reset');
				}
			});
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
				applicationScript.deleteComplete();
				sectionManagement.getGrid();
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

deleteSection = function(sectionId){
	$("#confirmDelete").modal();
	sectionManagement.sectionId = sectionId;
};

editSection = function(sectionId){
	sectionManagement.sectionId = sectionId;
	$('#sectionForm').validate().resetForm();
	$('#sectionForm .control-group').removeClass('success').removeClass('error');
	$("#sectionId").val(sectionId);
	$("#sectionName").val($("#section-name-"+sectionId).text());
	$("#sectionYear").val($("#section-year-"+sectionId).text());
	$("#sectionSemester").val($("#section-semester-"+sectionId).text());
	
	var optionText = $("#course-code-"+sectionId).text();
	$("#courseId option").filter(function() {
	    return $(this).text() == optionText; 
	}).attr('selected', true);

	$("#courseId").trigger("liszt:updated");
	
	$("#sectionModal h3").text("Edit Section");
	$("#sectionModal").modal('show');
};