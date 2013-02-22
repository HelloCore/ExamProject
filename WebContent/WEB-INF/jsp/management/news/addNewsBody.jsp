<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/select2/select2.css" />
<link rel="stylesheet" href="${contextPath}/css/management/news/addNews.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">เพิ่ม </font> ข่าวประชาสัมพันธ์</h2>
	</div>
	<div class="fixed-content" >
		<div class="control-group">
	    	<label class="control-label" for="courseId">วิชา</label>
	    	<div class="controls">
				<select id="courseId" name="courseId" class="input-large"></select>
	    	</div>
	  	</div>
		<div class="control-group">
	    	<label class="control-label" for="newsHeader">หัวข้อข่าว</label>
	    	<div class="controls">
				<input type="text" id="newsHeader" name="newsHeader" class="input-xxlarge"/>
	    	</div>
	  	</div>
		<div class="control-group">
	    	<label class="control-label" for="newsContent">เนื้อหาข่าว</label>
	    	<div class="controls">
				<textarea id="newsContent" name="newsContent" class="ckeditorarea"></textarea>
	    	</div>
	  	</div>
	  	<div class="button-holder pagination-centered">
	  		<button class="btn btn-primary" id="addButton"><i class="icon-pencil icon-white"></i> เพิ่ม</button>
	  	</div>
	</div>
</div>


<div class="modal hide fade" id="confirmAddNewsModal">
	<div class="modal-header">
		<h3>เพิ่มข่าว ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการเพิ่มข่าวใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-primary" id="confirmAddButton" data-loading-text="กำลังเพิ่ม..." ><i class="icon-pencil icon-white"></i> เพิ่ม</a>
  	</div>
</div>
