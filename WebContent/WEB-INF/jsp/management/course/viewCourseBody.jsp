<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/course/viewCourse.css">		
<script>
	application.courseId = ${courseData.courseId};
</script>
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">รายละเอียด</font> วิชา</h2>
</div>

<div class="row-fluid">
	<div class="span12">
		<h4>วิชา ${courseData.courseCode} ${courseData.courseName}</h4>
		<hr>
		<input type="hidden" id="courseId" name="courseId" value="${courseData.courseId}" />
		<div class="button-holder">
			<div class="form-inline" id="add-user-button-holder" style="display:none;">
				<label class="control-label" for="username">อาจารย์</label>
				<select id="username" class="input-xxlarge" data-placeholder="Choose a user..." name="username"></select>
		      	<button class="btn btn-primary" id="addTeacherButton"><i class="icon-pencil icon-white"></i> เพิ่ม</button>
		      	<button class="btn" id="cancelButton">ยกเลิก</button>
			</div>
			<div id="normal-button-holder">
		      	<button class="btn btn-info" id="addButon"><i class="icon-plus icon-white"></i> เพิ่มอาจารย์ผู้สอน</button>
			</div>
		</div>
		<table class="table table-striped table-bordered table-grid" id="teacherGrid">
			<thead>
				<tr>
					<th >ชื่อผู้ใช้</th>
					<th >ชื่อ-นามสกุล</th>
					<th >E-Mail</th>
					<th class="action-column" ></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>


<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>ลบอาจารย์ผู้สอน ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการลบอาจารย์ผู้สอนออกจากวิชานี้ใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="กำลังลบ..." ><i class="icon-trash icon-white"></i> ลบ</a>
  	</div>
</div>

<div class="modal hide fade" id="addTeacherConfirmModal">
	<div class="modal-header">
		<h3>เพิ่มอาจารย์ผู้สอน ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการเพิ่มอาจารย์ผู้สอนไปยังวิชานี้ใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-primary" id="addTeacherConfirmButton" data-loading-text="กำลังเพิ่ม..." ><i class="icon-pencil icon-white"></i> เพิ่ม</a>
  	</div>
</div>