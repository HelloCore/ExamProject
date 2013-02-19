<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/section/sectionManagement.css">
<script>
	application.masterSectionId = ${masterSectionData.masterSectionId};
	application.sectionSemester = ${masterSectionData.sectionSemester};
	application.sectionYear = ${masterSectionData.sectionYear};
</script>
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">จัดการ</font> Section</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="grid-toolbar">
				<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> เพิ่ม</button>
				<a class="btn btn-primary" data-toggle="modal" href="#searchSectionModal"><i class="icon-zoom-in icon-white"></i> ค้นหา</a>
				<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
			</div>
			<table class="table table-striped table-bordered table-grid" id="sectionGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="sectionYearHeader">ปีการศึกษา<i></i></th>
						<th class="sort-both sortable" id="sectionSemesterHeader">ภาคเรียน<i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา<i></i></th>
						<th class="sort-both sortable" id="sectionNameHeader">Section<i></i></th>
						<th class="sort-both sortable" id="statusHeader">สถานะ<i></i></th>
						<th class="action-column" ></th>
					</tr>
				</thead>
				<tbody>
					<script id="recordTemplate" type="text/x-jquery-tmpl">
						<tr>
							<td id="section-year-{{= sectionId}}">{{= sectionYear}}</td>
							<td id="section-semester-{{= sectionId}}">{{= sectionSemester}}</td>
							<td id="course-code-{{= sectionId}}">{{= courseCode}}</td>
							<td id="section-name-{{= sectionId}}">{{= sectionName}}</td>
							<td>
								<input type="hidden" id="status-{{= sectionId}}" value="{{= status}}">
								{{if status==0}}
									<span class="label label-important"><i class="icon-ban-circle icon-white"></i> ปิดการใช้งาน</span>
								{{else}}
									<span class="label label-success"><i class="icon-ok icon-white"></i> เปิดใช้งาน</span>
								{{/if}}
							</td>
							<td>
								<button class="btn btn-info" onclick="editSection({{= sectionId}},{{= masterSectionId}})"><i class="icon-edit icon-white"></i> แก้ไข</button>
								 <button class="btn btn-danger" onclick="deleteSection({{= sectionId}})"><i class="icon-trash icon-white"></i> ลบ</button> 
							</td>
						</tr>	
					</script>
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
		<h3>ลบ Section ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการลบ Section ใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="กำลังลบ..." ><i class="icon-trash icon-white"></i> ลบ</a>
  	</div>
</div>

<div class="modal hide fade" id="sectionModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>เพิ่ม Section</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="sectionForm">
    	<input type="hidden" id="sectionId" name="sectionId" />
    	<input type="hidden" id="masterSectionId" name="masterSectionId" value="${masterSectionData.masterSectionId}"/>
    	<div class="control-group withComboBox">
	     	<label class="control-label" for="courseId">วิชา</label>
	      	<div class="controls">
	        	<select id="courseId" class="input-medium" data-placeholder="กรุณาเลือกวิชา" name="courseId"></select>
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionName">ชื่อ Section</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="sectionName" name="sectionName">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionYear">ปีการศึกษา</label>
	      	<div class="controls">
	      		<span class="input-medium uneditable-input" id="sectionYear">${masterSectionData.sectionYear}</span>
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionSemester">ภาคเรียนที่</label>
	      	<div class="controls">
	      		<span class="input-medium uneditable-input" id="sectionSemester">${masterSectionData.sectionSemester}</span>
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">ปิด</a>
    <a href="#" class="btn btn-primary" id="saveButton" data-loading-text="กำลังบันทึก..."><i class="icon-pencil icon-white"></i> บันทึก</a>
  </div>
</div>

<div class="modal hide fade" id="searchSectionModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>ค้นหา Section</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchSectionForm">
    	<div class="control-group">
	     	<label class="control-label" for="courseCodeSearch">รหัสวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCodeSearch" name="courseCodeSearch" />
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionNameSearch">ชื่อ Section</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="sectionNameSearch" name="sectionNameSearch">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionYearSearch">ปีการศึกษา</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionYearSearch" name="sectionYearSearch">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionSemesterSearch">ภาคเรียนที่</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionSemesterSearch" name="sectionSemesterSearch">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">ปิด</a>
    <a href="#" class="btn btn-primary" id="searchButton"><i class="icon-zoom-in icon-white"></i> ค้นหา</a>
  </div>
</div>