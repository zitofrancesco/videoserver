package com.unict.riganozito.videoprocessingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
public class ProcessingService {

    public void processVideoAsync(Integer id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Process video with id " + id + " started");
                if (processVideo(id)) {
                    System.out.println("Video with id " + id + " is processed.");
                } else {
                    System.out.println("Video with id " + id + " isn't processed.");
                }
            }
        }, "ProcessThread " + id);
        thread.setDaemon(true);
        thread.start();
    }

    public boolean processVideo(Integer id) {
        try {
            ProcessBuilder builder = new ProcessBuilder("./script.sh", id.toString());
            builder.directory(new File(System.getProperty("user.dir")));

            Process p = builder.start();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
