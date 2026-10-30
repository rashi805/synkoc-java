# Student Event Registration App

The Student Event Registration App helps students discover upcoming campus events and reserve a place, while administrators can review event capacity and registration details in one connected application.

## Features

- Browse upcoming events ordered by date, see remaining seats, and open a registration form.
- Validate student details, prevent duplicate registrations, and enforce event capacity safely.
- View a confirmation page after registering.
- Review every event and its student roster in the admin dashboard.
- Use privacy-conscious REST endpoints that do not return student email addresses.
- Start with four sample future events, including a two-seat Resume Clinic.

## Technology

Java 21, Spring Boot 4.1.2, Maven, Spring Web MVC, Thymeleaf, Spring Data JPA, Bean Validation, H2, and JUnit.

## Run the application

Open a terminal in this project folder and run:

```bash
./mvnw spring-boot:run
```

Then visit `http://localhost:8082`. The normal application database is stored in `data/registration`; it is not the in-memory database used by tests.

## Run tests

```bash
./mvnw test
```

Tests use a separate in-memory H2 database, so they do not reset or replace your saved application data.

## Pages and API

| Method | Path | Purpose |
|---|---|---|
| GET | `/` | Redirect to the event listing |
| GET | `/events` | Browse upcoming events |
| GET, POST | `/events/{id}/register` | View and submit a registration |
| GET | `/registrations/{id}` | View registration confirmation |
| GET | `/admin/events` | Review all events and registration counts |
| GET | `/admin/events/{id}/registrations` | Review an event's registrations |
| GET | `/api/events` | Get upcoming event summaries and seats left |
| GET | `/api/events/{id}/registrations` | Get privacy-safe registration summaries |

The public registration API intentionally omits email addresses. The `/admin` pages are provided for this training project; authentication and authorization must be added before deploying an admin dashboard in a real production environment.

**Author:** Rashi Mishra  
**Demo video:** [Watch the Student Event Registration App demo](https://youtu.be/Ko7NY7sCKuA?feature=shared)

## 2–3 minute demo outline

1. Browse the upcoming events and point out the two seats available for Resume Clinic.
2. Submit the registration form once, then show the confirmation number and saved name.
3. Submit the same email again to show the duplicate-registration message.
4. Register a second email and show the event becomes full; demonstrate that another registration is rejected.
5. Open `/admin/events` and the event's registration list to show the admin view.
6. Open `/api/events` and `/api/events/{id}/registrations`; point out that the public response contains no email field.
7. Finish by running `./mvnw test` and showing the successful test summary.
