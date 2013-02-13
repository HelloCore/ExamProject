<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<script src="${contextPath}/resources/jquery.numeric.js"></script>
<script src="${contextPath}/resources/chosen/chosen.jquery.js"></script>
<script src="${contextPath}/resources/jquery.validate.js"></script>
<script src="${contextPath}/resources/jquery.editable.js"></script>
<script src="${contextPath}/resources/datepicker/bootstrap-datepicker.js"></script>
<script src="${contextPath}/resources/datepicker/bootstrap-timepicker.js"></script>
<script src="${contextPath}/resources/jquery.ui/jquery.ui.core.js"></script>
<script src="${contextPath}/resources/jquery.ui/jquery.ui.widget.js"></script>
<script src="${contextPath}/resources/jquery.ui/jquery.ui.mouse.js"></script>
<script src="${contextPath}/resources/jquery.ui/jquery.ui.sortable.js"></script>
<script src="${contextPath}/resources/json2.js"></script> 
<script src="${contextPath}/resources/jquery.globalize.js"></script>
<script src="${contextPath}/scripts/management/exam/viewExam.js"></script> 

<c:if test="${examData.isCalScore}" >
	<script src="${contextPath}/scripts/management/exam/viewExam.questionGroup.calScore.js"></script> 
</c:if>

<c:if test="${not examData.isCalScore}" >
	<script src="${contextPath}/scripts/management/exam/viewExam.questionGroup.js"></script> 
</c:if>
<script src="${contextPath}/scripts/management/exam/viewExam.section.js"></script> 
<script src="${contextPath}/scripts/management/exam/viewExam.examHeader.js"></script> 
<script src="${contextPath}/scripts/management/exam/viewExam.startDate.js"></script>
<script src="${contextPath}/scripts/management/exam/viewExam.endDate.js"></script>
<script src="${contextPath}/scripts/management/exam/viewExam.numOfQuestion.js"></script>
<script src="${contextPath}/scripts/management/exam/viewExam.examLimit.js"></script>
<script src="${contextPath}/scripts/management/exam/viewExam.examSequence.js"></script>
<script src="${contextPath}/scripts/management/exam/viewExam.timeLimitSecond.js"></script>