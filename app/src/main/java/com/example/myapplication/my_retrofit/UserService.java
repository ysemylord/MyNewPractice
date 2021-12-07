package com.example.myapplication.my_retrofit;

import com.example.myapplication.my_retrofit.annotation.GET;
import com.example.myapplication.my_retrofit.annotation.Query;

import okhttp3.Call;

public interface UserService {
    @GET("s")
    Call getS(@Query("wd") String wd);
}
