package com.xb.classloader;

/**
 * 封装加载类的信息
 * Created by Peter on 2017/12/27.
 */
public class LoadInfo {
    //自定义的类加载
    private MyClassLoader myClassLoader;
    //记录要加载的类的时间戳-->加载的时间
    private Long loadTime;
    private BasesManager basesManager;

    public LoadInfo(MyClassLoader myClassLoader, Long loadTime) {
        super();
        this.myClassLoader = myClassLoader;
        this.loadTime = loadTime;
    }
}
