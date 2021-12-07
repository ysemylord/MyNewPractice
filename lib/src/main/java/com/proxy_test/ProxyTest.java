package com.proxy_test;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


interface Massage {
    void massage();
}

class Mary implements Massage {

    @Override
    public void massage() {
        System.out.println("mary massage");
    }
}

public class ProxyTest {
    //使用动态代理对Mary进行代理
    public static void main(String[] args) {

        final Mary mary = new Mary();
        Object proxy = Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),//类加载器
                new Class[]{Massage.class},//被代理类实现的接口
                new InvocationHandler() {//句柄
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        System.out.println("record "+method.getName()+" start");
                        method.invoke(mary, objects);
                        return o;
                    }
                });

        ((Massage) proxy).massage();

        proxy();
    }

    private static void proxy(){
        try {
            Class pClass1 = Class.forName("java.lang.reflect.ProxyGenerator");
            Method method = pClass1.getDeclaredMethod("generateProxyClass",String.class,Class[].class);
            method.setAccessible(true);
            System.out.println(method.getName());
            byte[] bytes = (byte[]) method.invoke(null,"Massage$Proxy",new Class[]{Massage.class});
            FileOutputStream fileOutputStream = new FileOutputStream("lib\""+"Massage$Proxy"+".class");
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
