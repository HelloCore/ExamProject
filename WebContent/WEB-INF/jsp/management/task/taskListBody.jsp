<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/management/task/taskList.css">
		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">ตรวจ</font> Assignment</h2>
</div>
<c:if test="${not empty error}">
	<div class="alert alert-error" style="width:500px;margin:auto;">
		<strong>Error! </strong>${error}
	</div>
</c:if>
<div class="row-fluid">
	<div class="span12">
		<div class="form-inline">
			<label for="courseId">วิชา</label>
			<select id="courseId" class="input-medium"></select>
			<button class="btn btn-success" id="refeshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
		</div>
		<hr>
		<table class="table table-condensed table-hover" id="taskListTable">
			<thead>
				<tr>
					<th>วิชา</th>
					<th>หัวข้อ</th>
					<th>หมดเขตส่ง</th>
					<th>ส่งแล้ว(คน)</th>
					<th>ตรวจแล้ว(คน)</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<script id="recordTemplate" type="text/x-jquery-tmpl">
					<tr>
						<td>{{= $data[1]}}</td>
						<td>{{= $data[2]}}</td>
						<td>{{= Globalize.format(new Date($data[3]),'dd-MM-yyyy HH:mm')}}</td>
						<td>{{= $data[4]}}</td>
						<td>{{= $data[5]}}</td>
						<td><button class="btn btn-info" onClick="evaluateTask({{= $data[0]}})"><i class="icon-edit icon-white"></i> ตรวจการบ้าน</button></td>
					</tr>
				</script>
			</tbody>
		</table>
	</div>
</div>

<form action="${contextPath}/management/task/sendList.html" method="POST" id="sendTaskForm">
	<input type="hidden" id="taskId" name="taskId">
	<input type="hidden" name="method" value="initSendList">
</form>