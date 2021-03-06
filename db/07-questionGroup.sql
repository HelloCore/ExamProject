CREATE TABLE QUESTION_GROUP(
 	QUESTION_GROUP_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	QUESTION_GROUP_NAME VARCHAR(100) NOT NULL,
 	COURSE_ID INT(10) UNSIGNED NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	PRIMARY KEY (QUESTION_GROUP_ID),
 	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO QUESTION_GROUP VALUES(1,'Chapter 1',1,1);
INSERT INTO QUESTION_GROUP VALUES(2,'Chapter 2',1,1);
INSERT INTO QUESTION_GROUP VALUES(3,'Chapter 3',1,1);
INSERT INTO QUESTION_GROUP VALUES(4,'Chapter 4',1,1);
INSERT INTO QUESTION_GROUP VALUES(5,'Chapter 5',1,1);
INSERT INTO QUESTION_GROUP VALUES(6,'Chapter 6',1,1);

INSERT INTO QUESTION_GROUP VALUES(7,'Chapter 1',2,1);
INSERT INTO QUESTION_GROUP VALUES(8,'Chapter 2',2,1);
INSERT INTO QUESTION_GROUP VALUES(9,'Chapter 3',2,1);
INSERT INTO QUESTION_GROUP VALUES(10,'Chapter 4',2,1);
INSERT INTO QUESTION_GROUP VALUES(11,'Chapter 5',2,1);
INSERT INTO QUESTION_GROUP VALUES(12,'Chapter 6',2,1);