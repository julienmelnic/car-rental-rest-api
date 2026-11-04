# Car Rental REST API

## Description

This project is a simple car rental REST API developed in Java using Spring Boot and Gradle.

The application allows users to:
- Retrieve the list of available cars.
- Get information about a specific car.
- Rent a car with rental dates.
- Return a rented car.

## Technologies

- Java 17
- Spring Boot 4.1.2
- Gradle
- REST API
- Postman

## Running the application

1. Import the project into Eclipse as an existing Gradle project.
2. Refresh the Gradle project.
3. Run `CarrentalApplication.java` as a Java Application.
4. The API is available at `http://localhost:8080`.

## REST Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/` | Hello service |
| GET | `/cars` | List available cars |
| GET | `/cars/{plateNumber}` | Get a specific car |
| PUT | `/cars/{plateNumber}?rent=true` | Rent a car |
| PUT | `/cars/{plateNumber}?rent=false` | Return a car |

## Rental Example

**PUT** `http://localhost:8080/cars/AA11BB?rent=true`

Request body:

```json
{
  "begin": "11/11/2026",
  "end": "15/11/2026"
}
```

Expected response:

```text
Car rented successfully
```

## Return Example

**PUT** `http://localhost:8080/cars/AA11BB?rent=false`

Expected response:

```text
Car returned successfully
```

## Error Handling

The application handles several HTTP errors:

- **400 Bad Request:** Missing rental dates.
- **404 Not Found:** Car not found.
- **409 Conflict:** Car already rented or not currently rented.

## Limitations

Car data is stored in memory. Rental states are reset when the application restarts.

The application does not manage overlapping future reservations.

## Testing

All main REST endpoints were tested using Postman.