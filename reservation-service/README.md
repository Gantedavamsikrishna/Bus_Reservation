# Reservation Service

This service handles bus seat reservations and requires JWT authentication.

## Security Configuration

The service now has JWT-based authentication enabled. All endpoints require a valid JWT token from the auth service.

## Testing the Security

### 1. Get a JWT Token
First, authenticate with the auth service to get a JWT token:

```bash
# Login to get token
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"your_username","password":"your_password"}'
```

### 2. Use the Token
Include the JWT token in the Authorization header for all requests:

```bash
# Test authentication
curl -X GET http://localhost:8084/reservations/test \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"

# Book a seat
curl -X POST http://localhost:8084/reservations \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"busId":1,"seatNumber":"A1"}'

# Get user reservations
curl -X GET http://localhost:8084/reservations/user/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"

# Cancel reservation
curl -X DELETE http://localhost:8084/reservations/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## Configuration

The JWT secret and expiration are configured in `application.properties`:

```properties
jwt.secret=mysecretkey1234567890
jwt.expiration=3600000
```

**Important**: Make sure the JWT secret matches the one in your auth service!

## Endpoints

- `GET /reservations/test` - Test authentication
- `POST /reservations` - Book a seat
- `GET /reservations/user/{userId}` - Get user's reservations
- `DELETE /reservations/{id}` - Cancel a reservation

All endpoints require valid JWT authentication.
