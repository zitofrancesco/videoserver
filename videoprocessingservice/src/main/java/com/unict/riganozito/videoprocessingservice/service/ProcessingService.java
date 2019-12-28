package com.unict.riganozito.videoprocessingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
public class ProcessingService {

    public Thread processVideoAsync(String id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                processVideo(id);
            }
        });
        thread.start();
        return thread;
    }

    public boolean processVideo(String id) {
        ProcessBuilder builder = new ProcessBuilder("./script.sh", id);
        builder.directory(new File(System.getProperty("user.dir")));
        try {
            Process p = builder.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
