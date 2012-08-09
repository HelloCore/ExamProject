<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="contextPath" scope="request" value="${pageContext.request.contextPath}"></c:set>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport" content="width=device-width">


<link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${contextPath}/css/style.css">
<link rel="stylesheet" href="${contextPath}/resources/jquery.jgrowl.css">

<script src="${contextPath}/resources/modernizr-2.5.3-respond-1.1.0.min.js"></script>

<script type="text/javascript">
var application = {};
application.contextPath = '${contextPath}';

</script>