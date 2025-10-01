# Quick Start Guide

This guide will help you get the Clinic Backend up and running quickly.

## Prerequisites

Choose one of the following setup methods:

### Option 1: Docker (Recommended)
- Docker Desktop or Docker Engine
- Docker Compose

### Option 2: Local Development
- Java 17 or higher
- Maven 3.9.11 or higher
- MySQL 8.0 or higher

---

## Option 1: Quick Start with Docker

This is the fastest way to get started.

### 1. Clone the Repository
```bash
git clone https://github.com/thanhnamgl1609/clinic-desktop-backend-java.git
cd clinic-desktop-backend-java
```

### 2. Navigate to Development Config
```bash
cd build-config/dev
```

### 3. Create Environment File
```bash
cp .env.example .env
```

### 4. Start All Services
```bash
docker-compose -f docker-compose.dev.yml up --build
```

Wait for the services to start. You should see:
```
clinic-backend-dev  | Started ClinicBackendApplication in X.XXX seconds
```

### 5. Test the API
```bash
# Health check
curl http://localhost:8080/api/public/health

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 6. Stop Services
```bash
docker-compose -f docker-compose.dev.yml down
```

---

## Option 2: Quick Start with Local Setup

### 1. Clone the Repository
```bash
git clone https://github.com/thanhnamgl1609/clinic-desktop-backend-java.git
cd clinic-desktop-backend-java
```

### 2. Set Up MySQL Database
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE clinic_db;
CREATE USER 'clinic_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON clinic_db.* TO 'clinic_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

### 3. Configure Application
Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/clinic_db?useSSL=false&serverTimezone=UTC
    username: clinic_user
    password: password
```

### 4. Build the Application
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

Wait for the application to start:
```
Started ClinicBackendApplication in X.XXX seconds
```

### 6. Test the API
```bash
# Health check
curl http://localhost:8080/api/public/health

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

---

## Default Test Accounts

The application comes with two pre-configured accounts:

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Roles:** ADMIN
- **Permissions:** Full access to all resources

### Employee Account
- **Username:** `employee`
- **Password:** `employee123`
- **Roles:** EMPLOYEE
- **Permissions:** Limited access (patient and prescription management)

---

## Verify Installation

### 1. Check Health Endpoint
```bash
curl http://localhost:8080/api/public/health
```
Expected response: `Clinic Backend API is running`

### 2. Test Authentication
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Expected response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@clinic.com"
}
```

### 3. Check Logs
Look for these messages in the logs:
```
Default data initialized successfully!
Admin user - username: admin, password: admin123
Employee user - username: employee, password: employee123
```

---

## Next Steps

1. **Read the API Documentation:** See `docs/API.md` for complete API reference
2. **Explore the Code:** Check out the DDD architecture in `src/main/java/com/clinic/backend/`
3. **Run Tests:** Execute `mvn test` to run all tests
4. **Configure Production:** Update environment variables in `build-config/prod/.env.example`

---

## Troubleshooting

### Port Already in Use
If port 8080 is already in use:
1. Change the port in `.env` or `application.yml`
2. Update `PORT=8081` (or any available port)

### Database Connection Issues
1. Verify MySQL is running: `mysql -u root -p`
2. Check database exists: `SHOW DATABASES;`
3. Verify credentials in configuration

### Docker Issues
1. Make sure Docker is running: `docker --version`
2. Check containers: `docker ps -a`
3. View logs: `docker logs clinic-backend-dev`
4. Restart containers: `docker-compose down && docker-compose up --build`

### Build Errors
1. Clean Maven cache: `mvn clean`
2. Update dependencies: `mvn dependency:resolve`
3. Verify Java version: `java -version` (should be 17+)

---

## Useful Commands

### Docker Commands
```bash
# View running containers
docker ps

# View logs
docker logs -f clinic-backend-dev

# Access database
docker exec -it clinic-mysql-dev mysql -u root -p

# Rebuild without cache
docker-compose -f docker-compose.dev.yml build --no-cache

# Remove all containers and volumes
docker-compose -f docker-compose.dev.yml down -v
```

### Maven Commands
```bash
# Clean build
mvn clean install

# Run tests
mvn test

# Run without tests
mvn clean install -DskipTests

# Run specific test
mvn test -Dtest=PublicControllerTest

# Package application
mvn package
```

### Application Commands
```bash
# Run application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Run with debug
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

---

## Support

For issues and questions:
- Create an issue on GitHub
- Check the logs for detailed error messages
- Review the API documentation in `docs/API.md`
