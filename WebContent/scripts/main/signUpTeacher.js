application.page='addTeacher';

$(document).ready(function(){
	
	$("#signUpForm").validate({
		rules: {
			username:{
				required:true,
				rangelength:[6,20]
			},
			password:{
				minlength:6,
				maxlength:20,
				required:true
			},
			rePassword:{
				minlength:6,
				maxlength:20,
				required:true,
				equalTo: "#regPassword"
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
			$("#confirmSignUpModal").modal('show');
		}
	});
	$("#confirmSignUpButton").click(function(){
		$("#signUpForm").block(application.blockOption);
		var thisButton = $(this).button('loading');
		$("#signUpForm").ajaxSubmit({
			type:'post',
			url: application.contextPath + '/main/signUpTeacher.html',
			clearForm: true,
			success: function(){
				$('#confirmSignUpModal').modal('hide');
				thisButton.button('reset');
				applicationScript.successAlertWithStringHeader("สามารถเข้าสู่ระบบได้จากผู้ใช้งาน","ลงทะเบียนสำเร็จ");
				$(".control-group").removeClass('error').removeClass('success');
				$("#signUpForm").unblock().validate().resetForm();
			},
			error: function(a){
				applicationScript.errorAlertWithString(a.responseText);
				$('#confirmSignUpModal').modal('hide');
				thisButton.button('reset');
				$("#signUpForm").unblock();
			}
		});
	});
});