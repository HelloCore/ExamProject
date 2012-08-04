CREATE TABLE COURSE(
 COURSE_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 COURSE_CODE VARCHAR(10) NOT NULL,
 COURSE_NAME VARCHAR(50) NOT NULL,
 PRIMARY KEY (COURSE_ID),
 UNIQUE (COURSE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO COURSE VALUES(1,'CS.111','PROGRAMMING FUNDAMENTAL');
INSERT INTO COURSE VALUES(2,'CS.122','OBJECT-ORIENTED SOFTWARE DEVELOPMENT');