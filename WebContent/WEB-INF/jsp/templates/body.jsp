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
		
		<tiles:insertAttribute name="navbar"/>
		<div class="container">
		
		   	<div class="container main-container">
				<a href="${contextPath}"><img src="${contextPath}/images/logoPage.png" class="header-logo"/></a>
		   		<tiles:insertAttribute name="content"/>
		   	</div>
		   
		   	<hr>

			<tiles:insertAttribute name="footer"/>
	   </div> <!-- /container -->
		<div id="loadMask" style="display:none"></div>
		<!-- Placed at the end of the document so the pages load faster -->
			
		<script src="${contextPath}/resources/jquery-1.7.2.min.js"></script>
		<script>window.jQuery || document.write('<script src="${contextPath}/resources/jquery-1.7.2.min.js"><\/script>')</script>
		
		<script src="${contextPath}/resources/bootstrap/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/jquery.jgrowl.js"></script>
		<script src="${contextPath}/resources/jquery.blockUI.js"></script>
		<script src="${contextPath}/resources/spin.min.js"></script>
		<script src="${contextPath}/resources/jquery.tmpl.min.js"></script>
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