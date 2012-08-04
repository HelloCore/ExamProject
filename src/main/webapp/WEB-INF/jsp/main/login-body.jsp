<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>


<div class="well pagination-centered login-main-Form">
	<c:if test="${not empty error}">
		<div class="alert alert-error pagination-centered ">
			<button type="button" class="close" data-dismiss="alert">x</button>
			Login Error <br>Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	<form action="${contextPath}/main/login.do" method="POST">
		<div class="control-group">
	    	<label class="control-label" for="studentIdForm">Student ID</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="studentIdForm" name="username">
	     	</div>
	    </div>
       	<div class="control-group">
	    	<label class="control-label" for="passwordForm">Password</label>
	      	<div class="controls">
	        	<input type="password" class="input-medium" id="passwordForm" name="password">
	     	</div>
	    </div>
	    <div class="pagination-centered">
	    	<button type="submit" class="btn btn-primary login-btn">Sign In </button>
	    </div>
	</form>
	<a href="#">forgot password ?</a><br>
	dont have account ? <a href="#">sign up</a>
</div>