# Bus Reservation System - Microservices Architecture

## 🚀 Project Overview

This is a comprehensive **Bus Reservation System** built using **Spring Boot Microservices Architecture**. The system provides a complete solution for bus booking, user management, authentication, and reservation handling with a distributed architecture that ensures scalability, maintainability, and high availability.

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   API Gateway   │────│  Eureka Server   │────│  Load Balancer  │
│   (Port: 8080)  │    │   (Port: 8761)   │    │   (Ribbon)      │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │
         │                       │
         ▼                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Microservices Layer                          │
├─────────────────┬─────────────────┬─────────────────┬─────────┤
│  Auth Service   │  User Service   │  Bus Service    │Reservation│
│  (Port: 8081)   │  (Port: 8082)   │  (Port: 8083)  │ Service  │
│                 │                 │                 │(Port:8084)│
└─────────────────┴─────────────────┴─────────────────┴─────────┘
         │                 │                 │           │
         ▼                 ▼                 ▼           ▼
┌─────────────────┐ ┌─────────────────┐ ┌─────────────┐ ┌─────────────┐
│   MySQL DB      │ │   MySQL DB      │ │  MySQL DB   │ │  MySQL DB   │
│  (Auth Data)    │ │  (User Data)    │ │ (Bus Data)  │ │(Reservation)│
└─────────────────┘ └─────────────────┘ └─────────────┘ └─────────────┘
```

## 🛠️ Technology Stack

### **Backend Technologies**
| Component | Technology | Version |
|-----------|------------|---------|
| **Framework** | Spring Boot | 3.2.12 - 3.5.5 |
| **Language** | Java | 17 |
| **Build Tool** | Maven | Latest |
| **Database** | MySQL | 8.0+ |

### **Spring Cloud Components**
| Service | Component | Purpose |
|---------|-----------|---------|
| **API Gateway** | Spring Cloud Gateway | Route management, Load balancing |
| **Service Discovery** | Netflix Eureka | Service registration & discovery |
| **Load Balancer** | Spring Cloud LoadBalancer | Client-side load balancing |
| **Service Communication** | OpenFeign | Declarative REST client for inter-service calls |

### **Security & Authentication**
| Component | Technology | Purpose |
|-----------|------------|---------|
| **JWT** | JJWT Library | Token-based authentication |
| **Security** | Spring Security | Authentication & authorization |
| **Password Encoding** | BCrypt | Secure password hashing |

### **Data Layer**
| Component | Technology | Purpose |
|-----------|------------|---------|
| **ORM** | Spring Data JPA | Database operations |
| **Validation** | Bean Validation | Input validation |
| **Repository** | Spring Data Repository | Data access patterns |

## 📋 Service Details

### 1. **Eureka Server** (Service Registry)
- **Port**: 8761
- **Purpose**: Central service discovery and registration
- **Features**: 
  - Service health monitoring
  - Load balancing support
  - High availability configuration

### 2. **API Gateway** (Entry Point)
- **Port**: 8080
- **Purpose**: Single entry point for all client requests
- **Features**:
  - Route management
  - Load balancing
  - Cross-cutting concerns
  - Service aggregation

### 3. **Auth Service** (Authentication & Authorization)
- **Port**: 8081
- **Purpose**: User authentication and JWT token management
- **Features**:
  - User registration
  - User login
  - JWT token generation
  - Password encryption

### 4. **User Service** (User Management)
- **Port**: 8082
- **Purpose**: User profile and data management
- **Features**:
  - User CRUD operations
  - User profile management
  - User data validation

### 5. **Bus Service** (Bus Management)
- **Port**: 8083
- **Purpose**: Bus operations and seat management
- **Features**:
  - Bus CRUD operations
  - Route management
  - Seat availability
  - Search functionality

### 6. **Reservation Service** (Booking Management)
- **Port**: 8084
- **Purpose**: Reservation and booking operations
- **Features**:
  - Seat booking
  - Reservation management
  - Booking cancellation
  - User reservation history
  - **OpenFeign Integration**: Ready for inter-service communication

## 🔌 API Endpoints

### **Authentication Service** (`/auth`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/auth/register` | User registration | `User` object | Success message |
| `POST` | `/auth/login` | User authentication | `AuthRequest` | `AuthResponse` (JWT token) |

### **User Service** (`/users`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/users` | Create new user | `User` object | Created `User` |
| `GET` | `/users` | Get all users | - | List of `User` objects |

### **Bus Service** (`/buses`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/buses` | Create new bus | `Bus` object | Created `Bus` |
| `GET` | `/buses` | Get all buses | - | List of `Bus` objects |
| `GET` | `/buses/{id}` | Get bus by ID | - | `Bus` object |
| `GET` | `/buses/search` | Search buses | Query params | List of `Bus` objects |
| `PATCH` | `/buses/{id}/seats` | Adjust seat count | Query param `delta` | Updated `Bus` |

### **Reservation Service** (`/reservations`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/reservations` | Book a seat | `Reservation` object | Created `Reservation` |
| `GET` | `/reservations/user/{userId}` | Get user reservations | - | List of `Reservation` objects |
| `DELETE` | `/reservations/{id}` | Cancel reservation | - | Success message |
| `GET` | `/reservations/test` | Test authentication | - | Authentication status |

## 🚀 Getting Started

### **Prerequisites**
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### **Installation Steps**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Gantedavamsikrishna/Bus_Reservation.git
   cd Bus_Reservation
   ```

2. **Database Setup**
   - Create MySQL databases for each service
   - Update `application.properties` with your database credentials

3. **Start Services in Order**
   ```bash
   # 1. Start Eureka Server
   cd eureka-server
   mvn spring-boot:run
   
   # 2. Start Auth Service
   cd ../auth-service
   mvn spring-boot:run
   
   # 3. Start User Service
   cd ../user-service
   mvn spring-boot:run
   
   # 4. Start Bus Service
   cd ../bus-service
   mvn spring-boot:run
   
   # 5. Start Reservation Service
   cd ../reservation-service
   mvn spring-boot:run
   
   # 6. Start API Gateway
   cd ../api-gateway
   mvn spring-boot:run
   ```

4. **Access Services**
   - Eureka Dashboard: http://localhost:8761
   - API Gateway: http://localhost:8080
   - Auth Service: http://localhost:8081
   - User Service: http://localhost:8082
   - Bus Service: http://localhost:8083
   - Reservation Service: http://localhost:8084

## 🔐 Security Implementation

### **JWT Token Flow**
1. User registers/logs in through Auth Service
2. Auth Service validates credentials and generates JWT token
3. Client includes JWT token in subsequent requests
4. Services validate JWT token using shared secret
5. Access granted based on token validity

## 🔗 **Inter-Service Communication**

### **OpenFeign Client (Declarative REST Client)**
Your system includes **OpenFeign** for seamless service-to-service communication:

#### **What is OpenFeign?**
- **Declarative HTTP client** that simplifies inter-service calls
- **Automatic load balancing** through Eureka service discovery
- **Circuit breaker integration** for fault tolerance
- **Clean, interface-based** approach to REST clients

#### **Current Setup**
- **Reservation Service** has OpenFeign dependency ready
- **Not yet configured** but can be easily enabled

#### **How to Enable OpenFeign**
```java
// 1. Add @EnableFeignClients to main class
@SpringBootApplication
@EnableFeignClients
public class ReservationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }
}

// 2. Create Feign Client Interface
@FeignClient(name = "BUS-SERVICE")
public interface BusServiceClient {
    @GetMapping("/buses/{id}")
    Bus getBusById(@PathVariable("id") Long id);
    
    @PatchMapping("/buses/{id}/seats")
    Bus adjustSeats(@PathVariable("id") Long id, @RequestParam("delta") int delta);
}

// 3. Use in Service Layer
@Service
public class ReservationService {
    @Autowired
    private BusServiceClient busServiceClient;
    
    public Reservation bookSeat(ReservationRequest request) {
        // Call Bus Service to check availability
        Bus bus = busServiceClient.getBusById(request.getBusId());
        
        // Call Bus Service to adjust seats
        busServiceClient.adjustSeats(request.getBusId(), -1);
        
        // Create reservation...
    }
}
```

#### **Benefits of OpenFeign**
- **Automatic Load Balancing**: Routes to healthy instances
- **Service Discovery**: Uses Eureka for service lookup
- **Declarative**: Clean, readable interface definitions
- **Fault Tolerance**: Easy integration with circuit breakers
- **Type Safety**: Compile-time checking of API contracts

### **Security Features**
- Password encryption using BCrypt
- JWT-based stateless authentication
- Role-based access control (configurable)
- Secure password storage
- Token expiration management

## 📊 Data Models

### **User Model**
```java
- id: Long
- username: String
- password: String (encrypted)
- email: String
- role: String
```

### **Bus Model**
```java
- id: Long
- busNumber: String
- from: String
- to: String
- travelDate: LocalDate
- totalSeats: Integer
- availableSeats: Integer
- price: BigDecimal
```

### **Reservation Model**
```java
- id: Long
- userId: Long
- busId: Long
- seatNumber: Integer
- status: String (BOOKED/CANCELLED)
- bookingDate: LocalDateTime
```

## 🧪 Testing

### **Test Scripts**
- `test-reservation-service.bat` - Test reservation endpoints
- `test-user-service.bat` - Test user service endpoints

### **API Testing**
Use Postman or any REST client to test the endpoints:
1. Start with registration: `POST /auth/register`
2. Login to get JWT: `POST /auth/login`
3. Use JWT token in Authorization header for protected endpoints

## 🔧 Configuration

### **Key Configuration Files**
- `application.properties` - Service-specific configurations
- `application.yml` - YAML-based configurations
- `pom.xml` - Maven dependencies and build configuration

### **Environment Variables**
- Database URLs and credentials
- JWT secret keys
- Service ports
- Eureka server URLs

## 📈 Scalability Features

### **Horizontal Scaling**
- Stateless services for easy replication
- Load balancer support
- Service discovery for dynamic scaling

### **Performance Optimization**
- Connection pooling
- Caching strategies
- Asynchronous processing capabilities

## 🚨 Error Handling

### **Global Exception Handling**
- Custom exception classes
- Standardized error responses
- Proper HTTP status codes
- Detailed error logging

### **Circuit Breaker Pattern**
- Service resilience
- Fallback mechanisms
- Timeout handling

## 🔍 Monitoring & Observability

### **Health Checks**
- Spring Boot Actuator endpoints
- Service health monitoring
- Database connectivity checks

### **Logging**
- Structured logging
- Request/response logging
- Error tracking and debugging

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Built with ❤️ using Spring Boot Microservices Architecture**
