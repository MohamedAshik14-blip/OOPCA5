
CREATE DATABASE IF NOT EXISTS OOPCA5;

USE OOPCA5;


CREATE TABLE IF NOT EXISTS Student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    studentNumber INT,
    gpa FLOAT,
    name VARCHAR(255)
);


INSERT INTO Student (studentNumber, gpa, name) VALUES
(1001, 3.5, 'John Smith'),
(1002, 3.2, 'Alice Johnson'),
(1003, 3.9, 'Michael Davis'),
(1004, 3.7, 'Emily Wilson'),
(1005, 3.8, 'David Brown'),
(1006, 3.6, 'Sarah Taylor'),
(1007, 3.4, 'James Martinez'),
(1008, 3.1, 'Olivia Anderson'),
(1009, 3.3, 'Daniel Garcia'),
(1010, 3.0, 'Sophia Hernandez');







