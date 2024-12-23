#!/bin/bash

send_message() {
  price=$((RANDOM % 14001 + 1000))

  json=$(echo "{\"user_id\": 1, \"item\": \"laptop\", \"price\": \"$price\"}")

  echo $json | kafka-console-producer.sh --topic input_topic --bootstrap-server localhost:9092
}

while true; do
  for i in {1..5}; do
    send_message &
  done

  wait
done
