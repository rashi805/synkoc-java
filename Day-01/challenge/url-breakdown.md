# URL Breakdown

Consider this URL:

```text
https://shop.example.com/products/shoes?size=9&color=black
```

| Part | Value | What it means |
| --- | --- | --- |
| Protocol (scheme) | `https` | Tells the browser to use HTTP protected by TLS encryption. |
| Domain (host) | `shop.example.com` | The human-readable host name the browser looks up to locate the server. |
| Path | `/products/shoes` | Identifies the requested resource or route on that host. |
| Query string | `size=9&color=black` | Supplies optional key-value parameters to the request. The `?` marks where the query string begins; `&` separates parameters. |

The full address is the URL. In everyday conversation, people may call the host name the website's domain; technically, the host can also include a subdomain such as `shop`.

## Additional Examples

### A page with no query string

```text
https://example.com/about
```

- Protocol: `https`
- Host: `example.com`
- Path: `/about`
- Query string: none

### A search URL with two parameters

```text
https://shop.example.com/search?q=backpack&page=2
```

- Protocol: `https`
- Host: `shop.example.com`
- Path: `/search`
- Query string: `q=backpack&page=2`
- Parameters: `q` has the value `backpack`, and `page` has the value `2`.

Query-string values are inputs, not proof that the server has accepted or acted on them. The application decides how to validate and use them.

