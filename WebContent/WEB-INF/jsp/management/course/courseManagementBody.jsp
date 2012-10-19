<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/css/management/course/courseManagement.css">		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">จัดการ</font> วิชา</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="grid-toolbar">
				<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> เพิ่ม</button>
				<a class="btn btn-primary" data-toggle="modal" href="#searchCourseModal"><i class="icon-zoom-in icon-white"></i> ค้นหา</a>
				<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
			</div>
			<table class="table table-striped table-bordered table-grid" id="courseGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="courseCodeHeader">รหัสวิชา <i></i></th>
						<th class="sort-both sortable" id="courseNameHeader">ชื่อวิชา <i></i></th>
						<th class="action-column" ></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="row-fluid">
				<div class="span3">
					<div class="grid-info" id="gridInfo"></div>
				</div>
				<div class="span4 page-size-div">
					<select id="pageSize" name="pageSize" class="page-size ">
						<option value="5">5</option>
				 		<option value="10">10</option>
				 		<option value="20">20</option>
				 		<option value="50">50</option>
				 	</select> รายการต่อหน้า
				</div>
				<div class="span5">
					<div class="grid-pagination pagination pagination-centered">
						<ul>
							<li class="prev disabled"><a href="#" id="prevPageButton">«</a></li>
							<li class="next"><a href="#" id="nextPageButton">»</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>


<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>ลบวิชา ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการลบวิชาใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="กำลังลบ..." ><i class="icon-trash icon-white"></i> ลบ</a>
  	</div>
</div>

<div class="modal hide fade" id="courseModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>เพิ่มวิชา</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="courseForm">
    	<input type="hidden" id="courseId" name="courseId" />
    	<div class="control-group">
	     	<label class="control-label" for="courseCode">รหัสวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCode" name="courseCode">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="courseName">ชื่อวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-xlarge" id="courseName" name="courseName">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">ปิด</a>
    <a href="#" class="btn btn-primary" id="saveButton" data-loading-text="กำลังบันทึก..."><i class="icon-pencil icon-white"></i> บันทึก</a>
  </div>
</div>

<div class="modal hide fade" id="searchCourseModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>ค้นหาวิชา</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchCourseForm">
    	<div class="control-group">
	     	<label class="control-label" for="courseCodeSearch">รหัสวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCodeSearch" name="courseCode" />
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="courseNameSearch">ชื่อวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-xlarge" id="courseNameSearch" name="courseName" />
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">ปิด</a>
    <a href="#" class="btn btn-primary" id="searchButton"><i class="icon-zoom-in icon-white"></i> ค้นหา</a>
  </div>
</div>

<form id="viewDetailCourseForm" action="${contextPath}/management/course/view.html" method="POST">
	<input type="hidden" id="viewCourseId" name="courseId" />
	<input type="hidden" name="method" value="viewCourseDetail" />
</form>