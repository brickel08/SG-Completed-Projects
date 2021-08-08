DROP DATABASE IF EXISTS guessTheNumberDB;

CREATE DATABASE IF NOT EXISTS guessTheNumberDB;

USE guessTheNumberDB;

CREATE TABLE Game (
	id INT PRIMARY KEY AUTO_INCREMENT,
    answer CHAR(4) NOT NULL,
    finished BOOL DEFAULT 0
);

CREATE TABLE `Round` (
	id INT PRIMARY KEY AUTO_INCREMENT,
    guess CHAR(4) NOT NULL,
    `time`  DATETIME NOT NULL,
    result CHAR(7) NOT NULL,
	gameId INT NOT NULL,
    FOREIGN KEY fk_Round__Game (gameId)
		REFERENCES Game (id)
);

