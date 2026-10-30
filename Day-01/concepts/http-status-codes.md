# Common HTTP Status Codes

An HTTP status code is a number in the server's response that gives a broad indication of what happened to a request.

| Situation | Status code | Meaning | Practical example |
| --- | --- | --- | --- |
| Homepage loads successfully | `200 OK` | The request succeeded, and the response contains the requested result. | A request for `/` returns the homepage content. |
| Old URL redirects to a new address | `301 Moved Permanently` | The resource has a permanent new location; the response points the client to the new URL. | A site permanently moves `/old-courses` to `/courses`. |
| Page does not exist | `404 Not Found` | The server cannot find a resource at the requested URL. | A visitor requests `/courses/unknown` when no such page exists. |
| Backend code crashes during a request | `500 Internal Server Error` | The server encountered an unexpected condition and could not complete the request. | An unhandled server-side error prevents the requested page from being produced. |

These codes describe the result from the server's perspective. A `500` response should not expose sensitive internal error details to a visitor; developers can investigate the corresponding server logs.

