# Product Pricing Service

A RESTful service that calculates product prices based on configurable discount policies.

## Features

- Product information retrieval by UUID
- Dynamic price calculation with configurable discounts:
    - Quantity-based discounts (increasing with quantity)
    - Percentage-based discounts (flat rate)
- TestContainers postgresql database for development

## Prerequisites

- JDK 21
- Docker (optional)

## Building and Running

### Using Gradle

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Using Docker

```bash
# Build Docker image
docker-compose up
```

## API Endpoints

### Get Product Information

```http
GET /api/products/{productId}
```

Response:
```json
{
    "id": "uuid",
    "name": "Product Name",
    "price": 100.00
}
```

### Calculate Price with Discounts

```http
GET /api/calculate/productId?={productId}&quantity={quantity}
```

Response:
```json
{
    "price": 2000.00
}
```

## Testing

Run the tests using:

```bash
./gradlew test
```
