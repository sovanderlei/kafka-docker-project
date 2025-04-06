# kafka-docker-project

## Messaging kafka, Java Spring Boot, Python docker

---

# 📌 Kafka Sales Processing System

📝 Description
This project implements a sales processing system using Apache Kafka for communication between services. The system receives JSON files containing lists of sales, processes them, and sends the data to a Kafka topic. A Python consumer listens to the topic, generates Excel files with the sales data, and creates charts based on the sales month.

---

## 🏗️ Project Architecture
The project follows an event-driven architecture with Kafka as the messaging system. It consists of the following components:

Producer (Spring Boot)

Exposes a REST API endpoint to receive sales data via JSON.

Monitors the FOLDERRECB directory, processes JSON files, and sends the sales data to Kafka.

Moves processed files to the FOLDERRECB/enviado subdirectory.

Broker (Apache Kafka & Zookeeper)

Uses Kafka to mediate communication between services.

Zookeeper manages Kafka nodes and metadata.

Consumer (Python)

Listens to the Kafka topic and receives sales data in real time.

Generates an Excel file with the received data.

Creates a chart grouping sales by month.

Saves files in the FOLDEREXCEL directory.

## 🛠️ Technologies Used
🔹 Backend (Producer)
Java 17

Spring Boot (Spring Kafka, Spring Web, Spring Scheduling)

Apache Kafka

Docker & Docker Compose

🔹 Backend (Consumer)
Python 3.10

Kafka-Python (to consume Kafka messages)

Pandas & OpenPyXL (for Excel file generation and manipulation)

Matplotlib (for chart generation)

🔹 Infrastructure
Docker Compose (for service orchestration)

Apache Kafka & Zookeeper (for event management)

## 🚀 How to Run the Project
🔹 Step 1: Clone the Repository
sh
Copy
Edit
git clone https://github.com/your-username/kafka-sales-system.git
cd kafka-sales-system
🔹 Step 2: Start Services with Docker
sh
Copy
Edit
docker-compose up -d
🔹 Step 3: Test the Producer (Java)
To send a sales record, make a POST request to the producer API:

sh
Copy
Edit
curl -X POST http://localhost:8080/api/sales/send \
 -H "Content-Type: application/json" \
 -d '{
"item": 1,
"productName": "Laptop",
"unitPrice": 1500.50,
"quantity": 2,
"saleDate": "2024-03-25",
"total": 3001.00
}'
🔹 Step 4: Verify Kafka Messages
To check if the message was successfully sent, access the Kafka Consumer (Python) logs:

sh
Copy
Edit
docker logs -f consumer-python
🔹 Step 5: Check Generated Excel Files
Once the consumer processes the sales data, an Excel file with a sales table and monthly chart will be available in the FOLDEREXCEL directory.
