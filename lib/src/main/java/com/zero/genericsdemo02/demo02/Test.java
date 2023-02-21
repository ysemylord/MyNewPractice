package com.zero.genericsdemo02.demo02;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        scene01();
    }

    public static void scene01() {
        ArrayList<Apple> apples = new ArrayList<>();
        ArrayList<Banana> bananas = new ArrayList<>();
//        apples = bananas;
        //result true
        System.out.println(apples.getClass() == bananas.getClass());
    }

    public static void scene02() {
//        ArrayList<int> ints = new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>();
    }

    public static void scene03() {
        ArrayList<String> strings = new ArrayList<>();
        if(strings instanceof ArrayList<?>){}

//        if(strings instanceof ArrayList<String>){ }
    }

    public  static <T> void scene05() {
//        Plate<Apple>[] applePlates = new Plate<Apple>[10];//不允许
//        T[] arr = new T[10];//不允许
       Apple[] apples = new Apple[10];
       Fruit[] fruits = new Fruit[10];
        System.out.println(apples.getClass());
        //class [Lcom.zero.genericsdemo02.demo02.Apple;
        System.out.println(fruits.getClass());
        //class [Lcom.zero.genericsdemo02.demo02.Fruit;
       fruits = apples;
       // fruits里面原本是放什么类型的？ Fruit or Apple
        // Apple[]
       fruits[0] = new Banana();//编译通过，运行报ArrayStoreException
        //Fruit是Apple的父类，Fruit[]是Apple[]的父类，这就是数组的协变
        //如果加入泛型后，由于擦除机制，运行时将无法知道数组的类型
        Plate<?>[] plates = new Plate<?>[10];//这是可以的
    }
}

class ViewGroup{}
class LinearLayout extends ViewGroup{}

class Test03{

  public <T extends ViewGroup> T get(){//CAP#1
      return (T)new LinearLayout();
  }

}

//2. Cannot Create Instances of Type Parameters 无法创建类型参数的实例
class Test02 {
    //你无法创建一个类型参数的实例。例如，下面代码就会引起编译时错误：
    public static <E> void append(List<E> list) {
//        E elem = new E();  // compile-time error
//        list.add(elem);
    }
    //通过反射创建一个参数化类型的实例
    public static <E> void append(List<E> list, Class<E> cls) throws Exception {
        E elem = cls.newInstance();   // OK
        list.add(elem);
    }
}

class Test2<T>{
//    public static T one;
//    public static T test(T t){}

    public static <T> T test1(T t){return t;}
}


