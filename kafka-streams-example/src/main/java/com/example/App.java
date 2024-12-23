package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

public class App {
    public static void main(String[] args) {
        
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-simplified-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        
        KStream<String, String> input = builder.stream("input_topic");

        input.mapValues(value -> {
            try {
                // Parse JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(value);
                String priceString = jsonNode.get("price").asText();
                String userID = jsonNode.get("user_id").asText();

                ObjectNode responseJson = objectMapper.createObjectNode();

                responseJson.put("user_id", userID);

                Double priceValue = Double.parseDouble(priceString);
                if (priceValue > 13000) {
                    responseJson.put("probableFraud", "1");
                } else {
                    responseJson.put("probableFraud", "0");
                }

                if (priceString != null && !priceString.isEmpty()) {
                    responseJson.put("price", priceString); 
                } else {
                    System.err.println("invalid price");
                    responseJson.put("price", "0.0");
                }

                return responseJson.toString();  
            } catch (Exception e) {
                System.err.println("Error on processing: " + value + " - Error: " + e.getMessage());                
                ObjectNode errorJson = new ObjectMapper().createObjectNode();
                errorJson.put("error", "Error on processing");
                return errorJson.toString();
            }
        }).to("output_topic", Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        streams.start();
    }
}
