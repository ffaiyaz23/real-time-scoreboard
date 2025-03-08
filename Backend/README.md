# Real-Time Leaderboard Backend

This repository contains the backend implementation for a real-time leaderboard service built using a microservices architecture with Java, Spring Boot, Redis, JWT, Docker, and AWS services (EC2, RDS, ElastiCache). The system is composed of multiple independent microservices that are registered with a Eureka Server and are accessed via an API Gateway.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Services](#services)
  - [Eureka Discovery Server](#eureka-discovery-server)
  - [User Service](#user-service)
  - [Leaderboard Service](#leaderboard-service)
  - [Score History Service](#score-history-service)
  - [API Gateway Service](#api-gateway-service)
- [Security](#security)
- [Setup and Running the Application](#setup-and-running-the-application)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [License](#license)
- [Contributing](#contributing)
- [Contact](#contact)

## Overview

The Real-Time Leaderboard backend system enables users to register, log in, submit scores for games, view real-time leaderboards, and access historical score data. Each microservice is built independently and communicates with one another via Eureka and the API Gateway. The services use JWT for authentication, Redis for real-time score management, and PostgreSQL for persistent storage.

## Architecture

The system follows a microservices architecture with the following components:

- **Eureka Discovery Server**: A central registry where all microservices register themselves.
- **User Service**: Handles user registration, authentication, and JWT token issuance. Persists user data in PostgreSQL.
- **Leaderboard Service**: Accepts score submissions and maintains real-time leaderboards using Redis sorted sets.
- **Score History Service**: Persists every score record in PostgreSQL and provides endpoints for retrieving historical data and reporting.
- **API Gateway Service**: A single entry point that routes client requests to the appropriate microservice based on URL patterns. It also enforces security through a global JWT filter.

## Technologies

- **Java & Spring Boot**: Core framework for building microservices.
- **Spring Cloud Netflix Eureka**: Service registry for microservices.
- **Spring Cloud Gateway**: API Gateway for routing and cross-cutting concerns.
- **Spring Data JPA**: Persistence layer for relational databases (PostgreSQL).
- **Redis**: In-memory datastore for real-time leaderboards.
- **JWT (JSON Web Tokens)**: For authentication and secure communications.
- **Docker**: Containerization for local development and deployment.
- **AWS (EC2, RDS, ElastiCache)**: Production deployment targets.

## Services

### Eureka Discovery Server

- **Responsibilities**: Acts as a central registry for all microservices.
- **Usage**: All services register with Eureka and use it for load balancing and service discovery.
- **URL**: Typically runs on `http://localhost:8761`

### User Service

- **Responsibilities**:
  - Manage user registration and login.
  - Generate JWT tokens for authenticated users.
  - Store user data (username, email, password hash, etc.) in PostgreSQL.
- **Key Endpoints**:
  - `POST /users/register`
  - `POST /users/login`
  - `GET /users/me` (secured via JWT)
- **Notes**: Uses a shared JWT secret for token generation and validation. The JWT secret is stored in `application.properties`.

### Leaderboard Service

- **Responsibilities**:
  - Accept score submissions and update a real-time leaderboard using Redis sorted sets.
  - Provide endpoints to query top scores and individual user ranks.
- **Key Endpoints**:
  - `POST /leaderboards/{gameId}/score`
  - `GET /leaderboards/{gameId}?limit=N`
  - `GET /leaderboards/{gameId}/rank/{userId}`
- **Notes**: Validates JWT tokens, and uses Redis to store and manage scores. It uses the same JWT secret as the User Service.

### Score History Service

- **Responsibilities**:
  - Persist each score record (with fields: id, user_id, game_id, score, timestamp) in PostgreSQL.
  - Provide endpoints for retrieving a user’s score history and aggregated top player data.
- **Key Endpoints**:
  - `POST /score-history/record`
  - `GET /score-history/user/{userId}`
  - `GET /score-history/top-players?startDate=&endDate=`
- **Notes**: Uses Spring Data JPA for persistence. Also uses the shared JWT secret for securing endpoints.

### API Gateway Service

- **Responsibilities**:
  - Serve as the single entry point for external clients.
  - Route requests to User Service, Leaderboard Service, and Score History Service via load-balanced URIs using Eureka.
  - Enforce security through a global JWT filter.
- **Configuration**: Routes are defined in the gateway’s configuration (e.g., `/users/**`, `/leaderboards/**`, `/score-history/**`).
- **Notes**: Uses Spring Cloud Gateway and registers with Eureka.

## Security

Each microservice (and the gateway) uses JWT for authentication. The JWT secret is stored as a property (`jwt.secret`) in the `application.properties` file of each service. The same secret must be used across services to ensure that a token generated by the User Service is valid in the Leaderboard Service, Score History Service, and Gateway.

## Setup and Running the Application

### Prerequisites

- **Java 17 or higher**
- **Gradle**
- **Docker** (for running Redis, PostgreSQL, etc., locally)
- **IntelliJ IDEA** (or your favorite IDE)
- **PostgreSQL**: Create databases for user-service, leaderboard-service, and score-history-service.
- **Redis**: Can be run locally using Docker:
  ```bash
  docker run --name redis -p 6379:6379 redis
