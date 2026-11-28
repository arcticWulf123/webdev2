-- Week 13 seed data for the Product Catalog (schema: infoman1_catalog).
--
-- Run order:
--   1. start the app once so Hibernate creates the tables
--   2. run this file
--
-- This script RESETS the three catalog tables before inserting, so it can be
-- re-run safely and always produces the same rows.
--
-- Each product's stock_quantity equals the sum of its stock_movements,
-- so products.stock_quantity is the cached total and stock_movements is the
-- ledger that explains it.

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE stock_movements;
TRUNCATE TABLE products;
TRUNCATE TABLE categories;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO categories (id, name, description) VALUES
    (1, 'Electronics',  'Devices and computer accessories'),
    (2, 'Office',       'Desk and workspace supplies'),
    (3, 'Accessories',  'Bags, cables and add-ons');

INSERT INTO products (id, name, sku, price, stock_quantity, status, category_id) VALUES
    (1, 'Wireless Mouse',      'SKU-1001',  25.50,  40, 'ACTIVE',       1),
    (2, 'Mechanical Keyboard', 'SKU-1002',  89.99,  15, 'ACTIVE',       1),
    (3, 'USB-C Hub',           'SKU-1003',  42.00,   0, 'ACTIVE',       1),
    (4, 'Monitor Stand',       'SKU-2001',  65.00,  12, 'ACTIVE',       2),
    (5, 'Desk Lamp',           'SKU-2002',  34.75,  22, 'ACTIVE',       2),
    (6, 'Ergonomic Chair',     'SKU-2003', 249.00,   5, 'ACTIVE',       2),
    (7, 'Laptop Sleeve',       'SKU-3001',  29.99,  60, 'ACTIVE',       3),
    (8, 'Braided Cable',       'SKU-3002',  12.50,  98, 'DISCONTINUED', 3);

INSERT INTO stock_movements (product_id, stock_change, reason, created_at) VALUES
    (1,   50, 'RESTOCK',    '2026-11-18 08:00:00.000000'),
    (1,  -10, 'SALE',       '2026-11-20 14:30:00.000000'),
    (2,   20, 'RESTOCK',    '2026-11-18 08:10:00.000000'),
    (2,   -5, 'SALE',       '2026-11-21 09:15:00.000000'),
    (3,   25, 'RESTOCK',    '2026-11-18 08:20:00.000000'),
    (3,  -25, 'SALE',       '2026-11-22 16:45:00.000000'),
    (4,   12, 'RESTOCK',    '2026-11-19 10:00:00.000000'),
    (5,   22, 'RESTOCK',    '2026-11-19 10:10:00.000000'),
    (6,    6, 'RESTOCK',    '2026-11-19 10:05:00.000000'),
    (6,   -1, 'DAMAGE',     '2026-11-23 11:20:00.000000'),
    (7,   60, 'RESTOCK',    '2026-11-20 07:45:00.000000'),
    (8,  100, 'RESTOCK',    '2026-11-17 09:00:00.000000'),
    (8,   -2, 'ADJUSTMENT', '2026-11-24 13:00:00.000000');
