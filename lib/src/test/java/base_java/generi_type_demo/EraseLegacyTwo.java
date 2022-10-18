package base_java.generi_type_demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class Parent<T> {

}

class Child extends Parent<String> {

}

public class EraseLegacyTwo {

    public static void main(String[] args) {
        Type type = Child.class.getGenericSuperclass();//获取父类的泛型信息
        System.out.println("Child的Parent的Type类型为:" + type.getClass().getName());
        //这里懒得做类型转化了
        ParameterizedType parameterizedType = (ParameterizedType) type;

        //获取实际的类型参数
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];
        System.out.println("Child的Parent的类型参数为" + actualTypeArgument.getTypeName());
    }
}
