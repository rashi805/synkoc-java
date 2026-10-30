-- Task 3A: Find Bengaluru students and sort their names alphabetically.
SELECT name, city
FROM students
WHERE city = 'Bengaluru'
ORDER BY name ASC;

-- Task 3B: Show the three students paying the highest fees.
SELECT name, fee
FROM students
ORDER BY fee DESC
LIMIT 3;

-- Task 3C: Count students whose city is missing (NULL).
SELECT COUNT(*)
FROM students
WHERE city IS NULL;
