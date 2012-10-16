<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/customReport.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> สรุปคะแนน</h2>
</div>

<div class="row-fluid">
	<div class="span12">
		<h4> ข้อมูล วิชา ${sectionData.course.courseCode} Section ${sectionData.sectionName} เทอม ${sectionData.sectionSemester} ปี ${sectionData.sectionYear}</h4>
		<table class="table table-bordered table-hover table-condensed">
		<c:set var="maxScore" scope="page" value="0"/>
			<thead>
				<tr>
					<th>รหัสนักศึกษา</th>
					<th>ชื่อ - นามสกุล</th>
					<c:if test="${not empty examData}">
						<c:forEach items="${examData}" var="data">
							<c:set var="maxScore" scope="page" value="${maxScore+data.examScore}"/>
							<th>${data.examHeader} (${data.examScore})</th>
						</c:forEach>
					</c:if>
					<c:if test="${not empty assignmentData}">
						<c:forEach items="${assignmentData}" var="data">
							<c:set var="maxScore" scope="page" value="${maxScore+data.maxScore}"/>
							<th>${data.assignmentTaskName} (${data.maxScore})</th>
						</c:forEach>
					</c:if>
					<th> รวม (${maxScore})</th>
				</tr>
			</thead>
			<tbody>
				
				<c:if test="${not empty recordData}">
					<c:forEach items="${recordData}" var="data">
						<tr>
							<td>${data[0]}</td>
							<td>${data[1]} ${data[2]} ${data[3]}</td>
							
							<c:set var="nowScore" scope="page" value="0"/>
							<c:set var="listSize" scope="page" value="${fn:length(data) -1}"/>
							<c:forEach var="i" begin="4" end="${listSize}" step="1">
								<c:if test="${empty data[i]}">
									<td>-</td>
								</c:if>
								<c:if test="${not empty data[i]}">
									<c:set var="nowScore" scope="page" value="${nowScore +data[i]}"/>
									<td><fmt:formatNumber value="${data[i]}" maxFractionDigits="2"/></td>
								</c:if>
							</c:forEach>
							<td><fmt:formatNumber value="${nowScore}" maxFractionDigits="2"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>