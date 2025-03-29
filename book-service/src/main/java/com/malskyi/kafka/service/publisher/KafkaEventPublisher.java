package com.malskyi.kafka.service.publisher;

import com.malskyi.kafka.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {
    private final Producer<String, String> kafkaProducer;

    @Override
    public void publish(String topic, String key, Object value) {
        publish(topic, key, value, Collections.emptyMap());
    }

    @Override
    public void publish(String topic, String key, Object value, Map<String, String> properties) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, JsonUtil.toJson(value));

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            producerRecord.headers().add(entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
        }

        log.debug("Publishing new event: [{}]", producerRecord);
        kafkaProducer.send(producerRecord);
    }
}
