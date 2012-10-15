google.load("visualization", "1", {packages:["corechart"]});
google.load('visualization', '1', {packages:['gauge']});

$(document).ready(function(){
	
	google.setOnLoadCallback(drawChart);
	function drawChart() {
	  var data,tempData = [['Chapter', 'Avg Score(%)']],tempVal=0
	  		,optionsGague = {
		        width: 400, height: 150,
		        redFrom: 0, redTo: 50,
		        yellowFrom:50, yellowTo: 70,
		        greenFrom:70, greenTo: 100,
		        minorTicks: 5
	  		},tempSelect,tempGague;
	  
	  for( keyArray in application.examData){
		  tempVal = parseFloat((application.examData[keyArray][2]*100/application.examData[keyArray][3]).toFixed(2));
		  tempData[tempData.length] = [application.examData[keyArray][1],tempVal];
		  tempSelect = $("#chapter"+application.examData[keyArray][0]+"Div");
		  tempGague = new google.visualization.Gauge(tempSelect[0]);
		  tempGague.draw(google.visualization.arrayToDataTable([
		     ['Label', 'Value'],
		     [application.examData[keyArray][1],tempVal],
		    ]),optionsGague);
		  
	  }
	  
	  
	  var chart = new google.visualization.PieChart($("#mainDiv")[0]);
	  chart.draw(google.visualization.arrayToDataTable(tempData),null);

	  var data = new google.visualization.DataTable();
	  data.addColumn('number', 'Score (%)');
	  data.addColumn('number', 'Section 010');
	  data.addColumn({type:'string', role:'tooltip'});
	  data.addColumn('number', 'Section 020');
	  data.addColumn({type:'string', role:'tooltip'});
	  
	  data.addRows([
	      [10,1.8,"MR.AAA BBD",null,null],
	      [18,null,null,2.7,"MR.ABA BBE"],
	      [19,3.4,"MR.AAB BBS",null,null],
	      [58,null,null,9.3,"MR.ACA BSB"],
	      [47,5.2,"MR.AAC BBA",null,null],
	      [50,null,null,4.5,"MR.AAC BZZ"],
	      [90,8.0,"MR.ACA ZZB",null,null],
	      [60,null,null,5.5,"MR.CAA ZBB"],
	      [70,1.0,"MR.CAA BBZ",null,null],
	      [65,null,null,2.5,"MR.ACA BZB"],
	  ]);
	  
	  var chart = new google.visualization.ScatterChart($("#mainDiv2")[0]);
	  chart.draw(data,null);
	  
	  
	}
    
});
application.page="report";

