# Controller Mapping

In Spring Boot, a controller connects incoming HTTP requests to Java methods. Consider a controller with the class-level mapping `@RequestMapping("/events")`:

```java
@RestController
@RequestMapping("/events")
public class EventController {

    @GetMapping
    public List<Event> all() { /* ... */ }

    @GetMapping("/{id}")
    public Event one(@PathVariable Long id) { /* ... */ }

    @PostMapping
    public Event create(@RequestBody Event event) { /* ... */ }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { /* ... */ }

    @GetMapping("/{id}/registrations")
    public List<Registration> registrations(@PathVariable Long id) { /* ... */ }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event) { /* ... */ }
}
```

The class-level path is combined with each method-level path. `@GetMapping` with no additional path therefore handles `GET /events`.

| HTTP request | Controller method |
| --- | --- |
| `GET /events` | `all()` |
| `GET /events/7` | `one()` |
| `POST /events` | `create()` |
| `DELETE /events/7` | `delete()` |
| `GET /events/7/registrations` | `registrations()` |
| `PUT /events/3` | `update()` |
| `GET /courses` | No method matches this controller's `/events` base path. |
| `POST /events/7` | No method matches; this controller defines no `POST /{id}` mapping. |

For `GET /events/42`, Spring matches `/{id}` and passes `42` to the method's `id` parameter through `@PathVariable`.

## Beginner's Annotation Guide

- **`@RestController`** marks a class as a web controller whose method results are written to the HTTP response, commonly as JSON. It combines controller behavior with response-body behavior.
- **`@RequestMapping`** sets a URL path (and can also set request conditions) for a controller or method. A class-level `/events` is a shared path prefix.
- **`@GetMapping`** maps an HTTP GET request to a method, usually for reading data.
- **`@PostMapping`** maps an HTTP POST request, commonly used to create a resource or submit an operation.
- **`@PutMapping`** maps an HTTP PUT request, commonly used to replace or update a resource.
- **`@DeleteMapping`** maps an HTTP DELETE request, commonly used to remove a resource.
- **`@PathVariable`** takes a value from a path segment such as `42` in `/events/42` and supplies it to a method parameter.
- **`@RequestBody`** reads the request body, often JSON, and converts it into a Java object parameter.

The method and path together select a handler. A route matching the path but not the HTTP method is not a match.
