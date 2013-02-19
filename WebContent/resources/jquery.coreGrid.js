/*
 *  Project:
 *  Description:
 *  Author:
 *  License:
 */

// the semi-colon before function invocation is a safety net against concatenated
// scripts and/or other plugins which may not be closed properly.
;(function ( $, window, document, undefined ) {

    // undefined is used here as the undefined global variable in ECMAScript 3 is
    // mutable (ie. it can be changed by someone else). undefined isn't really being
    // passed in so we can ensure the value of it is truly undefined. In ES5, undefined
    // can no longer be modified.

    // window and document are passed through as local variable rather than global
    // as this (slightly) quickens the resolution process and can be more efficiently
    // minified (especially when both are regularly referenced in your plugin).

    // Create the defaults once
    var pluginName = "coreGrid",
        defaults = {
    		rows: 5,
    		page: 1,
    		lastPage : 1,
    		filter: null,
            url: null,
            type: 'POST',
            data: null,
            dataType: 'json',
            loadComplete: null,
            loadError: null,
            tmpl: null,
            method: null,
            orderBy: '',
            order: 'asc',
            defaultSort: null,
            prevPageButton: '#prevPageButton',
            nextPageButton: '#nextPageButton',
            sortable: '.sortable',
            pageSize: '#pageSize'
        };

    // The actual plugin constructor
    function Plugin( element, options ) {
        this.element = element;
        this.$element = $(element);

        // jQuery has an extend method which merges the contents of two or
        // more objects, storing the result in the first object. The first object
        // is generally empty as we don't want to alter the default options for
        // future instances of the plugin
        this.options = $.extend( {}, defaults, options );
        this.options.defaultSort = {
        	orderBy : this.options.orderBy,
        	order: this.options.order
        };
        this._defaults = defaults;
        this._name = pluginName;

        this.init();
    }

    Plugin.prototype = {

        init: function() {
            // Place initialization logic here
            // You already have access to the DOM element and
            // the options via the instance, e.g. this.element
            // and this.options
            // you can add more functions like the one below and
            // call them like so: this.yourOtherFunction(this.element, this.options).
        	var grid = this;
        	$(this.options.prevPageButton).click(function(e){ e.preventDefault();grid.prevPage(); });
        	$(this.options.nextPageButton).click(function(e){ e.preventDefault();grid.nextPage(); });
        	$(this.options.pageSize).change(function(e){ e.preventDefault();grid.changePageSize($(this).val()); });
        	$(this.options.sortable).click(function(e){
        		e.preventDefault();
        		var myId = $(this).attr('id').substring(0,$(this).attr('id').indexOf('Header'));
        		grid.sort(myId,this);
        	});
        	this.loadDefault();
        },

        load: function() {
        	
        	var grid = this; 
        	
        	var param = $.extend({
        		rows: this.options.rows,
        		page: this.options.page,
        		orderBy: this.options.orderBy,
        		order: this.options.order
        	},this.options.data,this.options.filter);
        	
        	this.$element.block(application.blockOption);
        	
        	$.ajax({
        		url: this.options.url,
        		type: this.options.type,
        		data: param,
        		dataType: this.options.dataType,
        		success: function(data,status){
        			grid.$element.find('tbody tr').remove();
        			
        			if(grid.options.tmpl){
        				$(grid.options.tmpl).tmpl(data.records).appendTo(grid.$element.find('tbody'));
        			}
        			
        			var startRecord = (((grid.options.rows)*(grid.options.page-1))+1);
        			
        			applicationScript.setGridInfo(startRecord,data.records.length,data.totalRecords);
        			
        			grid.options.lastPage = data.totalPages;
        			applicationScript.setPagination(grid.options.page,grid.options.lastPage);
        			grid.$element.unblock();
        			
        			if(typeof(grid.options.loadComplete) == 'function'){
        				grid.options.loadComplete(data,status);
        			}
        		},
        		error:function(data){
        			applicationScript.errorAlertWithStringTH(data.responseText);
        			grid.$element.unblock();
        			
        			if(typeof(grid.options.loadError) == 'function'){
        				grid.options.loadError(data);
        			}
        		}
        	});
        	delete param;
        },
        
        
        loadDefault: function(){
        	this.clearSort();
        	$('#'+this.options.orderBy+'Header').addClass('sort-asc current-sort');
        	this.options.orderBy = this.options.defaultSort.orderBy;
        	this.options.order = this.options.defaultSort.order;
        	this.clearFilter();
        	this.load();
        },
        
        search: function(filter){
        	this.options.page = 1;
        	this.options.filter = filter;
        	this.load();
        },
        clearSort: function(){
        	$('.current-sort').removeClass('sort-asc').removeClass('sort-desc').removeClass('current-sort').addClass('sort-both');
        },
        clearFilter: function(){
        	var filters = this.options.filter;
        	$.each(filters,function(key,value){
        		filters[key]= '';
        	});
        },
        
        changePage: function(page){
        	if(this.options.page != page){
        		this.options.page = page;
        		this.load();
        	}
        },
        
        prevPage : function(){
    		if(this.options.page > 1){
    			this.options.page--;
    			this.load();
    		}
        },
        
        nextPage : function(){
    		if(this.options.page < this.options.lastPage){
    			this.options.page++;
    			this.load();
    		}
        },
        
        sort : function(id,sortables){
    		this.clearSort();
    		var sortable = $(sortables);
    		if(this.options.orderBy == id){
    			if(this.options.order == "asc"){
    				sortable.addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
    					.addClass('sort-desc');
    				this.options.order = "desc";
    			}else{
    				sortable.addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
    				.addClass('sort-asc');
    				this.options.order = "asc";
    			}
    		}else{
    			sortable.addClass('current-sort').removeClass('sort-desc').removeClass('sort-both').removeClass('sort-asc')
    				.addClass('sort-asc');
    			this.options.orderBy = id;
    			this.options.order = "asc";
    		}
    		this.load();
        },
        
        changePageSize: function(size){
        	this.options.page = 1;
        	this.options.rows = size;
    		this.load();
        }
    };

    // A really lightweight plugin wrapper around the constructor,
    // preventing against multiple instantiations
    $.fn[pluginName] = function ( options ) {
        return this.each(function () {
            if (!$.data(this, 'plugin_'+pluginName)) {
                $.data(this, 'plugin_'+pluginName, new Plugin( this, options ));
            }
        });
    };

})( jQuery, window, document );