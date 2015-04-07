CREATE SCHEMA if not exists gymDB;
use gymDB;

CREATE TABLE if not exists Managers(
	mid serial,
	name VARCHAR(60) NOT NULL PRIMARY KEY
);

CREATE TABLE if not exists Customers(
	cid serial PRIMARY KEY,
	firstName VARCHAR(60) NOT NULL,
	secondName VARCHAR(60) NOT NULL,
	contactNumber VARCHAR(15) NOT NULL,
	manager VARCHAR(60),
	FOREIGN KEY(manager) REFERENCES Managers(name) ON DELETE SET NULL
);