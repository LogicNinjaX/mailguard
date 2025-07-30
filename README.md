# 📬 MailGuard – Consent-Based Email Preference API

MailGuard is a Spring Boot backend application that empowers users to **control which types of emails** they receive (e.g., marketing, updates, alerts). It enforces **email preference management** through a secure and consent-based system compliant with **GDPR/CCPA**.

---

## 🚀 Features

- 🔐 JWT-based Authentication
- ✅ Per-category Email Consent Control
- 📬 Secure Email Sending via Spring Mail
- 📄 OpenAPI (Swagger UI) Documentation
- 📁 Modular API Structure: `/preferences`, `/auth`, `/category`

---

## 📦 Tech Stack

| Layer         | Technology                        |
|---------------|------------------------------------|
| Language      | Java 21                            |
| Framework     | Spring Boot                        |
| Database      | MySQL + Spring Data JPA            |
| Auth          | Spring Security + JWT (JJWT)       |
| Validation    | Hibernate Validator                |
| Mapping       | MapStruct                          |
| API Docs      | springdoc-openapi (Swagger UI)     |
| Email         | Spring Boot Mail                   |
| Config        | Environment-based YAML Config      |

---

## 📁 Modules & API Overview

### 🔐 `/auth`
- `POST /auth/register` – User registration
- `POST /auth/login` – JWT token generation

### 📬 `/preferences`
- `GET /preferences` – Get current user's preferences
- `POST /preferences` – Update current user's preferences
- `POST /preferences` – Save user's preference

### 📨 `/email`
- `POST /admin/email` – Send email based on category **(checks consent)**

---

## 📑 Sample `.env` or application variables

```env
DB_URL=jdbc:mysql://localhost:3306/mailguard
DB_UNAME=root
DB_PASS=your_password

SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_UNAME=your_email@gmail.com
SMTP_PASS=your_app_password

JWT_SECRET=your_secret_key
JWT_EXP=86400000
```
---
## 🧱 Dependencies Used
✅ Core

  - spring-boot-starter-web

  - spring-boot-starter-data-jpa

  - spring-boot-starter-validation

  - mysql-connector-j

  - spring-boot-starter-security

✅ JWT Auth

  - io.jsonwebtoken:jjwt-api, impl, jackson

✅ Mail

  - spring-boot-starter-mail

✅ Mapping

  - org.mapstruct:mapstruct

  - mapstruct-processor

✅ Testing & Docs

  - spring-boot-starter-test

  - springdoc-openapi-starter-webmvc-ui

--- 

##  🧪 Running the App Locally

✅ Prerequisites

1. Java 21+
2. Maven 3+
3. MySQL running on port 3306
---
**Steps**

```bash
# 1. Clone the project
git clone git@github.com:LogicNinjaX/mailguard.git
cd mailguard

# 2. Add your application secrets in environment or .env file

# 3. Run using Maven
./mvnw spring-boot:run

Visit Swagger UI at: http://localhost:8080/swagger-ui.html
```
---
📂 Folder Structure (Simplified)

```bash
src/
├── config/
|   └── JwtConfig.java
|   └── OpenApiConfig.java
│   └── SecurityConfig.java
├── controller/
|   └── AdminController.java
│   └── AuthController.java
|   └── EmailCategoryController.java
│   └── UserController.java
│   └── UserPreference.java
├── dto/
|   └── request/
|   └── response/
├── entity/
│   └── User.java
│   └── EmailCategories.java
│   └── UserPreferences.java
│   └── UserProfile.java
├── enums/ # enums eg. UserRoles
├── exception/ # custom exceptions & exception handlers
├── mapper/ # entity/dto mappers
├── repository/
│   └── EmailCategoriesRepository.java
│   └── UserPreferencesRepository.java
|   └── UserProfileRepository.java
├── security/ # jwt &spring security classes
├── service/
│   └── AdminService.java
│   └── PreferenceService.java
│   └── EmailCategoriesService.java
|   └── EmailService.java
│   └── PreferenceService.java
|   └── UserProfileService.java
|   ├── util/ # application utility classes
└── MailguardApplication.java
```
---
## 💡 Future Improvements
- Audit Trail of User Consent Changes
- Add refresh token support
- Admin panel for managing categories
- Redis caching for frequent preference lookups

## Contributing 🤝💡

Contributions are welcome! Please fork the repository and submit a pull request.

## License 📜

This project is licensed under the **MIT License**.

---

Feel free to modify and expand as needed! 🚀🎉💡
