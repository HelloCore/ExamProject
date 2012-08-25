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
		<h2><font class="red-color">View </font> Examinations</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="span2">
				<div class="tabbable tabs-left">
					<ul class="nav nav-tabs">
						<li class="active"><a href='#tab1' data-toggle="tab">ข้อมูลการสอบ</a></li>
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
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label" for="courseCode">รหัสวิชา :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<input class="span9 " id="courseCode" type="text" value="${examData.course.courseCode}" disabled="disabled"/><button class="btn btn-info" disabled="disabled"><i class="icon-ban-circle icon-white"></i> Edit</button>
									</div>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examHeader">หัวข้อการสอบ :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<input id="examHeader" class="span9" size="20" name="examHeader" type="text" value="${examData.examHeader}" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examStartDate">วันเริ่มสอบ :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<input id="startDate" class="span9" size="20" name="startDate" type="text" value="" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examEndDate">วันหมดเขตสอบ :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<input id="endDate" class="span9" size="20" name="endDate" type="text" value="" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="numOfQuestion">จำนวนคำถาม :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<input id="numOfQuestion" class="span9" size="20" name="numOfQuestion" type="text" value="${examData.minQuestion} ถึง ${examData.maxQuestion} ข้อ" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examLimit">สอบได้ :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<input id="examLimit" class="span9" size="20" name="examLimit" type="text" value="${examData.examLimit} ครั้ง" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
									</div>
							    </div>
							</div>
							<div class="control-group">
								<label class="control-label" for="examSequence">การเรียงคำถาม :</label>
								<div class="controls controls-row">
									<div class="input-append span11">
										<c:if test="${examData.examSequence==true}">
											<input id="examSequence" class="span9" size="20" name="examSequence" type="text" value="เรียงตามลำดับ" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
										</c:if>
										<c:if test="${examData.examSequence==false}">
											<input id="examSequence" class="span9" size="20" name="examSequence" type="text" value="สุ่ม" disabled="disabled"/><button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
										</c:if>
									</div>
							    </div>
							</div>
						</form>
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
									<td colspan="4" class="center-row">
										<button class="btn btn-info" id="addQuestionGroupButton"><i class="icon-plus icon-white"></i> Add</button>
										<button class="btn btn-primary" id="saveEditQuestionGroupButton"><i class="icon-pencil icon-white"></i> Save</button>
										<button class="btn" id="cancelEditQuestionGroupButton">Cancel</button>
										<span class="help-inline">เปอร์เซ็นต์รวม </span> <span class="uneditable-input" id="totalPercent"> 100</span> <span class="help-inline">%</span>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab-pane" id="tab3">
						<h3>Section ที่มีสิทธิ์สอบ</h3>
						<hr>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>Section</th>
									<th>เทอม</th>
									<th>ปี</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>010</td>
									<td>1</td>
									<td>2555</td>
								</tr>
								<tr>
									<td>2</td>
									<td>020</td>
									<td>1</td>
									<td>2555</td>
								</tr>
								<tr>
									<td>3</td>
									<td>030</td>
									<td>1</td>
									<td>2555</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4" class="center-row">
										<button class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
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

<div class="modal hide fade" id="questionGroupModal" tabindex="-1" role="dialog">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Choose Question Group</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchSectionForm">	
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
