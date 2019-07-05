package com.github.wangchenning.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by Administrator on 2017/12/12.
 */
public class ScanUtil {

    //定义一个set来保存完整类名
    private static List<String> classNames = new ArrayList<String>();


    public static List<String> scan(String packageName) {
        if (packageName == null) {//抛异常
            throw new RuntimeException("The path can not be null.");
        }
        //传入的包名格式为 edu.nf  进行转换
        String packagePath = packageName.replace(".", "\\");
        //根据当前线程当前执行类获取url
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            //枚举获得一个路径
            Enumeration<URL> urls = loader.getResources(packagePath);
            while (urls.hasMoreElements()) {//如果不为空
                URL url = urls.nextElement();//获取路径
                System.out.println(url + "111");
                if ("file".equals(url.getProtocol())) {//如果是文件
                    String path = URLDecoder.decode(url.getPath(), "utf-8");//处理中文乱码
                    scanFromDir(path, packageName);//进行类扫描，保存Set集合
                }
                if ("jar".equals(url.getProtocol())) {//如果是jar
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    String pathjar = URLDecoder.decode(String.valueOf(connection), "utf-8");
                    scanFromDir(pathjar, packageName);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Resolve path error.", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classNames;
    }


    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath    文件目录
     * @param packageName 包名
     */
    public static List<Class<?>> scanFromDir(String filePath, String packageName) throws ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        File[] files = new File(filePath).listFiles();
        packageName = packageName + ".";
        for (File childFile : files) { //遍历文件
            if (childFile.isDirectory()) {//还是文件夹就继续递归
                scanFromDir(childFile.getPath(), packageName + childFile.getName());
            } else {
                String fileName = childFile.getName();//文件名
                if (fileName.endsWith(".class")) {//判断文件名是否是.class结尾
                    if (packageName.charAt(0) == '.') {//判断开头是否有'.',有的话截取
                        packageName = packageName.substring(1, packageName.length());
                    }
                    //拼接字符串，得到完整类名 如：edu.nf.beans.util.ScanUtil
                    String className = packageName + fileName.replace(".class", "");
                    classNames.add(className);
                    list.add(Class.forName(className));
                }
            }
        }
        return list;
    }

//    public static void main(String[] args) {
//        Set<String> classNames=scan("edu.nf");
//        for(String path:classNames){
//            System.out.println(path);
//        }
//    }

    //    private static ArrayList<Object> scanFiles = new ArrayList<Object>();
    private static List<Class<?>> classes = new ArrayList<>();

    public static List<Class<?>> scanFilesWithRecursion(String folderPath, String packageName, String d) throws RuntimeException {

        ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            throw new RuntimeException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        if (directory.isDirectory()) {
            File[] filelist = directory.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                /**如果当前是文件夹，进入递归扫描文件夹**/
                if (filelist[i].isDirectory()) {
                    //绝对路径名字符串
                    dirctorys.add(filelist[i].getAbsolutePath());
                    /**递归扫描下面的文件夹**/
                    scanFilesWithRecursion(filelist[i].getAbsolutePath(), packageName, d);
                }
                /**非文件夹**/
                else {
//                    scanFiles.add(filelist[i].getAbsolutePath());

//                    String s = packageName + "." + filelist[i].getName().substring(0,filelist[i].getName().length()-6);
                    String s = packageName + "." + filelist[i].getPath().replace("\\", ".").replace(d.substring(1).replace("/", ".") + ".", "");


                    try {
                        classes.add(Class.forName(s.substring(0, s.length() - 6)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

//                        classes.addAll(Class.forName(filelist[i].getName().substring(0, filelist[i].getName().length() - 6)));
//                    System.out.println(filelist[i].getName());
                }
            }
        }
        return classes;
    }


}