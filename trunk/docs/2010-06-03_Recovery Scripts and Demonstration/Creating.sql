create table Contractor
(
username VARCHAR2 (20) PRIMARY KEY,
name VARCHAR2 (50) NOT NULL,
contact VARCHAR2 (30) NOT NULL,
phone CHARACTER (10) NOT NULL,
email VARCHAR2 (60) NOT NULL,
address VARCHAR2 (100) NOT NULL
);

create table Laboratory
(
username VARCHAR2 (20) PRIMARY KEY,
name VARCHAR2 (50) NOT NULL,
contact VARCHAR2 (30) NOT NULL,
phone CHARACTER (10) NOT NULL,
email VARCHAR2 (60) NOT NULL,
address VARCHAR2 (100) NOT NULL
);





create table EnvironmentalOfficer
(
username VARCHAR2 (20) PRIMARY KEY,
name VARCHAR2 (50) NOT NULL,
phone CHARACTER (10) NOT NULL,
email VARCHAR2 (60) NOT NULL
);

create table ScreeningProgram
(
id INT,
start_date DATE NOT NULL,
complete CHAR (1),
username VARCHAR2 (20) NOT NULL,
PRIMARY KEY (id)
);





create table Sample
(
id NUMBER (19,0) NOT NULL,
date_taken DATE NOT NULL,
PH number(5,2),
EC number(5),
temperature number(5,1),
collar_depth number(5),
arsenic number(5,3),
grease number(3),
fluoride number(5,3),
chromium number(5,3),
username VARCHAR2 (20) NOT NULL,
tag VARCHAR2 (5) NOT NULL,
PRIMARY KEY (id)
);

create table Sampler
(
tag VARCHAR2 (5)  NOT NULL,
license VARCHAR2 (20),
longtitude number(9,6),
latitude number(9,6),
collar_height number(5),
comp_screening_freq VARCHAR2 (15) NOT NULL,
purpose VARCHAR2 (50),
contractor VARCHAR2 (20),
waterbody VARCHAR2 (30),
laboratory VARCHAR2 (20),
depth_to_collar_screening_freq VARCHAR2 (15) NOT NULL,
PRIMARY KEY (tag)
);





create table ParameterNames
(
parameter_id INT,
name VARCHAR2 (20) NOT NULL,
primary key (parameter_id)
);

create table FrequencyItem
(
frequency_id INT NOT NULL,
parameter_id INT NOT NULL, 
PRIMARY KEY (frequency_id, parameter_id)
);





create table ScreeningItem
(
program_id INT NOT NULL,
tag VARCHAR2 (5) NOT NULL,
parameter_id INT NOT NULL,
PRIMARY KEY (program_id, tag, parameter_id)
);

create table ScreeningProgramSamplers
(
id INT,
tag VARCHAR2 (5) NOT NULL,
status CHAR (1) NOT NULL,
PRIMARY KEY (id, tag)
);




create table ParameterThresholds
(
name VARCHAR2 (30) NOT NULL,
parameter_id INT NOT NULL,
Min number,
Max number,
PRIMARY KEY (name, parameter_id)
);

create table SamplerMedia
(
id INT,
content blob,
tag VARCHAR2 (5) NOT NULL,
PRIMARY KEY (id)
);





create table ScreeningFrequency
(
id INT,
description VARCHAR2 (20) NOT NULL,
frequency VARCHAR2 (15) NOT NULL,
tag VARCHAR2 (5) NOT NULL,
PRIMARY KEY (id)
);

create table TigersUser
(
username VARCHAR2 (20) NOT NULL,
password VARCHAR2 (20) NOT NULL,
activated CHAR (1),
deleted CHAR (1),
PRIMARY KEY (username)
);





create table Waterbody
(
name VARCHAR2 (30) PRIMARY KEY,
type CHARACTER (1) NOT NULL
);






create sequence hibernate_sequence;