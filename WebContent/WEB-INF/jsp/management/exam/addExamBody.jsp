<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/timepicker.css" />
<link rel="stylesheet" href="${contextPath}/css/management/exam/addExam.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">สร้าง </font> กำหนดการสอบ</h2>
	</div>
	
	<div class="well main-form">
		<div id="tab1" class="tab-content form-horizontal tab-overflow">
			<div class="control-group">
      			<label class="control-label" for="courseId">วิชา</label>
      			<div class="controls">
        			<select id="courseId" name="courseId" >
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
			<div class="control-group" id="examLimitGroup">
      			<label class="control-label" for="examLimit">จำนวนครั้งที่สอบได้</label>
      			<div class="controls">
        			<input type="text" class="input-xmini" id="examLimit" name="examLimit" value="1">
      			</div>
    		</div>
	   		<div class="control-button pagination-centered">
	   				<button class="btn btn-primary" id="tab1NextButton">Next <i class="icon-chevron-right icon-white"></i></button>
	   		</div>
		</div>
		<div id="tab2" class="tab-content form-horizontal hide tab-overflow">
			<div class="control-group" id="questionGroup">
      			<label class="control-label" for="questionGroupId">กลุ่มคำถาม</label>
      			<div class="controls">
        			<select id="questionGroupId" name="questionGroupId" multiple="multiple">
        			</select>
      			</div>
    		</div>
			<div class="control-group" id="sectionGroup">
      			<label class="control-label" for="sectionId">Section ที่มีสิทธิ์สอบ</label>
      			<div class="controls">
        			<select id="sectionId" name="sectionId" multiple="multiple">
        			</select>
      			</div>
    		</div>
	   		<div class="control-button pagination-centered">
	   				<button class="btn" id="tab2BackButton"><i class="icon-chevron-left"></i> Back</button>
	   				<button class="btn btn-primary" id="tab2NextButton">Next <i class="icon-chevron-right icon-white"></i></button>
	   		</div>
		</div>
		<div id="tab3" class="tab-content tab-overflow hide">
			<div id="orderHolder" class="order-holder pagination-centered">
				<ul id="questionGroupSort">
				</ul>
			</div>
			<div class="pagination-centered display-inline">
				<font>การเรียงคำถาม : </font><input type="radio" name="examSequence" id="examSequenced" value="1"><label for="examSequenced">เรียงตามกำหนด</label><input type="radio" name="examSequence" id="examRandom" value="0" checked="checked"><label for="examRandom">สุ่ม</label>
				<br>
				<label for="totalPercent">เปอร์เซ็นต์รวม </label><span class="input-mini uneditable-input error" id="totalPercent">0</span><label for="totalPercent" > % </label>
			</div>
	   		<div class="control-button pagination-centered">
	   			<button class="btn" id="tab3BackButton"><i class="icon-chevron-left"></i> Back</button>
	   			<button class="btn btn-primary" id="tab3NextButton">Next <i class="icon-chevron-right icon-white"></i></button>
	   		</div> 
		</div>
		<div id="tab4" class="tab-content form-horizontal tab-overflow hide">
			<div class="control-group">
      			<label class="control-label" for="courseIdConfirm">วิชา</label>
      			<div class="controls">
      				<span class="input-medium uneditable-input" id="courseIdConfirm"></span>
      			</div>
    		</div>
			<div class="control-group">
      			<label class="control-label" for="examHeaderConfirm">หัวข้อ</label>
      			<div class="controls">
      				<span class="input-medium uneditable-input" id="examHeaderConfirm"></span>
      			</div>
    		</div>
			<div class="control-group">
      			<label class="control-label" for="startDateConfirm">วันเริ่มสอบ</label>
      			<div class="controls">
      				<span class="input-medium uneditable-input" id="startDateConfirm"></span>
      			</div>
    		</div>
			<div class="control-group">
      			<label class="control-label" for="endDateConfirm">วันหมดเขตสอบ</label>
      			<div class="controls">
      				<span class="input-medium uneditable-input" id="endDateConfirm"></span>
      			</div>
    		</div>
			<div class="control-group">
      			<label class="control-label" for="rangeQuestionConfirm">จำนวนข้อสอบ</label>
      			<div class="controls">
      				<span class="input-small uneditable-input" id="rangeQuestionConfirm"></span>
      			</div>
    		</div>
			<div class="control-group">
      			<label class="control-label" for="examLimitConfirm">จำนวนครั้งที่สอบได้</label>
      			<div class="controls">
      				<span class="input-mini uneditable-input" id="examLimitConfirm"></span>
      			</div>
    		</div>
			<div class="control-group">
      			<label class="control-label" for="examSequenceConfirm">การเรียงคำถาม</label>
      			<div class="controls">
      				<span class="input-medium uneditable-input" id="examSequenceConfirm"></span>
      			</div>
    		</div>
    		<div class="question-group-div pagination-centered">
    			<h2>กลุ่มคำถาม</h2>
    			<table class="table table-striped table-bordered question-group-table">
    				<thead>
    					<tr>
    						<th>ลำดัับ</th>
    						<th>กลุ่มคำถาม</th>
    						<th>เปอร์เซ็นต์คำถาม</th>
    						<th>เวลาสอบ(วินาที ต่อ1ข้อ)</th>
    					</tr>
    				</thead>
    				<tbody>
    				</tbody>
    			</table> 
    		</div>
    		<div class="section-div pagination-centered">
    			<h2>Section ที่มีสิทธิ์สอบ</h2>
    			<table class="table table-striped table-bordered section-table">
    				<thead>
    					<tr>
    						<th>เทอม</th>
    						<th>ปี</th>
    						<th>Section</th>
    					</tr>
    				</thead>
    				<tbody>
    				</tbody>
    			</table> 
    		</div>
	   		<div class="control-button pagination-centered">
	   			<button class="btn" id="tab4BackButton" data-loading-text="Back"><i class="icon-chevron-left"></i> Back</button>
	   			<button class="btn btn-primary" id="tab4NextButton" data-loading-text="กำลังสร้าง..."><i class="icon-ok icon-white"></i> Add</button>
	   		</div> 
    		
		</div>
	</div>
</div>
