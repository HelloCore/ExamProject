<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/section/sectionManagement.css">
<div>
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Section</font> Management</h2>
	</div>
	<div class="grid-toolbar ">
		 
		<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> Add</button>
		<a class="btn btn-primary" data-toggle="modal" href="#searchSectionModal"><i class="icon-zoom-in icon-white"></i> Search</a>
		<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
	</div>
	<table class="table table-striped table-bordered table-grid" id="sectionGrid">
		<thead>
			<tr>
				<th class="sort-both sortable" id="sectionIdHeader">Section ID</th>
				<th class="sort-both sortable" id="sectionNameHeader">Section Name</th>
				<th class="sort-both sortable" id="sectionYearHeader">Section Year</th>
				<th class="sort-both sortable" id="sectionSemesterHeader">Section Semester</th>
				<th class="sort-both sortable" id="courseCodeHeader">Course Code</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<div class="row">
		<div class="span4">
			<div class="grid-info" id="gridInfo"></div>
		</div>
		<div class="span4">
			<select id="pageSize" name="pageSize" class="page-size">
		 	<option value="5">5</option>
		 	<option value="10">10</option>
		 	<option value="20">20</option>
		 	<option value="50">50</option>
		 </select> records per page
		</div>
		<div class="span4">
			<div class="grid-pagination pagination pull-right">
				<ul>
					<li class="prev disabled"><a href="#" id="prevPageButton">&larr; Prev</a></li>
					<li class="next"><a href="#" id="nextPageButton">Next &rarr;</a></li>
				</ul>
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
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="Deleting..." >Delete</a>
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
    	<div class="control-group">
	     	<label class="control-label" for="courseId">Course Code</label>
	      	<div class="controls">
	        	<select id="courseId" class="input-medium" data-placeholder="Choose a course..." name="courseId"></select>
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionName">Section Name</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="sectionName" name="sectionName">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionYear">Section Year</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionYear" name="sectionYear">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionSemester">Section Semester</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionSemester" name="sectionSemester">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="saveButton" data-loading-text="Saving...">Save changes</a>
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
	     	<label class="control-label" for="courseCodeSearch">Course Code</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCodeSearch" name="courseCodeSearch" />
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionNameSearch">Section Name</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="sectionNameSearch" name="sectionNameSearch">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionYearSearch">Section Year</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionYearSearch" name="sectionYearSearch">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="sectionSemesterSearch">Section Semester</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="sectionSemesterSearch" name="sectionSemesterSearch">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="searchButton">Search</a>
  </div>
</div>