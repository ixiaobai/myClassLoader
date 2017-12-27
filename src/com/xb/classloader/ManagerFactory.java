package com.xb.classloader;

import sun.rmi.runtime.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static jdk.nashorn.internal.objects.Global.load;

/**
 * 加载manager的工厂
 * Created by Peter on 2017/12/27.
 */
public class ManagerFactory {
    //记录热加载类的加载信息
    private static final Map<String,LoadInfo> loadTimeMap = new HashMap<String,LoadInfo>();
    //要加载的类的classpath
    public static final String CLASS_PATH="C:/Users/Peter/IdeaProjects/classLoader";
    //实现热加载类的全名称（包名+类名）
    public static final String MY_MANAGER="C:/Users/Peter/IdeaProjects/classLoader/src/com/xb/classloader/MyManager.java";

    public static BasesManager getManager(String className) {
        File loadFile = new File(CLASS_PATH+className.replace("\\.","/"));
        Long lastModified = loadFile.lastModified();
        //loadTimeMap不包含className为key的LoadInfo信息，证明这个类没有被加载，那么需要加载这个类到JVM
        if(loadTimeMap.get(className)==null) {
            load(className,lastModified);
        } //加载类的时间戳发生了变化，同样要重新加载这个类到JVM
        else if(loadTimeMap.get(className).getLoadTime()!=lastModified) {
            load(className,lastModified);
        }
        return loadTimeMap.get(className).getBasesManager();
    }

    private static void load(String className, Long lastModified) {
        MyClassLoader myClassLoader = new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BasesManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(myClassLoader,lastModified);
        loadInfo.setBasesManager(manager);
        loadTimeMap.put(className,loadInfo);

    }

    //以反射的方式创建BaseManager对象
    private static BasesManager newInstance(Class<?> loadClass) {
        try {
            return loadClass.getConstructor(new Class[]{new Object(){}});
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
