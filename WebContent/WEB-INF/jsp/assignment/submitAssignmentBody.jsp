<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/assignment/selectAssignment.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">ส่ง</font> Assignment</h2>
</div>
<c:if test="${empty assignmentData}">
	
</c:if>
<c:if test="${not empty assignmentData}">
	<fmt:parseDate var="startDate" value="${assignmentData.startDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
	<fmt:parseDate var="endDate" value="${assignmentData.endDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
	<fmt:parseDate var="createDate" value="${assignmentData.createDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
	<div class="row-fluid">
			<div class="span12 form-horizontal">
				<div class="fixed-content"  id="detailAssignment">
					<div class="page-header">
						<h3>รายละเอียด</h3>
					</div>
					<div class="control-group">
					    <label class="control-label">วิชา</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailCourseCode">${assignmentData.courseCode}</span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">หัวข้อ</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailAssignmentName">${assignmentData.assignmentName}</span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">รายละเอียด</label>
					    <div class="controls">
							<pre id="detailAssignmentDesc">${assignmentData.assignmentDesc}</pre>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">เริ่มส่งได้</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailStartDate"><fmt:formatDate value="${startDate}" pattern="dd-MM-yyyy HH:mm"/></span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">หมดเขต</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailEndDate"><fmt:formatDate value="${endDate}" pattern="dd-MM-yyyy HH:mm"/></span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">ขนาดไฟล์รวม</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailLimitFileSizeKb">${assignmentData.limitFileSizeKb} KB</span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">ส่งได้</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailNumOfFile">${assignmentData.numOfFile} ไฟล์</span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">สั่งโดย</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailCreateBy">${assignmentData.firstName} ${assignmentData.lastName}</span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">สั่งเมื่อ</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailCreateDate"><fmt:formatDate value="${createDate}" pattern="dd-MM-yyyy HH:mm"/></span>
					    </div>
					</div>
					<div class="control-group">
					    <label class="control-label">คะแนนเต็ม</label>
					    <div class="controls">
							<span class="input-xlarge uneditable-input" id="detailMaxScore">${assignmentData.maxScore}</span>
					    </div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="fixed-content">
					<div class="page-header">
						<h3>ส่งงาน</h3>
					</div>
					<div class="form-horizontal">
						<form  id="submitAssignmentForm" enctype="multipart/form-data" accept-charset="UTF-8" method="POST" action="${contextPath}/assignment/submitFile.html">
							<input type="hidden" name="assignmentId" value="${assignmentData.assignmentId}"/>
							<c:forEach var="i" begin="1" end="${assignmentData.numOfFile}">
								<div class="control-group">
								    <label class="control-label">ไฟล์ที่ ${i}</label>
								    <div class="controls">
										<input type="file" name="uploadFile[${i-1}]" class="upload-file"/>
									</div>
								</div>
							</c:forEach>
						</form>
						<div class="form-actions">
					  		<button class="btn btn-primary" id="submitAssignmentButton"><i class="icon-pencil icon-white"></i> ส่งงาน</button>
						</div>
					</div>
				</div>
			</div>
		</div>			
		
<div class="modal hide fade" id="confirmSubmit">
	<div class="modal-header">
		<h3>ส่งงาน ?</h3>
	</div>
	<div class="modal-body">
		<font class="error" id="errorItem"></font> คุณต้องการส่งงานใช่หรือไม โปรดยืนยัน ?
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-primary" id="submitButton" data-loading-text="submit..." ><i class="icon-pencil icon-white"></i> ส่งงาน</a>
  	</div>
</div>		
	</c:if>
	
	
