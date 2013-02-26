<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/assignment/selectAssignment.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายการ</font> Assignment</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<c:if test="${not empty error}">
			<div style="width:500px;margin:auto;text-align:center;">
				<div class="alert alert-error">
					<strong><i class="fam-exclamation"></i> เกิดข้อผิดพลาด !</strong> ${error} 
				</div>
			</div>
		</c:if>
		<c:if test="${empty assignmentData}">
			<div style="width:500px;margin:auto;text-align:center;">
				<div class="alert alert-warning">
					<strong><i class="fam-error"></i> ขออภัย</strong> ไม่พบงานที่คุณสามารถส่งได้ 
				</div>
				<a class="btn" href="${contextPath}/main/home.html"><i class=" icon-chevron-left "></i> กลับ</a>
			</div> 
		</c:if>
		<c:if test="${not empty assignmentData}">
			<table class="table table-hover table-condensed" id="assignmentTable">
				<thead>
					<tr>
						<th></th>
						<th>วิชา</th>
						<th>หัวข้อ</th>
						<th>ส่งได้</th>
						<th>หมดเขต</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${assignmentData}" var="data">
						<fmt:parseDate var="startDate" value="${data[2]}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						<fmt:parseDate var="endDate" value="${data[3]}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						<tr>
							<td><input type="radio" name="courseId" class="course-id-radio"  value="${data[0]}" /></td>
							<td>${data[4]}</td>
							<td>${data[1]}</td>
							<td><fmt:formatDate value="${startDate}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td><fmt:formatDate value="${endDate}" pattern="dd-MM-yyyy HH:mm"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>
<c:if test="${not empty assignmentData}">
<form id="redirectForm" method="POST" action="${contextPath}/assignment/submit.html">
	<input type="hidden" name="method" value="submitAssignment" />
	<input type="hidden" name="assignmentId" id="assignmentIdRedirect" />
</form>
<div class="row-fluid">
	<hr>
		<div class="span12 form-horizontal">
			<div class="fixed-content" style="display:none;" id="detailAssignment">
				<div class="page-header">
					<h3>รายละเอียด</h3>
				</div>
				<div class="control-group">
				    <label class="control-label">วิชา</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailCourseCode">CS.111</span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">หัวข้อ</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailAssignmentName">ทดสอบ</span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">รายละเอียด</label>
				    <div class="controls">
						<pre id="detailAssignmentDesc"></pre>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">เริ่มส่งได้</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailStartDate"></span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">หมดเขต</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailEndDate"></span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">ขนาดไฟล์รวม</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailLimitFileSizeKb"></span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">ส่งได้</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailNumOfFile">์</span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">สั่งโดย</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailCreateBy"></span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">สั่งเมื่อ</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailCreateDate"></span>
				    </div>
				</div>
				<div class="control-group">
				    <label class="control-label">คะแนนเต็ม</label>
				    <div class="controls">
						<span class="input-xlarge uneditable-input" id="detailMaxScore"></span>
				    </div>
				</div>
				<div class="form-actions">
			  		<button type="submit" class="btn btn-primary" id="selectAssignmentButton"><i class="icon-pencil icon-white"></i> ส่งงาน</button>
				</div>
			</div>
		</div>
	</div>
</c:if>

