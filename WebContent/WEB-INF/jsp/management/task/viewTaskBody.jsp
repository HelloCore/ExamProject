<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/timepicker.css" />
<link rel="stylesheet" href="${contextPath}/css/management/task/viewTask.css">

<script>
	application.taskId = ${taskData.assignmentTaskId};
	application.courseId = ${taskData.courseId};
	application.sectionId = ${sectionData};
	application.sectionIdJSON = '${sectionData}';
	application.taskName = '${taskData.assignmentTaskName}';
	application.startDate = new Date('${taskData.startDate}');
	application.endDate = new Date('${taskData.endDate}');
	application.numOfFile = ${taskData.numOfFile};
	application.limitFileSizeKb = ${taskData.limitFileSizeKb};
	application.maxScore = ${taskData.maxScore};
</script>

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">View</font> Task</h2>
</div>
<div class="well main-form form-horizontal" id="mainForm">
	<input type="hidden" id="taskId" name="taskId" value="${taskData.assignmentTaskId}"/>
	<input type="hidden" id="oldTaskDesc" name="oldTaskDesc" value="${taskData.assignmentTaskDesc}" />
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
     			<input type="text" name="taskName" id="taskName" value="${taskData.assignmentTaskName}"/>
   			</div>
   		</div>
		<div class="control-group" id="taskDescGroup">
   			<label class="control-label" for="taskDesc">รายละเอียดงาน</label>
   			<div class="controls">
     			<textarea name="taskDesc" id="taskDesc">${taskData.assignmentTaskDesc}</textarea>
   			</div>
   		</div>
   		<div class="control-group" id="startDateGroup">
     		<label class="control-label" for="startDate">วันเริ่มส่งงาน</label>
     		<div class="controls">
     			<fmt:parseDate var="startDateTime" value="${taskData.startDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
     			
       			<input class="input-small date-check" type="text" id="startDate" name="startDate" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${startDateTime}" pattern="dd/MM/yyyy"/>">
				<input type="text" class="input-mini date-check" id="startTime" name="startTime" value="<fmt:formatDate value="${startDateTime}" pattern="HH:mm"/>"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;"></i>
     		</div>
   		</div>
		<div class="control-group"  id="endDateGroup">
    		<label class="control-label" for="endDate">วันหมดเขตส่ง</label>
     		<div class="controls">
       			<fmt:parseDate var="endDateTime" value="${taskData.endDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
     			
     			<input class="input-small date-check"  type="text" id="endDate" name="endDate" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${endDateTime}" pattern="dd/MM/yyyy"/>">
       			<input type="text" class="input-mini date-check" id="endTime" name="endTime" value="<fmt:formatDate value="${endDateTime}" pattern="HH:mm"/>"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;" id="endTimeIcon"></i>
     		</div>
   		</div>
   		<div class="control-group" id="numOfFileGroup">
   			<label class="control-label" for="numOfFile">จำกัดจำนวนไฟล์</label>
   			<div class="controls">
   				<input type="text" class="input-xmini" id="numOfFile" name="numOfFile" value="${taskData.numOfFile}">
   			</div>
   		</div>
   		<div class="control-group" id="limitFileSizeGroup">
   			<label class="control-label" for="limitFileSizeKb">จำกัดขนาดไฟล์</label>
   			<div class="controls">
   				<div class="input-append" id="limitFileSizeInput">
     				<input type="text" class="input-mini" id="limitFileSizeKb" name="limitFileSizeKb" value="${taskData.limitFileSizeKb}"><span class="add-on">KB</span>
     			</div>
   			</div>
   		</div>
   		<div class="control-group" id="maxScoreGroup">
   			<label class="control-label" for="maxScore">คะแนนเต็ม</label>
   			<div class="controls">
   				<input type="text" class="input-mini" id="maxScore" name="maxScore" value="${taskData.maxScore}">
     		</div>
   		</div>
   		<div class="control-button pagination-centered">
   			<button class="btn btn-primary" id="editTaskButton"><i class="icon-pencil icon-white"></i> Edit</button>
   		</div>
   	</div>
   	
   	
<div class="modal hide fade" id="editTaskConfirmModal">
	<div class="modal-header">
		<h3>Edit Task ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-primary" id="editTaskConfirmButton" data-loading-text="Assign..." ><i class="icon-pencil icon-white"></i> Edit</a>
  	</div>
</div>

<form class="hide" id="refreshForm" method="POST">
	<input type="hidden" name="method" value="viewTask" />
	<input type="hidden" name="taskId" value="${taskData.assignmentTaskId}" />
</form>