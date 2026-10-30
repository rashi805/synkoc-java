-- Order dataset for practicing aggregate queries. A cancelled-only month is included.
CREATE TABLE IF NOT EXISTS orders (
	id INTEGER PRIMARY KEY,
	customer TEXT NOT NULL,
	category TEXT NOT NULL,
	city TEXT NOT NULL,
	order_date TEXT NOT NULL,
	status TEXT NOT NULL CHECK (status IN ('pending', 'delivered', 'cancelled')),
	amount REAL NOT NULL,
	rating REAL
);

DELETE FROM orders;
INSERT INTO orders (id, customer, category, city, order_date, status, amount, rating) VALUES
	(1, 'Asha', 'Software', 'Pune', '2025-01-05', 'delivered', 12000, 5),
	(2, 'Ravi', 'Cloud', 'Mumbai', '2025-01-11', 'pending', 9000, 4),
	(3, 'Asha', 'Software', 'Pune', '2025-02-02', 'delivered', 15000, 4),
	(4, 'Meera', 'Data', 'Delhi', '2025-02-18', 'cancelled', 8000, NULL),
	(5, 'Ravi', 'Cloud', 'Mumbai', '2025-03-01', 'delivered', 11000, 5),
	(6, 'Asha', 'Data', 'Pune', '2025-03-12', 'pending', 7000, 3),
	(7, 'Meera', 'Software', 'Delhi', '2025-04-04', 'delivered', 22000, 5),
	(8, 'Ravi', 'Software', 'Mumbai', '2025-04-20', 'cancelled', 6000, 2),
	(9, 'Asha', 'Cloud', 'Pune', '2025-05-03', 'delivered', 10000, 4),
	(10, 'Meera', 'Data', 'Delhi', '2025-05-14', 'pending', 9500, NULL),
	(11, 'Nikhil', 'Software', 'Mumbai', '2025-06-02', 'cancelled', 4000, 3),
	(12, 'Nikhil', 'Cloud', 'Mumbai', '2025-06-15', 'delivered', 13000, 4);

-- COUNT rows versus COUNT(non-null ratings).
SELECT COUNT(*) AS pending_orders FROM orders WHERE status = 'pending';
SELECT COUNT(rating) AS rated_orders FROM orders;

SELECT SUM(amount) AS delivered_revenue FROM orders WHERE status = 'delivered';
SELECT ROUND(AVG(amount), 2) AS average_order_value FROM orders;

SELECT category, COUNT(*) AS order_count FROM orders GROUP BY category ORDER BY category;

SELECT city, SUM(amount) AS delivered_revenue
FROM orders WHERE status = 'delivered'
GROUP BY city ORDER BY delivered_revenue DESC, city ASC;

SELECT strftime('%Y-%m', order_date) AS order_month, COUNT(*) AS order_count
FROM orders GROUP BY strftime('%Y-%m', order_date) ORDER BY order_month ASC;

SELECT category, COUNT(*) AS order_count
FROM orders GROUP BY category HAVING COUNT(*) >= 3;

SELECT customer, SUM(amount) AS delivered_total
FROM orders WHERE status = 'delivered'
GROUP BY customer HAVING SUM(amount) > 20000;

SELECT city, ROUND(AVG(rating), 1) AS average_rating, COUNT(rating) AS rating_count
FROM orders WHERE rating IS NOT NULL
GROUP BY city HAVING COUNT(rating) >= 2;

SELECT city,
	SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) AS pending_count,
	SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled_count
FROM orders GROUP BY city ORDER BY city;

SELECT category,
	ROUND(100.0 * SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) / COUNT(*), 1)
		AS cancellation_percent
FROM orders GROUP BY category ORDER BY category;

-- Include months with zero delivered orders by deriving all months from the table first.
WITH months AS (
	SELECT DISTINCT strftime('%Y-%m', order_date) AS order_month FROM orders
)
SELECT months.order_month,
	COALESCE(SUM(CASE WHEN orders.status = 'delivered' THEN orders.amount ELSE 0 END), 0)
		AS delivered_revenue
FROM months
LEFT JOIN orders ON strftime('%Y-%m', orders.order_date) = months.order_month
GROUP BY months.order_month
ORDER BY months.order_month;

SELECT customer,
	COUNT(*) AS total_orders,
	SUM(CASE WHEN status = 'delivered' THEN 1 ELSE 0 END) AS delivered_count,
	SUM(CASE WHEN status = 'delivered' THEN amount ELSE 0 END) AS delivered_spending
FROM orders
GROUP BY customer
HAVING COUNT(*) >= 2
ORDER BY delivered_spending DESC, customer ASC;

-- Internship application reporting data: student registrations for the Day 09 course API.
CREATE TABLE IF NOT EXISTS internship_registrations (
	id INTEGER PRIMARY KEY,
	course_title TEXT NOT NULL,
	student_name TEXT NOT NULL,
	college TEXT NOT NULL,
	year_of_study INTEGER NOT NULL,
	status TEXT NOT NULL,
	fee_paid REAL NOT NULL
);

DELETE FROM internship_registrations;
INSERT INTO internship_registrations
	(id, course_title, student_name, college, year_of_study, status, fee_paid) VALUES
	(1, 'Java Full Stack', 'Asha', 'North College', 2, 'confirmed', 14999),
	(2, 'Data Science', 'Ravi', 'City College', 3, 'pending', 12999),
	(3, 'Java Full Stack', 'Meera', 'North College', 1, 'confirmed', 14999),
	(4, 'Cloud Basics', 'Nikhil', 'City College', 4, 'cancelled', 0),
	(5, 'SQL Foundations', 'Asha', 'North College', 2, 'confirmed', 6999),
	(6, 'Web Design', 'Ira', 'Arts College', 1, 'pending', 7999),
	(7, 'Cloud Basics', 'Ravi', 'City College', 3, 'confirmed', 4999),
	(8, 'Data Science', 'Meera', 'North College', 1, 'confirmed', 12999);

-- Whole-table summary; no GROUP BY is needed for an overall report.
SELECT COUNT(*) AS registration_count,
	SUM(fee_paid) AS total_fees_paid,
	ROUND(AVG(fee_paid), 2) AS average_fee
FROM internship_registrations;

-- Multiple groups show counts and fee totals by course.
SELECT course_title, COUNT(*) AS registrations, SUM(fee_paid) AS fees_paid
FROM internship_registrations
GROUP BY course_title
ORDER BY course_title;

SELECT college, COUNT(*) AS registrations, SUM(fee_paid) AS fees_paid
FROM internship_registrations
GROUP BY college
HAVING COUNT(*) >= 2
ORDER BY fees_paid DESC, college;
