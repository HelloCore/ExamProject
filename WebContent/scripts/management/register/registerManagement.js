application.page='genericManagement';


registerManagement = {};

registerManagement.getRegisterIdArray = function(){
	var str = "[",isFirst=true;
	$('input[name=registerId\\[\\]]:checked').each(function(){
		if(isFirst){
			isFirst = false;
		}else{
			str+= ",";
		}
		str += $(this).val();
	});
	return str+"]";
};

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
			$("#registerTable tbody tr").remove();
			$("#recordTemplate").tmpl(data).appendTo("#registerTable tbody");
			registerManagement.getRegisterSectionData();
			$("#registerTable").trigger("update").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
};

registerManagement.initCourseComboBox = function(callback){
	$("#s2id_courseId").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2();
		$("#s2id_courseId").unblock();
		if(typeof(callback)=='function'){
			callback();
		}
	});
};

registerManagement.getRegisterSectionData = function(callback){
	$("#sectionData").block(application.blockOption);
	$.ajax({
		url: application.contextPath+ "/management/register.html",
		type: "POST",
		dataType: 'json',
		data:{
			method: "getRegisterSectionData",
			sectionId : $('#sectionId').val()
		},
		success : function(data){
			$("#sectionData").empty();
			$("#infoTemplate").tmpl(data).appendTo("#sectionData");
			$("#sectionData").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
};

registerManagement.initSectionComboBox = function(callback){
	$("#s2id_sectionId").block(application.blockOption);
	$.ajax({
		url: application.contextPath+ "/member/registerSectionComboBox.html",
		type: "POST",
		dataType: 'json',
		data:{
			method: "register",
			courseId : $('#courseId').val()
		},
		success: function(data){
			$("#sectionId optgroup").remove();
			$("#sectionTemplate").tmpl(data).appendTo("#sectionId");
			$("#sectionId").select2();
			$("#s2id_sectionId").unblock();
			if(typeof(callback)=='function'){
				callback();
			}
		},error:function(data){
			applicationScript.resolveError(data.responseText);
		}
	});
};

$(document).ready(function(){
		
	$("#courseId").select2().change(function(){
		registerManagement.initSectionComboBox(function(){
			registerManagement.getRegisterTable();
		});
	});
	
	$("#sectionId").select2();
	
	registerManagement.initCourseComboBox(function(){
		registerManagement.initSectionComboBox(function(){
			registerManagement.getRegisterTable();
		});
	});
	
	$("#registerTable").tablesorter({ headers: { 0: { sorter: false}}});
	$("#registerTable tbody").on('click','tr',function(e){
		$(this).toggleClass('info');
		var check = $(this).find('input:checkbox');
		if(check.is(':checked')){
			if(e.target.nodeName != "INPUT"){
				check.attr('checked',false);
			}
		}else{
			if(e.target.nodeName != "INPUT"){
				check.attr('checked',true);
			}
		}
	});
	$("#filterButton").click(function(){
		registerManagement.getRegisterTable();
	});
	$("#approveButton").click(function(){ 
		if($('input[name=registerId\\[\\]]:checked').length < 1){
			applicationScript.errorAlertWithStringTH("กรุณาเลือกนักศึกษาที่ต้องการ");
		}else{
			$("#confirmApproveModal").modal('show');
		}
	});
	$("#rejectButton").click(function(){
		if($('input[name=registerId\\[\\]]:checked').length < 1){
			applicationScript.errorAlertWithStringTH("กรุณาเลือกนักศึกษาที่ต้องการ");
		}else{
			$("#confirmRejectModal").modal('show');
		}
	});
	$("#approveConfirmButton").click(function(){
		var thisButton = $(this).button('loading');
		$.ajax({
			url: application.contextPath + '/management/register.html',
			type: 'POST',
			data: {
				method:'acceptSection',
				courseId: $("#courseId").val(),
				registerIdArray : registerManagement.getRegisterIdArray()
			},
			success: function(data,status){
				thisButton.button('reset');
				applicationScript.saveCompleteTH();
				$("#confirmApproveModal").modal('hide');
				registerManagement.getRegisterTable();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.resolveError(data.responseText);
				$("#confirmApproveModal").modal('hide');
			}
		});
	});
	$("#rejectConfirmButton").click(function(){
		var thisButton = $(this).button('loading');
		$.ajax({
			url: application.contextPath + '/management/register.html',
			type: 'POST',
			data: {
				method: 'rejectSection',
				courseId: $("#courseId").val(),
				registerIdArray : registerManagement.getRegisterIdArray()
			},
			success: function(data,status){
				thisButton.button('reset');
				applicationScript.saveCompleteTH();
				$("#confirmRejectModal").modal('hide');
				registerManagement.getRegisterTable();
			},
			error: function(data){
				thisButton.button('reset');
				applicationScript.resolveError(data.responseText);
				$("#confirmRejectModal").modal('hide');
			}
		});
	});
});