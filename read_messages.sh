kafka-console-consumer.sh --topic output_topic --bootstrap-server localhost:9092  | while read line; do
  if echo "$line" | jq -e '.probableFraud == "1"' > /dev/null; then
    echo -e "\033[31m$line\033[0m" 
  else
    echo "$line"  
  fi
done
