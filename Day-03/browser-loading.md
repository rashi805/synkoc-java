# How a Browser Loads a Web Page

This overview describes the usual sequence from entering a website address to interacting with its page.

1. **DNS lookup:** The browser resolves the website's domain name to an IP address so it can locate the server.
2. **HTTP request:** The browser connects to the server and requests the page using HTTP or HTTPS.
3. **Server HTML response:** The server responds with the requested HTML document, along with an HTTP status and response headers.
4. **HTML parsing into the DOM:** The browser reads the HTML and builds the Document Object Model (DOM), a structured representation of the page elements.
5. **CSS parsing into the CSSOM:** The browser reads stylesheets and builds the CSS Object Model (CSSOM), representing the applicable style rules.
6. **Render tree, layout, and paint:** The browser combines DOM content and CSS rules into a render tree, calculates element positions and sizes (layout), then paints pixels to the screen.
7. **JavaScript execution and interactivity:** The browser runs scripts, which can respond to user actions and update page content or behavior. Scripts may run during parsing or after the document is parsed, depending on how they are included.

Browsers can overlap or repeat parts of this process. For example, parsing HTML may discover stylesheets, scripts, images, and fonts that need additional network requests; later script or style changes can also cause rendering work again.
