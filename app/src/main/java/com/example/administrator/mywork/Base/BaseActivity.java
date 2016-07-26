package com.example.administrator.mywork.Base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;



public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        handler.sendEmptyMessage(0);
    }
}
