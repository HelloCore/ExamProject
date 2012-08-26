doExam = {};
doExam.nowAnswer = 0;
doExam.questionAnswerData = [];
var windowHeight = $(window).height()
	,navHeight = $("#navbarExample").height();
	if(windowHeight >= 767){
		var questionHeight = (windowHeight-175-navHeight);
		$('.scrollspy-example').attr('data-target','#navbarExample').css('height',questionHeight).scrollspy({
			offset:70
		});
		
		$('.blockPage').css('height',140-navHeight);
	}else{
		var questionHeight = (windowHeight-10-navHeight);
		if(questionHeight <= 100){
			questionHeight=200 ;
		}
		$('.scrollspy-example').attr('data-target','#navbarExample').css('height',questionHeight).scrollspy({
			offset:250
		});
		
		$('.blockPage').css('height',30+navHeight);
	}
doExam.convertToJSON = function(data){
	var newData = [],i=0;
	for(key in data){
		newData[i] = {};
		newData[i].questionId = data[key].questionId;
		newData[i].answerId = data[key].answerId;
		newData[i].examResultAnswerId = data[key].examResultAnswerId;
		newData[i].examResultId = data[key].examResultId;
		i++;
	}
	return JSON.stringify(newData);
};

doExam.sendExam = function(){
	$('.scrollspy-example').scrollspy('refresh');
	var examResultAnswerData = doExam.convertToJSON(doExam.questionAnswerData);
	doExam.questionAnswerData = [];
	$("body").block(application.blockOption);
	$('#countDown').countdown('pause');
	$("#sendExamButton").attr('data-loading-text','ส่งข้อสอบ...').button('loading');
	
	$.ajax({
		url: application.contextPath + '/exam/sendExam.html',
		type: 'POST',
		data: {
			method:'sendExam',
			examResultAnswerData: examResultAnswerData,
			examResultId:application.examResultId
		},
		success: function(){
			applicationScript.saveComplete();
			$("#sendExamButton").button('reset');
			$("#viewResultForm").submit();
		},
		error : function(){
			applicationScript.errorAlertWithStringTH("เกิดข้อผิดพลาด ไม่สามารถส่งข้อสอบได้");
			$("#sendExamButton").button('reset');
		}
	});
};
doExam.autoSave = function(){
	var examResultAnswerData = doExam.convertToJSON(doExam.questionAnswerData)
		,tempArray = doExam.questionAnswerData;
	doExam.nowRate += doExam.saveRate;
	if(doExam.nowRate >= application.numOfQuestion){
		doExam.nowRate = application.numOfQuestion +1;
	}
	
	$("#sendExamButton").attr('data-loading-text','Auto Save...').button('loading');
	
	$.ajax({
		url: application.contextPath + '/exam/autoSaveExam.html',
		type: 'POST',
		data: {
			method:'autoSave',
			examResultAnswerData: examResultAnswerData,
			examResultId:application.examResultId
		},
		success: function(){
			for(key in tempArray){
				delete doExam.questionAnswerData[key];
			}
			
			applicationScript.autoSaveComplete();
			$("#sendExamButton").button('reset');
		},
		error : function(){
			applicationScript.errorAlertWithStringTH("เกิดข้อผิดพลาด ไม่สามารถเซฟได้");
			$("#sendExamButton").button('reset');
		}
	});
};
	
doExam.checkAndEditData = function(questionId,answerId,examResultAnswerId){
	if($("#exam-choose-"+questionId).val().length==0){
		doExam.questionAnswerData[questionId.toString()] = {
				questionId:questionId,
				answerId:answerId,
				examResultAnswerId:examResultAnswerId,
				examResultId:application.examResultId
		};
		$("#exam-choose-"+questionId).val(answerId);
		doExam.nowAnswer++;
		doExam.checkDidAnswer();
	}else{
		doExam.questionAnswerData[questionId.toString()] = {
				questionId:questionId,
				answerId:answerId,
				examResultAnswerId:examResultAnswerId,
				examResultId:application.examResultId
		};
		$("#exam-choose-"+questionId).val(answerId);
		
	}
};

doExam.checkDidAnswer = function(){
	$("#doQuestion").text(doExam.nowAnswer+" / "+application.numOfQuestion);
	if(doExam.nowAnswer >= doExam.nowRate){
		doExam.autoSave();
	}
};

$(document).ready(function(){
	$('.navbar .btn').attr('disabled',true).removeAttr('data-toggle').click(function(e){e.preventDefault();});
	$('.navbar a').removeAttr('href');
	doExam.saveRate = Math.round(application.numOfQuestion /5 );
	doExam.nowRate = doExam.saveRate;
	
	var questionId,questionIdRaw;
	$('input[id^=exam-choose][value!=""]').each(function(){
		doExam.nowAnswer++;
		if(doExam.nowAnswer >= doExam.nowRate){
			doExam.nowRate+=doExam.saveRate;
			if(doExam.nowRate >= application.numOfQuestion){
				doExam.nowRate = application.numOfQuestion+1;
			}
		}
		questionIdRaw = $(this).attr('id');
		questionId = questionIdRaw.substring(12,questionIdRaw.length);
		$("#answer-panel-"+$(this).val()).addClass('choosed');
		if( !$('#nav-id-'+questionId).hasClass('did-answer')){$('#nav-id-'+questionId).addClass('did-answer');}
		$('a[href=#tab'+questionId+']').addClass('choosedA');
	});
	
	$("#doQuestion").text(doExam.nowAnswer+" / "+application.numOfQuestion);
	$("#navbarExample li:first a").tab('show');
	$('.answer-panel').click(function(){
		$(this).parent().find('.choosed').removeClass('choosed');
		$(this).addClass('choosed');
		var answerClass,answerId,questionClass,questionId,examResultAnswerId;
		answerClass = $(this).attr('id');
		answerId = answerClass.substring(13,answerClass.length);
		questionClass = $(this).parent().attr('id');
		questionId = questionClass.substring(11,questionClass.length);
		examResultAnswerId = $('#exam-result-answer-id-'+questionId).val();
		
		doExam.checkAndEditData(questionId,answerId,examResultAnswerId);
		
		if( !$('#nav-id-'+questionId).hasClass('did-answer')){$('#nav-id-'+questionId).addClass('did-answer');}
		$('a[href=#'+$(this).parent().attr('id')+']').addClass('choosedA');
	});
	
	$('#countDown').countdown({until: new Date(application.expireDate), compact:true,onExpiry:examExpire,onTick:watching});
	$('[data-spy="scroll"]').each(function () {
		  $(this).scrollspy('refresh');
		});
	$("#navbarExample ul li a").click(function(e){
		e.preventDefault();
		$(".scrollspy-example").scrollTo($(this).attr('href'),'fast');
	});
	
	$("#sendExamButton").click(function(){
		if(doExam.nowAnswer < application.numOfQuestion){
			$("#errorItem").html('คุณยังทำข้อสอบไม่ครบทุกข้อ ');
		}else{
			$("#errorItem").text('');
		}
		$("#sendExamConfirm").modal('show');
	});
	$("#sendExamConfirmButton").click(function(){
		$("#sendExamConfirm").modal('hide');
		doExam.sendExam();
	});
});

examExpire = function(){
	applicationScript.errorAlertWithStringHeader("บังคับส่งข้อสอบ","หมดเวลาสอบ");
	doExam.sendExam();
};


watching = function(periods){
	if(periods[6] == 0 && ( periods[5]==3
			|| periods[5]==2 || periods[5]==1 )){
		applicationScript.warningAlertWithString("เหลือเวลาอีก "+periods[5]+" นาที");
	}
	if(periods[5] == 0 && periods[6] % 5 ==0){
		applicationScript.warningAlertWithString("เหลือเวลาอีก "+periods[6]+" วินาที");
	}
	
};