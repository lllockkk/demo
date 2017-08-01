package com.cooolin.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by l on 17-7-31.
 */
@ComponentScan(basePackages = {"spitter"},
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION, value= EnableWebMvc.class)})
public class RootConfig {
}
