CREATE TABLE QUESTION_GROUP(
 	QUESTION_GROUP_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	QUESTION_GROUP_NAME VARCHAR(100) NOT NULL,
 	COURSE_ID INT(10) UNSIGNED NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	PRIMARY KEY (QUESTION_GROUP_ID),
 	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO QUESTION_GROUP VALUES(1,'บทที่ 1',1,1);
INSERT INTO QUESTION_GROUP VALUES(2,'บทที่ 2',1,1);
INSERT INTO QUESTION_GROUP VALUES(3,'บทที่ 3',1,1);
INSERT INTO QUESTION_GROUP VALUES(4,'บทที่ 4',1,1);
INSERT INTO QUESTION_GROUP VALUES(5,'บทที่ 5',1,1);
INSERT INTO QUESTION_GROUP VALUES(6,'บทที่ 6',1,1);

INSERT INTO QUESTION_GROUP VALUES(7,'บทที่ 1',2,1);
INSERT INTO QUESTION_GROUP VALUES(8,'บทที่ 2',2,1);
INSERT INTO QUESTION_GROUP VALUES(9,'บทที่ 3',2,1);
INSERT INTO QUESTION_GROUP VALUES(10,'บทที่ 4',2,1);
INSERT INTO QUESTION_GROUP VALUES(11,'บทที่ 5',2,1);
INSERT INTO QUESTION_GROUP VALUES(12,'บทที่ 6',2,1);