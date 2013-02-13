CREATE TABLE ASSIGNMENT_SECTION (
	ASSIGNMENT_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	ASSIGNMENT_TASK_ID INT(10) UNSIGNED NOT NULL,
	SECTION_ID INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (ASSIGNMENT_SECTION_ID),
	FOREIGN KEY (ASSIGNMENT_TASK_ID) REFERENCES ASSIGNMENT_TASK(ASSIGNMENT_TASK_ID),
	FOREIGN KEY (SECTION_ID) REFERENCES SECTION(SECTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;