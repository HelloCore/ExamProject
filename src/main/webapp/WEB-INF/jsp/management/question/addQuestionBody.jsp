<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/question/addQuestion.css">
<div>
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Add</font> Question</h2>
	</div>
	<div class="pagging-menu" id="menuHeader">
		<label for="courseId">วิชา&nbsp;:&nbsp;</label><select id="courseId" name="courseId" style="width:220px"></select>
		<label for="questionGroupId">กลุ่มคำถาม&nbsp;:&nbsp;</label><select id="questionGroupId" name="questionGroupId" style="width:220px"></select>
		<button class="btn btn-info" id="addAnswer"><i class="icon-plus icon-white"></i> Add Answer</button>
	</div>
	<ul class="nav nav-tabs" id="questionTabNav">
		<li class="active"><a href="#question-tab" data-toggle="tab">Question</a></li>
		<li><a href="#answer-tab1" data-toggle="tab">Answer</a></li>
		<li><a href="#answer-tab2" data-toggle="tab">Answer</a></li>
		<li><a href="#answer-tab3" data-toggle="tab">Answer</a></li>
		<li><a href="#answer-tab4" data-toggle="tab">Answer</a></li>
	</ul>
	<div class="tab-content" id="questionTabContent">
		<div class="tab-pane active" id="question-tab">
			<table class="table-center">
				<tr>
					<td align="center">
						<textarea id="questionTextArea" name="questionTextArea" class="ckeditorarea"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab-pane" id="answer-tab1">
		 	<table class="table-center">
				<tr>
					<td colspan="2"><textarea id="answerTextArea1" name="answerTextArea1" class="ckeditorarea"></textarea></td>
				</tr><tr>
					<td align="right" width="130">เป็นคำตอบที่ถูกต้อง&nbsp;:&nbsp;</td>
					<td align="left" >
						<input type="radio" id="answerScoreCorrect1" name="answerScore1" value="1"><label for="answerScoreCorrect1">&nbsp;ใช่&nbsp;</label>
						<input type="radio" id="answerScoreFool1" name="answerScore1" value="0" checked="checked"><label for="answerScoreFool1">&nbsp;ไม่ใช่&nbsp;</label>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab-pane" id="answer-tab2">
		 	<table class="table-center">
				<tr>
					<td colspan="2"><textarea id="answerTextArea2" name="answerTextArea2" class="ckeditorarea"></textarea></td>
				</tr><tr>
					<td align="right" width="130">เป็นคำตอบที่ถูกต้อง&nbsp;:&nbsp;</td>
					<td align="left" >
						<input type="radio" id="answerScoreCorrect2" name="answerScore2" value="1"><label for="answerScoreCorrect2">&nbsp;ใช่&nbsp;</label>
						<input type="radio" id="answerScoreFool2" name="answerScore2" value="0" checked="checked"><label for="answerScoreFool2">&nbsp;ไม่ใช่&nbsp;</label>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab-pane" id="answer-tab3">
		 	<table class="table-center">
				<tr>
					<td colspan="2"><textarea id="answerTextArea3" name="answerTextArea3" class="ckeditorarea"></textarea></td>
				</tr><tr>
					<td align="right" width="130">เป็นคำตอบที่ถูกต้อง&nbsp;:&nbsp;</td>
					<td align="left" >
						<input type="radio" id="answerScoreCorrect3" name="answerScore3" value="1"><label for="answerScoreCorrect3">&nbsp;ใช่&nbsp;</label>
						<input type="radio" id="answerScoreFool3" name="answerScore3" value="0" checked="checked"><label for="answerScoreFool3">&nbsp;ไม่ใช่&nbsp;</label>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab-pane" id="answer-tab4">
		 	<table class="table-center">
				<tr>
					<td colspan="2"><textarea id="answerTextArea4" name="answerTextArea4" class="ckeditorarea"></textarea></td>
				</tr><tr>
					<td align="right" width="130">เป็นคำตอบที่ถูกต้อง&nbsp;:&nbsp;</td>
					<td align="left" >
						<input type="radio" id="answerScoreCorrect4" name="answerScore4" value="1"><label for="answerScoreCorrect4">&nbsp;ใช่&nbsp;</label>
						<input type="radio" id="answerScoreFool4" name="answerScore4" value="0" checked="checked"><label for="answerScoreFool4">&nbsp;ไม่ใช่&nbsp;</label>
					</td>
				</tr>
			</table>
		</div>
	</div>	
	<div class="footer-panel">
		<font class="numOfCorrectAnswerText">มีคำตอบที่ถูกต้อง : </font><input type="text" name="numOfCorrectAnswer" id="numOfCorrectAnswer" value="0" class="numOfCorrectAnswerText labelTextBox" size="2" readonly="readonly"><font class="numOfCorrectAnswerText"> คำตอบ </font>
		และ <font class="numOfFoolAnswerText">มีคำตอบหลอก : </font><input type="text" name="numOfFoolAnswer" id="numOfFoolAnswer" value="4" class="numOfFoolAnswerText labelTextBox" size="2" readonly="readonly"><font class="numOfFoolAnswerText"> คำตอบ</font>
		<button id="createQuestionButton" class="btn btn-primary" ><i class="icon-pencil icon-white"></i> เพิ่มข้อสอบ</button>
	</div>
</div>


<div class="modal hide fade" id="confirmModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Confirm Add Question ?</h3>
  </div>
  <div class="modal-body">
    <p>ต้องการเพิ่มข้อสอบใช่หรือไม่</p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="confirmButton" data-loading-text="กำลังเพิ่ม..."><i class="icon-pencil icon-white"></i> Confirm</a>
  </div>
</div>