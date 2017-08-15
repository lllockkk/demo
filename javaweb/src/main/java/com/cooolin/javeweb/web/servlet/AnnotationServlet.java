package com.cooolin.javeweb.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by l on 17-7-31.
 */
@WebServlet(name="annotation",urlPatterns="/annotation")
public class AnnotationServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("init AnnotationServlet");
    }

    @Override
    public void destroy() {
        System.out.println("destroy AnnotationServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getCharacterEncoding());
        /*Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + req.getHeader(headerName));
        }*/

        Map<String, String[]> parameterMap = req.getParameterMap();
        System.out.println(req.getProtocol());
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        resp.getWriter().write("中文annotation get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line;
        while ((line=br.readLine()) != null) {
            System.out.println(line);
        }
        resp.getWriter().write("中文annotation post");
    }
}
