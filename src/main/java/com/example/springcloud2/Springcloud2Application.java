package com.example.springcloud2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Springcloud2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springcloud2Application.class, args);
    }
}
