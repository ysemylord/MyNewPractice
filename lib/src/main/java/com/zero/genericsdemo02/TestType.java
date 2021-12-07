package com.zero.genericsdemo02;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class TestType {
    Map<String,Integer> map;

    public static void main(String[] args) throws NoSuchFieldException {
        Field field = TestType.class.getDeclaredField("map");
        System.out.println(field.getGenericType());
        System.out.println(field.getGenericType() instanceof ParameterizedType);
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        System.out.println(parameterizedType.getRawType());
        for (Type type : parameterizedType.getActualTypeArguments()){
            System.out.println(type);
        }
        System.out.println(parameterizedType.getOwnerType());
    }
}
