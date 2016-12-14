package com.mycroft.safe.classloader;

import com.sun.nio.zipfs.ZipDirectoryStream;
import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Java中：ClassLoader 和 View, ViewParent有相似的层级关系
 * {@link ClassLoader} 默认有三种：Bootstrap ClassLoader, Extension ClassLoader, System ClassLoader(Application ClassLoader)
 * 引导加载器、扩展加载器、系统加载器（应用加载器）
 * 从下面可以看出，三种ClassLoader都是继承自 {@link URLClassLoader}, 而{@link URLClassLoader}则是通过路径来加载jar包和资源
 * <p>
 * 加载功能：
 * Bootstrap ClassLoader: 只是一个抽象的概念，实际上并不是一个类，通过它来加载jdk自己的jar包
 * Extension ClassLoader: 实际存在，用于加载java的扩展类库，默认加载 JAVA_HOME/jre/lib/ext下的所有jar
 * System ClassLoader(Application ClassLoader): 加载CLASSPATH下的包，通常也是我们需要执行的代码的地方
 * <p>
 * 功能：
 * Bootstrap ClassLoader: 加载jdk类的Class, 例如String.class, 但是实际上 Bootstrap ClassLoader 是不存在的，所以 String.class.getClassLoader() 返回 null
 * Extension ClassLoader: 加载java的扩展类库中的Class，如 JAVA_HOME/jre/lib/ext/zipfs.jar 包中的 ZipDirectoryStream.class.getClassLoader() 返回的则是 ExtClassLoader
 * System ClassLoader(Application ClassLoader): 例如当前类的Class的ClassLoader返回的则是 AppClassLoader
 * <p>
 * Created by Mycroft on 2016/12/7.
 */
public class ClassLoaderMain {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());  // null
        System.out.println(ZipDirectoryStream.class.getClassLoader());  // sun.misc.Launcher$ExtClassLoader@330bedb4
        System.out.println(ClassLoaderMain.class.getClassLoader());    // sun.misc.Launcher$AppClassLoader@7d4991ad

        System.out.println("------------------------------------------------------");

        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        ClassLoader classLoader = systemClassLoader;
        while (classLoader != null) {
            System.out.println(classLoader);
            // sun.misc.Launcher$AppClassLoader@7d4991ad
            // sun.misc.Launcher$ExtClassLoader@677327b6

            classLoader = classLoader.getParent();
        }

        System.out.println(Launcher.getLauncher()); // sun.misc.Launcher@14ae5a5
        System.out.println(Launcher.getLauncher().getClassLoader());    // sun.misc.Launcher$AppClassLoader@7d4991ad

        URLClassPath urlClassPath = Launcher.getBootstrapClassPath();
        System.out.println(urlClassPath);   // sun.misc.URLClassPath@7f31245a

        final URL[] urls = urlClassPath.getURLs();
        if (urls != null) {
            for (URL url : urls) {
                System.out.println(url);
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/resources.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/rt.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/sunrsasign.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jsse.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jce.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/charsets.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jfr.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/classes
            }
        }

        System.out.println("------------------------------------------------------");

        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        if (extensionClassLoader != null && extensionClassLoader instanceof URLClassLoader) {
            final URL[] extensionURLs = ((URLClassLoader) extensionClassLoader).getURLs();

            if (extensionURLs != null) {
                for (URL url : extensionURLs) {
                    System.out.println(url);
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/access-bridge-64.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/cldrdata.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/dnsns.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/jaccess.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/jfxrt.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/localedata.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/nashorn.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunec.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunjce_provider.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunmscapi.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunpkcs11.jar
                    // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/zipfs.jar
                }
            }
        }

        System.out.println("------------------------------------------------------");

        if (systemClassLoader instanceof URLClassLoader) {
            final URL[] systemURLs = ((URLClassLoader) systemClassLoader).getURLs();
            for (URL url : systemURLs) {
                System.out.println(url);
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/charsets.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/deploy.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/access-bridge-64.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/cldrdata.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/dnsns.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/jaccess.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/jfxrt.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/localedata.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/nashorn.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunec.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunjce_provider.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunmscapi.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/sunpkcs11.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/ext/zipfs.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/javaws.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jce.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jfr.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jfxswt.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/jsse.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/management-agent.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/plugin.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/resources.jar
                // file:/C:/Program%20Files/Java/jdk1.8.0_111/jre/lib/rt.jar
                // file:/E:/Documents/IdeaProjects/Safe/build/classes/main/
                // file:/C:/Users/Mycroft/.gradle/caches/modules-2/files-2.1/com.google.code.gson/gson/2.8.0/c4ba5371a29ac9b2ad6129b1d39ea38750043eff/gson-2.8.0.jar
                // file:/C:/Program%20Files%20(x86)/JetBrains/IntelliJ%20IDEA%202016.3/lib/idea_rt.jar
            }
        }

    }
}
