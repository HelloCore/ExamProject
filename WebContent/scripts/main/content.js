var isValidUpload = false;
application.folderNameRule = /^[a-zA-z0-9_]+$/;

validateNewFolder = function(){
	var isValid = true;
	if($("#folderName").val().length < 4 || $("#folderName").val().length > 25){
		applicationScript.errorAlertWithStringTH("กรุณากรอกชื่อ Folder จำนวน 4 ถึง 25 ตัวอักษรขึ้นไป");
		isValid=false;
	}else if(!application.folderNameRule.test($("#folderName").val())){
		applicationScript.errorAlertWithStringTH("ชื่อ Folder ต้องเป็นภาษาอังกฤษหรือตัวเลขหรือเครื่องหมาย _ เท่านั้น");
		isValid=false;
	}
	return isValid;
};
validateUploadFile = function(){
	var isValid = true;
	if($("#fileName").val().length < 4){
		applicationScript.errorAlertWithStringTH("กรุณากรอกชื่อ File จำนวน 4 ตัวอักษรขึ้นไป");
		isValid=false;
	}
	if(!isValidUpload){
		applicationScript.errorAlertWithStringTH("กรุณาเลือกไฟล์ที่ต้องการอัพโหลด");
		isValid=false;
	}
	return isValid;
};

deleteFolder = function(folderId){
	$("#deleteFolderId").val(folderId);
	$("#confirmDeleteFolder").modal('show');
};

deleteFile = function(fileId){
	$("#deleteFileId").val(fileId);
	$("#confirmDeleteFile").modal('show');
};

$(document).ready(function(){
	$("#newFolderButton").click(function(){
		$("#newFolderModal").modal('show');
	});
	
	$("#uploadFileConfirm").click(function(){
		if(validateUploadFile()){
			$(this).button('loading');
			$("#uploadFileForm").submit();
		}
	});
	
	$("#newFolderConfirm").click(function(){
		if(validateNewFolder()){
			$(this).button('loading');
			$("#newFolderForm").submit();
		}
	});
	$("#fileData").fileValidator({
		onInvalid:    function(validationType, file){
			if(validationType == 'maxSize'){
				applicationScript.errorAlertWithStringTH("รองรับไฟล์ขนาดน้อยกว่า 20MB เท่านั้น");	
			}
			if(validationType == 'type'){
				applicationScript.errorAlertWithStringTH("รองรับไฟล์ชนิด Image, Text, Word, Excel, PowerPoint, PDF, Java, Zip, RAR, 7z");
			}
			$("#fileData").val(null);
			isValidUpload = false;
		},
		onValidation: function(files){
			isValidUpload=true;
		},
		maxSize:      '20m',
		type:         /(png|jpe?g|gif|text|word|excel|powerpoint|pdf|java|zip|rar|7z)/
	});
	$("#uploadButton").click(function(){
		$("#uploadModal").modal('show');
	});
	$("#deleteFolderButton").click(function(){
		$("#deleteFolderForm").submit();
	});
	$("#deleteFileButton").click(function(){
		$("#deleteFileForm").submit();
	});
});