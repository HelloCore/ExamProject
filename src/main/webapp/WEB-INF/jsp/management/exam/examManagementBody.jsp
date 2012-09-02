<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/exam/examManagement.css">
<div>
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Examinations</font> Management</h2>
	</div>
	<div class="fixed-content">
		<div class="row-fluid grid-toolbar ">
			<div class="span4">
				<div class="control-group">
					<label for="courseId">วิชา : </label><select id="courseId" name="courseId" style="width:150px;"><option></option></select>
				</div>
			</div>
			<div class="span8 pagination-right">
				<div class="control-group">
					<label for="examHeader" class="search-box-label">หัวข้อการสอบ : </label>
					<div class="input-prepend input-append">
						<span class="add-on search-inactive">
							<i class="icon-zoom-in"></i>
						</span><input type="text" id="examHeader" name="examHeader" class="exam-header"><a class="btn btn-primary search-btn" id="searchButton"><i class="icon-zoom-in icon-white"></i> Search</a>
					</div>	
				</div>
			</div>
		</div>
		<div class="row-fluid grid-toolbar">
			<div class="span4">
				<div class="control-group">
					<div class="grid-info" id="gridInfo"> Record 1-5 of 12 Records</div>
				</div>
			</div>
			<div class="span8 pagination-right">
				<div class="control-group ">
					<button class="btn btn-info" id="addButton"><i class="icon-plus icon-white"></i> Add</button>
					<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
				</div>
			</div>
		</div>
		
		<div class="main-data" id="examDivHolder">
		</div>
		
		<br>
		<div class="row-fluid">
			<div class="span5">
				<select id="pageSize" name="pageSize" class="page-size">
			 	<option value="5">5</option>
			 	<option value="10">10</option>
			 	<option value="20">20</option>
			 	<option value="50">50</option>
			 </select> records per page
			</div>
			<div class="span7">
				<div class="grid-pagination pagination pull-right">
					<ul>
						<li class="prev disabled"><a href="#" id="prevPageButton">&larr; Prev</a></li>
						<li ><a href="#">1</a></li>
						<li ><a href="#">2</a></li>
						<li ><a href="#">3</a></li>
						<li ><a href="#">4</a></li>
						<li ><a href="#">5</a></li>
						<li class="next"><a href="#" id="nextPageButton">Next &rarr;</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>


<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>Delete Exam ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
  	</div>
</div>
<form id="viewExamForm" action="${contextPath}/management/exam/view.html" method="POST" style="display:none;">
	<input type="hidden" name="method" value="viewExam" />
	<input type="hidden" id="examId" name="examId"/>
</form>



