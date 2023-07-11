package com.example.serverapi.api;

import com.example.common.dto.ReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "server")
public interface ServerApi {
    @PostMapping("/server/a")
    String a(@RequestBody ReqDto reqDto);
}
