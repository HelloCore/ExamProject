<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<select>
	<c:if test="${not empty optionAll}">
		<c:if test="${optionAll==true}">
			<option value="0"><spring:message code="Option All" text="ทั้งหมด"></spring:message></option>
		</c:if>
	</c:if>
	<c:if test="${not empty courseData}">
		<c:forEach items="${courseData}" var="data">
			<option value="${data.courseId}">${data.courseCode}</option>
		</c:forEach>
	</c:if>
</select>
	