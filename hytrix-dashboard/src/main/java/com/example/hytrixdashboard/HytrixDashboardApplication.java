package com.example.hytrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class HytrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HytrixDashboardApplication.class, args);
    }
}
