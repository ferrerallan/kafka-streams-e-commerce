
# Kafka Streams E-Commerce Project

## Description

This project demonstrates the use of Kafka Streams to process real-time data in an e-commerce scenario. The main application analyzes purchase transactions from a Kafka topic, identifies potential fraudulent activities based on purchase price thresholds, and outputs the processed data to another Kafka topic. It serves as a practical example for developers aiming to understand Kafka Streams and its use in building real-time processing pipelines.

The project includes:
- **Real-time data transformation** using Kafka Streams.
- **Fraud detection logic** for high-value transactions.
- Scripts to simulate producers and consumers.

## Requirements

- **Java Development Kit (JDK)** 8 or higher
- **Apache Kafka** (with Zookeeper) installed and running locally
- **Kafka Streams library** (integrated via Maven/Gradle dependencies in the project)
- **bash** (for running producer and consumer scripts)
- **jq** (for JSON parsing in the consumer script)

## Mode of Use

### Clone the Repository

\`\`\`bash
git clone https://github.com/ferrerallan/kafka-streams-e-commerce.git
cd kafka-streams-e-commerce
\`\`\`

### Set Up Kafka

1. **Start Kafka and Zookeeper:**
   Ensure that Kafka and Zookeeper are running. Use the provided Kafka installation scripts or run them as shown below:

   \`\`\`bash
   zookeeper-server-start.sh config/zookeeper.properties &
   kafka-server-start.sh config/server.properties &
   \`\`\`

2. **Create Kafka Topics:**
   Create the input and output topics for the application:

   \`\`\`bash
   kafka-topics.sh --create --topic input_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   kafka-topics.sh --create --topic output_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   \`\`\`

### Build and Run the Application

1. **Compile the Application:**
   Ensure all dependencies are installed and compile the application using Maven or Gradle.

   Example with Maven:
   \`\`\`bash
   mvn clean install
   \`\`\`

2. **Run the Application:**
   Execute the application to start processing data from the \`input_topic\` and output results to \`output_topic\`:

   \`\`\`bash
   java -jar target/kafka-streams-e-commerce-1.0.jar
   \`\`\`

### Simulate Producers and Consumers

1. **Simulate Message Production:**
   Use the \`producer_messages.sh\` script to simulate real-time message production into the \`input_topic\`.

   \`\`\`bash
   ./producer_messages.sh
   \`\`\`

   This script generates random purchase transactions and sends them to the input topic.

2. **Consume Processed Messages:**
   Use the \`read_messages.sh\` script to consume messages from the \`output_topic\`. Fraudulent transactions are highlighted in red.

   \`\`\`bash
   ./read_messages.sh
   \`\`\`

## Example Output

- **Input Message:**
  \`\`\`json
  {"user_id": 1, "item": "laptop", "price": "15000"}
  \`\`\`

- **Output Message (Fraud Detected):**
  \`\`\`json
  {
    "user_id": "1",
    "price": "15000",
    "probableFraud": "1"
  }
  \`\`\`

- **Output Message (No Fraud):**
  \`\`\`json
  {
    "user_id": "1",
    "price": "12000",
    "probableFraud": "0"
  }
  \`\`\`

## Project Features

- **Real-Time Processing:** Uses Kafka Streams to process and transform streaming data.
- **Fraud Detection Logic:** Flags transactions with prices exceeding a predefined threshold.
- **Interactive Scripts:** Provides scripts for easy testing and visualization of the application’s functionality.

This project is an excellent starting point for developers looking to integrate Kafka Streams into their real-time data processing workflows.
