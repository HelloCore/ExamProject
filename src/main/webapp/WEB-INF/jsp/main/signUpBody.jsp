<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/css/main/signUp.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Sign</font> Up</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<form class="form-horizontal sign-up-form" >
			 <div class="control-group">
			    <label class="control-label" for="studentId">รหัสนักศึกษา</label>
			    <div class="controls">
			      <input type="text" id="studentId" placeholder="Student ID">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="password">รหัสผ่าน</label>
			    <div class="controls">
			      <input type="password" id="password" placeholder="Password">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="repassword">รหัสผ่านอีกครั้ง</label>
			    <div class="controls">
			      <input type="password" id="repassword" placeholder="Re Password">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="email">E-mail</label>
			    <div class="controls">
			      <input type="email" id="email" placeholder="E-mail">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="firstName">ชื่อ</label>
			    <div class="controls">
			      <input type="text" id="firstName" placeholder="First name">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="lastName">นามสกุล</label>
			    <div class="controls">
			      <input type="text" id="lastName" placeholder="Last name">
			    </div>
			 </div>
			 <div class="form-actions">
			  <button type="submit" class="btn btn-primary">Sign Up</button>
			  <button type="button" class="btn">Cancel</button>
			</div>
		</form>
	</div>
</div>