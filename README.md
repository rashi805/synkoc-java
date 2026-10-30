# SYNKOC – Java Full Stack Development Internship

Welcome to my learning repository for the **SYNKOC Java Full Stack Development internship**. It brings together the learning notes, Java practice programs, and project planning completed so far, with links to each day's work.

**Organization:** SYNKOC · **Domain:** Java Full Stack Development

> The stack overview distinguishes topics introduced in learning notes from technologies used in the code currently in this repository.

## Table of Contents

- [About the Internship](#about-the-internship)
- [Technology Stack](#technology-stack)
- [Daily Learning Roadmap](#daily-learning-roadmap)
- [Day 01 — Complete Documentation](#day-01--what-is-java-full-stack-development)
- [Day 02 — Complete Documentation](#day-02--java-fundamentals-and-console-programs)
- [Day 03 — Complete Documentation](#day-03--lab-how-the-web-works)
- [Day 04 — Complete Documentation](#day-04--html-basics)
- [Day 05 — Complete Documentation](#day-05--what-is-a-database)
- [Learning Progress](#learning-progress)
- [Projects](#projects)
- [Skills Developed](#skills-developed)
- [Repository Structure](#repository-structure)
- [Learning Outcomes](#learning-outcomes)
- [Future Learning](#future-learning)
- [Author](#author)

## About the Internship

This repository documents my progress through the SYNKOC Java Full Stack Development internship. It is a central place to follow the concepts introduced, review practical exercises, and navigate the work completed on each day. The current material covers web application fundamentals, browser tooling, introductory Java programming, and database concepts and schema design.

## Technology Stack

The Day 01 technology notes introduce the full-stack tools below. The status column clarifies whether a technology appears in hands-on code or is covered as an overview.

![Java practice](https://img.shields.io/badge/Java-Day%2002%20practice-ED8B00?logo=openjdk&logoColor=white)
![GitHub repository](https://img.shields.io/badge/GitHub-learning%20repository-181717?logo=github&logoColor=white)

| Category | Technologies | Evidence in this repository |
| --- | --- | --- |
| **Frontend** | HTML, CSS, JavaScript | Introduced in the Day 01 technology overview; no frontend implementation is included yet. |
| **Backend** | Java, Spring Boot, Spring Data JPA | Java is used in the Day 02 console exercises. Spring Boot and Spring Data JPA are introduced in Day 01 notes; no backend application is included yet. |
| **Database** | SQL, MySQL, H2 | Introduced in the Day 01 technology overview; Day 05 includes MySQL-compatible schema examples and relationship exercises, but no live database connection is configured. |
| **Tools** | Maven, Git, GitHub, IntelliJ IDEA | Maven and IntelliJ IDEA are introduced in the Day 01 notes. The existing root README also identified Git and GitHub Codespaces; this repository is hosted on GitHub. |

## Daily Learning Roadmap

| Day | Topic | Status | Documentation / Work |
| --- | --- | --- | --- |
| **Day 01** | Java full-stack foundations and browser lab | Completed | [Day 01 documentation](#day-01--what-is-java-full-stack-development) |
| **Day 02** | Java fundamentals and console programs | Completed | [Day 02 lab files](day02/) |
| **Day 03** | How the Web Works browser lab | Completed | [Day 03 documentation](#day-03--lab-how-the-web-works) |
| **Day 04** | HTML Basics | Completed | [Day 04 documentation](#day-04--html-basics) |
| **Day 05** | What Is a Database | Completed | [Day 05 documentation](#day-05--what-is-a-database) |

## Day 01 — What Is Java Full Stack Development?

- **Lab:** JFS-M1-01 – Browser Lab
- **Status:** Completed

Day 01 introduced the parts of a web application and how they work together. It covered frontend, backend, and database responsibilities; browser DevTools; URL structure; common HTTP status codes; the browser request-response lifecycle; and the Java full-stack technology landscape. CampusConnect was introduced as a project idea, not a built application.

### Part A — Guided Learning

#### A1 — Frontend, Backend, and Database

An online shopping website illustrates the three main application layers:

- **Frontend:** the part a person sees and uses in the browser. It presents controls and content and can send requests to the backend.
- **Backend:** the server-side part that handles requests, applies trusted application rules, and coordinates access to stored information.
- **Database:** organized, persistent storage for application data. It stores information; it does not independently apply shopping rules.

| Shopping website component | Layer | Reason |
| --- | --- | --- |
| Add-to-cart button and page layout | Frontend | The visible interface is presented and used in the browser; the button can send a request to the backend. |
| Product photos displayed on the page | Frontend | Display and layout are frontend responsibilities, even when image files are served elsewhere. |
| Search box | Frontend | The input and controls are part of the interface; searching is commonly performed by the backend after receiving the search text. |
| Email validation while typing | Frontend | Immediate browser feedback improves the form experience; the backend must also validate submitted values. |
| Checking stock availability | Backend | The server applies the inventory rule using current data before processing a request. |
| Calculating cart totals and GST | Backend | Pricing and tax rules should be applied consistently by the server, not trusted to browser code. |
| Verifying a password at login | Backend | The server validates a login against stored password-hash information. |
| Applying a coupon | Backend | The server checks coupon validity and eligibility and calculates the discount. |
| Product and price records | Database | Persistent product information is stored and retrieved by the backend. |
| Order history | Database | Past orders must be retained for later retrieval. |
| User accounts and password hashes | Database | Account data is stored persistently; passwords should be stored as secure hashes, not plain text. |
| Product stock counts | Database | Inventory quantities are persistent data that the backend reads and updates. |

When a shopper selects **Add to cart**, the frontend sends a request to the backend. The backend checks the product and stock, reads relevant information from the database, applies business rules, and returns a result for the frontend to display. These are responsibilities, not necessarily separate physical machines.

#### A2 — Browser Developer Tools

Developer Tools (DevTools) are built-in browser panels used to inspect and diagnose webpages. In Chrome or Edge, open a page and press **F12** (some laptops require **Fn + F12**) or use the browser menu's **More tools → Developer tools** option.

- The **Elements** panel displays the current HTML structure as a nested tree. It can show element content and attributes. For example:

  ```html
  <button type="button" data-level="...">Register</button>
  ```

  Here, `button` is the element, `Register` is its text, and `type` and `data-level` are attributes. A `data-*` attribute can hold application-specific data. The SYNKOC lab may assign each learner a unique `data-level`; inspect the value in your own lab rather than copying or inventing one.
- An HTML comment has the form `<!-- This is a comment in the HTML source. -->`. It is not shown as ordinary page text, though it may appear in the markup. Comments are not secure storage.
- The **Console** displays browser and webpage messages, such as errors, warnings, and informational output. Follow the actual lab instructions for any personal secret word or console code; do not use values copied from another learner or enter code from an untrusted source.
- A basic debugging process is to reproduce a problem, inspect the related element in **Elements**, check **Console** for related messages, change the source code as appropriate, and test again. DevTools helps investigate but does not automatically identify or fix every issue.

#### A3 — Java Full-Stack Technologies

| Technology | Layer or role | Purpose |
| --- | --- | --- |
| HTML | Frontend | Defines webpage structure and content, such as headings, forms, and buttons. |
| CSS | Frontend | Controls webpage visual presentation, colors, spacing, and layout. |
| JavaScript | Frontend | Adds browser behavior and interaction, such as responding to button selections. |
| Java | Backend | Implements server-side application logic and request processing. |
| Spring Boot | Backend | Helps create and run Spring-based Java applications with convenient defaults. |
| Spring Data JPA | Backend | Simplifies Java data access through repositories and JPA. |
| SQL | Database | Queries and changes data in relational databases. |
| MySQL | Database | Relational database management system for persistent application data. |
| H2 | Database | Java-based relational database often used for development, learning, or tests. |
| Maven | Build tool | Manages Java builds and dependencies and can compile and test a project. |
| Git | Development tool | Tracks changes to files over time. |
| GitHub | Collaboration and hosting | Hosts Git repositories and supports collaboration. |
| IntelliJ IDEA | Development tool | IDE for editing, navigating, and debugging code. |

For example, HTML, CSS, and JavaScript can make up an events page in the browser. The browser requests information from a Java and Spring Boot server. The server applies application rules and may use Spring Data JPA to access records in MySQL or H2. Maven can build and test the Java project; IntelliJ IDEA can be used to edit and debug it; Git tracks changes; and GitHub hosts the repository. The exact combination depends on the application.

#### A4 — Browser Request-Response Lifecycle

For a typical request such as `https://www.synkoc.com/courses`:

1. **DNS lookup:** The browser looks up the IP address associated with `www.synkoc.com`.
2. **Secure HTTPS connection:** The browser establishes an encrypted connection and checks the server certificate.
3. **HTTP GET request:** The browser requests the `/courses` resource.
4. **Backend route:** The server handles the request using the application logic associated with `/courses`.
5. **Database query:** If course data is needed, the backend asks the database for records and receives the results.
6. **HTTP response:** The server returns a status code and content such as HTML. A successful response may use `200 OK`; a redirect or failure may use another status.
7. **Browser rendering:** The browser processes the HTML and may request CSS, JavaScript, and images before displaying the page.

The database does not send a webpage directly to the browser. The backend uses database results as needed to prepare its response; the browser can then make additional requests for linked resources.

### Part B — Challenge

#### B1 — URL Breakdown

Consider:

```text
https://shop.example.com/products/shoes?size=9&color=black
```

| Part | Value | Meaning |
| --- | --- | --- |
| Protocol (scheme) | `https` | Uses HTTP protected by TLS encryption. |
| Domain (host) | `shop.example.com` | Human-readable host name the browser looks up to locate a server. |
| Path | `/products/shoes` | Identifies a resource or route on that host. |
| Query string | `size=9&color=black` | Optional key-value parameters; `?` begins the query and `&` separates parameters. |

For `https://example.com/about`, the protocol is `https`, host is `example.com`, path is `/about`, and there is no query string. For `https://shop.example.com/search?q=backpack&page=2`, the path is `/search`; query parameter `q` has value `backpack`, and `page` has value `2`. Query-string values are inputs, not proof that the server accepted or acted on them; the application decides how to validate and use them.

#### B2 — Common HTTP Status Codes

An HTTP status code is a number in a server response that broadly indicates what happened to a request.

| Code | Meaning | Example |
| --- | --- | --- |
| `200 OK` | The request succeeded and the response contains the requested result. | A request for `/` returns homepage content. |
| `301 Moved Permanently` | The resource has a permanent new location; the response points the client to it. | `/old-courses` permanently redirects to `/courses`. |
| `404 Not Found` | The server cannot find a resource at the requested URL. | `/courses/unknown` does not identify an existing page. |
| `500 Internal Server Error` | The server encountered an unexpected condition and could not complete the request. | An unhandled server-side error prevents the page from being produced. |

A `500` response should not expose sensitive internal details to visitors; developers can investigate server logs.

#### B3 — From Pressing Enter to Seeing a Page

When a visitor enters a website address and presses **Enter**, the browser uses DNS to find the domain's IP address, establishes an HTTPS connection, and sends an HTTP request. The backend runs the matching route and application rules and may ask a database for information such as products or events. The backend prepares an HTTP response containing a status and content such as HTML. The browser parses the HTML, requests linked CSS, JavaScript, and image resources, applies styles, runs scripts, and renders the page. A failed or moved resource can result in a different status code.

### Part C — Internship Task: CampusConnect Planning

**CampusConnect is a proposed college event registration system. This is planning documentation only; the application is not claimed to be built or deployed.**

- **Problem and objectives:** Event information may be spread across platforms. The proposal is to provide a central place to browse college events, register, track registration status, and manage events and registrations.
- **Planned users:** Students discover events and track registrations; Event Organizers coordinate event information; Faculty view or support relevant events; Administrators create events and manage registrations.
- **Planned features:** Browse events and announcements; view schedules, venues, and categories; submit registrations; review registration status; create and manage events; and manage registrations.
- **Planned pages:** Home Page, Events Page, Registration Page, My Registrations, and Admin Dashboard.
- **Planned data entities:** Students (identifier, name, email, account details); Events (identifier, title, description, schedule, venue, category, capacity, creator); Registrations (identifier, student, event, registration date, status); Admins (identifier, name, email, account details); and Event Categories (identifier and name). Exact fields and privacy requirements would need review before implementation. Credentials must be handled securely; passwords should not be stored as plain text.
- **Planned relationships:** One student can have many registrations; one event can have many registrations; registrations connect students and events in a many-to-many relationship. One category can group many events. An administrator can create or manage many events.
- **Student workflow:** Review events on Home; inspect details on Events; submit a registration; have the registration and initial status recorded; and review it under My Registrations.
- **Admin workflow:** Open the Admin Dashboard; create an event with its details and category; allow students to register; review registrations; and maintain event and registration information.
- **Possible future enhancements:** Search and filtering; capacity limits and waitlists; notifications; attendance tracking and feedback; role-based access and audit history; accessibility; mobile-friendly layouts; and deployment planning.

Supporting Day 01 topic files are retained in [Day-01/](Day-01/).

## Day 02 — Java Fundamentals and Console Programs

- **Lab:** JFS-M1-02
- **Status:** Completed

Day 02 develops Java fundamentals through variables and data types, console input and output, conditional logic, and formatted calculations. The source files are in [day02/](day02/). The lab checker uses sample inputs to verify the six activities; its results depend on the files and the learner name supplied when it runs.

**Practical activities**

- **A1 — Variables and types:** [Variables.java](day02/Variables.java) declares a course `String`, seat-count `int`, fee `double`, open-state `boolean`, and grade `char`, then prints their values.
- **A2 — Input and greeting:** [Greeting.java](day02/Greeting.java) reads a full name and age with `Scanner`, then greets the person and reports their age the following year.
- **A3 — Conditional grading:** [Grade.java](day02/Grade.java) checks that marks are in the 0–100 range and uses `if`/`else if`/`else` boundaries: A for 90–100, B for 75–89, C for 60–74, D for 40–59, and F below 40. Out-of-range marks produce `Invalid marks`.
- **A4 — Formatted receipt:** [Receipt.java](day02/Receipt.java) reads a course name and fee, calculates GST at 18%, adds it to the fee, and prints the course, fee, GST, and total as rupee amounts with two decimal places.
- **B — Course fee calculator:** [FeeCalculator.java](day02/FeeCalculator.java) offers Java Full Stack (Rs. 14,999), Data Science (Rs. 12,999), and Cloud Basics (Rs. 4,999); accepts 1-, 3-, or 6-month plans; applies a 10% student discount when applicable, a 2% surcharge for 3 months or 5% for 6 months, then calculates 18% GST, total, and per-month amount. It reports invalid course or plan choices and repeats the student prompt until the answer is yes or no.
- **C — CampusEvents outline:** [MyApp.java](day02/MyApp.java) prints the CampusEvents name and tagline, a five-page count, and a numbered list of Home, Event Details, Register, My Registrations, and Admin Dashboard.
- **Lab checks:** [CheckLab.java](day02/CheckLab.java) identifies the lesson as `JFS-M1-02`, runs checks A1–A4, B, and C, and prints pass or diagnostic output for the supplied learner name. Its source comment documents the command `java CheckLab.java "Your Full Name"`. Check results and any generated codes are learner-specific; no result is inferred here.

## Day 03 – Lab: How the Web Works

- **Day:** 03
- **Lab ID:** JFS-M2-01
- **Completion status:** Completed (**6/6 checks passed**)
- **Completion code:** `JFS-D03-4QN8NJ`

This browser lab used Developer Tools to inspect HTML, edit CSS temporarily, monitor network activity, and evaluate JavaScript in the Console. It also explained the browser loading process and applied it to a planned CampusConnect homepage request flow.

### A1 — Inspect Elements

Chrome and Edge Developer Tools provide panels for examining and debugging a webpage. The **Elements** panel displays the page's HTML structure and lets you select elements to inspect their markup and attributes, such as an element's `id`, `class`, or link target.

For this activity, the **Register** button on the **TechFest 2026** practice card was selected in the Elements panel. Inspecting it shows the button's HTML element and attributes, helping identify how it is structured and associated with the page.

### A2 — Edit CSS Live

The **Styles** panel shows CSS rules for the selected element. A style can be changed or added there to preview how the page would look with different CSS. The TechFest 2026 heading can, for example, be changed to green and enlarged:

```css
color: green;
font-size: 40px;
```

These are temporary browser-side edits. They change the current page view in Developer Tools, not the website's source files or server. Reloading the page or closing the tab can remove the edits.

### A3 — Network Tab

The **Network** panel records resources requested by a page, their HTTP statuses, resource types, transferred sizes, and request times.

| Resource | Status | Type | Size | Time |
| --- | --- | --- | --- | --- |
| `index.html` | 200 | document | 7.2 kB | 95 ms |
| `style.css` | 200 | stylesheet | 18 kB | 48 ms |
| `app.js` | 200 | script | 31 kB | 62 ms |
| `hero-banner.jpg` | 200 | jpeg | 3.4 MB | 2.6 s |
| `sponsor-logo.png` | 404 | png | 0.2 kB | 18 ms |
| `events` | 200 | fetch | 1.1 kB | 140 ms |

**Network summary**

- **Total requests:** 6
- **Transferred data:** 3.5 MB
- **DOMContentLoaded:** 0.41 seconds
- **Load time:** 2.9 seconds

**Observations**

- **HTTP 200** means the server successfully handled the request and returned a response.
- **HTTP 404** means the requested resource was not found at the requested URL.
- `sponsor-logo.png` failed because the server did not find a resource at the requested URL. A missing file or incorrect path are possible causes; the supplied Network data does not establish which one occurred.
- `hero-banner.jpg` was the slowest resource in this sample: its size was 3.4 MB and its request took 2.6 seconds. Its large transfer can take longer than the smaller listed resources.
- Image compression reduces the bytes needed to transfer an image. Appropriately sized, compressed images can reduce download time and improve page loading.
- `events` is an API fetch request: its resource type is `fetch`, indicating that page JavaScript requested data asynchronously rather than loading it as a document, stylesheet, or script file.

### A4 — Console

The browser **Console** displays JavaScript output and browser messages, and it can run JavaScript expressions in the context of the current page. For example:

```javascript
17 * 23
```

Output:

```text
391
```

The expression `document.title` reads the title of the current webpage from its document's `title` property. The returned value depends on the page open in the browser. No particular webpage title, warning filename, or error code is supplied or assumed here.

### B — Challenge: Browser Loading Process

1. **DNS finds the server IP.** The browser uses DNS to look up which IP address belongs to the website's domain name.
2. **The browser sends an HTTP request.** Using that address, the browser asks the server for the webpage or another resource.
3. **The server responds with HTML.** The server handles the request and sends an HTTP response containing the page's HTML, or another appropriate response.
4. **The browser parses HTML into the DOM.** The browser reads the HTML and represents the page's elements and relationships as the Document Object Model (DOM).
5. **CSS downloads and becomes the CSSOM.** The browser reads the page's CSS and represents its styling rules as the CSS Object Model (CSSOM).
6. **The browser renders the page.** It combines the DOM and CSSOM into a render tree, calculates where visible elements belong (layout), and paints them on screen.
7. **JavaScript runs and the page becomes interactive.** Scripts can respond to user actions and update page content or behavior, allowing the user to interact with the page.

### C — My Request Flow: CampusConnect

**CampusConnect is a planned project, not a deployed application.** It is a proposed college event registration system for **Students**, **Event Organizers**, **Faculty**, and **Administrators**. Planned pages are **Home**, **Events**, **Registration**, **My Registrations**, and **Admin Dashboard**. The `/api/events` endpoint below is an illustrative planned example, not a deployed service.

One possible eight-step flow for loading its homepage is:

1. A user opens a browser and enters the planned CampusConnect homepage address.
2. DNS looks up the domain and returns the IP address for the server that would host the application.
3. The browser sends an HTTP request for the homepage to that server.
4. The server would respond with the homepage HTML, which the browser parses into the DOM.
5. The browser requests the page's CSS and other required assets, builds the CSSOM, and uses the DOM and CSSOM to lay out and paint the page.
6. The browser loads and runs the page's JavaScript, making planned homepage controls interactive.
7. The page's JavaScript could send a fetch request to the illustrative `/api/events` endpoint to request event data.
8. The planned backend could respond with event data; the browser could then display it on the Events or Home page for the user.

### Day 03 Learning Outcomes

By completing Day 03, I learned to:

- Inspect HTML elements and attributes in the Elements panel.
- Edit CSS in the Styles panel and understand that live changes are temporary.
- Monitor page resources, request types, sizes, and load times in the Network panel.
- Explain HTTP 200 and 404 responses using the observed request results.
- Use the browser JavaScript Console to evaluate expressions and inspect `document.title`.
- Describe DNS lookup and HTTP requests in a browser's loading process.
- Explain how HTML becomes the DOM and CSS becomes the CSSOM.
- Describe the render tree, layout, and painting stages of browser rendering.
- Recognize an API fetch request and explain its role in loading page data.
- Explain how a frontend can request data from a backend and display the response.

## Day 04 – HTML Basics

- **Module:** Java Full Stack Development – Module 2
- **Day:** 04
- **Lab ID:** JFS-M2-02
- **Lab title:** HTML Basics
- **Status:** Completed
- **Checks passed:** 6/6
- **Practical work:** [Day-04 homepage](Day-04/index.html), with links to the individual exercises.

Day 04 introduced the structure and meaning of HTML. The guided exercises covered a first webpage, links, images, and semantic layout, followed by a personal profile and a homepage for the CampusEvents internship application.

### Part A – Four Guided HTML Tasks

#### A1 – My First Web Page

An HTML document starts with `<!DOCTYPE html>`, which tells the browser to use modern HTML standards mode. The root `<html lang="en">` element contains the page and identifies its language. Inside it, `<head>` holds document information such as `<meta charset="UTF-8">` (the character encoding) and `<title>` (the browser-tab title). The visible page content belongs in `<body>`.

Headings describe the page's content hierarchy: `<h1>` is the main heading, while `<h2>` introduces a subsection. `<p>` marks a paragraph of text.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My First Web Page</title>
</head>
<body>
    <h1>Welcome to My Page</h1>

    <h2>About This Lab</h2>
    <p>This is the first paragraph of my web page. I am learning how to structure HTML documents correctly using the doctype, head, and body tags.</p>

    <h2>My Goals</h2>
    <p>In this lab, I will practice adding links, images, and semantic tags to create a well-structured and accessible website.</p>
</body>
</html>
```

#### A2 – HTML Links

The `<a>` (anchor) element creates a link, and its `href` attribute gives the destination. Common link types include:

1. **External website:** links to another website. `target="_blank"` requests that it open in a new tab.
2. **Relative page:** links to another page in the same site or project directory, such as `schedule.html`.
3. **Email:** `mailto:` opens the user's configured email application to compose a message. The address below is an example, not my actual email address.

```html
<a href="https://www.synkoc.com" target="_blank" rel="noopener noreferrer">Visit SYNKOC</a>

<a href="schedule.html">View Schedule</a>

<a href="mailto:support@example.com">Contact Support</a>
```

Meaningful link text explains where a link goes or what it does, including when links are read out of context by assistive technology. Avoid vague text such as “click here.” With `target="_blank"`, `rel="noopener"` prevents the newly opened page from controlling the originating page through `window.opener`; `noreferrer` also omits referrer information.

#### A3 – HTML Images

The `<img>` element embeds an image in a page. Its `src` attribute specifies the image file or URL, `alt` provides a text alternative, and `width` can set the displayed width in pixels. The image files used in this lab are `campus.jpg` and `poster.png`.

```html
<img src="campus.jpg" alt="A wide view of our green college campus" width="600">

<img src="poster.png" alt="Event poster for TechFest 2026">
```

Useful alternative text communicates the image's important information to people using screen readers and appears when an image cannot be displayed. It should describe the image in context rather than repeat a filename; decorative images that add no information can use `alt=""`.

#### A4 – Semantic HTML

Semantic HTML uses elements that communicate the role of their content. `<header>` introduces a page or section, `<nav>` groups navigation links, `<main>` contains the page's primary content, and `<section>` groups related content. `<h1>` identifies the main heading and `<h2>` labels subsections. `<footer>` contains footer information such as authorship or copyright.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tech Portal</title>
</head>
<body>

<header>
    <h1>Tech Portal</h1>
    <nav>
        <a href="index.html">Home</a>
        <a href="about.html">About</a>
    </nav>
</header>

<main>
    <section>
        <h2>Latest News</h2>
        <p>Web development is evolving fast.</p>
    </section>

    <section>
        <h2>Upcoming Events</h2>
        <p>Join our next hackathon.</p>
    </section>
</main>

<footer>
    <p>© 2026 Rashi Mishra</p>
</footer>

</body>
</html>
```

### Part B – Personal Profile Page

The completed profile page introduces me, describes my interests, and presents my skills in a clear, semantic structure.

- **Name:** Rashi Mishra
- **About Me:** I am an aspiring web developer currently pursuing a Java Full Stack internship at SYNKOC. I am passionate about creating clean, efficient, and accessible user interfaces that solve real-world problems for users everywhere.
- **Skills:** HTML5 and Semantic Tagging; CSS3 and Responsive Layouts; JavaScript and Problem Solving.
- **Image:** `me.jpg` with descriptive alternative text. The file must exist in the same directory as the HTML page for this relative image path to display.
- **Email:** The example uses `your-email@example.com` as a clearly marked placeholder. Replace it with my chosen contact address before using the page; it is not represented as my real email address.

The page has a complete document structure, exactly one `<h1>` containing my name inside `<header>`, two sections inside `<main>`, an About Me paragraph with more than 30 words, a skills list, an email link, a profile image, and a footer. The HTML tags are properly closed.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rashi Mishra | Profile</title>
</head>
<body>
    <header>
        <h1>Rashi Mishra</h1>
        <nav>
            <a href="#about">About Me</a>
            <a href="#skills">Skills</a>
        </nav>
    </header>

    <main>
        <section id="about">
            <h2>About Me</h2>
            <img src="me.jpg" alt="Profile portrait of Rashi Mishra" width="200">
            <p>I am an aspiring web developer currently pursuing a Java Full Stack internship at SYNKOC. I am passionate about creating clean, efficient, and accessible user interfaces that solve real-world problems for users everywhere.</p>
            <p>Email: <a href="mailto:your-email@example.com">your-email@example.com (placeholder)</a></p>
        </section>

        <section id="skills">
            <h2>Skills</h2>
            <ul>
                <li>HTML5 and Semantic Tagging</li>
                <li>CSS3 and Responsive Layouts</li>
                <li>JavaScript and Problem Solving</li>
            </ul>
        </section>
    </main>

    <footer>
        <p>© 2026 Rashi Mishra</p>
    </footer>
</body>
</html>
```

### Part C – Internship Application: Home Page

The completed homepage is for **CampusEvents**, the name used by this Day 04 lab. It is documented here as a lab page, not as a claim that a deployed application exists. The illustrative image filename is `home.jpg`; keep the image in the same directory as the page or update its `src` to the correct location.

- **Lab page title / application name:** CampusEvents
- **Main heading:** Connecting Students to Opportunities
- **Description:** CampusEvents is a dedicated platform designed for college students to discover, track, and register for workshops, hackathons, and social gatherings happening across their university campus every single day.
- **Features:** Real-time event discovery with seat availability tracking; personalized event calendars and registration management; direct communication with event organizers and student leaders.
- **Additional section:** Get Started
- **Footer:** © 2026 CampusEvents. All student rights reserved.

The example has a full HTML skeleton, a header with exactly one `<h1>`, navigation links, a main area with multiple sections, the application description, three features, an image with meaningful alt text, a footer, and properly closed HTML tags.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CampusEvents</title>
</head>
<body>
    <header>
        <h1>Connecting Students to Opportunities</h1>
        <nav>
            <a href="#about">About</a>
            <a href="#features">Features</a>
            <a href="#get-started">Get Started</a>
        </nav>
    </header>

    <main>
        <section id="about">
            <h2>About CampusEvents</h2>
            <p>CampusEvents is a dedicated platform designed for college students to discover, track, and register for workshops, hackathons, and social gatherings happening across their university campus every single day.</p>
            <img src="home.jpg" alt="Students discovering campus events together" width="600">
        </section>

        <section id="features">
            <h2>Features</h2>
            <ul>
                <li>Real-time event discovery with seat availability tracking.</li>
                <li>Personalized event calendars and registration management.</li>
                <li>Direct communication with event organizers and student leaders.</li>
            </ul>
        </section>

        <section id="get-started">
            <h2>Get Started</h2>
            <p>Join thousands of students who are already enhancing their college experience through meaningful participation in local campus activities.</p>
        </section>
    </main>

    <footer>
        <p>© 2026 CampusEvents. All student rights reserved.</p>
    </footer>
</body>
</html>
```

The earlier project-planning section calls the proposed project **CampusConnect**; this Day 04 lab specifically uses **CampusEvents**. These names document different internship activities and are retained as given.

### Day 04 Learning Outcomes

By completing Day 04, I learned to:

- Create a complete HTML document with a doctype, language, metadata, title, head, and body.
- Identify HTML elements and use attributes to provide settings and additional information.
- Create external website, relative internal-page, and email links.
- Add images with descriptive alternative text and appropriate image paths.
- Use semantic HTML elements to communicate page structure and content roles.
- Build structured page layouts with headings, navigation, main content, sections, and footers.
- Create a personal profile page with an about section, skills list, image, and contact link.
- Create a homepage for a college event registration application.
- Explain why accessible and maintainable HTML benefits users and developers.

### Day 04 Completion Details

- **Day:** 04
- **Lab ID:** JFS-M2-02
- **Lab title:** HTML Basics
- **Status:** Completed
- **Checks passed:** 6/6
- **Completion code:** `JFS-D04-50MUCI`

## Day 05 — What Is a Database

- **Day:** 05
- **Title:** What Is a Database
- **Lab ID:** JFS-M3-01
- **Status:** Completed
- **Checks passed:** 6/6

Day 05 introduced databases and relational design. The lab explored sample students, courses, and enrollments; identified keys and relationships; compared SQL and NoSQL; and designed schemas for a canteen, a library, and a college event portal.

### Part A — Database Concepts and Canteen

#### A1 — Explore a Sample Database

A **database** is an organized collection of information that can be stored, searched, changed, and managed. A relational database organizes information in **tables**. A table contains **columns** (the attributes, or fields) and **rows** (individual records). For example, each row in `students` describes one student, and its columns can include `id`, `name`, and `city`.

The sample relational database uses three tables:

| Table | Example columns | Purpose |
| --- | --- | --- |
| `students` | `id`, `name`, `city` | Stores each student. |
| `courses` | `id`, `title` | Stores each course. |
| `enrollments` | `id`, `student_id`, `course_id` | Connects students with courses. |

The lab's actual query results were:

| Question | Result |
| --- | --- |
| Bengaluru students | 3 |
| Course with ID 3 | Cloud Basics |
| Students enrolled in Java Full Stack | 3 |
| Student without a course | Kiran Gowda |
| Arjun Rao's courses | Data Science and Cloud Basics |

For example, the course lookup and a student's course list use these joins:

```sql
SELECT title
FROM courses
WHERE id = 3;

SELECT c.title
FROM students AS s
JOIN enrollments AS e ON e.student_id = s.id
JOIN courses AS c ON c.id = e.course_id
WHERE s.name = 'Arjun Rao';
```

The [database basics practical](Day-05/database-basics.md) includes SQL examples for all five lab results.

#### A2 — Identify the Keys

A **primary key (PK)** uniquely identifies a row in its table. A **foreign key (FK)** refers to a key in another table and helps keep related records valid.

| Table | Primary key | Foreign key |
| --- | --- | --- |
| `students` (`id`, `name`, `city`) | `id` | — |
| `courses` (`id`, `title`) | `id` | — |
| `enrollments` (`id`, `student_id`, `course_id`) | `id` | `student_id` → `students.id`; `course_id` → `courses.id` |
| `payments` (`id`, `student_id`, `amount`) | `id` | `student_id` → `students.id` |

Each enrollment belongs to one student and one course. A student can have multiple enrollments, and a course can have multiple enrollments. Therefore, `enrollments` models the many-to-many relationship between students and courses. A student can also have multiple payment records; each payment references one student. See [keys-and-relationships.md](Day-05/keys-and-relationships.md) for a SQL definition example.

#### A3 — Compare SQL and NoSQL

**Relational SQL databases** use defined tables, constraints, and relationships. SQL supports queries and joins across those tables. **NoSQL document databases** store JSON-like documents and can allow documents in a collection to have different fields.

| Lab scenario | Typical choice |
| --- | --- |
| Bank balances and transfers | SQL |
| Flexible product catalogue | NoSQL document database |
| Train seat bookings | SQL |
| Millions of smart meter readings | NoSQL |
| Fixed columns | SQL |
| Flexible JSON documents | NoSQL document database |
| Foreign keys and joins | SQL |
| Records with different fields | NoSQL document database |

These are typical choices, not absolute rules. SQL databases can support JSON data, and some NoSQL systems support transactions. The right choice depends on consistency requirements, query patterns, data shape, and scale. The full comparison is in [sql-vs-nosql.md](Day-05/sql-vs-nosql.md).

#### A4 — Canteen Database

The canteen schema contains:

| Table | Columns |
| --- | --- |
| `students` | `id`, `name`, `email` |
| `menu_items` | `id`, `name`, `price`, `is_veg` |
| `orders` | `id`, `student_id`, `order_date`, `total_price` |
| `order_items` | `id`, `order_id`, `menu_item_id`, `quantity` |

`orders.student_id` references `students.id`, so a student can place multiple orders. `order_items.order_id` references an order and `order_items.menu_item_id` references a menu item. Each order can contain multiple menu items, and a menu item can appear in many orders; `order_items` represents that many-to-many relationship and records the quantity.

For example, the key relationships can be declared as:

```sql
FOREIGN KEY (student_id) REFERENCES students(id);
FOREIGN KEY (order_id) REFERENCES orders(id);
FOREIGN KEY (menu_item_id) REFERENCES menu_items(id);
```

The complete schema, including data types and constraints, is in [canteen-database.sql](Day-05/canteen-database.sql).

### Part B — Library Database

The library schema contains:

| Table | Columns |
| --- | --- |
| `books` | `id`, `title`, `author` |
| `members` | `id`, `name`, `member_type` |
| `loans` | `id`, `book_id`, `member_id`, `borrow_date`, `due_date`, `return_date` |

Each loan references one book and one member. A book can appear in many loans over time, and a member can have many loans. The loan row records when the book was borrowed, when it is due, and—when returned—the return date. `return_date` can be `NULL` while the book is still on loan. The [library schema](Day-05/library-database.sql) includes the foreign keys and date constraints.

### Part C — Internship Application: College Event Portal

The internship application is a college event portal:

| Table | Columns |
| --- | --- |
| `users` | `id`, `name`, `email`, `password` (conceptual credential input) |
| `events` | `id`, `title`, `description`, `event_date`, `organizer_id` |
| `registrations` | `id`, `user_id`, `event_id`, `registration_date` |

A user can organize events, and each event's `organizer_id` refers to its organizer in `users`. Users register for events through `registrations`: each registration references one user and one event. The user-to-event many-to-many relationship is represented by registration rows. A unique constraint on `(user_id, event_id)` prevents duplicate registrations for the same event.

The actual SQL schema uses a `password_hash` column in `users`, not a plaintext `password` column. Applications must store password hashes rather than passwords. The complete [college event portal schema](Day-05/internship-app-database.sql) includes the primary keys, foreign keys, data types, and constraints.

### Day 05 Learning Outcomes

By completing Day 05, I learned to:

- Explain what a database is and distinguish tables, rows, and columns.
- Explore related sample tables and use SQL joins to answer questions.
- Identify primary keys and foreign keys and describe the relationships they enforce.
- Compare relational SQL databases and NoSQL document databases using common scenarios.
- Design tables and choose data types, uniqueness rules, checks, and foreign-key constraints.
- Model canteen orders, library loans, and event registrations using related tables.
- Store password hashes rather than plaintext passwords in an application schema.

The SQL scripts are MySQL 8.0.16+ schema examples; they do not configure or connect to a live database.

### Day 05 Practical Files

- [Database basics and sample lab results](Day-05/database-basics.md)
- [Keys and relationships](Day-05/keys-and-relationships.md)
- [SQL vs NoSQL](Day-05/sql-vs-nosql.md)
- [Canteen database schema](Day-05/canteen-database.sql)
- [Library database schema](Day-05/library-database.sql)
- [College event portal schema](Day-05/internship-app-database.sql)
- [Day 05 lab notes and completion details](Day-05/lab-notes.md)

### Day 05 Completion Details

- **Day:** 05
- **Title:** What Is a Database
- **Lab ID:** JFS-M3-01
- **Status:** Completed
- **Checks passed:** 6/6
- **Completion code:** `JFS-D05-66MJVQ`

## Learning Progress

| Day | Main learning |
| --- | --- |
| Day 01 | Application layers, browser DevTools, Java full-stack technologies, URLs, HTTP response codes, request flow, and CampusConnect planning. |
| Day 02 | Java data types, console input/output, conditional logic, validation, formatted calculations, and a console application outline. |
| Day 03 | HTML/CSS inspection, Network monitoring, status codes, the Console, DNS/HTTP, DOM/CSSOM, rendering, and API communication. |
| Day 04 | HTML document structure, links, accessible images, semantic elements, profile-page content, and the CampusEvents homepage. |
| Day 05 | Database tables, sample SQL queries and joins, primary and foreign keys, SQL vs NoSQL, and relational schema design. |

## Projects

### CampusConnect — Project Idea and Planning

CampusConnect is documented as a **proposed college event registration system**. The Day 01 planning document outlines intended users, features, pages, database entities, relationships, and basic workflows. The application itself is not present and is not claimed to be built.

**Planning document:** [CampusConnect project planning](Day-01/project-planning/campusconnect.md)

## Skills Developed

The current work supports the following introductory skills. Conceptual topics are identified separately from implemented code.

| Area | Skills evidenced by the completed work |
| --- | --- |
| **Frontend Development** | Explain the roles of HTML, CSS, and JavaScript; identify frontend responsibilities; inspect page structure and browser messages with DevTools; document semantic HTML pages, accessible images, and links. |
| **Backend Development** | Explain backend responsibilities and request handling at a conceptual level; write Java console programs using variables, input/output, and conditional logic. No Spring Boot service is implemented yet. |
| **Database Management** | Explain database tables and relationships, compare SQL and NoSQL, write MySQL-compatible schema examples with keys and constraints, and model canteen, library, and event-portal data. No live database is configured. |
| **Development Tools** | Use browser DevTools concepts and work with Java source files in this repository. Maven and IntelliJ IDEA are introduced in the Day 01 notes. |
| **Problem Solving** | Break down URLs and HTTP responses, trace request flow, validate input, apply calculation rules, and check Java exercises against sample cases. |

## Repository Structure

```text
.
├── README.md
├── Day-01/
│   ├── challenge/
│   │   ├── browser-request-explanation.md
│   │   └── url-breakdown.md
│   ├── concepts/
│   │   ├── browser-devtools.md
│   │   ├── frontend-backend-database.md
│   │   ├── http-status-codes.md
│   │   ├── java-full-stack-technologies.md
│   │   └── request-response-lifecycle.md
│   └── project-planning/
│       └── campusconnect.md
├── Day-04/
│   ├── a1-first-web-page.html
│   ├── a2-html-links.html
│   ├── a3-html-images.html
│   ├── a4-semantic-html.html
│   ├── index.html
│   └── personal-profile.html
├── Day-03/
├── Day-05/
│   ├── canteen-database.sql
│   ├── database-basics.md
│   ├── internship-app-database.sql
│   ├── keys-and-relationships.md
│   ├── lab-notes.md
│   ├── library-database.sql
│   └── sql-vs-nosql.md
└── day02/
    ├── CheckLab.java
    ├── FeeCalculator.java
    ├── Grade.java
    ├── Greeting.java
    ├── MyApp.java
    ├── Receipt.java
    └── Variables.java
```

Day 03, Day 04, and Day 05's complete written lab documentation is included above in this master README. The practical files are grouped in their day folders; there are no per-day README files.

## Learning Outcomes

Across the completed days, I have documented how the frontend, backend, and database relate; how to inspect HTML and CSS with browser DevTools; how to monitor network requests and interpret HTTP status codes; how to use the JavaScript Console; and how DNS, HTTP, DOM, CSSOM, browser rendering, and API requests fit together. Day 02 adds Java practice with data types, console interaction, conditionals, input checks, and formatted fee calculations. Day 04 adds foundational HTML, semantic page structure, accessible links and images, a profile page, and the CampusEvents homepage. The CampusConnect plan applies full-stack concepts to a proposed application without representing it as implemented. Day 05 adds relational table design, keys and relationships, SQL/NoSQL comparisons, and MySQL-compatible schemas for canteen, library, and college event portal data.

## Future Learning

Additional internship days will be documented directly in this master README as their work becomes available. The roadmap and learning progress sections will be updated for each completed day. No per-day README files are needed.

## Author

Author details are not currently listed in the repository documentation.
