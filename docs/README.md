# Product Catalog API — Postman collection

`product-catalog.postman_collection.json` covers every endpoint plus the error paths
(400, 404, 409), pagination and category filtering.

## Import

1. Postman → **Import** → select `docs/product-catalog.postman_collection.json`.
2. The collection defines `baseUrl` = `http://localhost:8080`. Change it in the collection
   **Variables** tab if your app runs elsewhere.

## Run

The collection creates its own category and three products and deletes them at the end,
so it needs an **empty catalog**. The project persists to MySQL now, which does not reset
on restart, so empty the three tables first:

```sql
-- in an interactive mysql client connected to infoman1_catalog
source docs/week13/reset.sql
```

Start the app:

```powershell
$env:DB_PASSWORD="<your-password>"
.\mvnw.cmd spring-boot:run
```

Then either press **Run** on the collection in Postman, or use Newman:

```powershell
npx newman run docs/product-catalog.postman_collection.json --env-var baseUrl=http://localhost:8080
```

Expected: 22 requests, 51 assertions, 0 failures.

Restore the demo rows afterwards with `source docs/week13/seed.sql`.

## Week 13 documents

| File | Contents |
| --- | --- |
| `docs/week13/lecture.md` | Baseline audit, architecture and data flow, endpoint contract table, origins/CORS/DTO boundaries, one DevTools diagnosis |
| `docs/week13/checkpoint.md` | Requirement → feature → test table, setup commands, versions, test log, known defects, change log |
| `docs/week13/seed.sql` | 3 categories, 8 products, 13 stock movements (resets the tables first) |
| `docs/week13/reset.sql` | Empties the three tables, for running this collection |
| `docs/week13/evidence/` | Screenshots: list, detail, Network, SQL cross-check, empty state, not-found |

## What it proves

| Folder | Endpoints |
|---|---|
| 1. Categories | POST (201 + Location), GET list, GET by id, PUT, duplicate name (409), missing id (404) |
| 2. Products | POST (201), paginated list with `page/size/sort`, filter by `categoryId`, GET by id, PUT, invalid price (400), invalid SKU (400), missing id (404), delete guarded category (409) |
| 3. Cleanup | DELETE products (204), DELETE category (204), GET deleted product (404) |
