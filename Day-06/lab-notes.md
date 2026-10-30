# Day 06 — SQL Basics (CRUD)

- **Internship module:** JFS-M3-02
- **Lab title:** SQL Basics (CRUD)
- **Database:** SQLite
- **Status:** Completed
- **Checks passed:** 6/6
- **Completion code:** `JFS-D06-5WMY6R`

## What I Practised

The lab created a `courses` table, inserted four courses, queried student records, updated and deleted course data, created and queried fictional contacts, and designed an `events` table for the College Event Portal application.

The student queries in `03-query-students.sql` assume an existing `students` table with `name`, `city`, and `fee` columns. The original eight student records were not supplied, so this documentation does not claim specific results for those queries.

## Beginner's SQL Glossary

1. **SQL:** Structured Query Language, used to define, read, and change data in relational databases. For example, `SELECT * FROM courses;` reads course rows.
2. **SQLite:** A lightweight relational database engine that stores a database in a file (or in memory) and does not require a separate database server. SQLite uses SQL.
3. **Database table:** A named structure for storing similar records, such as `courses` or `contacts`.
4. **Row:** One complete record in a table. One course entry is one row in `courses`.
5. **Column:** One named property in a table, such as `title`, `fee`, or `duration_weeks`.
6. **CRUD:** The common data operations Create, Read, Update, and Delete. SQL statements commonly used for them are `INSERT`, `SELECT`, `UPDATE`, and `DELETE`.
7. **CREATE:** Defines a database object. `CREATE TABLE courses (...)` makes the course table and its columns.
8. **INSERT:** Adds new rows. The course exercise inserts four rows without specifying IDs; SQLite assigns integer primary-key values.
9. **SELECT:** Reads rows and columns. For example, `SELECT name, city FROM students;` returns those two columns.
10. **UPDATE:** Changes existing values. `UPDATE courses SET fee = 5999 WHERE title = 'Cloud Basics';` changes the matching course fee.
11. **DELETE:** Removes matching rows. `DELETE FROM courses WHERE id = 5;` removes a row with that ID if it exists. Always check the target first.
12. **WHERE:** Filters which rows a query returns or changes. Without a `WHERE` condition, an `UPDATE` or `DELETE` applies to every row in the table.
13. **ORDER BY:** Sorts the returned rows, for example `ORDER BY name ASC`.
14. **ASC and DESC:** `ASC` sorts ascending (A–Z or low to high); `DESC` sorts descending (Z–A or high to low). Ascending is the usual default, but writing `ASC` makes the intent clear.
15. **LIMIT:** Restricts the number of returned rows. `LIMIT 3` returns at most three rows, used with descending fees to show the highest three.
16. **COUNT:** An aggregate function that counts rows. `COUNT(*)` counts rows matching the query's filters.
17. **IS NULL:** Tests whether a value is missing/unknown. Use `city IS NULL` to count students without a city; `= NULL` is not the correct test.
18. **NOT NULL:** A column constraint that requires a value. In `title TEXT NOT NULL`, a course title cannot be omitted or set to SQL `NULL`.
19. **UNIQUE:** A constraint that prevents duplicate values in a column or combination of columns. `email TEXT UNIQUE` prevents two contacts from having the same non-NULL email.
20. **PRIMARY KEY:** A column or group of columns that uniquely identifies every row. In SQLite, `INTEGER PRIMARY KEY` aliases the rowid and is generated when omitted on insert.
21. **CHECK constraint:** A rule that must be true for values in a row. `CHECK (fee >= 0)` rejects negative course fees.

## Practical Files

- [Create courses table](01-create-table.sql)
- [Insert and display courses](02-insert-courses.sql)
- [Query students](03-query-students.sql)
- [Update and delete courses](04-update-delete.sql)
- [Contacts challenge](05-contacts-challenge.sql)
- [College Event Portal events practical](06-internship-app-events.sql)
