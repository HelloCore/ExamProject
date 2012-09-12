<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/member/register.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Register</font> </h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="button-holder">
			<div class="form-inline" id="register-button-holder" style="display:none;">
				<label class="control-label" for="courseId">วิชา</label>
				<select id="courseId" class="input-medium" data-placeholder="Choose a course..." name="courseId"></select>
				<label class="control-label" for="sectionId">Section</label>
				<select id="sectionId" class="input-medium" data-placeholder="Choose a section..." name="sectionId"></select>
		      	<button class="btn btn-primary" id="registerModalButton"><i class="icon-pencil icon-white"></i> Register</button>
		      	<button class="btn" id="cancelButton">Cancel</button>
			</div>
			<div id="normal-button-holder">
		      	<button class="btn btn-info" id="registerButton"><i class="icon-plus icon-white"></i> Register</button>
			</div>
		</div>
		<table class="table table-striped table-hover" id="registerTable">
			<thead>
				<tr>
					<th>เมื่อ</th>
					<th>วิชา</th>
					<th>Section</th>
					<th>ภาคเรียน</th>
					<th>สถานะ</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>

<div class="modal hide fade" id="confirmRegisterModal">
	<div class="modal-header">
   	 <button type="button" class="close" data-dismiss="modal">×</button>
		<h3>ลงทะเบียน ?</h3>
	</div>
	<div class="modal-body">
		
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-primary" id="confirmRegisterButton" data-loading-text="Register..." ><i class="icon-pencil icon-white"></i> Register</a>
  	</div>
</div>

<div class="modal hide fade" id="confirmCancelModal">
	<div class="modal-header">
		<h3>ยกเลิกการลงทะเบียน ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการยกเลิกการลงทะเบียนใช่หรือไม่
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="confirmCancelButton" data-loading-text="ยกเลิก..." ><i class="icon-trash icon-white"></i> ยกเลิก</a>
  	</div>
</div>
