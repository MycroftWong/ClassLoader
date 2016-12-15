package com.mycroft.safe.classloader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mycroft on 2016/12/15.
 */
public class ObjectMain {

    public static void main(String[] args) throws Exception {
        final URL url = new File("assets/obj.jar").toURI().toURL();
        // 默认使用AppClassLoader作为 parent
        ClassLoader classLoader = new URLClassLoader(new URL[]{url});

        System.out.println(classLoader.getParent());

        TimeUnit.SECONDS.sleep(1);
        Class<?> klazz = classLoader.loadClass("java.lang.Object");

        // Exception in thread "main" java.lang.NoSuchMethodException: java.lang.Object.getDescription()
        Method method = klazz.getMethod("getDescription");

        System.out.println(method.invoke(null));
    }
}
