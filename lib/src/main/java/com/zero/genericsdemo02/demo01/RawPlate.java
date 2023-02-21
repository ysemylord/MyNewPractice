package com.zero.genericsdemo02.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * 原始的盘子
 */
public class RawPlate implements Plate{

    private List items = new ArrayList(10);

    public RawPlate(){

    }

    @Override
    public void set(Object fruit){
        items.add(fruit);
    }

    @Override
    public Fruit get(){
        int index = items.size() -1;
        if(index>= 0){
            return (Fruit) items.get(index);
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
