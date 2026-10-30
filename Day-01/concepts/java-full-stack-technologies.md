# Java Full Stack Technologies

A Java full stack application combines browser technologies, server-side Java technologies, and a database. Tools help developers build, test, and manage the application.

| Technology | Layer | Purpose | Simple real-world explanation |
| --- | --- | --- | --- |
| HTML | Frontend | Defines the structure and content of a webpage. | The page's frame: headings, forms, buttons, and other content. |
| CSS | Frontend | Controls the visual presentation and layout of a webpage. | The colors, spacing, and arrangement that make the page readable. |
| JavaScript | Frontend | Adds behavior and interaction in the browser. | The code that can respond when a visitor selects a button or fills in a form. |
| Java | Backend | Implements server-side application logic. | The server's instructions for processing a request and applying application rules. |
| Spring Boot | Backend | Helps create and run Spring-based Java applications with convenient defaults. | A framework that helps assemble a Java web server without wiring every piece by hand. |
| Spring Data JPA | Backend | Simplifies data access through repositories and JPA. | A way for Java code to work with stored records without writing every common database operation from scratch. |
| SQL | Database | A language for querying and changing relational database data. | The language a program can use to ask for records or save changes in a relational database. |
| MySQL | Database | A relational database management system for persistent application data. | A database server that can store records such as users, events, and registrations. |
| H2 | Database | A Java-based relational database often used for development, learning, or tests. | A lightweight database that can be convenient while practicing or testing an application. |
| Maven | Build tool | Manages Java project builds and dependencies using a project configuration. | A project assistant that can download libraries and compile or test the application. |
| Git | Development tool | Tracks changes to files over time. | A change history that helps developers review and manage their work. |
| GitHub | Collaboration and hosting platform | Hosts Git repositories and supports collaboration. | An online place to share a repository and work with others. |
| IntelliJ IDEA | Development tool | An integrated development environment (IDE) for writing and working with code. | A coding workspace with editing, navigation, and debugging features. |

## How They Work Together

For example, a student could open an events page built with **HTML**, styled with **CSS**, and enhanced with **JavaScript**. The browser sends a request to a server built with **Java** and **Spring Boot**. The server applies the application's rules and can use **Spring Data JPA** to access event records in **MySQL** or **H2**. **SQL** is the standard language associated with relational data operations, while a JPA-based application may generate many database operations through its data-access layer.

Developers can use **Maven** to build and test the Java project, **IntelliJ IDEA** to edit and debug it, **Git** to track changes, and **GitHub** to host and collaborate on the repository. The exact combination depends on the application's needs; these tools do not all run in the same layer.

