applicationScript = {};
if(typeof($.jGrowl)!='undefined'){
	applicationScript.saveComplete = function(){
		$.jGrowl('Save Complete.',{header:'<i class="fam-accept"></i> Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.autoSaveComplete = function(){
		$.jGrowl('Auto Save Complete.',{header:'<i class="fam-accept"></i> Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.saveCompleteTH = function(){
		$.jGrowl('บันทึกข้อมูลสำเร็จ',{header:'<i class="fam-accept"></i> สำเร็จ',theme:'alert alert-block alert-success'});
	};
	applicationScript.deleteComplete = function(){
		$.jGrowl('Delete Complete.',{header:'<i class="fam-accept"></i> Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.deleteCompleteTH = function(){
		$.jGrowl('ลบข้อมูลสำเร็จ',{header:'<i class="fam-accept"></i> สำเร็จ',theme:'alert alert-block alert-success'});
	};
	applicationScript.errorAlert = function(){
		$.jGrowl('Please contact to admin.',{header:'<i class="fam-exclamation"></i> Error',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertTH = function(){
		$.jGrowl('กรุณาติดต่อผู้ดูแลระบบ',{header:'<i class="fam-exclamation"></i> เกิดข้อผิดพลาด',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertWithString = function(str){
		$.jGrowl(str,{header:'<i class="fam-exclamation"></i> Error',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertWithStringTH = function(str){
		$.jGrowl(str,{header:'<i class="fam-exclamation"></i> เกิดข้อผิดพลาด',theme:'alert alert-block alert-error'});
	};
	applicationScript.errorAlertWithStringHeader = function(str,header){
		$.jGrowl(str,{header:'<i class="fam-exclamation"></i> '+header,theme:'alert alert-block alert-error'});
	};
	applicationScript.successAlertWithStringTH = function(str){
		$.jGrowl(str,{header:'<i class="fam-accept"></i> Success',theme:'alert alert-block alert-success'});
	};
	applicationScript.successAlertWithStringHeader = function(str,header){
		$.jGrowl(str,{header:'<i class="fam-accept"></i> '+header,theme:'alert alert-block alert-success'});
	};
	applicationScript.warningAlertWithString = function(str,header){
		$.jGrowl(str,{header:'<i class="fam-error"></i> Warning ',theme:'alert alert-block alert-warning'});
	};
	
	applicationScript.alertNoDataChange = function(){
		$.jGrowl('No data change.',{header:'<i class="fam-accept"></i> Save Complete',theme:'alert alert-block alert-success'});
	};
	
	applicationScript.secondsToTime= function(secs){
	    var t = new Date(1970,0,1);
	    t.setSeconds(secs);
	    var s = t.toTimeString().substr(0,8);
	    if(secs > 86399)
	        s = Math.floor((t - Date.parse("1/1/70")) / 3600000) + s.substr(2);
	    return s;
	};
	
	applicationScript.calPaging = function(data,app){
		var startRecord = (((app.rows)*(app.page-1))+1);
		
		applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
		
		app.lastPage = data.totalPages;
		applicationScript.setPagination(app.page,app.lastPage);
	};
	
	applicationScript.setGridInfo = function(startRecord,length,totalRecords){
		if(totalRecords==0){
			$("#gridInfo").html('<span class="group-item">รายการที่ 0 - 0</span> <span class="group-item">จากทั้งหมด 0 รายการ</span>');		
		}else{
			$("#gridInfo").html('<span class="group-item">รายการที่ '+startRecord+' - '+(startRecord+length -1)+'</span> <span class="group-item">จากทั้งหมด '+totalRecords+' รายการ</span>');
		}
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
					applicationScript.changePage($(this).text());
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
	
	applicationScript.setUpGrid = function(app,customSortFilter){
		
		applicationScript.changePage = function(page){
			if(app.page != page){
				app.page = page;
				app.getGrid();
			}
		};
		
		$("#pageSize").change(function(){
			app.page = 1;
			app.rows = $(this).val();
			app.getGrid();
		});
		
		$("#prevPageButton").click(function(e){
			e.preventDefault();
			if(app.page > 1){
				app.page--;
				app.getGrid();
			}
		});
		$("#nextPageButton").click(function(e){
			e.preventDefault();
			if(app.page < app.lastPage){
				app.page++;
				app.getGrid();
			}
		});
		
		// ------------------ set Sortable
		$('.sortable').click(function(){
			var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
			if(typeof(customSortFilter) == 'function'){
				myId = customSortFilter(myId);
			}
			$('.current-sort').removeClass('sort-desc sort-asc current-sort').addClass('sort-both');
			
			if(app.orderBy == myId){
				if(app.order == "asc"){
					$(this).removeClass('current-sort sort-desc sort-both sort-asc').addClass('current-sort sort-desc');
					app.order = "desc";
				}else{
					$(this).removeClass('current-sort sort-desc sort-both sort-asc').addClass('current-sort sort-asc');
					app.order = "asc";
				}
			}else{
				$(this).removeClass('current-sort sort-desc sort-both sort-asc').addClass('current-sort sort-asc');
				app.orderBy = myId;
				app.order = "asc";
			}
			app.getGrid();
		});
		
		//--------------------end set sortable
	};
	
	applicationScript.resolveError = function(responseText){
		var errorObj = eval("("+responseText+")");
		if(errorObj.type=="CoreException"){
			applicationScript.errorAlertWithStringTH(application.errorMessage[errorObj.message]);
		}else if(errorObj.type=="BindException"){
			applicationScript.errorAlertWithStringTH(errorObj.message);
		}else{
			applicationScript.errorAlertTH();
		}
	};
}