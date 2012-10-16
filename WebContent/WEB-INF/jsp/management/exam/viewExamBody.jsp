<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/datepicker.css" />
<link rel="stylesheet" href="${contextPath}/resources/datepicker/timepicker.css" />
<link rel="stylesheet" href="${contextPath}/css/management/exam/viewExam.css">
<script>
	application.questionGroupData = ${questionGroupData};
	application.sectionData = ${sectionData};
	application.examQuestionGroupData = ${examQuestionGroupData};
	application.examSectionData = ${examSectionData};
	
	application.exam = {};
	application.exam.examId = ${examData.examId};
	application.exam.examHeader = '${examData.examHeader}';
	application.exam.startDateStr = '${examData.startDate}';
	application.exam.endDateStr = '${examData.endDate}';
	application.exam.examLimit = ${examData.examLimit};
	application.exam.minQuestion = ${examData.minQuestion};
	application.exam.maxQuestion = ${examData.maxQuestion};
	application.exam.courseId = ${examData.courseId};
	application.exam.examSequence = ${examData.examSequence};
	if(application.exam.startDateStr == ''){
		application.exam.startDate = null;
	}else{
		application.exam.startDate = new Date(application.exam.startDateStr);
	}
	if(application.exam.endDateStr == ''){
		application.exam.endDate = null;
	}else{
		application.exam.endDate = new Date(application.exam.endDateStr);
	}
</script>
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">รายละเอียด </font> การสอบ</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="span2">
					<div class="tabbable">
						<ul class="nav nav-list">
							<li class="nav-header">Menu</li>
							<li class="active"><a href='#tab1' data-toggle="tab">การสอบ</a></li>
							<li><a href='#tab2' data-toggle="tab">กลุ่มคำถาม</a></li>
							<li><a href='#tab3' data-toggle="tab">Section</a></li>					
						</ul>
					</div>
			</div>
			<div class="span10">
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<h3>ข้อมูลการสอบ</h3>
						<hr>
						<div class="form-horizontal">
							<div class="control-group">
								<label class="control-label" for="courseCode">รหัสวิชา :</label>
								<div class="controls">
									<input class="span7" size="25"  id="courseCode" type="text" value="${examData.course.courseCode}" disabled="disabled"/>
									<button class="btn btn-info" disabled="disabled"><i class="icon-ban-circle icon-white"></i> Edit</button>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examHeader">หัวข้อการสอบ :</label>
								<div class="controls" id="examHeaderControls">
									<input id="examHeader" class="span7" size="25" name="examHeader" type="text" value="${examData.examHeader}" disabled="disabled"/>
									<button class="btn btn-info normal-header-button" id="editExamHeaderButton"><i class="icon-edit icon-white"></i> Edit</button>
									<div class="wrapper edit-header-button" id="editExamHeaderButtonGroup" style="display:none;">
										<button class="btn btn-primary" id="saveExamHeaderButton"><i class="icon-pencil icon-white"></i> Save</button>
										<button class="btn " id="cancelExamHeaderButton"> Cancel</button>
									</div>
								</div>
							</div>
							<div class="control-group" id="startDateGroup">
								<label class="control-label" for="examStartDate">วันเริ่มสอบ :</label>
								<div class="controls" id="examStartDateControls">
									<div class="normal-start-date-button edit-start-date">
										<input id="startDate" class="span7" size="25" name="startDate" type="text" value="" disabled="disabled"/>
										<button class="btn btn-info" id="editStartDateButton"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
									<div class="edit-start-date-button edit-start-date hide">
										<div class="input-prepend" class="span7">
					        				<span class="add-on"><input type="checkbox" id="useStartDate" class="date-check" /></span><input class="input-small date-check date-box" type="text" id="editStartDate" name="editStartDate" data-date-format="dd/mm/yyyy">
										</div>
					        			<input type="text" class="input-mini date-check time-box" id="editStartTime" name="editStartTime" value="00:00"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;"></i>
					        			<div class="wrapper" id="editStartDateButtonGroup">
											<button class="btn btn-primary save-start-date-button" id="saveStartDateButton"><i class="icon-pencil icon-white"></i> Save</button>
											<button class="btn" id="cancelStartDateButton"> Cancel</button>
										</div>
									</div>
							    </div>
							</div>
							<div class="control-group" id="endDateGroup">
								<label class="control-label" for="examEndDate">วันหมดเขตสอบ :</label>
								<div class="controls" id="examEndDateControls">
									<div class="normal-end-date-button edit-end-date">
										<input id="endDate" class="span7" size="25" name="endDate" type="text" value="" disabled="disabled"/>
										<button class="btn btn-info " id="editEndDateButton"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
									<div class="edit-end-date-button edit-end-date hide">
										<div class="input-prepend" class="span7">
					        				<span class="add-on"><input type="checkbox" id="useEndDate" class="date-check" /></span><input class="input-small date-check date-box" type="text" id="editEndDate" name="editEndDate" data-date-format="dd/mm/yyyy">
										</div>
					        			<input type="text" class="input-mini date-check time-box" id="editEndTime" name="editEndTime" value="00:00"><i class="icon-time" style="margin: 2px 0 0 -22.5px; pointer-events: none; position: relative;"></i>
					        			<div class="wrapper" id="editEndDateButtonGroup">
											<button class="btn btn-primary save-end-date-button" id="saveEndDateButton"><i class="icon-pencil icon-white"></i> Save</button>
											<button class="btn" id="cancelEndDateButton"> Cancel</button>
										</div>
									</div>
							    </div>
							</div>
							<div class="control-group" id="numOfQuestionGroup">
								<label class="control-label" for="numOfQuestion">จำนวนคำถาม :</label>
								<div class="controls" id="numOfQuestionControls">
									<div class="normal-num-of-question-button edit-num-of-question">
										<input id="numOfQuestion" class="span7" size="25" name="numOfQuestion" type="text" value="${examData.minQuestion} ถึง ${examData.maxQuestion} ข้อ" disabled="disabled"/>
										<button class="btn btn-info " id="editNumOfQuestionButton"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
									<div class="edit-num-of-question-button edit-num-of-question hide">
										<input type="text" class="input-mini input-num-of-question" id="minQuestion" name="minQuestion" />
										<label for="maxQuestion" style="display:inline-block;"> ถึง </label>
										<input type="text" class="input-mini input-num-of-question" id="maxQuestion" name="maxQuestion" />
										<label for="maxQuestion" style="display:inline-block;"> ข้อ </label>
										<div class="wrapper" id="editNumOfQuestionButtonGroup">
											<button class="btn btn-primary save-num-of-question-button" id="saveNumOfQuestionButton"><i class="icon-pencil icon-white"></i> Save</button>
											<button class="btn" id="cancelNumOfQuestionButton"> Cancel</button>
										</div>
									</div>
							    </div>
							</div>
							<div class="control-group" id="examLimitGroup">
								<label class="control-label" for="examLimit">สอบได้ :</label>
								<div class="controls">
									<input id="examLimit" class="span7" size="25" name="examLimit" type="text" value="${examData.examLimit} ครั้ง" disabled="disabled"/>
									<button class="btn btn-info normal-exam-limit-button" id="editExamLimitButton"><i class="icon-edit icon-white"></i> Edit</button>
									<div class="wrapper edit-exam-limit-button" id="editExamLimitButtonGroup" style="display:none;">
										<button class="btn btn-primary save-exam-limit-button" id="saveExamLimitButton"><i class="icon-pencil icon-white"></i> Save</button>
										<button class="btn" id="cancelExamLimitButton"> Cancel</button>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examSequence">การเรียงคำถาม :</label>
								<div class="controls normal-exam-sequence-button" >
									<input id="examSequenceStr" class="span7" size="25" name="examSequenceStr" type="text" disabled="disabled" />
									<button class="btn btn-info" id="editExamSequenceButton"><i class="icon-edit icon-white"></i> Edit</button>
								</div>
								<div class="controls edit-exam-sequence-button" style="display:none;" >
									<input type="radio" id="examRandom" name="examSequence" value="0" class="sequence-radio"/>
									<label for="examRandom" class="sequence-radio"> สุ่ม </label>
									<input type="radio" id="examSequence" name="examSequence" value="1" class="sequence-radio"/>
									<label for="examSequence" class="sequence-radio"> เรียงตามลำดับ </label>
									<div class="wrapper" id="editExamSequenceButtonGroup">
										<button class="btn btn-primary save-exam-sequence-button" id="saveExamSequenceButton"><i class="icon-pencil icon-white"></i> Save</button>
										<button class="btn" id="cancelExamSequenceButton"> Cancel</button>
									</div>
							    </div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tab2">
						<h3>กลุ่มคำถาม</h3>
						<hr>
						<table class="table table-striped table-bordered editable-question">
							<thead>
								<tr>
									<th>#</th>
									<th>กลุ่มคำถาม</th>
									<th>% คำถาม</th>
									<th>เวลา (วินาที)</th>
								</tr>
							</thead>
							<tbody >
								
							</tbody>
							<tfoot>
								<tr class="normal-panel" id="normal-question-group-panel">
									<td colspan="4" class="center-row">
										<button class="btn btn-info" id="editQuestionGroupButton"><i class="icon-edit icon-white"></i> Edit</button>
									</td>
								</tr>
								<tr class="edit-panel" id="edit-question-group-panel"> 
									<td colspan="5" class="center-row">
										<button class="btn btn-info" id="addQuestionGroupButton"><i class="icon-plus icon-white"></i> Add</button>
										<button class="btn btn-primary" id="saveEditQuestionGroupButton"><i class="icon-pencil icon-white"></i> Save</button>
										<button class="btn" id="cancelEditQuestionGroupButton">Cancel</button>
										<div class="wrapper">
											<span class="help-inline">เปอร์เซ็นต์รวม </span> <span class="uneditable-input" id="totalPercent"> 100</span> <span class="help-inline">%</span>
										</div>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab-pane" id="tab3">
						<h3>Section ที่มีสิทธิ์สอบ</h3>
						<hr>
						<table class="table table-striped editable-section">
							<thead>
								<tr>
									<th>#</th>
									<th>Section</th>
									<th>เทอม</th>
									<th>ปี</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
							<tfoot>
								<tr class="normal-panel" id="normal-section-panel">
									<td colspan="4" class="center-row" >
										<button class="btn btn-info" id="editSectionButton"><i class="icon-edit icon-white"></i> Edit</button>
									</td>
								</tr>
								<tr class="edit-panel" id="edit-section-panel"> 
									<td colspan="5" class="center-row">
										<button class="btn btn-info" id="addSectionButton"><i class="icon-plus icon-white"></i> Add</button>
										<button class="btn btn-primary" id="saveEditSectionButton"><i class="icon-pencil icon-white"></i> Save</button>
										<button class="btn" id="cancelEditSectionButton">Cancel</button>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal hide fade" id="questionGroupModal" tabindex="-1" >
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Choose Question Group</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchQuestionGroupForm">	
    	<div class="control-group">
	     	<label class="control-label" for="questionGroupId">Course Code</label>
	      	<div class="controls">
	        	<select id="questionGroupId" class="input-medium" data-placeholder="Choose a question group..." name="questionGroupId"></select>
	        </div>
	    </div>
	  </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-info" id="addQuestionGroupConfirmButton" ><i class="icon-plus icon-white"></i> Add </a>
  </div>
</div>

<div class="modal hide fade" id="sectionModal" tabindex="-1" >
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Choose Section</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchSectionForm">	
    	<div class="control-group">
	     	<label class="control-label" for="sectionId">Section</label>
	      	<div class="controls">
	        	<select id="sectionId" class="input-medium" data-placeholder="Choose a section..." name="sectionId"></select>
	        </div>
	    </div>
	  </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-info" id="addSectionConfirmButton" ><i class="icon-plus icon-white"></i> Add </a>
  </div>
</div>

<div class="modal hide fade" id="confirmGenericModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Confirm Edit ?</h3>
  </div>
  <div class="modal-body">
    <p  id="genericModalBody"></p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="confirmGenericButton" data-loading-text="กำลังแก้ไข..."><i class="icon-pencil icon-white"></i> Confirm</a>
  </div>
</div>

<div class="modal hide fade" id="confirmQuestionGroupModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Confirm Edit Question Group?</h3>
  </div>
  <div class="modal-body">
    <p>ต้องการแก้ไขกลุ่มคำถามใช่หรือไม่</p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="confirmQuestionGroupButton" data-loading-text="กำลังแก้ไข..."><i class="icon-pencil icon-white"></i> Confirm</a>
  </div>
</div>


<div class="modal hide fade" id="confirmSectionModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Confirm Edit Section?</h3>
  </div>
  <div class="modal-body">
    <p>ต้องการแก้ไข Section ที่มีสิทธิ์สอบใช่หรือไม่</p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="confirmSectionButton" data-loading-text="กำลังแก้ไข..."><i class="icon-pencil icon-white"></i> Confirm</a>
  </div>
</div>

	