# Day 08 — What Is a Backend

- **Internship:** SYNKOC Java Full Stack
- **Module:** JFS-M4-01
- **Day:** 08
- **Title:** What Is a Backend
- **Status:** Completed
- **Result:** All 6 checks passed
- **Completion code:** `JFS-D08-N9ZXKP`

## Beginner's Guide

1. **What is a backend?** The backend is the server-side part of an application. It receives requests, applies trusted rules, reads or changes data, and sends responses. In a college portal, it can decide whether a student may register for an event.
2. **What is Spring Boot?** Spring Boot is a Java framework that helps create and run Spring applications with convenient configuration. It can start a web server and expose HTTP endpoints.
3. **What is a REST API?** A REST-style API exposes resources through URLs and uses HTTP methods such as GET and POST to describe operations. For example, `GET /events` can return event data.
4. **What is an HTTP method?** It describes the kind of request operation. `GET` reads; `POST` submits or creates; `PUT` replaces/updates; `PATCH` partially updates; and `DELETE` removes.
5. **What is a controller?** A controller maps HTTP requests to Java methods. For example, a Spring method mapped to `GET /events/{id}` can receive the event ID from the URL.
6. **What is a service?** A service contains application or business rules. It can check event capacity and registration eligibility before asking the repository to save a registration.
7. **What is a repository?** A repository handles data access. It can find an event or save a registration in the database.
8. **What is a database?** A database stores application data so it can be retrieved and changed later. The event portal may store users, events, and registrations.
9. **What is a route?** A route is the combination of an HTTP method and a URL path, such as `GET /events`.
10. **What is endpoint mapping?** Endpoint mapping connects a route to a controller method. For example, `@GetMapping("/{id}")` on a controller based at `/events` maps `GET /events/7` to a method and supplies `7` as the path ID.
11. **What is server-side validation?** It is checking submitted values and permissions on the server before accepting an operation. The server must check rules such as seat availability even if the browser already checked.
12. **What is three-layer architecture?** It separates request handling (controller), business rules (service), and data access (repository). The repository communicates with the database.

## Registration Flow Example

For `POST /events/7/register`, the server routes the request to the controller. The controller reads the event ID and submitted details, the service checks availability, the repository saves the registration, and the database stores it. A successful response can return the created registration as JSON with `201 Created`.

## Day 08 Practical Files

- [Controller mapping](01-controller-mapping.md)
- [Spring Boot project structure](02-spring-boot-project-structure.md)
- [Three-layer architecture](03-three-layer-architecture.md)
- [Backend validation rules](04-backend-validation-rules.md)
- [Library API endpoints](05-library-api-endpoints.md)
- [College Event Portal routes](06-college-event-portal-routes.md)
