-- Empties the three catalog tables without inserting anything.
--
-- Use this before running the Postman/Newman collection: the collection
-- assumes it starts with an empty catalog, because it creates fixed names
-- ("Electronics") and deletes fixed ids. Run docs/week13/seed.sql afterwards
-- to restore the demo rows.
--
-- Run it the same way as seed.sql:
--   mysql -h 127.0.0.1 -P 3307 -u root -p infoman1_catalog
--   mysql> source docs/week13/reset.sql

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE stock_movements;
TRUNCATE TABLE products;
TRUNCATE TABLE categories;
SET FOREIGN_KEY_CHECKS = 1;
