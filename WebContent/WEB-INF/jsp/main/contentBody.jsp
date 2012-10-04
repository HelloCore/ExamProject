<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="${contextPath}/css/main/content.css">

<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">เอกสาร</font> ประกอบการเรียน</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<c:if test="${not empty error}">
			<div class="alert alert-error" style="width:400px;margin:auto;">
				<a class="close" data-dismiss="alert" href="#">&times;</a>
				<strong>Error! </strong> ${error}
			</div>
		</c:if>
		<c:if test="${canEdit}">
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
		<c:if test="${fn:length(folderData)+fn:length(fileData) ==0}">
			<div class="alert alert-error" style="width:400px;margin:auto;">
				<a class="close" data-dismiss="alert" href="#">&times;</a>
				<strong>Warning!</strong> ไม่มีข้อมูลใน Folder นี้
			</div>
		</c:if>
		<c:if test="${fn:length(folderData)+fn:length(fileData) > 0}">
			<table class="table table-condensed main-table table-hover">
				<thead>
					<tr>
						<th>เอกสาร</th>
						<th>รายละเอียด</th>
						<th>ขนาดไฟล์</th>
						<th>View Count</th>
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
								<td>${folder.viewCount}</td>
							    <c:if test="${canEdit}">
							    	<td><button class="btn btn-danger" onClick="deleteFolder('${folder.contentPathId}')"><i class="icon-trash icon-white"></i> Delete</button></td>
							    </c:if>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${not empty fileData}">
						<c:forEach items="${fileData}" var="file">
							
							<tr>
								<td><i class="icon-doc-${file.contentFileType}"></i> 
									<a href="${contextPath}/main/content/download.html?file=${file.contentFileId}">${file.contentFileName}</a>
								</td>
								<td>${file.contentFileDesc}</td>
								<td> <fmt:formatNumber minFractionDigits="0" maxFractionDigits="0" value="${file.contentFileSize/1024}"/> Kb</td>
								<td>${file.viewCount}</td>
								<c:if test="${canEdit}">
						    		<td><button class="btn btn-danger" onClick="deleteFile('${file.contentFileId}')"><i class="icon-trash icon-white"></i> Delete</button></td>
						    	</c:if>
						    </tr>
					    </c:forEach>
					</c:if>
				</tbody>
			</table>
		</c:if>
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
		
		<form class="hide" method="POST" id="deleteFolderForm" enctype="multipart/form-data" accept-charset="UTF-8">
			<input type="hidden" name="method" value="deleteFolder">
			<input type="hidden" name="deleteFolderId" id="deleteFolderId">
		    <input type="hidden" name="folderId" value="${currentPath}" />
		</form>
		
		<form class="hide" method="POST" id="deleteFileForm" enctype="multipart/form-data" accept-charset="UTF-8">
			<input type="hidden" name="method" value="deleteFile">
			<input type="hidden" name="deleteFileId"  id="deleteFileId">
		   	<input type="hidden" name="folderId" value="${currentPath}" />
		</form>
		
		<div class="modal hide fade" id="confirmDeleteFolder">
			<div class="modal-header">
				<h3>Delete Folder ?</h3>
			</div>
			<div class="modal-body">
				<font class="error" id="errorItem">คำเตือน หากลบโฟลเดอร์ ไฟล์ข้างในจะถูกลบทั้งหมด</font>โปรดยืนยัน
			</div>
		  	<div class="modal-footer">
		    	<a href="#" class="btn" data-dismiss="modal">Close</a>
		    	<a href="#" class="btn btn-danger" id="deleteFolderButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
		  	</div>
		</div>
		
		<div class="modal hide fade" id="confirmDeleteFile">
			<div class="modal-header">
				<h3>Delete File ?</h3>
			</div>
			<div class="modal-body">
				โปรดยืนยัน
			</div>
		  	<div class="modal-footer">
		    	<a href="#" class="btn" data-dismiss="modal">Close</a>
		    	<a href="#" class="btn btn-danger" id="deleteFileButton" data-loading-text="Deleting..." ><i class="icon-trash icon-white"></i> Delete</a>
		  	</div>
		</div>
	</c:if>

</div>
