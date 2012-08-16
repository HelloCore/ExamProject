<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

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
   				<label for="doQuestion">คุณทำข้อสอบไปแล้ว </label> <span id="doQuestion" class="input-mini  uneditable-input" >0 / 50</span> <label for="doQuestion">ข้อ </label>
   			</div>
   		</div>
   		<br>
   	</div>
	<div class="span9">
  		  <div class="scrollspy-example" >
  		  	<c:forEach var="i" begin="1" end="50" step="1" varStatus ="status">
			<section id="tab${i}">
	          <br><h2>ข้อที่<font color="f16325"> ${i}</font></h2>
				<div id="question-panel-1" class="question-panel">
					<p> ${i}+1=? </p>
				</div>
				<div id="answer-panel-1" class="answer-panel">
					<p> ${i} </p>
				</div>
				<div id="answer-panel-2" class="answer-panel">
					<p> ${i+1} </p>
				</div>
				<div id="answer-panel-3" class="answer-panel">
					<p> ${i+2} </p>
				</div>
				<div id="answer-panel-4" class="answer-panel">
					<p> ${i+3} </p>
				</div>
            </section>
			</c:forEach>
  		  </div>
  	</div>
</div>
<br>
<div id="navbarExample" class="pagination-centered">
	<ul class="nav nav-pills">
		<li class="active"><a href="#tab1">1</a></li>
		<c:forEach var="i" begin="2" end="50" step="1" varStatus ="status">
			<li><a href="#tab${i}">${i}</a></li>
		</c:forEach>
    </ul>
</div>