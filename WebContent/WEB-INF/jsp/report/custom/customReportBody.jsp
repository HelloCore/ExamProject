<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/report/customReport.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> สรุปคะแนน</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="form-inline">
    		<label class="control-label" for="courseId">เลือกวิชาที่ต้องการ </label>
    		<select id="courseId" name="courseId" class="input-medium"></select>
    		<label class="control-label" for="sectionId">เลือก Section ที่ต้องการ</label>
    		<select id="sectionId" name="sectionId" class="input-large">		
				<script id="sectionTemplate" type="text/x-jquery-tmpl">
					<optgroup label="เทอม {{= sectionSemester}} ปี {{= sectionYear}}">
						{{each sections}}
							<option value="{{= $value.sectionId}}" sectionYear="{{= sectionYear}}" sectionSemester="{{= sectionSemester}}" sectionName="{{= $value.sectionName}}">
								เทอม {{= sectionSemester}} ปี {{= sectionYear}} [{{= $value.sectionName}}]
							</option>
						{{/each}}
					</optgroup>
				</script>
    		</select>
    	</div>
	</div>
</div>
<hr>
<div class="row" id="errorSection" style="display:none;">
	<div class="alert alert-warning" style="width:300px;margin:auto;"><strong><i class="fam-error"></i> ขออภัย</strong> ไม่พบข้อมูล Section</div>
</div>
<div class="row" id="choiceBody">
	<div class="span12">
		<div class="page-header">
			<h4>เลือกการสอบ</h4>
		</div>
		<ul class="unstyled" id="examChoice">		
			<script id="examChoiceTemplate" type="text/x-jquery-tmpl">
				<li>
					<label for="exam{{= examId}}" style="display:inline-block;">
						<input type="checkbox"  class="inline-choice"  id="exam{{= examId}}" name="examId[]" value="{{= examId}}" title="{{= examHeader}}" maxScore="{{= maxScore}}">
							{{= examHeader}} 
							เริ่มสอบ 
							{{if startDate}}{{= Globalize.format(new Date(startDate),'dd-MM-yyyy HH:mm'}}{{else}} ไม่กำหนด {{/if}} 
							หมดเขตสอบ
							{{if endDate}}{{= Globalize.format(new Date(endDateDate),'dd-MM-yyyy HH:mm'}}{{else}} ไม่กำหนด {{/if}}
							คะแนนเต็ม {{= maxScore}} คะแนน
					</label>
				</li>
			</script>
		</ul>
	</div>
	<div class="span12">
		<div class="page-header">
			<h4>คะแนนสอบที่แสดง</h4>
		</div>
		<ul class="unstyled">
		  	<li><label for="scoreType1"><input class="inline-choice" type="radio" value="1" id="scoreType1" checked="checked" name="scoreType">&nbsp;&nbsp;คะแนนสอบครั้งล่าสุด</label></li>
		  	<li><label for="scoreType2"><input class="inline-choice" type="radio" value="2" id="scoreType2" name="scoreType">&nbsp;&nbsp;คะแนนสอบที่สูงที่สุด</label></li>
		  	<li><label for="scoreType3"><input class="inline-choice" type="radio" value="3" id="scoreType3" name="scoreType">&nbsp;&nbsp;คะแนนเฉลี่ย</label></li>
		</ul>
	</div>
	<div class="span12">
		<div class="page-header">
			<h4>เลือก Assignment</h4>
		</div>
		<ul class="unstyled" id="assignmentChoice">
			<script id="assignmentChoiceTemplate" type="text/x-jquery-tmpl">
				<li>
					<label for="assignment{{= assignmentTaskId}}">
						<input maxScore="{{= maxScore}}" title="{{= assignmentTaskName}}" type="checkbox" class="inline-choice" id="assignment{{= assignmentTaskId}}" name="assignmentId[]" value="{{= assignmentTaskId}}">
							{{= assignmentTaskName}}
							 เริ่มส่ง {{= Globalize.format(new Date(startDate),'dd-MM-yyyy HH:mm')}}
							 หมดเขตส่ง {{= Globalize.format(new Date(endDate),'dd-MM-yyyy HH:mm')}}
							 สถานะ 
							 {{if isEvaluateComplete}}
							 	<span class="label label-success"><i class="icon-ok icon-white"></i> ตรวจเสร็จแล้ว</span>
							 {{else}}
							 	<span class="label label-warning"><i class="icon-pencil icon-white"></i> ยังตรวจไม่เสร็จ</span>
							 {{/if}}
							 คะแนนเต็ม {{= maxScore}} คะแนน
					</label>
				</li>
			</script>
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


