# SQL vs NoSQL

## Relational SQL Databases

Relational databases organize structured information in tables with defined columns and relationships. SQL (Structured Query Language) is used to define tables and to query or update their data. Primary keys, foreign keys, constraints, and joins help preserve and work with related records.

Relational SQL databases are often a suitable choice when an application needs consistent structure, relationships, and transactions—for example, financial transfers or seat reservations where related changes must remain consistent.

## NoSQL Document Databases

NoSQL is a broad group of database approaches that do not all use the same model. A document database stores records as documents, commonly JSON-like objects. Documents in a collection can have different fields, which can suit data whose shape varies or changes frequently.

Document databases are often suitable for flexible product attributes or large volumes of readings with evolving or varying fields. Data design, query patterns, consistency needs, and operational requirements still matter.

## Day 05 Scenario Choices

| Lab scenario | Typical choice | Reason |
| --- | --- | --- |
| Bank balances and transfers | SQL | Transactions and constraints help keep related balance changes consistent. |
| Flexible product catalogue | NoSQL document database | Product categories can have different attributes. |
| Train seat bookings | SQL | Structured reservations and transactions help prevent conflicting bookings. |
| Millions of smart meter readings | NoSQL | A scalable store may suit high-volume readings and varying metadata. |
| Fixed columns | SQL | A defined table schema describes the expected fields. |
| Flexible JSON documents | NoSQL document database | Documents can carry different fields and nested values. |
| Foreign keys and joins | SQL | Relational databases directly support these relationships and queries. |
| Records with different fields | NoSQL document database | Documents can vary without requiring every record to have identical fields. |

These are **typical choices, not absolute rules**. SQL databases can store flexible JSON data, and NoSQL systems can support transactions or structured relationships. Choose based on the application's consistency, query, scale, and data-shape requirements rather than treating either category as universally better.
