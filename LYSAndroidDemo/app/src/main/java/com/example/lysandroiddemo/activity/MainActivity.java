package com.example.lysandroiddemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lysandroiddemo.R;
import com.example.lysandroiddemo.bean.Movie;
import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    TextView textView;
    Button commitButton;
    ImageView imageView;
    Button toastButton;
    Button progressBarButton;
    Button alertDialogButton;
    ProgressBar progressBar;
    Button extentButton;

    OkHttpClient client = new OkHttpClient();

    private Integer count = 0;
    private Boolean canZoom = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView1);
        editText = findViewById(R.id.edit1);
        commitButton = findViewById(R.id.commitButton);
        commitButton.setOnClickListener(MainActivity.this);
        toastButton = findViewById(R.id.button1);
        toastButton.setOnClickListener(MainActivity.this);
        progressBarButton = findViewById(R.id.progressBarButton);
        progressBarButton.setOnClickListener(MainActivity.this);
        alertDialogButton = findViewById(R.id.button2);
        alertDialogButton.setOnClickListener(MainActivity.this);
        imageView = findViewById(R.id.imageView1);
        progressBar = findViewById(R.id.progressBar);
        extentButton = findViewById(R.id.extendButton);
        extentButton.setOnClickListener(MainActivity.this);

        //加载图片
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588074229764&di=3312d2db247c3abe3f8ee470f403dbb2&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201105%2F17%2F113554rnu40q7nbgnn3lgq.jpg").into(imageView);


       Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                //网络请求
                try {
                    String res = loadData("https://douban.uieee.com/v2/movie/top250");
                    //model赋值
//                    Movie movie = JSON.parseObject(res, Movie.class);
                    Movie movie1 = new Gson().fromJson(res, Movie.class);
                    Message message = new Message();
                    message.obj = movie1;
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Movie movie = (Movie) msg.obj;
            String uri = movie.getSubjects().get(0).getImages().getSmall();
            Glide.with(MainActivity.this).load(uri).into(imageView);
        }
    };

    public String loadData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Toast.makeText(MainActivity.this, "点击了ToastButton~", Toast.LENGTH_SHORT).show();
                break;

            case R.id.progressBarButton:
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
                break;

            case R.id.commitButton:
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "什么都没有写哦~", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button2:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("This is Dialog");
                dialog.setMessage("Something important");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "点击了OK", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "点击了Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;

            case R.id.extendButton:
                this.extend();
                break;

        }
    }


    private void extend() {
        Double scale = 1.1;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if(canZoom) {
            if (layoutParams.width < getWindowManager().getDefaultDisplay().getWidth()) {
                this.zoom(layoutParams, scale);
            } else {
                canZoom = false;
                this.reduce(layoutParams, scale);
            }
        } else {
            if (layoutParams.width < getWindowManager().getDefaultDisplay().getWidth() / 3) {
                canZoom = true;
                this.zoom(layoutParams, scale);
            } else {
                this.reduce(layoutParams, scale);
            }

        }
    }

    private void zoom(ViewGroup.LayoutParams layoutParams, Double scale) {
        extentButton.setText("点我放大");
        layoutParams.width *= scale;
        layoutParams.height *= scale;
        Log.d("display", "width = " + layoutParams.width + ",height = " + layoutParams.height+",scale = " + scale);
        imageView.setLayoutParams(layoutParams);
    }

    private void reduce(ViewGroup.LayoutParams layoutParams, Double scale) {
        extentButton.setText("点我缩小");
        layoutParams.width /= scale;
        layoutParams.height /= scale;
        Log.d("display", "width = " + layoutParams.width + ",height = " + layoutParams.height+",scale = " + scale);
        imageView.setLayoutParams(layoutParams);
    }
}
