doExam = {};
doExam.numOfQuestion = 50;
doExam.nowAnswer = 0;
doExam.questionAnswerData = [];
var windowHeight = $(window).height();
	if(windowHeight >= 600){
		$('.scrollspy-example').attr('data-spy','scroll').attr('data-target','#navbarExample').attr('data-offset',70).css('height',windowHeight-280);
	}else{
		$('.scrollspy-example').attr('data-spy','scroll').attr('data-target','#navbarExample').attr('data-offset',300).css('height',windowHeight-20);
	}
doExam.convertToJSON = function(){
	var jsonstr = "[" , first=true;

	for(key in doExam.questionAnswerData){
		if(!first){
			jsonstr+=',';
		}else{
			first  = false;
		}
		jsonstr+='{'
					+'"questionId":'+key
					+',"answerId":'+doExam.questionAnswerData[key]
				+'}';
	}
	jsonstr+= "]";
	console.log(jsonstr);
};
	
doExam.checkAndEditData = function(questionId,answerId){
	if(typeof(doExam.questionAnswerData[questionId.toString()]) == 'undefined'){
		doExam.questionAnswerData[questionId.toString()] = answerId;
		doExam.nowAnswer++;
		doExam.checkDidAnswer();
	}else{
		doExam.questionAnswerData[questionId.toString()] = answerId;
	}
};

doExam.checkDidAnswer = function(){
	$("#doQuestion").text(doExam.nowAnswer+" / "+doExam.numOfQuestion);
};

$(document).ready(function(){
	$("#navbarExample li:first a").tab('show');
	$('.answer-panel').click(function(){
		$(this).parent().find('.choosed').removeClass('choosed');
		$(this).addClass('choosed');
		var answerClass,answerId,questionClass,questionId;
		answerClass = $(this).attr('id');
		answerId = answerClass.substring(13,answerClass.length);
		questionClass = $(this).parent().attr('id');
		questionId = questionClass.substring(3,questionClass.length);
		doExam.checkAndEditData(questionId,answerId);
		if( !$('#nav-id-'+questionId).hasClass('did-answer')){$('#nav-id-'+questionId).addClass('did-answer');}
		$('a[href=#'+$(this).parent().attr('id')+']').addClass('choosedA');
		
	});
	
	$('#countDown').countdown({until: application.timeLimit, compact:true});
	$('[data-spy="scroll"]').each(function () {
		  $(this).scrollspy('refresh');
		});
	$("#navbarExample ul li a").click(function(e){
		e.preventDefault();
		$(".scrollspy-example").scrollTo($(this).attr('href'),'fast');
	});
	
	
});