
viewExam.questionGroup = {};
viewExam.questionGroup.questionGroupData = [];
viewExam.questionGroup.examQuestionGroupData = [];
viewExam.questionGroup.oldEditTable = null;
viewExam.questionGroup.backupExamQuestionGroupData = [];
viewExam.questionGroup.removedData = [];

viewExam.questionGroup.runNumber = function(){
	var number = 1;
	$(".number-ordinal").each(function(){
		$(this).text(number);
		number++;
	});
};

viewExam.questionGroup.compareIsDirty = function(var1,var2){
	var isDirty = false;
	if(typeof(var1) == 'object' && typeof(var2) == 'object'){
		if(var1 != var2){
			isDirty = true;
		}
	}else if(typeof(var1) == 'number' && typeof(var2) == 'number'){
		if(var1 != var2){
			isDirty = true;
		}
	}else if(typeof(var1)=='string' || typeof(var2) == 'string'){
		if(parseInt(var1,10) != parseInt(var2,10)){
			isDirty = true;
		}
	}else{
		isDirty = true;
	}
	return isDirty;
};

viewExam.questionGroup.checkDirtyQuestionGroup = function(questionGroupId){
	var isDirty = false
		,newData = viewExam.questionGroup.examQuestionGroupData[questionGroupId]
		,oldData = viewExam.questionGroup.backupExamQuestionGroupData[questionGroupId];
	
	// เช็คว่ามีค่าทั้งคู่
	// เช็คว่ามี ordinal ทั้งคู่
	if(typeof(newData) == 'object' && typeof(oldData) == 'object' ){
		if(viewExam.questionGroup.compareIsDirty(newData.ordinal,oldData.ordinal)){
			isDirty = true;
		}else 
		if(viewExam.questionGroup.compareIsDirty(newData.questionPercent,oldData.questionPercent)){
			isDirty = true;
		}else 
		if(viewExam.questionGroup.compareIsDirty(newData.secondPerQuestion,oldData.secondPerQuestion)){
			isDirty = true;
		}else 
		if(viewExam.questionGroup.compareIsDirty(newData.examQuestionGroupId,oldData.examQuestionGroupId)){
			isDirty = true;
		}else 
		if(viewExam.questionGroup.compareIsDirty(newData.questionGroupId,oldData.questionGroupId)){
			isDirty = true;
		}
	}else{
		isDirty = true;
	}
	return isDirty;
};

viewExam.questionGroup.getExamQuestionGroupString = function(){
	var examQuestionGroupTemp = [];
	for (key in viewExam.questionGroup.examQuestionGroupData){
		if(viewExam.questionGroup.checkDirtyQuestionGroup(key)){
			arrayPos = examQuestionGroupTemp.length;
			examQuestionGroupTemp[arrayPos] = {};
			examQuestionGroupTemp[arrayPos].ordinal 
				= viewExam.questionGroup.examQuestionGroupData[key].ordinal;
			examQuestionGroupTemp[arrayPos].questionPercent
				= viewExam.questionGroup.examQuestionGroupData[key].questionPercent;
			examQuestionGroupTemp[arrayPos].secondPerQuestion 
				= 0;
			if(viewExam.questionGroup.examQuestionGroupData[key].examQuestionGroupId){
				examQuestionGroupTemp[arrayPos].examQuestionGroupId 
					= viewExam.questionGroup.examQuestionGroupData[key].examQuestionGroupId;
			}
			examQuestionGroupTemp[arrayPos].questionGroupId = key;
			examQuestionGroupTemp[arrayPos].examId = application.exam.examId;
		}
	}
	return JSON.stringify(examQuestionGroupTemp);
};

viewExam.questionGroup.sendData = function(){
	$("#confirmQuestionGroupButton").button('loading');
	$(".editable-question").block(application.blockOption);
	var examQuestionGroupDeleteDataStr,examQuestionGroupSaveDataStr,deletedExamQuestionGroupTemp = [],arrayPos,questionGroupId;
	
		examQuestionGroupSaveDataStr = viewExam.questionGroup.getExamQuestionGroupString();
		
		for (deletedKey in viewExam.questionGroup.removedData){
			if(viewExam.questionGroup.removedData[deletedKey].examQuestionGroupId){
				arrayPos = deletedExamQuestionGroupTemp.length;
				deletedExamQuestionGroupTemp[arrayPos] = {
					examQuestionGroupId : viewExam.questionGroup.removedData[deletedKey].examQuestionGroupId,
					examId : application.exam.examId
				};
			}
		}
		
		examQuestionGroupDeleteDataStr = JSON.stringify(deletedExamQuestionGroupTemp);
		
		$.ajax({
			url: application.contextPath+'/management/exam/view.html'
			,data: {
				method: 'editExamQuestionGroup'
				,examId: application.exam.examId
				,examQuestionGroupSaveStr: examQuestionGroupSaveDataStr
				,examQuestionGroupDeleteStr: examQuestionGroupDeleteDataStr
			}
			,type: 'POST'
			,success: function(data){
				applicationScript.saveComplete();
				for(successKey in data){
					questionGroupId = data[successKey].questionGroupId;
					viewExam.questionGroup.examQuestionGroupData[questionGroupId].ordinal 
						= data[successKey].ordinal;
					
					viewExam.questionGroup.examQuestionGroupData[questionGroupId].examQuestionGroupId
						= data[successKey].examQuestionGroupId;
					
					viewExam.questionGroup.examQuestionGroupData[questionGroupId].questionGroupId
						= data[successKey].questionGroupId;
					
					viewExam.questionGroup.examQuestionGroupData[questionGroupId].questionPercent
						= data[successKey].questionPercent;
					
					viewExam.questionGroup.examQuestionGroupData[questionGroupId].secondPerQuestion
						= data[successKey].secondPerQuestion;
					
				}
				viewExam.questionGroup.createQuestionGroupTable();
				$(".editable-question").unblock();
				$("#confirmQuestionGroupButton").button('reset');
				$("#confirmQuestionGroupModal").modal('hide');
			}
			,error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);

				$(".editable-question").unblock();
				$("#confirmQuestionGroupButton").button('reset');
				$("#confirmQuestionGroupModal").modal('hide');
			}
		});
};

viewExam.questionGroup.setData = function(callback){
	var questionGroupId,questionGroupIdStr,num=1;
	$('.editable-question tbody tr').each(function(){
		questionGroupIdStr = $(this).attr('id');
		questionGroupId = questionGroupIdStr.substring(15,questionGroupIdStr.legth);
		viewExam.questionGroup.examQuestionGroupData[questionGroupId].ordinal = num;
		viewExam.questionGroup.examQuestionGroupData[questionGroupId].questionPercent = $(this).find('.questionPercent').text(); 
		//viewExam.questionGroup.examQuestionGroupData[questionGroupId].secondPerQuestion = $(this).find('.secondPerQuestion').text();
		num++;
	});
	
	
	if( typeof(callback) == 'function'){
		callback();
	}
};

viewExam.questionGroup.checkDirty = function(){
	var dirty = false;
	for( questionGroupIdDirty in viewExam.questionGroup.examQuestionGroupData){
		if ( viewExam.questionGroup.checkDirtyQuestionGroup(questionGroupIdDirty)){
			dirty = true;
			break;
		}
	}
	if(!dirty){
		for( deletedQuestionGroupId in viewExam.questionGroup.removedData ){
			if ( viewExam.questionGroup.removedData[deletedQuestionGroupId].examQuestionGroupId){
				dirty = true;
				break;
			}
		}
	}
	return dirty;
};

viewExam.questionGroup.checkSaveChange = function(){
	if(viewExam.questionGroup.validateData()){
		viewExam.questionGroup.setData(function(){
			if( viewExam.questionGroup.checkDirty() ){
				$("#confirmQuestionGroupModal").modal('show');
			}else{
				applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
				viewExam.questionGroup.cancelEdit();
			}
		});
	}
};

viewExam.questionGroup.validateData = function(){
	var validatePass = true;
	if(!viewExam.questionGroup.validateSecondPerQuestion()){
		validatePass = validatePass && false;
		applicationScript.errorAlertWithStringTH("กรุณากรอกเวลาให้ครบทุกกลุ่ม");
	}

	if(!viewExam.questionGroup.validateTotalPercent()){
		validatePass = validatePass && false;
		applicationScript.errorAlertWithStringTH("เปอร์เซ็นต์รวมต้องมี 100% และห้ามมีกลุ่มใดมีคำถาม 0%");
	}
	return validatePass;
};

viewExam.questionGroup.validateSecondPerQuestion = function(){
	var haveError = false;
	$(".secondPerQuestion").each(function(){
		$(this).removeClass('error').removeClass('success');
		if( parseInt($(this).text(),10)==0 ){
			haveError = true;
			$(this).addClass('error');
		}else{
			$(this).addClass('success');
		}
	});
	return !haveError;
};
viewExam.questionGroup.validateTotalPercent = function(){
	$("#totalPercent").removeClass("success").removeClass("error");
	var totalPercent =0,nowPercent,haveError = false;
	$(".questionPercent").removeClass("error").removeClass("success").each(function(){
		nowPercent = parseInt($(this).text(),10);
		totalPercent += nowPercent;
		if(nowPercent ==0 ){
			haveError = true;
			$(this).addClass('error');
		}
	});
	
	$("#totalPercent").text(parseInt(totalPercent,10));
	if(totalPercent != 100 || haveError){
		haveError = true;
		$("#totalPercent").addClass("error");
	}else{
		$("#totalPercent").addClass("success");
	}
	return !haveError;
};
viewExam.questionGroup.createQuestionGroupTable = function(){
	$(".editable-question").block(application.blockOption);
	$(".editable-question thead tr th:eq(3)").remove();
	$(".editable-question tbody").empty().removeClass('sortable');
	$("#normal-question-group-panel").show();
	$("#edit-question-group-panel").hide();
	var rawHtml = '',strHtml = [],nowData,i=1;
	for(createKey in viewExam.questionGroup.examQuestionGroupData){
		nowData = viewExam.questionGroup.examQuestionGroupData[createKey];
		strHtml[nowData.ordinal] = '<tr>'
				+'<td>'+nowData.ordinal+'</td>'
				+'<td>'+nowData.questionGroupName+'</td>'
				+'<td class="questionPercent">'+nowData.questionPercent+'</td>'
			+'</tr>';
	}
	
	for(i=1;i<=strHtml.length;i++){
		rawHtml +=strHtml[i];
	}
	$(".editable-question tbody").append(rawHtml);
	$(".editable-question").unblock();
};

viewExam.questionGroup.beginEdit = function(){
	viewExam.questionGroup.removedData = [];
	$("#totalPercent").text("100").removeClass("error").removeClass("success");
	viewExam.questionGroup.backupExamQuestionGroupData = $.extend(true,{},viewExam.questionGroup.examQuestionGroupData);
	$(".editable-question").block(application.blockOption);
	viewExam.questionGroup.oldEditTable = $(".editable-question tbody").html();
	$("#normal-question-group-panel").hide();
	$("#edit-question-group-panel").show();
	$(".editable-question thead tr").append('<th>Action</th>');
	$(".editable-question tbody").empty().addClass('sortable');
	var nowData,rawHtml = '',strHtml = [],i=1;
	for(key in viewExam.questionGroup.examQuestionGroupData){
		nowData = viewExam.questionGroup.examQuestionGroupData[key];
		strHtml[nowData.ordinal] = '<tr id="question-group-'+nowData.questionGroupId+'">'
					+'<td class="number-ordinal">'+nowData.ordinal+'</td>'
					+'<td>'+nowData.questionGroupName+'</td>'
					+'<td class="questionPercent">'+nowData.questionPercent+'</td>'
					+'<td><button class="btn btn-danger" onClick="removeQuestionGroup('+nowData.questionGroupId+')"><i class="icon-trash icon-white" ></i> Delete</button></td>'
				+'</tr>';
	}
	for(i=1;i<=strHtml.length;i++){
		rawHtml +=strHtml[i];
	}
	$(".editable-question tbody").append(rawHtml);
	viewExam.questionGroup.setEditable();
	$('.sortable').sortable({
		placeholder: "placeholder"
		,update: viewExam.questionGroup.runNumber
	}).disableSelection();
	viewExam.questionGroup.validateTotalPercent();
	$(".editable-question").unblock();
};

viewExam.questionGroup.cancelEdit = function(){
	viewExam.questionGroup.examQuestionGroupData = $.extend(true,{},viewExam.questionGroup.backupExamQuestionGroupData);
	$(".editable-question").block(application.blockOption);
	$('.sortable').sortable( "destroy" );
	$(".editable-question thead tr th:last").remove();
	$(".editable-question tbody").removeClass('sortable').html(viewExam.questionGroup.oldEditTable);
	$("#normal-question-group-panel").show();
	$("#edit-question-group-panel").hide();
	$(".editable-question").unblock();
};

viewExam.questionGroup.iniQuestionGroupData = function(){
	viewExam.questionGroup.examQuestionGroupData = [];
	var questionGroupId;
	for( initKey in application.questionGroupData){
		questionGroupId = application.questionGroupData[initKey].questionGroupId;
		viewExam.questionGroup.questionGroupData[questionGroupId]=
			$.extend(true,{},application.questionGroupData[initKey]);		
	}
	
	for( initKey2 in application.examQuestionGroupData){
		questionGroupId = application.examQuestionGroupData[initKey2].questionGroupId;
		viewExam.questionGroup.examQuestionGroupData[questionGroupId] = 
			$.extend(true,{},application.examQuestionGroupData[initKey2]);
	}
	
	delete application.questionGroupData;
	delete application.examQuestionGroupData;
};
viewExam.questionGroup.addQuestionGroup = function(questionGroupId){
	var result=null,strHtml;
	if(viewExam.questionGroup.removedData[questionGroupId]){
		result = $.extend(true,{},viewExam.questionGroup.removedData[questionGroupId]);
		delete viewExam.questionGroup.removedData[questionGroupId];
	}
	if(result){
		viewExam.questionGroup.examQuestionGroupData[result.questionGroupId] = result;
		strHtml = '<tr id="question-group-'+result.questionGroupId+'">'
			+'<td class="number-ordinal">'+result.ordinal+'</td>'
			+'<td>'+result.questionGroupName+'</td>'
			+'<td class="questionPercent">'+result.questionPercent+'</td>'
			+'<td><button class="btn btn-danger" onClick="removeQuestionGroup('+result.questionGroupId+')"><i class="icon-trash icon-white" ></i> Delete</button></td>'
		+'</tr>';
		$(".editable-question tbody").append(strHtml);
	}else{
		result = viewExam.questionGroup.questionGroupData[questionGroupId];
		if(result){
			viewExam.questionGroup.examQuestionGroupData[result.questionGroupId] = {
				questionGroupId: result.questionGroupId,
				questionGroupName: result.questionGroupName,
				questionPercent: 0
			};
			strHtml = '<tr id="question-group-'+result.questionGroupId+'">'
				+'<td class="number-ordinal">0</td>'
				+'<td>'+result.questionGroupName+'</td>'
				+'<td class="questionPercent">0</td>'
				+'<td><button class="btn btn-danger" onClick="removeQuestionGroup('+result.questionGroupId+')"><i class="icon-trash icon-white" ></i> Delete</button></td>'
			+'</tr>';
			$(".editable-question tbody").append(strHtml);
		}
	}
	$("#questionGroupModal").modal("hide");

	viewExam.questionGroup.runNumber();
	viewExam.questionGroup.setEditable(questionGroupId);
	viewExam.questionGroup.validateTotalPercent();
};

viewExam.questionGroup.findNotInQuestionGroup = function(){
	var strHtml= '',newData;
	for(findKey in viewExam.questionGroup.questionGroupData){
		if(!viewExam.questionGroup.examQuestionGroupData[findKey]){
			newData = viewExam.questionGroup.questionGroupData[findKey];
			strHtml += '<option value="'+newData.questionGroupId+'">'+newData.questionGroupName+'</option>';
		}
	}
	return strHtml;
};

viewExam.questionGroup.initQuestionGroupComboBoxModal = function(){
	var result = viewExam.questionGroup.findNotInQuestionGroup();
	if(result){
		$("#questionGroupId").empty().append(result.toString()).trigger('liszt:updated');
		$("#questionGroupModal").modal("show");
	}else{
		applicationScript.errorAlertWithStringTH("ไม่พบข้อมูลบทเรียน");
	}
};
viewExam.questionGroup.setEditable = function(questionGroupId){
	var percentId = '.questionPercent';
	if(questionGroupId){
		percentId = '#question-group-'+questionGroupId+' .questionPercent';
	}
	$(percentId).editable({
		onSubmit:updatePercent,
		editClass: 'numeric',
		onEdit:function(){
			$('.numeric').numeric({ decimal: false, negative: false });
		}
	});
};
$(document).ready(function(){
	viewExam.questionGroup.iniQuestionGroupData();
	viewExam.questionGroup.createQuestionGroupTable();
	$("#editQuestionGroupButton").click(function(){ viewExam.questionGroup.beginEdit(); });
	$("#cancelEditQuestionGroupButton").click(function(){ viewExam.questionGroup.cancelEdit(); });
	$("#questionGroupId").chosen();
	$("#addQuestionGroupButton").click(function(){ viewExam.questionGroup.initQuestionGroupComboBoxModal(); });
	$("#addQuestionGroupConfirmButton").click(function(){ viewExam.questionGroup.addQuestionGroup($("#questionGroupId").val()); });
	$("#saveEditQuestionGroupButton").click(function(){ viewExam.questionGroup.checkSaveChange(); });
	$("#confirmQuestionGroupButton").click(function(){ viewExam.questionGroup.sendData(); });
});

removeQuestionGroup = function(questionGroupId){
	$(".editable-question").block(application.blockOption);
	$("#question-group-"+questionGroupId).remove();
	viewExam.questionGroup.removedData[questionGroupId] = $.extend(true,{},viewExam.questionGroup.examQuestionGroupData[questionGroupId]);
	delete viewExam.questionGroup.examQuestionGroupData[questionGroupId];
	viewExam.questionGroup.runNumber();
	$(".editable-question").unblock();
	viewExam.questionGroup.validateTotalPercent();
};

updatePercent = function(content){
	$(this).removeClass("error").removeClass("success");
	if(content.current.length==0 || parseInt(content.current,10)==0){
		$(this).text(content.previous);
		if(parseInt(content.previous,10)==0){
			$(this).addClass("error");
		}
	}else{
		$("#totalPercent").removeClass("error");
		var totalPercent = $("#totalPercent").text();
		totalPercent -= parseInt(content.previous,10);
		totalPercent += parseInt(content.current,10);
	
		$("#totalPercent").text(parseInt(totalPercent,10));
		if(totalPercent != 100 ){
			$("#totalPercent").addClass("error");
		}else{
			$("#totalPercent").addClass("success");
		}
	}
};
