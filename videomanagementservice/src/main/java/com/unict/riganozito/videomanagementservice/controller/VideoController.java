package com.unict.riganozito.videomanagementservice.controller;

import java.security.Principal;
import java.util.List;

import com.unict.riganozito.videomanagementservice.services.*;
import com.unict.riganozito.videomanagementservice.entity.User;
import com.unict.riganozito.videomanagementservice.entity.Video;
import com.unict.riganozito.videomanagementservice.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(path = "/videos")
public class VideoController {

    @Autowired
    VideoService videoService;

    @Autowired
    StorageService storageService;

    @Autowired
    UserService userService;

    @Autowired
    VideoProcessingService videoProcessingService;

    @PostMapping(path = "/")
    public @ResponseBody Video addVideo(Principal principal, @RequestBody Video video)
            throws HttpStatusBadRequestException {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        if (user == null)
            throw new HttpStatusBadRequestException();
        video.setUser(user);
        return videoService.addVideo(video);
    }

    @PostMapping(path = "/{id}")
    public @ResponseBody Video uploadVideo(Principal principal, @PathVariable Integer id,
            @RequestParam("file") MultipartFile file)
            throws HttpStatusBadRequestException, HttpStatusUnauthorizedException,
            HttpStatusInternalServerErrorException, HttpStatusServiceUnavailableException {
        // find video by id
        Video video = videoService.findById(id);
        if (video == null)
            throw new HttpStatusBadRequestException();

        if (!video.getUser().getUsername().equals(principal.getName()))
            throw new HttpStatusUnauthorizedException();

        // store video
        if (!storageService.storeFile(video, file))
            throw new HttpStatusInternalServerErrorException();
        // send post request to video processing service
        if (!videoProcessingService.videoProcess(video))
            throw new HttpStatusServiceUnavailableException();

        // aggiorna lo stato
        video = videoService.updateStatus(video, "uploaded");
        return video;
    }

    @GetMapping(path = "/")
    public @ResponseBody List<Video> getsVideos() {
        return videoService.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String getVideo(@PathVariable Integer id) throws HttpStatusNotFoundException {
        Video video = videoService.findById(id);
        if (video == null)
            throw new HttpStatusNotFoundException();
        String url = storageService.getRelativePath(video).toString();
        return "redirect:/" + url;

    }
}