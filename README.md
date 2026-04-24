#  Healthcare Microservices

A scalable healthcare management system built using Spring Boot microservices architecture, featuring REST APIs, asynchronous communication with Kafka, and gRPC (Protobuf) for efficient inter-service communication. Fully containerized using Docker for consistent deployment.

---

# Tech Stack

- Spring Boot – Microservices framework  
- REST APIs – External communication  
- Apache Kafka – Event-driven communication between services  
- gRPC (Protobuf) – High-performance internal service communication  
- Docker – Containerization  
- Maven – Build tool  

---

# Microservices Overview

| Service | Description |
|--------|------------|
| api-gateway | Entry point for all client requests |
| auth-service | Handles authentication & authorization |
| patient-service | Manages patient data |
| billing-service | Handles billing operations |
| analytic-service | Processes analytics and events |
| integration-tests | End-to-end testing |

---

#  Architecture

## Communication Types

###  REST APIs
- Used for client → system communication  
- Routed via API Gateway  

###  Kafka (Event Streaming)
- Used for asynchronous communication  
- Enables loose coupling between services  

**Example flow:**
- Patient created → event published → analytics & billing consume it  

###  gRPC (Protocol Buffers)
- Used for high-performance service-to-service calls  
- Efficient binary communication  
- Strongly typed contracts using `.proto` files  

