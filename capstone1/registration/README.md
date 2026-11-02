# Student Event Registration System

The Student Event Registration System is a Java full-stack web application for
students to browse campus events and reserve a place. It also provides a
simple admin view of event attendance and registration details.

**Project demo:** [Watch the Student Event Registration App demo](https://youtu.be/Ko7NY7sCKuA?feature=shared)

## Problem and solution

Campus event information and sign-ups can be difficult to manage in one place.
This project puts event discovery, registration, capacity checks, and
registration review into one small application.

## Features

- Browse upcoming events, dates, venues, capacity, and seats remaining.
- Search upcoming events by event name or venue.
- View an individual event and register for an open event directly from the
  event listing or its detail page.
- Validate student details on the server and in the browser.
- Prevent duplicate registrations for the same event and prevent overbooking.
- Close registrations for events that have already taken place.
- Show a registration confirmation after a successful sign-up.
- Review event counts and student rosters in the admin pages.
- Return event summaries and a checker-required registration list without
  student email addresses. The registration list includes names, colleges,
  and years of study and is only for fictional academic-demo data.
- Seed four sample future events the first time the database is empty.

The admin pages are not protected by authentication. They are intended for
local learning and demonstration, not deployment with real student data.

## Technology stack

- Java 21
- Spring Boot 4.1.2
- Spring MVC and Thymeleaf
- Spring Data JPA and Hibernate
- Jakarta Bean Validation
- H2 database
- Maven and Maven Wrapper
- JUnit 5, Spring Boot Test, and MockMvc
- HTML and CSS

## Architecture

The application uses a simple layered design:

```text
Browser or API client
        ↓
Web / REST controllers
        ↓
Services (registration and event rules)
        ↓
Spring Data repositories
        ↓
H2 database
```

Thymeleaf renders the browser pages. REST controllers return DTOs rather than
database entities. The registration-list API intentionally returns limited
student details for the academic checker and does not include email addresses.

See [docs/PROJECT_OVERVIEW.md](docs/PROJECT_OVERVIEW.md) for the application
workflow, data model, and design decisions. Actual routes and response
examples are in [docs/API_DOCUMENTATION.md](docs/API_DOCUMENTATION.md).

## Project structure

```text
src/
├── main/
│   ├── java/in/synkoc/registration/
│   │   ├── data/          # First-run sample event data
│   │   ├── dto/           # API and admin response models
│   │   ├── exception/     # Application-specific exceptions
│   │   ├── form/          # Validated registration form
│   │   ├── model/         # Event and Registration entities
│   │   ├── repository/    # Spring Data JPA repositories
│   │   ├── service/       # Application and business rules
│   │   └── web/           # Page controllers and REST API
│   └── resources/
│       ├── static/css/    # Responsive stylesheet
│       └── templates/     # Thymeleaf pages and fragments
└── test/
    ├── java/              # Application, web, and HTTP tests
    └── resources/         # Isolated test database settings
```

## Database

The app uses H2 with Spring Data JPA. The normal application profile stores
data in `data/registration`; the `data/` directory is ignored by Git so local
database files are not published. On first startup, four example events are
added only when the events table is empty. Tests use an independent in-memory
database and do not clear local application data.

`Registration` has a required many-to-one relationship to `Event`. A database
constraint also prevents the same email from registering twice for one event.
The project does not include student accounts or authentication. Confirmation
URLs use sequential registration IDs, so they are not private links.

## Requirements

- Java Development Kit (JDK) 21
- A terminal
- No separately installed Maven is needed; use the included Maven Wrapper.

## Pages and REST endpoints

| Method | Path | Purpose |
|---|---|---|
| GET | `/` | Redirect to the event listing |
| GET | `/events` | Browse or search upcoming events, with direct registration links |
| GET | `/events/{id}` | View event details |
| GET, POST | `/events/{eventId}/register` | View and submit a registration |
| GET | `/registrations/{id}` | View a registration confirmation |
| GET | `/admin/events` | View event counts and capacity |
| GET | `/admin/events/{id}/registrations` | View an event's admin roster |
| GET | `/api/events` | List upcoming event summaries |
| GET | `/api/events/{id}` | Get one event summary |
| GET | `/api/events/{id}/registrations` | List registration IDs, names, colleges, and years of study; excludes email |

Full requests, responses, statuses, and privacy notes are in
[docs/API_DOCUMENTATION.md](docs/API_DOCUMENTATION.md).

## How to run

From the project directory:

```bash
./mvnw -B clean package
./mvnw spring-boot:run
```

The server binds to `127.0.0.1:8082` and is intentionally restricted to the
local machine. Open [http://localhost:8082](http://localhost:8082). The home
page redirects to the event listing. Stop the application with `Ctrl+C`.

This is a local/demo application, not production-ready for real student data.
Admin pages and confirmation pages have no authentication; the H2 database
uses the local `sa` account with an empty password, and confirmation IDs are
sequential. The loopback binding prevents remote network access by default,
but does not protect data from other users or processes on the same machine.
Do not put real personal or confidential information into this instance.
The registration-list API returns student names, colleges, and years of study
to satisfy the academic checker. Use only fictional demo registrations; do not
deploy or expose this endpoint publicly with real student data.

## Tests

Run the full test suite from this directory:

```bash
./mvnw -B clean test
```

The tests cover page rendering and navigation, event search, API responses and
privacy, invalid and unknown API requests, validation, duplicate registration,
event capacity, past events, and admin views.

## Screenshots

No screenshots are included because no real browser captures have been
produced. Do not use mock or generated images as screenshots. To capture the
running application manually:

1. Start the app using the instructions above and open `http://localhost:8082`.
2. Capture the event listing and save it as
   `docs/screenshots/events.png`.
3. Open an event and capture its detail page as
   `docs/screenshots/event-details.png`.
4. Open an available event's registration form and capture it as
   `docs/screenshots/registration.png`.
5. Open `/admin/events` and capture the dashboard as
   `docs/screenshots/admin-dashboard.png`. Use only fictional test records;
   the admin view currently displays student details and has no login.
6. If including the roster page, use only fictional test records and save it
   as `docs/screenshots/admin-registrations.png`.

After capturing images, add Markdown image references here using their paths
under `docs/screenshots/`.

## Future improvements

- Add authenticated, role-based access before using the admin views with real
  student information.
- Add event creation, editing, and deletion if those operations are approved
  by the capstone specification.
- Add richer event descriptions if the event data model is extended.
- Consider confirmation email, registration cancellation, and deployment
  after defining their requirements.

## Learning outcomes

This project demonstrates a Spring MVC request flow, controller/service/
repository separation, JPA entities and relationships, transactional
registration rules, validation, REST DTOs, Thymeleaf templates, and automated
tests.

## Internship context

This is Capstone 1 from the SYNKOC Java Full Stack Internship, kept in its
original location in the `rashi805/synkoc-java` repository at
`capstone1/registration/`. Related Day 1–Day 30 course work and the existing
repository CI workflow remain in place.

## Author

**Rashi Mishra**

- GitHub: [rashi805](https://github.com/rashi805)
- LinkedIn: [Rashi Mishra](https://www.linkedin.com/in/rashi-mishra-7b1b21279/)
