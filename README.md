WealthFlow – Investment Portfolio Manager

WealthFlow is a full-stack investment portfolio management platform that allows users to buy, sell, and track stock investments in real time. The application integrates live market data and provides dynamic portfolio updates with secure authentication.

Features

User registration and login with JWT authentication

OTP-based email verification

Search and invest in stocks using Finnhub Stock API

Buy and sell stocks with portfolio tracking

Real-time portfolio updates using WebSockets

RESTful backend APIs built with Spring Boot

Fully deployed full-stack application

Tech Stack

Backend

Java

Spring Boot

Spring Security

Spring Data JPA

PostgreSQL

Apache Kafka (local architecture for event streaming)

WebSockets

Frontend

React

Vite

Axios

Infrastructure

Backend: Railway

Frontend: Vercel

Database: Neon PostgreSQL

Architecture Overview

Frontend (React)
⬇
Backend API (Spring Boot)
⬇
PostgreSQL Database (Neon)
⬇
Real-time updates via WebSockets
⬇
Email OTP via Resend API

Live Demo

Frontend:
https://wealth-flow-full-stack.vercel.app

Backend API:
https://trustworthy-blessing-production.up.railway.app

Testing Instructions
Demo Login

You can use the demo account below to explore the dashboard:

Email:
demo@wealthflow.com

Password:
demo123

OTP Verification

Due to domain verification limitations of the email service used in the demo deployment, all OTP emails are routed to the developer mailbox.

When testing account registration or verification:

Enter any email during registration.

The OTP will be sent to:

pranavmishra9807@gmail.com

The email will include both the user email and OTP, allowing the developer to provide the verification code if needed.

Example email format:

User Email: pranavmishra9807@gmail.com

OTP: 123456

This setup ensures the OTP verification system can still be demonstrated without requiring a verified email domain.

Project Highlights

Implemented secure authentication using JWT and OTP verification

Designed real-time portfolio updates using WebSockets

Integrated external financial APIs for live stock prices

Built scalable backend services using Spring Boot and JPA

Implemented event-driven architecture using Apache Kafka (local setup)

Future Improvements

Kafka-based streaming deployment for live market updates

Multi-user real-time stock alerts

Portfolio analytics and performance charts

Verified email domain for direct OTP delivery
