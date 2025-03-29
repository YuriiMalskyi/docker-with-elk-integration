package com.malskyi.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Slf4j
@Configuration
public class KafkaProducerConfig {
    @Bean
    public Producer<String, String> kafkaProducer(@Value("${com.malskyi.kafka.bootstrap.servers}") String bootstrap) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", bootstrap);
//        props.setProperty("bootstrap.servers", "192.168.1.253:9092");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
}
