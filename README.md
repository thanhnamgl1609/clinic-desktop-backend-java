# Clinic Desktop Backend

A cross-platform desktop application backend designed for small clinics to efficiently manage patient records, medical history, prescriptions, and clinic medicine inventory. The backend supports role-based access control, JWT authentication, comprehensive audit logging, and is built with scalability and extensibility in mind.

## Tech Stack

- **Java 17**: Programming language
- **Spring Boot 3.2.0**: Backend framework with dependency injection and IoC
- **Spring Security**: Security framework with JWT authentication
- **Spring Data JPA**: Data persistence layer
- **MySQL 8.0**: Relational database
- **Docker & Docker Compose**: Containerization for development and deployment
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code

## Architecture

This project follows Domain-Driven Design (DDD) principles with a layered architecture:

```
src/main/java/com/clinic/backend/
├── domain/               # Domain layer - entities and repositories
│   ├── user/
│   ├── role/
│   └── permission/
├── application/          # Application layer - use cases and DTOs
│   ├── auth/
│   └── user/
├── infrastructure/       # Infrastructure layer - technical concerns
│   ├── config/
│   ├── security/
│   └── persistence/
└── presentation/         # Presentation layer - REST controllers
    └── controller/
```

## Features

### Implemented Features

- ✅ Spring Boot project structure with DDD architecture
- ✅ Dependency Injection and IoC container configuration
- ✅ JWT-based authentication (login/logout)
- ✅ User entity with roles and permissions
- ✅ Role-based access control (RBAC)
- ✅ Password encryption with BCrypt
- ✅ Docker development environment
- ✅ MySQL database integration

### Planned Features

- [ ] Multi-Factor Authentication (MFA) with OTP
- [ ] User management (CRUD operations)
- [ ] Role and permission management
- [ ] Patient management
- [ ] Medicine inventory management
- [ ] Prescription management
- [ ] Audit logging
- [ ] Notification system
- [ ] IP allowlist management

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.9.11 or higher (for local development)
- Docker and Docker Compose (for containerized development)

### Local Development (without Docker)

1. **Clone the repository**
   ```bash
   git clone https://github.com/thanhnamgl1609/clinic-desktop-backend-java.git
   cd clinic-desktop-backend-java
   ```

2. **Set up MySQL database**
   - Install MySQL 8.0
   - Create a database: `clinic_db`
   - Update credentials in `src/main/resources/application.yml`

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080/api`
   - Health check: `http://localhost:8080/api/public/health`

### Docker Development Environment

1. **Navigate to the build-config directory**
   ```bash
   cd build-config/dev
   ```

2. **Create environment file**
   ```bash
   cp .env.example .env
   # Edit .env file with your configuration
   ```

3. **Start the services**
   ```bash
   docker-compose -f docker-compose.dev.yml up --build
   ```

4. **Stop the services**
   ```bash
   docker-compose -f docker-compose.dev.yml down
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080/api`
   - Health check: `http://localhost:8080/api/public/health`

### Production Deployment

1. **Navigate to production config**
   ```bash
   cd build-config/prod
   ```

2. **Configure environment**
   ```bash
   cp .env.example .env
   # IMPORTANT: Update .env with strong passwords and secrets
   ```

3. **Deploy with Docker Compose**
   ```bash
   docker-compose -f docker-compose.prod.yml up -d
   ```

## API Documentation

### Authentication Endpoints

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "your-username",
  "password": "your-password"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "your-username",
  "email": "your-email@example.com"
}
```

#### Logout
```http
POST /api/auth/logout
Authorization: Bearer {token}

Response:
"Logged out successfully"
```

#### Health Check
```http
GET /api/public/health

Response:
"Clinic Backend API is running"
```

### Protected Endpoints

All protected endpoints require a valid JWT token in the Authorization header:

```http
Authorization: Bearer {your-jwt-token}
```

## Project Structure

```
clinic-desktop-backend-java/
├── build-config/           # Docker and deployment configurations
│   ├── dev/               # Development environment
│   │   ├── backend.Dockerfile
│   │   ├── docker-compose.dev.yml
│   │   └── .env.example
│   └── prod/              # Production environment
│       ├── backend.Dockerfile
│       ├── docker-compose.prod.yml
│       └── .env.example
├── docs/                  # Documentation
│   └── planning/
│       └── 01.md         # Project planning document
├── src/
│   ├── main/
│   │   ├── java/com/clinic/backend/
│   │   │   ├── domain/
│   │   │   ├── application/
│   │   │   ├── infrastructure/
│   │   │   └── presentation/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml               # Maven configuration
└── README.md            # This file
```

## Development Guidelines

### Dependency Injection

This project uses Spring's dependency injection through constructor injection (recommended approach):

```java
@Service
@RequiredArgsConstructor  // Lombok generates constructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // ...
}
```

### Adding New Features

1. **Domain Layer**: Define entities and repositories
2. **Application Layer**: Create DTOs and service classes
3. **Infrastructure Layer**: Implement technical concerns (security, config)
4. **Presentation Layer**: Create REST controllers

### Security

- All passwords are encrypted using BCrypt
- JWT tokens are used for stateless authentication
- Token expiration is configurable (default: 24 hours)
- CSRF protection is disabled (stateless API)
- Role-based access control via Spring Security

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_HOST` | Database host | localhost |
| `DB_PORT` | Database port | 3306 |
| `DB_NAME` | Database name | clinic_db |
| `DB_USER` | Database user | root |
| `DB_PASSWORD` | Database password | password |
| `JWT_SECRET` | JWT signing secret | (must be changed in production) |
| `JWT_EXPIRATION` | JWT expiration time in ms | 86400000 (24h) |
| `PORT` | Application port | 8080 |
| `LOG_LEVEL` | Logging level | DEBUG |

## Testing

Run tests with Maven:

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write/update tests
5. Submit a pull request

## License

[Add your license here]

## Support

For issues and questions, please create an issue in the GitHub repository.
