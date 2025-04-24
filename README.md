# Library Management System

A Spring Boot application for managing library operations including books, users, and book lendings.

## Technologies Used

- Java 17
- Spring Boot 3.3.x
- Spring Data JPA
- Thymeleaf (Template Engine)
- H2 Database
- Lombok
- Maven

## Prerequisites

Before running this application, make sure you have the following installed:

- Java Development Kit (JDK) 17 or higher
- Maven 3.6+
- Your favorite IDE (Spring Tool Suite, IntelliJ IDEA, or VS Code)

## Project Setup

1. Clone the repository:

```bash
git clone <repository-url>
cd lms
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Database Configuration

The application uses H2 in-memory database by default. The database console is available at `http://localhost:8080/h2-console` with these default settings:

- JDBC URL: jdbc:h2:./data/lmsdb
- Username: sa
- Password: (leave empty)

## Features

- Book Management

  - Add new books
  - List all books
  - Update book details
  - Delete books

- User Management

  - Register new users
  - List all users
  - Update user details
  - Delete users

- Book Lending
  - Lend books to users
  - Track borrowed books
  - Return books
  - View lending history

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/project_lms/lms/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repo/
│   │       └── service/
│   └── resources/
│       ├── application.properties
│       └── templates/
└── test/
    └── java/
```

## Development

To make changes to the project:

1. Create a new branch:

```bash
git checkout -b feature/your-feature-name
```

2. Make your changes and commit:

```bash
git add .
git commit -m "Description of changes"
```

3. Push your changes:

```bash
git push origin feature/your-feature-name
```

## Accessing the Application

After starting the application, you can access:

- Home page: `http://localhost:8080`
- Books management: `http://localhost:8080/books`
- Users management: `http://localhost:8080/users`
- Lendings management: `http://localhost:8080/lendings`

## Testing

To run the tests:

```bash
mvn test
```
