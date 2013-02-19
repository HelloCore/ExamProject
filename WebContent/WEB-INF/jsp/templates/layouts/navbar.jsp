<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

		<div class="navbar navbar-fixed-top navbar-inverse">
	    	<div class="navbar-inner">
	        	<div class="container">
	          		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
	          		</a>
	          		<%-- <a class="brand" href="${contextPath}/main/home.html">ExamProject</a> --%>
					<sec:authorize access="isAnonymous()">
		          		<ul class="nav pull-right">
		          			<li class="signUp"><a href="${contextPath}/main/signUp.html">สมัครสมาชิก</a></li>
		          			<li class="divider-vertical"></li>
		          			<li class="signIn dropdown"><a href="#signIn" class="dropdown-toggle" data-toggle="dropdown" >เข้าสู่ระบบ <b class="caret"></b></a>
		          				<form class="login-form dropdown-menu" action="${contextPath}/main/login.do" method="POST">
	            				<div class="control-group">
							      <label class="control-label" for="username">รหัสนักศึกษา</label>
							      <div class="controls">
							        <input type="text" class="input-medium" id="username" name="username">
							      </div>
							    </div>
	            				<div class="control-group">
							      <label class="control-label" for="password">รหัสผ่าน</label>
							      <div class="controls">
							      	<input type="hidden" id="miniLoginTarget" name="target" />
							        <input type="password" class="input-medium" id="password" name="password">
							      </div>
							    </div>
							    <div class="pagination-centered">
							    	<button type="submit" class="btn btn-primary login-btn">เข้าสู่ระบบ</button>
							    	<br>
							    	<a href="${contextPath}/main/forgotPassword.html">ลืมรหัสผ่าน ?</a>
							    </div>
	            			</form>
		          			</li>
		          		</ul>
	          		</sec:authorize>
					<sec:authorize access="isAuthenticated()">
							<div class="dropdown btn-group pull-right">
				           		<button class="btn dropdown-toggle" data-toggle="dropdown" id="personal-data">
				           			<i class="icon-user"></i> 
				           			<sec:authentication property="principal.user.prefixName.prefixNameTh" />
				           			<sec:authentication property="principal.user.firstName" />
									<sec:authentication property="principal.user.lastName" /> 
								    <b class="caret"></b>
							   	</button>
				           	 	<ul class="dropdown-menu">
				              		<sec:authorize access="hasRole('ROLE_NOT_ACTIVE')" >
				              			<li><a  href="${contextPath}/main/activeUser.html?studentId=<sec:authentication property="principal.user.username" />">Active User</a></li>
				              		</sec:authorize>
					          		<sec:authorize access="hasRole('ROLE_STUDENT')" >
				             	 		<li><a  href="${contextPath}/member/register.html">ลงทะเบียน</a></li>
					          		</sec:authorize>
							        <li><a  href="${contextPath}/member/editPersonalData.html">ข้อมูลส่วนตัว</a></li>
							        <li><a  href="${contextPath}/member/changePassword.html">เปลี่ยนรหัสผ่าน</a></li>
							        <li class="divider"></li>
				              		<li><a  href="${contextPath}/main/logout.do">ออกจากระบบ</a></li>
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
				            <sec:authorize access="hasRole('ROLE_ADMIN')">
				            	<li class="courseManagement"><a href="${contextPath}/management/course.html">จัดการข้อมูลวิชา</a></li>
				            	<li class="addTeacher"><a href="${contextPath}/main/signUpTeacher.html">ลงทะเบียนอาจารย์</a></li>
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
	    								<li><a  href="${contextPath}/management/questionGroup.html">จัดการบทเรียน</a></li>
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
	    								<li><a  href="${contextPath}/report/avgScore.html">กราฟคะแนนเฉลี่ยวิชา</a></li>
	    								<li><a  href="${contextPath}/report/customReport.html">สรุปคะแนน</a></li>
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