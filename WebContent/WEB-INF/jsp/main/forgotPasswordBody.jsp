<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="${contextPath}/css/main/forgotPassword.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">ลืม</font> รหัสผ่าน</h2>
</div>

<div class="row-fluid">
	<div class="span12">
		<form class="form-horizontal forgot-password-form" id="forgotPasswordForm">
			 <div class="control-group">
			    <label class="control-label" for="studentId">รหัสนักศึกษา</label>
			    <div class="controls">
			      <input type="text" id="studentId" name="studentId" placeholder="Student ID" value="${studentId}">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="activeCode">E-mail</label>
			    <div class="controls">
			      <input type="text" id="email" name="email" placeholder="E-mail">
			    </div>
			 </div>
			 <div class="form-actions">
			  <button type="submit" class="btn btn-primary"><i class="icon-pencil icon-white"></i> reset password</button>
			  <button type="reset" class="btn">Cancel</button>
			</div>
		</form>
	</div>
</div>