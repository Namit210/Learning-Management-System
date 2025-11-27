# JWT Authentication Guide

## Overview
JWT (JSON Web Token) authentication has been implemented in this Spring Boot application. All endpoints except `/auth/register` and `/auth/login` now require authentication.

## Setup

### 1. Dependencies Added
- Spring Security
- JWT (io.jsonwebtoken) libraries

### 2. Configuration
JWT secret and expiration time are configured in `application.properties`:
```properties
jwt.secret=mySecretKeyForJWTTokenGenerationWhichShouldBeLongEnoughForHS256AlgorithmToWorkProperly
jwt.expiration=86400000  # 24 hours in milliseconds
```

## API Endpoints

### Authentication Endpoints (Public - No Authentication Required)

#### 1. Register a New User
```bash
POST http://localhost:8888/auth/register

Request Body:
{
  "nickname": "john_doe",
  "name": "John Doe",
  "age": 25,
  "phone": "1234567890",
  "address": "123 Main St",
  "password": "password123",
  "roles": ["USER"]  // Optional, defaults to ["USER"]
}

Response:
{
  "token": null,
  "nickname": "john_doe",
  "message": "User registered successfully"
}
```

#### 2. Login
```bash
POST http://localhost:8888/auth/login

Request Body:
{
  "nickname": "john_doe",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "nickname": "john_doe",
  "message": "Login successful"
}
```

#### 3. Validate Token
```bash
GET http://localhost:8888/auth/validate

Headers:
Authorization: Bearer <your-jwt-token>

Response:
{
  "token": null,
  "nickname": "john_doe",
  "message": "Token is valid"
}
```

### Protected Endpoints (Authentication Required)

All other endpoints require a valid JWT token in the Authorization header:

```bash
Headers:
Authorization: Bearer <your-jwt-token>
```

#### Example: Get All Courses
```bash
GET http://localhost:8888/courses

Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## User Roles

- **USER**: Can access GET endpoints and enroll in courses
- **ADMIN**: Can perform all CRUD operations (POST, PUT, DELETE)

### Endpoint Permissions

#### User Controller
- `POST /join` - Public
- `GET /users` - USER, ADMIN
- `GET /find/{nickname}` - USER, ADMIN
- `PUT /enroll` - USER, ADMIN
- `GET /getcourses` - USER, ADMIN

#### Course Controller
- `GET /courses` - USER, ADMIN
- `POST /courses` - ADMIN only
- `PUT /courses` - ADMIN only
- `DELETE /courses` - ADMIN only
- `GET /courses/modules` - USER, ADMIN
- `POST /courses/modules` - ADMIN only
- `PUT /courses/modules/{mno}` - ADMIN only
- `DELETE /courses/modules/{mno}` - ADMIN only

#### Assignment Controller
- `GET /assignments/` - USER, ADMIN
- `GET /assignments/{index}` - USER, ADMIN
- `POST /assignments/` - ADMIN only

## Testing with Postman/cURL

### 1. Register a User
```bash
curl -X POST http://localhost:8888/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "testuser",
    "name": "Test User",
    "age": 30,
    "password": "test123",
    "roles": ["USER"]
  }'
```

### 2. Login and Get Token
```bash
curl -X POST http://localhost:8888/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "testuser",
    "password": "test123"
  }'
```

### 3. Use Token to Access Protected Endpoint
```bash
curl -X GET http://localhost:8888/courses \
  -H "Authorization: Bearer <your-token-here>"
```

## Security Features

1. **Password Encryption**: All passwords are encrypted using BCrypt
2. **Stateless Authentication**: JWT tokens enable stateless authentication
3. **Token Expiration**: Tokens expire after 24 hours (configurable)
4. **Role-Based Access Control**: Different roles have different permissions
5. **CORS Configuration**: Already configured for frontend at http://localhost:5173

## Database Changes

The `User` model has been updated with:
- `password` field (String) - Encrypted password
- `roles` field (Set<String>) - User roles (e.g., "USER", "ADMIN")

## Important Notes

1. **Existing Users**: Existing users in your database don't have passwords. They need to re-register.
2. **Token Storage**: Store the JWT token securely on the client side (e.g., localStorage, sessionStorage)
3. **Token Refresh**: Current implementation doesn't include token refresh. Token expires after 24 hours.
4. **CORS**: Update the `@CrossOrigin` annotation if your frontend runs on a different port.

## Creating an Admin User

To create an admin user, register with the "ADMIN" role:
```json
{
  "nickname": "admin",
  "name": "Admin User",
  "password": "admin123",
  "roles": ["ADMIN", "USER"]
}
```
