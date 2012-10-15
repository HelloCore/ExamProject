<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/report/graphList.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">ข้อมูล</font> กราฟ</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<form class="form-horizontal">
  			<div class="control-group">
    			<label class="control-label">ข้อมูลกราฟ</label>
    			<div class="controls">
      				<label for="graphType1"> <input type="radio" name="graphType" id="graphType1" value="1" > ข้อมูลคะแนนเฉลี่ยในวิชา</label>
      				<label for="graphType2"> <input type="radio" name="graphType" id="graphType2" value="2" > ข้อมูลคะแนนสอบนักศึกษา</label>
    			</div>
  			</div>
  		</form>
	</div>
</div>
