$(document).ready(function(){

	$('<div class="modal hide fade" id="timeoutModal">'
			+'<div class="modal-header">'
				+'<h3>Your session is about to expire!</h3>'
			+'</div>'
			+'<div class="modal-body">'
				+'You will be logged off in <span id="dialog-countdown" style="font-weight:bold"></span> seconds.'
			+'</div>'
		  	+'<div class="modal-footer">'
		    	+'<a href="#" class="btn btn-primary" data-dismiss="modal" id="continueSessionButton" >Confirm</a>'
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
			alert("Session Timeout!!");
			window.location = application.contextPath+'/main/logout.do';
		},
		onIdle: function(){
			$(this).modal('show');
		},
		onCountdown: function(counter){
			$countdown.html(counter);
		}
	});
});