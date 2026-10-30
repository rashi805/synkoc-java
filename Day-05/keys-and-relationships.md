# Keys and Relationships

A **key** is a column, or set of columns, used to identify or connect records.

- A **primary key (PK)** uniquely identifies each row in its own table. It must be unique and not null.
- A **foreign key (FK)** references a key in another table. It helps ensure that a relationship points to an existing record.

## Lab Tables and Keys

| Table | Primary key | Foreign key(s) |
| --- | --- | --- |
| `students` | `id` | — |
| `courses` | `id` | — |
| `enrollments` | `id` | `student_id` references `students.id`; `course_id` references `courses.id` |
| `payments` | `id` | `student_id` references `students.id` |

In SQL, the core declarations can be written as:

```sql
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE courses (
    id INT PRIMARY KEY,
    title VARCHAR(150) NOT NULL
);

CREATE TABLE enrollments (
    id INT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE payments (
    id INT PRIMARY KEY,
    student_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

## How the Tables Connect

- **Student to enrollment:** One student can have many enrollment rows. Each enrollment belongs to one student. `enrollments.student_id` references `students.id`.
- **Course to enrollment:** One course can appear in many enrollment rows. Each enrollment refers to one course. `enrollments.course_id` references `courses.id`.
- **Students to courses:** Together, `enrollments` represents a many-to-many relationship: a student can take multiple courses, and each course can have multiple students.
- **Student to payment:** One student can have multiple payment rows. Each payment belongs to one student through `payments.student_id`.

Foreign keys help prevent orphaned records—for example, an enrollment referring to a student ID that does not exist. A database may also define deletion/update rules; those rules should match the application's data-retention requirements.
