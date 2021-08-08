DROP DATABASE IF EXISTS superheroSightingsDB;
CREATE DATABASE superheroSightingsDB;
USE superheroSightingsDB;

CREATE TABLE `power` (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45),
    element VARCHAR(45),
    description VARCHAR(45)
);

CREATE TABLE location (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45),
    streetNumber VARCHAR(10),
    streetName VARCHAR(45),
    city VARCHAR(45),
    state CHAR(2),
    zip CHAR(5),
    description VARCHAR(160),
    latitude DECIMAL (8, 6),
    longitude DECIMAL (9, 6),
    pic VARCHAR(200) NULL
);

CREATE TABLE `super` (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45),
    morality VARCHAR(7),
    description VARCHAR(160),
    powerId INT,
    pic VARCHAR(200) NULL,
    FOREIGN KEY (powerId) REFERENCES `power`(id)
);

CREATE TABLE organization (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45),
    description VARCHAR(160),
    phone CHAR(10) NULL,
    email VARCHAR(45) NULL,
    locationId INT,
    pic VARCHAR(200) NULL,
    FOREIGN KEY (locationId) REFERENCES location(id)
);

CREATE TABLE sighting (
	id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NULL,
    superId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY (superId) REFERENCES `super`(id),
    FOREIGN KEY (locationId) REFERENCES location(id)
);

CREATE TABLE super_organization (
	superId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY pk_super_organization (superId, organizationId),
    FOREIGN KEY fk_super_organization_super (superId) REFERENCES super(id),
    FOREIGN KEY fk_super_organization_organization (organizationId) REFERENCES organization(id)
);

SELECT * FROM sighting ORDER BY date DESC;