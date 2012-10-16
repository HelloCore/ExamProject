<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/section/sectionManagement.css">		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">จัดการ</font> Section</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="grid-toolbar">
				<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> Add</button>
				<a class="btn btn-primary" data-toggle="modal" href="#searchSectionModal"><i class="icon-zoom-in icon-white"></i> Search</a>
				<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
			</div>
			<table class="table table-striped table-bordered table-grid" id="sectionGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="sectionNameHeader">Section<i></i></th>
						<th class="sort-both sortable" id="sectionYearHeader">ปีการศึกษา<i></i></th>
						<th class="sort-both sortable" id="sectionSemesterHeader">ภาคเรียน<i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">วิชา<i></i></th>
						<th class="sort-both sortable" id="statusHeader">สถานะ<i></i></th>
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
				 	</select> items per page
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
		<h3>Delete Section ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
  	</div>
</div>

<div class="modal hide fade" id="sectionModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Add Section</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="sectionForm">
    	<input type="hidden" id="sectionId" name="sectionId" />
    	<div class="control-group withComboBox">
	     	<label class="control-label" for="courseId">วิชา</label>
	      	<div class="controls">
	        	<select id="courseId" class="input-medium" data-placeholder="Choose a course..." name="courseId"></select>
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
	        	<input type="text" class="input-medium numeric" id="sectionYear" name="sectionYear">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionSemester">ภาคเรียนที่</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionSemester" name="sectionSemester">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="status">สถานะ</label>
	      	<div class="controls status-controls">
	        	<label for="statusInActive" ><input type="radio" name="status" id="statusInActive" value="0"/> Inactive</label>
	        	<label for="statusActive" ><input type="radio" name="status" id="statusActive" value="1" checked="checked" /> Active</label>
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="saveButton" data-loading-text="Saving..."><i class="icon-pencil icon-white"></i> Save changes</a>
  </div>
</div>

<div class="modal hide fade" id="searchSectionModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Search Section</h3>
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
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="searchButton"><i class="icon-zoom-in icon-white"></i> Search</a>
  </div>
</div>