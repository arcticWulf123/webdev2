# Product Catalog API — Postman collection

`product-catalog.postman_collection.json` covers every endpoint plus the error paths
(400, 404, 409), pagination and category filtering.

## Import

1. Postman → **Import** → select `docs/product-catalog.postman_collection.json`.
2. The collection defines `baseUrl` = `http://localhost:8080`. Change it in the collection
   **Variables** tab if your app runs elsewhere.

## Run

The collection creates its own category and three products and deletes them at the end,
so it needs an **empty database**. H2 is in-memory and resets on every restart, so start
the app first and run the collection immediately:

```powershell
.\mvnw.cmd spring-boot:run
```

Then either press **Run** on the collection in Postman, or use Newman:

```powershell
npx newman run docs/product-catalog.postman_collection.json --env-var baseUrl=http://localhost:8080
```

Expected: all requests pass, 0 failures.

## What it proves

| Folder | Endpoints |
|---|---|
| 1. Categories | POST (201 + Location), GET list, GET by id, PUT, duplicate name (409), missing id (404) |
| 2. Products | POST (201), paginated list with `page/size/sort`, filter by `categoryId`, GET by id, PUT, invalid price (400), invalid SKU (400), missing id (404), delete guarded category (409) |
| 3. Cleanup | DELETE products (204), DELETE category (204), GET deleted product (404) |
