DROP DATABASE IF EXISTS familyTree;
CREATE DATABASE  IF NOT EXISTS `familyTree`;

USE familyTree;

DROP TABLE IF EXISTS person;

CREATE TABLE person (
	last_name varchar(45) NOT NULL, 
    first_name varchar(45) NOT NULL, 
    person_id INT NOT NULL AUTO_INCREMENT, 
    DoB DATE NOT NULL, 
    DOD DATE DEFAULT NULL, 
    PRIMARY KEY(person_id)
); 
   
   
DROP TABLE IF EXISTS address; 

CREATE TABLE address ( 
	house_id INT NOT NULL AUTO_INCREMENT,
    street varchar(45) NOT NULL, 
    city varchar(45) NOT NULL, 
    state varchar(45) NOT NULL, 
    country varchar(45) NOT NULL, 
    PRIMARY KEY(house_id) 
); 

 
DROP TABLE IF EXISTS relationship; 

CREATE TABLE relationship ( 
	person1_id INT NOT NULL, 
    person2_id INT NOT NULL,
    relationship varchar(45) NOT NULL,
    relation_id INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (relation_id), 
    CONSTRAINT relationship_bw_people
    FOREIGN KEY(person1_id) REFERENCES person(person_id) 
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(person2_id) REFERENCES person(person_id) 
    ON DELETE CASCADE ON UPDATE CASCADE
);    
    
DROP TABLE IF EXISTS reunion; 

CREATE TABLE reunion ( 
	reunion_id INT NOT NULL,
	head_of_house INT NOT NULL, 
		-- person_id
	num_families_attending INT NOT NULL, 
    address INT NOT NULL,
		-- house_id
	occasion varchar(45) DEFAULT NULL, 
    reunion_date DATE NOT NULL,
	PRIMARY KEY (reunion_id),
    CONSTRAINT reunion_host
    FOREIGN KEY(address) REFERENCES address(house_id)
    ON DELETE CASCADE ON UPDATE CASCADE, 
    FOREIGN KEY(head_of_house) REFERENCES person(person_id) 
    ON DELETE CASCADE ON UPDATE CASCADE
);        
    


DROP TABLE IF EXISTS house;

CREATE TABLE house (
	person_id INT NOT NULL,
    house_id INT NOT NULL AUTO_INCREMENT, 
    num_residents INT NOT NULL, 
    head_of_house INT NOT NULL, 
		-- head_of_house is represented through the person_id
	PRIMARY KEY(house_id), 
    CONSTRAINT family_unit_house_id
    FOREIGN KEY(house_id) REFERENCES address(house_id) 
	ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(person_id) REFERENCES person(person_id) 
    ON DELETE CASCADE ON UPDATE CASCADE
);    


    
    
    
    
    
    
    
    
    
    
    
    
    
    
