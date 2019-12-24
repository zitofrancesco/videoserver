package com.unict.riganozito.videomanagementservice.controller;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${SPRING_PROFILES_ACTIVE:none}")
    private String profile;

    @Value("${videoservice.properties:none}")
    private String mode;

    private String getSigned() {
        return "\n"+profile + " " + mode;
    }

    @PostMapping(path = "/")
    public @ResponseBody String postPing(String value) {
        return "Pong " + value+ getSigned();
    }

    @GetMapping(path = "/")
    public @ResponseBody String getPing() {
        return "Pong" + getSigned();
    }

    @PutMapping(path = "/")
    public @ResponseBody String putPing() {
        return "Pong" + getSigned();
    }

}
