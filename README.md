# Rewards Application

A Spring Boot REST API for calculating retailer customer reward points based on transaction history.

## Features

- Calculate reward points for customers based on configurable thresholds.
- Monthly and total reward breakdown.
- Input validation and error handling.
- In-memory H2 database for demo/testing.
- Seed data for quick start.

## API Endpoints

### Calculate Rewards

`POST /api/rewards`

**Request Body:**
```json
{
  "customerId": "11111111-1111-1111-1111-111111111111",
  "from": "2024-04-01",
  "to": "2025-07-30"
}
```

**Response:**
```json
{
  "customerId": "11111111-1111-1111-1111-111111111111",
  "customerName": "Wisely",
  "from": "2024-04-01",
  "to": "2025-07-30",
  "totalPoints": 160,
  "monthly": {
    "2024": [
      {
        "month": "MAY",
        "points": 25,
        "transactions": [ ... ]
      }
    ],
    "2025": [
      {
        "month": "MAY",
        "points": 90,
        "transactions": [ ... ]
      },
      {
        "month": "JUNE",
        "points": 150,
        "transactions": [ ... ]
      }
    ]
  }
}
```

## Reward Calculation Logic

- **1x Points:** For each dollar spent over `app.rewards-point1xThreshold` (default: 50), earn 1 point.
- **2x Points:** For each dollar spent over `app.rewards-point2xThreshold` (default: 100), earn 2 points, plus 50 points for the first threshold.

Thresholds are configurable in [`src/main/resources/application.properties`](src/main/resources/application.properties).

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Build & Run

```sh
./mvnw spring-boot:run
```
 
## Testing

Run unit and integration tests:

```sh
./mvnw test
```

## Project Structure

- `src/main/java/com/shopping/rewards` - Source code
- `src/test/java/com/shopping/rewards` - Test cases
- `src/main/resources/application.properties` - Configuration
 
---