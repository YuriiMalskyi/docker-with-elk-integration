package com.malskyi.kafka.service.publisher;

import java.util.Map;

public interface EventPublisher {
    void publish(String topic, String key, Object value);
    void publish(String topic, String key, Object value, Map<String, String> properties);
}
