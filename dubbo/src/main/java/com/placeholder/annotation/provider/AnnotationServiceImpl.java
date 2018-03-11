package com.placeholder.annotation.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.placeholder.annotation.service.AnnotationService;

/**
 * Created by L on 2018/3/4.
 */
@Service()
public class AnnotationServiceImpl implements AnnotationService {

    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
