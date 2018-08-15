package com.example.ribbonconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

//    @Autowired
//    RestTemplate restTemplate;

    @GetMapping(value = "/ribbon-consumer")
    public String helloConsumer() {
        return helloService.helloService();
//        return "receive: " + restTemplate.getForEntity("http://hello-service/home", String.class).getBody();
    }

//    @GetMapping(value = "/ribbon-consumer-user")
//    public String userConsumer(){
//        User user = new User("feifei", 19, "male");
//        System.out.println(user.toString());
//        return "receive: " + restTemplate.postForEntity("http://hello-service/user", user, String.class).getBody();
//    }
}
