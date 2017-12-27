package com.xb.classloader;

/**
 * BaseManager的子类，子类需要实行java类的热加载功能
 * Created by Peter on 2017/12/27.
 */
public class MyManager implements BasesManager{

    @Override
    public void logic() {
        System.out.println("实现java类的热加载案例");
    }
}
