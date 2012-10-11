CREATE TABLE ASSIGNMENT_WORK (
	ASSIGNMENT_WORK_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	SCORE FLOAT(5),
	ASSIGNMENT_TASK_ID INT(10) UNSIGNED NOT NULL,
	SEND_DATE DATETIME NOT NULL,
	SEND_BY VARCHAR(40) NOT NULL,
	STATUS INT(1) NOT NULL,
	EVALUATE_DATE DATETIME,
	PRIMARY KEY (ASSIGNMENT_WORK_ID),
	FOREIGN KEY (ASSIGNMENT_TASK_ID) REFERENCES ASSIGNMENT_TASK(ASSIGNMENT_TASK_ID),
	FOREIGN KEY (SEND_BY) REFERENCES USERS(USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- STATUS 0 = Pending
-- 		  1 = evaluated