package com.unict.riganozito.videoprocessingservice.service;

import com.unict.riganozito.videoprocessingservice.entity.Video;
import com.unict.riganozito.videoprocessingservice.entity.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class ProcessingService {

    @Autowired
    VideoRepository repository;

    public boolean processVideo(Integer id) {
        Optional<Video> video = repository.findById(id);
        if(video.isPresent()){
            ProcessBuilder builder = new ProcessBuilder("script.sh");
            builder.environment().put("name", video.get().getName());
            try{
                Process p = builder.start();
            }
            catch (IOException e){
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }
}
