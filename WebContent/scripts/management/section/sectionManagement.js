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
			$("#sectionGrid tbody").empty();
			var strHtml,labelActive = '<span class="label label-success"><i class="icon-ok icon-white"></i> เปิดใช้งาน</span>'
					,labelInActive = '<span class="label label-important"><i class="icon-ban-circle icon-white"></i> ปิดการใช้งาน</span>';
			for(keyArray in data.records){
				strHtml = '<tr>'+
							'<td id="section-year-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionYear+'</td>'+
							'<td id="section-semester-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionSemester+'</td>'+
							'<td id="course-code-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].courseCode+'</td>'+
							'<td id="section-name-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionName+'</td>'+
							'<td><input type="hidden" id="status-'+data.records[keyArray].sectionId+'" value="'+data.records[keyArray].status+'" />';
				if(data.records[keyArray].status==0){
					strHtml+=labelInActive;
				}else{
					strHtml+=labelActive;
				}	
				strHtml += '</td>'+
							'<td>'+
								'<button class="btn btn-info" onClick="editSection('+data.records[keyArray].sectionId+','+data.records[keyArray].masterSectionId+')"><i class="icon-edit icon-white"></i> แก้ไข</button> '+
								'<button class="btn btn-danger" onClick="deleteSection('+data.records[keyArray].sectionId+')"><i class="icon-trash icon-white"></i> ลบ</button> '+
							'</td>'+
							'</tr>';
				$("#sectionGrid tbody").append(strHtml);
			}
			var startRecord = (((sectionManagement.rows)*(sectionManagement.page-1))+1);
			
			applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
			
			
			sectionManagement.lastPage = data.totalPages;
			applicationScript.setPagination(sectionManagement.page,sectionManagement.lastPage);
			$("#sectionGrid").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#sectionGrid").unblock();
		}
	});
};

sectionManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	sectionManagement.orderBy = "";
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
		sectionManagement.page = 1;
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
						applicationScript.saveComplete();
						sectionManagement.getGrid();
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
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
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
				applicationScript.deleteComplete();
				sectionManagement.getGrid();
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
	sectionManagement.sectionId = sectionId;
};

editSection = function(sectionId,masterSectionId){
	var optionText = $("#course-code-"+sectionId).text();
	$("#courseId option").filter(function() {
	    return $(this).text() == optionText; 
	}).attr('selected', true);
	$("#courseId").trigger("liszt:updated");
	
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