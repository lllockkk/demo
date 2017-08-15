package com.placeholder.concurrent;

import jdk.management.resource.internal.inst.SocketInputStreamRMHooks;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/7.
 * I/O 阻塞的线程不能用interrupt打断，可以通过关闭使线程阻塞的资源来中断线程
 */
public class _16CloseResource {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InputStream is = new Socket("localhost", 8080).getInputStream();
        executor.execute(new IOBlocked(is));

        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Shutting down all threads");
        executor.shutdownNow();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Closing " + is.getClass().getName());
        is.close();
    }
}
