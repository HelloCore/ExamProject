<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/examReport.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> ผลการสอบ</h2>
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
		<table class="table  table-hover table-bordered" id="examTable">
			<thead>
				<tr>
					<th class="sort-both sortable" id="courseCodeHeader">วิชา <i></i></th>
					<th class="sort-both sortable" id="examHeaderHeader">หัวข้อการสอบ <i></i></th>
					<th class="sort-both sortable" id="startDateHeader">เริ่มสอบ <i></i></th>
					<th class="sort-both sortable" id="endDateHeader">หมดเขตสอบ <i></i></th>
					<th class="sort-both sortable" id="maxQuestionHeader">จำนวนคำถาม <i></i></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<!-- <tr>
					<td>CS.111</td>
					<td>สอบกลางภาค</td>
					<td>2012-08-21 09:00</td>
					<td>2012-08-30 00:45</td>
					<td>30 - 40 ข้อ</td>
					<td><button class="btn btn-info"><i class="icon-zoom-in icon-white"></i> ดูผลการสอบ</button></td>
				</tr>
				<tr>
					<td>CS.111</td>
					<td>สอบปลายภาค</td>
					<td>2012-10-15 00:00</td>
					<td>2012-10-17 23:45</td>
					<td>30 - 40 ข้อ</td>
					<td><button class="btn btn-info"><i class="icon-zoom-in icon-white"></i> ดูผลการสอบ</button></td>
				</tr> -->
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

<form id="viewExamReportForm" action="${contextPath}/report/examDetail.html" method="POST">
	<input type="hidden" name="method" value="viewExamReportDetail" />
	<input type="hidden" id="examId" name="examId" />
</form>

<form id="viewExamGraphForm" action="${contextPath}/report/examGraph.html" method="POST">
	<input type="hidden" name="method" value="viewExamGraph" />
	<input type="hidden" id="examGraphId" name="examId" />
</form>



