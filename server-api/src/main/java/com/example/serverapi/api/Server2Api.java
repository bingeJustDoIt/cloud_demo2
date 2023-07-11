package com.example.serverapi.api;

import com.example.common.dto.ReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "server-2",path = "/server2")
public interface Server2Api {
    @PostMapping("/a")
    String a(@RequestBody ReqDto reqDto);
}
