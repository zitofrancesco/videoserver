package com.unict.riganozito.videoprocessingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
public class ProcessingService {

    public boolean processVideo(String id) {
        ProcessBuilder builder = new ProcessBuilder("./script.sh", id);
        builder.directory(new File(System.getProperty("user.dir")));
        try{
            Process p = builder.start();
            p.waitFor();
        }
        catch (IOException | InterruptedException e){
            return false;
        }
        return true;
    }
}
