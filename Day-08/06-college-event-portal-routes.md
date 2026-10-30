# College Event Portal Routes

The Day 05 internship application is a **College Event Portal** with the main tables `users`, `events`, and `registrations`. These REST-style routes map HTTP requests to portal operations.

| Method | Path | Purpose | Related table(s) |
| --- | --- | --- | --- |
| `GET` | `/events` | Display all college events. | `events` |
| `GET` | `/events/{id}` | Display details of one event. | `events` |
| `POST` | `/users` | Create/register a new user account. | `users` |
| `POST` | `/auth/login` | Authenticate an existing user. | `users` |
| `GET` | `/registrations` | Display registrations for the authenticated user. | `registrations`, `events` |
| `POST` | `/events/{id}/registrations` | Register the authenticated user for the specified event. | `registrations`, `users`, `events` |
| `DELETE` | `/registrations/{id}` | Cancel a registration if the user is authorized to do so. | `registrations` |
| `GET` | `/users/{id}` | Display a user's profile, subject to access rules. | `users` |

## How Routes Relate to the Tables

- Event list and detail routes read event rows from `events`.
- User registration creates a row in `users`. Login checks credentials associated with a user; passwords should be stored as hashes, not plaintext.
- A new event registration creates a row in `registrations` that references one user and one event. The database relationships help ensure both records exist.
- The registrations route returns the current user's registration rows, commonly joined with `events` to show event details. The identity of the current user should come from authenticated server-side context, not an untrusted user ID supplied by the client.
- Cancelling a registration deletes or marks the relevant registration according to the portal's retention rules, after an authorization check.

Paths use resource names and HTTP methods to express operations. `{id}` is a placeholder for an actual identifier, such as `/events/7`.
