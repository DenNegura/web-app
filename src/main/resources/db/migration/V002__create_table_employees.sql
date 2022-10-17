CREATE TABLE employees
(
    employee_id NUMERIC(4) NOT NULL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL CHECK (LENGTH(first_name) > 2),
    last_name VARCHAR(25) NOT NULL CHECK (LENGTH(last_name) > 2),
    department_id NUMERIC(4) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE CHECK (LENGTH(email) > 10),
    phone_number VARCHAR(15) NOT NULL UNIQUE CHECK (LENGTH(phone_number) > 6),
    salary NUMERIC(7,2) NOT NULL CHECK (salary > 1.0),

    CONSTRAINT department_pk FOREIGN KEY (department_id) REFERENCES departments (department_id)
);

CREATE SEQUENCE next_employee_id
    START WITH 104;