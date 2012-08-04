<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<div class="page-header pagination-centered">
		<h2><a class="red-color">Section</a> Management</h2>
	</div>
	<div class="grid-toolbar ">
		 
		<button class="btn btn-info" id="addButton"><i class="icon-plus icon-white"></i> Add</button>
		<button class="btn btn-primary" id="searchButton"><i class="icon-zoom-in icon-white"></i> Search</button>
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