package com.zero.genericsdemo02.demo01;

import java.util.ArrayList;
import java.util.List;

class Plate1 implements Plate<Apple>{

    @Override
    public void set(Apple apple) {

    }

    @Override
    public Apple get() {
        return null;
    }
}

public class AIPlate<T> implements Plate<T>{

    private List<T> items = new ArrayList<T>(10);

  //  private List<T> items1 = new ArrayList<>(10);

    public AIPlate(){

    }

    public AIPlate(T t){
        items.add(t);
    }

    @Override
    public void set(T t) {
        items.add(t);
    }

    @Override
    public T get(){
        int index = items.size() -1;
        if(index>= 0){
            return items.get(index);
        }else{
            return null;
        }
    }

    @Override
    public String toString() {
        return "Plate{" +
                "items=" + items +
                '}';
    }
}
