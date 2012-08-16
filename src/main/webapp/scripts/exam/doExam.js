	var windowHeight = $(window).height();
	if(windowHeight >= 600){
		$('.scrollspy-example').attr('data-spy','scroll').attr('data-target','#navbarExample').attr('data-offset',70).css('height',windowHeight-280);
	}else{
		$('.scrollspy-example').attr('data-spy','scroll').attr('data-target','#navbarExample').attr('data-offset',300).css('height',windowHeight-20);
	}
$(document).ready(function(){
	$('.answer-panel').click(function(){
		$(this).parent().find('.choosed').removeClass('choosed');
		$(this).addClass('choosed');
		$('a[href=#'+$(this).parent().attr('id')+']').addClass('choosedA');
	});
	
	$('#countDown').countdown({until: 300, compact:true});
	$('[data-spy="scroll"]').each(function () {
		  $(this).scrollspy('refresh');
		});
	$("#navbarExample ul li a").click(function(e){
		e.preventDefault();
		$(".scrollspy-example").scrollTo($(this).attr('href'),'fast');
	});
});