/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.resize_enabled = true;
	config.resize_dir = 'vertical';
	config.width = 650;
	config.skin = 'BootstrapCK-Skin';
	config.toolbar = [
	                  	[ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ],
	                  	[ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ],
	                  	[ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ],
	                  	[ 'Link','Unlink','Anchor' ],
	                  	[ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',
	                  	'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ],
	                  	[ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak' ],
	                  	[ 'ShowBlocks','-','About' ] ,
	                  	[ 'Styles','Format','Font','FontSize' ],[ 'TextColor','BGColor' ]
	                  
	];
	config.filebrowserBrowseUrl = application.contextPath+'/resources/ckfinder/ckfinder.htm';
	config.filebrowserImageBrowseUrl =  application.contextPath+'/resources/ckfinder/ckfinder.htm?type=Images';
	config.filebrowserImageBrowseLinkUrl =  application.contextPath+'/resources/ckfinder/ckfinder.htm?type=Images';
	config.filebrowserFlashBrowseUrl =  application.contextPath+'/resources/ckfinder/ckfinder.htm?type=Flash';
	config.filebrowserUploadUrl =  application.contextPath+'/resources/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
	config.filebrowserImageUploadUrl =   application.contextPath+'/resources/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
	config.filebrowserFlashUploadUrl =   application.contextPath+'/resources/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';

};
