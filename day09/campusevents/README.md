# Campus Events Courses API

This Spring Boot application exposes a small, database-backed API for browsing and creating campus learning courses.

## Features

- List all courses in stable ID order and retrieve a course by ID.
- Create a course with basic request validation and a `201 Created` response.
- Save course data in a file-based H2 database so it persists between restarts.
- Seed the original three sample courses only when the database is empty.

## Requirements

- Java 21
- Maven (or the included Maven Wrapper)
- Spring Boot 4.1.2

## Run

From this directory, run `./mvnw spring-boot:run`, then open `http://localhost:8080/api/courses`.

| Method | Path | Purpose |
|---|---|---|
| GET | `/` | Welcome message |
| GET | `/api/courses` | List stored courses |
| GET | `/api/courses/{id}` | Find one course or return 404 |
| POST | `/api/courses` | Validate and create a course |

Run the application tests with `./mvnw test`. The production application uses a file database under `data/`; tests use an isolated in-memory H2 database.

**Author:** Rashi Mishra
