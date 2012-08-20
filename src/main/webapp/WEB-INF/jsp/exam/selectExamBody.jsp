<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="nowTodayTime" value="<%= new Date()%>" scope="page"/>
<link rel="stylesheet" href="${contextPath}/css/exam/selectExam.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Select</font> Exam</h2>
	</div>
	<div class="row">
		<div class="span10 offset1">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>วิชา</th>
						<th>หัวข้อการสอบ</th>
						<th>วันหมดเขตสอบ</th>
						<th>สอบได้(ครั้ง)</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty examData}">
						<c:forEach items="${examData}" var="data">
							<tr>
								<td>${data[0]}</td>
								<td>${data[1]}</td>
								<c:if test="${not empty data[2]}">
									<td>${data[2]}</td>
								</c:if>
								<c:if test="${empty data[2]}">
									<td>ไม่กำหนด</td>
								</c:if>
								<td>${data[4]-data[3]}</td>
								<td><button class="btn btn-info havePopover" rel="popover" exam-count="${data[3]+1}" min-question="${data[5]}"  max-question="${data[6]}" data-content="จำนวนข้อสอบ ${data[5]} ถึง ${data[6]} ข้อ" data-original-title="รายละเอียด" onClick="createExam(${data[7]})" id="do-exam-${data[7]}"><i class="icon-edit icon-white"></i> สอบ</button></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	
	<c:if test="${not empty examResult}">
		<div class="page-header pagination-centered" id="pageHeader">
			<h2><font class="red-color">Incomplete</font> Exam</h2>
		</div>
		<div class="row">
			<div class="span10 offset1">
				<table class="table table-striped table-bordered" id="incomplete-table">
					<thead>
						<tr>
							<th>วิชา</th>
							<th>หัวข้อการสอบ</th>
							<th>ครั้งที่</th>
							<th>หมดเวลาสอบ</th>
							<th>จำนวนคำถาม(ข้อ)</th>
							<th>ทำไปแล้ว(ข้อ)</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${examResult}" var="data">
							<tr id="exam-incomplete-${data[6]}">
								<fmt:parseDate var="expireDate" value="${data[3]}" pattern="yyyy-MM-dd HH:mm:ss"/>
								<td>${data[0]}</td>
								<td>${data[1]}</td>
								<td>${data[2]}</td>
								<td>${data[3]}</td>
								<td>${data[4]}</td>
								<td>${data[5]}</td>
								<td>
									<c:if test="${nowTodayTime < expireDate}">
										<button class="btn btn-info" id="continue-exam-${data[6]}" data-loading-text="กำลังโหลดข้อมูล..." onClick="continueExam(${data[6]})"><i class="icon-edit icon-white"></i> ทำต่อ </button>
									</c:if>
									<button class="btn btn-success"  id="send-exam-${data[6]}" data-loading-text="กำลังส่ง..." onClick="sendExam(${data[6]})" ><i class="icon-check icon-white"></i> ส่งข้อสอบ</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
</div>


<form class="hide" action="${contextPath}/exam/doExam.html" method="POST" id="doExamForm">
    <input type="hidden" name="method" value="doExam" />
	<input type="hidden" name="examResultId" id="examResultId" />
</form>

<div class="modal hide fade create-exam-modal" id="createExamModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>สอบ</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="createExamForm">
    	<input type="hidden" name="method" value="createExamResult" />
    	<input type="hidden" id="examId" name="examId" />
    	<input type="hidden" id="examCount" name="examCount" />
    	<div class="control-group">
	     	<label class="control-label" for="numOfQuestion">จำนวนข้อสอบ</label>
	      	<div class="controls">
	        	<input type="text" class="input-mini" id="numOfQuestion" name="numOfQuestion" />
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-info" id="createExamButton" data-loading-text="กำลังโหลดข้อมูล..." ><i class="icon-edit icon-white"></i> สอบ</a>
  </div>
</div>



