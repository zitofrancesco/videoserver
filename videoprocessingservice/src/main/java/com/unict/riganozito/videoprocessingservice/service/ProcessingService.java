package com.unict.riganozito.videoprocessingservice.service;

import com.unict.riganozito.videoprocessingservice.entity.Video;
import com.unict.riganozito.videoprocessingservice.entity.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
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
            ProcessBuilder builder = new ProcessBuilder("./script.sh");
            builder.directory(new File(System.getProperty("user.dir").concat("/src/main/java/com/unict/riganozito/videoprocessingservice/service/")));
            System.out.println(System.getProperty("user.dir").concat("/src/main/java/com/unict/riganozito/videoprocessingservice/service/"));
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
