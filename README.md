# 🛒 EcomSoft — E-Commerce Backend API

A production-ready **Spring Boot** REST API backend for an e-commerce platform, featuring JWT-based authentication, role-based access control, product/category/order management, file uploads, and a real-time dashboard endpoint.

---

## 📌 Project Overview

**EcomSoft** is the backend service powering a full-stack e-commerce web application. It exposes clean RESTful APIs consumed by a React (Vite) frontend running on `http://localhost:5173`. The backend follows a layered architecture (Controller → Service → Repository) and uses Spring Security with stateless JWT authentication.

- **Group ID:** `com.rrecom`
- **Artifact ID:** `ecomsoft`
- **Base URL:** `http://localhost:9091/api/v1.0`
- **Context Path:** `/api/v1.0`
- **Server Port:** `9091`

---

## ✅ Implemented Features

| Feature | Details |
|---|---|
| 🔐 JWT Authentication | Login returns a signed JWT token |
| 👤 Role-Based Authorization | `ROLE_USER` and `ROLE_ADMIN` |
| 🧾 User Management | Register, list, delete users (Admin only) |
| 📦 Product (Item) Management | Add with image upload, list, delete |
| 🗂️ Category Management | Add with image upload, list, delete |
| 🛍️ Order Management | Create, delete, get latest orders |
| 📊 Dashboard API | Today's sales, order count, recent orders |
| 🖼️ Static File Serving | Uploaded images served from `/upload/` |
| 🔒 BCrypt Password Encoding | Secure password hashing |
| 🌐 CORS Support | Configured for frontend URL via env variable |
| 🐳 Docker Support | Multi-stage Dockerfile included |

---

## ⚙️ Technology Stack

| Technology | Version / Details |
|---|---|
| **Java** | 21 |
| **Spring Boot** | 3.4.5 |
| **Spring Security** | JWT + BCrypt |
| **Spring Data JPA** | Hibernate ORM |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Lombok** | 1.18.38 |
| **JWT Library** | JJWT (0.9.1 + 0.11.5) |
| **JAXB API** | 2.3.1 (Java 11+ compatibility) |
| **Docker** | Multi-stage build (eclipse-temurin:21) |

---

## 📂 Project Structure

```
ecomsoft/
├── src/
│   ├── main/
│   │   ├── java/com/rrecom/ecomsoft/
│   │   │   ├── EcomsoftApplication.java        # Spring Boot entry point
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java          # JWT filter chain, CORS, BCrypt
│   │   │   │   └── StaticResourseConfig.java    # Static file serving config
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java          # Login & password encode
│   │   │   │   ├── UserController.java          # Admin: register, list, delete users
│   │   │   │   ├── CategoryController.java      # Category CRUD + image upload
│   │   │   │   ├── ItemController.java          # Item CRUD + image upload
│   │   │   │   ├── OrderController.java         # Order create, delete, latest
│   │   │   │   └── DashBoardController.java     # Dashboard stats
│   │   │   ├── entity/
│   │   │   │   ├── UserEntity.java
│   │   │   │   ├── CategoryEntity.java
│   │   │   │   ├── ItemEntity.java
│   │   │   │   ├── OrderEntity.java
│   │   │   │   └── OrderItemEntity.java
│   │   │   ├── filter/
│   │   │   │   └── JwtRequestFilter.java        # JWT validation filter
│   │   │   ├── io/                              # DTOs (Request/Response records)
│   │   │   │   ├── AuthRequest / AuthResponse
│   │   │   │   ├── UserRequest / UserResponse
│   │   │   │   ├── CategoryRequest / CategoryResponse
│   │   │   │   ├── ItemRequest / ItemResponse
│   │   │   │   ├── OrderRequest / OrderResponse
│   │   │   │   ├── DashBoardResponse
│   │   │   │   ├── PaymentDetails
│   │   │   │   └── PaymentMethod
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   ├── ItemRepository.java
│   │   │   │   ├── OrderEntityRepository.java
│   │   │   │   └── OrderItemRepository.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   ├── ItemService.java
│   │   │   │   └── OrderService.java
│   │   │   └── service/imp/
│   │   │       ├── AppUserDeatilsService.java   # UserDetailsService impl
│   │   │       ├── UserServiceImpl.java
│   │   │       ├── CategoryServiceImp.java
│   │   │       ├── ItemServiceImp.java
│   │   │       └── OrderServiceImp.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── upload/                                      # Runtime image upload directory
├── Dockerfile
├── .env                                         # Local environment variables
├── .gitignore
├── pom.xml
└── mvnw / mvnw.cmd
```

---

## 🔐 Security & Authorization

The API uses **stateless JWT authentication**. All protected routes require a valid `Bearer` token in the `Authorization` header.

| Route Pattern | Access |
|---|---|
| `POST /login` | Public |
| `POST /encode` | Public |
| `/upload/**` | Public (static files) |
| `GET /categories`, `GET /items`, `GET /orders`, `GET /dashboard` | USER or ADMIN |
| `/admin/**` | ADMIN only |

---

## 📖 API Endpoints

> Base path: `/api/v1.0`

### 🔑 Auth
| Endpoint | Method | Access | Description |
|---|---|---|---|
| `/login` | POST | Public | Login with email & password, returns JWT + role |
| `/encode` | POST | Public | BCrypt-encode a raw password |

### 👤 Users (Admin)
| Endpoint | Method | Access | Description |
|---|---|---|---|
| `/admin/register` | POST | ADMIN | Register a new user |
| `/admin/users` | GET | ADMIN | List all users |
| `/admin/users/{id}` | DELETE | ADMIN | Delete a user by ID |

### 🗂️ Categories
| Endpoint | Method | Access | Description |
|---|---|---|---|
| `/categories` | GET | USER/ADMIN | List all categories |
| `/admin/categories` | POST | ADMIN | Add category with image (multipart) |
| `/admin/categories/{categoryId}` | DELETE | ADMIN | Delete category by ID |

### 📦 Items (Products)
| Endpoint | Method | Access | Description |
|---|---|---|---|
| `/items` | GET | USER/ADMIN | List all items |
| `/admin/items` | POST | ADMIN | Add item with image (multipart) |
| `/admin/items/{itemId}` | DELETE | ADMIN | Delete item by ID |

### 🛍️ Orders
| Endpoint | Method | Access | Description |
|---|---|---|---|
| `/orders` | POST | USER/ADMIN | Create a new order |
| `/orders/{orderId}` | DELETE | USER/ADMIN | Delete an order |
| `/orders/latest` | GET | USER/ADMIN | Get latest orders |

### 📊 Dashboard
| Endpoint | Method | Access | Description |
|---|---|---|---|
| `/dashboard` | GET | USER/ADMIN | Today's sales, order count & recent orders |

---

## 🛠️ Setup & Installation

### Prerequisites

- Java 21+
- Maven 3.9+
- MySQL 8+
- Docker (optional)

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Rupeswarbhainsa777/Ecommerce-Backend.git
cd ecomsoft
```

### 2️⃣ Configure Environment Variables

Create a `.env` file in the project root (already in `.gitignore`):

```env
DATASOURCE_URL=jdbc:mysql://localhost:3306/ecom
DATASOURCE_USER=root
DATASOURCE_PASSWORD=your_password
FRONTEND_URL=http://localhost:5173
```

### 3️⃣ Create the MySQL Database

```sql
CREATE DATABASE ecom;
```

Spring Boot will auto-create all tables via `ddl-auto=update`.

### 4️⃣ Run the Application

```bash
./mvnw spring-boot:run
```

The API will be available at: **`http://localhost:9091/api/v1.0`**

---

## 🐳 Docker

Build and run with Docker:

```bash
# Build the image
docker build -t ecomsoft .

# Run the container
docker run -p 9091:9091 \
  -e DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/ecom \
  -e DATASOURCE_USER=root \
  -e DATASOURCE_PASSWORD=your_password \
  -e FRONTEND_URL=http://localhost:5173 \
  ecomsoft
```

---

## 📝 Sample Login Request

```bash
POST /api/v1.0/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "yourpassword"
}
```

**Response:**
```json
{
  "email": "admin@example.com",
  "token": "<JWT_TOKEN>",
  "role": "ROLE_ADMIN"
}
```

Use the token in subsequent requests:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🌐 CORS Configuration

CORS is dynamically configured via the `FRONTEND_URL` environment variable. Allowed methods: `GET, POST, PUT, DELETE, OPTIONS`. Credentials are allowed.

---

## 📁 File Uploads

Product and category images are stored under the local `upload/` directory and served as static resources at `/upload/{filename}`.

---

## 👨‍💻 Author

**Rupeswar Bhainsa**  
GitHub: [@Rupeswarbhainsa777](https://github.com/Rupeswarbhainsa777)
