# Backend Validation Rules

The backend is responsible for making trusted decisions about submitted data and actions. A frontend can provide fast feedback, but a client can be changed, bypassed, or replaced by a direct HTTP request. Therefore, frontend checks do not replace backend validation and authorization.

## Rules the Backend Must Enforce

| Rule | Why the backend must check it |
| --- | --- |
| Check remaining seats | The server has to use current trusted availability and prevent overbooking when requests arrive close together. |
| Calculate registration fees | Prices and totals are business rules; a client-provided total must not be trusted. |
| Validate email on the server | Requests can bypass browser form validation, so the server must check the submitted value too. |
| Check admin authorization | The server must verify the authenticated user's permissions before allowing administrative actions. |
| Prevent duplicate registrations | The server must check the user's existing registration and ideally enforce uniqueness in storage as well. |
| Reject past event dates | The server applies the event creation/update rule consistently regardless of client behavior. |
| Verify discount codes and expiry | The server must determine whether a code exists, is eligible, and is still valid. |

Validation checks that data follows rules; **authorization** checks whether the user is allowed to perform an action. Both are backend responsibilities for protected operations.

## Frontend-Only Presentation Examples

These are presentation or convenience behaviors and can be handled in the frontend:

- Turn an invalid field red while typing.
- Display dates in the user's language.
- Collapse long descriptions.
- Show confetti after registration.
- Remember the last opened tab.

Even when the frontend shows immediate validation, the backend must repeat any rule that affects data integrity, access, money, capacity, or eligibility. This makes the rule apply to every request, not only requests from the intended web page.
