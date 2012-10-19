application.page='courseManagement';
viewCourse = {};
viewCourse.isAdd = false;
viewCourse.username = null;

viewCourse.getGrid = function(){
	$("#teacherGrid").block(application.blockOption);
	
	$.ajax({
		url: application.contextPath+'/management/course/view.html',
		type: 'POST',
		dataType: 'json',
		data: {
			method: 'getTeacherTable',
			courseId: application.courseId
		},
		success: function(data){
			
			$("#teacherGrid tbody").empty();
			var strHtml;

			for(keyArray in data){
				strHtml = '<tr>'
								+'<td>'+data[keyArray].username+'</td>'
								+'<td>'+data[keyArray].prefixNameTh
									+' '+data[keyArray].firstName
									+' '+data[keyArray].lastName
								+'</td>'
								+'<td>'+data[keyArray].email+'</td>'
								+'<td>'
									+'<button class="btn btn-danger" onClick="removeTeacher(\''+data[keyArray].username+'\')">'
										+'<i class="icon-white icon-trash"></i> ลบ'
									+'</button>'
								+'</td>'
						+'</tr>';
								
				$("#teacherGrid tbody").append(strHtml);
			}
			$("#teacherGrid").unblock();
		},
		error: function(data){
			$("#teacherGrid").unblock();
			applicationScript.errorAlertWithStringTH(data.responseText);
		}
	});
};

viewCourse.getUser = function(callback){
	$("#username_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath+'/management/teacherComboBox.html',
		type: 'POST',
		data: {
			courseId: application.courseId
		},
		dataType: 'json',
		success: function(data){
			if(data.length > 0 ){
				$("#username").empty();
				var strHtml;
				for(keyArray in data){
					strHtml = '<option value="'+data[keyArray].username+'">'
									+'ชื่อผู้ใช้ [ '+data[keyArray].username+' ] '
									+'ชื่อ-นามสกุล [ '+data[keyArray].prefixNameTh+' '+data[keyArray].firstName+' '+data[keyArray].lastName
									+' ]</option>';
					$("#username").append(strHtml);
				}
				$("#username_chzn").unblock();
				$("#username").trigger("liszt:updated");
				if(typeof(callback) == 'function'){
					callback();
				}
			}else{
				$(".button-holder").unblock();
				$("#username_chzn").unblock();
				applicationScript.errorAlertWithStringTH("ไม่สามารถเพิ่มอาจารย์ได้อีก เนื่องจากได้เพิ่มอาจารย์ครบทุกท่านแล้ว");
			}
		},
		error: function(data){
			$("#username_chzn").unblock();
			applicationScript.errorAlertWithStringTH(data.responseText);
		}
	});
};

viewCourse.beginAdd = function(){
	viewCourse.isAdd = true;
	$(".button-holder").block(application.blockOption);
	viewCourse.getUser(function(){
		$("#add-user-button-holder").show();
		$("#normal-button-holder").hide();
		$(".button-holder").unblock();
	});
	
};

viewCourse.cancelAdd = function(){
	viewCourse.isAdd = false;
	$(".button-holder").block(application.blockOption);
	$("#add-user-button-holder").hide();
	$("#normal-button-holder").show();
	$(".button-holder").unblock();
};

viewCourse.finishAdd = function(){
	$("#addTeacherConfirmButton").button('loading');
	$(".button-holder").block(application.blockOption);
	$.ajax({
		url: application.contextPath+'/management/course/view/save.html',
		type: 'POST',
		data: {
			courseId: application.courseId,
			username: $("#username").val()
		},
		success: function(){
			applicationScript.saveCompleteTH();
			$("#add-user-button-holder").hide();
			$("#normal-button-holder").show();
			$("#addTeacherConfirmModal").modal('hide');
			$("#addTeacherConfirmButton").button('reset');
			$(".button-holder").unblock();
			viewCourse.getGrid();
		},
		error: function(data){
			$(".button-holder").unblock();
			$("#addTeacherConfirmModal").modal('hide');
			$("#addTeacherConfirmButton").button('reset');
			applicationScript.errorAlertWithStringTH(data.responseText);
		}
	});
};

$(document).ready(function(){
	viewCourse.getGrid();
	$("#username").chosen();
	$("#addButon").click(function(){ viewCourse.beginAdd(); });
	$("#cancelButton").click(function(){ viewCourse.cancelAdd(); });
	$("#addTeacherButton").click(function(){
		$("#addTeacherConfirmModal").modal('show');
	});
	
	$("#addTeacherConfirmButton").click(function(){
		viewCourse.finishAdd();
	});
	
	$("#deleteButton").click(function(){
		var thisButton = $(this).button('loading');
		$.ajax({
			url: application.contextPath+'/management/course/view/delete.html',
			type: 'POST',
			data: {
				courseId: application.courseId,
				username: viewCourse.username
			},
			success: function(){
				thisButton.button('reset');
				$("#confirmDelete").modal('hide');
				viewCourse.getGrid();
				applicationScript.deleteCompleteTH();
			},
			error: function(data){
				thisButton.button('reset');
				$("#confirmDelete").modal('hide');
				applicationScript.errorAlertWithStringTH(data.responseText);
			}
		});
	});
});

removeTeacher = function(username){
	viewCourse.username = username;
	$("#confirmDelete").modal('show');
};