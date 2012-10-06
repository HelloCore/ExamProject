<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/assignment/resultAssignment.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Result</font> Assignments</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<c:if test="${not empty success}">
			<div style="width:500px;margin:auto;text-align:center;">
				<div class="alert alert-success">
					<strong>Success !</strong> ${success} 
				</div>
			</div>
		</c:if>
	</div>
</div>

