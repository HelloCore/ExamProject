<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link rel="stylesheet" href="${contextPath}/css/exam/examReport.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Exam</font> Report</h2>
	</div>
	<div class="row-fluid">
		<div class="span10 offset1">
			<table class="table table-hover table-bordered table-striped table-grid" id="examReportGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="examCompleteDateHeader">สอบเมื่อ</th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา</th>
						<th class="sort-both sortable" id="examHeaderHeader">หัวข้อการสอบ</th>
						<th class="sort-both sortable" id="examCountHeader">ครั้งที่</th>
						<th class="sort-both sortable" id="numOfQuestionHeader">จำนวน</th>
						<th class="sort-both sortable" id="examUsedTimeHeader">ใช้เวลา</th>
						<th class="sort-both sortable" id="examScoreHeader">ได้คะแนน</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span10 offset1">
			<div class="row-fluid">
				<div class="span4">
					<div class="grid-info" id="gridInfo"></div>
				</div>
				<div class="span4">
					<select id="pageSize" name="pageSize" class="page-size">
				 	<option value="10">10</option>
				 	<option value="20">20</option>
				 	<option value="50">50</option>
				 </select> records per page
				</div>
				<div class="span4">
					<div class="grid-pagination pagination pull-right">
						<ul>
							<li class="prev disabled"><a href="#" id="prevPageButton">&larr; Prev</a></li>
							<li class="next"><a href="#" id="nextPageButton">Next &rarr;</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

