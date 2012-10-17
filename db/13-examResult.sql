CREATE TABLE EXAM_RESULT(
	EXAM_RESULT_ID  INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	EXAM_ID INT(10) UNSIGNED NOT NULL,
 	USERNAME VARCHAR(40) NOT NULL,
	NUM_OF_QUESTION INT NOT NULL,
	EXAM_COUNT INT NOT NULL,
	EXAM_START_DATE DATETIME NOT NULL,
	EXAM_EXPIRE_DATE DATETIME NOT NULL,
	EXAM_COMPLETE_DATE DATETIME,
	EXAM_COMPLETED TINYINT(1) NOT NULL,
	EXAM_IS_VIEWED TINYINT(1),
	EXAM_USED_TIME INT,
	EXAM_CORRECT INT,
	EXAM_SCORE FLOAT,
	PRIMARY KEY (EXAM_RESULT_ID),
	FOREIGN KEY (EXAM_ID) REFERENCES EXAM(EXAM_ID),
	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;