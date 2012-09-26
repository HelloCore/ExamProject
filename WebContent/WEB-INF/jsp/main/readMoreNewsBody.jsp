<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/css/main/readNews.css">
<div class="row-fluid">
	<div class="span12 news-column">
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">ข่าว</font> ประกาศ</h2>
	</div>
	    <ul class="news-list nav nav-pills nav-stacked">
	    </ul>
	    <hr>
		<div class="grid-pagination pagination pagination-centered" style="float:none;">
			<ul>
				<li class="prev disabled"><a href="#" id="prevPageButton">«</a></li>
				<li class="next"><a href="#" id="nextPageButton">»</a></li>
			</ul>
		</div>
	</div>
</div>
