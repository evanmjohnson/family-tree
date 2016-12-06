DROP DATABASE IF EXISTS familyTree;
CREATE DATABASE  IF NOT EXISTS `familyTree`;

USE familyTree;

DROP TABLE IF EXISTS person;

CREATE TABLE person (
	last_name varchar(45) NOT NULL, 
    first_name varchar(45) NOT NULL, 
    DoB INT(4) NOT NULL, 
    DOD INT(4) DEFAULT NULL, 
    person_id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(person_id)
); 
   
   
DROP TABLE IF EXISTS address; 

CREATE TABLE address ( 
    street varchar(45) NOT NULL, 
    city varchar(45) NOT NULL, 
    state varchar(45), 
    country varchar(45) NOT NULL,
    house_id INT NOT NULL AUTO_INCREMENT,
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


INSERT INTO person VALUES ('Joseph','Kennedy',1888,1969,1);
INSERT INTO person VALUES ('Rose  ','Kennedy',1890,1995,2);
INSERT INTO person VALUES ('Joseph','Kennedy',1915,1944,3);
INSERT INTO person VALUES ('John','Kennedy',1917,1963,4);
INSERT INTO person VALUES ('Jacqueline ','Bouvier',1929,1994,5);
INSERT INTO person VALUES ('Caroline','Kennedy',1957,NULL,6);
INSERT INTO person VALUES ('John','Kennedy',1960,1999,7);
INSERT INTO person VALUES ('Patrick ','Kennedy',1963,NULL,8);
INSERT INTO person VALUES ('Rosemary','Kennedy',1918,2005,9);
INSERT INTO person VALUES ('Kathleen ','Kennedy',1920,1948,10);
INSERT INTO person VALUES ('William ','Cavendish',1917,1944,11);
INSERT INTO person VALUES ('Eunice','Kennedy',1921,2009,12);
INSERT INTO person VALUES ('Patricia','Kennedy',1924,2006,13);
INSERT INTO person VALUES ('Robert','Kennedy',1925,1968,14);
INSERT INTO person VALUES ('Jean','Kennedy',1928,NULL,15);
INSERT INTO person VALUES ('Edward','Kennedy',1932,2009,16);
INSERT INTO person VALUES ('Robert','Shriver',1915,2011,17);
INSERT INTO person VALUES ('Robert','Shriver',1954,NULL,18);
INSERT INTO person VALUES ('Maria ','Shriver',1955,NULL,19);
INSERT INTO person VALUES ('Timothy','Shriver',1959,NULL,20);
INSERT INTO person VALUES ('Mark ','Shriver',1964,NULL,21);
INSERT INTO person VALUES ('Anthony','Shriver',1965,NULL,22);
INSERT INTO person VALUES ('Peter','Lawford',1923,1984,23);
INSERT INTO person VALUES ('Christopher ','Lawford',1955,NULL,24);
INSERT INTO person VALUES ('Sydney','Lawford',1956,NULL,25);
INSERT INTO person VALUES ('Victoria ','Lawford',1958,NULL,26);
INSERT INTO person VALUES ('Robin ','Lawford',1961,NULL,27);
INSERT INTO person VALUES ('Ethel ','Shakel',1928,NULL,28);
INSERT INTO person VALUES ('Kathleen ','Kennedy',1951,NULL,29);
INSERT INTO person VALUES ('Joseph ','Kennedy',1952,NULL,30);
INSERT INTO person VALUES ('Robert','Kennedy',1954,NULL,31);
INSERT INTO person VALUES ('David','Kennedy',1955,NULL,32);
INSERT INTO person VALUES ('Mary','Kennedy',1956,NULL,33);
INSERT INTO person VALUES ('Michael ','Kennedy',1958,NULL,34);
INSERT INTO person VALUES ('Mary','Kennedy',1959,NULL,35);
INSERT INTO person VALUES ('Christopher ','Kennedy',1963,NULL,36);
INSERT INTO person VALUES ('Matthew ','Kennedy',1965,NULL,37);
INSERT INTO person VALUES ('Douglas ','Kennedy',1967,NULL,38);
INSERT INTO person VALUES ('Rory ','Kennedy',1968,NULL,39);
INSERT INTO person VALUES ('Stephen ','Smith',1927,1990,40);
INSERT INTO person VALUES ('Stephen ','Smith',1957,NULL,41);
INSERT INTO person VALUES ('William ','Smith',1960,NULL,42);
INSERT INTO person VALUES ('Amanda','Smith',1967,NULL,43);
INSERT INTO person VALUES ('Kym','Smith',1972,NULL,44);
INSERT INTO person VALUES ('Virginia','Bennett',1936,NULL,45);
INSERT INTO person VALUES ('Kara ','Kennedy',1960,2011,46);
INSERT INTO person VALUES ('Edward ','Kennedy',1961,NULL,47);
INSERT INTO person VALUES ('Patrick ','Kennedy',1967,NULL,48);
INSERT INTO person VALUES ('Victoria ','Reggie',1954,NULL,49);
INSERT INTO person VALUES ('Michael ','Michlin',1966,NULL,50);
INSERT INTO person VALUES ('Irina','Michlin',1963,NULL,51);
INSERT INTO person VALUES ('Dana ','Michlin',1996,NULL,52);
INSERT INTO person VALUES ('Maya','Michlin',1999,NULL,53);
INSERT INTO person VALUES ('Dina','Vishnevsky',1941,NULL,54);
INSERT INTO person VALUES ('Yevgeniy','Michlin',1942,NULL,55);
INSERT INTO person VALUES ('Yevgeniy','Michlin',1988,NULL,56);
INSERT INTO person VALUES ('Sima','Vishnevsky',1965,NULL,57);
INSERT INTO person VALUES ('Alex','Milich',1960,NULL,58);
INSERT INTO person VALUES ('Michael ','Milich',1994,NULL,59);
INSERT INTO person VALUES ('Yossi ','Milich',1995,NULL,60);
INSERT INTO person VALUES ('Yulia','Milich',2001,NULL,61);
    
INSERT INTO address VALUES ('48 Beacon Street','Boston','MA','USA',1);
INSERT INTO address VALUES ('64 Chestnut Hill Avenue','Boston','MA','USA',2);
INSERT INTO address VALUES ('10 Huntington Avenue','Brookline ','MA','USA',3);
INSERT INTO address VALUES ('86 Commonwealth Avenue','Boston ','MA','USA',4);
INSERT INTO address VALUES ('97 Main Street','Boston','MA','USA',5);
INSERT INTO address VALUES ('104 Marquis Plaza','New York ','MA','USA',6);
INSERT INTO address VALUES ('126 Memorial Drive','Boston','MA','USA',7);
INSERT INTO address VALUES ('208 Boylston Street','Boston ','MA','USA',8);
INSERT INTO address VALUES ('16 Columbus Avenue ','Boston','MA','USA',9);
INSERT INTO address VALUES ('18 Charles Street','Boston ','MA','USA',10);
INSERT INTO address VALUES ('69 Wayne Road','Newton','MA','USA',11);
INSERT INTO address VALUES ('40 Leon Street','Boston','MA','USA',12);
INSERT INTO address VALUES ('42 Nahal Zoar','Modiin',NULL,'Israel',13);
INSERT INTO address VALUES ('20 Rehov Zion','Netanya',NULL,'Israel',14);
INSERT INTO address VALUES ('5 Alonim','Lapid',NULL,'Israel ',15);
INSERT INTO address VALUES ('4 Technion','Haifa',NULL,'Israel',16);

INSERT INTO relationship VALUES (1,2,'husband',1);
INSERT INTO relationship VALUES (2,1,'wife ',2);
INSERT INTO relationship VALUES (1,3,'father',3);
INSERT INTO relationship VALUES (3,1,'son',4);
INSERT INTO relationship VALUES (5,4,'sister',5);
INSERT INTO relationship VALUES (4,5,'brother',6);
INSERT INTO relationship VALUES (17,12,'husband',7);
INSERT INTO relationship VALUES (2,24,'grandmother ',8);
INSERT INTO relationship VALUES (1,50,'unrelated',9);

INSERT INTO Reunion VALUES (1,1,10,1,'Christmas','2016-12-25');
INSERT INTO Reunion VALUES (2,5,4,3,NULL,'2014-08-10');
INSERT INTO Reunion VALUES (3,55,3,15,'Hannukkah','2016-12-25');

INSERT INTO house VALUES (1,1,2,1);
INSERT INTO house VALUES (2,2,2,1);
INSERT INTO house VALUES (3,3,1,3);
INSERT INTO house VALUES (17,4,7,17);
INSERT INTO house VALUES (52,5,1,52);
INSERT INTO house VALUES (52,6,1,50);


    
    
    
    
    
    
