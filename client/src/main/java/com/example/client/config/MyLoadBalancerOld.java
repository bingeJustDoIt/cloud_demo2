//package com.example.client.config;
//
//import cn.hutool.core.util.RandomUtil;
//import cn.hutool.extra.spring.SpringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.*;
//import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.http.HttpHeaders;
//import org.springframework.util.ObjectUtils;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * A Round-Robin-based implementation of {@link ReactorServiceInstanceLoadBalancer}.
// *
// * @author Spencer Gibb
// * @author Olga Maciaszek-Sharma
// * @author Zhuozhi JI
// */
//@Slf4j
//public class MyLoadBalancerOld implements ReactorServiceInstanceLoadBalancer {
//
//
//    final AtomicInteger position;
//
//    final String serviceId;
//
//    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
//
//
//    public MyLoadBalancerOld(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
//                             String serviceId) {
//        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
//    }
//
//
//    public MyLoadBalancerOld(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
//                             String serviceId, int seedPosition) {
//        this.serviceId = serviceId;
//        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
//        this.position = new AtomicInteger(seedPosition);
//    }
//
//    @Override
//    public Mono<Response<ServiceInstance>> choose(Request request) {
//        RequestDataContext context = (RequestDataContext) request.getContext();
//        RequestData clientRequest = context.getClientRequest();
//        URI url = clientRequest.getUrl();
//        System.out.println(url);
//        HttpHeaders headers = clientRequest.getHeaders();
//        List<String> path = headers.get("path");
//        System.out.println(path);
//        List<ServiceInstance> server = SpringUtil.getBean(DiscoveryClient.class).getInstances(url.getHost());
//        if (!ObjectUtils.isEmpty(path)) {
//            if (path.get(0).equals("1")) {
//                Optional<ServiceInstance> any = server.stream().filter(x -> x.getPort() == 9002).findAny();
//                if (!any.isPresent()){
//                    log.error("找不到目标服务 9002:{}", url);
//                    return Mono.just(new EmptyResponse());
//                }
//                ServiceInstance serviceInstance = any.get();
//                return Mono.just(new DefaultResponse(serviceInstance));
//            }
//            if (path.get(0).equals("2")) {
//                Optional<ServiceInstance> any = server.stream().filter(x -> x.getPort() == 9003).findAny();
//                if (!any.isPresent()){
//                    log.error("找不到目标服务 9003:{}", url);
//                    return Mono.just(new EmptyResponse());
//                }
//                ServiceInstance serviceInstance = any.get();
//                return Mono.just(new DefaultResponse(serviceInstance));
//            }
//        } else {
//            log.error("找不到目标服务:{}", url);
//            return Mono.just(new EmptyResponse());
//        }
//        //其他情况
//        return Mono.just(new DefaultResponse(server.get(RandomUtil.randomInt(0, server.size()))));
//    }
//
//
//}
