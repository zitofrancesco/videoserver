package com.unict.riganozito.videomanagementservice.services;

import com.unict.riganozito.videomanagementservice.entity.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository repository;

    public void signinUser() {

    }
}
