package com.example.lysandroiddemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lysandroiddemo.R;

public class RootActivity extends AppCompatActivity {

    private String[] data = {"UI-Learning及约束布局", "四大组件", "MVC", "MVVM", "MVP"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RootActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick", String.valueOf(position));
                switch (position) {
                    case 0:
                        Intent intent = new Intent(RootActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent mvcIntent = new Intent(RootActivity.this, MVCActivity.class);
                        startActivity(mvcIntent);
                        break;
                }
//                Toast.makeText(RootActivity.this, data[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
