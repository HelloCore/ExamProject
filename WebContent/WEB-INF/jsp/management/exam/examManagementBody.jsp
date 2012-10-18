<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/exam/examManagement.css">
<div>
		
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">จัดการ</font> การสอบ</h2>
	</div>
	<div class="row-fluid fixed-content">
		<div class="span3">
			<div class="leftbar" id="leftBar">
				<div class="control-group search-bar course-bar">
					<label for="courseId">วิชา : </label>
					<select id="courseId" name="courseId" class="combobox"><option></option></select>
				</div>
				<div class="control-group search-bar header-bar">
					<div class="input-prepend">
						<span class="add-on search-inactive">
							<i class="icon-zoom-in"></i>
						</span><input type="text" id="examHeader" name="examHeader" class="exam-header" placeholder="หัวข้อการสอบ">
					</div>	
				</div>
				<div class="control-group pagination-centered grid-bar">
					<div class="grid-info" id="gridInfo"></div>
				</div>
				<div class="clear"></div>
				<div class="control-group btn-holder-div">
					<div class="btn-holder">
						<button class="btn btn-primary btn-block " id="searchButton"><i class="icon-zoom-in icon-white"></i> ค้นหา</button>
					</div>
					<div class="btn-holder">
						<button class="btn btn-info btn-block " id="addButton"><i class="icon-plus icon-white"></i> สร้าง</button>
					</div>
					<div class="btn-holder">
						<button class="btn btn-success btn-block " id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
					</div>
				</div>
			</div>
		</div>
		<div class="span9">
			<div class="main-data" id="examDivHolder">
			</div>
			
			<br>
			<div class="row-fluid">
				<div class="span5 page-size-div">
					<select id="pageSize" name="pageSize" class="page-size">
					 	<option value="5">5</option>
					 	<option value="10">10</option>
					 	<option value="20">20</option>
					 	<option value="50">50</option>
				 	</select> รายการต่อหน้า
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
</div>


<div class="modal hide fade" id="confirmDelete">
	<div class="modal-header">
		<h3>ลบการสอบ ?</h3>
	</div>
	<div class="modal-body">
		คุณต้องการลบการสอบใช่หรือไม่ โปรดยืนยัน
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">ปิด</a>
    	<a href="#" class="btn btn-danger" id="deleteButton" data-loading-text="กำลังลบ..." ><i class="icon-trash icon-white"></i> ลบ</a>
  	</div>
</div>
<form id="viewExamForm" action="${contextPath}/management/exam/view.html" method="POST" style="display:none;">
	<input type="hidden" name="method" value="viewExam" />
	<input type="hidden" id="examId" name="examId"/>
</form>



