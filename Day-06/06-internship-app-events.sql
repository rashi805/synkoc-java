-- Part C: College Event Portal events table.
CREATE TABLE events (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT,
    event_date TEXT NOT NULL,
    organizer_id INTEGER
);

-- Fictional college event examples.
INSERT INTO events (title, description, event_date, organizer_id)
VALUES
    ('Campus Coding Sprint', 'A friendly team programming challenge.', '2026-11-08', 1),
    ('Cloud Computing Workshop', 'An introductory workshop on cloud services.', '2026-11-12', 2),
    ('Student Design Showcase', 'Students present interface and product design work.', '2026-11-16', 3),
    ('Data Science Guest Talk', 'A talk about practical data science projects.', '2026-11-20', 1),
    ('Annual Tech Expo', 'A campus exhibition of student technology projects.', '2026-11-24', 2);

-- Show selected events; this date filter returns some, but not all, sample rows.
SELECT id, title, event_date
FROM events
WHERE event_date >= '2026-11-10';

-- List all events in date order (ISO YYYY-MM-DD dates sort chronologically).
SELECT id, title, event_date
FROM events
ORDER BY event_date ASC;
