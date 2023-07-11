//package com.example.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Configuration
//@Slf4j
//public class DynamicFeignConfig implements RequestInterceptor {
//    @Resource
//    private DiscoveryClient discoveryClient;
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        log.info("begin");
//        List<String> services = discoveryClient.getServices();
////        requestTemplate.target("http://192.168.1.138");
////        Method method = requestTemplate.methodMetadata().method();
////        Class<?> declaringClass = method.getDeclaringClass();
////        FeignClient annotation = declaringClass.getAnnotation(FeignClient.class);
////        if (!annotation.value().equals("server")){
////            return;
////        }
////
//
////        String body = new String(requestTemplate.body(), StandardCharsets.UTF_8);
////        JSONObject parseObj = JSONUtil.parseObj(body);
////        Integer type = parseObj.get("type", Integer.class);
////        String url = requestTemplate.url();
////        log.info("原本url：{}",url);
////        if (type == 1) {
////            requestTemplate.target("http://server:9002");
////        } else {
////            requestTemplate.target("http://server:9003");
////        }
//        log.info("over");
//    }
//}
