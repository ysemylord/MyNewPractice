package com.zero.genericsdemo02.demo01;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XiaoMingMa extends Person {

    public void addFruit(RawPlate rawPlate){
        rawPlate.set(new Apple());
    }

    /**
     * 这里不是泛型方法，这只是一个普通方法，只是使用了AIPlate<Banana>这个泛型类做形参而已
     * @param aiPlate
     */
    public  void addFruitToAi(AIPlate<Banana> aiPlate){
//        aiPlate.set(new Apple());
        aiPlate.set(new Banana());
    }



    public void add1(){
        try {
            Plate<? extends Fruit> fruitplate = new AIPlate<Apple>(new Apple());

            Method m = fruitplate.getClass().getMethod("set", java.lang.Object.class);
            m.setAccessible(true);
            m.invoke(fruitplate,new Banana());

            fruitplate.set(null);
            Fruit fruit = fruitplate.get();
            System.out.println("fruit: " +fruit);
        }catch (Exception e){}

    }

    public void add2(Plate<? extends Fruit> fruitplate){
        try {

            Method m = fruitplate.getClass().getMethod("set", java.lang.Object.class);
            m.setAccessible(true);
            m.invoke(fruitplate,new Banana());

            Fruit fruit = fruitplate.get();
            System.out.println("fruit: " +fruit);



        }catch (Exception e){}
    }

    public  double sumOfList(List<? extends Number> list) {
        System.out.println("sumOfList: " + list.getClass());
        list.add(null);
        System.out.println("list: " + list.getClass());
        //副作用
//        list.add(1); //上限 in  只读，但这不是严格限制
        //反射调用 最新的不能调用
        // Caused by: java.lang.UnsupportedOperationException
        Class<?> clazz = list.getClass();
        System.out.println(clazz);
        try {
//            方法原型 boolean add(E e)
//            list = new ArrayList<Number>();
//            clazz = list.getClass();
            Method addMethod = clazz.getMethod("add", java.lang.Object.class);
            addMethod.setAccessible(true);
            addMethod.invoke(list,1000);
            System.out.println("list: " + list.toString());

        } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        double s = 0.0;
        for (Number n : list)
            s += n.doubleValue();
        return s;
    }
}
