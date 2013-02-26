<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/report/assignmentReport.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> Assignment</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="button-holder">
			<div class="form-inline">
				<label for="courseId">วิชา </label>
				<select id="courseId" class="input-medium" data-placeholder="Choose a course..." name="courseId"></select>
				<button class="btn btn-success" id="refreshButton"><i class="icon-white icon-refresh"></i> Refresh</button>	
			</div>
		</div>
		<hr>
		<table class="table  table-hover table-bordered" id="assignmentTable">
			<thead>
				<tr>
					<th class="sort-both sortable" id="courseCodeHeader">วิชา <i></i></th>
					<th class="sort-both sortable" id="assignmentTaskNameHeader">หัวข้องาน <i></i></th>
					<th class="sort-both sortable" id="startDateHeader">เริ่มส่ง <i></i></th>
					<th class="sort-both sortable" id="endDateHeader">หมดเขตส่ง <i></i></th>
					<th class="sort-both sortable" id="maxScoreHeader">คะแนนเต็ม <i></i></th>
					<th class="sort-both sortable" id="isEvaluateCompleteHeader">สถานะ <i></i></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<script id="recordTemplate" type="text/x-jquery-tmpl">
					<tr>
						<td>{{= courseCode}}</td>
						<td>{{= taskName}}</td>
						<td>{{= Globalize.format(new Date(startDate),'dd-MM-yyyy HH:mm')}}</td>
						<td>{{= Globalize.format(new Date(endDate),'dd-MM-yyyy HH:mm')}}</td>
						<td style="text-align:right;">{{= maxScore}}</td>
						{{if isEvaluateComplete}}
							<td><span class="label label-success"><i class="icon-ok icon-white"></i> ตรวจเสร็จแล้ว</span></td>
						{{else}}
							<td><span class="label label-warning"><i class="icon-pencil icon-white"></i> ยังตรวจไม่เสร็จ</span></td>
						{{/if}}
						<td><button class="btn btn-info" onclick="viewResult({{= taskId}})"><i class="icon-zoom-in icon-white"></i> ดูรายละเอียด</button></td>
					</tr>
				</script>
			</tbody>
		</table>
		<div class="row-fluid">
			<div class="span4">
				<div class="grid-info" id="gridInfo"></div>
			</div>
			<div class="span4 page-size-div">
				<select id="pageSize" name="pageSize" class="page-size ">
					<option value="20">20</option>
			 		<option value="50">50</option>
			 		<option value="50">100</option>
			 	</select> รายการต่อหน้า
			</div>
			<div class="span4">
				<div class="grid-pagination pagination pagination-centered">
					<ul>
						<li class="prev disabled"><a href="#" id="prevPageButton">«</a></li>
						<li class="next"><a href="#" id="nextPageButton">»</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<form id="viewAssignmentResultForm" action="${contextPath}/report/assignmentResult.html" method="POST">
	<input type="hidden" name="method" value="viewAssignmentResult" />
	<input type="hidden" id="taskId" name="taskId" />
</form>