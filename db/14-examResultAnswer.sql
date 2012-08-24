CREATE TABLE EXAM_RESULT_ANSWER(
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


SELECT q.question_text,a1.answer_text,a2.answer_text,a3.answer_text,a4.answer_text
FROM question q INNER JOIN answer a1 ON a1.question_id = q.question_id  and a1.answer_score =1 
INNER JOIN answer a2 ON a2.question_id = q.question_id and a1.answer_id <> a2.answer_id and a2.answer_score =0 
INNER JOIN answer a3 ON a3.question_id = q.question_id and a2.answer_id <> a3.answer_id and a3.answer_score =0 
INNER JOIN answer a4 ON a4.question_id = q.question_id and a3.answer_id <> a4.answer_id and a4.answer_score =0
GROUP BY q.question_id ORDER BY RAND()



-- (SELECT
--        q.QUESTION_ID  ,
--        q.QUESTION_TEXT  ,
--        a1.ANSWER_ID  ,
--        a1.ANSWER_TEXT  ,
--        a2.ANSWER_ID  ,
--        a2.ANSWER_TEXT  ,
--        a3.ANSWER_ID  ,
--        a3.ANSWER_TEXT  ,
--        a4.ANSWER_ID  ,
--        a4.ANSWER_TEXT  
--    FROM
--        QUESTION q  
--    INNER JOIN
--        ANSWER a1 
--            ON a1.QUESTION_ID = q.QUESTION_ID  
--            AND a1.ANSWER_SCORE = 1 
--            AND a1.FLAG = 1  
--    INNER JOIN
--        ANSWER a2 
--            ON a2.QUESTION_ID = q.QUESTION_ID  
--            AND a2.ANSWER_SCORE = 0 
--            AND a2.FLAG = 1  
--    INNER JOIN
--        ANSWER a3 
--            ON a3.QUESTION_ID = q.QUESTION_ID  
--            AND a3.ANSWER_SCORE = 0 
--            AND a3.FLAG = 1  
--    INNER JOIN
--        ANSWER a4 
--            ON a4.QUESTION_ID = q.QUESTION_ID  
--            AND a4.ANSWER_SCORE = 0 
--            AND a4.FLAG = 1  
--    WHERE
--        q.QUESTION_GROUP_ID = 1
--        AND q.FLAG = 1  
--    GROUP BY
--        q.QUESTION_ID  
--    ORDER BY
--        RAND() LIMIT 1
--    )UNION (
--    SELECT
--        q.QUESTION_ID  ,
--        q.QUESTION_TEXT  ,
--        a1.ANSWER_ID  ,
--        a1.ANSWER_TEXT  ,
--        a2.ANSWER_ID  ,
--        a2.ANSWER_TEXT  ,
--        a3.ANSWER_ID  ,
--        a3.ANSWER_TEXT  ,
--        a4.ANSWER_ID  ,
--        a4.ANSWER_TEXT  
--    FROM
--        QUESTION q  
--    INNER JOIN
--        ANSWER a1 
--            ON a1.QUESTION_ID = q.QUESTION_ID  
--            AND a1.ANSWER_SCORE = 1 
--            AND a1.FLAG = 1  
--    INNER JOIN
--        ANSWER a2 
--            ON a2.QUESTION_ID = q.QUESTION_ID  
--            AND a2.ANSWER_SCORE = 0 
--            AND a2.FLAG = 1  
--    INNER JOIN
--        ANSWER a3 
--            ON a3.QUESTION_ID = q.QUESTION_ID  
--            AND a3.ANSWER_SCORE = 0 
--            AND a3.FLAG = 1  
--    INNER JOIN
--        ANSWER a4 
--            ON a4.QUESTION_ID = q.QUESTION_ID  
--            AND a4.ANSWER_SCORE = 0 
--            AND a4.FLAG = 1  
--    WHERE
--        q.QUESTION_GROUP_ID = 5  
--        AND q.FLAG = 1  
--    GROUP BY
--        q.QUESTION_ID  
--    ORDER BY
--        RAND() LIMIT 14 )



	select
        question0_.QUESTION_ID as col_0_0_,
        answer1_.ANSWER_ID as col_1_0_,
        answer2_.ANSWER_ID as col_2_0_,
        answer3_.ANSWER_ID as col_3_0_,
        answer4_.ANSWER_ID as col_4_0_ 
    from
        QUESTION question0_ cross 
    join
        ANSWER answer1_ cross 
    join
        ANSWER answer2_ cross 
    join
        ANSWER answer3_ cross 
    join
        ANSWER answer4_ 
    where
        question0_.QUESTION_GROUP_ID = 5
        and answer1_.QUESTION_ID=question0_.QUESTION_ID 
        and answer1_.ANSWER_SCORE=1 
        and answer1_.FLAG=1 
        and answer2_.QUESTION_ID=question0_.QUESTION_ID 
        and answer2_.ANSWER_SCORE=0 
        and answer2_.FLAG=1 
        and answer3_.QUESTION_ID=question0_.QUESTION_ID 
        and answer3_.ANSWER_ID<>answer2_.ANSWER_ID 
        and answer3_.ANSWER_SCORE=0 
        and answer2_.FLAG=1 
        and answer4_.QUESTION_ID=question0_.QUESTION_ID 
        and answer4_.ANSWER_SCORE=0 
        and answer2_.FLAG=1 
        and answer4_.ANSWER_ID<>answer3_.ANSWER_ID 
        and answer4_.ANSWER_ID<>answer2_.ANSWER_ID 
    group by
        question0_.QUESTION_ID 
    order by
        rand() limit 20
     ;   




