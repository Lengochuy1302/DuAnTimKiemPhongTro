package com.example.duanaeth;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {
    TextView btnDangNhap;
    public String linkRealTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        anhxa();
        //ẩn toolBar
        getSupportActionBar().hide();


        //Chức năng đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();
            }
        });

    }



    final private void anhxa() {
        //gọi đường dẫn firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById
        btnDangNhap = findViewById(R.id.btndangnhap);
    }
}