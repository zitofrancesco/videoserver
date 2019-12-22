package com.unict.riganozito.videoprocessingservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.riganozito.videoprocessingservice.exception.HttpStatusBadRequestException;
import com.unict.riganozito.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping(path = "/videos")
public class ProcessingController {

    @Autowired
    ProcessingService processingService;

    private final ObjectMapper mapper = new ObjectMapper();

    // POST http://localhost:8080/videos/process
    @PostMapping(path = "/process")
    @ResponseStatus(HttpStatus.PROCESSING)
    public @ResponseBody String process(@RequestBody String video) throws IOException, HttpStatusBadRequestException {
        final JsonNode jsonNode = mapper.readTree(video);
        String id = jsonNode.findValue("videoId").toString();
        if (processingService.processVideo(id))
            return String.format("Video with id %s processed.", id);
        else
            throw new HttpStatusBadRequestException(String.format("Cannot process video with id %s.", id));
    }
}
