package com.unict.riganozito.videoprocessingservice.controller;

import com.unict.riganozito.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path= "/videos")
public class ProcessingController {

    @Autowired
    ProcessingService processingService;

    // POST http://localhost:8080/videos/process
    @PostMapping(path= "/process")
    public boolean process(@RequestBody Integer id){
        return processingService.processVideo(id);
    }
}
