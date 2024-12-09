# Product Pricing Service

A RESTful service that calculates product prices based on configurable discount policies.

## Features

- Product information retrieval by UUID
- Dynamic price calculation with configurable discounts:
    - Quantity-based discounts (increasing with quantity)
    - Percentage-based discounts (flat rate)

## Prerequisites

- JDK 21
- Docker (optional)

## Building and Running

### Using Docker

```bash
# Build Docker image
docker-compose up
```
Running application with profile `dev` 

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

## Test data

For profiles `dev` application create schemas and insert test data into database.
For test purposes there is one product with id: `7a81cfcd-c25b-4b65-9ea4-8eca89b39316`.
