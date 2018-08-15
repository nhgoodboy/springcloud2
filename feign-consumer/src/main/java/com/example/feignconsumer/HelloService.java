package com.example.feignconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("hello-service")
public interface HelloService {

    @RequestMapping("/hello")
    String hello();

    @GetMapping("/hello1")
    String hello(@RequestParam("name") String name);

    @GetMapping(value = "/hello2")
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @PostMapping(value = "/hello3")
    String hello(@RequestBody User user);

}
