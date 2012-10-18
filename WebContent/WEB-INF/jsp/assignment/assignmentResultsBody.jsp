<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/assignment/assignmentResults.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">ประวัติ</font> Assignment</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<c:if test="${not empty success}">
			<div style="width:500px;margin:auto;text-align:center;">
				<div class="alert alert-success">
					<strong>Success !</strong> ${success} 
				</div>
			</div>
		</c:if>
	</div>
</div>
<div class="row-fluid">
		<div class="span12">
			<table class="table table-hover table-bordered table-striped table-grid" id="assignmentResultGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="sendDateHeader">ส่งเมื่อ <i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา <i></i></th>
						<th class="sort-both sortable" id="assignmentTaskNameHeader">หัวข้องาน <i></i></th>
						<th class="sort-both sortable" id="statusHeader">สถานะ <i></i></th>
						<th class="sort-both sortable" id="maxScoreHeader">คะแนนเต็ม <i></i></th>
						<th class="sort-both sortable" id="scoreHeader">ได้คะแนน <i></i></th>
						<th class="sort-both sortable" id="evaluateDateHeader">ตรวจเมื่อ <i></i></th>
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
				 	</select> รายการต่อหน้า
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
