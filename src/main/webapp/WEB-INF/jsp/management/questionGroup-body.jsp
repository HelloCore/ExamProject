<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/questionGroup.css">
<div>
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Question Group</font> Management</h2>
	</div>
	<div class="grid-toolbar ">
		 
		<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> Add</button>
		<a class="btn btn-primary" data-toggle="modal" href="#searchQuestionGroupModal"><i class="icon-zoom-in icon-white"></i> Search</a>
		<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
	</div>
	<table class="table table-striped table-bordered table-grid" id="questionGroupGrid">
		<thead>
			<tr>
				<th class="sort-both sortable" id="questionGroupIdHeader">Question Group ID</th>
				<th class="sort-both sortable" id="questionGroupNameHeader">Question Group Name</th>
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
		<h3>Delete Question Group ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="Deleting..." >Delete</a>
  	</div>
</div>

<div class="modal hide fade" id="questionGroupModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Add Question Group</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="questionGroupForm">
    	<input type="hidden" id="questionGroupId" name="questionGroupId" />
    	<div class="control-group">
	     	<label class="control-label" for="courseId">Course Code</label>
	      	<div class="controls">
	        	<select id="courseId" class="input-medium" data-placeholder="Choose a course..." name="courseId"></select>
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="questionGroupName">Question Group Name</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="questionGroupName" name="questionGroupName">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="saveButton" data-loading-text="Saving...">Save changes</a>
  </div>
</div>

<div class="modal hide fade" id="searchQuestionGroupModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Search Question Group</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="searchQuestionGroupForm">
    	<div class="control-group">
	     	<label class="control-label" for="courseCodeSearch">Course Code</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCodeSearch" name="courseCodeSearch" />
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="questionGroupNameSearch">Question Group Name</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="questionGroupNameSearch" name="questionGroupNameSearch">
	        </div>
	    </div>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="searchButton">Search</a>
  </div>
</div>