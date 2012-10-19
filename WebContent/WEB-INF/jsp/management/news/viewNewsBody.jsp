<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/news/viewNews.css">

<c:if test="${newsData.courseId == null}">
	<input type="hidden" id="oldCourseId" value="0" />
</c:if>				
<c:if test="${newsData.courseId != null}">
	<input type="hidden" id="oldCourseId" value="${newsData.courseId}" />
</c:if>				
<input type="hidden" id="oldNewsHeader" value="${newsData.newsHeader}"/>
<textarea style="display:none;" id="oldNewsContent">${newsData.newsContent}</textarea>


<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">รายละเอียด </font> ข่าวประชาสัมพันธ์</h2>
	</div>
	<div class="fixed-content" >
		<input type="hidden" id="newsId" name="newsId" value="${newsData.newsId}" />
		<div class="control-group">
	    	<label class="control-label" for="courseId">วิชา</label>
	    	<div class="controls">
				<select id="courseId" name="courseId">
					<c:if test="${newsData.courseId == null}">
						<option value="0" selected="selected">All</option>
					</c:if>
					<c:if test="${newsData.courseId != null}">
						<option value="0">All</option>
					</c:if>
					<c:if test="${not empty courseData}">
						<c:forEach items="${courseData}" var="data">
							<c:if test="${data.courseId == newsData.courseId}">
								<option value="${data.courseId}" selected="selected">${data.courseCode}</option>
							</c:if>
							<c:if test="${data.courseId != newsData.courseId}">
								<option value="${data.courseId}">${data.courseCode}</option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
	    	</div>
	  	</div>
		<div class="control-group">
	    	<label class="control-label" for="newsHeader">หัวข้อข่าว</label>
	    	<div class="controls">
				<input type="text" id="newsHeader" name="newsHeader" class="input-xxlarge" value="${newsData.newsHeader}"/>
	    	</div>
	  	</div>
		<div class="control-group">
	    	<label class="control-label" for="newsContent">เนื้อหาข่าว</label>
	    	<div class="controls">
				<textarea id="newsContent" name="newsContent" class="ckeditorarea">${newsData.newsContent}</textarea>
	    	</div>
	  	</div>
	  	<div class="button-holder pagination-centered">
	  		<button class="btn" id="cancelButton"> ปิด</button>
	  		<button class="btn btn-info" id="editButton"><i class="icon-pencil icon-white"></i> แก้ไข</button>
	  	</div>
	</div>
</div>


<div class="modal hide fade" id="confirmEditNewsModal">
	<div class="modal-header">
		<h3>แก้ไขข่าว ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการแก้ไขข่าวประชาสัมพันธ์ใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-info" id="confirmEditButton" data-loading-text="กำลังแก้ไข..." ><i class="icon-pencil icon-white"></i> แก้ไข</a>
  	</div>
</div>
