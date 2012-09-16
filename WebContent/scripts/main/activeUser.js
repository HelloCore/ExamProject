application.page='signUp';
application.studentIdRule = /[5,6][0-9]{10}/;
application.facultyRule = /[5,6][0-9]07024[0-9]{5}/;

$(document).ready(function(){
	$("#miniLoginTarget").val("/main/home.html");
	$.validator.addMethod("isStudentId",function(value) {
		return application.studentIdRule.test(value);
	},"กรุณาใส่รหัสนักศึกษาให้ถูกต้อง");
	
	$.validator.addMethod("isInFaculty",function(value) {
		return application.facultyRule.test(value);
	},"คุณไม่ได้อยู่ในคณะวิทยาศาสตร์และเทคโนโลยี");
	
	$("#activeUserForm").validate({
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
			$(form).block(application.blockOption).ajaxSubmit({
				type:'post',
				url: application.contextPath + '/main/activeUser.html',
				clearForm: true,
				success: function(){
					applicationScript.successAlertWithStringHeader("กรุณาเข้าสู่ระบบ","ยืนยันการสมัครสมาชิกเสร็จสมบูรณ์");
					setTimeout(function(){
						window.location = application.contextPath + '/main/logout.do' ;
					},2000);
				},
				error: function(a){
					applicationScript.errorAlertWithString(a.responseText);
					$("#activeUserForm").unblock();
				}
			});
		}
	});
});