package com.placeholder.io;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class _18WatchServiceDemo {
    private static final String path = "D:/test";
    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path p = Paths.get(path);
        p.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    WatchKey watchKey = watchService.take();
                    List<WatchEvent<?>> events = watchKey.pollEvents();
                    for (WatchEvent<?> event : events) {
                        Object context = event.context();
                        WatchEvent.Kind<?> kind = event.kind();
                        System.out.println("[" + path + "/" + context + "]文件发生了[" + kind + "]事件");
                    }
                    watchKey.reset();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(false);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                watchService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
