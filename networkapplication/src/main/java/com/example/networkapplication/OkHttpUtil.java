package com.example.networkapplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    /**
     * @Author lsc
     * <p>get 请求 </p>
     * @Param [url]
     * @Return
     */
    public static void get(String url) {
        // 1 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 2设置请求
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        // 3封装call
        Call call = client.newCall(request);
        // 4异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // ...
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    // ...
                    // response.body().string();
                }
            }
        });

    }

    /**
     * @Author lsc
     * <p> post 请求， form 参数</p>
     * @Param [url]
     * @Return
     */
    public static void postFormData(String url) {
        // 1 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 2 构建参数
        FormBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .build();
        // 3 构建 request
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        // 4 将Request封装为Call
        Call call = client.newCall(request);
        // 5 异步调用
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response != null && response.isSuccessful()) {

                }
            }
        });
    }

    /**
     * @Author lsc
     * <p>post 请求 ，json参数 </p>
     * @Param [url, json]
     * @Return
     */
    public static void postForJson(String url, String json){
        // 1 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 2 构建参数
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        // 3 构建 request
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        // 4 将Request封装为Call
        Call call = client.newCall(request);
        // 5 异步调用
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // ...
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    // ...
                }
            }
        });
    }
}
