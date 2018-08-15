package com.example.eurekaclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient client;

    @RequestMapping("/home")
    public String home() {
        return "Hello home";
    }

    @GetMapping("/hello")
    public String index() throws InterruptedException {
//        ServiceInstance instance = client.get
//        int sleepTime = new Random().nextInt(3000);

//        logger.info("sleepTime: " + sleepTime);
//        Thread.sleep(sleepTime);
//        ServiceInstance instance = client.getInstances();
//        logger.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "Hello World";
    }

    @PostMapping("/user")
    public String user(@Valid User user) {
        System.out.println(user.toString());
        return user.toString();
    }

    @GetMapping(value = "/hello1")
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping(value = "/hello2")
    public User hello(@RequestHeader String name, @RequestHeader Integer age) {
        return new User(name, age);
    }

    @PostMapping(value = "/hello3")
    public String hello(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }
}
