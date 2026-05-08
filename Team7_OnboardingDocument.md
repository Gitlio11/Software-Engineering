# Team 7 — Onboarding Document
**Course:** Software Engineering  
**Team Members:** Emilio Munoz, Savannah Nelson, Yash Shah  
**Feature:** List and Visualize Department Research Profiles (Features 5 & 7)

---

## 1. Environment Setup

### Prerequisites
- Java JDK 11 or higher (`java -version` to verify)
- sbt (Scala Build Tool) — install via [https://www.scala-sbt.org/download.html](https://www.scala-sbt.org/download.html)
- IntelliJ IDEA (recommended) with the Scala plugin installed
- MySQL (for running with real data) — install via Homebrew: `brew install mysql`
- Git

### Clone the Repository
```
git clone https://github.com/Gitlio11/Software-Engineering.git
cd Software-Engineering
```

### Running the Backend
1. Open IntelliJ IDEA
2. Open the `backend/` folder as a project
3. In the sbt shell at the bottom, type: `run`
4. Backend starts at: `http://localhost:9037`

### Running the Frontend
1. Open IntelliJ IDEA
2. Open the `frontend/` folder as a separate project
3. In the sbt shell, type: `run`
4. Frontend starts at: `http://localhost:9036`

### Running Tests
In the backend sbt shell:
```
sbt test
```
To run a specific test:
```
testOnly controllers.DepartmentBrowserTest
testOnly controllers.DepartmentModelTest
```

---

## 2. Architecture Overview

The project follows a two-application Play Framework architecture:

```
Browser
  |
  v
Frontend (port 9036)
  controllers/  — receives HTTP request, calls backend API, passes data to view
  views/         — Scala HTML templates rendered and returned to browser
  conf/routes    — maps URLs to frontend controllers
  |
  v (REST API call)
Backend (port 9037)
  controllers/  — exposes REST endpoints, calls services
  services/     — business logic and database queries
  models/       — Java data classes (Ebean ORM entities)
  conf/routes   — maps URLs to backend controllers
  conf/evolutions/ — SQL schema and seed data
  |
  v
Database (MySQL or H2 in-memory)
```

---

## 3. Key Files for Team 7's Feature

| File | Location | Purpose |
|---|---|---|
| `DepartmentController.java` | `frontend/app/controllers/` | Calls backend API, renders views |
| `departments.scala.html` | `frontend/app/views/` | Department list page (search, sort, chart) |
| `departmentDetail.scala.html` | `frontend/app/views/` | Department detail page (faculty, projects) |
| `DepartmentController.java` | `backend/app/controllers/` | REST endpoints for list and detail |
| `DepartmentService.java` | `backend/app/services/` | SQL queries, data aggregation |
| `Department.java` | `backend/app/models/` | Department data model |
| `DepartmentBrowserTest.java` | `backend/test/controllers/` | End-to-end headless browser tests |
| `DepartmentModelTest.java` | `backend/test/controllers/` | Unit tests for Department model |
| `2.sql` | `backend/conf/evolutions/default/` | Seed data for local development |
| `routes` | `frontend/conf/` | URL mappings for department endpoints |

### Department Routes (frontend/conf/routes)
```
GET  /department/departmentList                    controllers.DepartmentController.departmentList()
GET  /department/departmentListData                controllers.DepartmentController.departmentListData()
GET  /department/departmentDetail/:departmentName  controllers.DepartmentController.departmentDetail(departmentName: String)
```

---

## 4. Database Configuration

### Option A: H2 In-Memory (default, no setup needed)
The app runs out of the box using H2 in-memory database. Seed data is automatically applied from `backend/conf/evolutions/default/2.sql` on startup. No configuration needed.

### Option B: Real MySQL Database (shows actual SMU faculty data)
1. Start MySQL: `brew services start mysql`
2. Create the database and import the teacher's backup:
```
mysql -u root -e "CREATE DATABASE scihub;"
mysql -u root scihub < db_backup/20250904-082908.sql
```
3. Create the file `backend/conf/application.local.conf` with:
```
db.default.driver   = com.mysql.cj.jdbc.Driver
db.default.url      = "jdbc:mysql://localhost:3306/scihub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
db.default.username = "root"
db.default.password = ""
play.evolutions.enabled = false
```
4. Restart the backend — it will now show real SMU professors

> Note: `application.local.conf` is gitignored and should never be committed.

---

## 5. Three Existing Controllers (Code Exploration)

### 1. FacultyProfileController (backend)
- **File:** `backend/app/controllers/FacultyProfileController.java`
- **What it does:** Returns a list of researcher profiles from the database. Queries `researcher_info` joined with `user` and returns JSON with name, department, school, and research fields.
- **Key endpoint:** `GET /faculty/facultyList`

### 2. OrganizationController (backend)
- **File:** `backend/app/controllers/OrganizationController.java`
- **What it does:** Manages research organizations on the platform. Supports listing, creating, and retrieving organization profiles including their associated projects and members.
- **Key endpoint:** `GET /organization/organizationList`

### 3. PaperController (backend)
- **File:** `backend/app/controllers/PaperController.java`
- **What it does:** Handles research paper data — searching papers, retrieving paper details, and linking papers to authors. Uses the `paper`, `author`, and `author_paper` tables.
- **Key endpoint:** `GET /paper/searchPaper`

---

## 6. Play Framework Tech Stack

| Module | Purpose |
|---|---|
| `play.filters` | CORS, CSRF, security headers |
| `javaJdbc` | Database connection pooling |
| `ebean` | ORM for database queries |
| `play.libs.Json` | JSON serialization/deserialization |
| `com.mysql:mysql-connector-j` | MySQL JDBC driver |
| `com.h2database:h2` | H2 in-memory database for testing |
| `play.test.WithBrowser` | Headless browser (HtmlUnit) for E2E tests |
| Scala HTML templates | Server-side rendering via Play views |

---

## 7. Team Setup Status

| Team Member | Cloned Repo | App Running Locally | Tests Passing |
|---|---|---|---|
| Emilio Munoz | Yes | Yes | Yes |
| Savannah Nelson | TBD | TBD | TBD |
| Yash Shah | TBD | TBD | TBD |

---

## 8. Test Execution Report

### DepartmentModelTest (Unit Test — existing model)
Tests the `Department` model class and its nested `DepartmentFaculty` and `DepartmentProject` classes with no database or Play app required.

| Test | Result |
|---|---|
| `department_gettersAndSetters_workCorrectly` | PASS |
| `department_topKeywords_storeAndRetrieveList` | PASS |
| `department_facultyList_storesCorrectly` | PASS |
| `department_projectList_storesCorrectly` | PASS |
| `department_defaultConstructor_fieldsAreZeroOrNull` | PASS |

### DepartmentBrowserTest (End-to-End Headless Browser Test)
Spins up a real Play test server with H2 in-memory database and simulates browser requests.

| Test | Result |
|---|---|
| `departmentList_pageContainsDepartmentName` | PASS |
| `departmentList_responseIncludesFacultyCount` | PASS |
| `departmentDetail_pageContainsDepartmentName` | PASS |
| `departmentDetail_pageContainsFacultyData` | PASS |
| `departmentDetail_unknownDepartmentReturnsError` | PASS |

**Total: 10 tests, 0 failures, 0 errors**
