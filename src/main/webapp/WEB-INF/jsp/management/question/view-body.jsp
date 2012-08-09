<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/question/view.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Question & Answer</font> Detail</h2>
	</div>
	<div class="well form-inline parent-holder">
		<input type="hidden" id="questionId" name="questionId" value="${questionData.questionId}">
		<label for="courseCode">วิชา : </label><select id="courseId" name="courseId" class="select-box" disabled="disabled"><option value="${questionData.courseId}" selected="selected">${questionData.courseCode}</option></select>
		<label for="questionGroupName">กลุ่มคำถาม : </label><select  id="questionGroupId" name="questionGroupId" class="select-box" disabled="disabled"><option value="${questionData.questionGroupId}" selected="selected">${questionData.questionGroupName}</option></select>
		<button class="btn btn-info" id="editParentButton"><i class="icon-edit icon-white"></i> Edit</button>
		<button class="btn btn-primary hide" id="saveParentButton">Save</button>
		<button class="btn hide" id="cancelParentButton">Cancel</button>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#question" data-toggle="tab">Question</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane active" id="questionHolder">
			<div class="question-panel">${questionData.questionText}</div>
		  	<div class="pagination-centered">
		  		<button id="editQuestionButton" class="btn btn-info"><i class="icon-edit icon-white"></i> Edit</button>
		  		<button id="addAnswerButton" class="btn btn-primary"><i class="icon-plus icon-white"></i> Add Answer</button>
		  	</div>
		</div>
	</div>
	<ul class="nav nav-tabs" id="answerTabNav">
		<c:forEach items="${answerData}" var="data">
			<li id="answer-menu-${data.answerId}"><a href="#answer-${data.answerId}" data-toggle="tab">Answer</a></li>	
		</c:forEach>
	</ul>
	<div class="tab-content" id="answerTabContent">
		<c:forEach items="${answerData}" var="data">
			<div class="tab-pane active" id="answer-${data.answerId}">
				<div id="answer-panel-${data.answerId}">${data.answerText}</div>
		  		<div class="pagination-centered">
			  		<font>เป็นคำตอบที่ถูกต้อง : </font>
			  		<font id="answer-score-text-${data.answerId}">
			  			<c:if test="${data.answerScore==0}">
							ไม่ใช่
						</c:if>
						<c:if test="${data.answerScore==1}">
							ใช่
						</c:if>
					</font>
			  	</div>
			  	<div class="pagination-centered button-group">
			  		<input type="hidden" id="answer-score-${data.answerId}" value="${data.answerScore}">
					<button class="btn btn-info" onclick="editAnswer(${data.answerId})"><i class="icon-edit icon-white"></i> Edit</button>
			  		<button class="btn btn-danger" onclick="deleteAnswer(${data.answerId})"><i class="icon-trash icon-white"></i> Delete</button>
			  	</div>
		  	</div>
	  </c:forEach>
	</div>
	<div class="tab-footer pagination-centered">
		<label for="numOfCorrentAnswer">มีคำตอบที่ถูกต้อง </label><span id="numOfCorrectAnswer" class="input-mini uneditable-input">0</span><label for="numOfCorrentAnswer">คำตอบ</label>
		และ <label for="numOfFoolAnswer">มีคำตอบหลอก </label><span id="numOfFoolAnswer" class="input-mini uneditable-input">0</span><label for="numOfFoolAnswer">คำตอบ</label>
	</div>
</div>


<div class="modal hide fade modal-with-editor" id="editQuestionModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Edit Question</h3>
  </div>
  <div class="modal-body">
    <textarea id="questionText" name="questionText" class="ckeditorText"></textarea>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="saveQuestionButton" data-loading-text="กำลังบันทึก...">Save</a>
  </div>
</div>

<div class="modal hide fade modal-with-editor" id="editAnswerModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Edit Question</h3>
  </div>
  <div class="modal-body">
  	<input type="hidden" id="answerId" name="answerId">
    <textarea id="answerText" name="answerText" class="ckeditorText"></textarea>
  </div>
  <div class="modal-footer">
  	<div class="pull-left form-inline score-holder">
  		<font>เป็นคำตอบที่ถูกต้อง : </font> 
    	<input type="radio" name="answerScore" id="answerScoreCorrect" value="1" /><label for="answerScoreCorrect">ใช่</label>
    	<input type="radio" name="answerScore" id="answerScoreFool" value="0" /><label for="answerScoreFool">ไม่ใช่</label>
  	</div>
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="saveAnswerButton" data-loading-text="กำลังบันทึก...">Save</a>
  </div>
</div>

<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>Delete Answer ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Cancel</a>
    	<a href="#" class="btn btn-danger" id="deleteAnswerButton" data-loading-text="Deleting..." >Delete</a>
  	</div>
</div>




