package com.unict.riganozito.videoprocessingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${videoservice.kafka.producer.topic}")
    public String topic;


    public void publishMessage(boolean val, Integer id) {
        if (val) {
            kafkaTemplate.send(topic, "processed|" + id.toString());
        } else
            kafkaTemplate.send(topic, "processingFailed|" + id.toString());
    }
}
