<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/management/register/registerManagement.css">
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">อนุมัติ</font> สิทธิ์นักศึกษา</h2>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<div class="button-holder form-inline" >
				<label for="courseId"> วิชา </label>
				<select id="courseId" name="courseId"  class="input-large"></select>
				
				<label for="sectionId"> Section </label>
				<select id="sectionId" name="sectionId"  class="input-large">
					<script id="sectionTemplate" type="text/x-jquery-tmpl">
						<optgroup label="เทอม {{= sectionSemester}} ปี {{= sectionYear}}">
							{{each sections}}
								<option value="{{= $value[0]}}" sectionYear="{{= $value[2]}}" sectionSemester="{{= $value[3]}}" sectionName="{{= $value[1]}}">
									เทอม {{= $value[3]}} ปี {{= $value[2]}} [{{= $value[1]}}]
								</option>
							{{/each}}
						</optgroup>
					</script>
				</select>
				<button class="btn btn-info" id="filterButton"><i class="icon-filter icon-white"></i> กรอง</button>
			</div>
			<hr>
			<p id="sectionData"></p>
			
			<script id="infoTemplate" type="text/x-jquery-tmpl">
				<span class="group-item">นักศึกษาลงทะเบียนทั้งหมด <span class="badge badge-info">{{= totalList}}</span> คน</span>
				 <span class="group-item">รออนุมัติ <span class="badge badge-warning">{{= pendingList}}</span> คน</span>
				 <span class="group-item">อนุมัติแล้ว <span class="badge badge-success">{{= acceptList}}</span> คน</span>
				 <span class="group-item">ไม่อนุมัติ <span class="badge badge-important">{{= deniedList}}</span> คน</span>
			</script>
			
			<hr>
			<table class="table table-striped table-bordered table-hover table-grid table-condensed" id="registerTable">
				<thead>
					<tr>
						<th></th>
						<th>รหัส<span class="hidden-phone">นักศึกษา</span> <i></i></th>
						<th>ชื่อ-นามสกุล <i></i></th>
						<th>วิชา <i></i></th>
						<th>Section <i></i></th>
						<th>เมื่อ <i></i></th>
					</tr>
				</thead>
				<tbody>
					<script id="recordTemplate" type="text/x-jquery-tmpl">
						<tr>
							<td><input type="checkbox" id="register-id-{{= registerId}}" name="registerId[]" value="{{= registerId}}"/></td>
							<td>{{= studentId}}</td>
							<td>{{= prefixNameTh}} {{= firstName}} {{= lastName}}</td>
							<td>{{= courseCode}}</td>
							<td>[{{= sectionName}}] {{= sectionSemester}}/{{= sectionYear}}</td>
							<td>{{= Globalize.format( new Date(requestDate),"dd-MM-yyyy HH:mm:ss")}}</td>
						  </tr>
					</script>
				</tbody>
				<tfoot >
					<tr>
						<td colspan="6" >
							<button class="btn btn-success" id="approveButton"><i class="icon-ok icon-white"></i> อนุมัติ</button>
							<button class="btn btn-danger" id="rejectButton"><i class="icon-ban-circle icon-white"></i> ไม่อนุมัติ</button>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	
	
	<div class="modal hide fade" id="confirmApproveModal">
		<div class="modal-header">
   			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>อนุมัติ ?</h3>
		</div>
		<div class="modal-body">
			อนุมัติสิทธิ์นักศึกษาที่เลือกใช่หรือไม่ ?
		</div>
	  	<div class="modal-footer">
	    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
	    	<a href="#" class="btn btn-success" id="approveConfirmButton" data-loading-text="อนุมัติ..." ><i class="icon-ok icon-white"></i> อนุมัติ</a>
	  	</div>
	</div>
	
	<div class="modal hide fade" id="confirmRejectModal">
		<div class="modal-header">
   			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>ไม่อนุมัติ ?</h3>
		</div>
		<div class="modal-body">
			ไม่อนุมัติสิทธิ์นักศึกษาที่เลือกใช่หรือไม่ ?
		</div>
	  	<div class="modal-footer">
	    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
	    	<a href="#" class="btn btn-danger" id="rejectConfirmButton" data-loading-text="ไม่อนุมัติ..." ><i class="icon-ban-circle icon-white"></i> ไม่อนุมัติ</a>
	  	</div>
	</div>
	
	
