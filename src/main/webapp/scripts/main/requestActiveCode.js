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
	
	$("#requestActiveCodeForm").validate({
		rules: {
			studentId:{
				required:true,
				minlength:12,
				maxlength:12,
				number:true,
				isStudentId:true,
				isInFaculty:true
			},
			email:{
				required:true,
				email:true
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
				url: application.contextPath + '/main/requestActiveCode.html',
				clearForm: true,
				success: function(){
					applicationScript.successAlertWithStringHeader("กรุณาเช็คอีเมลล์","ส่งรหัสยืนยันสำเร็จ");
					setTimeout(function(){
						window.location = application.contextPath + '/main/activeUser.html?studentId='+studentId ;
					},2000);
				},
				error: function(a){
					applicationScript.errorAlertWithString(a.responseText);
					$("#requestActiveCodeForm").unblock();
				}
			});
		}
	});
});