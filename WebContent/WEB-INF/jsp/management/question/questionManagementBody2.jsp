<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/question/questionManagement.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Question </font> Management</h2>
	</div>
	<div class="row-fluid">
		<div class="span3">
			<div id="leftBar">
				<div class="form-inline search-bar">
					<div class="control-group ">
						<label for="courseId" class="label-group">วิชา : </label>	
						<select id="courseId" name="courseId" class="combobox"></select>
					</div>
				</div>
				<div class="form-inline  search-bar">
					<div class="control-group ">
						<label for="questionGroupId" class="label-group">กลุ่ม : </label>
						<select id="questionGroupId" name="questionGroupId" class="combobox"></select>
					</div>
				</div>
				<div class="grid-info " id="gridInfo">1-5 of 12 Records</div>
				
				<div class="control-group  text-bar">
					<div class="input-prepend  question-text-div">
						<span class="add-on search-inactive">
							<i class="icon-zoom-in"></i>
						</span>
						<input type="text" id="questionText" name="questionText" class="question-text" placeholder="คำถาม">	
					</div>
				</div>
				<div class="clear"></div>
				<div class="control-group">
					<div class="btn-holder">
						<button class="btn btn-primary btn-large btn-block" id="searchButton"><i class="icon-zoom-in icon-white"></i> Search</button>
					</div>
					<div class="btn-holder">
						<button class="btn btn-success btn-large btn-block" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
					</div>
					<div class="btn-holder">
						<button class="btn btn-info btn-large btn-block" id="addButton"><i class="icon-plus icon-white"></i> Add</button>
					</div>
				</div>
			</div>
		</div>
		<div class="span9">
			<div class="main-data" id="questionDivHolder">
			
			</div>
			<div class="row-fluid">
				<div class="span4">
					<select id="pageSize" name="pageSize" class="page-size">
					 	<option value="5">5</option>
					 	<option value="10">10</option>
					 	<option value="20">20</option>
					 	<option value="50">50</option>
				 	</select> records per page
				</div>
				<div class="span8">
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
</div>

<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>Delete Question ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
  	</div>
</div>

<form id="viewQuestionForm" action="${contextPath}/management/question/view.html" method="POST" style="display:none;">
	<input type="hidden" name="method" value="viewQuestion" />
	<input type="hidden" id="questionId" name="questionId"/>
</form>