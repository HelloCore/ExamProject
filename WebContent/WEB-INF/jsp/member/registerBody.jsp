<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/member/register.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2>ลงทะเบียน</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="button-holder">
			<div class="form-inline" id="register-button-holder" style="display:none;">
				<label class="control-label" for="courseSectionId">Section</label>
				<select id="courseSectionId" class="input-medium" data-placeholder="Choose a section..." name="courseSectionId"></select>
		      	<button class="btn btn-primary" id="registerModalButton"><i class="icon-pencil icon-white"></i> ลงทะเบียน</button>
		      	<button class="btn btn-primary" id="changeSectionModalButton"><i class="icon-pencil icon-white"></i> ย้าย Section</button>
		      	<button class="btn" id="cancelButton">ยกเลิก</button>
			</div>
			<div id="normal-button-holder">
		      	<button class="btn btn-primary" id="registerButton"><i class="icon-pencil icon-white"></i> ลงทะเบียน</button>
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
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-primary" id="confirmRegisterButton" data-loading-text="กำลังลงทะเบียน..." ><i class="icon-pencil icon-white"></i> ลงทะเบียน</a>
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
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-danger" id="confirmCancelButton" data-loading-text="กำลังยกเลิก..." ><i class="icon-trash icon-white"></i> ยกเลิก</a>
  	</div>
</div>

<div class="modal hide fade" id="confirmChangeSectionModal">
	<div class="modal-header">
		<h3>ย้าย Section ?</h3>
	</div>
	<div class="modal-body">
		<font class="error">* สิทธิ์ใน Section เก่าจะถูกลบหากทำการย้าย Section และไม่สามารถยกเลิกได้</font>
		คุณต้องการย้าย Section ใช่หรือไม่ ?
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-info" id="confirmChangeSectionButton" data-loading-text="ย้าย..." ><i class="icon-edit icon-white"></i> ย้าย</a>
  	</div>
</div>