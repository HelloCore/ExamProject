<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${contextPath}/css/management/task/evaluateTask.css">
<script>
	application.maxScore = ${workData.assignmentTask.maxScore};
</script>		
<div class="page-header pagination-centered" id="pageHeader">
	<h2><font class="red-color">Evaluate</font> Task</h2>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="well" style="width:600px;margin:auto;">
			<div class="form-horizontal">
				<div class="control-group">
			    	<label class="control-label" >หัวข้องาน</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${workData.assignmentTask.assignmentTaskName}</span>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >รายละเอียดงาน</label>
			    	<div class="controls">
			    		<pre class="input-xlarge">${workData.assignmentTask.assignmentTaskDesc}</pre>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >รหัสนักศึกษา</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${workData.user.username}</span>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >ส่งโดย</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${workData.user.firstName} ${workData.user.lastName}</span>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >ส่งเมื่อ</label>
			    	<div class="controls">
						<fmt:parseDate var="sendDate" value="${workData.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="en_US"/>
			    		<span class="input-xlarge uneditable-input"><fmt:formatDate value="${sendDate}" pattern="dd-MM-yyyy HH:mm"/></span>
			    	</div>
			  	</div>
			  	<div class="control-group">
			  		<label class="control-label"> ไฟล์งาน </label>
			  		<div class="controls">
			  			<ol>
			  				<c:if test="${not empty fileList}">
								<c:forEach items="${fileList}" var="data">
									<li><a href="${contextPath}/management/task/getFile.html?file=${data.assignmentFileId}">${data.fileName}</a></li>
								</c:forEach>
							</c:if>
			  			</ol>
			  		</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >คะแนนเต็ม</label>
			    	<div class="controls">
			    		<span class="input-xlarge uneditable-input">${workData.assignmentTask.maxScore} คะแนน</span>
			    	</div>
			  	</div>
				<div class="control-group">
			    	<label class="control-label" >คะแนน</label>
			    	<div class="controls">
			    		<form method="POST" id="evaluateForm">
			    			<input type="text" id="score" name="score" value="${workData.score}"/>
			    			<input type="hidden" name="workId" value="${workData.assignmentWorkId}" />
			    			<input type="hidden" name="method" value="evaluate" />
			    			<input type="hidden" name="taskId" value="${workData.assignmentTask.assignmentTaskId}" />
			    		</form>
			    	</div>
			  	</div>
			  	<div class="pagination-centered">
			  		<button class="btn btn-info" id="evaluateButton"><i class="icon-edit icon-white"></i> ให้คะแนน</button>
			  		<button class="btn" onclick="backFunction()"> Cancel</button>
			  	</div>
			</div>
		</div>
	</div>
</div>

<div class="modal hide fade" id="evaluateTaskConfirmModal">
	<div class="modal-header">
		<h3>Evaluate Task ?</h3>
	</div>
	<div class="modal-body">
		please confirm
	</div>
  	<div class="modal-footer">
    	<a href="#" class="btn" data-dismiss="modal">Close</a>
    	<a href="#" class="btn btn-primary" id="evaluateTaskConfirmButton" data-loading-text="Evaluate..." ><i class="icon-pencil icon-white"></i> Evaluate</a>
  	</div>
</div>

<form action="${contextPath}/management/task/sendList.html" method="POST" id="sendTaskForm">
	<input type="hidden" id="taskId" name="taskId" value="${workData.assignmentTask.assignmentTaskId}">
	<input type="hidden" name="method" value="initSendList">
</form>
