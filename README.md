# ClassLoader与Class的理解

对ClassLoader和Class的含义始终比较模糊和混淆，认为这是比较高级的东西，理解起来比较麻烦。今天稍微有些理解，这里写一下。

## 比喻

一店铺中有两种商品，水果和蛋糕。水果分不同的种类，有香蕉、苹果..., 蛋糕也有不同种类，有水果味，奶油味...

一界面有两种控件，显示文字的TextView, 显示图像的ImageView, 界面上有若干个这两种控件。

一个类有两种成员，域和方法，其中有若干个这两种成员。

一个文件，里面是文字，其中的文字是有一定的规则，这些文字描述了两种东西。我们要知道文件中的文字到底是什么，需要将其中的内容加载到内存中。因为我们知道这两种东西是什么东西，那么我们可以抽象成一个类，来描述这些文字。而同时因为文字内容的不同，所以类的实例是不相同的。

## 理解

Class类是什么，Class是描述.class文件内容的类，Class类描述了.class文件的两种东西。而不同的Class描述的.class文件则是不同的。Class的成员具体是什么，是由.class文件的内容来决定的。

.class仅仅是文件，它的内容需要被编程语言所描述，用于编程。

像是HTTP请求一样，它的内容仅仅是一堆文字，但是被描述成了Request, Response, ResponseBody等。

Class类是什么。Java是面向对象的，几乎所有的类型都是以“类”的概念来描述。类是描述一类事物抽象概念，在Java中，使用class来具体描述类。在class中，可以有域、静态域、方法、构造方法、静态方法等等，所有的类都是相似的，都可以具有这些属性。这时，我们再次进行抽象，class也可以被认为是一类事物，使用class Class类描述。这就是Class.

ClassLoader是什么。.class是以文件的方式存在，程序运行，必然需要将其加载到内存，这个过程就是使用ClassLoader来完成的。将.class文件描述的类加载到内存中则是它的功能。

不过需要注意的是，上面的理解完全是以Java平台来说明的，Android平台下，字节码是以dex文件的形式存在的。无论是哪种平台，都是将存储在文件中的字节码加载到内存中。

## ClassLoader加载过程

详细加载过程参考文章[java笔记--理解java类加载器以及ClassLoader类](http://www.cnblogs.com/fingerboy/p/5456371.html)

ClassLoader本身的理解参考文章[Java类加载器深入理解](http://www.codeceo.com/article/java-classloader-learn.html)，同时参考本Demo的示例代码。


## ClassLoader 扩展

在Android中，使用DexClassLoader对ClassLoader进行扩展，因为字节码文件存在的形式不同，所以必然使用到的ClassLoader也不同，不能使用Java平台常用的URLClassLoader来加载类。

DexClassLoader继承自BaseClassLoader, BaseClassLoader还有一个子类PathClassLoader. 

PathClassLoader用于加载Android系统相关的类，不能从网络中加载类。

DexClassLoader可以用于加载从jar, apk（其中需要包含dex文件）中的类。可以用于加载不属于安装应用一部分的代码。

从上面我们就可以看出，DexClassLoader适用于Android平台的动态加载。

除了Android平台，其他平台也会重写ClassLoader, 如Tomcat，它也有两种ClassLoader, CatalinaClassLoader用于加载Tomcat服务器相关的类。WebappClassLoader则用于加载Web应用的类。相对于Android平台需要将.class转换成.dx, Tomcat则更接近于Java本身，使用起来也相对来说更加简单方便。

## TODO

使用git命令提交到GitHub仓库，并总结提交到任意仓库的方式。











