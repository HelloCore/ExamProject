<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${contextPath}/css/member/changePassword.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">เปลี่ยน</font> รหัสผ่าน</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<form class="form-horizontal change-password-form" id="changePasswordForm">
			<div class="control-group">
	     		<label class="control-label" for="oldPassword">รหัสผ่านเก่า</label>
	      		<div class="controls">
	        		<input type="password" class="input-large" id="oldPassword" name="oldPassword" placeholder="Old Password">
	        	</div>
	    	</div>
			<div class="control-group">
	     		<label class="control-label" for="newPassword">รหัสผ่านใหม่</label>
	      		<div class="controls">
	        		<input type="password" class="input-large" id="newPassword" name="newPassword" placeholder="New Password">
	        	</div>
	    	</div>
			<div class="control-group">
	     		<label class="control-label" for="newPassword">รหัสผ่านใหม่อีกครั้ง</label>
	      		<div class="controls">
	        		<input type="password" class="input-large" id="retypePassword" name="retypePassword" placeholder="Retype Password">
	        	</div>
	    	</div>
	    	<div class="pagination-centered">
	    		<button class="btn btn-primary" id="changePasswordButton"><i class="icon-pencil icon-white"></i> เปลี่ยนรหัสผ่าน</button>
	    	</div>
		</form>
	</div>
</div>

<div class="modal hide fade" id="changePasswordConfirmModal">
	<div class="modal-header">
		<h3>Change Password ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการเปลี่ยนรหัสผ่านใช่หรือไม่ โปรดยืนยัน ?
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-primary" id="changePasswordConfirmButton" data-loading-text="เปลี่ยนรหัสผ่าน..." ><i class="icon-pencil icon-white"></i> เปลี่ยนรหัสผ่าน</a>
  	</div>
</div>
