<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/customReport.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> สรุปคะแนน</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="form-inline">
    		<label class="control-label" for="courseId">เลือกวิชาที่ต้องการ </label>
    		<select id="courseId" name="courseId"></select>
    		<label class="control-label" for="sectionId">เลือก Section ที่ต้องการ</label>
    		<select id="sectionId" name="sectionId"></select>
    	</div>
	</div>
</div>
<hr>
<div class="row" id="choiceBody">
	<div class="span12">
		<div class="page-header">
			<h4>เลือกการสอบ</h4>
		</div>
		<ul class="unstyled" id="examChoice">
		</ul>
	</div>
	<div class="span12">
		<div class="page-header">
			<h4>คะแนนสอบที่แสดง</h4>
		</div>
		<ul class="unstyled">
		  	<li><label for="scoreType1"><input type="radio" value="1" id="scoreType1" checked="checked" name="scoreType">&nbsp;&nbsp;คะแนนสอบครั้งล่าสุด</label></li>
		  	<li><label for="scoreType2"><input type="radio" value="2" id="scoreType2" name="scoreType">&nbsp;&nbsp;คะแนนสอบที่สูงที่สุด</label></li>
		  	<li><label for="scoreType3"><input type="radio" value="3" id="scoreType3" name="scoreType">&nbsp;&nbsp;คะแนนเฉลี่ย</label></li>
		</ul>
	</div>
	<div class="span12">
		<div class="page-header">
			<h4>เลือก Assignment</h4>
		</div>
		<ul class="unstyled" id="assignmentChoice">
		</ul>
	</div>
</div>
<hr>
<div class="row" >
	<div class="span12">
		<button class="btn btn-info" id="viewReportButton"><i class="icon-file icon-white"></i> ดูรายงาน</button>
	</div>
</div>

<div class="modal hide fade" id="viewReportConfirmModal">
	<div class="modal-header">
		<h3>ดูรายงาน ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการดูรายงานใช่หรือไม่ โปรดยืนยัน ?
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-info" id="viewReportConfirmButton" data-loading-text="โหลดข้อมูล..." ><i class="icon-file icon-white"></i> ดูรายงาน</a>
  	</div>
</div>

<form action="${contextPath}/report/viewCustomReport.html" method="post" id="viewCustomReportForm">
	<input type="hidden" id="reportCourseId" name="courseId" />
	<input type="hidden" id="reportSectionId" name="sectionId" />
	<input type="hidden" id="examData" name="examData" />
	<input type="hidden" id="scoreChoice" name="scoreChoice" />
	<input type="hidden" id="assignmentData" name="assignmentData" />
</form>


