package com.example.duanaeth;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TrangChu extends AppCompatActivity {
    public String linkRealTime;
    TextView btnDangXuat, prof;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = findViewById(R.id.navigation_view);

        //anh xạ
        anhxa();
//        btnDangXuat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                SweetAlertDialog pDialog = new SweetAlertDialog(TrangChu.this, SweetAlertDialog.PROGRESS_TYPE);
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setTitleText("Loading ...");
//                pDialog.setCancelable(true);
//                pDialog.show();
//                Intent introIntent = new Intent(TrangChu.this, IntroActivity.class);
//                startActivity(introIntent);
//                finishAffinity();
//            }
//        });
//
//        prof.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SweetAlertDialog pDialog = new SweetAlertDialog(TrangChu.this, SweetAlertDialog.PROGRESS_TYPE);
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setTitleText("Loading ...");
//                pDialog.setCancelable(true);
//                pDialog.show();
//                Intent introIntent = new Intent(TrangChu.this, UpdateProfile.class);
//                startActivity(introIntent);
//                finishAffinity();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.chiase:

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(TrangChu.this);
                aBuilder.setMessage("Bạn muốn đăng xuất?");
                aBuilder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user == null) {
                            return;
                        }

                        Device device = new Device();
                        String iddevice = "";
                        device.setID("ThietBi");
                        device.setTenDevice(iddevice);
                        reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                        reference.child("ThietBi").setValue(device);


                        final ProgressDialog progressDialog = ProgressDialog.show(TrangChu.this,"Thông Báo","Đang đăng xuất...");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(TrangChu.this, IntroActivity.class));
                            }
                        },2500);
                    }
                });
                //nút không
                aBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                aBuilder.show();

                return true;
            case R.id.thoat:

                AlertDialog.Builder aBuildera = new AlertDialog.Builder(TrangChu.this);
                aBuildera.setMessage("Xác nhận thoát?");
                aBuildera.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        aBuildera.show();
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                });
                //nút không
                aBuildera.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                aBuildera.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    final private void anhxa() {
        //gọi đường dẫn firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById

    }
}