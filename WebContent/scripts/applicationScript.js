applicationScript = {};
if(typeof($.jGrowl)!='undefined'){
	applicationScript.saveComplete = function(){
		$.jGrowl('Save Complete.',{header:'Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.autoSaveComplete = function(){
		$.jGrowl('Auto Save Complete.',{header:'Success',theme:'alert alert-block alert-success'});
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
	applicationScript.errorAlertWithStringHeader = function(str,header){
		$.jGrowl(str,{header:header,theme:'alert alert-block alert-error'});
	};
	applicationScript.successAlertWithStringHeader = function(str,header){
		$.jGrowl(str,{header:header,theme:'alert alert-block alert-success'});
	};
	applicationScript.warningAlertWithString = function(str,header){
		$.jGrowl(str,{header:"Warning ",theme:'alert alert-block alert-warning'});
	};
	
	applicationScript.secondsToTime= function(secs){
	    var t = new Date(1970,0,1);
	    t.setSeconds(secs);
	    var s = t.toTimeString().substr(0,8);
	    if(secs > 86399)
	        s = Math.floor((t - Date.parse("1/1/70")) / 3600000) + s.substr(2);
	    return s;
	};
	applicationScript.setPagination = function(page,lastPage){
		var sClass;
		var first = (parseInt(page)-2);
		if( first < 1){ first = 1;}
		var last = (parseInt(page)+2);
		if( last > lastPage){ last = lastPage;}
		if( first <= 1 && lastPage >= 5){
			last = 5;
		}
		
		if(page == lastPage){
			if(lastPage-4 >0){
				first = lastPage -4;
			}
		}else if (page == lastPage -1){
			if(lastPage-4 > 0){
				first = lastPage -4;
			}
		}
		
		$('.grid-pagination li:gt(0)').filter(':not(:last)').remove();

		for(var i=first;i<=last;i++){
			sClass = (i==page) ? 'class="active"' :'';
			$('<li '+sClass+'><a href="#">'+i+'</a></li>')
				.insertBefore( $('.grid-pagination li:last')[0] )
				.bind('click',function(e){
					e.preventDefault();
					changePage($(this).text());
				});
		}
		
		if ( page == 1 ) {
			$('.grid-pagination li:first').addClass('disabled');
		} else {
			$('.grid-pagination li:first').removeClass('disabled');
		}

		if ( page >= lastPage ) {
			$('.grid-pagination li:last').addClass('disabled');
		} else {
			$('.grid-pagination li:last').removeClass('disabled');
		}
	};
}