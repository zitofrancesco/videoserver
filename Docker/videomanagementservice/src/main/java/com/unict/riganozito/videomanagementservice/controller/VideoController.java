package com.unict.riganozito.videomanagementservice.controller;

import java.util.List;

import com.unict.riganozito.videomanagementservice.services.*;
import com.unict.riganozito.videomanagementservice.entity.User;
import com.unict.riganozito.videomanagementservice.entity.Video;
import com.unict.riganozito.videomanagementservice.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin
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

    private User getUserAuthenticated() throws HttpStatusUnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            throw new HttpStatusUnauthorizedException();
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        if (user == null)
            throw new HttpStatusUnauthorizedException();
        return user;
    }

    @PostMapping(path = "/", consumes = "application/JSON", produces = "application/JSON")
    public @ResponseBody Video addVideo(@RequestBody Video video) throws HttpStatusUnauthorizedException {
        User user = getUserAuthenticated();
        video.setUser(user);
        return videoService.addVideo(video);
    }

    @PostMapping(path = "/{id}", produces = "application/JSON")
    public @ResponseBody Video uploadVideo(@PathVariable Integer id, @RequestParam("file") MultipartFile file)
            throws HttpStatusBadRequestException, HttpStatusUnauthorizedException, HttpStatusNotFoundException,
            HttpStatusInternalServerErrorException, HttpStatusServiceUnavailableException {

        // find video
        Video video = videoService.findById(id);
        if (video == null)
            throw new HttpStatusNotFoundException();

        // check data
        if (!storageService.checkVideo(file))
            throw new HttpStatusBadRequestException();

        // check user
        User user = getUserAuthenticated();
        if (video.getUser().getId() != user.getId())
            throw new HttpStatusUnauthorizedException();

        // store video
        if (!storageService.storeVideo(video, file))
            throw new HttpStatusInternalServerErrorException();

        // send post request to video processing service
        if (!videoProcessingService.videoProcess(video))
            throw new HttpStatusServiceUnavailableException();

        // update status
        video = videoService.updateStatus(video, "uploaded");
        return video;
    }

    @GetMapping(path = "/", produces = "application/JSON")
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
        return "redirect:" + url;

    }
}