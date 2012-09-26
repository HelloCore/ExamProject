<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/css/management/news/newsManagement.css">		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">News</font> Management</h2>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="grid-toolbar">
				<button class="btn btn-info" id="addButton" ><i class="icon-plus icon-white"></i> Add</button>
				<a class="btn btn-primary" data-toggle="modal" href="#searchNewsModal"><i class="icon-zoom-in icon-white"></i> Search</a>
				<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
			</div>
			<table class="table table-striped table-bordered table-grid" id="newsGrid">
				<thead>
					<tr>
						<th class="sort-both sortable" id="updateDateHeader">วันที่อัพเดท<i></i></th>
						<th class="sort-both sortable" id="courseCodeHeader">สังกัดวิชา<i></i></th>
						<th class="sort-both sortable" id="newsHeaderHeader">หัวข้อข่าว<i></i></th>
						<th class="sort-both sortable" id="firstNameHeader">โดย<i></i></th>
						<th class="action-column" >Action</th>
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

<form id="editNewsForm" method="POST" action="${contextPath}/management/news/view.html">
	<input type="hidden" name="newsId" id="newsId" value="" />
</form>

<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>Delete News ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
  	</div>
</div>

<div class="modal hide fade" id="searchNewsModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Search News</h3>
  </div>
  <div class="modal-body">
    <div class="form-horizontal">
    	<div class="control-group">
	     	<label class="control-label" for="courseCodeSearch">รหัสวิชา</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="courseCodeSearch" name="courseCodeSearch" />
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="newsHeaderSearch">หัวข้อข่าว</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium" id="newsHeaderSearch" name="newsHeaderSearch">
	        </div>
	    </div>
    	<div class="control-group">
	     	<label class="control-label" for="newsContentHeader">เนื้อหาข่าว</label>
	      	<div class="controls">
	        	<input type="text" class="input-medium numeric" id="newsContentHeader" name="newsContentHeader">
	        </div>
	    </div>
    </div>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="searchButton"><i class="icon-zoom-in icon-white"></i> Search</a>
  </div>
</div>