package com.unict.riganozito.videomanagementservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import com.unict.riganozito.videomanagementservice.entity.Video;
import com.unict.riganozito.videomanagementservice.services.StorageService;
import com.unict.riganozito.videomanagementservice.services.VideoService;

@Component
@Transactional
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    VideoService videoService;

    @Autowired
    StorageService storageService;

    /*
     * Se il video è stato processato correttemente command=processed|$ID altrimenti
     * in presenza di fallimento command=processingFailed|$ID
     */
    @KafkaListener(topics = "${videoservice.kafka.consumer-topic}", groupId = "vms-consumer")
    public void processVideoCommand(String command) {
        logger.info("received command = '{}'", command);
        try {
            // fetch command
            String[] v = command.split("|", 2);
            boolean isFailure = !v[0].equals("processed");
            int videoId = Integer.parseInt(v[1]);
            Video video = videoService.findById(videoId);
            if (video == null)
                throw new Exception("Video-" + videoId + "was not found");

            if (isFailure) {
                video = videoService.updateStatus(video, Video.STATE_NOT_AVAILABLE);
                if (!storageService.removeVideo(video))
                    throw new Exception("Video-" + videoId + " was not deleted");
            } else {
                video = videoService.updateStatus(video, Video.STATE_AVAILABLE);
            }

        } catch (Exception e) {
            logger.error("An error occurred! '{}'", e.getMessage());
        }
    }
}