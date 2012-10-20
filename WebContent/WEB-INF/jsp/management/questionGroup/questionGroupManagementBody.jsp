<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/questionGroup/questionGroupManagement.css">
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">จัดการ</font> บทเรียน</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="grid-toolbar ">
				<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> เพิ่ม</button>
				<a class="btn btn-primary" data-toggle="modal" href="#searchQuestionGroupModal"><i class="icon-zoom-in icon-white"></i> ค้นหา</a>
				<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
			</div>
			<table class="table table-striped table-bordered table-grid" id="questionGroupGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="questionGroupIdHeader">#<i></i></th>
						<th class="sort-both sortable" id="questionGroupNameHeader">ชื่อบทเรียน<i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา<i></i></th>
						<th class="action-column"></th>
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
		<h3>ลบบทเรียน ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการลบบทเรียนใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="กำลังลบ..." ><i class="icon-trash icon-white"></i> ลบ</a>
  	</div>
</div>

<div class="modal hide fade" id="questionGroupModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>เพิ่มบทเรียน</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="questionGroupForm">
    	<input type="hidden" id="questionGroupId" name="questionGroupId" />
    	<div class="control-group withComboBox">
	     	<label class="control-label" for="courseId">วิชา</label>
	      	<div class="controls">
	        	<select id="courseId" class="input-medium" data-placeholder="Choose a course..." name="courseId"></select>
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="questionGroupName">ชื่อบทเรียน</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="questionGroupName" name="questionGroupName">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">ปิด</a>
    <a href="#" class="btn btn-primary" id="saveButton" data-loading-text="กำลังบันทึก..."><i class="icon-pencil icon-white"></i>  บันทึก</a>
  </div>
</div>

<div class="modal hide fade" id="searchQuestionGroupModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>ค้นหาบทเรียน</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchQuestionGroupForm">
    	<div class="control-group">
	     	<label class="control-label" for="courseCodeSearch">รหัสวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCodeSearch" name="courseCodeSearch" />
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="questionGroupNameSearch">ชื่อบทเรียน</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="questionGroupNameSearch" name="questionGroupNameSearch">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">ปิด</a>
    <a href="#" class="btn btn-primary" id="searchButton"><i class="icon-zoom-in icon-white"></i> ค้นหา</a>
  </div>
</div>