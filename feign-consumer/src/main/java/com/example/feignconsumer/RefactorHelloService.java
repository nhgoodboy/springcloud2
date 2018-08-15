package com.example.feignconsumer;

import com.didispace.dto.HelloService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "hello-service", fallback = HelloServiceFallback.class)
public interface RefactorHelloService extends HelloService {

}
