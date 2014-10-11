-- create_HL7.SQL
-- Mark Addinall
-- August 2014
--
-- Create the HL7 v2.5 database for these routines.
-- the captured data will be used to perform various
-- statistical reporting on the ranking of hospitals
-- and outcomes


CREATE TABLE hospital_outcomes (
			Provider_Number VARCHAR(255)  NOT NULL,
			Hospital_Name VARCHAR(255)  NOT NULL,
			Address_1 VARCHAR(255)  NULL,
			Address_2 VARCHAR(255)  NULL,
			Address_3 VARCHAR(255)  NULL,
			City VARCHAR(255)  NULL,
			State VARCHAR(255)  NULL,
			ZIP_Code VARCHAR(255)  NULL,
			County_Name VARCHAR(255)  NULL,
			Phone_Number VARCHAR(255)  NULL,
			Death_Rates_Heart_Attack FLOAT NULL,
			Comp_Death_Rates_Heart_Attack VARCHAR(255)  NULL,
			Lower_Death_Rates_from_Heart_Attack FLOAT  NULL,
			Upper_Death_Rates_from_Heart_Attack FLOAT  NULL,
			Number_Death_Rates_Heart_Attack INTEGER  NULL,
			Note_Death_Rates_from_Heart_Attack VARCHAR(255)  NULL,
			Death_Rates_Heart_Failure FLOAT  NULL,
			Comp_Death_Rates_from_Heart_Failure VARCHAR(255)  NULL,
			Lower_Death_Rates_from_Heart_Failure FLOAT  NULL,
			Upper_Death_Rates_from_Heart_Failure FLOAT  NULL,
			Number_Death_Rates_from_Heart_Failure INTEGER  NULL,
			Note_Death_Rates_from_Heart_Failure FLOAT  NULL,
			Death_Rates_Pneumonia FLOAT  NULL,
			Comp_Death_Rates_from_Pneumonia VARCHAR(255)  NULL,
			Lower_Death_Rates_from_Pneumonia FLOAT  NULL,
			Upper_Death_Rates_from_Pneumonia FLOAT  NULL,
			Number_Death_Rates_from_Pneumonia INTEGER  NULL,
			Note_Death_Rates_from_Pneumonia VARCHAR(255)  NULL,
			Hospital_30_Day_Readmission_Rates_from_Heart_Attack FLOAT  NULL,
			Comp_Readmission_Rates_from_Heart_Attack VARCHAR(255)  NULL,
			Est_UReadmission_Rates_from_Heart_Attack FLOAT  NULL,
			Est_LReadmission_Rates_from_Heart_Attack FLOAT  NULL,
			Number_Readmission_Rates_from_Heart_Attack INTEGER  NULL,
			Note_Readmission_Rates_from_Heart_Attack VARCHAR(255)  NULL,
			Hospital_30_Day_Readmission_Rates_from_Heart_Failure FLOAT  NULL,
			Comp_Readmission_Rates_from_Heart_Failure VARCHAR(255)  NULL,
			Est_UReadmission_Rates_from_Heart_Failure FLOAT  NULL,
			Est_LReadmission_Rates_from_Heart_Failure FLOAT  NULL,
			Number_Readmission_Rates_from_Heart_Failure INTEGER  NULL,
			Note_Readmission_Rates_from_Heart_Failure VARCHAR(255)  NULL,
			Hospital_30_Day_Readmission_Rates_from_Pneumonia FLOAT  NULL,
			Comp_Readmission_Rates_from_Pneumonia VARCHAR(255)  NULL,
			Est_UReadmission_Rates_from_Pneumonia FLOAT  NULL,
			Est_LReadmission_Rates_from_Pneumonia FLOAT  NULL,
			Number_Readmission_Rates_from_Pneumonia INTEGER  NULL,
			Note_Readmission_Rates_from_Pneumonia FLOAT,
			id INT AUTO_INCREMENT NOT NULL,
		PRIMARY KEY(id)
	);
		

create table states (State VARCHAR(10));

create table maladies (illness VARCHAR(255), long_name VARCHAR(255));

create table WIES_separations (WIES_code VARCHAR(64), WIES_description VARCHAR(255), rate FLOAT);

