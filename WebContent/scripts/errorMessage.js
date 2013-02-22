if(application){
	application.errorMessage = [];
	
	application.errorMessage['PERMISSION_DENIED'] = 'คุณไม่สิทธิ์ใช้งานในส่วนนี้';
	application.errorMessage['PARAMETER_NOT_FOUND'] = 'ระบบส่งข้อมูลมาไม่ครบ';
	application.errorMessage['INVALID_DATA'] = 'ข้อมูลผิดพลาด';
	
	application.errorMessage['CANT_EDIT_EXAM'] = 'ไม่สามารถแก้ไขข้อสอบได้';
	application.errorMessage['EXAM_EXPIRED'] = 'การสอบหมดอายุ';
	application.errorMessage['EXAM_NOT_STARTED'] = 'การสอบยังไม่เริ่ม';
	application.errorMessage['CANT_DO_EXAM_ANYMORE'] = 'คุณไม่สามารถทำการสอบได้อีก';
	application.errorMessage['QUESTION_NOT_ENOUGH'] = 'คำถามมีไม่พอ';
	application.errorMessage['EXAM_NOT_COMPLETE'] = 'การสอบยังไม่เสร็จสิ้น';
	
	application.errorMessage['DUPLICATE_COURSE'] = 'ชื่อวิชาซ้ำ';
	
	application.errorMessage['INVALID_YEAR'] = 'ปีไม่ถูกต้อง';
	application.errorMessage['NOT_IN_FACULTY'] = 'คุณไม่ได้อยู่ในคณะวิทยาศาสตร์และเทคโนโลยี';
	application.errorMessage['STUDENT_NOT_FOUND'] = 'ไม่พบรหัสนักศึกษาดังกล่าว';
	application.errorMessage['CANT_ACTIVE_ANYMORE'] = 'ไม่สามารถเปิดใช้งานได้ เนื่องจากเปิดการใช้งานแล้ว';
	application.errorMessage['INVALID_ACTIVE_CODE'] = 'รหัสในการเปิดใช้งานไม่ถูกต้อง';
	application.errorMessage['STUDENT_ID_AND_EMAIL_NOT_MATCH'] = 'รหัสนักศึกษาและ E-mail ไม่ตรงกัน';

	application.errorMessage['DUPLICATE_EMAIL'] = 'มีผู้ใช้งาน E-mail นี้แล้ว';
	application.errorMessage['DUPLICATE_STUDENT_ID_OR_EMAIL'] = 'มีผู้ใช้งานรหัสนักศึกษาหรือ E-mail นี้แล้ว';
	
	application.errorMessage['FILE_NOT_FOUND'] = 'ไม่พบไฟล์';
	application.errorMessage['FOLDER_IS_EXISTS'] = 'มีโฟลเดอร์นี้อยู่แล้ว';
	application.errorMessage['CANT_CREATE_FOLDER'] = 'ไม่สามารถสร้างโฟลเดอร์ได้';
	
	application.errorMessage['CANT_ACCEPT_FILE_TYPE'] = 'ระบบไม่รองรับไฟล์ประเภทนี้';
	application.errorMessage['FILE_IS_EXISTS'] = 'มีไฟล์นี้อยู่ในระบบแล้ว';

	application.errorMessage['CANT_DELETE_FILE'] = 'ไม่สามารถลบไฟล์ได้';
	
	application.errorMessage['ASSIGNMENT_NOT_STARTED'] = 'Assignment ยังไม่เริ่มต้น';
	application.errorMessage['ASSIGNMENT_EXPIRED'] = 'Assignment หมดเขตส่งแล้ว';
	application.errorMessage['LIMIT_NUM_OF_FILE'] = 'จำนวนไฟล์เกินกำหนด';
	application.errorMessage['LIMIT_FILE_SIZE'] = 'ขนาดไฟล์เกินกำหนด';
	application.errorMessage['CANT_SUBMIT_ANYMORE'] = 'ไม่สามารถส่ง Assignment ได้อีก';
	
	application.errorMessage['OLD_AND_NEW_PASSWORD_IS_DUPLICATE'] = 'รหัสผ่านเก่าและใหม่ซ้ำกัน';
	application.errorMessage['PASSWORD_IS_INVALID'] = 'รหัสผ่านไม่ถูกต้อง';
	application.errorMessage['PASSWORD_IS_NOT_MATCH'] = 'กรุณากรอกรหัสผ่านให้เหมือนกัน';
	
}