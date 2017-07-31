package com.example.demo.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by l on 17-7-27.
 */
@RestController
public class TestController {
    @RequestMapping(value = "/test1", method = {RequestMethod.GET, RequestMethod.POST})
    public String test1() {
        return "test1";
    }

    @RequestMapping(value = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
    public String test2(@RequestBody Test1 test1, Test2 test2, HttpServletRequest request) {
        return "result";
    }
}

class Test1 {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Test2 {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
