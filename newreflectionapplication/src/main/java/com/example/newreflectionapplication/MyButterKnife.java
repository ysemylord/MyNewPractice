
package com.example.newreflectionapplication;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

public class MyButterKnife {
    public static void inject(Activity activity)  {
        Class<? extends Activity> activityClazz = activity.getClass();
        // activityClazz.getFields(); 只能获取public的field
        Field[] fields = activityClazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(MyInject.class)) {//field上有MyInject注解
                MyInject myInject = field.getAnnotation(MyInject.class);
                int resId = myInject.value();
                View targetView = activity.findViewById(resId);
                field.setAccessible(true);//让private的field也可以被访问
                try {
                    field.set(activity,targetView);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


