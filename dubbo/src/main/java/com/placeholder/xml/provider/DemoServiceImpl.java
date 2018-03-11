package com.placeholder.xml.provider;

import com.placeholder.xml.service.DemoService;

/**
 * Created by L on 2018/3/4.
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello" + name;
    }
}
