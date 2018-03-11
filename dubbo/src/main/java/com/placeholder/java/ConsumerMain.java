package com.placeholder.java;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.placeholder.xml.service.DemoService;

/**
 * Created by L on 2018/3/4.
 */
public class ConsumerMain {
    public static void main(String[] args) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("consumerapplication");

        RegistryConfig registry = new RegistryConfig("multicast://224.5.6.7:1234");
        // 引用远程服务
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(DemoService.class);

        // 和本地bean一样使用xxxService
        DemoService demoService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        String result = demoService.sayHello(" world");
        System.out.println(result);
    }
}
