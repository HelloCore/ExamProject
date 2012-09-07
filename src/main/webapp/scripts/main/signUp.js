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
	
	$("#signUpForm").validate({
		rules: {
			studentId:{
				required:true,
				minlength:12,
				maxlength:12,
				number:true,
				isStudentId:true,
				isInFaculty:true
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
		var studentId = $("#studentId").val();
		$("#signUpForm").block(application.blockOption);
		var thisButton = $(this).button('loading');
		$("#signUpForm").ajaxSubmit({
			type:'post',
			url: application.contextPath + '/main/signUp.html',
			clearForm: true,
			success: function(){
				$('#confirmSignUpModal').modal('hide');
				thisButton.button('reset');
				applicationScript.successAlertWithStringHeader("รหัสยืนยันได้ถูกส่งไปยังอีเมลล์ของท่านเรียบร้อยแล้ว กรุณารอซักครู่.. ระบบจะนำท่านไปยังหน้าจอยืนยันการสมัครสมาชิก","สมัครสมาชิกสำเร็จ");
				setTimeout(function(){
					window.location = application.contextPath + '/main/activeUser.html?studentId='+studentId ;
				},3000);
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