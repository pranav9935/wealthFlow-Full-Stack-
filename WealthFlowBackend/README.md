# WealthManager – Portfolio Management System

## Overview
WealthManager is a Spring Boot based backend application designed to manage investment portfolios, users, and asset tracking.

## Tech Stack
- Java 24
- Spring Boot
- Spring Security (JWT)
- Hibernate / JPA
- MySQL
- Swagger (OpenAPI)
- Maven

## Features
- User Registration & Login
- JWT Authentication
- Role-Based Authorization
- Portfolio Creation
- Investment Tracking
- REST APIs with Swagger documentation

## Architecture
Layered Architecture:
Controller → Service → Repository

## How to Run
1. Clone repository
2. Configure database in `application.yml`
3. Run:
   mvn spring-boot:run

## Future Enhancements
- Redis caching
- Kafka event logging
- Docker deployment
- AWS hosting