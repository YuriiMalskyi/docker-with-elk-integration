DROP DATABASE IF EXISTS book_db;
CREATE DATABASE book_db;
USE book_db;

CREATE TABLE books (
    book_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20),
    publication_date VARCHAR(20),
    author_id BIGINT NOT NULL,
    genre VARCHAR(100),
    description TEXT
);

CREATE TABLE author_info (
    author_id BIGINT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100)
);

INSERT INTO books (title, isbn, publication_date, author_id, genre, description) VALUES
('1984', '978-0451524935', '1949-06-08', 1, 'Dystopian', 'A dystopian novel about totalitarianism.'),
('SPQR: A History of Ancient Rome', '978-0871404237', '2015-10-13', 2, 'History', 'A comprehensive history of ancient Rome.'),
('The Histories', '978-0140415104', '-450-01-01', 3, 'History', 'An account of the Greco-Persian Wars.'),
('The Iliad', '978-0140275360', '-750-01-01', 4, 'Epic', 'An ancient Greek epic poem.'),
('The Odyssey', '978-0140268867', '-750-01-01', 4, 'Epic', 'An ancient Greek epic poem.'),
('Principia Mathematica', null, '1910-01-01', 5, 'Philosophy', 'A three-volume work on the foundations of mathematics.');

INSERT INTO author_info (author_id, first_name, last_name) VALUES
(1, 'George', 'Orwell'),
(2, 'Mary', 'Beard'),
(3, 'Herodotus', NULL),
(4, 'Homer', NULL),
(5, 'Bertrand', 'Russell');