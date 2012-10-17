<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/examReportDetail.css">
<script>
	application.examId = ${examDetail.examId};
</script>
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> ผลการสอบ</h2>
</div>
<a href="${contextPath}/report/exam.html" class="btn" style="margin-top:-10px;"><i class=" icon-chevron-left"></i> Back</a>
<hr>
<div class="row-fluid">
	<div class="span12">
		<div class="detail-holder">
			<div class="form-horizontal well" >
				<div class="control-group">
    				<label class="control-label">ประเภท</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">
							<c:if test="${examDetail.isCalScore}" >สอบจริง</c:if>
							<c:if test="${not examDetail.isCalScore}" >ทดลองสอบ</c:if>
						</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">วิชา</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">${examDetail.course.courseCode}</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">หัวข้อการสอบ</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">${examDetail.examHeader}</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">วันเริ่มสอบ</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">
      						<c:if test="${not empty examDetail.startDate}">
								<fmt:parseDate var="startDate" value="${examDetail.startDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
								<fmt:formatDate value="${startDate}"  pattern="dd-MM-yyyy HH:mm"/>
							</c:if>
							<c:if test="${empty examDetail.startDate}">ไม่กำหนด</c:if>
      					</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">วันหมดเขตสอบ</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">
							<c:if test="${not empty examDetail.endDate}">
								<fmt:parseDate var="endDate" value="${examDetail.endDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
								<fmt:formatDate value="${endDate}"  pattern="dd-MM-yyyy HH:mm"/>
							</c:if>
							<c:if test="${empty examDetail.endDate}">ไม่กำหนด</c:if>
						</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">จำนวนคำถาม</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">${examDetail.minQuestion} - ${examDetail.maxQuestion} ข้อ</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">คะแนนเต็ม</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">${examDetail.maxScore}</span>
    				</div>
  				</div>
				<div class="control-group">
    				<label class="control-label">สอบได้</label>
    				<div class="controls">
      					<span class="input-large uneditable-input">${examDetail.examLimit} ครั้ง</span>
    				</div>
  				</div>
			</div>
		</div>
		<hr>
		<table class="table  table-hover table-bordered" id="examReportDetailTable">
			<thead>
				<tr>
					<th class="sort-both sortable" id="studentIdHeader">รหัสนักศึกษา <i></i></th>
					<th class="sort-both sortable" id="firstNameHeader">ชื่อ - นามสกุล <i></i></th>
					<th class="sort-both sortable" id="examStartDateHeader">สอบเมื่อ <i></i></th>
					<th class="sort-both sortable" id="examCountHeader">ครั้งที่ <i></i></th>
					<th class="sort-both sortable" id="examCompleteDateHeader">ส่งเมื่อ <i></i></th>
					<th class="sort-both sortable" id="examUsedTimeHeader">ใช้เวลา <i></i></th>
					<th class="sort-both sortable" id="examScoreHeader">ได้คะแนน <i></i></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div class="row-fluid">
			<div class="span3">
				<div class="grid-info" id="gridInfo"></div>
			</div>
			<div class="span4 page-size-div">
				<select id="pageSize" name="pageSize" class="page-size ">
					<option value="20">20</option>
			 		<option value="50">50</option>
			 		<option value="50">100</option>
			 	</select> items per page
			</div>
			<div class="span5">
				<div class="grid-pagination pagination pagination-centered">
					<ul>
						<li class="prev disabled"><a href="#" id="prevPageButton">«</a></li>
						<li class="next"><a href="#" id="nextPageButton">»</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<form id="viewExamReportForm" action="${contextPath}/report/examResult.html" method="POST">
	<input type="hidden" name="method" value="viewResult" />
	<input type="hidden" id="examResultId" name="examResultId" />
</form>
<hr>
<a href="${contextPath}/report/exam.html" class="btn" ><i class=" icon-chevron-left"></i> Back</a>