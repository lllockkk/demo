package com.placeholder.classloader.hotswap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class HotTest {
    private static final String path = "D:/git-repo/demo/demo/target/classes/com/placeholder/classloader/hotswap/";
    public static IHot hot = new HotImpl();

    public static void main(String[] args) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path p = Paths.get(path);
        p.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        new Thread(() -> {
            try {
                while (true) {
                    WatchKey watchKey = watchService.take();
                    List<WatchEvent<?>> events = watchKey.pollEvents();
                    for (WatchEvent<?> event : events) {
                        System.out.println("[" + event.context() + "]" + event.kind());

                        if (event.kind().toString().equals("ENTRY_MODIFY") && event.context().toString().equals("HotImpl.class")) {
                            hotLoad("HotImpl.class");
                        }
                    }
                    watchKey.reset();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void hotLoad (String classFileName) {
        System.out.println("before load: " + hot.test());
        Object o = new MyClassLoader().FindNewClass(path + classFileName);
//        if (o.getClass().getName().equals(hot.getClass().getName())) {
//            System.out.println("equals");
//        }
        if (o != null) {
            hot = MyClassLoader.ReLoadClass(o);
        }
        System.out.println("after load: " + hot.test());
    }
}

class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Object FindNewClass(String classPath) {
        try {
            byte[] b = getBytes(classPath);
            return defineClass(null, b, 0, b.length).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T ReLoadClass(Object o) {
        return (T) o;
    }

    private byte[] getBytes(String filename) throws IOException {
        File file = new File(filename);
        byte raw[] = new byte[(int) file.length()];
        FileInputStream fin = new FileInputStream(file);
        fin.read(raw);
        fin.close();
        return raw;
    }
}






















