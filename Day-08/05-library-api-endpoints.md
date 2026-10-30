# Library API Endpoints

REST APIs use **nouns** in paths to identify resources (such as `books`, `loans`, and `members`). The HTTP method describes what the client wants to do to that resource. This keeps paths predictable and avoids encoding every action as a verb in the URL.

| Job | Method | Path | What it does |
| --- | --- | --- | --- |
| List all books | `GET` | `/books` | Returns a collection of books. |
| Show one book | `GET` | `/books/{id}` | Returns the book identified by `id`. |
| Add a book | `POST` | `/books` | Creates a book from the request body. |
| Borrow a book | `POST` | `/loans` | Creates a loan record, usually with the member and book references. |
| Return a book | `PATCH` | `/loans/{id}` | Partially updates the identified loan, for example to record its return date. |
| Show member loans | `GET` | `/members/{id}/loans` | Returns the loans associated with the identified member. |
| List available books | `GET` | `/books/available` | Returns books currently available to borrow. |

The `{id}` parts are path parameters filled in by a particular request, such as `/books/7`. A server may return JSON, appropriate HTTP status codes, and error responses when a resource is not found or an operation is invalid.

Typical method meaning:

- **GET** reads data and should not create or change a resource.
- **POST** submits data, commonly to create a resource or initiate an operation.
- **PATCH** partially changes an existing resource.

For example, `GET /books` reads the book collection, while `POST /books` creates a book. The path names the resource and the HTTP method communicates the operation.
