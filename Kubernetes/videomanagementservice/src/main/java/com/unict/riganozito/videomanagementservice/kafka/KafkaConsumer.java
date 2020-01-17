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
import com.unict.riganozito.videomanagementservice.saga.Saga;
import com.unict.riganozito.videomanagementservice.saga.SagaCondition;
import com.unict.riganozito.videomanagementservice.saga.SagaOperation;

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
    @KafkaListener(topics = "${videoservice.kafka.consumer-topic}")
    public void processVideoCommand(String command) {
        logger.info("received command = '{}'", command);
        try {
            // fetch command
            String[] v = command.split("|", 2);
            String state = v[0];
            int videoId = Integer.parseInt(v[1]);

            Saga sagaBuilder = new Saga();
            sagaBuilder.addTransaction(new SagaOperation() {
                @Override
                public void action(Saga saga) throws Exception {
                    // check state
                    boolean isFailure = !state.equals("processed");
                    // find video
                    Video video = videoService.findById(videoId);
                    if (video == null)
                        throw new Exception("Video-" + videoId + "was not found");
                    saga.setData("video", video);
                    saga.setData("isFailure", isFailure);
                }
            },new SagaOperation(){
                @Override
                public void action(Saga saga) throws Exception {
                    saga.removeData("video");
                    saga.removeData("isFailure");
                }
            });

            //viene eseguita solamente quando isFailure=true
            sagaBuilder.addTransaction(new SagaOperation(){
                @Override
                public void action(Saga saga) throws Exception {
                    //set new state failure
                    Video video = (Video) saga.getData("video");
                    video = videoService.updateStatus(video, Video.STATE_NOT_AVAILABLE);
                    saga.setData("video", video);
                }
            }, new SagaOperation(){
                @Override
                public void action(Saga saga) throws Exception {
                    Video video = (Video)saga.getData("video");
                    video = videoService.updateStatus(video, Video.STATE_WAITING_UPLOAD);
                    saga.setData("video", video);
                }
            },new SagaCondition(){
                @Override
                public boolean expression(Saga saga) {
                    boolean isFailure = (boolean) saga.getData("isFailure");
                    return isFailure;
                }
            });


            // viene eseguita solamente quando isFailure=true
            sagaBuilder.addTransaction(new SagaOperation() {
                @Override
                public void action(Saga saga) throws Exception {
                    //remove video, se falisce il video non è state eliminato
                    Video video = (Video) saga.getData("video");
                    if (!storageService.removeVideo(video))
                        throw new Exception("Video-" + videoId + " was not deleted");
                }
            },null, new SagaCondition() {
                @Override
                public boolean expression(Saga saga){
                    Boolean isFailure = (boolean)saga.getData("isFailure");
                    return isFailure;
                }
            });
           

            // viene eseguita solamente quando isFailure=false
            sagaBuilder.addTransaction(new SagaOperation() {
                @Override
                public void action(Saga saga) throws Exception {
                    Video video = (Video) saga.getData("video");
                    video = videoService.updateStatus(video, Video.STATE_AVAILABLE);
                    saga.setData("video", video);
                }
            }, new SagaOperation(){
                @Override
                public void action(Saga saga) throws Exception {
                    Video video = (Video)saga.getData("video");
                    video = videoService.updateStatus(video, Video.STATE_WAITING_UPLOAD);
                    saga.setData("video", video);
                }
            }, new SagaCondition() {
                @Override
                public boolean expression(Saga saga) {
                    boolean isFailure = (boolean) saga.getData("isFailure");
                    return !isFailure;
                }
            });

            sagaBuilder.executive();

        } catch (Exception e) {
            logger.error("An error occurred! '{}'", e.getMessage());
        }
    }
}