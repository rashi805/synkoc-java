# Spring Boot Project Structure

A Spring Boot project commonly groups Java code by responsibility and keeps configuration and page resources under `src/main/resources`.

```text
synkoc-events/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/in/synkoc/events/
    │   │   ├── EventsApplication.java
    │   │   ├── controller/EventController.java
    │   │   ├── service/EventService.java
    │   │   ├── repository/EventRepository.java
    │   │   └── model/Event.java
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/events.html
    │       └── static/css/style.css
    └── test/
        └── java/in/synkoc/events/EventServiceTest.java
```

## Where to Make Common Changes

| Task | File | What it is for |
| --- | --- | --- |
| Change the server port | `src/main/resources/application.properties` | Application settings such as `server.port=8081`. |
| Add a dependency | `pom.xml` | Maven project metadata and dependency declarations. |
| Find `main()` | `src/main/java/in/synkoc/events/EventsApplication.java` | The application entry point that starts Spring Boot. |
| Add a URL / endpoint | `controller/EventController.java` | Maps HTTP methods and paths to controller methods. |
| Add business rules | `service/EventService.java` | Applies application decisions and coordinates work. |
| Add database queries | `repository/EventRepository.java` | Declares or implements data access. A Spring Data repository may derive queries from method names. |
| Add event fields | `model/Event.java` | Defines the event data model; it may be a JPA entity when persistence is configured. |
| Change HTML | `resources/templates/events.html` | A server-rendered page template. |
| Change button colour | `resources/static/css/style.css` | Static CSS styling served by the application. |
| Write automatic tests | `src/test/java/in/synkoc/events/EventServiceTest.java` | Tests service behavior so it can be checked repeatedly. |

The exact packages and conventions can vary between projects. In a Maven project, Java source normally lives under `src/main/java`, resources under `src/main/resources`, and tests under `src/test/java`.
