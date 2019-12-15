package com.unict.riganozito.videoprocessingservice.service;

import com.unict.riganozito.videoprocessingservice.entity.Video;
import com.unict.riganozito.videoprocessingservice.entity.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${pathscript}")
    public String pathscript;

    public boolean processVideo(Integer id) {
        Optional<Video> video = repository.findById(id);
        if(video.isPresent()){
            ProcessBuilder builder = new ProcessBuilder("./script.sh", video.get().getName());
            builder.directory(new File(System.getProperty("user.dir").concat(pathscript)));
            try{
                Process p = builder.start();
                p.waitFor();
            }
            catch (IOException | InterruptedException e){
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }
}
