<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
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
					<th class="sort-both sortable" id="isCalScoreHeader">ประเภท <i></i></th>
					<th class="sort-both sortable" id="courseCodeHeader">วิชา <i></i></th>
					<th class="sort-both sortable" id="examHeaderHeader">หัวข้อการสอบ <i></i></th>
					<th class="sort-both sortable" id="startDateHeader">เริ่มสอบ <i></i></th>
					<th class="sort-both sortable" id="endDateHeader">หมดเขตสอบ <i></i></th>
					<th class="sort-both sortable" id="maxScoreHeader">คะแนนเต็ม <i></i></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<script id="recordTemplate" type="text/x-jquery-tmpl">
					<tr>
						<td>{{if isCalScore}}สอบจริง{{else}}ทดลองสอบ{{/if}}</td>
						<td>{{= courseCode}}</td>
						<td>{{= examHeader}}</td>
						{{if startDate}}
							<td>{{Globalize.format(new Date(startDate),'dd-MM-yyyy HH:mm')}}</td>
						{{else}}
							<td>ไม่กำหนด</td>
						{{/if}}
						{{if endDate}}
							<td>{{Globalize.format(new Date(endDate),'dd-MM-yyyy HH:mm')}}</td>
						{{else}}
							<td>ไม่กำหนด</td>
						{{/if}}
						<td style="text-align:right">{{= minQuestion}} ถึง {{= maxQuestion}} ข้อ</td>
						<td><button class="btn btn-success" onclick="viewExamReport({{= examId}})"><i class="icon-zoom-in icon-white"></i> ดูผลการสอบ</button> 
						{{if isCalScore}}
							<button class="btn btn-info" onclick="viewExamGraph({{= examId}})"><i class=" icon-eye-open icon-white"></i> ดูกราฟ</button></td>
						{{/if}}
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

<form id="viewExamReportForm" action="${contextPath}/report/examDetail.html" method="POST">
	<input type="hidden" name="method" value="viewExamReportDetail" />
	<input type="hidden" id="examId" name="examId" />
</form>

<form id="viewExamGraphForm" action="${contextPath}/report/examGraph.html" method="POST">
	<input type="hidden" name="method" value="viewExamGraph" />
	<input type="hidden" id="examGraphId" name="examId" />
</form>



