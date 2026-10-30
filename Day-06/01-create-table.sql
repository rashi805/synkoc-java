-- Task 1: Create a table for courses.
CREATE TABLE courses (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    fee REAL CHECK (fee >= 0),
    duration_weeks INTEGER
);
