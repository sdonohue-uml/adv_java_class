
/* delete tables if they exist already - ensuring a clean db*/
DROP TABLE IF EXISTS stocks.user_stocks CASCADE;
DROP TABLE IF EXISTS stocks.person CASCADE;
DROP TABLE IF EXISTS stocks.quotes CASCADE;

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

CREATE TABLE stocks.quotes
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    symbol VARCHAR(4) NOT NULL,
    time DATETIME NOT NULL,
    price DECIMAL(10,0) NOT NULL
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

INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (1, "GOOG", "2004-08-19 00:00:01", 85);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (2, "GOOG", "2015-02-03 00:00:01", 527);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (3, "APPL", "2000-01-01 00:00:01", 118);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (4, "AMZN", "2015-02-03 00:00:01", 363);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (5, "AMZN", "2020-01-02 00:00:01", 100);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (6, "AMZN", "2020-01-03 00:00:01", 110);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (7, "AMZN", "2020-01-04 00:00:01", 120);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (8, "AMZN", "2020-01-05 00:00:01", 130);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (9, "AMZN", "2020-01-06 00:00:01", 140);
INSERT INTO stocks.quotes (id, symbol, time, price) VALUES (10, "AMZN", "2020-01-04 12:00:01", 125);

