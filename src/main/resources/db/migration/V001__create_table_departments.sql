CREATE TABLE departments
(
    department_id NUMERIC(4) PRIMARY KEY NOT NULL,
    name VARCHAR(40) NOT NULL CHECK (LENGTH(name) > 1),
    location VARCHAR(30) NOT NULL CHECK (LENGTH(name) > 1)
);

CREATE SEQUENCE next_department_id
    START WITH 3;