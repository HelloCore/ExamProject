<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="nowTodayTime" class="java.util.Date" scope="page"/>
<link rel="stylesheet" href="${contextPath}/css/exam/selectExam.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">ทำการ</font> สอบ</h2>
	</div>
	<div class="row">
		<div class="span12">
			<c:if test="${empty examData and empty examResult}">
				<div style="width:500px;margin:auto;text-align:center;">
					<div class="alert alert-warning">
						<strong>Warning !</strong> ไม่มีข้อสอบที่คุณสามารถสอบได้ 
					</div>
					<a class="btn" href="${contextPath}/main/home.html"><i class=" icon-chevron-left "></i> Back</a>
				</div> 
			</c:if>
			<c:if test="${not empty examData}">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>ประเภท</th>
							<th>วิชา</th>
							<th>หัวข้อ</th>
							<th>คะแนนเต็ม</th>
							<th>เริ่มสอบ</th>
							<th>หมดเขต</th>
							<th>สอบได้(ครั้ง)</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${examData}" var="data">
							<tr>
								<td>
									<c:if test="${data[9]}" >สอบจริง</c:if>
									<c:if test="${not data[9]}" >ทดลองสอบ</c:if>
								</td>
								<td>${data[0]}</td>
								<td>${data[1]}</td>
								<td>${data[10]} คะแนน</td>
								<td>
									<c:if test="${not empty data[8]}">
										<fmt:parseDate var="startDate" value="${data[8]}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
										<fmt:formatDate value="${startDate}"  pattern="dd-MM-yyyy HH:mm"/>
									</c:if>
									<c:if test="${empty data[8]}">ไม่กำหนด</c:if>
								</td>
								<td>
									<c:if test="${not empty data[2]}">
										<fmt:parseDate var="endDate" value="${data[2]}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
										<fmt:formatDate value="${endDate}"  pattern="dd-MM-yyyy HH:mm"/>
									</c:if>
									<c:if test="${empty data[2]}">ไม่กำหนด</c:if>
								</td>
								<td>${data[4]-data[3]}</td>
								<td>
									<c:if test="${not empty startDate && nowTodayTime < startDate}">
										<button class="btn btn-info" disabled="disabled"><i class="icon-edit icon-white"></i> สอบ</button>
									</c:if>
									<c:if test="${empty startDate || nowTodayTime > startDate}">
										<button class="btn btn-info havePopover" exam-count="${data[3]+1}" min-question="${data[5]}"  max-question="${data[6]}" onClick="createExam(${data[7]})" id="do-exam-${data[7]}"><i class="icon-edit icon-white"></i> สอบ</button>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			
		</div>
	</div>
	
	<c:if test="${not empty examResult}">
		<div class="page-header pagination-centered" id="pageHeader">
			<h2><font class="red-color">ข้อสอบ</font> ที่ยังไม่ได้ส่ง</h2>
		</div>
		<div class="row">
			<div class="span12">
				<table class="table table-striped table-bordered" id="incomplete-table">
					<thead>
						<tr>
							<th>ประเภท</th>
							<th>วิชา</th>
							<th>หัวข้อ</th>
							<th>คะแนนเต็ม</th>
							<th>ครั้งที่</th>
							<th>หมดเวลา</th>
							<th>จำนวนคำถาม</th>
							<th>ทำไปแล้ว</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${examResult}" var="data">
							<tr id="exam-incomplete-${data[6]}">
								<fmt:parseDate var="expireDate" value="${data[3]}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
								
								<td>
									<c:if test="${data[7]}" >สอบจริง</c:if>
									<c:if test="${not data[7]}" >ทดลองสอบ</c:if>
								</td>
								<td>${data[0]}</td>
								<td>${data[1]}</td>
								<td>${data[8]}</td>
								<td><fmt:formatDate value="${nowTodayTime}" pattern="dd-MM-yyyy HH:mm"/></td>
								<td><fmt:formatDate value="${expireDate}" pattern="dd-MM-yyyy HH:mm"/></td>
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

<form class="hide" action="${contextPath}/exam/viewResult.html" method="POST" id="viewResultForm">
	<input type="hidden" name="method" value="viewResult">
	<input type="hidden" name="examResultId" value="${examResultId}" id="examResultIdView">
</form>

<div class="modal hide fade" id="sendExamConfirm">
	<div class="modal-header">
		<h3>ส่งข้อสอบ ?</h3>
	</div>
	<div class="modal-body" id="confirmBody">
		<font class="error" id="errorItem">การสอบนี้ยังไม่หมดเวลา</font> คุณต้องการส่งข้อสอบใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ยกเลิก</a>
    	<a href="#" class="btn btn-info" id="continueExamConfirmButton" data-loading-text="กำลังโหลดข้อมูล..." ><i class="icon-edit icon-white"></i> ทำต่อ </a>
    	<a href="#" class="btn btn-success" id="sendExamConfirmButton" data-loading-text="กำลังส่ง..." ><i class="icon-check icon-white"></i> ส่งข้อสอบ</a>
  	</div>
</div>
