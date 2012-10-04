application.page = "assignment";

$(document).ready(function(){
	getAssignmentDetail = function(assignmentId){
		$("#detailAssignment").show().block(application.blockOption);
		$.ajax({
			url: application.contextPath + '/assignment/select.html',
			type: 'POST',
			data: {
				method: 'getAssignmentDetail',
				assignmentId: assignmentId
			},
			success: function(data){
				var startDate = new Date(data.startDate);
				console.log(startDate);
				$("#detailAssignment").unblock();
				$("#detailCourseCode").text(data.courseCode);
				$("#detailAssignmentName").text(data.assignmentName);
				$("#detailAssignmentDesc").text(data.assignmentDesc);
				$("#detailStartDate").text(Globalize.format(new Date(data.startDate),'dd-MM-yyyy HH:mm'));
				$("#detailEndDate").text(Globalize.format(new Date(data.endDate),'dd-MM-yyyy HH:mm'));
				$("#detailLimitFileSizeKb").text(data.limitFileSizeKb);
				$("#detailNumOfFile").text(data.numOfFile+" ไฟล์");
				$("#detailCreateBy").text(data.firstName+" "+data.lastName);
				$("#detailCreateDate").text(Globalize.format(new Date(data.createDate),'dd-MM-yyyy HH:mm'));
				$("#detailMaxScore").text(data.maxScore);
				
				if(startDate >= new Date()){
					$("#selectAssignmentButton").attr('disabled',true);
				}else{
					$("#selectAssignmentButton").attr('disabled',false);
				}
			},
			error: function(data){
				applicationScript.errorAlertWithStringTH(data.responseText);
				$("#detailAssignment").unblock();
			}
		});
	};
	
	$("#assignmentTable tbody tr").click(function(){
		$('.checked').removeClass('checked');
		getAssignmentDetail($(this).addClass('checked').find('input').attr('checked',true).val());
	});
});