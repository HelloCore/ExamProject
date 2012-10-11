<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${contextPath}/css/assignment/assignmentResults.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Assignments</font> Result</h2>
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
						<th class="sort-both sortable" id="scoreHeader">ได้คะแนน <i></i></th>
						<th class="sort-both sortable" id="evaluateDateHeader">ตรวจเมื่อ <i></i></th>
					</tr>
				</thead>
				<tbody>
					<tr>
					<tr>
						<td>12/10/2012 15:30</td>
						<td>CS.111</td>
						<td>ทดสอบงาน2</td>
						<td><span class="label label-warning"><i class="icon-refresh icon-white"></i> รอตรวจ</span></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>10/10/2012 15:30</td>
						<td>CS.111</td>
						<td>ทดสอบงาน</td>
						<td><span class="label label-success"><i class="icon-ok icon-white"></i> ตรวจแล้ว</span></td>
						<td>50/100</td>
						<td>11/10/2012 18:00</td>
					</tr>
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
