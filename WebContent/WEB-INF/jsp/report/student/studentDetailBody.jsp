<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/studentDetail.css">
		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน </font> รายบุคคล</h2>
</div>
<button class="btn" style="margin-top:-10px;" onclick="history.back();"><i class="icon-chevron-left"></i> กลับ</button>
<hr>
<div class="row-fluid">
	<div class="span12">
		<div class="well" style="width:600px;margin:auto;">
			<div class="form-horizontal">
				<div class="control-group">
			    	<label class="control-label" >รหัสนักศึกษา</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${studentData.username}</span>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >ชื่อ นามสกุล</label>
			    	<div class="controls">
			    			<span class="input-xlarge uneditable-input">${studentData.firstName} ${studentData.lastName}</span>
			    	</div>
			  	</div>
			</div>
		</div>
	</div>
</div>
<br>
<div class="row-fluid">
	<div class="span12">
		<div class="page-header">
			<h4>ข้อมูลการสอบ</h4>
		</div>
		<c:if test="${empty examData}">
			<div class="alert alert-warning" style="width:300px;margin:auto;">
				<strong>คำเตือน</strong> ไม่พบข้อมูล
			</div>
		</c:if>
		<c:if test="${not empty examData}">
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th>สอบเมื่อ</th>
						<th>ส่งเมื่อ</th>
						<th>หัวข้อการสอบ</th>
						<th>ครั้งที่</th>
						<th>ตอบถูก</th>
						<th>ใช้เวลา</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${examData}" var="data">
						<fmt:parseDate var="examStartDate" value="${data.examStartDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						<fmt:parseDate var="examCompleteDate" value="${data.examCompleteDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						
						<fmt:parseDate var="examUseTime" value="2010-01-01 00:00:00" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						
						<c:set target="${examUseTime}" property="time" value="${examUseTime.time + data.examUsedTime*1000}" />
						<tr>
							<td><fmt:formatDate value="${examStartDate}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td><fmt:formatDate value="${examCompleteDate}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td>${data.examHeader}</td>
							<td>${data.examCount}</td>
							<td>${data.examScore} / ${data.numOfQuestion} ข้อ</td>
							<td><fmt:formatDate value="${examUseTime}" pattern="HH:mm:ss"/></td>
							<td><button class="btn btn-info" onClick="viewExamReport(${data.examResultId})"><i class="icon-zoom-in icon-white"></i> ดูรายละเอียด</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>

<div class="row-fluid">
	<div class="span12">
		<div class="page-header">
			<h4>ข้อมูลการส่งงาน</h4>
		</div>
		<c:if test="${empty evaluatedList}">
			<div class="alert alert-warning" style="width:300px;margin:auto;">
				<strong>คำเตือน</strong> ไม่พบข้อมูล
			</div>
		</c:if>
		<c:if test="${not empty evaluatedList}">
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th>ส่งเมื่อ</th>
						<th>หัวข้องาน</th>
						<th>ตรวจเมื่อ</th>
						<th>คะแนน</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${evaluatedList}" var="data">
						<fmt:parseDate var="sendDate" value="${data.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						<fmt:parseDate var="evaluateDate" value="${data.evaluateDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						<tr>
							<td><fmt:formatDate value="${sendDate}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td>${data.assignmentName}</td>
							<td><fmt:formatDate value="${evaluateDate}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td>${data.score} / ${data.maxScore}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>
<form id="viewExamReportForm" action="${contextPath}/report/examResult.html" method="POST">
	<input type="hidden" name="method" value="viewResult" />
	<input type="hidden" id="examResultId" name="examResultId" />
</form>
<hr>
<button class="btn" onclick="history.back();"><i class="icon-chevron-left"></i> กลับ</button>
