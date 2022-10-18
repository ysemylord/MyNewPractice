package base_java.generi_type_demo;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

/**
 * 泛型的Type体系
 */
public class GenericType {

    Map<Integer, String> map;


    //这个声明在类上的T是类型参数TypeVariable
    class Demo0<T> {

        //List的Type为Class
        List list0;

        //List<String>的Type为ParameterizedType，其中String为ActualTypeArguments，Type为Class
        List<String> list1;

        //List<? extends String>的Type为ParameterizedType，其中? extends String为ActualTypeArguments，
        // ? extends String 的Type为WildCard
        List<? extends String> list2;

        //T[]就泛型数组 GenericArrayType
        T[] array;
    }

    @Test
    public void test() throws NoSuchFieldException {

        Type type0 = Demo0.class.getGenericSuperclass();
        System.out.println(type0.getClass().getName());

        TypeVariable typeVariable = Demo0.class.getTypeParameters()[0];
        System.out.println(typeVariable);

        Field list0Field = Demo0.class.getDeclaredField("list0");
        System.out.println("list0的类型的type为:" + list0Field.getGenericType().getClass().getName());

        Field list1Field = Demo0.class.getDeclaredField("list1");
        Type genericTypeList1 = list1Field.getGenericType();
        ParameterizedType parameterizedTypeList1 = (ParameterizedType) genericTypeList1;
        Type type = parameterizedTypeList1.getActualTypeArguments()[0];
        System.out.println("list1的类型的type为" + parameterizedTypeList1.getClass().getName());
        System.out.println("list1的类型的typed的实际参数类型为 " + type.getClass().getName());

        Field list2Field = Demo0.class.getDeclaredField("list2");
        ParameterizedType parameterizedTypeList2 = (ParameterizedType) list2Field.getGenericType();
        WildcardType wildcardType = (WildcardType) parameterizedTypeList2.getActualTypeArguments()[0];
        System.out.println("list2的类型的type为 " + parameterizedTypeList2.getClass().getName());
        System.out.println("list2的类型的type的实际参数类型为" + wildcardType.getClass().getName());

        Field array = Demo0.class.getDeclaredField("array");
        System.out.println("泛型数组："+array.getGenericType().getClass().getName());

    }


    class Demo<T1 extends String & Map> {

    }

    @Test
    public void typeVariableTest() {
        TypeVariable[] typeVariables = Demo.class.getTypeParameters();
        for (TypeVariable typeVariable : typeVariables) {
            System.out.println("类型变量名称: " + typeVariable.getName());
            System.out.println("声明类型变量的类型及名称: " + typeVariable.getGenericDeclaration());
            Type[] bounds = typeVariable.getBounds();
            for (Type type : bounds
            ) {
                System.out.println("类型变量的边界: " + type);
            }
        }
    }

    @Test
    public void parameterizedTypeTest() throws NoSuchFieldException {
        Field field = GenericType.class.getDeclaredField("map");
        Type genericType = field.getGenericType();
        System.out.println(genericType);
        System.out.println(genericType instanceof ParameterizedType);
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        System.out.println(parameterizedType.getRawType());
        for (Type type : parameterizedType.getActualTypeArguments()) {
            System.out.println(type.getTypeName());
        }
    }

    class Demo2<T> {
        T[] array;
    }

    /**
     * 泛型数组
     */
    @Test
    public void genericArrayTypeTest() throws NoSuchFieldException {
        Field field = Demo2.class.getDeclaredField("array");
        GenericArrayType arrayType = (GenericArrayType) field.getGenericType();
        System.out.println(arrayType.getTypeName());
        System.out.println(arrayType.getGenericComponentType());
    }

    class Demo3<T> {
        List<? extends String> array;
        List array2;
    }

    /**
     * 泛型数组
     */
    @Test
    public void wildcardTypeTest() throws NoSuchFieldException {
        Field field = Demo3.class.getDeclaredField("array");
        ParameterizedType arrayType = (ParameterizedType) field.getGenericType();
        WildcardType wildcardType = (WildcardType) arrayType.getActualTypeArguments()[0];
        System.out.println(wildcardType.getTypeName());

    }

    @Test
    public void genericAayTypeTest() throws NoSuchFieldException {
        Field field = Demo3.class.getDeclaredField("array2");
        Type arrayType = (Type) field.getGenericType();
        System.out.println("Type " + arrayType);
    }

}
