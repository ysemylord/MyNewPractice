package com.zero.genericsdemo02.demo01;

import java.util.ArrayList;
import java.util.List;



public class AIPlate2<T extends Comparable> implements Plate<T>{

    private List<T> items = new ArrayList<T>(10);

  //  private List<T> items1 = new ArrayList<>(10);

    public AIPlate2(){

    }

    public AIPlate2(T t){
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
