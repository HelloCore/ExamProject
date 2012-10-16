<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/task/taskManagement.css">
		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">จัดการ</font> Assignment</h2>
</div>

<div class="row-fluid">
	<div class="span3">
		<div id="leftBar">
			<div class="form-inline search-bar courseBox">
				<div class="control-group ">
					<label for="courseId" class="label-group">วิชา : </label>	
					<select id="courseId" name="courseId" class="combobox">
					</select>
				</div>
			</div><div class="clear"></div>			
			<div class="control-group  text-bar">
				<div class="input-prepend  task-text-div">
					<span class="add-on search-inactive">
						<i class="icon-zoom-in"></i>
					</span>
					<input type="text" id="taskName" name="taskName" class="task-text" placeholder="หัวข้องาน">	
				</div>
			</div>
			<div class="clear visible-phone-portrait"></div>	
			<div class="grid-info-holder">
				<div class="grid-info " id="gridInfo">1-5 of 12 Records</div>
			</div>
			<div class="clear"></div>
			<div class="control-group btn-holder-group">
				<div class="btn-holder">
					<button class="btn btn-primary btn-block" id="searchButton"><i class="icon-zoom-in icon-white"></i> Search</button>
				</div>
				<div class="btn-holder">
					<button class="btn btn-success btn-block" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
				</div>
				<div class="btn-holder">
					<a class="btn btn-info btn-block" id="addButton" href="${contextPath}/management/task/assign.html"><i class="icon-pencil icon-white"></i> Assign</a>
				</div>
			</div>
		</div>
	</div>
	<div class="span9">
			
		<div class="main-data" id="taskDivHolder">
			
		</div>
		
		<div class="row-fluid">
			<div class="span5 page-size-div">
				<select id="pageSize" name="pageSize" class="page-size">
				 	<option value="5">5</option>
				 	<option value="10">10</option>
				 	<option value="20">20</option>
				 	<option value="50">50</option>
			 	</select> records per page
			</div>
			<div class="span7">
				<div class="grid-pagination pagination pagination-centered ">
					<ul>
						<li class="prev disabled"><a href="#" id="prevPageButton">«</a></li>
						<li class="next"><a href="#" id="nextPageButton">»</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal hide fade" id="evaluateCompleteModal">
	<div class="modal-header">
		<h3>ตรวจเสร็จแล้ว ?</h3>
	</div>
	<div class="modal-body">
		งานนี้ตรวจเสร็จแล้ว 
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-primary" id="evaluateCompleteButton" data-loading-text="ส่งข้อมูล..." ><i class="icon-ok icon-white"></i> ตรวจเสร็จแล้ว</a>
  	</div>
</div>


<div class="modal hide fade" id="confirmDeleteTaskModal">
	<div class="modal-header">
		<h3>Delete Task ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-danger" id="confirmDeleteTaskButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
  	</div>
</div>

<form method="POST" action="${contextPath}/management/task/view.html" id="viewTaskForm">
	<input type="hidden" name="method" value="viewTask" />
	<input type="hidden" id="viewTaskId" name="taskId" />
</form>