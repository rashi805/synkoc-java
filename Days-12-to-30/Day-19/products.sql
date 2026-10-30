-- Self-contained product catalogue used to practise filtering, ordering, and pagination.
CREATE TABLE IF NOT EXISTS products (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	category TEXT NOT NULL,
	price INTEGER NOT NULL,
	in_stock INTEGER NOT NULL CHECK (in_stock IN (0, 1)),
	rating REAL
);

DELETE FROM products;
INSERT INTO products (id, name, category, price, in_stock, rating) VALUES
	(1, 'Swift Phone', 'Mobiles', 18999, 1, 4.4),
	(2, 'Pro Phone Max', 'Mobiles', 24999, 1, 4.7),
	(3, 'Air Buds Pro', 'Audio', 3999, 1, 4.5),
	(4, 'Studio Headphones', 'Audio', 6999, 0, 4.6),
	(5, 'Fit Watch', 'Wearables', 4999, 1, 4.1),
	(6, 'Smart Band', 'Wearables', 1999, 1, NULL),
	(7, 'Creator Laptop', 'Laptops', 72999, 1, 4.8),
	(8, 'Student Laptop', 'Laptops', 42999, 0, 4.2),
	(9, 'Pro Case', 'Accessories', 899, 1, 4.0),
	(10, 'Air Charger', 'Accessories', 1499, 1, 4.3),
	(11, 'Sound Speaker', 'Audio', 2999, 1, 4.2),
	(12, 'Slim Phone', 'Mobiles', 15999, 0, NULL);

-- Mobiles below ₹20,000.
SELECT id, name, price FROM products
WHERE category = 'Mobiles' AND price < 20000;

-- Products that are out of stock.
SELECT id, name, category FROM products WHERE in_stock = 0;

-- Audio or Wearables priced between ₹1,000 and ₹5,000, inclusive.
SELECT id, name, category, price FROM products
WHERE category IN ('Audio', 'Wearables') AND price BETWEEN 1000 AND 5000;

-- Most expensive laptops first.
SELECT id, name, price FROM products WHERE category = 'Laptops' ORDER BY price DESC;

-- Audio and Mobiles grouped alphabetically, then price descending within each category.
SELECT id, name, category, price FROM products
WHERE category IN ('Audio', 'Mobiles') ORDER BY category ASC, price DESC;

-- NULL ratings sort last on SQLite; name breaks rating ties.
SELECT id, name, rating FROM products ORDER BY rating DESC NULLS LAST, name ASC;

-- Three most expensive products.
SELECT id, name, price FROM products ORDER BY price DESC, name ASC LIMIT 3;

-- Page two: four products per page, ordered by id (zero-based offset four).
SELECT id, name FROM products ORDER BY id LIMIT 4 OFFSET 4;

-- Two cheapest Accessories that are in stock.
SELECT id, name, price FROM products
WHERE category = 'Accessories' AND in_stock = 1 ORDER BY price ASC, name ASC LIMIT 2;

-- Names containing "pro", case-insensitive.
SELECT id, name FROM products WHERE name LIKE '%pro%' COLLATE NOCASE;

-- Names beginning with "s", case-insensitive.
SELECT id, name FROM products WHERE name LIKE 's%' COLLATE NOCASE;

-- Count names containing "air".
SELECT COUNT(*) AS names_containing_air FROM products WHERE name LIKE '%air%' COLLATE NOCASE;

-- Product search: keyword "o", at most ₹20,000, in stock, first five matches.
SELECT id, name, category, price FROM products
WHERE name LIKE '%o%' COLLATE NOCASE AND price <= 20000 AND in_stock = 1
ORDER BY price ASC, name ASC
LIMIT 5;

-- Internship application data: the Day 09 project is the Campus Events/Courses API.
CREATE TABLE IF NOT EXISTS internship_courses (
	id INTEGER PRIMARY KEY,
	title TEXT NOT NULL,
	category TEXT NOT NULL,
	fee INTEGER NOT NULL,
	weeks INTEGER NOT NULL,
	level TEXT NOT NULL
);

DELETE FROM internship_courses;
INSERT INTO internship_courses (id, title, category, fee, weeks, level) VALUES
	(1, 'Java Full Stack', 'Software', 14999, 6, 'beginner'),
	(2, 'Data Science', 'Data', 12999, 6, 'intermediate'),
	(3, 'Cloud Basics', 'Cloud', 4999, 2, 'beginner'),
	(4, 'Mobile App Basics', 'Software', 8999, 4, 'beginner'),
	(5, 'SQL Foundations', 'Data', 6999, 3, 'beginner'),
	(6, 'Cloud Deployment', 'Cloud', 11999, 5, 'intermediate'),
	(7, 'Web Design', 'Design', 7999, 4, 'beginner'),
	(8, 'Advanced Java', 'Software', 19999, 8, 'advanced');

SELECT id, title, fee FROM internship_courses
WHERE fee < 15000 AND weeks <= 6;

SELECT id, title, category FROM internship_courses
WHERE level = 'beginner' OR category = 'Cloud';

SELECT id, title, fee FROM internship_courses
WHERE category IN ('Software', 'Data');

SELECT id, title, fee FROM internship_courses
WHERE fee BETWEEN 7000 AND 15000;

SELECT id, title FROM internship_courses WHERE title LIKE '%Java%';

SELECT id, title, fee FROM internship_courses ORDER BY fee DESC LIMIT 5;
