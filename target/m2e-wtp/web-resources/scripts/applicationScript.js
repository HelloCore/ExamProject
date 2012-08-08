applicationScript = {};
if(typeof($.jGrowl)!='undefined'){
	applicationScript.saveComplete = function(){
		$.jGrowl('Save Complete.',{header:'Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.saveCompleteTH = function(){
		$.jGrowl('บันทึกสำเร็จ',{header:'Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.deleteComplete = function(){
		$.jGrowl('Delete Complete.',{header:'Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.deleteCompleteTH = function(){
		$.jGrowl('ลบสำเร็จ',{header:'Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.errorAlert = function(){
		$.jGrowl('Please contact to admin.',{header:'Error',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertTh = function(){
		$.jGrowl('กรุณาติดต่อผู้ดูแลระบบ',{header:'เกิดข้อผิดพลาด',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertWithString = function(str){
		$.jGrowl(str,{header:'Error',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertWithStringTH = function(str){
		$.jGrowl(str,{header:'เกิดข้อผิดพลาด',theme:'alert alert-block alert-error'});
	};
}