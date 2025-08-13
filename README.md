# Spring Boot with Liquibase Flow Integration

## Overview
Demonstrates Spring Boot application integration with Liquibase Flow for coordinated database schema management across both H2 (embedded relational) and MongoDB (document) databases. This pattern shows how to integrate database migrations directly into the Spring Boot application startup lifecycle using Liquibase Flow orchestration.

## Use Case
Java development teams using Spring Boot with mixed database architectures who need coordinated database schema management, require database initialization as part of application startup, or want to demonstrate multi-database migration patterns within a single application.

## What You'll Learn
- Integrating Liquibase Flow directly into Spring Boot application lifecycle
- Managing both relational (H2) and NoSQL (MongoDB) databases simultaneously
- Custom Flow configuration within Java applications
- Maven-based build integration with database management

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker (for MongoDB container)
- Liquibase Pro license (for MongoDB features)
- H2 database (embedded, included in dependencies)

## Quick Start

### 1. Environment Setup
```bash
# Clone and navigate to the repository
cd /path/to/liquibase-patterns/repos/springboot-flowfile

# Start MongoDB container
docker run --name liquibase-mongo -p 27017:27017 -d mongo:6.0
```

### 2. Database Setup
```bash
# MongoDB will be automatically initialized by the application
# H2 database will be created automatically as embedded database
```

### 3. Configure Application
```bash
# Review application.properties for database connections
cat src/main/resources/application.properties

# Review Liquibase Flow configuration
cat src/main/resources/db/changelog/flowfile.yaml
```

### 4. Run the Application
```bash
# Build and run the Spring Boot application
mvn spring-boot:run

# Or build JAR and run
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Repository Structure
```
├── pom.xml                           # Maven configuration with dependencies
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java           # Main Spring Boot application
│   │   │       └── LiquibaseFlowCustomizer.java  # Custom Flow integration
│   │   └── resources/
│   │       ├── application.properties             # Spring Boot configuration
│   │       ├── db/changelog/
│   │       │   ├── db.changelog-master.xml        # H2 database changelog
│   │       │   ├── db.changelog-master.yaml       # Alternative YAML format
│   │       │   ├── flowfile.yaml                  # Flow orchestration config
│   │       │   ├── mongo.xml                      # MongoDB changelog
│   │       │   ├── mongosh.js                     # MongoDB script
│   │       │   └── release1.0.sql                 # SQL changesets
│   │       └── liquibase.properties               # Liquibase configuration
│   └── test/
│       └── java/
│           └── com/example/demo/
│               └── DemoApplicationTests.java      # Unit tests
├── setup.txt                        # Setup instructions and notes
└── mvnw, mvnw.cmd                   # Maven wrapper scripts
```

## Key Features Demonstrated

### Multi-Database Support
- Embedded H2 database for relational data
- External MongoDB for document storage
- Coordinated migrations across database types
- Separate changelog management for each database

### Spring Boot Integration
- Custom Liquibase Flow configuration within application
- Application startup dependency on successful database initialization
- Spring Boot properties integration with Liquibase configuration
- Maven build lifecycle integration

### Flow Orchestration
- Sequential database initialization workflow
- Cross-database dependency management
- Custom validation steps in Flow configuration
- Rollback coordination across multiple databases

## Configuration

### Environment Variables
| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | default | No |
| `LIQUIBASE_ENABLED` | Enable/disable Liquibase | true | No |
| `MONGODB_URL` | MongoDB connection URL | localhost:27017 | No |
| `H2_CONSOLE_ENABLED` | Enable H2 console | true | No |

### Application Properties
Key configuration options in `application.properties`:
- `spring.liquibase.change-log`: Path to main changelog file
- `spring.datasource.url`: H2 database connection (embedded)
- `spring.data.mongodb.uri`: MongoDB connection string
- `logging.level.liquibase`: Liquibase logging level

## Deployment Workflows

### Development Environment
1. Start MongoDB container locally
2. Run `mvn spring-boot:run` 
3. Application initializes both databases automatically
4. Access H2 console at http://localhost:8080/h2-console

### Testing Environment
1. Set `SPRING_PROFILES_ACTIVE=test`
2. Run `mvn test` for unit tests
3. Integration tests verify database initialization
4. Automated database cleanup after tests

### Production Environment
1. Configure external MongoDB cluster
2. Replace H2 with production relational database
3. Set appropriate connection pooling settings
4. Enable production logging and monitoring

## Common Operations

### Adding a New Migration
```bash
# For H2 (relational database)
echo "CREATE TABLE users (id BIGINT PRIMARY KEY, name VARCHAR(255));" > src/main/resources/db/changelog/001_create_users.sql

# For MongoDB
echo "db.products.createIndex({name: 1})" > src/main/resources/db/changelog/001_create_product_index.js

# Update main changelog
vim src/main/resources/db/changelog/db.changelog-master.xml
```

### Rolling Back Changes
```bash
# Rollback through application restart with rollback configuration
# Or use Liquibase CLI commands
liquibase --changeLogFile=db/changelog/db.changelog-master.xml rollback-count 1
```

### Checking Database Status
```bash
# Application health endpoint (if actuator is enabled)
curl http://localhost:8080/actuator/health

# Check H2 console
# Navigate to http://localhost:8080/h2-console

# Check MongoDB
mongo localhost:27017
```

## Troubleshooting

### Common Issues

#### Issue: Application startup failures due to database connection issues
**Symptoms**: Spring Boot application fails to start, database connection errors in logs  
**Solution**: Verify MongoDB container is running, check connection strings in application.properties, ensure databases are accessible

#### Issue: Maven dependency conflicts with Liquibase Pro features
**Symptoms**: ClassNotFoundException, method not found errors, licensing errors  
**Solution**: Verify Liquibase Pro license configuration, ensure commercial dependencies are included in pom.xml, check version compatibility

#### Issue: H2 database file locking issues
**Symptoms**: Database cannot be accessed, file locking errors  
**Solution**: Ensure only one application instance is running, check H2 connection URL settings, restart application to release locks

### Debugging Tips
- Enable debug logging with `logging.level.liquibase=DEBUG` in application.properties
- Use Spring Boot actuator endpoints for application health monitoring
- Check both H2 console and MongoDB logs independently
- Review Maven dependency tree with `mvn dependency:tree`

## Customization Guide

### For Different Databases
- Replace H2 with PostgreSQL, MySQL, or other relational databases
- Update Maven dependencies for new database drivers
- Modify connection settings in application.properties
- Adjust Flow file for database-specific initialization steps

### For Production Deployment
- Configure external database connections
- Implement proper connection pooling
- Add comprehensive health checks and monitoring
- Set up proper logging and audit trails

### For Multi-Environment Support
- Create environment-specific Spring profiles
- Use external configuration for database connections
- Implement environment-specific Flow configurations
- Add deployment scripts for different environments

## Additional Resources
- [Spring Boot Database Initialization](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization)
- [Liquibase Spring Boot Integration](https://docs.liquibase.com/tools-integrations/springboot/home.html)
- [Liquibase Flow Documentation](https://docs.liquibase.com/concepts/liquibase-flow.html)
- [MongoDB with Liquibase](https://docs.liquibase.com/start/tutorials/mongodb-pro.html)

## Support
For questions or issues with this pattern, consult the repository metadata and summary files for detailed implementation guidance. Check the setup.txt file for additional configuration notes and troubleshooting tips.