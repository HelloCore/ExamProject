$(document).ready(function(){

	$('<div class="modal hide fade" id="timeoutModal">'
			+'<div class="modal-header">'
				+'<h3>Session ของคุณกำลังจะหมดอายุ</h3>'
			+'</div>'
			+'<div class="modal-body">'
				+'คุณจะถูกบังคับให้ออกจากระบบในอีก <span id="dialog-countdown" style="font-weight:bold"></span> วินาที.'
			+'</div>'
		  	+'<div class="modal-footer">'
		  		+'<a href="'+application.contextPath+'/main/logout.do" class="btn">ออกจากระบบ</a>'
		    	+'<a href="#" class="btn btn-primary" data-dismiss="modal" id="continueSessionButton" ><i class="icon-time icon-white"></i> ต่ออายุ Session</a>'
		  	+'</div>'
		+'</div>').insertAfter(".main-container").modal({
			backdrop: 'static',
			keyboard: true,
			show:false
		});
	
	var $countdown = $("#dialog-countdown");
	
	$.idleTimeout('#timeoutModal', '#continueSessionButton', {
		idleAfter: 1200, // 20 minute
		pollingInterval: 1500, // 25 minute
		keepAliveURL: application.contextPath+'/main/keepAlive.html',
		serverResponseEquals: 'OK',
		onTimeout: function(){
			$(this).modal('hide');
			$('title').text('Your session has expired');
			$.get(application.contextPath+'/main/logout.do');
			$('<div class="modal fade" id="sessionTimeoutModal">'
					+'<div class="modal-header">'
						+'<h3>Session ของคุณหมดอายุ</h3>'
					+'</div>'
					+'<div class="modal-body">'
						+'กรุณาล็อกอินใหม่อีกครั้ง'
					+'</div>'
					+'<div class="modal-footer">'
						+'<a href="'+application.contextPath+'/main/home.html" class="btn btn-primary">'
							+'<i class="icon-home icon-white"></i> กลับสู่หน้าหลัก'
						+'</a>'
					+'</div>'
				+'</div>').modal({
					backdrop: 'static',
					keyboard: true,
					show:true
				});
		},
		onIdle: function(){
			$(this).modal('show');
		},
		onCountdown: function(counter){
			$countdown.html(counter);
		}
	});
});