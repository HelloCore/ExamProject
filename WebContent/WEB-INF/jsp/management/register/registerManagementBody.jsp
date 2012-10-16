<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/register/registerManagement.css">
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">อนุมัติ</font> สิทธิ์นักศึกษา</h2>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<div class="button-holder form-inline" >
				<label for="courseId"> วิชา </label>
				<select id="courseId" name="courseId"></select>
				<label for="sectionId"> Section </label>
				<select id="sectionId" name="sectionId"></select>
				<button class="btn btn-info" id="filterButton"><i class="icon-filter icon-white"></i> กรอง</button>
			</div>
			<hr>
			<table class="table table-striped table-bordered table-hover table-grid table-condensed" id="registerTable">
				<thead>
					<tr>
						<th></th>
						<th>รหัสนักศึกษา <i></i></th>
						<th>ชื่อ-นามสกุล <i></i></th>
						<th>วิชา <i></i></th>
						<th>Section <i></i></th>
						<th>เมื่อ <i></i></th>
					</tr>
				</thead>
				<tbody>
					
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
			<h3>Approve ?</h3>
		</div>
		<div class="modal-body">
			อนุมัติ Section ที่เลือกใช่หรือไม่ ?
		</div>
	  	<div class="modal-footer">
	    	<a href="#" class="btn" data-dismiss="modal">Close</a>
	    	<a href="#" class="btn btn-success" id="approveConfirmButton" data-loading-text="อนุมัติ..." ><i class="icon-ok icon-white"></i> อนุมัติ</a>
	  	</div>
	</div>
	
	<div class="modal hide fade" id="confirmRejectModal">
		<div class="modal-header">
   			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Reject ?</h3>
		</div>
		<div class="modal-body">
			ไม่อนุมัติ Section ที่เลือกใช่หรือไม่ ?
		</div>
	  	<div class="modal-footer">
	    	<a href="#" class="btn" data-dismiss="modal">Close</a>
	    	<a href="#" class="btn btn-danger" id="rejectConfirmButton" data-loading-text="ไม่อนุมัติ..." ><i class="icon-ban-circle icon-white"></i> ไม่อนุมัติ</a>
	  	</div>
	</div>
	
	
