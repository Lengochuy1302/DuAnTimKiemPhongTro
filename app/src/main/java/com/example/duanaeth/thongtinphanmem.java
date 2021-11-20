package com.example.duanaeth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class thongtinphanmem extends AppCompatActivity {
     //thêm thông tin appp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtimphanmem);
        getSupportActionBar().hide();
    }
}