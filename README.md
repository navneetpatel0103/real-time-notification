# Real-Time Order Notification System

A backend system that delivers live order status updates to users using **Server-Sent Events (SSE)** and **Apache Kafka**.

Built with Spring Boot to demonstrate real-world distributed systems concepts including async messaging, real-time communication, and concurrent connection management.

## Architecture
```
User (Browser)
     |
     | SSE (persistent HTTP connection)
     |
[Spring Boot - Monolith]
     ├── Order Service       → handles order creation and status updates
     ├── Notification Service → manages SSE connections per user
     └── Kafka Producer      → publishes order status events
          |
        Kafka (order-status-updates topic)
          |
     Kafka Consumer → pushes SSE update to correct user
```

## Tech Stack

- **Java 17** + **Spring Boot 4.0.3**
- **Apache Kafka** — async event streaming between services
- **SSE (Server-Sent Events)** — real-time push notifications to clients
- **Docker** — running Kafka and Zookeeper locally
- **Lombok** — reducing boilerplate

## Key Design Decisions

- **SSE over WebSockets** — order updates are unidirectional (server → client only), making SSE simpler and sufficient
- **Kafka over direct HTTP** — decouples order service from notification service; events are not lost if notification service is temporarily down
- **Monolith over Microservices** — early stage system with small scope; avoids unnecessary operational complexity
- **ConcurrentHashMap** — thread-safe storage of active SSE connections, handles concurrent user connections safely

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders/create` | Place a new order |
| PATCH | `/orders/{orderId}/status` | Update order status |
| GET | `/api/notifications/subscribe/{userId}` | Subscribe to live order updates via SSE |

## How to Run

### Prerequisites
- Java 17
- Docker Desktop

### 1. Start Kafka
```bash
docker-compose up -d
```

### 2. Configure application
Copy the example config and update values:
```bash
cp src/main/resources/application.example src/main/resources/application.properties
```

### 3. Run the application
```bash
mvn spring-boot:run
```

### 4. Test the flow

**Step 1 — Create an order:**
```bash
POST http://localhost:8080/orders/create
{
  "userId": "user_123",
  "itemName": "Burger"
}
```

**Step 2 — Subscribe to notifications (keep connection open):**
```bash
GET http://localhost:8080/api/notifications/subscribe/user_123
```

**Step 3 — Update order status:**
```bash
PATCH http://localhost:8080/orders/{orderId}/status
{
  "status": "BEING_PREPARED"
}
```

**Result:** SSE tab instantly receives `ORDER_STATUS_UPDATE: BEING_PREPARED`

## What I Learned

- How SSE maintains a persistent HTTP connection over a single TCP connection
- Why Kafka decouples producers and consumers — no message loss if consumer is down
- How ConcurrentHashMap handles thread-safe access across multiple simultaneous SSE connections
- HTTP/2 multiplexing advantage over HTTP/1.1 for SSE connections