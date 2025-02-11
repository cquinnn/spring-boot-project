# Blog App Web Application
## Introduction
This project is a Blog App web application developed using Java, Spring Boot, JWT Authentication, and MySQL. It provides users with functionalities to create, read, update, and delete blog posts, as well as authentication and authorization features to secure the application.

## Technologies
Java: Programming language used for backend development.
Spring Boot: Framework for building Java-based web applications.
Spring Security: Provides authentication, authorization, and other security features.
JWT (JSON Web Tokens): Used for authentication by generating and verifying tokens.
MySQL: Relational database management system for storing application data.

## Features (in progress)
- User Registration: Allows users to create new accounts with unique usernames and passwords.
- User Authentication: Secure authentication using JWT tokens.
- Authorization: Restricts access to certain functionalities based on user roles (e.g., admin, regular user).
- Password Encryption: Safely stores user passwords using encryption techniques.
- Media Attachment: Enables users to create, read, update, and delete their media.
- Blog Post Management: Enables users to create, read, update, and delete their blog posts.

## Architecture -
The application follows a standard client-server architecture:
- **Backend (Server)**: Developed using Java and Spring Boot.
- **Database**: MySQL is used to store blog posts, user information, and other data.

## Modules -
The key modules of the application include:
- **Authentication Module**: Implements user registration, login, and JWT token generation.
- 