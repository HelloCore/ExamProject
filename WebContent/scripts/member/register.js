application.page='register';
register = {};
register.getGrid = function(){
	$("#registerTable").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/member/register.html',
		type: 'POST',
		data: {
			method: 'getRegisterTable'
		},
		dataType: 'json',
		success: function(data,status){
			$("#registerTable tbody").empty();
			var strHtml;
			for(key in data){
				nowDate = new Date(data[key].requestDate);
				strHtml = '<tr>'
							+'<td>'+Globalize.format( new Date(data[key].requestDate),"dd-MM-yyyy HH:mm:ss")+'</td>'
							+'<td>'+data[key].courseCode+'</td>'
							+'<td>'+data[key].sectionName+'</td>'
							+'<td>'+data[key].sectionSemester+'/'+data[key].sectionYear+'</td>';
				if(data[key].status==0){
					strHtml += '<td><span class="label label-warning"><i class="icon-refresh icon-white"></i> Pending</span></td>'
							+ '<td><button class="btn btn-danger" onclick="cancelRegister('+data[key].registerId+')"><i class="icon-trash icon-white"></i> ยกเลิก</button></td>';
				}else if (data[key].status==1){
					strHtml += '<td><span class="label label-success"><i class="icon-ok icon-white"></i> Accept</span></td>'
							+	'<td><button class="btn btn-info" onclick="changeSection('+data[key].registerId+')"><i class="icon-edit icon-white"></i> ย้าย Section</button></td>';
				}else{
					strHtml += '<td><span class="label label-important"><i class="icon-ban-circle icon-white"></i> Denied</span></td><td></td>';
				}
				strHtml+='</tr>';
				$("#registerTable tbody").append(strHtml);
			}
			$("#registerTable").unblock();
		},
		error: function(data){
			$("#registerTable").unblock();
		}
	});
	
};
register.loadCourseIdBox = function(load,callback){
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/member/registerCourseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		if($(this).find("option").length==0){
			applicationScript.errorAlertWithStringTH("คุณไม่สามารถลงทะเบียนได้ เนื่องจากคุณลงทะเบียนไปแล้ว หรืออาจารย์ผู้สอนยังไม่เปิด Section เพิ่ม");
		}else{
			if(load){
				register.loadSectionIdBox(callback);
			}else{
				if(typeof(callback)=='function'){
					callback();
				}
			}
		}
	});
	$("#courseId_chzn").unblock();
};
register.loadSectionIdBox = function(callback){
	$("#sectionId_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath+ "/member/registerSectionComboBox.html",
		type: "POST",
		dataType: 'json',
		data:{
			courseId : $('#courseId').val()
		},
		success: function(data){
			var newData = '',nowSemester=null,nowYear=null,isFirst=true;
			for(key in data){
				if( nowSemester != data[key][3] || nowYear != data[key][2]){
					nowSemester = data[key][3];
					nowYear = data[key][2];
					if(!isFirst){
						newData+= '</optgroup>';
					}else{
						isFirst = false;
					}
					newData += '<optgroup label="เทอม '+nowSemester+' ปี '+nowYear+'">';
				}
				newData += '<option value="'+data[key][0]+'"'
				+' sectionYear="'+data[key][2]+'"'
				+' sectionSemester="'+data[key][3]+'"'
				+' sectionName="'+data[key][1]+'"'
				+'>เทอม '+data[key][3]+' ปี '+data[key][2]+' ['+data[key][1]+']</option>';
			}
			$("#sectionId").empty().html(newData).trigger("liszt:updated");
			if($("#sectionId").find("option").length==0){
				applicationScript.errorAlertWithStringTH("คุณไม่สามารถลงทะเบียนได้ เนื่องจากคุณลงทะเบียนไปแล้ว หรืออาจารย์ผู้สอนยังไม่เปิด Section เพิ่ม");					
			}else{
				if(typeof(callback)=='function'){
					callback();
				}
			}
			$("#sectionId_chzn").unblock();
		}
	});
};

$(document).ready(function(){
	register.getGrid();
	$("#courseId").chosen().change(function(){
		register.loadSectionIdBox();
	});
	$("#sectionId").chosen();
	$("#registerButton").click(function(){
		$(".button-holder").block(application.blockOption);
		register.loadCourseIdBox(true,function(){
			$("#normal-button-holder").hide();
			$("#register-button-holder").show();
		});
		$(".button-holder").unblock();
	});
	$("#cancelButton").click(function(){
		$(".button-holder").block(application.blockOption);
		$("#normal-button-holder").show();
		$("#register-button-holder").hide();
		$(".button-holder").unblock();
	});
	$("#registerModalButton").click(function(){
		var courseText = $("#courseId option:checked").text(),
			sectionChoose = $("#sectionId option:checked"),
			modalBody = "คุณต้องการลงทะเบียนวิชา "+courseText+
					" Section ["+sectionChoose.attr("sectionName")+
					"] เทอม "+sectionChoose.attr("sectionSemester")+ 
					" ปี "+sectionChoose.attr("sectionYear")+
					" ใช่หรือไม่ ?";
		$("#confirmRegisterModal div.modal-body").text(modalBody);
		$("#confirmRegisterModal").modal('show');
	});
	$("#confirmRegisterButton").click(function(){
		var thisButton = $(this).button('loading');
		$('body').block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/member/register.html',
			type: 'POST',
			data: {
				method: 'register',
				courseId: $("#courseId").val(),
				sectionId: $("#sectionId").val()
			},
			dataType: 'json',
			success: function(data,status){
				applicationScript.successAlertWithStringHeader("ลงทะเบียนสำเร็จ กรุณารออาจารย์ผู้สอนอนุมัติ","Success");
				$("#confirmRegisterModal").modal('hide');
				thisButton.button('reset');
				$("#normal-button-holder").show();
				$("#register-button-holder").hide();
				register.getGrid();
				$("body").unblock();
			},
			error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#confirmRegisterModal").modal('hide');
				thisButton.button('reset');
				$("#normal-button-holder").show();
				$("#register-button-holder").hide();
				$("body").unblock();
			}
		});
	});
	$("#confirmCancelButton").click(function(){
		var thisButton = $(this).button('loading');
		$('body').block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/member/register.html',
			type: 'POST',
			data: {
				method: 'cancelRegister',
				registerId : register.currentRegisterId
			},
			dataType: 'json',
			success: function(data,status){
				applicationScript.successAlertWithStringHeader("ยกเลิกการลงทะเบียนสำเร็จ","Success");
				$("#confirmCancelModal").modal('hide');
				thisButton.button('reset');
				register.getGrid();
				$("body").unblock();
			},error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#confirmCancelModal").modal('hide');
				thisButton.button('reset');
				$("body").unblock();
			}
		});
	});
});


cancelRegister = function(registerId){
	register.currentRegisterId = registerId;
	$("#confirmCancelModal").modal('show');
};

