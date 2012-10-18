
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
					strHtml += '<td><span class="label label-warning"><i class="icon-refresh icon-white"></i> รออนุมัติ</span></td>'
							+ '<td><button class="btn btn-danger" onclick="cancelRegister('+data[key].registerId+')"><i class="icon-trash icon-white"></i> ยกเลิก</button></td>';
				}else if (data[key].status==1){
					strHtml += '<td><span class="label label-success"><i class="icon-ok icon-white"></i> อนุมัติแล้ว</span></td>'
							+	'<td><button class="btn btn-info btn-change-section" id="change-section-button-'+data[key].registerId+'" onclick="changeSection('+data[key].registerId+','+data[key].courseId+','+data[key].sectionId+')" data-loading-text="เลือก Section ใหม่..."><i class="icon-edit icon-white"></i> ย้าย Section</button></td>';
				}else if (data[key].status==2){
					strHtml += '<td><span class="label label-important"><i class="icon-ban-circle icon-white"></i> ไม่อนุมัติ</span></td><td></td>';
				}else if (data[key].status==3){
					strHtml += '<td><span class="label label-info"><i class="icon-repeat icon-white"></i> รออนุมัติ</span></td><td></td>';
				}else{
					strHtml += '<td><span class="label label-inverse"><i class="icon-exclamation-sign icon-white"></i> Unknow</span></td><td></td>';
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

register.loadCourseSectionIdBox = function(callback){
	$("#courseSectionId_chzn").block(application.blockOption);
	$.ajax({
		url: application.contextPath+ "/member/registerCourseSectionComboBox.html",
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
				+' >เทอม '+data[key][3]+' ปี '+data[key][2]+' ['+data[key][1]+']</option>';
			}
			$("#courseSectionId").empty().html(newData).trigger("liszt:updated");
			if($("#courseSectionId").find("option").length==0){
				applicationScript.errorAlertWithStringTH("คุณไม่สามารถลงทะเบียนได้ เนื่องจากคุณลงทะเบียนไปแล้ว หรืออาจารย์ผู้สอนยังไม่เปิด Section เพิ่ม");					
			}else{
				if(typeof(callback)=='function'){
					callback();
				}
			}
			$("#courseSectionId_chzn").unblock();
		}
	});
};

register.loadSectionIdBox = function(courseId,method,sectionId,callback){
	$("#courseSectionId_chzn").block(application.blockOption);
	var params = {
			method:method,
			courseId:courseId
	};
	if(sectionId){
		params.sectionId = sectionId;
	}
	$.ajax({
		url: application.contextPath+ "/member/registerSectionComboBox.html",
		type: "POST",
		dataType: 'json',
		data:params,
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
			$("#courseSectionId").empty().html(newData).trigger("liszt:updated");
			if($("#courseSectionId").find("option").length==0){
				applicationScript.errorAlertWithStringTH("คุณไม่สามารถลงทะเบียนได้ เนื่องจากคุณลงทะเบียนไปแล้ว หรืออาจารย์ผู้สอนยังไม่เปิด Section เพิ่ม");					
			}else{
				if(typeof(callback)=='function'){
					callback();
				}
			}
			$("#courseSectionId_chzn").unblock();
		}
	});
};

$(document).ready(function(){
	register.getGrid();
	$("#courseSectionId").chosen();
	$("#registerButton").click(function(){
		$(".button-holder").block(application.blockOption);
		register.loadCourseSectionIdBox(function(){
			$("#changeSectionModalButton").hide();
			$("#registerModalButton").show();
			$("#normal-button-holder").hide();
			$("#register-button-holder").show();
		});
		$(".button-holder").unblock();
	});
	$("#cancelButton").click(function(){
		$(".button-holder").block(application.blockOption);
		$("#normal-button-holder").show();
		$("#register-button-holder").hide();
		$(".btn-change-section").attr('disabled',false).button('reset');
		$(".button-holder").unblock();
	});
	$("#registerModalButton").click(function(){
		var courseText = $("#courseSectionId option:checked").attr('courseCode'),
			sectionChoose = $("#courseSectionId option:checked"),
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
				courseId: $("#courseSectionId option:checked").attr("courseId"),
				sectionId: $("#courseSectionId").val()
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

	$("#changeSectionModalButton").click(function(){ $("#confirmChangeSectionModal").modal('show'); });
	$("#confirmChangeSectionButton").click(function(){
		var thisButton = $(this).button('loading');
		$('body').block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/member/register.html',
			type: 'POST',
			data: {
				method: 'changeSection',
				registerId : register.currentRegisterId,
				courseId : register.currentCourseId,
				sectionId : register.currentSectionId,
				toSectionId : $("#courseSectionId").val()
			},
			success: function(data,status){
				applicationScript.successAlertWithStringHeader("ย้าย Section สำเร็จ กรุณารออาจารย์ผู้สอนอนุมัติ","Success");
				$("#confirmChangeSectionModal").modal('hide');
				thisButton.button('reset');
				register.getGrid();
				$("#normal-button-holder").show();
				$("#register-button-holder").hide();
				$("body").unblock();
			},error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#confirmChangeSectionModal").modal('hide');
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

changeSection = function(registerId,courseId,sectionId){
	register.loadSectionIdBox(courseId,"changeSection",sectionId,function(){
		$(".btn-change-section").attr('disabled',true);
		$("#change-section-button-"+registerId).button('loading');
		$("#normal-button-holder").hide();
		$("#register-button-holder").show();
		$("#changeSectionModalButton").show();
		$("#registerModalButton").hide();
	});
	register.currentRegisterId = registerId;
	register.currentSectionId = sectionId;
	register.currentCourseId = courseId;
};



