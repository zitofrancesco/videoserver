package com.unict.riganozito.videoprocessingservice.controller;

import com.unict.riganozito.videoprocessingservice.request.VideoRequest;
import com.unict.riganozito.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/videos")
public class ProcessingController {

    @Autowired
    ProcessingService processingService;

    // POST http://localhost:8090/videos/process
    @PostMapping(path = "/process", consumes = "application/JSON", produces = "application/JSON")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String process(@RequestBody VideoRequest video) {
        Integer id = video.getVideoId();
        processingService.processVideoAsync(id);
        return String.format("Video with id %s is processing.", id);
    }
}
