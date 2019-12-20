package com.unict.riganozito.videomanagementservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "/ping")
public class PingController {

    @PostMapping(path = "/")
    public @ResponseBody String postPing(String value) {
        return "Pong " + value;
    }

    @GetMapping(path = "/")
    public @ResponseBody String getPing() {
        return "Pong";
    }

    @PutMapping(path = "/")
    public @ResponseBody String putPing() {
        return "Pong";
    }
}