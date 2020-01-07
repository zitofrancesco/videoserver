package com.unict.riganozito.videoprocessingservice.kafka;

import com.unict.riganozito.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {

    @Autowired
    ProcessingService processingService;

    @KafkaListener(topics="${videoservice.kafka.vms.topic}")
    public void consumeMessage(String content){
        String[] v = content.split("|", 2);
        int videoId = Integer.parseInt(v[1]);
        processingService.processVideoAsync(videoId);
    }
}
