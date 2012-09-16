$(document).ready(function(){
	$('.navbar .navbar-inner .nav .'+application.page).addClass("active").find('i').addClass("icon-white");
	$('.navbar .nav li').not('[class~=active]').hover(function(){
		$(this).find('i').removeClass('icon-grey').addClass('icon-white');
	},function(){
		$(this).find('i').removeClass('icon-white').addClass('icon-grey');
	});
	$('.dropdown .login-form input, .dropdown .login-form label').click(function(e) {
		e.stopPropagation();
	});
	$("div.modal").modal({
		backdrop: 'static',
		keyboard: true,
		show:false
	});
	$("#loadMask").spin("large");
	application.blockOption = {
			message:$("#loadMask"),
			css:{ 
				'width':'0px',
				'background-color':'transparent',
				'border':'none',
				'margin':'auto'
			}
	};
});