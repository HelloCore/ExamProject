google.load("visualization", "1", {packages:["corechart"]});
google.load('visualization', '1', {packages:['gauge']});

avgScore = {};
avgScore.drawGraph = function(){
	$("body").block(application.blockOption);
	$.ajax({
		url: application.contextPath+"/report/avgScore.html",
		data: {
			method: 'getAvgScoreData',
			courseId: $("#courseId").val()
		},
		type: 'POST',
		dataType: 'json',
		success: function(data){
			if(data.length==0){
				$("#mainLoader").hide();
				$("#alertWarning").show();
			}else{
				$("#alertWarning").hide();
				$("#mainLoader").show();
				var subGraph = $("#subGraph").empty();
				var mainDiv = $("#mainDiv").empty();
				
				var tempData = [['Chapter', 'Avg Score(%)']],tempVal=0
			  		,optionsGague = {
				        width: 400, height: 150,
				        redFrom: 0, redTo: 50,
				        yellowFrom:50, yellowTo: 70,
				        greenFrom:70, greenTo: 100,
				        minorTicks: 5
			  		},tempSelect,tempGague,tempVal2;
				
				if($(window).width() < 980 && $(window).width()>=768){
					optionsGague.width = 350;
					optionsGague.height = 130;
				}else if ($(window).width()<768){
					optionsGague.height = 150;
				}
				for( keyArray in data){
					subGraph.append('<div class="span2 sub-item">'
										+'<div id="chapter'+data[keyArray][0]+'Div" style="height:130px;">'
										+'</div>'
									+'</div>');
					
					  tempVal = Math.round(((data[keyArray][2]*100)/data[keyArray][3])*100)/100;
					  tempVal2 = Math.round((100-tempVal)*100)/100;
					  tempData[tempData.length] = [data[keyArray][1]+' ตอบถูก '+tempVal+'% \nตอบผิด '+tempVal2+'%',tempVal];
					  tempSelect = $("#chapter"+data[keyArray][0]+"Div");
					  tempGague = new google.visualization.Gauge(tempSelect[0]);
					  tempGague.draw(google.visualization.arrayToDataTable([
					     ['Label', 'Value'],
					     [data[keyArray][1],tempVal],
					    ]),optionsGague);
					  
				  }
				var chart = new google.visualization.PieChart(mainDiv[0]);
				chart.draw(google.visualization.arrayToDataTable(tempData),{
					tooltip:{
						text: 'percentage'
					},chartArea:{
						width: '85%',
						height: '85%'
					}
				});
			}
			$("body").unblock();
		},
		error: function(data){
			applicationScript.resolveError(data.responseText);
			$("body").unblock();
		}
	});
};

$(document).ready(function(){
	
	$("#courseId").load(application.contextPath+"/management/courseComboBox.html",function(){
		$(this).select2();
		$('body').unblock();
	});
	$("#drawGraphButton").click(function(){
		avgScore.drawGraph();
	});
   
});
application.page="report";

