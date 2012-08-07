<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/chosen/chosen.css" />
<link rel="stylesheet" href="${contextPath}/css/management/question.css">
<div>
	<div class="page-header pagination-centered" id="pageHeader">
		<h2><font class="red-color">Question </font> Management</h2>
	</div>
	<div class="fixed-content">
		<div class="row-fluid grid-toolbar">
			<div class="span7" >
					<div class="control-group">
						<label for="courseId">วิชา : </label><select id="courseId" name="courseId" style="width:140px;"></select>
						<label for="questionGroupId">กลุ่ม : </label><select id="questionGroupId" name="questionGroupId" style="width:140px;"></select><br>	
					</div>
						<button class="btn btn-info" id="addButton"><i class="icon-plus icon-white"></i> Add</button>
				<button class="btn btn-success" id="refreshButton"><i class="icon-refresh icon-white"></i> Refresh</button>
			
			</div>
			<div class="span5">
				<div class="control-group">
					<label for="questionText" class="search-box-label">คำถาม : </label>
					<div class="input-prepend input-append">
						<span class="add-on search-inactive">
							<i class="icon-zoom-in"></i>
						</span><input type="text" id="questionText" name="questionText" class="question-text"><a class="btn btn-primary" data-toggle="modal" href="#searchQuestionGroupModal"><i class="icon-zoom-in icon-white"></i> Search</a>	
					</div>
				</div>
				<div class="grid-info" id="gridInfo"> Record 1-5 of 12 Records</div>
			</div>
		</div>
		
		<div class="main-data">
			<table class="table table-bordered question-table">
				<tr>
					<td colspan="3"><p>1+1=?</p></td>
				</tr><tr>
					<td>วิชา : CS.101</td>
					<td>กลุ่มคำถาม : Chapter 1</td>
					<td><button class="btn btn-info"><i class="icon-edit icon-white"></i> View </button></td>
				</tr><tr>
					<td>มีจำนวนคำตอบที่ถูกต้อง : 1 คำตอบ</td>
					<td>มีจำนวนคำตอบหลอก : 3 คำตอบ</td>
					<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				</tr>
			</table>
			<table class="table table-bordered question-table">
				<tr>
					<td colspan="3"><p>2+2=?</p></td>
				</tr><tr>
					<td>วิชา : CS.101</td>
					<td>กลุ่มคำถาม : Chapter 1</td>
					<td><button class="btn btn-info"><i class="icon-edit icon-white"></i> View </button></td>
				</tr><tr>
					<td>มีจำนวนคำตอบที่ถูกต้อง : 1 คำตอบ</td>
					<td>มีจำนวนคำตอบหลอก : 3 คำตอบ</td>
					<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				</tr>
			</table>
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