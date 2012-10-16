<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/task/sendList.css">
		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Evaluate</font> Task</h2>
</div>

<div class="row-fluid">
	<div class="span12">
		<a class="btn" href="${contextPath}/management/task/taskList.html"><i class="icon-chevron-left"></i> Back</a>
		<c:if test="${not empty success}">		
			<div class="alert alert-success" style="width:300px;margin:auto;">
				<strong>Success!</strong> Evaluate Complete
			</div>
		</c:if>
		<br>
		<div class="well" style="width:600px;margin:auto;">
			<div class="form-horizontal">
				<div class="control-group">
			    	<label class="control-label" >หัวข้องาน</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${taskData.assignmentTaskName}</span>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >รายละเอียดงาน</label>
			    	<div class="controls">
			    		<pre class="input-xlarge">${taskData.assignmentTaskDesc}</pre>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >คะแนนเต็ม</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${taskData.maxScore} คะแนน</span>
			    	</div>
			  	</div>
			</div>
		</div>
	</div>
</div>
<br>
<div class="row-fluid">
	<div class="span12">
		<ul class="nav nav-tabs" id="tabs">
			<li class="active"><a href="#sendList" data-toggle="tab">ยังไม่ตรวจ</a></li>
			<li><a href="#evaluatedList" data-toggle="tab">ตรวจแล้ว</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="sendList">
				<c:if test="${empty sendList}">
					<div class="alert alert-warning" style="width:300px;margin:auto;">
						<strong>คำเตือน</strong> ไม่พบข้อมูล
					</div>
				</c:if>
				<c:if test="${not empty sendList}">
					<table class="table table-hover table-condensed">
						<thead>
							<tr>
								<th>ส่งเมื่อ</th>
								<th>รหัสนักศึกษา</th>
								<th>ส่งโดย</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${sendList}" var="data">
								<fmt:parseDate var="sendDate" value="${data.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
								<tr>
									<td><fmt:formatDate value="${sendDate}" pattern="dd-MM-yyyy HH:mm"/></td>
									<td>${data.studentId}</td>
									<td>${data.prefixNameTh} ${data.firstName} ${data.lastName}</td>
									<td><button class="btn btn-info" onClick="evaluateWork(${data.assignmentWorkId})"><i class="icon-edit icon-white"></i> ตรวจ</button></td>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
			<div class="tab-pane" id="evaluatedList">
				<c:if test="${empty evaluatedList}">
					<div class="alert alert-warning" style="width:300px;margin:auto;">
						<strong>คำเตือน</strong> ไม่พบข้อมูล
					</div>
				</c:if>
				<c:if test="${not empty evaluatedList}">
					<table class="table table-hover table-condensed">
						<thead>
							<tr>
								<th>ส่งเมื่อ</th>
								<th>รหัสนักศึกษา</th>
								<th>ส่งโดย</th>
								<th>คะแนน</th>
								<th>ตรวจเมื่อ</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${evaluatedList}" var="data">
								<fmt:parseDate var="sendDate" value="${data.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
								<fmt:parseDate var="evaluateDate" value="${data.evaluateDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
								<tr>
									<td><fmt:formatDate value="${sendDate}" pattern="dd-MM-yyyy HH:mm"/></td>
									<td>${data.studentId}</td>
									<td>${data.prefixNameTh} ${data.firstName} ${data.lastName}</td>
									<td>${data.score} / ${taskData.maxScore}</td>
									<td><fmt:formatDate value="${evaluateDate}" pattern="dd-MM-yyyy HH:mm"/></td>
									<td><button class="btn btn-info" onClick="reEvaluateWork(${data.assignmentWorkId})"><i class="icon-edit icon-white"></i> ตรวจใหม่</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
		</div>
	</div>
</div>

<form id="evaluateForm" action="${contextPath}/management/task/evaluate.html" method="POST">
	<input type="hidden" id="evaluateWorkId" name="workId" />
	<input type="hidden" name="method" value="evaluateWork" />
</form>