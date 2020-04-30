package com.example.lysandroiddemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lysandroiddemo.R;
import com.example.lysandroiddemo.model.Callback;
import com.example.lysandroiddemo.model.MVCModel;

import java.io.IOException;

public class MVCActivity extends AppCompatActivity {
    private MVCModel mvcModel = new MVCModel();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        progressBar = findViewById(R.id.mvc_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Log.e("onCreate---currentThread", String.valueOf(Thread.currentThread()));

        mvcModel.getMovies("https://douban.uieee.com/v2/movie/top250", new Callback() {
            @Override
            public void onSuccess(Object result) {
                Log.e("runOnUiThread---currentThread", String.valueOf(Thread.currentThread()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Log.e("runOnUiThread---currentThread", String.valueOf(Thread.currentThread()));
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}
