package com.cooolin.javeweb.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by L on 2017/8/3.
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setHeader("Transfer-Encoding", "chunked");
        resp.getWriter().println("hello");
        resp.flushBuffer();
        resp.getWriter().println("world");
    }
}
