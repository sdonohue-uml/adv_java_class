
/* delete tables if they exist already - ensuring a clean db*/
DROP TABLE IF EXISTS stocks.user_stocks CASCADE;
DROP TABLE IF EXISTS stocks.person CASCADE;

/** creates a table to store a list of people */
CREATE TABLE stocks.person
(
  ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(256) NOT NULL,
  last_name VARCHAR(256) NOT NULL,
  birth_date DATETIME NOT NULL
);

/** A list of people and their stock interests */
CREATE TABLE stocks.user_stocks
(
    ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    person_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (ID),
    stock VARCHAR(256) NOT NULL
);

/** now add some sample data */

INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Drew', 'Hope', '1999/10/10');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Lang', 'Heckman', '1959/11/11');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Lucy', 'Jones', '2010/1/1');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Stew', 'Hammer', '1990/3/28');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Dan', 'Lane', '1986/4/18');

INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (1, 1, "GOOG");
INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (2, 1, "AMZN");
INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (3, 2, "AMZN");
INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (4, 3, "AMZN");
INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (5, 3, "IBM");
INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (6, 3, "FORD");
INSERT INTO stocks.user_stocks (ID, person_id, stock) VALUES (7, 4, "NFLX");

