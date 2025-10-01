# API Documentation

## Base URL

- Development: `http://localhost:8080/api`
- Production: Configure based on your deployment

## Authentication

All authenticated endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer {your-jwt-token}
```

## Default Test Accounts

For development/testing purposes, the following accounts are automatically created:

1. **Admin Account**
   - Username: `admin`
   - Password: `admin123`
   - Roles: ADMIN
   - Permissions: create_user, edit_user, view_patient_detail, create_patient

2. **Employee Account**
   - Username: `employee`
   - Password: `employee123`
   - Roles: EMPLOYEE
   - Permissions: view_patient_detail, create_patient

## Endpoints

### Public Endpoints

#### Health Check
```http
GET /api/public/health
```

**Response:**
```
200 OK
"Clinic Backend API is running"
```

---

### Authentication Endpoints

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@clinic.com"
}
```

**Status Codes:**
- `200 OK` - Login successful
- `401 Unauthorized` - Invalid credentials
- `400 Bad Request` - Invalid request body

---

#### Logout
```http
POST /api/auth/logout
Authorization: Bearer {token}
```

**Response:**
```
200 OK
"Logged out successfully"
```

**Note:** In a stateless JWT setup, logout is handled client-side by removing the token. This endpoint is provided for audit logging purposes.

---

#### Auth Health Check
```http
GET /api/auth/health
```

**Response:**
```
200 OK
"Authentication service is running"
```

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-01T10:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/auth/login"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-01-01T10:00:00.000+00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Bad credentials",
  "path": "/api/auth/login"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-01-01T10:00:00.000+00:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied",
  "path": "/api/users"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-01T10:00:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/..."
}
```

---

## Using the API

### Example: Login and Access Protected Resource

1. **Login to get token:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

2. **Use token to access protected endpoints:**
```bash
curl -X GET http://localhost:8080/api/protected-endpoint \
  -H "Authorization: Bearer {token-from-login-response}"
```

---

## Postman Collection

You can import these endpoints into Postman for easier testing:

1. Create a new collection
2. Add the base URL as a variable: `{{base_url}}` = `http://localhost:8080/api`
3. Add authentication variable: `{{token}}`
4. Import the endpoints above

---

## Rate Limiting

Currently, no rate limiting is implemented. This should be added in production.

---

## Security Considerations

1. **JWT Secret**: Change the default JWT secret in production
2. **Password Policy**: Implement stronger password policies in production
3. **HTTPS**: Always use HTTPS in production
4. **Token Expiration**: Tokens expire after 24 hours by default
5. **CORS**: Configure CORS settings for your frontend application
