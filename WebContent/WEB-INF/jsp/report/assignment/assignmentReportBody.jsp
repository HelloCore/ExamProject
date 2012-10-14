<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
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
			</tbody>
		</table>
		<div class="row-fluid">
			<div class="span3">
				<div class="grid-info" id="gridInfo"></div>
			</div>
			<div class="span4 page-size-div">
				<select id="pageSize" name="pageSize" class="page-size ">
					<option value="20">20</option>
			 		<option value="50">50</option>
			 		<option value="50">100</option>
			 	</select> items per page
			</div>
			<div class="span5">
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