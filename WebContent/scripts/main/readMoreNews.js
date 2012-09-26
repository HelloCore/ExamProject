application.page="news";

readMoreNews = {};
readMoreNews.totalItems;
readMoreNews.totalPage;
readMoreNews.currentPage = 1;


readMoreNews.getGrid = function(){
	$('.news-list').block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/main/readMoreNews.html',
		type: 'POST',
		data: {
			method : 'getNews',
			start : ((readMoreNews.currentPage-1) * 20)
		},
		success: function(data){
			$('.news-list li').empty();
			var strHtml,labelNormal = '<span class="label label-warning">ทั่วไป</span>'
						,labelCourseFirst = '<span class="label label-important">'
						,labelCourseLast = '</span>',currentLabel ='';
			for(keyArray in data){
				if(data[keyArray].courseCode){
					currentLabel = labelCourseFirst + data[keyArray].courseCode + labelCourseLast;
				}else{
					currentLabel = labelNormal;
				}
				strHtml = '<li><a href="'+application.contextPath+'/main/readNews.html?id='+data[keyArray].id+'">'
								+ currentLabel +' '+ data[keyArray].newsHeader+
								'</a></li>';
				$('.news-list').append(strHtml);
			}
			applicationScript.setPagination(readMoreNews.currentPage,readMoreNews.totalPage);
			$('.news-list').unblock();
		}
	});
};

changePage = function(page){
	if(readMoreNews.currentPage != page){
		readMoreNews.currentPage = page;
		readMoreNews.getGrid();
	}
};


$(document).ready(function(){
	$('.news-list').block(application.blockOption);
	$.ajax({
		url: application.contextPath + '/main/readMoreNews.html',
		type: 'POST',
		data: {method : 'getNewsCount'},
		success: function(data){
			readMoreNews.totalItems = data;
			readMoreNews.totalPage = Math.ceil(data/20);
			readMoreNews.getGrid();
		}
	});
	$("#prevPageButton").click(function(e){
		e.preventDefault();
		if(readMoreNews.currentPage > 1){
			readMoreNews.currentPage--;
			readMoreNews.getGrid();
		}
	});
	$("#nextPageButton").click(function(e){
		e.preventDefault();
		if(readMoreNews.currentPage < sectionManagement.totalPage){
			readMoreNews.currentPage++;
			readMoreNews.getGrid();
		}
	});
});