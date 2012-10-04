<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('ROLE_STUDENT')" >
	<script>
		application.page='content';
	</script>
</sec:authorize>

<sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')" >
	<script>
		application.page='genericManagement';
	</script>
</sec:authorize>

<c:if test="${canEdit}">
	<script>
		application.currentPath=${currentPath};
	</script>
	
	<script src="${contextPath}/resources/jquery.file.validator.js"></script>
	<script src="${contextPath}/scripts/main/content.js"></script>
</c:if>