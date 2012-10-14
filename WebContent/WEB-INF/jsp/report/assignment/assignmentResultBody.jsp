<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/assignmentResult.css">
		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน </font> Assignment</h2>
</div>
<a href="${contextPath}/report/assignment.html" class="btn" style="margin-top:-10px;"><i class="icon-chevron-left"></i> Back</a>
<hr>
<div class="row-fluid">
	<div class="span12">
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
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${evaluatedList}" var="data">
						<fmt:parseDate var="sendDate" value="${data.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
						<tr>
							<td><fmt:formatDate value="${sendDate}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td>${data.studentId}</td>
							<td>${data.firstName} ${data.lastName}</td>
							<td>${data.score} / ${taskData.maxScore}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>
<hr>
<a href="${contextPath}/report/assignment.html" class="btn" ><i class="icon-chevron-left"></i> Back</a>
