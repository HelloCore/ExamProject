
viewExam.section = {};
viewExam.section.sectionData = [];
viewExam.section.examSectionData = [];
viewExam.section.backupExamSectionData = [];
viewExam.section.removedData = [];
viewExam.section.oldEditTable = null;

viewExam.section.getSavedSectionData = function(){
	var examSectionTemp = [],arrayPos;
	for (key in viewExam.section.examSectionData){
		if(!viewExam.section.examSectionData[key].examSectionId){
			arrayPos = examSectionTemp.length;
			
			examSectionTemp[arrayPos] = {
					examId : application.exam.examId,
					sectionId : viewExam.section.examSectionData[key].sectionId
			};
		}
	}
	return JSON.stringify(examSectionTemp);
};

viewExam.section.getDeletedSectionData = function(){
	var examSectionTemp = [],arrayPos;
	for (key in viewExam.section.removedData){
		if(viewExam.section.removedData[key].examSectionId){
			arrayPos = examSectionTemp.length;
			
			examSectionTemp[arrayPos] = {
					examSectionId : viewExam.section.removedData[key].examSectionId,
					examId : application.exam.examId,
					sectionId : viewExam.section.removedData[key].sectionId
			};
		}
	}
	return JSON.stringify(examSectionTemp);
};

viewExam.section.sendData = function(){
	$("#confirmSectionButton").button('loading');
	$(".editable-section").block(application.blockOption);
	var  saveSectionDataStr = viewExam.section.getSavedSectionData(),
		deletedSectionDataStr = viewExam.section.getDeletedSectionData();
	
	$.ajax({
		url: application.contextPath+'/management/exam/view.html'
		,data: {
			method: 'editExamSection'
			,examId: application.exam.examId
			,saveSectionDataStr: saveSectionDataStr
			,deletedSectionDataStr: deletedSectionDataStr
		}
		,type: 'POST'
		,success: function(data){
			applicationScript.saveComplete();
			for(successKey in data){
				sectionId = data[successKey].sectionId;
				
				viewExam.section.examSectionData[sectionId].examSectionId
					= data[successKey].examSectionId;
				
				viewExam.section.examSectionData[sectionId].sectionId
					= data[successKey].sectionId;
				
				viewExam.section.examSectionData[sectionId].sectionName
					= viewExam.section.sectionData[sectionId].sectionName;
				
				viewExam.section.examSectionData[sectionId].sectionSemester
					= viewExam.section.sectionData[sectionId].sectionSemester;

				viewExam.section.examSectionData[sectionId].sectionYear
					= viewExam.section.sectionData[sectionId].sectionYear;
			}
			viewExam.section.createSectionTable();
			$(".editable-section").unblock();
			$("#confirmSectionButton").button('reset');
			$("#confirmSectionModal").modal('hide');
		}
		,error: function(){
			applicationScript.errorAlert();

			$(".editable-section").unblock();
			$("#confirmSectionButton").button('reset');
			$("#confirmSectionModal").modal('hide');
		}
	});
};

viewExam.section.checkDirty = function(){

	var dirty = false;
	for( sectionIdDirty in viewExam.section.examSectionData){
		if ( !viewExam.section.backupExamSectionData[sectionIdDirty]){
			dirty = true;
			break;
		}
	}
	
	if(!dirty){
		for( deletedSectionId in viewExam.section.removedData ){
			if ( viewExam.section.removedData[deletedSectionId].examSectionId){
				dirty = true;
				break;
			}
		}
	}
	return dirty;
};

viewExam.section.checkSaveChange= function(){
	if( viewExam.section.checkDirty() ){
		$("#confirmSectionModal").modal('show');
	}else{
		applicationScript.successAlertWithStringHeader('No data change.','Save Complete');
		viewExam.section.cancelEdit();
	}
};


viewExam.section.iniSectionData = function(){
	viewExam.section.examSectionData = [];
	var sectionId;
	for( initKey in application.sectionData){
		sectionId = application.sectionData[initKey].sectionId;
		
		viewExam.section.sectionData[sectionId]=
			$.extend(true,{},application.sectionData[initKey]);		
	}
	
	for( initKey2 in application.examSectionData){
		sectionId = application.examSectionData[initKey2].sectionId;
		viewExam.section.examSectionData[sectionId] = 
			$.extend(true,{},application.examSectionData[initKey2]);
	}
	
	delete application.sectionData;
	delete application.examSectionData;
};

viewExam.section.runNumber = function(){
	var number = 1;
	$(".number-section").each(function(){
		$(this).text(number);
		number++;
	});
};

viewExam.section.beginEdit = function(){
	$(".editable-section").block(application.blockOption);
	
	viewExam.section.removedData = [];
	viewExam.section.backupExamSectionData = $.extend(true,{},viewExam.section.examSectionData);
	viewExam.section.oldEditTable = $(".editable-section tbody").html();
	$("#normal-section-panel").hide();
	$("#edit-section-panel").show();
	$(".editable-section thead tr").append('<th>Action</th>');
	$(".editable-section tbody").empty();
	var nowData,strHtml,i=1;
	for(key in viewExam.section.examSectionData){
		nowData = viewExam.section.examSectionData[key];
		strHtml = '<tr id="section-'+nowData.sectionId+'">'
					+'<td class="number-section" >'+i+'</td>'
					+'<td>'+nowData.sectionName+'</td>'
					+'<td>'+nowData.sectionSemester+'</td>'
					+'<td>'+nowData.sectionYear+'</td>'
					+'<td><button class="btn btn-danger" onClick="removeSection('+nowData.sectionId+')"><i class="icon-trash icon-white" ></i> Delete</button></td>'
				+'</tr>';
		$(".editable-section tbody").append(strHtml);
		i++;
	}
	$(".editable-section").unblock();
};
viewExam.section.cancelEdit = function(){
	viewExam.section.examSectionData = $.extend(true,{},viewExam.section.backupExamSectionData);
	$(".editable-section").block(application.blockOption);
	$(".editable-section thead tr th:last").remove();
	$(".editable-section tbody").html(viewExam.section.oldEditTable);
	$("#normal-section-panel").show();
	$("#edit-section-panel").hide();
	$(".editable-section").unblock();
	
};

viewExam.section.createSectionTable = function(){
	$(".editable-section").block(application.blockOption);
	$(".editable-section thead tr th:eq(4)").remove();
	$(".editable-section tbody").empty();
	$("#normal-section-panel").show();
	$("#edit-section-panel").hide();
	var strHtml,nowData,i=1;
	for(createKey in viewExam.section.examSectionData){
		nowData = viewExam.section.examSectionData[createKey];
		strHtml = '<tr>'
				+'<td class="number-section" >'+i+'</td>'
				+'<td>'+nowData.sectionName+'</td>'
				+'<td>'+nowData.sectionSemester+'</td>'
				+'<td>'+nowData.sectionYear+'</td>'
			+'</tr>';
		i++;
		$(".editable-section tbody").append(strHtml);
	}
	$(".editable-section").unblock();
};

viewExam.section.addSection = function(sectionId){
	var result=null,strHtml;
	if(viewExam.section.removedData[sectionId]){
		result = $.extend(true,{},viewExam.section.removedData[sectionId]);
		delete viewExam.section.removedData[sectionId];
	}

	if(result){
		viewExam.section.examSectionData[result.sectionId] = result;
		strHtml = '<tr id="section-'+result.sectionId+'">'
			+'<td class="number-section"></td>'
			+'<td>'+result.sectionName+'</td>'
			+'<td>'+result.sectionSemester+'</td>'
			+'<td>'+result.sectionYear+'</td>'
			+'<td><button class="btn btn-danger" onClick="removeSection('+result.sectionId+')"><i class="icon-trash icon-white" ></i> Delete</button></td>'
		+'</tr>';
		$(".editable-section tbody").append(strHtml);
	}else{
		result = viewExam.section.sectionData[sectionId];
		if(result){
			viewExam.section.examSectionData[sectionId] = {
				sectionId: result.sectionId,
				sectionName: result.sectionName,
				sectionYear: result.sectionYear,
				sectionSemester: result.sectionSemester
			};
			strHtml = '<tr id="section-'+result.sectionId+'">'
				+'<td class="number-section"></td>'
				+'<td>'+result.sectionName+'</td>'
				+'<td>'+result.sectionSemester+'</td>'
				+'<td>'+result.sectionYear+'</td>'
				+'<td><button class="btn btn-danger" onClick="removeSection('+result.sectionId+')"><i class="icon-trash icon-white" ></i> Delete</button></td>'
			+'</tr>';
			$(".editable-section tbody").append(strHtml);
		}
	}

	$("#sectionModal").modal("hide");
	viewExam.section.runNumber();
};

viewExam.section.findNotInSection = function(){
	var value,nowSemester=null,nowYear=null,isFirst=true,strHtml='';
	for(findKey in viewExam.section.sectionData){
		if(!viewExam.section.examSectionData[findKey]){
			value = viewExam.section.sectionData[findKey];
			
			if( nowSemester != value.sectionSemester || nowYear != value.sectionYear){
				nowSemester = value.sectionSemester;
				nowYear = value.sectionYear;
				if(!isFirst){
					strHtml+= '</optgroup>';
				}else{
					isFirst = false;
				}
				strHtml += '<optgroup label="เทอม '+nowSemester+' ปี '+nowYear+'">';
			}
			strHtml += '<option value="'+value.sectionId+'"'
							+' sectionYear="'+value.sectionYear+'"'
							+' sectionSemester="'+value.sectionSemester+'"'
							+' sectionName="'+value.sectionName+'"'
							+'>เทอม '+value.sectionSemester+' ปี '+value.sectionYear+' ['+value.sectionName+']</option>';
		}
	}
	return strHtml;
};

viewExam.section.initSectionComboBoxModal = function(){

	var result = viewExam.section.findNotInSection();
	if(result){
		$("#sectionId").empty().append(result.toString()).trigger('liszt:updated');
		$("#sectionModal").modal("show");
	}else{
		applicationScript.errorAlertWithStringTH("ไม่พบข้อมูล Section");
	}
};

$(document).ready(function(){
	viewExam.section.iniSectionData();
	
	viewExam.section.createSectionTable();
	$("#sectionId").chosen();
	$("#addSectionButton").click(function(){ viewExam.section.initSectionComboBoxModal(); });
	$("#addSectionConfirmButton").click(function(){ viewExam.section.addSection($("#sectionId").val()); });
	$("#editSectionButton").click(function(){ viewExam.section.beginEdit(); });
	$("#cancelEditSectionButton").click(function(){ viewExam.section.cancelEdit(); });
	
	$("#saveEditSectionButton").click(function(){ viewExam.section.checkSaveChange(); });
	$("#confirmSectionButton").click(function(){ viewExam.section.sendData(); });

});

removeSection = function(sectionId){
	$(".editable-section").block(application.blockOption);
	$("#section-"+sectionId).remove();
	viewExam.section.removedData[sectionId] = $.extend(true,{},viewExam.section.examSectionData[sectionId]);
	delete viewExam.section.examSectionData[sectionId];
	$(".editable-section").unblock();
	viewExam.section.runNumber();
};
