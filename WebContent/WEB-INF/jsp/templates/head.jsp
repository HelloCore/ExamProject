<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="contextPath" scope="request" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/css/icons.css">
<link rel="stylesheet" href="${contextPath}/resources/jquery.jgrowl.css">
<link rel="stylesheet" href="${contextPath}/css/style.css">

<script src="${contextPath}/resources/modernizr-2.5.3-respond-1.1.0.min.js"></script>

<script type="text/javascript">
var application = {};
application.contextPath = '${contextPath}';
</script>