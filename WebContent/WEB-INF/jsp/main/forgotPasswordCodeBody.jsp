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
			    <label class="control-label" for="activeCode">รหัสยืนยัน</label>
			    <div class="controls">
			      <input type="text" id="activeCode" name="activeCode" placeholder="Active Code">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="newPassword">รหัสผ่าน</label>
			    <div class="controls">
			      <input type="password" id="newPassword" name="newPassword" placeholder="New Password">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="retypePassword">รหัสผ่านอีกครั้ง</label>
			    <div class="controls">
			      <input type="password" id="retypePassword" name="retypePassword" placeholder="Retype Password">
			    </div>
			 </div>
			 <div class="form-actions">
			  <button type="submit" class="btn btn-primary"><i class="icon-pencil icon-white"></i> Active</button>
			  <button type="reset" class="btn">Cancel</button>
			  <div class="link-holder">
				 <sec:authorize access="isAuthenticated()">
			 	     <a href="${contextPath}/main/forgotPassword.html?studentId=<sec:authentication property="principal.user.username" />">ขอรหัสยืนยันอีกครั้ง</a>
			 	 </sec:authorize>
				 <sec:authorize access="isAnonymous()">
				     <a href="${contextPath}/main/forgotPassword.html">ขอรหัสยืนยันอีกครั้ง</a>
			 	 </sec:authorize>
			  </div>
			</div>
		</form>
	</div>
</div>