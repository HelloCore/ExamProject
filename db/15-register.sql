CREATE TABLE REGISTER(
 	REGISTER_ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 	SECTION_ID INT(10) UNSIGNED NOT NULL,
 	USERNAME VARCHAR(40) NOT NULL,
 	REQUEST_DATE DATETIME NOT NULL,
 	PROCESS_DATE DATETIME,
 	STATUS INT(1) UNSIGNED NOT NULL,
 	PRIMARY KEY (REGISTER_ID),
 	FOREIGN KEY (SECTION_ID) REFERENCES SECTION(SECTION_ID),
 	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- status
-- 0 = pending
-- 1 = accept
-- 2 = denied
-- 3 = change section