# 🛒 E-Commerce Backend API

A fully functional E-Commerce Backend application developed using **Spring Boot**.  
It provides RESTful APIs for user authentication, product management, order management, and payment processing.

---

## 📌 Project Overview

This project serves as the backend service for an e-commerce web or mobile application.  
It follows clean architecture principles and uses industry-standard tools for authentication, data persistence, and application security.

---

## ✅ Features

- User Registration & Login with JWT Authentication
- Role-Based Authorization (User, Admin)
- Product Management (CRUD)
- Category Management
- Order Management
- Shopping Cart Functionality
- Inventory Tracking
- Payment Integration (Mock/Real Payment Gateway)
- Secure Password Encryption with BCrypt
- API Documentation using Swagger (Optional)

---

## ⚙️ Technology Stack

| Technology    | Version/Details       |
|---------------|----------------------|
| Java          | 17 or higher          |
| Spring Boot   | 3.x                   |
| Spring Security | JWT + BCrypt        |
| Spring Data JPA | Hibernate ORM       |
| Database      | MySQL / PostgreSQL    |
| Build Tool    | Maven                 |
| Lombok        | ✅                    |
| Swagger (Optional) | For API Docs    |

---

## 📂 Project Structure

```
src
├── main
│ ├── java
│ │ └── com.yourcompany.ecommerce
│ │ ├── config
│ │ ├── controller
│ │ ├── dto
│ │ ├── model
│ │ ├── repository
│ │ ├── service
│ └── resources
│ ├── application.properties
│ └── data.sql (optional)
├── test


```


---

## 🛠️ Setup Instructions

### Prerequisites:

- Java 17+
- Maven
- MySQL or PostgreSQL Database
- IDE (IntelliJ, VS Code, Eclipse)

---

### 1️⃣ Clone the Repository:

```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_jwt_secret_key
jwt.expirationMs=3600000



📖 API Endpoints
Endpoint	Method	Role	Description
/api/auth/register	POST	Public	Register new user
/api/auth/login	POST	Public	Login & get JWT token
/api/products	GET	Public	Get all products
/api/products	POST	Admin	Add new product
/api/products/{id}	PUT	Admin	Update product by ID
/api/products/{id}	DELETE	Admin	Delete product by ID
/api/orders	POST	User	Place a new order
/api/orders/user	GET	User	Get logged-in user orders
/api/orders/admin	GET	Admin	Get all orders

