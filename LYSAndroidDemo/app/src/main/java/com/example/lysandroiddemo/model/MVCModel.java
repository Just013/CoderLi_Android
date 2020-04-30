package com.example.lysandroiddemo.model;


import com.example.lysandroiddemo.bean.Movie;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MVCModel {
    //获取top250电影数据
    public void getMovies(final String url, final Callback mCallBack) {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(url)//访问连接
                .get()
                .build();
        //创建Call对象
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mCallBack.onFailure("请求异常，请稍后重试");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    //处理网络请求的响应，处理UI需要在UI线程中处理
                    //...
                    mCallBack.onSuccess(new Gson().fromJson(response.body().string(), Movie.class));
                } else {
                    mCallBack.onFailure("请求异常，请稍后重试");
                }
            }
        });
    }
}
