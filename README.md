# spring-ecommerce-order

This project is the continuation of the previous ecommerce-product mission.
In this step, the codebase will integrate Spring Data JPA for database interactions, replacing the previous JDBC-based implementation.

## Previous Mission – spring-ecommerce-product
### Step 1-1 — Basic Product API (In-Memory Storage)
- Implemented HTTP API for CRUD operations on products.
- Stored data in memory using Kotlin MutableMap.
- JSON request/response format.

### Step 1-2 — Admin Interface with Thymeleaf
- Server-Side Rendering (SSR) with Thymeleaf templates.
- Admin can view, add, update, delete products via HTML forms.

### Step 1-3 — Persistence with H2 Database & JdbcTemplate
- Replaced in-memory storage with H2 in-memory database.
- SQL scripts for schema creation and initial data.
- Centralized exception handling.

### Step 2-1 — Product Validation
- Validation rules for product name, price, and image URL.
- Unique product name check.
- Structured error responses.

### Step 2-2 — Member Authentication (JWT)
- Member registration and login.
- JWT token generation and validation.
- Custom argument resolver for authenticated users.

### Step 2-3 — Shopping Cart
- Cart created automatically on user registration.
- Add, update, remove products in the cart.
- Separate controllers for guests, members, and admins.

### Step 2-4 — Admin Statistics
- Top 5 most-added products in last 30 days.
- Recently active members (last 7 days).
- Admin-only endpoints.

# Current Mission – spring-ecommerce-order
## STEP 1-1 - Entity Mapping - Entity Mapping

### Features
- [x] Refactor existing codebase from spring-ecommerce-product from JdbcTemplate to use Spring Data JPA.
- [x] Model real domain objects and map them to database tables using JPA annotations
- [x] Write learning tests using @DataJpaTest.
- [x] objects should use references to navigate relationships like:
  ```kotlin
  val question = findQuestionById(questionId)
  val answers = question.answers
  ```
