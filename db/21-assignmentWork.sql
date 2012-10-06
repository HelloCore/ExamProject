CREATE TABLE ASSIGNMENT_WORK (
	ASSIGNMENT_WORK_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	SCORE INT(5),
	ASSIGNMENT_TASK_ID INT(10) UNSIGNED NOT NULL,
	SEND_DATE DATETIME NOT NULL,
	SEND_BY VARCHAR(40) NOT NULL,
	STATUS INT(1) NOT NULL,
	PRIMARY KEY (ASSIGNMENT_WORK_ID),
	FOREIGN KEY (ASSIGNMENT_TASK_ID) REFERENCES ASSIGNMENT_TASK(ASSIGNMENT_TASK_ID),
	FOREIGN KEY (SEND_BY) REFERENCES USERS(USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;