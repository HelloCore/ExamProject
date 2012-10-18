application.page="report";
studentReport = {};
studentReport.page = 1;
studentReport.rows = 50;
studentReport.lastPage = 1;
studentReport.orderBy = 'username';
studentReport.order = 'asc';

studentReport.loadCourseSectionIdBox = function(callback){
	$("#courseSectionId_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath+ "/management/courseSectionComboBox.html",
		type: "GET",
		dataType: 'json',
		success: function(data){
			var newData = '',nowCourseId=null,isFirst=true;
			for(key in data){
				if(nowCourseId != data[key][4]){
					nowCourseId = data[key][4];
					if(!isFirst){
						newData+= '</optgroup>';
					}else{
						isFirst = false;
					}
					newData += '<optgroup label="วิชา '+data[key][5]+'">';
				}
				newData += '<option value="'+data[key][0]+'"'
				+' sectionYear="'+data[key][2]+'"'
				+' sectionSemester="'+data[key][3]+'"'
				+' sectionName="'+data[key][1]+'"'
				+' courseId="'+data[key][4]+'"'
				+' courseCode="'+data[key][5]+'"'
				+' >วิชา '+data[key][5]+' เทอม '+data[key][3]+' ปี '+data[key][2]+' ['+data[key][1]+']</option>';
			}
			$("#courseSectionId").empty().html(newData).trigger("liszt:updated");
			if(typeof(callback)=='function'){
				callback();
			}
			$("#courseSectionId_chzn").unblock();
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
			$("#studentTable tbody").empty();
			var strHtml;
			for(keyArray in data.records){
				strHtml = '<tr>'
								+'<td>'+data.records[keyArray].studentId+'</td>'
								+'<td>'+data.records[keyArray].prefixNameTh+' '+data.records[keyArray].firstName+' '+data.records[keyArray].lastName+'</td>'
								+'<td><button class="btn btn-info" onClick="viewDetail(\''+data.records[keyArray].studentId+'\')"><i class="icon-zoom-in icon-white"></i> ดูรายละเอียด</button></td>'
							+'</tr>';
				$("#studentTable tbody").append(strHtml);
			}
			var startRecord = (((studentReport.rows)*(studentReport.page-1))+1);
			applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
			
			studentReport.lastPage = data.totalPages;
			applicationScript.setPagination(studentReport.page,studentReport.lastPage);
			$("#studentTable").unblock();
		},
		error:function(data){
			applicationScript.errorAlertWithStringTH(data.responseText);
			$("#studentTable").unblock();
		}
	});
};

studentReport.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$('#usernameHeader').addClass('current-sort').addClass('sort-asc');
	studentReport.orderBy = "username";
	studentReport.order = "asc";
	studentReport.getGrid();
};

changePage = function(page){
	if(studentReport.page != page){
		studentReport.page = page;
		studentReport.getGrid();
	}
};

$(document).ready(function(){
	$("#courseSectionId").chosen().change(function(){
		studentReport.getGrid();
	});
	studentReport.loadCourseSectionIdBox(function(){
		studentReport.getDefaultGrid();
	});
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(studentReport.page > 1){
			studentReport.page--;
			studentReport.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(studentReport.page < studentReport.lastPage){
			studentReport.page++;
			studentReport.getGrid();
		}
	});
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(studentReport.orderBy == myId){
			if(studentReport.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				studentReport.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				studentReport.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			studentReport.orderBy = myId;
			studentReport.order = "asc";
		}
		studentReport.getGrid();
	});
	$("#pageSize").change(function(){
		studentReport.page = 1;
		studentReport.rows = $(this).val();
		studentReport.getGrid();
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