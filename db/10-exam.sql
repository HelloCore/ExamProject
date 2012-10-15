CREATE TABLE EXAM(
 	EXAM_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	EXAM_HEADER VARCHAR(200) NOT NULL,
 	START_DATE DATETIME,
 	END_DATE DATETIME,
 	EXAM_LIMIT INT UNSIGNED NOT NULL,
 	MIN_QUESTION INT UNSIGNED NOT NULL,
 	MAX_QUESTION INT UNSIGNED NOT NULL,
 	COURSE_ID INT(10) UNSIGNED NOT NULL,
 	EXAM_SEQUENCE INT(1) UNSIGNED NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	PRIMARY KEY(EXAM_ID),
 	FOREIGN KEY(COURSE_ID) REFERENCES COURSE(COURSE_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;