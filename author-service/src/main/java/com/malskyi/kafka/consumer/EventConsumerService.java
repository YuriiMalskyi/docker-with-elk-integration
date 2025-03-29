package com.malskyi.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
public class EventConsumerService {
    private final KafkaConsumer<String, String> bookEventsKafkaConsumer;
    private final EventHandler eventHandler;

    @Autowired
    public EventConsumerService(EventHandler eventHandler, @Value("${com.malskyi.kafka.bootstrap.servers}") String serverAddress) {
        this.bookEventsKafkaConsumer = initBookEventsConsumer(serverAddress);
        this.eventHandler = eventHandler;
    }

    private KafkaConsumer<String, String> initBookEventsConsumer(String serverAddress) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", serverAddress);
//        props.setProperty("bootstrap.servers", "192.168.1.253:9092");
        props.setProperty("group.id", "authorService");
//        props.setProperty("enable.auto.commit", "true");
        props.setProperty("enable.auto.commit", "false"); // Manual offset commit
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("auto.offset.reset", "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("book-events"));
        return consumer;
    }

    @Scheduled(fixedRate = 100L)
    private void consumeBookBusMessages() {
        ConsumerRecords<String, String> consumerRecords = bookEventsKafkaConsumer.poll(Duration.ofMillis(100));
        if (consumerRecords.isEmpty()) {
            return;
        }
        final List<Object> events = new ArrayList<>();
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            try {
                String eventTypeClass = new String(consumerRecord.headers().lastHeader("eventType").value(), StandardCharsets.UTF_8);
                final Class<?> eventClass = Class.forName(eventTypeClass);
                final Object event = new ObjectMapper().readValue(consumerRecord.value(), eventClass);
                events.add(event);
            } catch (ClassNotFoundException | JsonProcessingException | NullPointerException e) {
                log.error("Could not process record: [{}], exception: {}", consumerRecord, e.getMessage());
                // Skip committing this record's offset to allow reprocessing
            }
        }

        if (!events.isEmpty()) {
            eventHandler.handleEvents(events);
            try {
                bookEventsKafkaConsumer.commitSync(); // Commit offsets after successful processing
                log.info("Offsets committed successfully");
            } catch (Exception e) {
                log.error("Failed to commit offsets: {}", e.getMessage());
            }
        }
//        for (ConsumerRecord<?, ?> consumerRecord : consumerRecords) {
//            String eventTypeClass = new String(consumerRecord.headers().lastHeader("eventType").value(), StandardCharsets.UTF_8);
//            try {
//                final Class<?> aClass = Class.forName(eventTypeClass);
//                final Object value = new ObjectMapper().readValue((String) consumerRecord.value(), aClass);
//                events.add(value);
//            } catch (ClassNotFoundException | JsonProcessingException e) {
//                log.error("Could not process record: [{}], exception: {}", consumerRecord, e.getMessage());
//            }
//        }
//
//        eventHandler.handleEvents(events);
    }
}
