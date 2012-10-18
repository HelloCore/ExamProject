<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<link rel="stylesheet" href="${contextPath}/css/main/login.css">


<div class="well pagination-centered login-main-Form">
	<c:if test="${not empty error}">
		<div class="alert alert-error pagination-centered ">
			<button type="button" class="close" data-dismiss="alert">x</button>
			เกิดข้อผิดพลาด <br>เนื่องจาก : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	<form action="${contextPath}/main/login.do" method="POST">
		<c:if test="${not empty target}">
			<input type="hidden" name="target" value="${target}"/>
		</c:if>
		<c:if test="${empty target}">
			<input type="hidden" name="target" value="/main/home.html"/>
		</c:if>
		<div class="control-group">
	    	<!-- <label class="control-label" for="studentIdForm">Student ID</label> -->
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="studentIdForm" name="username" placeholder="รหัสนักศึกษา" value="${studentId}" }>
	     	</div>
	    </div>
       	<div class="control-group">
	    	<!-- <label class="control-label" for="passwordForm">Password</label> -->
	      	<div class="controls">
	        	<input type="password" class="input-medium" id="passwordForm" name="password" placeholder="รหัสผ่าน">
	     	</div>
	    </div>
	    <div class="pagination-centered">
	    	<button type="submit" class="btn btn-primary login-btn">เข้าสู่ระบบ </button>
	    </div>
	</form>
	<a href="${contextPath}/main/forgotPassword.html">ลืมรหัสผ่าน ?</a><br>
	ยังไม่มีรหัสใช้งาน ? <a href="${contextPath}/main/signUp.html">สมัครสมาชิก</a>
</div>