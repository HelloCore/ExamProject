CREATE TABLE COURSE(
 	COURSE_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	COURSE_CODE VARCHAR(10) NOT NULL,
 	COURSE_NAME VARCHAR(50) NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	PRIMARY KEY (COURSE_ID),
 	UNIQUE (COURSE_CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO COURSE VALUES(1,'CS.111','PROGRAMMING FUNDAMENTAL',1);
INSERT INTO COURSE VALUES(2,'CS.122','OBJECT-ORIENTED SOFTWARE DEVELOPMENT',1);

-- db   ExamProject
-- user examProject
-- pass j4sxJFcEmVmY4Z93CREATE TABLE MASTER_SECTION (
 	MASTER_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	SECTION_YEAR INT(4) NOT NULL,
 	SECTION_SEMESTER  INT(1) NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	STATUS INT(1) NOT NULL,
 	PRIMARY KEY (MASTER_SECTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO MASTER_SECTION VALUES(1,2555,1,1,0);
INSERT INTO MASTER_SECTION VALUES(2,2555,2,1,1);
INSERT INTO MASTER_SECTION VALUES(3,2555,3,1,0);
CREATE TABLE SECTION (
 	SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	SECTION_NAME VARCHAR(10) NOT NULL,
 	COURSE_ID INT(10) UNSIGNED NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	MASTER_SECTION_ID INT(10) UNSIGNED NOT NULL,
 	PRIMARY KEY (SECTION_ID),
 	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID),
 	FOREIGN KEY (MASTER_SECTION_ID) REFERENCES MASTER_SECTION(MASTER_SECTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO SECTION VALUES(1,'011',1,1,1);
INSERT INTO SECTION VALUES(2,'021',1,1,1);
INSERT INTO SECTION VALUES(3,'011',1,1,2);
INSERT INTO SECTION VALUES(4,'021',1,1,2);CREATE TABLE USER_TYPE(
 	TYPE INT(1) UNSIGNED NOT NULL AUTO_INCREMENT,
 	AUTHORITY VARCHAR(45) NOT NULL,
 	TYPE_DESC VARCHAR(50),
 	PRIMARY KEY(TYPE)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO USER_TYPE VALUES(1,'ROLE_ADMIN','ADMIN');
INSERT INTO USER_TYPE VALUES(2,'ROLE_TEACHER','TEACHER');
INSERT INTO USER_TYPE VALUES(3,'ROLE_STUDENT','STUDENT');
INSERT INTO USER_TYPE VALUES(4,'ROLE_NOT_ACTIVE','NOT_ACTIVE_USER');CREATE TABLE PREFIX_NAME(
	PREFIX_NAME_ID INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
	PREFIX_NAME_TH VARCHAR(100) NOT NULL,
	PREFIX_NAME_EN VARCHAR(100) ,
	PRIMARY KEY (PREFIX_NAME_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO PREFIX_NAME VALUES
(1,'นาย','Mr')
,(2,'นาง','Mrs')
,(3,'นางสาว','Ms')
,(4,'อาจารย์',null);CREATE TABLE USERS (
 	USERNAME VARCHAR(40) NOT NULL,
 	PASSWORD VARCHAR(32) NOT NULL,
 	PASSWORDS VARCHAR(40) NOT NULL,
 	EMAIL VARCHAR(100) NOT NULL,
 	PREFIX_NAME_ID INT(4) UNSIGNED NOT NULL,
 	FIRST_NAME VARCHAR(100) NOT NULL,
 	LAST_NAME VARCHAR(100) NOT NULL,
	TYPE INT(1) UNSIGNED, 
 	FLAG TINYINT(1) NOT NULL,
 	ACTIVE_STR VARCHAR(6),
 	UNIQUE  (EMAIL),
 	PRIMARY KEY (USERNAME),
 	FOREIGN KEY (TYPE) REFERENCES USER_TYPE(TYPE),
 	FOREIGN KEY (PREFIX_NAME_ID) REFERENCES PREFIX_NAME(PREFIX_NAME_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO USERS VALUES('admin','21232f297a57a5a743894a0e4a801fc3','admin','aukwat2@hotmail.com',1,'อัครวรรธน์','ชยาภิวัฒน์',1,1,null);
INSERT INTO USERS VALUES('teacher','8d788385431273d11e8b43bb78f3aa41','teacher','teacher@hotmail.com',4,'teacher','last',2,1,null);
INSERT INTO USERS VALUES('stephen','25f9e794323b453885f5181f1b624d0b','123456789','send2abac@gmail.com',4,'สง่า','สงค์เมือง',2,1,null);

INSERT INTO USERS VALUES('520702477348','25f9e794323b453885f5181f1b624d0b','123456789','ultraman_ex@hotmail.com',1,'ณรงค์','แก้วรอดไว',3,1,null);
INSERT INTO USERS VALUES('520702477349','25f9e794323b453885f5181f1b624d0b','123456789','chainat_33@hotmail.com',1,'ชัยณัฐ','กุลนานันท์',3,1,null);
INSERT INTO USERS VALUES('520702477374','25f9e794323b453885f5181f1b624d0b','123456789','theeraphon@hotmail.com',1,'ธีรพล','พืชพันธุ์',3,1,null);
INSERT INTO USERS VALUES('520702477977','25f9e794323b453885f5181f1b624d0b','123456789','karoon@hotmail.com',1,'การุณ','เทพวงค์',3,1,null);
INSERT INTO USERS VALUES('520702478401','25f9e794323b453885f5181f1b624d0b','123456789','pump_junk@hotmail.com',1,'ฐานวุฒิ','วงศ์วิญญูชัย',3,1,null);
INSERT INTO USERS VALUES('520702478404','25f9e794323b453885f5181f1b624d0b','123456789','outbody3@hotmail.com',1,'จตุพร','กลัดใบไม้',3,1,null);

INSERT INTO USERS VALUES('520702478656','25f9e794323b453885f5181f1b624d0b','123456789','saranyoo@hotmail.com',1,'ศรัณยู','ปั้นบรรจง',3,1,null);
INSERT INTO USERS VALUES('520702478767','25f9e794323b453885f5181f1b624d0b','123456789','paiboon@hotmail.com',1,'ไพบูลย์','เดชเปรื่อง',3,1,null);
INSERT INTO USERS VALUES('520702478805','25f9e794323b453885f5181f1b624d0b','123456789','rungwit@hotmail.com',1,'รุ่งวิทย์','วารีสิริสิน',3,1,null);
INSERT INTO USERS VALUES('520702478885','25f9e794323b453885f5181f1b624d0b','123456789','watcharaphon@hotmail.com',1,'วัชรพล','คำโท',3,1,null);
INSERT INTO USERS VALUES('520702478971','25f9e794323b453885f5181f1b624d0b','123456789','aukwat@hotmail.com',1,'อัครวรรธน์','ชยาภิวัฒน์',3,1,null);
INSERT INTO USERS VALUES('520702479745','25f9e794323b453885f5181f1b624d0b','123456789','suksan@hotmail.com',1,'สุขสันต์','เหลืองผดุง',3,1,null);
INSERT INTO USERS VALUES('520702480318','25f9e794323b453885f5181f1b624d0b','123456789','pongsaton@hotmail.com',1,'พงศธร','มูและ',3,1,null);
INSERT INTO USERS VALUES('520702480375','25f9e794323b453885f5181f1b624d0b','123456789','natthanath@hotmail.com',1,'ณัฐนาถ','ภูมิเจริญ',3,1,null);
CREATE TABLE STUDENT_SECTION(
 	STUDENT_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	USERNAME VARCHAR(40) NOT NULL,
 	SECTION_ID INT(10) UNSIGNED NOT NULL,
 	PRIMARY KEY (STUDENT_SECTION_ID),
 	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME),
 	FOREIGN KEY (SECTION_ID) REFERENCES SECTION(SECTION_ID),
 	UNIQUE(USERNAME,SECTION_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE TEACHER_COURSE(
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
INSERT INTO TEACHER_COURSE VALUES(null,'stephen',2);CREATE TABLE QUESTION_GROUP(
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
INSERT INTO QUESTION_GROUP VALUES(12,'บทที่ 6',2,1);CREATE TABLE QUESTION(
 	QUESTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	QUESTION_TEXT TEXT NOT NULL,
 	QUESTION_GROUP_ID INT(10) UNSIGNED NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	PRIMARY KEY(QUESTION_ID),
 	FOREIGN KEY (QUESTION_GROUP_ID) REFERENCES QUESTION_GROUP(QUESTION_GROUP_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE ANSWER(
 	ANSWER_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	ANSWER_TEXT TEXT NOT NULL,
 	ANSWER_SCORE INT(1) NOT NULL,
 	QUESTION_ID INT(10) UNSIGNED NOT NULL,
 	FLAG TINYINT(1) NOT NULL,
 	PRIMARY KEY(ANSWER_ID),
 	FOREIGN KEY(QUESTION_ID) REFERENCES QUESTION(QUESTION_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE EXAM(
	EXAM_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	EXAM_HEADER VARCHAR(200) NOT NULL,
	START_DATE DATETIME,
	END_DATE DATETIME,
	EXAM_LIMIT INT UNSIGNED NOT NULL,
	MIN_QUESTION INT UNSIGNED NOT NULL,
	MAX_QUESTION INT UNSIGNED NOT NULL,
	COURSE_ID INT(10) UNSIGNED NOT NULL,
	IS_CAL_SCORE TINYINT(1) NOT NULL,
	MAX_SCORE INT(4) UNSIGNED NOT NULL,
	TIME_LIMIT_SECOND INT(10) UNSIGNED NOT NULL,
	EXAM_SEQUENCE INT(1) UNSIGNED NOT NULL,
	FLAG TINYINT(1) NOT NULL,
	PRIMARY KEY(EXAM_ID),
	FOREIGN KEY(COURSE_ID) REFERENCES COURSE(COURSE_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE EXAM_SECTION(
	EXAM_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	EXAM_ID INT(10) UNSIGNED NOT NULL,
	SECTION_ID INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (EXAM_SECTION_ID),
	UNIQUE (EXAM_ID,SECTION_ID),
 	FOREIGN KEY(EXAM_ID) REFERENCES EXAM(EXAM_ID),
 	FOREIGN KEY(SECTION_ID) REFERENCES SECTION(SECTION_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE EXAM_QUESTION_GROUP(
	EXAM_QUESTION_GROUP_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	EXAM_ID INT(10) UNSIGNED NOT NULL,
	QUESTION_GROUP_ID INT(10) UNSIGNED NOT NULL,
	QUESTION_PERCENT INT(3) UNSIGNED NOT NULL,
	SECOND_PER_QUESTION INT NOT NULL,
	ORDINAL INT NOT NULL,
	PRIMARY KEY (EXAM_QUESTION_GROUP_ID),
	UNIQUE (EXAM_ID,QUESTION_GROUP_ID),
 	FOREIGN KEY(EXAM_ID) REFERENCES EXAM(EXAM_ID),
 	FOREIGN KEY(QUESTION_GROUP_ID) REFERENCES QUESTION_GROUP(QUESTION_GROUP_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE EXAM_RESULT(
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
)ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE EXAM_RESULT_ANSWER(
	EXAM_RESULT_ANSWER_ID  INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	EXAM_RESULT_ID  INT(10) UNSIGNED NOT NULL,
	ORDINAL INT UNSIGNED NOT NULL,
	QUESTION_ID INT(10) UNSIGNED NOT NULL,
	ANSWER_ID INT(10) UNSIGNED,
	ANSWER_ID_1 INT(10) UNSIGNED NOT NULL,
	ANSWER_ID_2 INT(10) UNSIGNED NOT NULL,
	ANSWER_ID_3 INT(10) UNSIGNED NOT NULL,
	ANSWER_ID_4 INT(10) UNSIGNED NOT NULL,
	UNIQUE(EXAM_RESULT_ID,QUESTION_ID),
	UNIQUE(EXAM_RESULT_ID,ANSWER_ID),
	PRIMARY KEY (EXAM_RESULT_ANSWER_ID),
	FOREIGN KEY (EXAM_RESULT_ID) REFERENCES EXAM_RESULT(EXAM_RESULT_ID),
	FOREIGN KEY (QUESTION_ID) REFERENCES QUESTION(QUESTION_ID),
	FOREIGN KEY (ANSWER_ID) REFERENCES ANSWER(ANSWER_ID),
	FOREIGN KEY (ANSWER_ID_1) REFERENCES ANSWER(ANSWER_ID),
	FOREIGN KEY (ANSWER_ID_2) REFERENCES ANSWER(ANSWER_ID),
	FOREIGN KEY (ANSWER_ID_3) REFERENCES ANSWER(ANSWER_ID),
	FOREIGN KEY (ANSWER_ID_4) REFERENCES ANSWER(ANSWER_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
--SELECT q.question_text,a1.answer_text,a2.answer_text,a3.answer_text,a4.answer_text
--FROM question q INNER JOIN answer a1 ON a1.question_id = q.question_id  and a1.answer_score =1 
--INNER JOIN answer a2 ON a2.question_id = q.question_id and a1.answer_id <> a2.answer_id and a2.answer_score =0 
--INNER JOIN answer a3 ON a3.question_id = q.question_id and a2.answer_id <> a3.answer_id and a3.answer_score =0 
--INNER JOIN answer a4 ON a4.question_id = q.question_id and a3.answer_id <> a4.answer_id and a4.answer_score =0
--GROUP BY q.question_id ORDER BY RAND()
--
--
--
---- (SELECT
----        q.QUESTION_ID  ,
----        q.QUESTION_TEXT  ,
----        a1.ANSWER_ID  ,
----        a1.ANSWER_TEXT  ,
----        a2.ANSWER_ID  ,
----        a2.ANSWER_TEXT  ,
----        a3.ANSWER_ID  ,
----        a3.ANSWER_TEXT  ,
----        a4.ANSWER_ID  ,
----        a4.ANSWER_TEXT  
----    FROM
----        QUESTION q  
----    INNER JOIN
----        ANSWER a1 
----            ON a1.QUESTION_ID = q.QUESTION_ID  
----            AND a1.ANSWER_SCORE = 1 
----            AND a1.FLAG = 1  
----    INNER JOIN
----        ANSWER a2 
----            ON a2.QUESTION_ID = q.QUESTION_ID  
----            AND a2.ANSWER_SCORE = 0 
----            AND a2.FLAG = 1  
----    INNER JOIN
----        ANSWER a3 
----            ON a3.QUESTION_ID = q.QUESTION_ID  
----            AND a3.ANSWER_SCORE = 0 
----            AND a3.FLAG = 1  
----    INNER JOIN
----        ANSWER a4 
----            ON a4.QUESTION_ID = q.QUESTION_ID  
----            AND a4.ANSWER_SCORE = 0 
----            AND a4.FLAG = 1  
----    WHERE
----        q.QUESTION_GROUP_ID = 1
----        AND q.FLAG = 1  
----    GROUP BY
----        q.QUESTION_ID  
----    ORDER BY
----        RAND() LIMIT 1
----    )UNION (
----    SELECT
----        q.QUESTION_ID  ,
----        q.QUESTION_TEXT  ,
----        a1.ANSWER_ID  ,
----        a1.ANSWER_TEXT  ,
----        a2.ANSWER_ID  ,
----        a2.ANSWER_TEXT  ,
----        a3.ANSWER_ID  ,
----        a3.ANSWER_TEXT  ,
----        a4.ANSWER_ID  ,
----        a4.ANSWER_TEXT  
----    FROM
----        QUESTION q  
----    INNER JOIN
----        ANSWER a1 
----            ON a1.QUESTION_ID = q.QUESTION_ID  
----            AND a1.ANSWER_SCORE = 1 
----            AND a1.FLAG = 1  
----    INNER JOIN
----        ANSWER a2 
----            ON a2.QUESTION_ID = q.QUESTION_ID  
----            AND a2.ANSWER_SCORE = 0 
----            AND a2.FLAG = 1  
----    INNER JOIN
----        ANSWER a3 
----            ON a3.QUESTION_ID = q.QUESTION_ID  
----            AND a3.ANSWER_SCORE = 0 
----            AND a3.FLAG = 1  
----    INNER JOIN
----        ANSWER a4 
----            ON a4.QUESTION_ID = q.QUESTION_ID  
----            AND a4.ANSWER_SCORE = 0 
----            AND a4.FLAG = 1  
----    WHERE
----        q.QUESTION_GROUP_ID = 5  
----        AND q.FLAG = 1  
----    GROUP BY
----        q.QUESTION_ID  
----    ORDER BY
----        RAND() LIMIT 14 )
--
--
--
--	select
--        question0_.QUESTION_ID as col_0_0_,
--        answer1_.ANSWER_ID as col_1_0_,
--        answer2_.ANSWER_ID as col_2_0_,
--        answer3_.ANSWER_ID as col_3_0_,
--        answer4_.ANSWER_ID as col_4_0_ 
--    from
--        QUESTION question0_ cross 
--    join
--        ANSWER answer1_ cross 
--    join
--        ANSWER answer2_ cross 
--    join
--        ANSWER answer3_ cross 
--    join
--        ANSWER answer4_ 
--    where
--        question0_.QUESTION_GROUP_ID = 5
--        and answer1_.QUESTION_ID=question0_.QUESTION_ID 
--        and answer1_.ANSWER_SCORE=1 
--        and answer1_.FLAG=1 
--        and answer2_.QUESTION_ID=question0_.QUESTION_ID 
--        and answer2_.ANSWER_SCORE=0 
--        and answer2_.FLAG=1 
--        and answer3_.QUESTION_ID=question0_.QUESTION_ID 
--        and answer3_.ANSWER_ID<>answer2_.ANSWER_ID 
--        and answer3_.ANSWER_SCORE=0 
--        and answer2_.FLAG=1 
--        and answer4_.QUESTION_ID=question0_.QUESTION_ID 
--        and answer4_.ANSWER_SCORE=0 
--        and answer2_.FLAG=1 
--        and answer4_.ANSWER_ID<>answer3_.ANSWER_ID 
--        and answer4_.ANSWER_ID<>answer2_.ANSWER_ID 
--    group by
--        question0_.QUESTION_ID 
--    order by
--        rand() limit 20
--     ;   




CREATE TABLE REGISTER(
 	REGISTER_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	SECTION_ID INT(10) UNSIGNED NOT NULL,
 	USERNAME VARCHAR(40) NOT NULL,
 	REQUEST_DATE DATETIME NOT NULL,
 	PROCESS_DATE DATETIME,
 	STATUS INT(1) UNSIGNED NOT NULL,
 	VERIFY_BY VARCHAR(40),
 	PRIMARY KEY (REGISTER_ID),
 	FOREIGN KEY (SECTION_ID) REFERENCES SECTION(SECTION_ID),
 	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME),
 	FOREIGN KEY (VERIFY_BY) REFERENCES USERS(USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- status
-- 0 = pending
-- 1 = accept
-- 2 = denied
-- 3 = change section

-- ระหว่างย้าย Section
-- > ก่อนย้ายขึ้นเตือนว่าสิทธิ์ใน Section เก่าจะถูกลบ ต้องการย้าย Section อีกหรือไม่
-- > ลบ Section เก่าใน [STUDENT_SECTION] ออก
-- > จากนั้นเปลี่ยน STATUS เป็น 3 
-- > แล้วเปลี่ยน SECTION_ID ใน REGISTER เป็น SECTION ใหม่
-- > รออาจารย์อนุมัติ


CREATE TABLE NEWS (
	NEWS_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	NEWS_HEADER VARCHAR(255) NOT NULL,
	NEWS_CONTENT TEXT NOT NULL,
	COURSE_ID INT(10) UNSIGNED,
	FLAG TINYINT(1) NOT NULL,
	UPDATE_DATE DATETIME NOT NULL,
	USERNAME VARCHAR(40) NOT NULL,
	PRIMARY KEY(NEWS_ID),
 	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME),
	FOREIGN KEY(COURSE_ID) REFERENCES COURSE(COURSE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- COURSE ID > NULL = Web NewsCREATE TABLE CONTENT_PATH (
	CONTENT_PATH_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	CONTENT_PATH_NAME VARCHAR(150) NOT NULL,
	CONTENT_PATH_DESC VARCHAR(255),
	CONTENT_PATH_LOCATION VARCHAR(255),
	PARENT_PATH_ID INT(10) UNSIGNED,
	COURSE_ID INT(10) UNSIGNED,
	VIEW_COUNT INT(5) UNSIGNED NOT NULL,
	UNIQUE (CONTENT_PATH_NAME,PARENT_PATH_ID),
	PRIMARY KEY (CONTENT_PATH_ID),
	FOREIGN KEY (PARENT_PATH_ID) REFERENCES CONTENT_PATH(CONTENT_PATH_ID),
	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO CONTENT_PATH VALUES (1,'ROOT',null,'resources',null,null,0);
INSERT INTO CONTENT_PATH VALUES (2,'CS.111',null,'resources/CS.111/',1,1,0);
INSERT INTO CONTENT_PATH VALUES (3,'CS.122',null,'resources/CS.122/',1,2,0);

CREATE TABLE CONTENT_FILE (
	CONTENT_FILE_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	CONTENT_FILE_NAME VARCHAR(150) NOT NULL,
	CONTENT_FILE_PATH VARCHAR(255) NOT NULL,
	CONTENT_FILE_TYPE INT(2) UNSIGNED NOT NULL,
	CONTENT_FILE_DESC VARCHAR(255),
	CONTENT_FILE_SIZE INT(10) UNSIGNED NOT NULL,
	CONTENT_PATH_ID INT(10) UNSIGNED,
	CREATE_DATETIME DATETIME NOT NULL,
	CREATE_BY VARCHAR(40) NOT NULL,
	VIEW_COUNT INT(5) UNSIGNED NOT NULL,
	UNIQUE (CONTENT_FILE_NAME,CONTENT_PATH_ID),
	PRIMARY KEY (CONTENT_FILE_ID),
	FOREIGN KEY (CONTENT_PATH_ID) REFERENCES CONTENT_PATH(CONTENT_PATH_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- CONTENT_FILE_TYPE
-- 1 - Word
-- 2 - Excel
-- 3 - PowerPoint
-- 4 - PDF
-- 5 - Java
-- 6 - Zip
-- 7 - OtherCREATE TABLE ASSIGNMENT_TASK (
	ASSIGNMENT_TASK_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	ASSIGNMENT_TASK_NAME VARCHAR(255) NOT NULL,
	ASSIGNMENT_DESC TEXT NOT NULL,
	START_DATE DATETIME NOT NULL,
	END_DATE DATETIME NOT NULL,
	NUM_OF_FILE INT(2) NOT NULL,
	LIMIT_FILE_SIZE_KB INT(10) UNSIGNED NOT NULL,
	COURSE_ID INT(10) UNSIGNED NOT NULL,
	CREATE_DATE DATETIME NOT NULL,
	CREATE_BY VARCHAR(40) NOT NULL,
	MAX_SCORE FLOAT(6) NOT NULL,
	FLAG TINYINT(1) NOT NULL,
	IS_EVALUATE_COMPLETE TINYINT(1) NOT NULL,
	PRIMARY KEY (ASSIGNMENT_TASK_ID),
	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID),
	FOREIGN KEY (CREATE_BY) REFERENCES USERS(USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE ASSIGNMENT_SECTION (
	ASSIGNMENT_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	ASSIGNMENT_TASK_ID INT(10) UNSIGNED NOT NULL,
	SECTION_ID INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (ASSIGNMENT_SECTION_ID),
	FOREIGN KEY (ASSIGNMENT_TASK_ID) REFERENCES ASSIGNMENT_TASK(ASSIGNMENT_TASK_ID),
	FOREIGN KEY (SECTION_ID) REFERENCES SECTION(SECTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;CREATE TABLE ASSIGNMENT_WORK (
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
-- 		  1 = evaluatedCREATE TABLE ASSIGNMENT_FILE (
	ASSIGNMENT_FILE_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	ASSIGNMENT_WORK_ID INT(10) UNSIGNED NOT NULL,
	CONTENT MEDIUMBLOB NOT NULL,
	FILE_NAME VARCHAR(255) NOT NULL,
	CONTENT_TYPE VARCHAR(255) NOT NULL,
	PRIMARY KEY (ASSIGNMENT_FILE_ID),
	FOREIGN KEY (ASSIGNMENT_WORK_ID) REFERENCES ASSIGNMENT_WORK(ASSIGNMENT_WORK_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;