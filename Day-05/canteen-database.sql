-- Canteen schema example; MySQL 8.0.16+ syntax.
CREATE TABLE students (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_students_email UNIQUE (email)
) ENGINE = InnoDB;

CREATE TABLE menu_items (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    is_veg BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_menu_items_price CHECK (price >= 0)
) ENGINE = InnoDB;

CREATE TABLE orders (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    student_id INT UNSIGNED NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_orders_total_price CHECK (total_price >= 0),
    CONSTRAINT fk_orders_student
        FOREIGN KEY (student_id) REFERENCES students(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = InnoDB;

CREATE TABLE order_items (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    order_id INT UNSIGNED NOT NULL,
    menu_item_id INT UNSIGNED NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_order_items_order_menu UNIQUE (order_id, menu_item_id),
    CONSTRAINT chk_order_items_quantity CHECK (quantity > 0),
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_order_items_menu_item
        FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = InnoDB;
