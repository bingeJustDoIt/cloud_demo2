package com.example.server2.controller;

import com.example.common.dto.ReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/server2")
@Slf4j
public class ServerController {
    @Resource
    private Environment environment;
    @PostMapping("/a")
    public String a(@RequestBody ReqDto reqDto){
        String property = environment.getProperty("server.port");
        System.out.println(property);
        log.info("server 收到请求 a:{}",reqDto);
        return "server ok a";
    }
    @PostMapping("/b")
    public String b(@RequestBody ReqDto reqDto){
        log.info("server 收到请求 b:{}",reqDto);
        return "server ok b";
    }
}
