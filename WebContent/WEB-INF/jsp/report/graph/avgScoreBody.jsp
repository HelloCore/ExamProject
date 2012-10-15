<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/avgScore.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">กราฟ</font> คะแนนเฉลี่ยวิชา</h2>
</div>
<div class="btn-holder form-inline">
	<label> วิชา </label>
	<select id="courseId" name="courseId"></select>
	<button class="btn btn-info" id="drawGraphButton"><i class="icon-zoom-in icon-white"></i> ดูข้อมูล</button>
</div>
<div class="row">
	<div class="span12" id="mainLoader" style="display:none;">
		<hr>
		
		<div class="page-header" id="pageHeader">
			<h4>กราฟคะแนนเฉลี่ยโดยรวม</h4>
		</div>
		<div class="row">
			<div class="span12">
				<div id="mainDiv" style="height:500px;margin-top:0px;text-align:center;">
				</div>
			</div>
		</div>
		
		<div class="page-header" id="pageHeader">
			<h4>กราฟคะแนนเฉลี่ยแต่ละบทเรียน</h4>
		</div>
		<div class="row" id="subGraph">
			<div class="span2 sub-item">
				<div id="chapterXDiv" style="height:130px;">
				</div>
			</div>
		</div>
	</div>
</div>