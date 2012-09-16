CREATE TABLE STUDENT_SECTION(
 	STUDENT_SECTION_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	USERNAME VARCHAR(40) NOT NULL,
 	SECTION_ID INT(10) UNSIGNED NOT NULL,
 	PRIMARY KEY (STUDENT_SECTION_ID),
 	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME),
 	FOREIGN KEY (SECTION_ID) REFERENCES SECTION(SECTION_ID),
 	UNIQUE(USERNAME,SECTION_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO STUDENT_SECTION VALUES(null,'student',1);
INSERT INTO STUDENT_SECTION VALUES(null,'student2',3);