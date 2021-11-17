package com.example.duanaeth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProfile extends AppCompatActivity {
    public String linkRealTime;
    TextView btnTiepTuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        //ẩn toolBar
        getSupportActionBar().hide();

        //anh xạ
        anhxa();
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();
                Intent introIntent = new Intent(UpdateProfile.this, TrangChu.class);
                startActivity(introIntent);
                finishAffinity();
            }
        });
    }

    final private void anhxa() {
        //gọi đường dẫn firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById
        btnTiepTuc = findViewById(R.id.btn_ok_capNhatActivity);

    }


}