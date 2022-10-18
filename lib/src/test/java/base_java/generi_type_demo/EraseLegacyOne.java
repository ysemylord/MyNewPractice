package base_java.generi_type_demo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

class Info {
    Map<Integer, String> map;
}

public class EraseLegacyOne {
    public static void main(String[] args) throws NoSuchFieldException {
        Field field = Info.class.getDeclaredField("map");
        //获取泛型类型
        //泛型类型是什么：我们使用Type体系秒速泛型类型，所以泛型类型就是Type
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            System.out.println(" map 的类型为 ParameterizedType");
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                System.out.println(actualTypeArguments[i].getTypeName());
            }

        }
    }
}
