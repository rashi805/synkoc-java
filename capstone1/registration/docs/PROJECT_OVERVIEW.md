# Project Overview

## Objective

Build a small, working Java full-stack application where students can find
campus events and register, while an admin view can review event capacity and
registrations.

## Problem statement

Event information and registration can be scattered across separate channels.
Students need a clear way to see event dates and availability, and organizers
need a central place to review registrations.

## Proposed solution

The application serves event pages using Spring MVC and Thymeleaf, applies
registration rules in a service layer, and persists events and registrations
with Spring Data JPA and H2. It also provides a read-only REST API for event
summaries.

The existing SYNKOC project planning document proposes a broader CampusConnect
system, including accounts and event administration. Those planned features
are not all part of this capstone's current implementation. No separate
official Capstone requirements document was present in the repository or
bundled archive, so this project does not claim that authentication or event
CRUD is implemented or required.

## System architecture

```text
Browser pages / REST client
            ↓
   Spring MVC controllers
            ↓
  Event and registration services
            ↓
    Spring Data repositories
            ↓
             H2
```

- **Controllers** map page and REST routes, validate form submissions, and
  select a page or response.
- **Services** apply business rules, including date, duplicate-registration,
  and capacity checks.
- **Repositories** read and write JPA entities.
- **Entities** represent events and registrations. A registration references
  one event and has a unique event/email pair.
- **DTOs** limit public event API responses to event information.
- **Thymeleaf and CSS** provide the pages, validation feedback, and responsive
  layout.

## Application workflow

1. A visitor opens the event listing and sees upcoming events sorted by date,
   or searches by event title or venue.
2. The visitor opens an event detail page to review its date, venue, capacity,
   and remaining seats.
3. For an event that has not passed and still has room, the visitor submits
   their name, email, college, and year of study.
4. The service validates the date, normalizes the email, checks for a
   duplicate and available capacity, then saves the registration.
5. The visitor sees a confirmation page. The admin pages can show event totals
   and registration details.

## Database design

| Entity | Important fields | Relationship |
| --- | --- | --- |
| `Event` | ID, title, venue, date, capacity | One event can have many registrations. |
| `Registration` | ID, event ID, name, email, college, year of study | Each registration belongs to one event. The event/email pair is unique. |

The normal application uses an H2 file database in `data/registration`.
Automated tests use a separate in-memory H2 database. The sample data
initializer adds four future events only when the event table is empty.

There are no student account, admin account, category, event description, or
registration status tables in the current model.

## Technology decisions

- **Java 21 and Spring Boot** follow the repository's existing Maven
  configuration and CI setup.
- **Spring MVC and Thymeleaf** keep the user interface server-rendered, using
  the technology already present in the project.
- **Spring Data JPA and H2** provide persistence without introducing another
  database platform.
- **DTOs for REST responses** avoid exposing entity data and student records
  through the event API.
- **Maven Wrapper** makes the project buildable without a separate Maven
  installation.

## Challenges addressed

- Checking duplicate registrations and capacity as part of one transactional
  operation, with an event row lock to reduce overbooking under concurrent
  requests.
- Enforcing uniqueness in both application logic and the database.
- Separating student registration records from public event response data.
- Keeping the test database separate from the local file-backed application
  database.

## Future improvements

- Add administrator authentication and authorization before real student data
  is handled.
- Confirm whether the capstone requires event create/update/delete operations
  before implementing them.
- Add richer event descriptions and further accessibility review as
  requirements are agreed.
- Add registration cancellation, email notifications, and deployment after
  deciding their behavior and privacy requirements.
