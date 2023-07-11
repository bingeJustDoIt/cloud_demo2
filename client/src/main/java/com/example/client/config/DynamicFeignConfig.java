package com.example.client.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class DynamicFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("begin");
        Method method = requestTemplate.methodMetadata().method();
        Class<?> declaringClass = method.getDeclaringClass();
        FeignClient annotation = declaringClass.getAnnotation(FeignClient.class);
        if (!annotation.value().equals("server")){
            return;
        }

        String body = new String(requestTemplate.body(), StandardCharsets.UTF_8);
        JSONObject parseObj = JSONUtil.parseObj(body);
        Integer type = parseObj.get("type", Integer.class);
        String url = requestTemplate.url();
        log.info("原本url：{}",url);
        if (type == 1) {
//            requestTemplate.target("http://server:9002");
            requestTemplate.header("path","1");
        } else {
            requestTemplate.header("path","2");
        }
        log.info("over");
    }



}
