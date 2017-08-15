package com.cooolin.springmvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by l on 17-7-31.
 */
@Controller
public class TestController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request) {
        Enumeration<String> e = request.getAttributeNames();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }

        System.out.println("============");
        System.out.println(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        System.out.println(WebApplicationContext.CONTEXT_ATTRIBUTES_BEAN_NAME);
        Object obj = request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        AnnotationConfigWebApplicationContext ctx = (AnnotationConfigWebApplicationContext) obj;
        return "test";
    }
}
