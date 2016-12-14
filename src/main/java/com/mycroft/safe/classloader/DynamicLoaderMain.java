package com.mycroft.safe.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 一个动态加载jar包的示例
 * Created by Mycroft on 2016/12/7.
 */
public class DynamicLoaderMain {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        if (appClassLoader == null || !(appClassLoader instanceof URLClassLoader)) {
            return;
        }

        final URL url = new File("assets/safe.jar").toURI().toURL();
        // 下面两种方法都可以
//        final URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, appClassLoader);
        final URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});

        Class<?> userClass = urlClassLoader.loadClass("com.mycroft.safe.User");

        Method setMethod = userClass.getMethod("setName", String.class);
        Object user = userClass.newInstance();

        setMethod.invoke(user, "Mycroft");

        Method getMethod = userClass.getMethod("getName");

        String name = (String) getMethod.invoke(user);
        System.out.println(name);
    }
}
