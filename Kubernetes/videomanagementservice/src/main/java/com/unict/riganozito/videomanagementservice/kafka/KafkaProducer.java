package com.unict.riganozito.videomanagementservice.kafka;

import com.unict.riganozito.videomanagementservice.entity.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${videoservice.kafka.vms.topic}")
    public String vmsTopic;

    public ListenableFuture<SendResult<String, String>> sendVideoProcessRequest(Video video) {
        return kafkaTemplate.send(vmsTopic, "process|" + video.getId());
    }
}