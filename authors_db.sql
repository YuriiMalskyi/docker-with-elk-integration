DROP DATABASE IF EXISTS author_db;
CREATE DATABASE author_db;
USE author_db;

CREATE TABLE authors (
    author_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    biography TEXT,
    date_of_birth VARCHAR(20),
    nationality VARCHAR(100)
);

CREATE TABLE book_info (
    book_id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    genre VARCHAR(100),
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
);

INSERT INTO authors (first_name, last_name, biography, date_of_birth, nationality) VALUES
('George', 'Orwell', 'English novelist and essayist.', '1903-06-25', 'British'),
('Mary', 'Beard', 'British classicist and historian.', '1955-01-01', 'British'),
('Herodotus', NULL, 'Ancient Greek historian.', '-484-01-01', 'Greek'),
('Homer', NULL, 'Ancient Greek poet.', '-800-01-01', 'Greek'),
('Bertrand', 'Russell', 'British philosopher, logician, mathematician.', '1872-05-18', 'British');

INSERT INTO book_info (book_id, title, genre, author_id) VALUES
(1, '1984', 'Dystopian', 1),
(2, 'SPQR: A History of Ancient Rome', 'History', 2),
(3, 'The Histories', 'History', 3),
(4, 'The Iliad', 'Epic', 4),
(5, 'The Odyssey', 'Epic', 4),
(6, 'Principia Mathematica', 'Philosophy', 5);