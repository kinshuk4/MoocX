CREATE TABLE ALBUMS
  (ID int NOT NULL,
   NAME VARCHAR(250) NULL,
   ARTIST VARCHAR(250) NULL,
   ANNUM int,
   COST int,
   ARTISTDETAILS_ID INT NULL);
ALTER TABLE ALBUMS
  ADD CONSTRAINT PK_ALBUMS PRIMARY KEY (ID);


 INSERT INTO ALBUMS (ID, NAME , ARTIST , ANNUM ,COST)
	VALUES (1,'SWAN SONG','NUSRAT FATEH',1999,30);
 INSERT INTO ALBUMS (ID, NAME , ARTIST , ANNUM ,COST)
	VALUES (2,'DEAD MAN WALKING','NUSRAT FATEH',2006,45);
 INSERT INTO ALBUMS (ID, NAME , ARTIST , ANNUM ,COST)
	VALUES (3,'TIMELESS','SUKHVINDER SINGH',2005,28);
 INSERT INTO ALBUMS (ID, NAME , ARTIST , ANNUM ,COST)
	VALUES (4,'RAHAT','RAHAT FATEH',2006,27);