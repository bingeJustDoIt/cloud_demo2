package com.example.client;

import com.example.client.lb.MyLoadBalancerConfiguration;
import com.example.client.lb.MyLoadBalancerConfiguration2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.example.serverapi.api", "com.example.client.feign"})
//@RibbonClient(name = "client")
//@LoadBalancerClients(defaultConfiguration = MyLoadBalancerConfiguration.class)
@LoadBalancerClients(
        value = {
                @LoadBalancerClient(value = "server",configuration = MyLoadBalancerConfiguration.class),
                @LoadBalancerClient(value = "server-2",configuration = MyLoadBalancerConfiguration2.class)
        }
)
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
