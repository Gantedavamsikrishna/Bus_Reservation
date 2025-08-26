@echo off
echo Testing User Service Endpoints
echo =============================
echo.

echo 1. Testing GET /users (Get all users)
echo -------------------------------------
curl -X GET http://localhost:8082/users
echo.
echo.

echo 2. Testing POST /users (Add new user)
echo -------------------------------------
curl -X POST http://localhost:8082/users -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"123-456-7890\"}"
echo.
echo.

echo 3. Testing POST /users (Add another user)
echo -----------------------------------------
curl -X POST http://localhost:8082/users -H "Content-Type: application/json" -d "{\"name\":\"Jane Smith\",\"email\":\"jane.smith@example.com\",\"phone\":\"098-765-4321\"}"
echo.
echo.

echo 4. Testing GET /users again (Verify users were added)
echo -----------------------------------------------------
curl -X GET http://localhost:8082/users
echo.
echo.

echo Testing completed!
pause
