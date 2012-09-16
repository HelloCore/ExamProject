application.page='genericManagement';
registerManagement = {};

registerManagement.getRegisterTable = function(){
	$("#registerTable").block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/management/register.html',
		type: 'POST',
		data: {
			method: 'getRegisterTable'
			,courseId : $("#courseId").val()
			,sectionId : $("#sectionId").val()
		},
		dataType: 'json',
		success: function(data,status){
			$("#registerTable tbody").empty();
			for(keyArray in data){
				strHtml = '<tr>'+
							'<td><input type="checkbox" id="register-id-'+data[keyArray].registerId+'" name="registerId[]" value="'+data[keyArray].registerId+'"/></td>'+
							'<td>'+data[keyArray].studentId+'</td>'+
							'<td>'+data[keyArray].firstName+' '+data[keyArray].lastName+'</td>'+
							'<td>'+data[keyArray].courseCode+'</td>'+
							'<td>['+data[keyArray].sectionName+'] '+data[keyArray].sectionSemester+'/'+data[keyArray].sectionYear+'</td>'+
							'<td>'+Globalize.format( new Date(data[keyArray].requestDate),"dd-MM-yyyy HH:mm:ss")+'</td>'+
						  '</tr>';
				$("#registerTable tbody").append(strHtml);
			}
			$("#registerTable").trigger("update").unblock();
		}
	});
};

registerManagement.initCourseComboBox = function(callback){
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).trigger("liszt:updated");
		$("#courseId_chzn").unblock();
		if(typeof(callback)=='function'){
			callback();
		}
	});
};

registerManagement.initSectionComboBox = function(callback){
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
			$("#sectionId").empty().append('<option value="0">ทั้งหมด</option>').append(newData).trigger("liszt:updated");
			$("#sectionId_chzn").unblock();
			if(typeof(callback)=='function'){
				callback();
			}
		}
	});
};

$(document).ready(function(){
	$("#courseId").chosen().change(function(){registerManagement.initSectionComboBox();});
	$("#sectionId").chosen();
	registerManagement.initCourseComboBox(function(){
		registerManagement.initSectionComboBox(function(){
			registerManagement.getRegisterTable();
		});
	});
	$("#registerTable").tablesorter({ headers: { 0: { sorter: false}}}); 
	$("#filterButton").click(function(){
		registerManagement.getRegisterTable();
	});
});