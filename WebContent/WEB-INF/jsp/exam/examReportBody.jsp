<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link rel="stylesheet" href="${contextPath}/css/exam/examReport.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">ผล</font> การสอบ</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<table class="table table-hover table-bordered table-striped table-grid" id="examReportGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="examCompleteDateHeader">สอบเมื่อ <i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา <i></i></th>
						<th class="sort-both sortable" id="examHeaderHeader">หัวข้อการสอบ <i></i></th>
						<th class="sort-both sortable" id="examCountHeader">ครั้งที่ <i></i></th>
						<th class="sort-both sortable" id="numOfQuestionHeader">จำนวน <i></i></th>
						<th class="sort-both sortable" id="examUsedTimeHeader">ใช้เวลา <i></i></th>
						<th class="sort-both sortable" id="maxScoreHeader">คะแนนเต็ม <i></i></th>
						<th class="sort-both sortable" id="examScoreHeader">ได้คะแนน <i></i></th>
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
						<option value="10">10</option>
				 		<option value="20">20</option>
				 		<option value="50">50</option>
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
</div>

