<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/main/readMoreNews.css">
<div class="row-fluid">
	<div class="span12 news-column">
		<div class="page-header pagination-centered" id="pageHeader">
			<c:if test="${newsInfo != null}">
				<h3>${newsInfo.newsHeader}</h3>
			</c:if>
		</div>
		<div class="news-content">
			<c:if test="${newsInfo == null }">
				<div class="alert alert-error" style="width:300px;margin:auto;">
				  <strong>เกิดข้อผิดพลาด!</strong> ไม่พบข่าวที่ต้องการ หรือคุณไม่มีสิทธิ์อ่านข่าวนี้
				</div>
			</c:if>
			<c:if test="${newsInfo != null }">
				${newsInfo.newsContent}
			</c:if>
		</div>
		<hr />
		<div class="news-footer pagination-right">
			<a class="btn  pull-left" href="${contextPath}/main/readMoreNews.html"><i class=" icon-chevron-left "></i> Back</a> 
			<c:if test="${newsInfo != null }">
				<fmt:parseDate var="updateDate" value="${newsInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
				<p>อัพเดทล่าสุดเมื่อ : <fmt:formatDate value="${updateDate}" pattern="dd-MM-yyyy HH:mm:ss"/> โดย : ${newsInfo.user.firstName} ${newsInfo.user.lastName}</p>
			</c:if>
		</div>
	</div>
</div>