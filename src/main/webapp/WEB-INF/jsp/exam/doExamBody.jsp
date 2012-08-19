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
  		  	<c:forEach var="i" begin="50" end="100" step="1" varStatus ="status">
			<section id="tab${i}">
	          <br><h2>ข้อที่<font color="f16325"> ${i-49}</font></h2>
				<div id="question-panel-${i}" class="question-panel">
					<p> ${i}+1=? </p>
				</div>
				<div id="answer-panel-${((i-1)*4)}" class="answer-panel">
					<p> ${i} </p>
				</div>
				<div id="answer-panel-${((i-1)*4)+1}" class="answer-panel">
					<p> ${i+1} </p>
				</div>
				<div id="answer-panel-${((i-1)*4)+2}" class="answer-panel">
					<p> ${i+2} </p>
				</div>
				<div id="answer-panel-${((i-1)*4)+3}" class="answer-panel">
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
		<li class="active" id="nav-id-50"><a href="#tab50">1</a></li>
		<c:forEach var="i" begin="51" end="99" step="1" varStatus ="status">
			<li id="nav-id-${i}"><a href="#tab${i}">${i-49}</a></li>
		</c:forEach>
    </ul>
</div>