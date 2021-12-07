package com.zero.genericsdemo02.generic_and_reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestBaseQuickAdapter {
    public static void main(String[] args) {
        MyAdapter myAdapter = new MyAdapter();
    }
}

class MyViewHolder extends BaseViewHolder {

    public MyViewHolder(String name) {
        super(name);
    }
}

class MyAdapter extends BaseQuickAdapter<MyViewHolder> {

}

class BaseViewHolder {
    public String name;

    public BaseViewHolder(String name) {
        this.name = name;
    }
}

abstract class BaseQuickAdapter<K extends BaseViewHolder> {

    public BaseQuickAdapter() {
        K viewHolder = onBaseCreateViewHolder();
        String name = viewHolder.name;
        System.out.println(name);
    }

    public K onBaseCreateViewHolder() {
        Class<? extends BaseViewHolder> targetClass = null;
        Class<? extends BaseQuickAdapter> z = this.getClass();
        Type type = z.getGenericSuperclass();//拿到参数化类型
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();//拿到实际类型
            for (int i = 0; i < types.length; i++) {
                Type typeOne = types[i];
                if (typeOne instanceof Class) {
                    Class typeClass = (Class) typeOne;
                    if (BaseViewHolder.class.isAssignableFrom(typeClass)) {
                        targetClass = typeClass;//将实际类型转化为class
                    }
                }
            }
        }

        try {
            if(targetClass == null){
                return null;
            }
            Constructor<? extends BaseViewHolder> constructor = targetClass.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            try {
                return (K) constructor.newInstance("1233");//通过反射调用构造函数，构造对象
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
