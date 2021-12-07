package com;


import com.zero.genericsdemo02.demo01.Apple;
import com.zero.genericsdemo02.demo01.Banana;
import com.zero.genericsdemo02.demo01.Fruit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TongPeiHu {


    private void test0() {
        //没有泛型通配符出现的问题
        List<Fruit> fruits = new ArrayList<>();
        getFruit(fruits);

        List<Apple> apples = new ArrayList<>();
        getFruit(apples);

    }

    private void getFruit(List<? extends Fruit> fruits){
        for (Fruit fruit:fruits) {
            System.out.println(fruit.toString());
        }
    }

    private void test1(List<? extends Fruit> fruits) {
        //fruits.add(new Apple());
    }

    private void test2(){
        List<Apple> apples = new ArrayList<>();
        put(apples);
        List<Fruit> fruits = new ArrayList<>();
        put(fruits);
    }
    private void put(List<? super Apple> apples){
        apples.add(new Apple());
    }

    private void test3(List<? super Apple> apples){
        //Fruit fruit =apples.get(0);
    }

    //
    private void test4(){
        //Collections.copy();
    }
}
