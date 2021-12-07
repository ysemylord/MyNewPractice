package com.example.myapplication.reflect_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldUseAnnotationActivity extends AppCompatActivity {
    @AutoWired
    private String name1;
    @AutoWired("name second")
    private String name2;//对象
    @AutoWired
    private int age;//基本数据类型
    @AutoWired
    private String[] strArr;//对象数组
    @AutoWired
    private int[] intArr;//基本数据类型数组
    @AutoWired
    private String[] strList;//对象列表
    @AutoWired
    private int[] intList;//基本数据类列表

    @AutoWired
    private UserSerializable userSerializable;

    @AutoWired
    private UserSerializable[] userSerializables;

    @AutoWired
    private UserParcelable userParcelable;//序列化对象

    @AutoWired
    private UserParcelable[] userParcelables;//序列化对象数组
    // Caused by: java.lang.IllegalArgumentException:
    // field userParcelables has type UserParcelable[],
    // got android.os.Parcelable[]
    //通过bundle.get()是获取到的类型是android.os.Parcelable[]，不是UserParcelable[]，所以会报错
    //解决方法：类型转化

    /**
     * if (field.getType().isArray() &&
     * Parcelable.class.isAssignableFrom(field.getType().getComponentType())) {
     *      Object[] objs = (Object[]) object;
     *      Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) field.getType());
     *      object = objects;
     *      Log.i("AutoWiredUtil-", object.getClass().getTypeName());
     * }
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_use_annotation);
        AutoWiredUtil.inject(this);
        //injectAutowired(this);
        Log.i("FieldUseAnnotationActivity", name1 + "|" + name2 + "|" + age);
        Log.i("FieldUseAnnotationActivity", strArr.toString());
        Log.i("FieldUseAnnotationActivity", intArr.length + "");
        Log.i("FieldUseAnnotationActivity", strList.length + "");
        Log.i("FieldUseAnnotationActivity", intList.length + "");
        Log.i("FieldUseAnnotationActivity", userParcelable.getName() + "");
        Log.i("FieldUseAnnotationActivity", userParcelables[0].getName() + "");
    }


    static class AutoWiredUtil {
        public static void inject(Activity activity) {
            Class<? extends Activity> activityClass = activity.getClass();
            Field[] fields = activityClass.getDeclaredFields();
            Bundle bundle = activity.getIntent().getExtras();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoWired.class)) {
                    field.setAccessible(true);
                    AutoWired autoWired = field.getAnnotation(AutoWired.class);
                    String keyString = autoWired.value();
                    if (keyString.isEmpty()) {
                        keyString = field.getName();
                    }
                    Object object;
                    object = bundle.get(keyString);
                    if (object == null) {
                        continue;
                    }
                    Log.i("AutoWiredUtil", object.getClass().getTypeName());
                    Class<?> fieldClass = field.getType();
                    if (fieldClass.isArray() &&
                            Parcelable.class.isAssignableFrom(fieldClass.getComponentType())) {
                        //判断为Parcelable[]的子类数组

                        Object[] objs = (Object[]) object;
                        Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) fieldClass);
                        object = objects;
                        Log.i("AutoWiredUtil-", object.getClass().getTypeName());
                    }

                    try {
                        field.set(activity, object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }


}
