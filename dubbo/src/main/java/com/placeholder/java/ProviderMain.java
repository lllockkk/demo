package com.placeholder.java;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.placeholder.xml.provider.DemoServiceImpl;
import com.placeholder.xml.service.DemoService;

import java.io.IOException;

/**
 * Created by L on 2018/3/4.
 */
public class ProviderMain {
    public static void main(String[] args) throws IOException {
        DemoService demoService = new DemoServiceImpl();
        ApplicationConfig application = new ApplicationConfig();
        application.setName("providerapplication");

        // registry
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("multicast://224.5.6.7:1234");
//        registry.setUsername("username");
//        registry.setPassword("password");
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);

        ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(application);
        serviceConfig.setRegistry(registry); // 多个注册中心可以用setRegistries()
        serviceConfig.setProtocol(protocol); // 多个协议可以用setProtocols()
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRef(demoService);
//        serviceConfig.setVersion("1.0.0");

        serviceConfig.export();

        System.in.read();
    }
}
