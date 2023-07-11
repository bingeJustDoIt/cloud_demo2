package com.example.client.controller;

import com.example.client.feign.ToGatewayApi;
import com.example.common.dto.ReqDto;
import com.example.common.util.ThreadUtil;
import com.example.serverapi.api.Server2Api;
import com.example.serverapi.api.ServerApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {
    @Resource
    private ServerApi serverApi;
    @Resource
    private Server2Api server2Api;
    @Resource
    private ToGatewayApi toGatewayApi;
    @Resource
    ThreadUtil threadUtil;

    @GetMapping("/a")
    public String a(Integer type) {
        log.info("begin...");
        threadUtil.submit(()->{
            String a = serverApi.a(ReqDto.builder().type(type).data("测试").build());
            log.info("res :{}", a);
        });
        return "client ok";
    }
    @GetMapping("/b")
    public String b(Integer type) {
        String a = server2Api.a(ReqDto.builder().type(type).data("测试").build());
        log.info("res :{}", a);
        return "client ok";
    }
    @GetMapping("/c")
    public String c(Integer type) {
        String a = toGatewayApi.a(ReqDto.builder().type(type).data("测试gateway").build());
        log.info("res :{}", a);
        return "client ok";
    }
}
