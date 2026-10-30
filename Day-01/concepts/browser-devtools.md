# Browser Developer Tools (DevTools)

## What Is DevTools?

Developer Tools, usually called **DevTools**, are built-in browser panels for inspecting and diagnosing webpages. They let developers examine the page structure and styles, see messages from webpage scripts, and investigate problems without guessing what the browser received.

## Opening DevTools

1. Open the webpage in a browser such as Chrome or Edge.
2. Press **F12**. On some laptops, you may need **Fn + F12**.
3. If that shortcut is unavailable, open the browser menu and choose **More tools → Developer tools**.
4. Select the **Elements** or **Console** tab as needed.

Browser names and menus can differ slightly, but the same basic inspection ideas apply.

## Elements Tab: HTML Structure

The **Elements** tab displays the page's current HTML structure, often as a nested tree. HTML elements are the building blocks of a page:

```html
<button type="button" data-level="...">Register</button>
```

In this example, `<button>` is the element, and `Register` is its text. An element's opening tag, content, and closing tag together make up an HTML element.

### HTML Attributes

Attributes are name-and-value details written inside an element's opening tag. In the example above, `type` and `data-level` are attributes. The value following `data-level=` belongs to the particular element. A custom attribute whose name begins with `data-` can be used to attach application-specific data to an HTML element.

The SYNKOC Browser Lab may give each learner a unique `data-level` value. Inspect the element specified by your own lab and record the value shown there. This guide deliberately does not provide or guess it.

### HTML Comments

An HTML comment has this form:

```html
<!-- This is a comment in the HTML source. -->
```

Comments are not displayed as normal page text. They may be visible in the Elements panel if present in the page markup. A developer can use comments to leave notes in source code, but comments should not be treated as secret storage.

## Console Tab

The **Console** tab displays messages from the browser and webpage scripts. Common messages include:

- **Errors**, which may point to a script or resource that failed.
- **Warnings**, which flag something developers may want to review.
- **Informational messages** written by the webpage or browser.

Some labs ask learners to inspect a message or enter a lab-provided console code. Follow the instructions shown in your actual SYNKOC lab and use only its unique **secret word** and **console code**. They are not included or invented in this document. Do not enter code from an untrusted source into a browser console.

## How Developers Debug a Webpage

Debugging means finding and understanding the cause of a problem. A developer can reproduce the issue, inspect the relevant element and its attributes in **Elements**, then check **Console** for related errors or messages. After making a code change, the developer tests the page again to see whether the behavior is correct. DevTools helps investigate a page; it does not automatically explain or fix every problem.

## Personal Lab Checklist

- [ ] Open the official SYNKOC Browser Lab and follow its instructions.
- [ ] Inspect the element identified by the lab and record its actual `data-level` value.
- [ ] Find the lab's actual secret word and console code as directed.
- [ ] Keep these personal values out of shared notes unless the lab explicitly asks you to submit them.

