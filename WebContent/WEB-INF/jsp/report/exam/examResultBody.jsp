<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/exam/viewResult.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">ผล</font> การสอบ</h2>
	</div>
	<button class="btn" style="margin-top:-10px;" onclick="history.back();"><i class="icon-chevron-left"></i> กลับ</button>
	<hr>
	<div class="row-fluid">
		<div class="span6 offset3 ">
			<div class="well">
				<dl class="dl-horizontal">
				  <dt>ประเภท : </dt>
				  <dd>
					<c:if test="${examResultData.exam.isCalScore}" >สอบจริง</c:if>
					<c:if test="${not examResultData.exam.isCalScore}" >ทดลองสอบ</c:if>
				  </dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>วิชา : </dt>
				  <dd>${examResultData.exam.course.courseCode}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>สอบโดย : </dt>
				  <dd>${examResultData.user.username}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>ชื่อ-สกุล : </dt>
				  <dd>${examResultData.user.prefixName.prefixNameTh} ${examResultData.user.firstName} ${examResultData.user.lastName} </dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>หัวข้อการสอบ : </dt>
				  <dd>${examResultData.exam.examHeader}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>ครั้งที่ : </dt>
				  <dd>${examResultData.examCount}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>เริ่มสอบ : </dt>
				  <dd>${examResultData.examStartDate}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>กำหนดส่ง : </dt>
				  <dd>${examResultData.examExpireDate}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>ส่งข้อสอบ : </dt>
				  <dd>${examResultData.examCompleteDate}</dd>
				</dl>
				<dl class="dl-horizontal">
				  <fmt:parseDate var="examUseTime" value="2010-01-01 00:00:00" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
				  <c:set target="${examUseTime}" property="time" value="${examUseTime.time + examResultData.examUsedTime*1000}" />
				  <dt>ใช้เวลาทำข้อสอบ : </dt>
				  <dd id="examUsedTime"><fmt:formatDate value="${examUseTime}" pattern="HH:mm:ss"/></dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>จำนวนข้อสอบ : </dt>
				  <dd>${examResultData.numOfQuestion} ข้อ</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>คะแนนเต็ม : </dt>
				  <dd>${examResultData.exam.maxScore} คะแนน</dd>
				</dl>
				<dl class="dl-horizontal">
				  <dt>ได้คะแนน : </dt>
				  <dd><fmt:formatNumber value="${examResultData.examScore}" maxFractionDigits="2"/> คะแนน</dd>
				</dl>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span10 offset1">
			<table class="table table-hover table-bordered table-striped">
				<thead>
					<tr>
						<th class="span6">
							คำถาม
						</th>
						<th class="span5">
							คำตอบ
						</th>
						<th class="span1">
							ผลลัพธ์
						</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty examResultAnswer}">
						<c:forEach items="${examResultAnswer}" var="data">
							<tr>
								<td><p>${data.questionText}</p></td>
								<td><p>${data.answerText}</p></td>
								<c:if test="${empty data.answerScore}">
									<td><span class="label label-warning"><i class="icon-ban-circle icon-white"></i> ไม่ตอบ</span></td>
								</c:if>
								<c:if test="${not empty data.answerScore}">
									<c:if test="${data.answerScore<=0}">
										<td><span class="label label-important"><i class="icon-remove icon-white"></i> ตอบผิด</span></td>
									</c:if>
									<c:if test="${data.answerScore>0}">
										<td><span class="label label-success"><i class="icon-ok icon-white"></i> ตอบถูก</span></td>
									</c:if>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</div>
<hr>
<button class="btn"  onclick="history.back();"><i class="icon-chevron-left"></i> กลับ</button>

