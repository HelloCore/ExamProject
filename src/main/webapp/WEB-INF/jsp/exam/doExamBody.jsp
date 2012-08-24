<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<script>
	application.expireDate = ${expireDate};
	application.examResultId = ${examResultId};
	application.numOfQuestion = ${numOfQuestion};
</script>
<c:set var="myNumber" value="0"/>
<link rel="stylesheet" href="${contextPath}/css/exam/doExam.css">
<div class="row">
   	<div class="span3">
   		<div id="leftMenu" class="pagination-centered">
   			<div>
   				<img src="${contextPath}/images/logoPage.png" class="logoPage"/>
   			</div>
   			<br>
   			<div>
   				<div id="countDown"></div> 
   			</div>
   			<br>
   			<div>
   				<button class="btn btn-success" id="sendExamButton"><i class="icon-check icon-white"></i> ส่งข้อสอบ</button>
   			</div>
   			<br>
   			<div>
   				<label for="doQuestion">คุณทำข้อสอบไปแล้ว </label> <span id="doQuestion" class="input-mini  uneditable-input" > </span> <label for="doQuestion">ข้อ </label>
   			</div>
   		</div>
   		<br>
   	</div>
	<div class="span9">
  		<div class="scrollspy-example" data-target="#navbarExample" data-offset="70" data-spy="scroll" >
  			<c:forEach items="${questionAnswerData}" var="data">
  				<c:set var="myNumber" value="${myNumber+1}"/>
				<hr class="hr-pagging" id="tab${data.questionId}">
  				<section id="tab-section${data.questionId}">
					<input type="hidden" id="exam-choose-${data.questionId}"  value="${data.answerId}"/>
					<input type="hidden" id="exam-result-answer-id-${data.questionId}"  value="${data.examResultAnswerId}"/>
	          		<br><h2>ข้อที่<font color="f16325"> ${myNumber}</font></h2>
					<div id="question-panel-${data.questionId}" class="question-panel">
						${data.questionText}
					</div>
					<div id="answer-panel-${data.answer1Id}" class="answer-panel">
						${data.answer1Text}
					</div>
					<div id="answer-panel-${data.answer2Id}" class="answer-panel">
						${data.answer2Text}
					</div>
					<div id="answer-panel-${data.answer3Id}" class="answer-panel">
						${data.answer3Text}
					</div>
					<div id="answer-panel-${data.answer4Id}" class="answer-panel">
						${data.answer4Text}
					</div>
            	</section>
			</c:forEach>
			<div class="blockPage"></div>
  		</div>
  	</div>
</div>
<br>
<c:set var="myNumber" value="0"/>
<div id="navbarExample" class="pagination-centered">
	<ul class="nav nav-pills">
		<c:forEach items="${questionAnswerData}" var="data">
  				<c:set var="myNumber" value="${myNumber+1}"/>
			<li id="nav-id-${data.questionId}"><a href="#tab${data.questionId}">${myNumber}</a></li>
		</c:forEach>
    </ul>
</div>

<div class="modal hide fade" id="sendExamConfirm">
	<div class="modal-header">
		<h3>ส่งข้อสอบ ?</h3>
	</div>
	<div class="modal-body" id="confirmBody">
		<font class="error" id="errorItem"></font>โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ยกเลิก</a>
    	<a href="#" class="btn btn-success" id="sendExamConfirmButton" data-loading-text="กำลังส่ง..." ><i class="icon-check icon-white"></i> ส่งข้อสอบ</a>
  	</div>
</div>

<form class="hide" action="${contextPath}/exam/viewResult.html" method="POST" id="viewResultForm">
	<input type="hidden" name="method" value="viewResult">
	<input type="hidden" name="examResultId" value="${examResultId}">
</form>


