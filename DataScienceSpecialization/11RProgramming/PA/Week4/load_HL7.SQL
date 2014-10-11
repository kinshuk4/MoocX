-- load_HL7.SQL
-- Mark Addinall
-- August 2014
--
-- Load the supplied CSV data into the HL7 v2.5 database for these routines.
-- the captured data will be used to perform various
-- statistical reporting on the ranking of hospitals
-- and outcomes


LOAD DATA INFILE '/tmp/care' 
	INTO TABLE hospital_outcomes 
		FIELDS TERMINATED BY ',' 
		CLOSED BY '"' 
		LINES TERMINATED BY '\n' 
			IGNORE 1 ROWS;

