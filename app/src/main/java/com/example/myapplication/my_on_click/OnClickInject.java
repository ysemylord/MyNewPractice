package com.example.myapplication.my_on_click;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OnClickInject {
    public static void inject(Activity activity) {
        Method[] methods = activity.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length == 1 && (annotations[0] instanceof MyOnClick)) {//暂时只考虑只有一个注解的情况
                MyOnClick myOnClick = (MyOnClick) annotations[0];
                int[] ids = myOnClick.value();
                for (int id : ids) {
                    activity.findViewById(id).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity,v);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
}
