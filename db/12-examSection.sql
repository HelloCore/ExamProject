CREATE TABLE EXAM_SECTION(
	EXAM_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	EXAM_ID INT(10) UNSIGNED NOT NULL,
	SECTION_ID INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (EXAM_SECTION_ID),
	UNIQUE (EXAM_ID,SECTION_ID),
 	FOREIGN KEY(EXAM_ID) REFERENCES EXAM(EXAM_ID),
 	FOREIGN KEY(SECTION_ID) REFERENCES SECTION(SECTION_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;