SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS entry (
    id int PRIMARY KEY auto_increment,
    firstName VARCHAR,
    lastName VARCHAR,
    addressId INTEGER,
    phone VARCHAR
);

CREATE TABLE IF NOT EXISTS address (
    id int PRIMARY KEY auto_increment,
    street VARCHAR,
    city VARCHAR,
    state VARCHAR,
    country VARCHAR,
    zip VARCHAR
);