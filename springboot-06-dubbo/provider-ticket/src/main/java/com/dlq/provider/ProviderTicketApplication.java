package com.dlq.provider;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、将服务提供者注册到注册中心
 * 2、配置dubbo的扫描包和注册中心地址
 * 3、使用@DubboService发布服务
 */
@DubboComponentScan(basePackages = "com.dlq.provider.service")
@SpringBootApplication
public class ProviderTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderTicketApplication.class, args);
    }

}
