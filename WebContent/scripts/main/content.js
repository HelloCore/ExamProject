var isValidUpload = false;

validateNewFolder = function(){
	var isValid = true;
	if($("#folderName").val().length < 4){
		applicationScript.errorAlertWithStringTH("กรุณากรอกชื่อ Folder จำนวน 4 ตัวอักษรขึ้นไป");
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
			$("#uploadFileForm").submit();
		}
	});
	
	$("#newFolderConfirm").click(function(){
		if(validateNewFolder()){
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