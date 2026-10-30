# Day 05 — What Is a Database

- **Day:** 05
- **Lab ID:** JFS-M3-01
- **Status:** Completed
- **Checks passed:** 6/6
- **Completion code:** `JFS-D05-66MJVQ`

## Practical Tasks

### Part A — Database Concepts and Canteen

- **A1 — Explore a Sample Database:** Reviewed the `students`, `courses`, and `enrollments` tables and recorded the lab results: 3 Bengaluru students; course ID 3 is Cloud Basics; 3 Java Full Stack enrollees; Kiran Gowda has no course; and Arjun Rao takes Data Science and Cloud Basics.
- **A2 — Identify the Keys:** Identified `id` as each table's primary key and documented the foreign keys from enrollments to students/courses and payments to students.
- **A3 — Compare SQL and NoSQL:** Compared typical SQL and document-database choices for banking, catalogues, train bookings, smart-meter readings, fixed columns, flexible documents, joins, and records with different fields.
- **A4 — Canteen Database:** Designed `students`, `menu_items`, `orders`, and `order_items`. A student can place multiple orders. Each order can contain multiple menu items, represented by order-item rows.

### Part B — Library Database

Designed `books`, `members`, and `loans`. Each loan connects one book to one member and records `borrow_date`, `due_date`, and an optional `return_date`.

### Part C — Internship Application Database

Designed a relational schema for a college event portal. Users can organize events and register for events. The SQL schema stores `password_hash`, never plaintext passwords.

## Learning Outcomes

By completing Day 05, I learned to:

- Describe databases, tables, rows, and columns.
- Read related sample tables and use joins to answer questions.
- Identify primary keys, foreign keys, and one-to-many or many-to-many relationships.
- Compare relational SQL databases and NoSQL document databases using common scenarios.
- Define practical schemas with data types, uniqueness, checks, and foreign-key constraints.
- Model canteen orders, library loans, and college event registrations.
- Keep account credentials as password hashes rather than plaintext passwords.

The SQL files are MySQL 8.0.16+ schema examples. They document table definitions and relationships; no live database connection is configured by these files.

## Practical Files

- [Database basics and sample lab results](database-basics.md)
- [Keys and relationships](keys-and-relationships.md)
- [SQL vs NoSQL](sql-vs-nosql.md)
- [Canteen database schema](canteen-database.sql)
- [Library database schema](library-database.sql)
- [College event portal schema](internship-app-database.sql)
