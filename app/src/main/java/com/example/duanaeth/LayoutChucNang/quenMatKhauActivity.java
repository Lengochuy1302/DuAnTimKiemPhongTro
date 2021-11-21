package com.example.duanaeth.LayoutChucNang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.duanaeth.R;

public class quenMatKhauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        getSupportActionBar().hide();
    }
}