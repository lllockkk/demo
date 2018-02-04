package com.cooolin.springmvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by l on 17-7-31.
 */
@RestController
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

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String form(@RequestBody Test test) {
        System.out.println(test);
        return "result";
    }
}

class Test {
    private int id;
    private String name;

    public Test(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "{ id: " + id + ", name: " + name + " }";
    }
}
