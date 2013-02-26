<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/report/studentReport.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายงาน</font> รายบุคคล</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="button-holder">
			<div class="form-inline">
				<label for="courseSectionId">Section </label>
				<select id="courseSectionId" class="input-xlarge" data-placeholder="Choose a course..." name="courseSectionId">
					<script id="courseSectionTemplate" type="text/x-jquery-tmpl">
						<optgroup label="วิชา {{= courseCode}}">
							{{each sections}}
								<option value="{{= $value[0]}}"
									sectionYear="{{= $value[2]}}"
									sectionSemester="{{= $value[3]}}"
									sectionName="{{= $value[1]}}"
									courseId="{{= $value[4]}}"
									courseCode="{{= $value[5]}}"
									>วิชา {{= $value[5]}} เทอม {{= $value[3]}} ปี {{= $value[2]}} [{{= $value[1]}}]</option>
							{{/each}}
						</optgroup>
					</script>
				</select>
				<button class="btn btn-success" id="refreshButton"><i class="icon-white icon-refresh"></i> Refresh</button>	
			</div>
		</div>
		<hr>
		<table class="table  table-hover table-bordered table-condensed" id="studentTable">
			<thead>
				<tr>
					<th class="sort-both sortable" id="usernameHeader">รหัสนักศึกษา <i></i></th>
					<th class="sort-both sortable" id="firstNameHeader">ชื่อ-นามสกุล <i></i></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<script id="recordTemplate" type="text/x-jquery-tmpl">
					<tr>
						<td>{{= studentId}}</td>
						<td>{{= prefixNameTh}} {{= firstName}} {{= lastName}}</td>
						<td><button class="btn btn-info" onClick="viewDetail({{= studentId}})"><i class="icon-zoom-in icon-white"></i> ดูรายละเอียด</button></td>
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

<form id="viewStudentDetailForm" action="${contextPath}/report/studentDetail.html" method="POST">
	<input type="hidden" name="method" value="viewStudentDetail" />
	<input type="hidden" id="studentId" name="studentId" />
	<input type="hidden" id="courseId" name="courseId" />
	<input type="hidden" id="sectionId" name="sectionId" />
</form>