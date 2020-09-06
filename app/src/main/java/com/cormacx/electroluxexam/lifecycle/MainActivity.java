package com.cormacx.electroluxexam.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cormacx.electroluxexam.R;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}