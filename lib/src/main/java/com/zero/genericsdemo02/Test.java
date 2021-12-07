package com.zero.genericsdemo02;

import com.zero.genericsdemo02.demo01.AIPlate;
import com.zero.genericsdemo02.demo02.Apple;
import com.zero.genericsdemo02.demo02.Fruit;
import com.zero.genericsdemo02.demo02.Plate;

class BigPlate<T> extends AIPlate<T>{

}

class ColorPlate<K,T> extends BigPlate<T>{

}

public class Test {
    public static void main(String[] args) {
        //Apple的父类是Fruit
        //AIPlate<Apple> appleAIPlate = new AIPlate<>();
        //AIPlate<Fruit> fruitAIPlate = new AIPlate<>();
        //AIPlate<Fruit> fruitAIPlate1 = (AIPlate<Fruit>) appleAIPlate;

        AIPlate<Apple> appleAIPlate = new AIPlate<>();
        BigPlate<Apple> bigPlate = new BigPlate<>();
        ColorPlate<Integer,Apple> colorPlate = new ColorPlate<>();
        //colorPlate的父类是bigPlate，bigPlate的父类是appleAIPlate
        appleAIPlate= bigPlate;
        bigPlate = colorPlate;
    }
}
