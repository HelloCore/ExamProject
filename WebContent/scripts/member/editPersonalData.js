$(document).ready(function(){
	$("#editPersonalDataForm").validate({
		rules: {
			password:{
				minlength:6,
				maxlength:20,
				required:true
			},
			email:{
				required:true,
				email:true
			},
			firstName:{
				required:true,
				maxlength:50
			},
			lastName:{
				required:true,
				maxlength:50
			},
			prefixName:{
				required:true
			}
		},
	    highlight: function(label) {
	    	$(label).closest('.control-group').removeClass('success').addClass('error');
	    },
	    success: function(label) {
	    	label
	    		.text('OK!').addClass('valid')
	    		.closest('.control-group').removeClass('error').addClass('success');
	    },
		submitHandler: function(form) {
			$("#confirmEditPersonalDataModal").modal('show');
		}
	});
	$("#confirmEditPersonalDataButton").click(function(){
		$("#editPersonalDataForm").block(application.blockOption);
		var thisButton = $(this).button('loading');
		$("#editPersonalDataForm").ajaxSubmit({
			type:'post',
			url: application.contextPath + '/member/editPersonalData.html',
			clearForm: true,
			success: function(){
				$('#confirmEditPersonalDataModal').modal('hide');
				thisButton.button('reset');
				applicationScript.successAlertWithStringHeader("แก้ไขข้อมูลสำเร็จ กรุณาเข้าสู่ระบบอีกครั้ง","แก้ไขข้อมูลสำเร็จ");
				setTimeout(function(){
					window.location = application.contextPath + '/main/logout.do';
				},3000);
			},
			error: function(a){
				applicationScript.errorAlertWithString(a.responseText);
				$('#confirmEditPersonalDataModal').modal('hide');
				thisButton.button('reset');
				$("#editPersonalDataForm").unblock();
			}
		});
	});
});