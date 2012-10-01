<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" href="${contextPath}/css/main/content.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">เอกสาร</font> ประกอบการเรียน</h2>
</div>
	
<div class="row-fluid">
	<div class="span12">
		<c:if test="${canEdit}">
			<c:if test="${not empty error}">
				<div class="alert alert-error" style="width:400px;margin:auto;">
					<a class="close" data-dismiss="alert" href="#">&times;</a>
					<strong>เกิดข้อผิดพลาด</strong>${error}
				</div>
			</c:if>
			<c:if test="${not empty success}">
				<div class="alert alert-success" style="width:400px;margin:auto;">
					<a class="close" data-dismiss="alert" href="#">&times;</a>
					<strong>Success !</strong> Save Complete
				</div>
			</c:if>
		</c:if>
		<c:if test="${parentPath != 0}">
			<a class="btn" href="${contextPath}/main/content.html?path=${parentPath}"><i class="icon-chevron-left"></i> Back</a>
		</c:if>
		<c:if test="${canEdit}">
			<a class="btn btn-primary" id="uploadButton"><i class="icon-upload icon-white"></i> Upload</a>
			<a class="btn btn-info" id="newFolderButton"><i class="icon-plus icon-white"></i> New Folder</a>
		</c:if>
		<table class="table table-condensed main-table">
			<thead>
				<tr>
					<th>เอกสาร</th>
					<th>รายละเอียด</th>
					<th>ขนาดไฟล์</th>
					<c:if test="${canEdit}">
			    		<th>Action</th>
			    	</c:if>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty folderData}">
					<c:forEach items="${folderData}" var="folder">
						<tr>
							<td><i class="icon-folder"></i> <a href="${contextPath}/main/content.html?path=${folder.contentPathId}">${folder.contentPathName}</a></td>
							<td>${folder.contentPathDesc}</td>
							<td></td>
						    <c:if test="${canEdit}">
						    	<td><button class="btn btn-danger" onClick="deleteFolder('${folder.contentPathId}')"><i class="icon-trash icon-white"></i> Delete</button></td>
						    </c:if>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${not empty fileData}">
					<c:forEach items="${fileData}" var="file">
						
						<tr>
							<td><i class="icon-word"></i> 
								<a href="${contextPath}/${file.contentFilePath}">${file.contentFileName}</a>
							</td>
							<td>${file.contentFileDesc}</td>
							<td> <fmt:formatNumber minFractionDigits="0" maxFractionDigits="0" value="${file.contentFileSize/1024}"/> Kb</td>
							<c:if test="${canEdit}">
					    		<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
					    	</c:if>
					    </tr>
				    </c:forEach>
				</c:if>
				
			<%--
				<tr>
					<td><i class="icon-word"></i> เอกสาร 1</td>
					<td>ทดสอบเอกสาร 1</td>
					<td>1024 kb</td>
				    <sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')" >
				    	<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				    </sec:authorize>
				</tr>
				<tr>
					<td><i class="icon-excel"></i> เอกสาร 2</td>
					<td>ทดสอบเอกสาร 2</td>
					<td>1024 kb</td>
				    <sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')" >
				    	<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				    </sec:authorize>
				</tr>
				<tr>
					<td><i class="icon-powerpoint"></i> เอกสาร 3</td>
					<td>ทดสอบเอกสาร 3</td>
					<td>1024 kb</td>
				    <sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')" >
				    	<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				    </sec:authorize>
				</tr>
				<tr>
					<td><i class="icon-pdf"></i> เอกสาร 4</td>
					<td>ทดสอบเอกสาร 4</td>
					<td>1024 kb</td>
				    <sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')" >
				    	<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				    </sec:authorize>
				</tr>
				<tr>
					<td><i class="icon-java"></i> เอกสาร 5</td>
					<td>ทดสอบเอกสาร 5</td>
					<td>1024 kb</td>
				    <sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')" >
				    	<td><button class="btn btn-danger"><i class="icon-trash icon-white"></i> Delete</button></td>
				    </sec:authorize>
				</tr> --%>
			</tbody>
		</table>
	</div>

	<c:if test="${canEdit}">
		<!-- Modal Here -->
		<div class="modal hide fade" id="newFolderModal">
			<div class="modal-header">
		    	<button type="button" class="close" data-dismiss="modal">×</button>
		    	<h3>New Folder</h3>
		  	</div>
		  	<div class="modal-body">
		    	<form class="form-horizontal" id="newFolderForm" method="POST" accept-charset="UTF-8">
		    		<input type="hidden" name="method" value="newFolder" />
		    		<input type="hidden" name="folderId" value="${currentPath}" />
		    		<div class="control-group">
			     		<label class="control-label" for="folderName">ชื่อ Folder</label>
			      		<div class="controls">
			        		<input type="text" class="input-medium" id="folderName" name="folderName" />
			        	</div>
				    </div>
			    	<div class="control-group">
				     	<label class="control-label" for="folderDesc">รายละเอียด</label>
				      	<div class="controls">
				        	<input type="text" class="input-medium" id="folderDesc" name="folderDesc" />
				        </div>
				    </div>
		    	</form>
		  	</div>
		  	<div class="modal-footer">
		    	<a href="#" class="btn" data-dismiss="modal">Close</a>
		    	<button class="btn btn-info" id="newFolderConfirm" data-loading="New Folder..."><i class="icon-plus icon-white"></i> New Folder</button>
		  	</div>
		</div>
		
		<div class="modal hide fade" id="uploadModal">
			<div class="modal-header">
		    	<button type="button" class="close" data-dismiss="modal">×</button>
		    	<h3>Upload File</h3>
		  	</div>
		  	<div class="modal-body">
		    	<form class="form-horizontal" id="uploadFileForm" method="POST" enctype="multipart/form-data" accept-charset="UTF-8">
		    		<input type="hidden" name="method" value="uploadFile" />
		    		<input type="hidden" name="folderId" value="${currentPath}" />
		    		<div class="control-group">
			     		<label class="control-label" for="fileName">ชื่อ File</label>
			      		<div class="controls">
			        		<input type="text" class="input-medium" id="fileName" name="fileName" />
			        	</div>
				    </div>
			    	<div class="control-group">
				     	<label class="control-label" for="fileDesc">รายละเอียด</label>
				      	<div class="controls">
				        	<input type="text" class="input-medium" id="fileDesc" name="fileDesc" />
				        </div>
				    </div>
			    	<div class="control-group">
				     	<label class="control-label" for="fileData">File</label>
				      	<div class="controls">
				        	<input type="file" class="input-medium" id="fileData" name="fileData" />
				        </div>
				    </div>
		    	</form>
		  	</div>
		  	<div class="modal-footer">
		    	<a href="#" class="btn" data-dismiss="modal">Close</a>
		    	<button class="btn btn-primary" id="uploadFileConfirm" data-loading="Upload..."><i class="icon-upload icon-white"></i> Upload</button>
		  	</div>
		</div>
		
	</c:if>

</div>
