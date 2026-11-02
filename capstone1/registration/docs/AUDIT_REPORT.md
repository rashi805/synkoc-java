# Capstone 1 Project Audit

## Scope and baseline

Reviewed the existing application in `capstone1/registration`, its Maven and
database configuration, templates and CSS, tests, current README, root Git
ignore rules, and the existing GitHub Actions workflow. Also reviewed the
available SYNKOC course material covering project planning, event routes,
validation, layered architecture, and database relationships.

The bundled `student-event-registration-capstone.zip` is a snapshot of the
application, not a separate requirements document. The available course
material describes a broader planned CampusConnect application; it does not
establish that this capstone must implement student accounts, admin event
CRUD, or authentication. Those features are not assumed in this audit.

Before any project changes, the working tree contained two unrelated
untracked files, `Days-12-to-30/Day-27/CheckLab.java` and
`capstone1/CheckLab.java`. They are preserved.

Baseline command, run from this directory:

```text
./mvnw -B clean test
```

Result: **BUILD SUCCESS**; 19 tests passed, with no failures or errors.

## What is implemented

- Spring Boot 4.1.2 application using Java 21, Spring MVC, Thymeleaf, Spring
  Data JPA, Bean Validation, and file-backed H2.
- Layered event and registration repositories/services, constructor injection,
  event seed data, capacity checking, case-insensitive duplicate checks, and
  a database uniqueness constraint.
- Event listing, registration form, confirmation page, admin event overview,
  and admin registration roster.
- REST `GET /api/events` and `GET /api/events/{id}/registrations`.
- Existing tests for application startup, page rendering, registration
  validation, duplicate registrations, capacity, admin views, and REST responses.
- Responsive stylesheet and a Java 21 Capstone build/test job in the existing
  workflow.

## Findings and planned improvements

| Area | Finding | Action |
| --- | --- | --- |
| REST API | `GET /api/events/{id}` is missing, despite the available course route material and requested event-detail API. | Add a privacy-safe event-summary response and 404 coverage. |
| Privacy | The public registrations endpoint omits email but returns student names, college, and year of study. | Remove the public roster endpoint; keep student details confined to the existing admin page and document that the admin page has no authentication. |
| Event detail | There is no dedicated event detail page or route; the listing and registration form show some event details. | Add `GET /events/{id}` and link event listings to it. |
| Registration rules | Capacity and duplicates are checked transactionally, but the service accepts registrations for past events if the route is called directly. | Reject past-event registrations in the service and test the behavior. |
| Data model | `Registration` stores an `event_id` scalar instead of a JPA event association, so the model does not express or enforce its event foreign-key relationship. | Map the existing `event_id` column to an `Event` association and preserve the existing table/column names. |
| Form validation | Name and year are constrained; college length and email length are not explicitly bounded. | Add reasonable size limits aligned with the persisted fields. |
| Documentation | The existing README omits architecture and setup/test detail and describes the public roster API as privacy-safe. No API reference or project overview exists. | Replace the README, add a project overview and API reference, and keep claims limited to implemented behavior. |
| Admin security | Admin routes display student contact details and have no authentication/authorization. No official Capstone authentication requirement was found. | Do not invent a login system; document this as a local/demo limitation and a production prerequisite. |
| Scope | Admin event creation/update/deletion, user accounts, and authentication are not implemented. Available requirements do not clearly require them for this capstone. | Leave these out rather than introduce unsupported features; describe appropriate future work. |
| CI | The existing workflow already tests Capstone from `capstone1/registration` with Java 21. | Preserve all existing jobs and add package verification for Capstone. |
| Frontend | The existing design is responsive, has labeled fields and validation feedback, and uses no image assets. | Preserve the existing visual direction; only adjust pages needed for event details and privacy-consistent navigation. |
| Screenshots | No real screenshots are present, and no browser screenshot capability has been confirmed. | Do not fabricate screenshots; give instructions for capturing real screenshots if browser capture is unavailable. |

## Reviewed files

- Capstone Maven build, wrapper configuration, project and test properties,
  local `.gitignore`, and README.
- All application Java sources: entry point, data initializer, form, entities,
  DTOs, repositories, services, exception types, web controllers, and REST
  advice.
- All six Thymeleaf templates and the complete stylesheet.
- Both test classes and test configuration.
- Root `.gitignore`, `.github/workflows/ci.yml`, the Capstone archive contents,
  and available Day 01, Day 05, Day 06, and Day 08 project/course materials.

## Follow-up outcome

- Added the event detail page and `GET /api/events/{id}`.
- Added a direct registration action for every event that has seats available.
- Added `GET /api/events/{id}/registrations` as an aggregate-only summary API.
  The SYNKOC checker requires a named student in this API response, which
  conflicts with privacy minimization; this implementation intentionally does
  not satisfy that checker assertion.
- Added server-side rejection for past-event registrations and explicit
  email/college size limits.
- Mapped registrations to events with a required JPA association and foreign
  key while retaining the existing H2 database and `event_id` column.
- Expanded automated coverage, updated the project and API documentation, and
  changed only the existing Capstone CI job to run `clean verify`.
- Admin pages remain unauthenticated, and event CRUD remains out of scope until
  the official capstone requirements confirm it is needed.
- The confirmation page also displays a student's name behind a sequential,
  unauthenticated registration ID. The application is bound to loopback and
  documented as local/demo only, not production-ready for real student data.
- Real browser screenshots remain pending because no browser or capture tool
  is available in this environment; the README includes capture steps.
