# Database Basics

A **database** is an organized collection of information that can be stored, searched, changed, and managed. A database management system (DBMS), such as MySQL, provides the tools and rules for working with that information.

## Tables, Rows, and Columns

A relational database organizes related information into tables:

- A **table** stores one kind of entity, such as students or courses.
- A **row** is one record in a table, such as one student.
- A **column** is an attribute shared by the table's records, such as a student's name or city.

For example, a `students` table might have the columns `id`, `name`, and `city`. Each student is represented by a separate row. An ID can identify one row even when two students have the same name.

## Sample Students, Courses, and Enrollments Database

The lab's sample database has three related tables:

| Table | Example columns | Purpose |
| --- | --- | --- |
| `students` | `id`, `name`, `city` | Stores each student once. |
| `courses` | `id`, `title` | Stores each course once. |
| `enrollments` | `id`, `student_id`, `course_id` | Records which student is enrolled in which course. |

`students.id` and `courses.id` identify their respective records. The `student_id` and `course_id` columns in `enrollments` refer to those IDs. This junction table allows a student to take multiple courses and a course to have multiple students.

### A1 — Explore a Sample Database

These are the results observed in the lab:

| Question | Result |
| --- | --- |
| How many students are from Bengaluru? | 3 |
| Which course has ID 3? | Cloud Basics |
| How many students are enrolled in Java Full Stack? | 3 |
| Which student has no course enrollment? | Kiran Gowda |
| Which courses is Arjun Rao enrolled in? | Data Science and Cloud Basics |

The corresponding SQL uses joins to look up related records:

```sql
SELECT COUNT(*) AS bengaluru_students
FROM students
WHERE city = 'Bengaluru';

SELECT title
FROM courses
WHERE id = 3;

SELECT COUNT(DISTINCT e.student_id) AS java_full_stack_students
FROM enrollments AS e
JOIN courses AS c ON c.id = e.course_id
WHERE c.title = 'Java Full Stack';

SELECT s.name
FROM students AS s
LEFT JOIN enrollments AS e ON e.student_id = s.id
WHERE e.id IS NULL;

SELECT c.title
FROM students AS s
JOIN enrollments AS e ON e.student_id = s.id
JOIN courses AS c ON c.id = e.course_id
WHERE s.name = 'Arjun Rao';
```

The query results above document the supplied lab results; the schema and queries do not claim that a database server is configured in this repository.

See [keys-and-relationships.md](keys-and-relationships.md) for how the IDs connect the tables.
