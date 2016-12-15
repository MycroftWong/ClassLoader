package com.mycroft.safe;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Mycroft on 2016/12/6.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        System.out.println(String.class.getClassLoader());

        final URL url = new File("assets/safe.jar").toURI().toURL();
        Class<?> klazz = Class.forName("com.mycroft.safe.User", true, new URLClassLoader(new URL[]{url}));
        Object user = klazz.newInstance();

        Method setName = klazz.getDeclaredMethod("setName", String.class);
        setName.invoke(user, "Mycroft");

        Method getName = klazz.getDeclaredMethod("getName");

        String name = (String) getName.invoke(user);
        System.out.println(name);
    }

}
