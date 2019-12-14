package com.unict.riganozito.videoprocessingservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.riganozito.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping(path= "/videos")
public class ProcessingController {

    @Autowired
    ProcessingService processingService;

    private final ObjectMapper mapper = new ObjectMapper();

    // POST http://localhost:8080/videos/process
    @PostMapping(path= "/process")
    public @ResponseBody String process(@RequestBody String video) throws IOException {
        final JsonNode jsonNode = mapper.readTree(video);
        Integer id = jsonNode.findValue("videoId").asInt();
        if(processingService.processVideo(id))
            return String.format("Video with id %d processed.", id);
        else
            return String.format("Cannot process video with id %d.", id);
    }
}