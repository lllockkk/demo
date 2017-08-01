package com.cooolin.javeweb.web.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by l on 17-8-1.
 */
@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("中文before async.");
        writer.flush();

        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(5*1000L);
        asyncContext.start(() -> {
            try {
                // todo 不添加sleep报错？
                Thread.sleep(1L);
                writer.println("中文async content.");
                writer.flush();
                asyncContext.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        writer.println("中文after async content.");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
