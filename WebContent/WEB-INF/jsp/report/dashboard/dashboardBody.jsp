<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/studentDetail.css">
<script>
	application.examData = ${examDataJSON};
</script>

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">ข้อมูลวิชา</font> ${courseData.courseCode}</h2>
</div>

<div class="page-header" id="pageHeader">
	<h4>กราฟคะแนนเฉลี่ยโดยรวม</h4>
</div>
<div class="row">
	<div class="span12">
		<div id="mainDiv" style="height:500px;margin-top:0px;text-align:center;">
		</div>
	</div>
	<div class="span12">
		<div id="mainDiv2" style="height:500px;margin-top:0px;text-align:center;">
		</div>
	</div>
</div>

<div class="page-header" id="pageHeader">
	<h4>กราฟคะแนนเฉลี่ยแต่ละบทเรียน</h4>
</div>
<div class="row">
	<div class="span12">
		<div id="testDiv">
		</div>
	</div>
	<c:forEach items="${examData}" var="data">
		<div class="span2 sub-item">
			<div id="chapter${data[0]}Div" style="height:130px;">
			</div>
		</div>
	</c:forEach>
</div>