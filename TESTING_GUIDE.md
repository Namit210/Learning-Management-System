# JWT Authentication Testing Examples

## Using cURL

### 1. Register a new user
```bash
curl -X POST http://localhost:8888/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "testuser",
    "name": "Test User",
    "age": 25,
    "phone": "1234567890",
    "address": "Test Address",
    "password": "password123",
    "roles": ["USER"]
  }'
```

### 2. Register an admin user
```bash
curl -X POST http://localhost:8888/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "admin",
    "name": "Admin User",
    "age": 30,
    "phone": "9876543210",
    "address": "Admin Address",
    "password": "admin123",
    "roles": ["ADMIN", "USER"]
  }'
```

### 3. Login and get JWT token
```bash
curl -X POST http://localhost:8888/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "testuser",
    "password": "password123"
  }'
```

### 4. Access protected endpoint (replace TOKEN with actual token from login)
```bash
curl -X GET http://localhost:8888/courses \
  -H "Authorization: Bearer TOKEN"
```

### 5. Validate token
```bash
curl -X GET http://localhost:8888/auth/validate \
  -H "Authorization: Bearer TOKEN"
```

## Using Postman

### Setup
1. Create a new collection for your API
2. Set a collection variable `baseUrl` = `http://localhost:8888`
3. Set a collection variable `token` (will be set dynamically)

### Test Workflow

**Step 1: Register**
- Method: POST
- URL: `{{baseUrl}}/auth/register`
- Body (JSON):
```json
{
  "nickname": "john",
  "name": "John Doe",
  "age": 28,
  "password": "john123",
  "roles": ["USER"]
}
```

**Step 2: Login**
- Method: POST
- URL: `{{baseUrl}}/auth/login`
- Body (JSON):
```json
{
  "nickname": "john",
  "password": "john123"
}
```
- Tests (save token automatically):
```javascript
var jsonData = pm.response.json();
pm.collectionVariables.set("token", jsonData.token);
```

**Step 3: Access Protected Endpoints**
- Method: GET
- URL: `{{baseUrl}}/courses`
- Headers:
  - Key: `Authorization`
  - Value: `Bearer {{token}}`

## Common Response Status Codes

- **200 OK**: Successful request
- **201 Created**: User registered successfully
- **400 Bad Request**: User already exists or invalid data
- **401 Unauthorized**: Invalid credentials or token
- **403 Forbidden**: User doesn't have required role
- **500 Internal Server Error**: Server error

## Testing Different Scenarios

### 1. Test USER role permissions
Login as a regular user and try:
- ✅ GET /courses (should work)
- ❌ POST /courses (should fail - needs ADMIN role)

### 2. Test ADMIN role permissions
Login as an admin and try:
- ✅ GET /courses (should work)
- ✅ POST /courses (should work)

### 3. Test without token
Try accessing protected endpoint without Authorization header:
- ❌ Should return 401 Unauthorized

### 4. Test with expired/invalid token
- ❌ Should return 401 Unauthorized
