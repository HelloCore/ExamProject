<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<script>
	application.page='content';
</script>

<c:if test="${canEdit}">
	<script>
		application.currentPath=${currentPath};
	</script>
	
	<script src="${contextPath}/resources/jquery.file.validator.js"></script>
	<script src="${contextPath}/scripts/main/content.js"></script>
</c:if>