package com.unict.riganozito.videoprocessingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${videoservice.kafka.vps.topic}")
    public String vmsTopic;


    public void publishMessage(boolean val, Integer id) {
        if (val) {
            kafkaTemplate.send(vmsTopic, "processed|" + id.toString());
        } else
            kafkaTemplate.send(vmsTopic, "processingFailed|" + id.toString());
    }
}
