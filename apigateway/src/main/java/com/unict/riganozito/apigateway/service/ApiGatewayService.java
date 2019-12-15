package com.unict.riganozito.apigateway.service;

import com.unict.riganozito.apigateway.entity.Request;
import com.unict.riganozito.apigateway.entity.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class ApiGatewayService {

    @Autowired
    RequestRepository repository;

    public Request addStatistics(Request request){
        return repository.save(request);
    }
}
