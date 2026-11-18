# Lab 11 — Answer Template

**Course:** Web Development 2 &nbsp;|&nbsp; **Week:** 11 &nbsp;|&nbsp; **Lab:** Capstone Milestone 3

> **Instructions:** Fill in each section with links to your code and screenshots/recordings. Do not paste full file contents here — link to the file in your repository instead. Submit your GitHub repository URL to Moodle.

---

## Student Information

| Field | Value |
| --- | --- |
| Name | Jade Roland Eduard C. Pinon |
| Student ID | 2511036 |
| GitHub Repository URL | <https://github.com/arcticWulf123/webdev2> |
| Branch (if not `main`) | week11 |

---

## Task 1 — Input Validation on DTOs

**DTO files with annotations** *(link to files in your repo)*:

- `CreateDepartmentRequest.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/CreateDepartmentRequest.java
- `UpdateDepartmentRequest.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/UpdateDepartmentRequest.java

**Evidence of 400 response for missing field:**

> Paste the JSON response body you received when you sent a POST with a missing required field:

```json
{
  "timestamp":"2026-11-18T02:52:05.023Z",
  "status":400,
  "error":"Bad Request",
  "path":"/api/departments"
}
```

---

## Task 2 — Global Exception Handler

**Files created:**

- `GlobalExceptionHandler.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/GlobalExceptionHandler.java
- `ErrorResponse.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/ErrorResponse.java
- `ResourceNotFoundException.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/ResourceNotFoundException.java

**Evidence — 404 response:**

> Paste the response body for a GET request to a non-existent ID:

```json
{
  "status": 404,
  "message": "Department not found with id: 99999",
  "errors": null,
  "timestamp": "2026-11-18T11:02:00.337398300Z"
}
```

**Evidence — 400 response with field errors:**

> Paste the response body for a POST with an invalid body, showing the `errors` array:

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "name",
      "message": "Name cannot be blank"
    }
  ],
  "timestamp": "2026-11-18T11:03:27.961865800Z"
}
```

---

## Task 3 — CORS Configuration

**File created:**

- `WebConfig.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/WebConfig.java

**Evidence — CORS headers present:**

> Paste the curl output or describe the browser Network tab result showing `Access-Control-Allow-Origin`:

```
curl.exe -s -i -X OPTIONS http://localhost:8080/api/departments -H "Origin: http://localhost:3000" -H "Access-Control-Request-Method: POST"

HTTP/1.1 200
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET,POST,PUT,PATCH,DELETE,OPTIONS
Access-Control-Allow-Credentials: true
Access-Control-Max-Age: 1800
Allow: GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH
Content-Length: 0
```

---

## Task 4 — Pagination and Filtering

**Files updated:**

- `DepartmentRepository.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/DepartmentRepository.java
- `DepartmentService.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/DepartmentService.java
- `DepartmentController.java` — https://github.com/arcticWulf123/webdev2/blob/week11/src/main/java/com/week10/activity/DepartmentController.java

**Evidence — paginated response:**

> Paste the JSON response for `GET /api/departments?page=0&size=5&sort=name,asc`:

```json
{
  "content": [
    {"id": 11, "name": "Engineering"},
    {"id": 8, "name": "Finance"},
    {"id": 7, "name": "HR"},
    {"id": 1, "name": "IT"},
    {"id": 9, "name": "Marketing"}
  ],
  "empty": false,
  "first": true,
  "last": false,
  "number": 0,
  "numberOfElements": 5,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "size": 5,
  "totalElements": 7,
  "totalPages": 2
}
```

**Evidence — filtered response:**

> Paste the JSON response for `GET /api/departments?name=it`:

```json
{
  "content": [
    {"id": 1, "name": "IT"}
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "unpaged": false
  },
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

**Evidence — max size cap:**

> What response did you get when you requested `?size=999`? What was the actual page size in the metadata?

Requesting `?size=999` returned `"size": 100` and `"pageable": { "pageSize": 100 }`, so the service capped the requested 999 at 100. `totalElements` stayed 7 because only 7 rows exist.

---

## Task 5 — Documentation and Test Collection

**Files committed:**

- `API.md` — https://github.com/arcticWulf123/webdev2/blob/week11/API.md — repository root
- `api_tests.json` — https://github.com/arcticWulf123/webdev2/blob/week11/api_tests.json — repository root

**Test collection run evidence:**

> Paste a screenshot link or describe the test run result (e.g., "5/5 requests passed"):

`screenshots/collection_tests.png` — https://raw.githubusercontent.com/arcticWulf123/webdev2/week11/screenshots/collection_tests.png

10/10 requests passed, 21/21 assertions passed, 0 failures.

```
npx newman run api_tests.json --reporters cli

requests:     10 executed, 0 failed
test-scripts: 10 executed, 0 failed
assertions:   21 executed, 0 failed
total run duration: 1.07s

All 10 requests passed:
  Create department (happy path)      POST   201
  Get department by id                GET    200
  Update department (PUT)             PUT    200
  Patch department (PATCH)            PATCH  200
  List departments (paginated)        GET    200
  Filter departments by name          GET    200
  List departments (size cap 100)     GET    200
  Create department (missing name)    POST   400
  Get non-existent department         GET    404
  Delete department (cleanup)         DELETE 204
```

**List the requests in your collection:**

| Request name | Method | Path | Expected status |
| --- | --- | --- | --- |
| Create department (happy path) | POST | `/api/departments` | 201 |
| Get department by id | GET | `/api/departments/{{createdId}}` | 200 |
| Update department (PUT) | PUT | `/api/departments/{{createdId}}` | 200 |
| Patch department (PATCH) | PATCH | `/api/departments/{{createdId}}` | 200 |
| List departments (paginated) | GET | `/api/departments?page=0&size=5&sort=name,asc` | 200 |
| Filter departments by name | GET | `/api/departments?name=Research` | 200 |
| List departments (size cap 100) | GET | `/api/departments?size=999` | 200 |
| Create department (missing name) | POST | `/api/departments` | 400 |
| Get non-existent department | GET | `/api/departments/99999` | 404 |
| Delete department (cleanup) | DELETE | `/api/departments/{{createdId}}` | 204 |

---

## Reflection

> In 2–3 sentences: which of the five topics from Week 11 had the biggest impact on your capstone API? What surprised you most when implementing it?

The global exception handler had the biggest impact on the API. Before it, every controller method wrapped its logic in `try/catch`, and duplicate names were detected by comparing exception message strings, which was fragile and duplicated in three places. Moving that into a single `@RestControllerAdvice` with typed exceptions (`ResourceNotFoundException`, `DepartmentAlreadyExistsException`) made the controllers thin and gave every error the same JSON shape. What surprised me most was pagination: Spring Data builds `totalElements`, `totalPages` and the sorting metadata automatically once the service returns `Page<DTO>`, so the only real work was capping `size` at 100 and mapping the page of entities to DTOs.
