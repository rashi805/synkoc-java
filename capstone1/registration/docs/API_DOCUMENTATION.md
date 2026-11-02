# API and Route Documentation

The REST API returns event summaries and a limited registration roster for
the academic checker. The roster omits email addresses but includes student
names, colleges, and years of study. Use only fictional demo data and do not
expose this endpoint publicly with real student information. Dates are
serialized as ISO-8601 strings (`YYYY-MM-DD`). Unknown event IDs return an
RFC 7807-style problem response.

## REST endpoints

### `GET /api/events`

Returns upcoming events, ordered by date. Each item contains `id`, `title`,
`date`, `venue`, `capacity`, and `seatsLeft`.

- **Path/query parameters:** None.
- **Request body:** None.
- **Success:** `200 OK`, JSON array.
- **Errors:** No event-specific errors; an empty list is returned when there
  are no upcoming events.

Example request:

```http
GET /api/events
Accept: application/json
```

Example response:

```json
[
  {
    "id": 1,
    "title": "Resume Clinic",
    "date": "2026-11-09",
    "venue": "Innovation Hub",
    "capacity": 2,
    "seatsLeft": 2
  }
]
```

### `GET /api/events/{id}`

Returns one event summary by its ID. This endpoint can return an event even if
its date has passed; the list endpoint includes upcoming events only.

- **Path parameter:** `id` — event identifier.
- **Query parameters:** None.
- **Request body:** None.
- **Success:** `200 OK`, the event summary shown above.
- **Errors:** `404 Not Found` when the event does not exist; `400 Bad Request`
  when the ID cannot be converted to a number.

Example request:

```http
GET /api/events/1
Accept: application/json
```

Example response:

```json
{
  "id": 1,
  "title": "Resume Clinic",
  "date": "2026-11-09",
  "venue": "Innovation Hub",
  "capacity": 2,
  "seatsLeft": 2
}
```

Example not-found response (`application/problem+json`):

```json
{
  "type": "about:blank",
  "title": "Event not found",
  "status": 404,
  "detail": "Event 999 was not found."
}
```

The example event values illustrate the actual response fields; sample dates
are illustrative because seeded dates are calculated from the application
date.

### `GET /api/events/{id}/registrations`

Returns registrations associated with one event, ordered by registration ID.
Each item contains exactly `id`, `name`, `college`, and `yearOfStudy`.
Email addresses are not included. An event with no registrations returns an
empty JSON array.

- **Path parameter:** `id` — event identifier.
- **Query parameters:** None.
- **Request body:** None.
- **Success:** `200 OK`, JSON array; `[]` when the event has no registrations.
- **Errors:** `404 Not Found` with `application/problem+json` when the event
  does not exist; `400 Bad Request` when the ID cannot be converted to a
  number.

Example request:

```http
GET /api/events/1/registrations
Accept: application/json
```

Example response:

```json
[
  {
    "id": 12,
    "name": "SYNKOC Check 12345",
    "college": "SYNKOC College",
    "yearOfStudy": 2
  }
]
```

An event with no registrations returns:

```json
[]
```

The SYNKOC checker requires the student name, college, and year of study in
this response. These are personal details even though email is omitted. This
endpoint is intended only for the academic demo with fictional student data;
do not expose it publicly or use it with real student information without
appropriate access control and a privacy review.

## Server-rendered web routes

These are HTML routes, not REST endpoints. Form submissions use standard
browser form fields and a redirect after successful registration.

| Method | Path | Purpose | Request | Response and errors |
| --- | --- | --- | --- | --- |
| `GET` | `/` | Home route. | None. | Redirects to `/events`. |
| `GET` | `/events` | List or search upcoming events with detail and direct registration links for events with seats. | Optional `query` parameter searches title and venue, case-insensitively. | `200 OK`, event listing HTML; an empty search result has a helpful message. |
| `GET` | `/events/{id}` | View event information and registration availability. | Event ID in path. | `200 OK`; `404 Not Found` for an unknown event. |
| `GET` | `/events/{eventId}/register` | View the registration form. | Event ID in path. | `200 OK`; past events redirect to their details page; unknown event is `404`. |
| `POST` | `/events/{eventId}/register` | Submit a registration. | `name`, `email`, `college`, `yearOfStudy` form fields. | Success redirects to `/registrations/{id}`. Invalid fields, duplicate email, full event, or past event re-render the form with an error. Unknown event is `404`. |
| `GET` | `/registrations/{id}` | View a registration confirmation. | Registration ID in path. | `200 OK`; unknown registration is `404`. |
| `GET` | `/admin/events` | View event counts and capacity. | None. | `200 OK`, admin HTML. |
| `GET` | `/admin/events/{id}/registrations` | View an event's registration roster. | Event ID in path. | `200 OK`, admin HTML; unknown event is `404`. |

Registration fields are server-validated: name is required and limited to 60
characters; email must be valid and at most 254 characters; college is
required and at most 100 characters; year of study must be from 1 through 4.
Registration is rejected for past events, duplicate email addresses for an
event, and events at capacity.

## Privacy and administration

The event endpoints do not serialize registration entities. The
registrations endpoint exposes registration IDs, student names, colleges,
and years of study to meet the checker requirement, but omits email
addresses. Use only fictional data for demonstrations. Do not expose the
endpoint publicly with real student information.

The admin HTML roster displays student information and has **no
authentication or authorization**. The registration confirmation route also
uses a sequential registration ID and displays the student's name without
authentication. These are local/demo features only. Do not expose them to
untrusted users or use them with real student data until access control and
non-guessable confirmation access are implemented.
