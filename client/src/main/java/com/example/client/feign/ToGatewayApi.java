package com.example.client.feign;

import com.example.common.dto.ReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gateway")
public interface ToGatewayApi {
    @PostMapping("/server/a")
    String a(@RequestBody ReqDto reqDto);
}
