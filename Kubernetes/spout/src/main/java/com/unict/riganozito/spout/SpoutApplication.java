package com.unict.riganozito.spout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpoutApplication.class, args);
    }

}
