package com.example.demo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by L on 2017/8/21.
 */
@RestController
@RequestMapping("/jsonp")
public class JsonpController {

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String callback = request.getParameter("callback");
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        result.put("data", "hello jsonp");
        result.put("errorCode", "0");
        result.put("errorMessage", "success");

        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.write(callback + "(" + mapper.writeValueAsString(result) + ")");
//        writer.write("while(true) {alert('1')}");
        writer.flush();
        writer.close();
    }
}
