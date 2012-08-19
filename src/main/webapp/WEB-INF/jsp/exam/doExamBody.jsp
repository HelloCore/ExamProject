<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<script>
	application.timeLimit = ${timeLimit};
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
   				<button class="btn btn-success"><i class="icon-check icon-white"></i> ส่งข้อสอบ</button>
   			</div>
   			<br>
   			<div>
   				<label for="doQuestion">คุณทำข้อสอบไปแล้ว </label> <span id="doQuestion" class="input-mini  uneditable-input" >0 / ${numOfQuestion}</span> <label for="doQuestion">ข้อ </label>
   			</div>
   		</div>
   		<br>
   	</div>
	<div class="span9">
  		<div class="scrollspy-example" >
  			<c:forEach items="${questionData}" var="data">
  				<c:set var="myNumber" value="${myNumber+1}"/>
				<section id="tab${data.questionId}">
	          		<br><h2>ข้อที่<font color="f16325"> ${myNumber}</font></h2>
					<div id="question-panel-${data.questionId}" class="question-panel">
						${data.questionText}
					</div>
					<c:forEach items="${data.answerData}" var="answerData">
						<div id="answer-panel-${answerData.answerId}" class="answer-panel">
							${answerData.answerText}
						</div>
					</c:forEach>
            	</section>
			</c:forEach>
  		</div>
  	</div>
</div>
<br>
<c:set var="myNumber" value="0"/>
<div id="navbarExample" class="pagination-centered">
	<ul class="nav nav-pills">
		<c:forEach items="${questionData}" var="data">
  				<c:set var="myNumber" value="${myNumber+1}"/>
			<li id="nav-id-${data.questionId}"><a href="#tab${data.questionId}">${myNumber}</a></li>
		</c:forEach>
    </ul>
</div>