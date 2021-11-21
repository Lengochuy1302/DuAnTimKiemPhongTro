package com.example.duanaeth.LayoutChucNang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.duanaeth.R;

public class thongTinApp extends AppCompatActivity {
     //thêm thông tin appp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtimphanmem);
        getSupportActionBar().hide();
    }
}