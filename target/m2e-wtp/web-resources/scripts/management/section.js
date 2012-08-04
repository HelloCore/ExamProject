application.page='management';
sectionManagement = {};
sectionManagement.rows = 5;
sectionManagement.page = 1;
sectionManagement.lastPage = 1;
sectionManagement.sectionId = '';
sectionManagement.sectionName = '';
sectionManagement.sectionYear = '';
sectionManagement.sectionSemester = '';
sectionManagement.courseCode = '';
sectionManagement.orderBy = 'sectionId';
sectionManagement.order = 'asc';
sectionManagement.pagination = $('.grid-pagination');

sectionManagement.getGrid = function(){
	$.ajax({
		url: application.contextPath + '/management/section.html',
		type: 'POST',
		data: {
			method: 'getSectionTable'
			,rows: sectionManagement.rows
			,page: sectionManagement.page
			,orderBy: sectionManagement.orderBy
			,order: sectionManagement.order
			,sectionNameSearch: sectionManagement.sectionName
			,sectionYearSearch : sectionManagement.sectionYear
			,sectionSemesterSearch: sectionManagement.sectionSemester
			,courseCodeSearch : sectionManagement.courseCode
		},
		dataType: 'json',
		success: function(data,status){
			$("#sectionGrid tbody").empty();
			var strHtml ;
			for(keyArray in data.records){
				strHtml = '<tr>'+
							'<td id="section-id-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionId+'</td>'+
							'<td id="section-name-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionName+'</td>'+
							'<td id="section-year-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionYear+'</td>'+
							'<td id="section-semester-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].sectionSemester+'</td>'+
							'<td id="course-code-'+data.records[keyArray].sectionId+'">'+data.records[keyArray].courseCode+'</td>'+
							'<td>'+
								'<button class="btn btn-info" onClick="editSection('+data.records[keyArray].sectionId+')"><i class="icon-edit icon-white"></i> Edit</button> '+
								'<button class="btn btn-danger" onClick="deleteSection('+data.records[keyArray].sectionId+')"><i class="icon-trash icon-white"></i> Delete</button> '+
							'</td>'+
							'</tr>';
				$("#sectionGrid tbody").append(strHtml);
			}
			var startRecord = (((sectionManagement.rows)*(sectionManagement.page-1))+1);
			$("#gridInfo").text('Record '+startRecord+' - '+(startRecord+data.records.length -1)+' of '+data.totalRecords+' Records ');
			sectionManagement.lastPage = data.totalPages;
			sectionManagement.setPagination();
		},
		error: function(){
			
		}
	});
};

sectionManagement.getDefaultGrid = function(){
	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').addClass('sort-both');
	$("#sectionIdHeader").addClass('current-sort')
		.removeClass('sort-asc')
		.removeClass('sort-desc')
		.removeClass('sort-both')
		.addClass('sort-asc');
	sectionManagement.orderBy = "sectionId";
	sectionManagement.order = "asc";
	sectionManagement.getGrid();
};

sectionManagement.setPagination = function(){
	var sClass;
	var first = (sectionManagement.page-2);
	if( first < 1){ first = 1;} 
	var last = (sectionManagement.page+2);
	if( last > sectionManagement.lastPage){ last = sectionManagement.lastPage;} 
	
	$('.grid-pagination li:gt(0)').filter(':not(:last)').remove();

	for(var i=first;i<=last;i++){
		sClass = (i==sectionManagement.page) ? 'class="active"' :'';
		$('<li '+sClass+'><a href="#">'+i+'</a></li>')
			.insertBefore( $('.grid-pagination li:last')[0] )
			.bind('click',function(e){
				e.preventDefault();
				changePage($(this).text());
			});
	}
	
	if ( sectionManagement.page == 1 ) {
		$('.grid-pagination li:first').addClass('disabled');
	} else {
		$('.grid-pagination li:first').removeClass('disabled');
	}

	if ( sectionManagement.page == sectionManagement.lastPage ) {
		$('.grid-pagination li:last').addClass('disabled');
	} else {
		$('.grid-pagination li:last').removeClass('disabled');
	}
};

changePage = function(page){
	if(sectionManagement.page != page){
		sectionManagement.page = page;
		sectionManagement.getGrid();
	}
};
$(document).ready(function(){
	sectionManagement.getDefaultGrid();
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(sectionManagement.page > 1){
			sectionManagement.page--;
			sectionManagement.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(sectionManagement.page < sectionManagement.lastPage){
			sectionManagement.page++;
			sectionManagement.getGrid();
		}
	});
	$('.sortable').click(function(){
		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
		$('.current-sort').removeClass('sort-desc').removeClass('sort-asc').addClass('sort-both').removeClass('currentSort');
		
		if(sectionManagement.orderBy == myId){
			if(sectionManagement.order == "asc"){
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
					.addClass('sort-desc');
				sectionManagement.order = "desc";
			}else{
				$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
				sectionManagement.order = "asc";
			}
		}else{
			$(this).addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
				.addClass('sort-asc');
			sectionManagement.orderBy = myId;
			sectionManagement.order = "asc";
		}
		sectionManagement.getGrid();
	});
	$("#pageSize").change(function(){
		sectionManagement.rows = $(this).val();
		sectionManagement.getGrid();
	});
	$("#refreshButton").click(function(){
		sectionManagement.sectionName = '';
		sectionManagement.sectionYear = '';
		sectionManagement.sectionSemester = '';
		sectionManagement.courseCode = '';
		$("#sectionNameSearch").val('');
		$("#sectionYearSearch").val('');
		$("#sectionSemesterSearch").val('');
		$("#courseCodeSearch").val('');
//		$('#searchButton').removeClass('ui-state-active-search');
		sectionManagement.getDefaultGrid();
	});
});