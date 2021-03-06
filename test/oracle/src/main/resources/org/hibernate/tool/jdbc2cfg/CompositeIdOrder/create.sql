CREATE TABLE REQUEST (REQUEST_KEY NUMBER(11) NOT NULL, TIMEFRAME_KEY NUMBER(11))
CREATE UNIQUE INDEX PK_REQUEST ON REQUEST(REQUEST_KEY)
ALTER TABLE REQUEST ADD (CONSTRAINT PK_REQUEST PRIMARY KEY (REQUEST_KEY))
CREATE TABLE SCHEDULE (SCHEDULE_KEY NUMBER(11) NOT NULL, TITLE VARCHAR2(255) NOT NULL)
CREATE UNIQUE INDEX PK_SCHEDULE ON SCHEDULE (SCHEDULE_KEY)
ALTER TABLE SCHEDULE ADD (CONSTRAINT PK_SCHEDULE PRIMARY KEY (SCHEDULE_KEY))
CREATE TABLE COURSE (SCHEDULE_KEY NUMBER(11) NOT NULL, REQUEST_KEY NUMBER(11) NOT NULL, TIMEFRAME_KEY NUMBER(11))
CREATE UNIQUE INDEX PK_COURSE ON COURSE (SCHEDULE_KEY, REQUEST_KEY)
ALTER TABLE COURSE ADD (CONSTRAINT PK_COURSE PRIMARY KEY (SCHEDULE_KEY, REQUEST_KEY))
ALTER TABLE COURSE ADD (CONSTRAINT FK_COURSE__REQUEST FOREIGN KEY (REQUEST_KEY) REFERENCES REQUEST (REQUEST_KEY) ON DELETE CASCADE)
ALTER TABLE COURSE ADD (CONSTRAINT FK_COURSE__SCHEDULE FOREIGN KEY (SCHEDULE_KEY) REFERENCES SCHEDULE (SCHEDULE_KEY) ON DELETE CASCADE)
CREATE TABLE COURSE_TOPIC (SCHEDULE_KEY NUMBER(11) NOT NULL, REQUEST_KEY NUMBER(11) NOT NULL, TOPIC_KEY NUMBER(11))
ALTER TABLE COURSE_TOPIC ADD (CONSTRAINT FK_COURSE_TOPIC__COURSE FOREIGN KEY (SCHEDULE_KEY, REQUEST_KEY) REFERENCES COURSE (SCHEDULE_KEY,REQUEST_KEY) ON DELETE CASCADE)
ALTER TABLE COURSE_TOPIC ADD (CONSTRAINT PK_COURSE_TOPIC PRIMARY KEY (TOPIC_KEY))
