-- Task 4: Change the fee for Cloud Basics.
UPDATE courses
SET fee = 5999
WHERE title = 'Cloud Basics';

-- Delete the course whose ID is 5, if such a row exists.
-- Check the target row before running DELETE against real data.
DELETE FROM courses
WHERE id = 5;

-- Display the remaining courses.
SELECT * FROM courses;
