<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/css/main/signUp.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">สมัคร</font> สมาชิก</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<form class="form-horizontal sign-up-form" id="signUpForm">
			 <div class="control-group">
			    <label class="control-label" for="studentId">รหัสนักศึกษา</label>
			    <div class="controls">
			      <input type="text" id="studentId" name="studentId" placeholder="Student ID">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="regPassword">รหัสผ่าน</label>
			    <div class="controls">
			      <input type="password" id="regPassword" name="password" placeholder="Password">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="repassword">ยืนยันรหัสผ่าน</label>
			    <div class="controls">
			      <input type="password" id="rePassword" name="rePassword" placeholder="Retype Password">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="email">E-mail</label>
			    <div class="controls">
			      <input type="email" id="email" name="email" placeholder="E-mail">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="prefixName">คำนำหน้าชื่ิอ</label>
			    <div class="controls">
			    	<select id="prefixName" name="prefixName">
			    		<option></option>
			    		<option value="1">นาย</option>
			    		<option value="2">นาง</option>
			    		<option value="3">นางสาว</option>
			    	</select>
				</div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="firstName">ชื่อ (ภาษาไทย)</label>
			    <div class="controls">
			      <input type="text" id="firstName" name="firstName" placeholder="First name">
			    </div>
			 </div>
			 <div class="control-group">
			    <label class="control-label" for="lastName">นามสกุล (ภาษาไทย)</label>
			    <div class="controls">
			      <input type="text" id="lastName" name="lastName" placeholder="Last name">
			    </div>
			 </div>
			 <div class="form-actions">
			  <button type="submit" class="btn btn-primary"><i class="icon-pencil icon-white"></i> สมัครสมาชิก</button>
			  <button type="reset" class="btn">ยกเลิก</button>
			</div>
		</form>
	</div>
</div>


<div class="modal hide fade" id="confirmSignUpModal">
	<div class="modal-header">
		<h3>สมัครสมาชิก ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการสมัครสมาชิกใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-primary" id="confirmSignUpButton" data-loading-text="กำลังสมัคร..." ><i class="icon-pencil icon-white"></i> สมัครสมาชิก</a>
  	</div>
</div>

