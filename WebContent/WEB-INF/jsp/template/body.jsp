<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
	<head>
		<title><tiles:insertAttribute name="title"/></title>
		<tiles:insertAttribute name="head"/>
	</head>
	<body>
	<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
		<div class="navbar navbar-fixed-top navbar-inverse">
	    	<div class="navbar-inner">
	        	<div class="container">
	          		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
	          		</a>
	          		<a class="brand" href="${contextPath}/main/home.html">ExamProject</a>
					<sec:authorize access="isAnonymous()">
		          		<ul class="nav pull-right">
		          			<li class="signUp"><a href="${contextPath}/main/signUp.html">Sign Up</a></li>
		          			<li class="divider-vertical"></li>
		          			<li class="signIn dropdown"><a href="#signIn" class="dropdown-toggle" data-toggle="dropdown" >Sign In <b class="caret"></b></a>
		          				<form class="login-form dropdown-menu" action="${contextPath}/main/login.do" method="POST">
	            				<div class="control-group">
							      <label class="control-label" for="username">Student ID</label>
							      <div class="controls">
							        <input type="text" class="input-medium" id="username" name="username">
							      </div>
							    </div>
	            				<div class="control-group">
							      <label class="control-label" for="password">Password</label>
							      <div class="controls">
							      	<input type="hidden" id="miniLoginTarget" name="target" />
							        <input type="password" class="input-medium" id="password" name="password">
							      </div>
							    </div>
							    <div class="pagination-centered">
							    	<button type="submit" class="btn btn-primary login-btn">Sign In </button>
							    	<a href="#">forgot password ?</a>
							    </div>
	            			</form>
		          			</li>
		          		</ul>
	          		</sec:authorize>
					<sec:authorize access="isAuthenticated()">
							<div class="dropdown btn-group pull-right">
				           		<button class="btn dropdown-toggle" data-toggle="dropdown" id="personal-data">
				           			<i class="icon-user"></i> 
				           			<sec:authentication property="principal.user.firstName" />
									<sec:authentication property="principal.user.lastName" /> 
								    <b class="caret"></b>
							   	</button>
				           	 	<ul class="dropdown-menu">
				              		<sec:authorize access="hasRole('ROLE_NOT_ACTIVE')" >
				              			<li><a  href="${contextPath}/main/activeUser.html?studentId=<sec:authentication property="principal.user.username" />">Active User</a></li>
				              		</sec:authorize>
					          		<sec:authorize access="hasRole('ROLE_STUDENT')" >
				             	 		<li><a  href="${contextPath}/member/register.html">Register</a></li>
					          		</sec:authorize>
							        <li><a  href="#">Profile</a></li>
							        <li><a  href="#">Settings</a></li>
							        <li><a  href="#">Change Password</a></li>
							        <li class="divider"></li>
					          		<%-- <sec:authorize access="hasRole('ROLE_TEACHER')" >
					              		<li class="nav-header">จัดการข้อมูลวิชา</li>
								        <li><a  href="#"><i class="icon-ok"></i>  CS.101</a></li>
									    <li class="disabled"><a  href="#"><i class="icon-none"></i>  CS.102</a></li>
									    <li class="disabled"><a  href="#"><i class="icon-none"></i>  CS.103</a></li>
									    <li class="disabled"><a  href="#"><i class="icon-none"></i>  CS.104</a></li>
									    <li class="divider"></li>
				              		</sec:authorize> --%>
				              		<li><a  href="${contextPath}/main/logout.do">Sign Out</a></li>
				            	</ul>
				            </div>
					</sec:authorize>
	          		<div class="nav-collapse">
	            		<ul class="nav">
				            <li class="index"><a href="${contextPath}/main/home.html"> หน้าแรก</a></li>
				            <li class="news"><a href="${contextPath}/main/readMoreNews.html">ข่าว</a></li>
				            <sec:authorize access="hasRole('ROLE_STUDENT')" >
				            	<li class="content"><a  href="${contextPath}/main/content.html">เอกสารประกอบการเรียน</a></li>
				            </sec:authorize>
				            <sec:authorize access="hasRole('ROLE_NOT_ACTIVE')" >
				            	<li class="signUp"><a  href="${contextPath}/main/activeUser.html?studentId=<sec:authentication property="principal.user.username" />">ยืนยันการสมัครสมาชิก</a></li>
				            </sec:authorize>
				            <sec:authorize access="hasRole('ROLE_STUDENT')" >
								<li class="register"><a href="${contextPath}/member/register.html">ลงทะเบียน</a></li>
				            	<li class="dropdown exam">
								    <a href="#"
								          class="dropdown-toggle"
								          data-toggle="dropdown">
								          สอบ
								          <b class="caret"></b>
								    </a>
								    <ul class="dropdown-menu " >
								    	<li><a  href="${contextPath}/exam/selectExam.html">ทำการสอบ</a></li>
	    								<li><a  href="${contextPath}/exam/examReport.html">ผลการสอบ</a></li>
									</ul>
								</li>
								<li class="dropdown assignment">
								    <a href="#"
								          class="dropdown-toggle"
								          data-toggle="dropdown">
								          ส่งงาน
								          <b class="caret"></b>
								    </a>
								    <ul class="dropdown-menu" >
								    	<li><a  href="${contextPath}/assignment/select.html">รายการงาน</a></li>
	    								<li><a  href="${contextPath}/assignment/result.html">ประวัติการส่งงาน</a></li>
									</ul>
								</li>
				            </sec:authorize>
				            <sec:authorize access="hasRole('ROLE_TEACHER')">
								<li class="dropdown genericManagement">
								    <a href="#"
								          class="dropdown-toggle"
								          data-toggle="dropdown">
								          จัดการทั่วไป
								          <b class="caret"></b>
								    </a>
								    <ul class="dropdown-menu">
							    		<li><a  href="${contextPath}/management/register.html">อนุมัติสิทธิ์นักศึกษา</a></li>
							    		<li><a  href="${contextPath}/management/register/history.html">ประวัติการอนุมัติ</a></li>
							    		<li><a  href="${contextPath}/management/section.html">จัดการ Section</a></li>
							    		<li><a  href="${contextPath}/management/news.html">จัดการข่าวประชาสัมพันธ์</a></li>
							    		<li><a  href="${contextPath}/management/news/add.html">เพิ่มข่าวประชาสัมพันธ์</a></li>								    
    									<li><a  href="${contextPath}/main/content.html">เอกสารประกอบการเรียน</a></li>
									</ul>
								</li>
								<li class="dropdown examManagement">
								    <a href="#"
								          class="dropdown-toggle"
								          data-toggle="dropdown">
								          จัดการการสอบ
								          <b class="caret"></b>
								    </a>
								    <ul class="dropdown-menu">
	    								<li><a  href="${contextPath}/management/questionGroup.html">จัดการกลุ่มคำถาม</a></li>
	    								<li><a  href="${contextPath}/management/question.html">จัดการคำถาม</a></li>
	    								<li><a  href="${contextPath}/management/question/add.html">เพิ่มคำถาม</a></li>
	    								<li><a  href="${contextPath}/management/exam.html">จัดการการสอบ</a></li>
	    								<li><a  href="${contextPath}/management/exam/add.html">สร้างกำหนดการสอบ</a></li>
									</ul>
								</li>
								<li class="dropdown task">
								    <a href="#"
								          class="dropdown-toggle"
								          data-toggle="dropdown">
								          จัดการ Assignment
								          <b class="caret"></b>
								    </a>
								    <ul class="dropdown-menu">
	    								<li><a  href="${contextPath}/management/task.html">จัดการ Assignment</a></li>
	    								<li><a  href="${contextPath}/management/task/assign.html">สั่ง Assignment</a></li>
	    								<li><a  href="${contextPath}/management/task/taskList.html">ตรวจ Assignment</a></li>
									</ul>
								</li>
								<li class="dropdown report">
								    <a href="#"
								          class="dropdown-toggle"
								          data-toggle="dropdown">
								          รายงาน
								          <b class="caret"></b>
								    </a>
								    <ul class="dropdown-menu">
	    								<li><a  href="${contextPath}/report/dashboard.html">Dashboard</a></li>
	    								<li><a  href="${contextPath}/report/exam.html">ผลการสอบ</a></li>
	    								<li><a  href="${contextPath}/report/assignment.html">Assignment</a></li>
	    								<li><a  href="${contextPath}/report/student.html">ข้อมูลรายบุคคล</a></li>
									</ul>
								</li>
								
							</sec:authorize>
	            		</ul>
	          		</div>
	          		<!--/.nav-collapse -->
	        	</div>
	      	</div>
	    </div>
		<div class="container">
		
		   	<div class="container main-container">
				<img src="${contextPath}/images/logoPage.png" class="header-logo"/>
		   		<tiles:insertAttribute name="content"/>
		   	</div>
		   
		   	<hr>

	      	<footer class="pagination-centered footer">
	        	<p>&copy; Core 2012</p>
	      	</footer>
		</div> <!-- /container -->
		<div id="loadMask" style="display:none"></div>
		<!-- Placed at the end of the document so the pages load faster -->
			
		<script src="${contextPath}/resources/jquery-1.7.2.min.js"></script>
		<script>window.jQuery || document.write('<script src="${contextPath}/resources/jquery-1.7.2.min.js"><\/script>')</script>
		
		<script src="${contextPath}/resources/bootstrap/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/jquery.jgrowl.js"></script>
		<script src="${contextPath}/resources/jquery.blockUI.js"></script>
		<script src="${contextPath}/resources/spin.min.js"></script>
		<script src="${contextPath}/resources/jquery.spin.js"></script>
		<script src="${contextPath}/scripts/applicationScript.js"></script>
		<script src="${contextPath}/scripts/mainScript.js"></script>
		
		<sec:authorize access="isAuthenticated()">
			<script src="${contextPath}/resources/jquery.idletimer.js"></script>
			<script src="${contextPath}/resources/jquery.idletimeout.js"></script>
			<script src="${contextPath}/scripts/checkSession.js"></script>
		</sec:authorize>
		<!--  Google Analytics -->
			<script>
				var _gaq=[['_setAccount','UA-35105992-1'],['_trackPageview']];
				(function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
				g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
				s.parentNode.insertBefore(g,s)}(document,'script'));
			</script> 
		<tiles:insertAttribute name="footerFile"/>
		
	</body>
</html>