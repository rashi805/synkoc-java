-- Task 2: Insert sample courses. SQLite generates each INTEGER PRIMARY KEY.
INSERT INTO courses (title, fee, duration_weeks)
VALUES
    ('Java Full Stack', 14999, 6),
    ('Data Science', 12999, 6),
    ('Cloud Basics', 4999, 2),
    ('UI Design', 6999, 4);

-- Display the inserted courses.
SELECT * FROM courses;
