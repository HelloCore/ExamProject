<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/examGraph.css">
<script>
	application.examScoreData = ${examScoreData};
	application.sectionData = ${sectionData};
</script>
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">กราฟ</font> คะแนนสอบ</h2>
</div>
<div class="row">
	<div class="span12" id="mainLoader" style="display:none;">
		<div class="page-header" id="pageHeader">
			<h4>กราฟคะแนนสอบ</h4>
		</div>
		<div class="row">
			<div class="span12">
				<div id="mainDiv" style="height:500px;margin-top:0px;text-align:center;">
				</div>
			</div>
		</div>
	</div>
</div>