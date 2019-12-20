package com.unict.riganozito.videomanagementservice.services;

import com.unict.riganozito.videomanagementservice.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VideoService {

    @Autowired
    VideoRepository repository;

    public Video addVideo(Video video) {
        video.setStatus("empty");
        return repository.save(video);
    }

    public Video findById(Integer id) {
        Optional<Video> video = repository.findById(id);
        if (video.isPresent())
            return video.get();
        else
            return null;
    }

    public Video updateStatus(Video video, String status) {
        video.setStatus(status);
        return repository.save(video);
    }

    public List<Video> findAll() {
        ArrayList<Video> list = new ArrayList<Video>();
        Iterable<Video> videos = repository.findAll();
        for (Video video : videos) {
            list.add(video);
        }
        return list;
    }

}
