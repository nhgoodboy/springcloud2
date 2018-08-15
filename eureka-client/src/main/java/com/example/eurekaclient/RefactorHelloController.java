package com.example.eurekaclient;

import com.didispace.dto.HelloService;
import com.didispace.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RefactorHelloController implements HelloService {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient client;

    @Override
    public String hello(@RequestParam("name") String name) throws InterruptedException {
        int sleepTime = new Random().nextInt(3000);

        logger.info("sleepTime: " + sleepTime);
        Thread.sleep(sleepTime);
//        ServiceInstance instance = client.getInstances();
//        logger.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "Hello " + name;
    }

    @Override
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return new User(name, age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }
}
