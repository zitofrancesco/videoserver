package com.unict.riganozito.videomanagementservice.controller;

import java.util.List;

import com.unict.riganozito.videomanagementservice.services.*;
import com.unict.riganozito.videomanagementservice.entity.Video;
import com.unict.riganozito.videomanagementservice.exception.HttpStatusBadRequestException;
import com.unict.riganozito.videomanagementservice.exception.HttpStatusInternalServerErrorException;
import com.unict.riganozito.videomanagementservice.exception.HttpStatusServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/videos")
public class VideoController {

    @Autowired
    VideoService videoService;

    @Autowired
    StorageService storageService;

    @Autowired
    VideoProcessingService videoProcessingService;

    @PostMapping(path = "/")
    public @ResponseBody Video addVideo(@RequestBody Video video) {
        return videoService.addVideo(video);
    }

    @PostMapping(path = "/{id}")
    public @ResponseBody Video uploadVideo(@PathVariable Integer id, @RequestParam("file") MultipartFile file)
            throws HttpStatusBadRequestException, HttpStatusInternalServerErrorException,
            HttpStatusServiceUnavailableException {
        // find video by id
        Video video = videoService.findById(id);
        if (video == null)
            throw new HttpStatusBadRequestException();

        // si deve verifica che l'utente sia autenticato

        // store video
        if (!storageService.storeFile(video.getId(), file))
            throw new HttpStatusInternalServerErrorException();
        // send post request to video processing service
        if (!videoProcessingService.videoProcess(video))
            throw new HttpStatusServiceUnavailableException();

        // aggiorna lo stato
        video = videoService.updateStatus(video, "uploaded");
        return video;
    }

    @GetMapping(path = "/")
    public @ResponseBody List<Video> getsVideo() {
        return null;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Video getVideo(@PathVariable Integer id) {
        return null;
    }
}