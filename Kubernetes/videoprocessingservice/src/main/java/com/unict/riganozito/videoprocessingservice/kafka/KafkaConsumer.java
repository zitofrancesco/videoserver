package com.unict.riganozito.videoprocessingservice.kafka;

import com.unict.riganozito.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class KafkaConsumer {

    @Autowired
    ProcessingService processingService;

    @KafkaListener(topics = "${videoservice.kafka.vms.topic}", groupId = "vps-consumer")
    public void consumeMessage(String content) {
        String[] v = content.split("|", 2);
        int videoId = Integer.parseInt(v[1]);
        processingService.processVideoAsync(videoId);
    }
}
