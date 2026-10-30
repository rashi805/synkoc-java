# Planned CampusConnect Homepage Request Flow

**Project status:** CampusConnect is a planned project, not a built or deployed application. The `/api/events` path below is an illustrative endpoint, not a claim that a live API exists.

1. **User enters the website address:** The user types the planned CampusConnect website address into a browser.
2. **DNS resolves the domain:** The browser looks up the domain name to find the server address.
3. **Browser requests HTML:** The browser sends a request for the homepage document.
4. **Server returns HTML:** The server responds with the page's HTML.
5. **Browser loads page resources:** The browser requests the CSS, JavaScript, images, and fonts referenced by the page.
6. **Frontend requests events:** The page's frontend sends a request to the illustrative `/api/events` endpoint.
7. **Backend processes the request:** In the planned application, the backend would handle the request and retrieve event information from its data source.
8. **Browser displays the homepage and upcoming events:** The frontend would use the returned page and event information to display the homepage and upcoming events to the user.
