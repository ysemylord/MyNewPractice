package com.example.myapplication.reflect_test;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/*
 * 通过反射，自动给View赋值
 */
public class InjectUtil {
    public static void inject(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
              field.setAccessible(true);
              if(field.isAnnotationPresent(MyInjectView.class)){
                      MyInjectView MyInjectView = field.getAnnotation(MyInjectView.class);
                      int id = MyInjectView.value();
                      View targetView =activity.findViewById(id);
                  try {
                      field.set(activity,targetView);
                  } catch (IllegalAccessException e) {
                      e.printStackTrace();
                  }
              }
        }

    }
}
