# Pawtify

A web application for pet and product management built with Spring Boot. It features a shopping cart, user history, registration and login modules, a contact form, and modern layered architecture. Security is handled via Spring Security, and the system uses modular Thymeleaf templates and static resources.

## 🚀 Tech Stack
- **Backend:** Java 17+, Spring Boot, Spring Security, Maven
- **Frontend:** Thymeleaf, CSS, JS
- **Persistence:** SQL (DB.sql), assumed JPA
- **DevOps:** Maven, Git

## 🏗️ Architecture
[User] → [Controllers] → [Services] → [Persistence (SQL/JPA)] → [Database]


## 📦 Main Modules
- **Pet Management:** Registration, forms, listing
- **Product Management:** CRUD, shopping cart, product listing, editing
- **User History:** Tracks actions or purchases
- **User Registration & Security:** Registration, login, role-based access control
- **Contact:** User contact form
- **Error Handling & Layouts:** Custom 404, centralized layouts

## 🔑 Key Flows
- User registration and login
- Pet management (register, edit, list)
- Shopping cart operations (add, edit, checkout)
- User history viewing
- Sending messages via contact form
- Custom error handling

## 🛠️ Best Practices
- Clear layered architecture (MVC)
- DRY Thymeleaf templates and modular fragments
- Unified security configuration
- Well-organized static resources
- Ready for CI/CD with Maven and Git

## ▶️ Quick Start
1. Requirements: Java 17+, Maven, compatible database
2. Configure your database connection in `application.properties`
3. Build and run:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
    or run the `.jar` in `target/`
4. Open [http://localhost:8080](http://localhost:8080) in your browser

---
Developed following modern engineering standards.  
Stack: Java, Spring Boot, Thymeleaf, Maven.
