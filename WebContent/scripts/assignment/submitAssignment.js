application.page = "assignment";

submitAssignment = {};
submitAssignment.getFileCount = function(){
	var fileCount = 0;
	$(".upload-file").each(function(){
		if($(this).val()){
			fileCount++;
		}
	});
	return fileCount;
};
submitAssignment.getEmptyFileCount = function(){
	var fileCount = 0;
	$(".upload-file").each(function(){
		if(!$(this).val()){
			fileCount++;
		}
	});
	return fileCount;
};


$(document).ready(function(){
	$("#submitAssignmentButton").click(function(e){
		e.preventDefault();
		var fileCount = submitAssignment.getFileCount(),
			emptyCount = submitAssignment.getEmptyFileCount();
		if(fileCount == 0){
			applicationScript.errorAlertWithStringTH("กรุณาเลือกไฟล์อย่างน้อย 1 ไฟล์");
		}else{
			if(emptyCount > 0){
				$("#errorItem").text('คำเตือน คุณอัพโหลดไฟล์ '+fileCount+'/'+(fileCount+emptyCount)+' ไฟล์');
			}else{
				$("#errorItem").text('');
			}
			$("#confirmSubmit").modal('show');
		}
	});
	$("#submitButton").click(function(){
		$("#submitAssignmentForm").submit();
	});
	$(".upload-file").fileValidator({
		onInvalid:    function(validationType, file){
			if(validationType == 'maxSize'){
				applicationScript.errorAlertWithStringTH("รองรับไฟล์ขนาดน้อยกว่า 20MB เท่านั้น");	
			}
			if(validationType == 'type'){
				applicationScript.errorAlertWithStringTH("รองรับไฟล์ชนิด Image, Text, Word, Excel, PowerPoint, PDF, Java, Zip, RAR, 7z");
			}
			$(this).val(null);
		},
		maxSize:      '20m',
		type:         /(png|jpe?g|gif|text|word|excel|powerpoint|pdf|java|zip|rar|7z)/
	});
});