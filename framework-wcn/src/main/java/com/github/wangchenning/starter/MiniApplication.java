package com.github.wangchenning.starter;

import com.github.wangchenning.beans.BeanFactory;
import com.github.wangchenning.core.ClassScanner;
import com.github.wangchenning.web.handler.HandlerManager;
import com.github.wangchenning.web.server.TomcatServer;

import java.util.List;


public class MiniApplication {
    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello wcn-spring!");

        System.out.println("      *             ,");
        System.out.println("                  _/^\\_");
        System.out.println("                 <     >");
        System.out.println("*                 /.-.\\         *");
        System.out.println("         *        `/&\\`                   *");
        System.out.println("                 ,@.*;@,");
        System.out.println("                /_o.I %_\\    *");
        System.out.println("   *           (`'--:o(_@;");
        System.out.println("              /`;--.,__ `')             *");
        System.out.println("             ;@`o % O,*`'`&\\");
        System.out.println("       *    (`'--)_@ ;o %'()\\      *");
        System.out.println("            /`;--._`''--._O'@;");
        System.out.println("           /&*,()~o`;-.,_ `\"\"`)");
        System.out.println("*          /`,@ ;+& () o*`;-';\\");
        System.out.println("          (`\"\"--.,_0o*`;-' &()\\");
        System.out.println("          /-.,_    ``''--....-'`) *");
        System.out.println("     *    /@%;o`:;'--,.__   __.'\\");
        System.out.println("         ;*,&(); @ % &^;~`\"`o;@();         *");
        System.out.println("         /(王晨宁的  技术博客   空间\\");
        System.out.println("         `\"=\"==\"\"==,,,.,=\"==\"===\"`");
        System.out.println("      __.----.---''#####---...___...-----._");

        System.out.println("Hello wcn-spring!");




        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            HandlerManager.resolveMappingHandler(classList);
            classList.forEach(it-> System.out.println(it.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
