$(document).ready(function(){
	$('.navbar .navbar-inner .nav .'+application.page).addClass("active").find('i').addClass("icon-white");
	$('.dropdown .login-form input, .dropdown .login-form label').click(function(e) {
		e.stopPropagation();
	});
	$("div.modal").modal({
		backdrop: 'static',
		keyboard: true,
		show:false
	});
	$("#loadMask").spin("large");
	if($.validator){
		$.extend($.validator.messages, {
			required: "โปรดระบุ",
			email: "โปรดระบุอีเมล์ให้ถูกต้อง",
			number: "โปรดระบุจำนวนให้ถูกต้อง",
			equalTo: "โปรดระบุค่าเดิมอีกครั้ง",
			maxlength: jQuery.validator.format("โปรดอย่าระบุค่าที่ยาวกว่า {0} ตัวอักษร"),
			minlength: jQuery.validator.format("โปรดอย่าระบุค่าที่สั้นกว่า {0} ตัวอักษร"),
			rangelength: jQuery.validator.format("โปรดอย่าระบุค่าความยาวระหว่าง {0} ถึง {1} ตัวอักษร"),
			range: jQuery.validator.format("โปรดระบุค่าระหว่าง {0} และ {1}"),
			max: jQuery.validator.format("โปรดระบุค่าน้อยกว่าหรือเท่ากับ {0}"),
			min: jQuery.validator.format("โปรดระบุค่ามากกว่าหรือเท่ากับ {0}")
		});
	}
});
application.blockOption = {
		message:$("#loadMask"),
		css:{ 
			'width':'0px',
			'background-color':'transparent',
			'border':'none',
			'margin':'auto'
		}
};