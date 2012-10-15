google.load("visualization", "1", {packages:["corechart"]});

examScore = {};
examScore.drawGraph = function(){
	$("#mainLoader").show();
	
	var data = new google.visualization.DataTable();
	data.addColumn('number', 'Score (%)');
	for(keyArray in application.sectionData){
		  data.addColumn('number', 'Section ['+application.sectionData[keyArray].sectionName+'] เทอม ['+application.sectionData[keyArray].sectionSemester+'/'+application.sectionData[keyArray].sectionYear+']');
		  data.addColumn({type:'string', role:'tooltip'});
	}
	
	data.addRows(application.examScoreData);
	var chart = new google.visualization.ScatterChart($("#mainDiv")[0]);
	chart.draw(data,{
        vAxis: {title: 'เวลา (วินาที)'},
        hAxis: {title: 'ตอบถูก (%)'},
	});
	$("html").unblock();
};

$(document).ready(function(){
	examScore.drawGraph();
});
application.page="report";

