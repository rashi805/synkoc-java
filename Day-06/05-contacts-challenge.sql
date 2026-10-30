-- Part B: Create a contacts table.
CREATE TABLE contacts (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    email TEXT UNIQUE,
    city TEXT
);

-- Fictional examples: example.com and 555-01xx numbers are reserved for examples.
INSERT INTO contacts (name, phone, email, city)
VALUES
    ('Asha Nair', '555-0101', 'asha.nair@example.com', 'Bengaluru'),
    ('Rohan Mehta', '555-0102', 'rohan.mehta@example.com', 'Mysuru'),
    ('Meera Iyer', '555-0103', 'meera.iyer@example.com', 'Bengaluru'),
    ('Kabir Shah', '555-0104', 'kabir.shah@example.com', 'Pune'),
    ('Nisha Rao', '555-0105', 'nisha.rao@example.com', 'Chennai');

-- Show the names and phone numbers of Bengaluru contacts alphabetically.
SELECT name, phone
FROM contacts
WHERE city = 'Bengaluru'
ORDER BY name ASC;
