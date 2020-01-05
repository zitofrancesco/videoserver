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
        return repository.save(video);
    }

    public Video findById(Integer id) {
        return findById(id, null);
    }

    public Video findById(Integer id, String status) {
        Optional<Video> temp = repository.findById(id);
        if (temp.isPresent()) {
            Video video = temp.get();
            if (status == null || video.getStatus().equals(status))
                return video;
        }
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

    public List<Video> findAll(String status) {
        ArrayList<Video> list = new ArrayList<Video>();
        Iterable<Video> videos = repository.findAll();
        for (Video video : videos) {
            if (status == null || video.getStatus().equals(status))
                list.add(video);
        }
        return list;
    }

}
