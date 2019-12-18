package com.unict.riganozito.apigateway.controller;

import com.unict.riganozito.apigateway.service.ApiGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.unict.riganozito.apigateway.entity.Request;

import javax.transaction.Transactional;

@Controller
@RequestMapping(path="/vms")
public class RequestController {

    @Autowired
    ApiGatewayService service;

    @GetMapping("/")
    public Request preFilterGetRequest(@RequestHeader HttpHeaders header){ return service.addStatistics(header); }

    @PutMapping("/")
    public Request preFilterPutRequest(@RequestHeader HttpHeaders header){
        return service.addStatistics(header);
    }

    @PostMapping("/")
    public Request preFilterPostRequest(@RequestHeader HttpHeaders header){
        return service.addStatistics(header);
    }

    @DeleteMapping("/")
    public Request preFilterDeleteRequest(@RequestHeader HttpHeaders header){
        return service.addStatistics(header);
    }
}
