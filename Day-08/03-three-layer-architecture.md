# Three-Layer Architecture

Three-layer architecture separates the HTTP interface, application rules, and data access. A database stores the data used by the repository.

```text
Client → Controller → Service → Repository → Database
```

## The Layers

1. **Controller:** Receives HTTP requests, reads path values and request bodies, calls the appropriate service, and turns the result into an HTTP response.
2. **Service:** Applies business rules, such as checking event capacity or calculating a registration fee. It coordinates work without making the controller responsible for the rules.
3. **Repository:** Reads and writes application data. A repository may use Spring Data JPA to execute queries.
4. **Database:** Persistently stores tables and records. It is the storage layer behind the repository, not a Java request handler.

## Registration Request Flow

For `POST /events/7/register`, one possible request flow is:

1. The HTTP request reaches the embedded Tomcat server.
2. Spring matches the HTTP method and path and routes the request to `EventController.register()`.
3. The controller reads the event ID (`7`) from the path and the registration details from the request body.
4. The controller calls `EventService`, which checks whether the event is available and applies other registration rules.
5. The service asks `RegistrationRepository` to save the registration.
6. The repository writes the new row to the database.
7. The result travels back through the service and controller. The controller responds with the registration as JSON and HTTP status `201 Created`.

The precise response body and status depend on the API design. `201 Created` is appropriate when a new registration has been created successfully.

## Response Travels Back

The response follows the reverse direction: **Database → Repository → Service → Controller → Client**. The repository returns the stored record, the service provides the result to the controller, and the controller serializes it (for example, as JSON) and sets an HTTP status. The server sends that response back to the client.

## Classify the Code

| Code or responsibility | Layer | Reason |
| --- | --- | --- |
| `@PostMapping("/events/{id}/register")` | Controller | Declares an HTTP route. |
| Check seats availability | Service | Applies an event registration business rule. |
| `JpaRepository` interface | Repository | Provides data access operations. |
| `return service.register(id, form)` | Controller | Delegates the request to the service and returns its result. |
| Calculate registration fee | Service | Applies application pricing rules. |
| `findByCity(String city)` | Repository | Declares a data lookup by city. |
