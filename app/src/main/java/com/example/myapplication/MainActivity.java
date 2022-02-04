package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.my_on_click.MyOnClick;
import com.example.myapplication.my_on_click.OnClickInject;
import com.example.myapplication.my_retrofit.Retrofit;
import com.example.myapplication.my_retrofit.UserService;
import com.example.myapplication.reflect_test.MyInjectView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // myRetrofit();
        //OnClickInject.inject(this);

   /*     InjectUtil.inject(this);
        textView.setText("12222");
        Intent intent = new Intent(this, FieldUseAnnotationActivity.class);
        intent.putExtra("age", 11);
        intent.putExtra("name1", "jack");
        intent.putExtra("name second", "load");
        intent.putExtra("strArr", new String[]{"str1", "str2", "str3"});
        intent.putExtra("intArr", new int[]{1,2,3});
        intent.putExtra("strList", new String[]{"str1", "str2", "str3"});
        intent.putExtra("intList", new int[]{1,2,3});
        intent.putExtra("userParcelable", new UserParcelable("jack",122));
        intent.putExtra("userParcelables", new UserParcelable[]{
                new UserParcelable("jack",122)
        });
        intent.putExtra("userSerializables", new UserSerializable[]{
                new UserSerializable("jack")
        });
        intent.putExtra("userSerializable", new UserSerializable("jack"));
        startActivity(intent);*/
    }

    private void myRetrofit() {
        Retrofit retrofit = new Retrofit("http://www.baidu.com/", new OkHttpClient());
        UserService userService = retrofit.create(UserService.class);
        userService.getS("nihao").enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.i("network", responseStr);
            }
        });
    }

    @MyOnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onMyClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Log.i("MyOnClick", "btn1");
                break;
            case R.id.btn2:
                Log.i("MyOnClick", "btn2");
                break;
            case R.id.btn3:
                Log.i("MyOnClick", "btn3");
                break;
            default:
                Log.i("MyOnClick", "btn default");
                break;
        }
    }
}
