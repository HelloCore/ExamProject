$(document).ready(function(){
	$("#changePasswordForm").validate({
		rules: {
			oldPassword:{
				minlength:6,
				maxlength:20,
				required:true
			},
			newPassword:{
				minlength:6,
				maxlength:20,
				required:true
			},
			retypePassword:{
				minlength:6,
				maxlength:20,
				required:true,
				equalTo: "#newPassword"
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
			$("#changePasswordConfirmModal").modal('show');
		}
	});
	
	$("#changePasswordConfirmButton").click(function(){
		$("#changePasswordForm").block(application.blockOption);
		var thisButton = $(this).button('loading');
		
		$("#changePasswordForm").ajaxSubmit({
			type:'post',
			url: application.contextPath + '/member/changePassword.html',
			clearForm: true,
			success: function(){
				$('#changePasswordConfirmModal').modal('hide');
				thisButton.button('reset');
				applicationScript.successAlertWithStringHeader("กรุณาล็อกอินใหม่","เปลี่ยนรหัสผ่านสำเร็จ");
				setTimeout(function(){
					window.location = application.contextPath + '/main/logout.do' ;
				},3000);
			},
			error: function(data){
				applicationScript.errorAlertWithString(data.responseText);
				$('#changePasswordConfirmModal').modal('hide');
				thisButton.button('reset');
				$("#changePasswordForm").unblock();
			}
		});
	});
});