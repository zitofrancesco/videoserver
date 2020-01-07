package com.unict.riganozito.videoprocessingservice.service;

import com.unict.riganozito.videoprocessingservice.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class ProcessingService {

    @Autowired
    KafkaProducer kafkaProducer;

    public void processVideoAsync(Integer id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Process video with id " + id + " started");
                processVideo(id);
            }
        }, "ProcessThread " + id);
        thread.setDaemon(true);
        thread.start();
    }

    public void processVideo(Integer id) {
        boolean isError = false;

        try {
            ProcessBuilder builder = new ProcessBuilder("./script.sh", id.toString());
            builder.directory(new File(System.getProperty("user.dir")));

            Process p = builder.start();
            p.waitFor();

            isError = p.exitValue() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }

        if (isError) {
            kafkaProducer.publishMessage(false, id);
            System.out.println("Process video with id " + id + " failed");
        }
        else {
            kafkaProducer.publishMessage(true, id);
            System.out.println("Process video with id " + id + " finished");
        }
       
    }
}
