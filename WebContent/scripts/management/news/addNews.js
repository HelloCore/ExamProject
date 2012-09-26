application.page='genericManagement';

addNews = {};

$(document).ready(function(){
	$("#courseId").chosen();
	$(".ckeditorarea").ckeditor();
	$("#courseId_chzn").block(application.blockOption);
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html?optionAll=1",function(){
		$(this).trigger("liszt:updated");
		$("#courseId_chzn").unblock();
	});
	$("#addButton").click(function(){
		$('.control-group').removeClass('success').removeClass('error');
		$('.generate-label').remove();
		var haveError = false,
			courseIdLength = $("#courseId").val().length,
			newsHeaderLength = $("#newsHeader").val().length,
			newsContentLength = $("#newsContent").val().length;
		if(courseIdLength==0){
			$("#courseId").closest('.control-group').addClass('error');
			$('<label for="courseId" class="generate-label error" id="courseIdError">กรุณาเลือกวิชา</label>').insertAfter('#courseId_chzn');
			haveError=true;
		}
		if(newsHeaderLength < 5 || newsHeaderLength > 200){
			$("#newsHeader").closest('.control-group').addClass('error');
			$('<label for="newsHeader" class="generate-label error" id="newsHeaderError">หัวข้อข่าวต้องมีความยาว 5 ถึง 200 ตัวอักษร</label>').insertAfter('#newsHeader');
			haveError=true;
		}
		
		if(newsContentLength == 0){
			$("#newsContent").closest('.control-group').addClass('error');
			$('<label for="newsContent" class="generate-label error" id="newsContentError">กรุณากรอกเนื้อหาข่าว</label>').insertAfter('#cke_newsContent');
			haveError=true;
		}
		if(!haveError){
			$("#confirmAddNewsModal").modal('show');
		}
	});
	$("#confirmAddButton").click(function(){
		var thisButton = $(this).button('loading');
		$("body").block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/management/news/add.html',
			type: 'POST',
			data: {
				method: 'addNews',
				courseId: $("#courseId").val(),
				newsHeader: $("#newsHeader").val(),
				newsContent: $("#newsContent").val()
			},
			success: function(){
				$("#newsHeader").val('');
				$("#newsContent").val('');
				applicationScript.saveComplete();
				thisButton.button('reset');
				$("#confirmAddNewsModal").modal('hide');
				$("body").unblock();
			},
			error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#confirmAddNewsModal").modal('hide');
				thisButton.button('reset');
				$("body").unblock();
			}
		});
	});
});