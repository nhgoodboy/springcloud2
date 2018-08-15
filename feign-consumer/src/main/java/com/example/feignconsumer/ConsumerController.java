package com.example.feignconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    private final HelloService helloService;

    @Autowired
    public ConsumerController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping(value = "/feign-consumer")
    public String helloConsumer() {
        return helloService.hello();
    }

    @GetMapping(value = "/feign-consumer2")
    public String helloConsumer2() {
        StringBuilder sb = new StringBuilder();
        sb.append(helloService.hello()).append("\n");
        sb.append(helloService.hello("DIDI")).append("\n");
        sb.append(helloService.hello("DIDI", 30)).append("\n");
        sb.append(helloService.hello(new User("DIDI", 30))).append("\n");
        return sb.toString();
    }

    @Autowired
    RefactorHelloService refactorHelloService;

    @GetMapping(value = "/feign-consumer3")
    public String helloConsumer3() throws InterruptedException {
        StringBuilder sb = new StringBuilder();
//        sb.append(refactorHelloService.hello()).append("\n");
        sb.append(refactorHelloService.hello("MIMI")).append("\n");
        sb.append(refactorHelloService.hello("MIMI", 20)).append("\n");
        sb.append(refactorHelloService.hello(new com.didispace.dto.User("MIMI", 20))).append("\n");
        return sb.toString();
    }
}
