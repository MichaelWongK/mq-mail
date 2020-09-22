package com.michealwang.mqmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqMailApplication.class, args);
    }

}
