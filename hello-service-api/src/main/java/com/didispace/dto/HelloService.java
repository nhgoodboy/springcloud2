package com.didispace.dto;


import org.springframework.web.bind.annotation.*;

@RequestMapping("/refactor")
public interface HelloService {

    @GetMapping("/hello4")
    String hello(@RequestParam("name") String name) throws InterruptedException;

    @GetMapping(value = "/hello5")
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @PostMapping(value = "/hello6")
    String hello(@RequestBody User user);
}
