application.page='signUp';
application.studentIdRule = /[5,6][0-9]{10}/;
application.facultyRule = /[5,6][0-9]07024[0-9]{5}/;

$(document).ready(function(){
	$.validator.addMethod("isStudentId",function(value) {
		return application.studentIdRule.test(value);
	},"กรุณาใส่รหัสนักศึกษาให้ถูกต้อง");
	
	$.validator.addMethod("isInFaculty",function(value) {
		return application.facultyRule.test(value);
	},"คุณไม่ได้อยู่ในคณะวิทยาศาสตร์และเทคโนโลยี");
	
	$("#forgotPasswordForm").validate({
		rules: {
			studentId:{
				required:true,
				minlength:12,
				maxlength:12,
				number:true,
				isStudentId:true,
				isInFaculty:true
			},
			activeCode:{
				minlength:6,
				maxlength:6,
				required:true
			},
			email:{
				required:true,
				email: true
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
			},
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
			var studentId = $("#studentId").val();
			$(form).block(application.blockOption).ajaxSubmit({
				type:'post',
				url: application.contextPath + '/main/forgotPasswordCode.html',
				clearForm: true,
				success: function(){
					applicationScript.successAlertWithStringHeader(" คุณสามารถเข้าสู่ระบบได้จากรหัสผ่านใหม่","เปลี่ยนรหัสผ่านสำเร็จ");
					setTimeout(function(){
						window.location = application.contextPath + '/main/login.html?studentId='+studentId ;
					},3000);
				},
				error: function(a){
					applicationScript.errorAlertWithString(a.responseText);
					$("#forgotPasswordForm").unblock();
				}
			});
		}
	});
});