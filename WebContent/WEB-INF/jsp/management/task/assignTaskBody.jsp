<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/timepicker.css" />
<link rel="stylesheet" href="${contextPath}/css/management/task/assignTask.css">
		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">สั่ง</font> Assignment</h2>
</div>
<div class="well main-form form-horizontal" id="mainForm">
		<div class="control-group" id="courseIdGroup">
   			<label class="control-label" for="courseId">วิชา</label>
   			<div class="controls">
     			<select id="courseId" name="courseId" ></select>
   			</div>
   		</div>
		<div class="control-group" id="sectionIdGroup">
   			<label class="control-label" for="sectionId">Section</label>
   			<div class="controls">
     			<select id="sectionId" name="sectionId" multiple="multiple"></select>
   			</div>
   		</div>
		<div class="control-group" id="taskNameGroup">
   			<label class="control-label" for="taskName">หัวข้องาน</label>
   			<div class="controls">
     			<input type="text" name="taskName" id="taskName" />
   			</div>
   		</div>
		<div class="control-group" id="taskDescGroup">
   			<label class="control-label" for="taskDesc">รายละเอียดงาน</label>
   			<div class="controls">
     			<textarea name="taskDesc" id="taskDesc"></textarea>
   			</div>
   		</div>
   		<div class="control-group" id="startDateGroup">
     		<label class="control-label" for="startDate">วันเริ่มส่งงาน</label>
     		<div class="controls">
       			<input class="input-small date-check" type="text" id="startDate" name="startDate" data-date-format="dd/mm/yyyy">
				<input type="text" class="input-mini date-check" id="startTime" name="startTime" value="00:00"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;"></i>
     		</div>
   		</div>
		<div class="control-group"  id="endDateGroup">
    		<label class="control-label" for="endDate">วันหมดเขตส่ง</label>
     		<div class="controls">
       			<input class="input-small date-check"  type="text" id="endDate" name="endDate" data-date-format="dd/mm/yyyy">
       			<input type="text" class="input-mini date-check" id="endTime" name="endTime" value="00:00"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;" id="endTimeIcon"></i>
     		</div>
   		</div>
   		<div class="control-group" id="numOfFileGroup">
   			<label class="control-label" for="numOfFile">จำกัดจำนวนไฟล์</label>
   			<div class="controls">
   				<input type="text" class="input-xmini" id="numOfFile" name="numOfFile" value="1">
   			</div>
   		</div>
   		<div class="control-group" id="limitFileSizeGroup">
   			<label class="control-label" for="limitFileSizeKb">จำกัดขนาดไฟล์</label>
   			<div class="controls">
   				<div class="input-append" id="limitFileSizeInput">
     				<input type="text" class="input-mini" id="limitFileSizeKb" name="limitFileSizeKb" value="1024"><span class="add-on">KB</span>
     			</div>
   			</div>
   		</div>
   		<div class="control-group" id="maxScoreGroup">
   			<label class="control-label" for="maxScore">คะแนนเต็ม</label>
   			<div class="controls">
   				<input type="text" class="input-mini" id="maxScore" name="maxScore" value="100">
     		</div>
   		</div>
   		<div class="control-button pagination-centered">
   			<button class="btn btn-primary" id="assignTaskButton"><i class="icon-pencil icon-white"></i> สั่ง Assignment</button>
   		</div>
   	</div>
   	
   	
<div class="modal hide fade" id="assignTaskConfirmModal">
	<div class="modal-header">
		<h3>คุณต้องการสั่ง Assignment ใช่หรือไม่ ?</h3>
	</div>
	<div class="modal-body">
		โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-primary" id="assignTaskConfirmButton" data-loading-text="กำลังบันทึก..." ><i class="icon-pencil icon-white"></i> สั่ง Assignment</a>
  	</div>
</div>