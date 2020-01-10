package com.unict.riganozito.spout.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${videoservice.kafka.spark.topic}")
    public String topic;

    public void publish(String str) {
        kafkaTemplate.send(topic, str);
    }

}
