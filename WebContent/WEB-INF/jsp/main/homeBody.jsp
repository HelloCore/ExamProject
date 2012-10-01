<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=225787540877141";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<link rel="stylesheet" href="${contextPath}/css/main/home.css">
<div class="row-fluid">
	<div class="span7 news-column">
		<div class="page-header">
	    	<h2>News <small>ข่าวประกาศ</small></h2>
	  	</div>
	    <ul class="news-list nav nav-pills nav-stacked">
			<c:forEach items="${newsData}" var="data">
				<li>
					<a href="${contextPath}/main/readNews.html?id=${data.id}">
					<c:if test="${data.courseCode == null}">
						<span class="label label-warning">ทั่วไป</span>
					</c:if>
					<c:if test="${data.courseCode != null}">
						<span class="label label-important">${data.courseCode}</span>
					</c:if>
						${data.newsHeader}
					</a>
				</li>
			</c:forEach>
	    </ul>
	    <a href="${contextPath}/main/readMoreNews.html" class="pull-right">อ่านเพิ่มเติม</a>
	</div>
	<div class="span5">
		<div class="fb-comments" data-href="http://www.core-serv.com/main/home.html" data-num-posts="2" data-width="350"></div>
	</div>
</div>