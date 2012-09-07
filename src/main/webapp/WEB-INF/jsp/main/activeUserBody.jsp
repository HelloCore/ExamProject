<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/css/main/activeUser.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Active</font> User</h2>
</div>

<div class="row-fluid">
	<div class="span12">
		<form class="form-horizontal active-user-form" id="activeUserForm">
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
			 <div class="form-actions">
			  <button type="submit" class="btn btn-primary"><i class="icon-pencil icon-white"></i> Sign Up</button>
			  <button type="button" class="btn">Cancel</button>
			</div>
		</form>
	</div>
</div>