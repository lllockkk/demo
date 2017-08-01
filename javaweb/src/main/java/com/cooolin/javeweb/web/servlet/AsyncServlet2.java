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
 * Created by l on 17-7-31.
 */
@WebServlet(value="/async2", asyncSupported=true)
public class AsyncServlet2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        final PrintWriter writer = resp.getWriter();
        writer.println("异步之前输出的内容。");
        writer.flush();
        //开始异步调用，获取对应的AsyncContext。
        final AsyncContext asyncContext = req.startAsync();
        //设置超时时间，当超时之后程序会尝试重新执行异步任务，即我们新起的线程。
        asyncContext.setTimeout(10 * 1000L);
        //新起线程开始异步调用，start方法不是阻塞式的，它会新起一个线程来启动Runnable接口，之后主程序会继续执行
        asyncContext.start(() -> {
            try {
                Thread.sleep(2 * 1000L);
                writer.println("异步调用之后输出的内容。");
                writer.flush();
                //异步调用完成，如果异步调用完成后不调用complete()方法的话，异步调用的结果需要等到设置的超时
                //时间过了之后才能返回到客户端。
                asyncContext.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        writer.println("可能在异步调用前输出，也可能在异步调用之后输出，因为异步调用会新起一个线程。");
        writer.flush();
    }

}
