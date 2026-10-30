# Frontend, Backend, and Database

An online shopping website is a useful way to understand the three main parts of a web application:

- **Frontend:** the part a person sees and uses in the browser.
- **Backend:** the server-side part that applies rules, checks requests, and coordinates work.
- **Database:** the organized storage used to keep information between requests.

The frontend can ask the backend to do something, and the backend can read from or write to the database. The database stores information; it does not decide on its own how the shopping rules work.

## Shopping Website Components

| # | Component | Layer | Why it belongs there |
| --- | --- | --- | --- |
| 1 | Add to cart button and page layout | Frontend | The button, its placement, and the visible page are part of the browser interface a shopper interacts with. The button can send an add-to-cart request to the backend. |
| 2 | Product photos shown on the page | Frontend | Displaying and arranging product images is part of presenting the page. The image files may be delivered by a server or content service, but their display is frontend work. |
| 3 | Search box | Frontend | The input field and search controls are visible interface elements. Searching for matching products is usually handled by the backend after the frontend sends the search text. |
| 4 | Email required validation while typing | Frontend | Immediate feedback while typing improves the form experience and runs in the browser. The backend must still validate submitted data because browser-side checks can be bypassed. |
| 5 | Checking whether an item is in stock | Backend | The server applies the shopping rule using current stock information, so it can make a trusted decision when processing a request. |
| 6 | Calculating cart total with GST | Backend | Pricing and tax calculations are business rules that should be applied consistently on the server rather than trusted to a browser. |
| 7 | Verifying password at login | Backend | The server checks the login attempt against stored password-hash information. A password should not be verified by frontend code alone. |
| 8 | Applying coupon code | Backend | The server checks coupon rules, validity, and eligibility and calculates the resulting discount. |
| 9 | List of products and prices | Database | Product and price records need persistent, organized storage. The backend reads these records to serve product information. |
| 10 | Order history | Database | Past orders must be stored so they can be retrieved later for a shopper or an administrator. |
| 11 | User accounts, names, emails, and password hashes | Database | Account records need persistent storage. Passwords should be stored as secure hashes, not as plain text. |
| 12 | Stock count for each product | Database | Inventory quantities are persistent product data. The backend reads and updates them when orders are processed. |

## How the Layers Work Together

When a shopper selects **Add to cart**, the frontend sends a request to the backend. The backend checks the requested product and current stock, retrieves the product information from the database, and applies the relevant rules. It then returns a result that the frontend can show to the shopper.

These are responsibilities, not necessarily separate physical machines. For example, the frontend may receive product data from the backend, but the data is still stored in the database and rendered by the frontend.

