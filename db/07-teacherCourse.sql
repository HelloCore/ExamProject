CREATE TABLE TEACHER_COURSE(
 	TEACHER_COURSE_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	USERNAME VARCHAR(40) NOT NULL,
 	COURSE_ID INT(10) UNSIGNED NOT NULL,
 	PRIMARY KEY (TEACHER_COURSE_ID),
 	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME),
 	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID),
 	UNIQUE(USERNAME,COURSE_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TEACHER_COURSE VALUES(null,'teacher',1);
INSERT INTO TEACHER_COURSE VALUES(null,'teacher',2);
INSERT INTO TEACHER_COURSE VALUES(null,'stephen',1);
INSERT INTO TEACHER_COURSE VALUES(null,'stephen',2);