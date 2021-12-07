package com.zero.genericsdemo02;

import java.util.List;

public class TestEqual<T extends Comparable> {

    public boolean equals(T t) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

/*public class TestEqual<T> {

    public boolean equals(T t) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}*/

class NewTest {

    /**
     * 泛型无法创建实例，因为不知道它的实际类型
     */
    public static <E> void add(List<E> list,E e) {
           //E e = new E(); 如果这句话不会报错，那么E会被擦除为Object,每次new的就是一个Object对象
    }

    /**
     * 解决方法，使用带泛型的Class
     */
    public static <E> void add(List<E> list,Class<E> eClass) throws IllegalAccessException, InstantiationException {
         E e =eClass.newInstance();
         list.add(e);
    }

}