# Week 13 checkpoint — F1: Connected Foundation

- **Repository:** https://github.com/arcticWulf123/webdev2
- **Branch:** `week13`, created from `midterm` (`d3f5911`)
- **Application root:** repository root. `pom.xml`, `mvnw.cmd` and the frontend in
  `src/main/resources/static/` are all at the top level.
- **Persistence:** MySQL 9.7.2, schema `infoman1_catalog` (H2 remains test-scope
  only)

Branch base note: `week13` was branched from `midterm` rather than `week11`.
The Week 10–11 Department API was superseded by the Product Catalog API built
for the midterm practical, and the Product Catalog is the approved final project
domain. `week11` is untouched as a historical checkpoint.

---

## 1. Requirements → feature → test

| # | Requirement | Feature / file | Test | Result |
| --- | --- | --- | --- | --- |
| R1 | Product list renders rows from the API's paged response | `static/js/app.js` — `loadProducts()`, `renderRows()`; reads `page.content` | Open `/`, count rows against `totalElements` | Pass — 5 rows, "Showing 5 of 8 products." |
| R2 | Paging is driven by the server's page metadata | `static/js/app.js` — `renderPager()`, `prev`/`next` handlers | Click Next/Previous, check `Page n of 2` and disabled states | Pass — page 2 shows 3 rows, Next disabled |
| R3 | Category filter loads from the non-paged category endpoint | `static/js/app.js` — `loadCategories()`; `Array.isArray` guard | Open the dropdown, compare with `GET /api/categories` | Pass — 3 options plus "All categories" |
| R4 | Detail view loads a single record by id | `static/js/app.js` — `loadProduct()`, `renderDetail()` | Click **View**, compare fields with `GET /api/products/{id}` | Pass — matches for id 1 and id 6 |
| R5 | A missing id produces a clear not-found result, distinct from a transport failure | `static/js/api.js` — `ApiError` keeps `status`; `app.js` branches on `404` | Enter `9999`, press Load | Pass — "No product exists with ID 9999.", no Retry button |
| R6 | Loading, empty and failed-request states are visible and safe | `app.js` — `setListStatus()` with `loading` / `empty` / `error` kinds; `retry` button; `textContent` only | Office filter emptied of products; app stopped | Pass — empty message and "Cannot reach the server…" both render; no `innerHTML` in the source |
| R7 | Three related domain tables exist in MySQL with enforced relationships | `model/StockMovement.java`, `model/StockMovementReason.java`; `docs/week13/seed.sql` | `SHOW CREATE TABLE stock_movements`; FK-constrained delete attempt | Pass — FK to `products(id)`; deleting a referenced product is refused |
| R8 | Frontend is served over HTTP from the same origin as the API, with the base URL as a setting | `static/index.html`, `static/js/config.js` (`window.API_BASE = ""`) | Load `http://localhost:8080/`, check no CORS preflight in Network | Pass — no `OPTIONS` request, no `Access-Control-*` headers |

Existing behaviour preserved: the Postman collection still runs green
(22 requests, 51 assertions, 0 failures) after the database migration.

---

## 2. Setup and run

### Prerequisites

- JDK 26, MySQL 9.7 running on `127.0.0.1:3307`
- Maven wrapper is included — no local Maven install needed

### 1. Create the schema (once)

```powershell
& "C:\Program Files\MySQL\MySQL Server 9.7\bin\mysql.exe" -h 127.0.0.1 -P 3307 -u root -p -e "CREATE DATABASE IF NOT EXISTS infoman1_catalog CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;"
```

### 2. Configure credentials

Credentials are read from environment variables; nothing secret is committed.
See `application.properties.example` for the shape.

```powershell
$env:DB_URL      = "jdbc:mysql://127.0.0.1:3307/infoman1_catalog?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USER     = "root"
$env:DB_PASSWORD = "<your-password>"
```

### 3. Start the application

```powershell
.\mvnw.cmd spring-boot:run
```

Hibernate creates `categories`, `products` and `stock_movements` on first
startup (`ddl-auto=update`).

### 4. Load the demo data

In an interactive client — `source` works here, but not inside `-e`:

```powershell
& "C:\Program Files\MySQL\MySQL Server 9.7\bin\mysql.exe" -h 127.0.0.1 -P 3307 -u root -p infoman1_catalog
```

```sql
source docs/week13/seed.sql
```

Expected: 3 categories, 8 products, 13 stock movements.

### 5. Open the interface

<http://localhost:8080/>

### 6. Run the API contract tests

The collection assumes an **empty** catalog, because it creates fixed names and
deletes fixed ids. Empty it first, then restore the demo data:

```sql
source docs/week13/reset.sql
```

```powershell
npx --yes newman run docs/product-catalog.postman_collection.json --env-var baseUrl=http://localhost:8080
```

Expected: 22 requests, 51 assertions, 0 failures.

```sql
source docs/week13/seed.sql
```

---

## 3. Versions actually used

| Component | Version |
| --- | --- |
| JDK | 26.0.1 |
| Spring Boot | 4.1.1 |
| Spring Framework / Spring Data JPA | managed by Spring Boot 4.1.1 |
| Hibernate ORM | 7.4.5.Final |
| MySQL Server | 9.7.2 |
| MySQL Connector/J | 9.7.0 |
| H2 | 2.4.240 — `test` scope only |
| Maven Wrapper | 3.9.9 |
| Newman (via `npx`) | 6.2.3 |
| Browser used for evidence | Chrome, 1920×1080 |

---

## 4. Database schema

```sql
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `sku` varchar(20) NOT NULL,
  `status` enum('ACTIVE','DISCONTINUED') NOT NULL,
  `stock_quantity` int NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfhmd06dsmj6k0n90swsh8ie9g` (`sku`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9`
    FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `stock_movements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `reason` enum('ADJUSTMENT','DAMAGE','RESTOCK','SALE') NOT NULL,
  `stock_change` int NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKjcaag8ogfjxpwmqypi1wfdaog`
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

Constraints that carry meaning:

- `categories.name` unique — no duplicate categories.
- `products.sku` unique — no duplicate stock-keeping units.
- `products.category_id` NOT NULL + FK — a product always belongs to a category.
- `stock_movements.product_id` NOT NULL + FK — a movement always references a
  real product, which is why a product with history cannot be deleted by hand
  without handling the ledger first.

Seed consistency check (returns no rows when correct):

```sql
SELECT p.id, p.name, p.stock_quantity, SUM(m.stock_change) AS ledger_total
FROM products p
LEFT JOIN stock_movements m ON m.product_id = p.id
GROUP BY p.id, p.name, p.stock_quantity
HAVING p.stock_quantity <> COALESCE(SUM(m.stock_change), 0);
```

The join-based SQL view required by the INFOMAN1 track is **not** in this branch
yet — see section 8.

---

## 5. Test log — actual vs expected

| Test | Expected | Actual | Status |
| --- | --- | --- | --- |
| `GET /api/products?page=0&size=5` | `content` length 5, `totalElements` 8, `totalPages` 2 | 5 / 8 / 2 | Pass |
| `GET /api/products?page=1&size=5` | 3 rows, `first:false`, `last:true` | 3 / false / true | Pass |
| `GET /api/products?categoryId=999` | `200` with `content: []`, `totalPages: 0` | 200, empty array | Pass |
| `GET /api/products/9999` | `404` + `ErrorResponse` message | `{"status":404,"message":"Product not found with id: 9999",...}` | Pass |
| `GET /api/categories` | raw array of 3 categories | 3 objects | Pass |
| `/` and `/css`, `/js` assets | all `200` from port 8080 | 200 / 200 / 200 / 200 / 200 | Pass |
| Browser row vs API JSON | identical values | Wireless Mouse, SKU-1001, 25.50, 40, ACTIVE | Pass |
| API JSON vs SQL for id 6 | identical values | Ergonomic Chair, SKU-2003, 249.00, 5, ACTIVE, Office | Pass |
| Pager at both ends | Next disabled on last page, Previous on first | as expected | Pass |
| Empty filter (Office emptied) | empty state, pager "Page 0 of 0" | as expected | Pass |
| Server stopped, Retry clicked | connection-failure message, Retry visible | "Cannot reach the server. Is the Spring Boot app running?" | Pass |
| Postman collection on empty catalog | 51 assertions, 0 failures | 51 / 0 | Pass |
| Postman collection on seeded catalog | — | 34 failures: fixed names and ids collide with seed data | Expected, documented — use `reset.sql` first |
| Data survives an application restart | rows still present | 3 categories after restart | Pass |

---

## 6. Evidence

All screenshots are in `docs/week13/evidence/`.

| File | Content |
| --- | --- |
| `01-list.png` | Product list, "Showing 5 of 8 products.", Page 1 of 2, Previous disabled |
| `02-detail.png` | Detail panel for Wireless Mouse (id 1) loaded from the API |
| `03-network.png` | DevTools Network → Response for the list request, showing the `content` array |
| `04-sql-crosscheck.png` | MySQL row for id 6 beside `GET /api/products/6`, with `git branch` showing `week13` |
| `05-empty.png` | Office category emptied, "No products match this filter.", Page 0 of 0 |
| `06-not-found.png` | "No product exists with ID 9999." from a real `404` |

`04-sql-crosscheck.png` compares the API and SQL for **product 6**, while the
browser pane in that capture shows the list on page 1 (product 1). The
browser↔API match is covered by `01-list.png` + `03-network.png`, and the
API↔SQL match by `04`. A single three-way capture for one id is the cleaner
form of this evidence; see section 7.

---

## 7. Known defects and limitations

| # | Item | Impact | Plan |
| --- | --- | --- | --- |
| D1 | Postman collection fails against a seeded catalog (409 on fixed names, 400/404 on fixed ids) | Contract test is not runnable without a reset | Documented; `docs/week13/reset.sql` added. Making the collection seed-independent is a week 16 test task |
| D2 | `stock_movements` has no HTTP surface | Third table is not reachable through the API yet | Week 14 — atomic stock adjustment endpoint |
| D3 | Product delete is not yet blocked when movements exist | A manual `DELETE` on a product with history fails at the DB level with a 500-class error rather than a clean 409 | Week 14, together with D2 |
| D4 | Pager has no "jump to page" and no page-size control | Cosmetic; not required by the milestone | Not planned |
| D5 | Prices are formatted as USD in the UI | Display-only; values are correct | Not planned |
| D6 | `ddl-auto=update` manages the schema | Fine for coursework; not a migration strategy | Week 17 release configuration |
| D7 | `04-sql-crosscheck.png` does not show the same product in all three panes | Slightly weaker single-image evidence | Retake with product 6 open in the detail panel |
| D8 | INFOMAN1 `week13/` folder not created yet | Dual-course deliverable incomplete | Next, after the WEBDEV2 submission |

---

## 8. INFOMAN1 link (dual-enrolled)

This branch is the shared application for both courses. The INFOMAN1 week 13
folder is not created yet; when it is, it will live at
`infoman1/week13/` in the INFOMAN1 repository and link back to
<https://github.com/arcticWulf123/webdev2/tree/week13>.

Still owed on that side:

- `schema.sql` — the three tables and constraints from section 4
- `seed.sql` — reusable from `docs/week13/seed.sql`
- `view.sql` — the join-based view (for example product, category, current
  stock and ledger total), not yet written

---

## 9. Change log

| Commit | Change |
| --- | --- |
| `chore: switch datasource to MySQL` | `mysql-connector-j` added, H2 moved to test scope, datasource pointed at `infoman1_catalog` via env placeholders, `application.properties.example` added |
| `feat: add stock movements table and seed data` | `StockMovement`, `StockMovementReason`, `docs/week13/seed.sql`, `docs/week13/reset.sql` |
| `feat: vanilla JS product browser` | `static/index.html`, `css/styles.css`, `js/config.js`, `js/api.js`, `js/app.js` |
| `docs: week13 lecture, checkpoint and evidence` | `docs/week13/lecture.md`, this checkpoint, six evidence screenshots |

Verified after every stage with `.\mvnw.cmd clean compile` and a live browser
check; no stage left the application unable to start.
