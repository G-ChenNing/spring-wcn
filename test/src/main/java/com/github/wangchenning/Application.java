package com.github.wangchenning;

import com.github.wangchenning.starter.MiniApplication;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        MiniApplication.run(Application.class, args);
    }
}
