<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/management/register/registerHistory.css">
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">ประวัติ</font> การอนุมัติสิทธิ์</h2>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<div class="button-holder form-inline" >
				<label for="courseId"> วิชา </label>
				<select id="courseId" name="courseId" class="input-medium"></select>
				<button class="btn btn-info" id="filterButton"><i class="icon-filter icon-white"></i> กรอง</button>
			</div>
			<hr>
			<table class="table table-striped table-bordered table-hover table-grid table-condensed" id="registerHistoryTable">
				<thead>
					<tr>
						<th class="sort-both sortable" id="requestDateHeader">ร้องขอเมื่อ<i></i></th>
						<th class="sort-both sortable" id="processDateHeader">จัดการเมื่อ<i></i></th>
						<th class="sort-both sortable" id="studentIdHeader">รหัสนักศึกษา<i></i></th>
						<th class="sort-both sortable" id="firstNameHeader">ชื่อ-นามสกุล<i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา<i></i></th>
						<th class="sort-both sortable" id="sectionNameHeader">Section<i></i></th>
						<th class="sort-both sortable" id="statusHeader">ผลการจัดการ<i></i></th>
						<th class="sort-both sortable" id="verifyByHeader">อนุมัติโดย<i></i></th>
					</tr>
				</thead>
				<tbody>
					<script id="recordTemplate" type="text/x-jquery-tmpl">
						<tr>
							<td>{{= Globalize.format( new Date(requestDate),"dd-MM-yyyy HH:mm:ss")}}</td>
							<td>{{= Globalize.format( new Date(processDate),"dd-MM-yyyy HH:mm:ss")}}</td>
							<td>{{= studentId}}</td>
							<td>{{= prefixNameTh}} {{= firstName}} {{= lastName}}</td>
							<td>{{= courseCode}}</td>
							<td>{{= sectionName}} เทอม {{= sectionSemester}}/{{= sectionYear}}</td>
							<td>
								{{if status==1}}
									<span class="label label-success"><i class="icon-ok icon-white"></i> อนุมัติ</span>
								{{else}}
									<span class="label label-important"><i class="icon-ban-circle icon-white"></i> ไม่อนุมัติ</span>
								{{/if}}
							</td>
							<td>{{= verifyBy}}</td>
						</tr>
					</script>
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
				 		<option value="100">100</option>
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
	
	
