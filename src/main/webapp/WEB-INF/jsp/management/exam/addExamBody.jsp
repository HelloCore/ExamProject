<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/timepicker.css" />
<link rel="stylesheet" href="${contextPath}/css/management/exam/addExam.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Add </font> Exam</h2>
	</div>
	
	<div class="well main-form">
		<div id="tab1" class="tab-content ">
			<form id="tab1Form" class="form-horizontal">
				<div class="control-group">
	      			<label class="control-label" for="courseId">วิชา</label>
	      			<div class="controls">
	        			<select id="courseId" name="courseId">
	        				<option value="1">CS.101</option>
	        				<option value="2">CS.102</option>
	        			</select>
	      			</div>
	    		</div>
				<div class="control-group">
	      			<label class="control-label" for="examHeader">หัวข้อ</label>
	      			<div class="controls">
	        			<input type="text" class="input-medium" id="examHeader" name="examHeader" placeholder="5 - 30 ตัวอักษร">
	      			</div>
	    		</div>
				<div class="control-group" id="startDateGroup">
	      			<label class="control-label" for="startDate">วันเริ่มสอบ</label>
	      			<div class="controls">
	        			<div class="input-prepend">
	        				<span class="add-on"><input type="checkbox" id="useStartDate" class="date-check" /></span><input class="input-small date-check" type="text" id="startDate" name="startDate" data-date-format="dd/mm/yyyy">
						</div>
	        			<input type="text" class="input-mini date-check" id="startTime" name="startTime" value="00:00"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;"></i>
	      			</div>
	    		</div>
				<div class="control-group"  id="endDateGroup">
	      			<label class="control-label" for="endDate">วันหมดเขตสอบ</label>
	      			<div class="controls">
	        			<div class="input-prepend">
	        				<span class="add-on"><input type="checkbox" id="useEndDate" class="date-check" /></span><input class="input-small date-check"  type="text" id="endDate" name="endDate" data-date-format="dd/mm/yyyy">
	        			</div>
	        			<input type="text" class="input-mini date-check" id="endTime" name="endTime" value="00:00"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;" id="endTimeIcon"></i>
	      			</div>
	    		</div>
				<div class="control-group" id="numQuestionGroup">
	      			<label class="control-label" for="minQuestion">จำนวนข้อสอบ</label>
	      			<div class="controls">
	        			<input type="text" class="input-xmini num-question" id="minQuestion" name="minQuestion">
	        			<font>ถึง</font>
	        			<input type="text" class="input-xmini num-question" id="maxQuestion" name="maxQuestion">
	      			</div>
	    		</div>
				<div class="control-group" id="testCountGroup">
	      			<label class="control-label" for="testCount">จำนวนครั้งที่สอบได้</label>
	      			<div class="controls">
	        			<input type="text" class="input-xmini" id="testCount" name="testCount" value="1">
	      			</div>
	    		</div>
    		</form>
    		<div class="control-button pagination-centered">
    				<button class="btn btn-primary">Next <i class="icon-chevron-right icon-white"></i></button>
    		</div>
		</div>
	</div>
</div>
