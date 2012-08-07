<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<script>
	alert("Session Timeout!!");
	window.location = "${contextPath}/main/login.html";
</script>