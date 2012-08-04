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
		<link rel="stylesheet/less" href="${contextPath}<tiles:getAsString name="less"/>" />
		<script src="${contextPath}/resources/less-1.3.0.min.js"></script>
	</head>
	<body>
	<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
		<div class="container">
	    	<header class="subheader">
				<div class="navbar navbar-fixed-top">
			    	<div class="navbar-inner">
			        	<div class="container-fluid">
			          		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					            <span class="icon-bar"></span>
					            <span class="icon-bar"></span>
					            <span class="icon-bar"></span>
			          		</a>
			          		<a class="brand" href="${contextPath}/main/index.html">ExamProject</a>
							<sec:authorize access="isAnonymous()">
				          		<ul class="nav pull-right">
				          			<li class="signUp"><a href="#signUp">Sign Up</a></li>
				          			<li class="divider-vertical"></li>
				          			<li class="signIn dropdown"><a href="#signIn" class="dropdown-toggle" data-toggle="dropdown" >Sign In <b class="caret"></b></a>
				          				<form class="login-form dropdown-menu" action="${contextPath}/main/login.do" method="POST">
			            				<div class="control-group">
									      <label class="control-label" for="studentId">Student ID</label>
									      <div class="controls">
									        <input type="text" class="input-medium" id="studentId" name="username">
									      </div>
									    </div>
			            				<div class="control-group">
									      <label class="control-label" for="password">Password</label>
									      <div class="controls">
									        <input type="password" class="input-medium" id="password" name="password">
									      </div>
									    </div>
									    <div class="pagination-centered">
									    	<button type="submit" class="btn btn-primary login-btn">Sign In </button>
									    </div>
			            			</form>
				          			</li>
				          		</ul>
			          		</sec:authorize>
							<sec:authorize access="isAuthenticated()">
								<div class="btn-group pull-right">
						            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						              <i class="icon-user"></i> 
						              	<sec:authentication property="principal.user.firstName" />
										<sec:authentication property="principal.user.lastName" />
						              <span class="caret"></span>
						            </a>
						            <ul class="dropdown-menu">
						              <li><a href="#">Profile</a></li>
						              <li class="divider"></li>
						              <li><a href="${contextPath}/main/logout.do">Sign Out</a></li>
						            </ul>
						    	</div>
							</sec:authorize>
			          		<div class="nav-collapse">
			            		<ul class="nav">
						            <li class="index"><a href="${contextPath}/main/home.html"> Home</a></li>
						            <li class="news"><a href="#news">News</a></li>
						            <li class="contact"><a href="#contact">Contact</a></li>
			            		</ul>
			          		</div>
			          		<!--/.nav-collapse -->
			        	</div>
			      	</div>
			    </div>
			</header>
			<br>
		
		   	<div class="container main-container">
				<img src="${contextPath}/images/logoPage.png" class="header-logo"/>
		   		<tiles:insertAttribute name="content"/>
		   	</div>
		   
		   	<hr>

	      	<footer class="pagination-centered">
	        	<p>&copy; Core 2012</p>
	      	</footer>
			</div> <!-- /container -->
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script>window.jQuery || document.write('<script src="${contextPath}/resources/jquery-1.7.2.min.js"><\/script>')</script>
		<script src="${contextPath}/resources/bootstrap/bootstrap.min.js"></script>
		<script src="${contextPath}/scripts/mainScript.js"></script>
		<script>
			var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
			(function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
			g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
			s.parentNode.insertBefore(g,s)}(document,'script'));
		</script>
		<tiles:insertAttribute name="footerFile"/>
		
	</body>
</html>