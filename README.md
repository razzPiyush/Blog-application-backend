# Blogging Application

A comprehensive backend for a blogging platform built with Spring Boot, implementing user authentication, role-based access, and CRUD functionalities for posts, comments, and categories.

## Features
- **User Authentication**: Secure JWT-based login.
- **Role Management**: ADMIN and USER roles.
- **Post Management**: CRUD operations for blog posts.
- **Comment Management**: Add, edit, or delete comments.
- **Category Management**: Organize posts by categories.
- **Exception Handling**: Global exception handling for seamless user experience.

## Technology Stack
- **Backend**: Java, Spring Boot
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **API Documentation**: Swagger

## Project Structure
- `controllers/`: Handle HTTP requests.
- `services/`: Business logic for entities.
- `repositories/`: Interface for database operations.
- `security/`: JWT authentication and authorization.
- `entities/`: Define database models.
- `payloads/`: DTOs for data transfer.
- `exceptions/`: Custom exception handling.

## How to Run
1. Clone the repository.
2. Configure `application.properties` for database connection.
3. Build and run the application using `mvn spring-boot:run`.

## API Endpoints
- User Management: `/api/users`
- Authentication: `/api/auth`
- Posts: `/api/posts`
- Comments: `/api/comments`
- Categories: `/api/categories`

## Author
Developed by Piyush Raj.
