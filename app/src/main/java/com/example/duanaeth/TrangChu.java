package com.example.duanaeth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TrangChu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public String linkRealTime;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    FrameLayout frameLayout;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        //anh xạ
        anhxa();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set data
        setprofile();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        return true;
    }

    public void setprofile() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView gmailtext = (TextView) headerView.findViewById(R.id.gmailnguoidung);
        TextView tennguoidung = (TextView) headerView.findViewById(R.id.tennguoidung);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.avatar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        tennguoidung.setText(name);
        gmailtext.setText(email);
        Picasso.get().load(photoUrl).into(avatar);
    }


    //chức năng menubar
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


    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.khoanthu) {
//            if (FRAGMENT_THU != currenFragment) {
//                setTitle("QUẢN LÝ THU NHẬP");
//                replaceFragment(new KhoanThu_Fragment());
//                bottomNavigationView.getMenu().findItem(R.id.btnthunhap).setChecked(true);
//                currenFragment = FRAGMENT_THU;
//            }
        } else if (id == R.id.khoanchi) {
//            if (FRAGMENT_CHI != currenFragment) {
//                setTitle("QUẢN LÝ CHI TIÊU");
//                replaceFragment(new KhoanChi_Fragment());
//                bottomNavigationView.getMenu().findItem(R.id.btnchitieu).setChecked(true);
//                currenFragment = FRAGMENT_CHI;
//
//            }
        }
        else if (id == R.id.thongke) {
//            if (FRAGMENT_THONGKE != currenFragment) {
//                setTitle("THỐNG KÊ THU CHI");
//                replaceFragment(new ThongKe_Fragment());
//                bottomNavigationView.getMenu().findItem(R.id.btnthongke).setChecked(true);
//                currenFragment = FRAGMENT_THONGKE;
//
//            }
        }
        else if (id == R.id.gioithieu) {
            final ProgressDialog progressDialog = ProgressDialog.show(TrangChu.this,"Thông Báo","Đang tìm kiếm bản cập nhật...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    Toast.makeText(TrangChu.this, "Chưa có bản cập nhật mới!", Toast.LENGTH_SHORT).show();
                }
            },2500);

        }
        else if (id == R.id.doimatkhau) {
//            if (FRAGMENT_DOIMK != currenFragment) {
//                setTitle("ĐỔI MẬT KHẨU");
//                Intent introIntent = new Intent(MainActivity.this, doimk.class);
//                startActivity(introIntent);
//                currenFragment = FRAGMENT_DOIMK;
//            }
        }
        else if (id == R.id.intro) {
//            setTitle("GIỚI THIỆU");
//            Intent introIntent = new Intent(MainActivity.this, gioithieu.class);
//            startActivity(introIntent);
        }
        else if (id == R.id.capnhat) {
//            if (FRAGMENT_THONGTIN != currenFragment) {
//                setTitle("THÔNG TIN NGƯỜI DÙNG");
//                navigationView.setCheckedItem(R.id.capnhat);
                Intent introIntent = new Intent(TrangChu.this, UpdateProfile.class);
                startActivity(introIntent);
//                currenFragment = FRAGMENT_THONGTIN;
//            }
        }

        else if (id == R.id.thongtin) {
//            if (FRAGMENT_GIOITHIEU != currenFragment) {
//                setTitle("THÔNG TIN PHẦN MỀM");
//                gioithieu_fragment gioithieu_fragment = new gioithieu_fragment();
//                navigationView.setCheckedItem(R.id.thongtin);
//                replaceFragment(gioithieu_fragment);
//                currenFragment = FRAGMENT_GIOITHIEU;
//            }
        }

        else if (id == R.id.phienban) {
            Toast.makeText(TrangChu.this, "Phiên bản v1.0.1 mới nhất!", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.logout) {
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
        }
        else if (id == R.id.ghichu) {
//            if (FRAGMENT_GHICHU != currenFragment) {
//                setTitle("GHI CHÚ THU CHI");
//                Tab_GhiChu_Fragment ghiChu_fragment = new Tab_GhiChu_Fragment();
//                replaceFragment(ghiChu_fragment);
//                currenFragment = FRAGMENT_GHICHU;
//                bottomNavigationView.getMenu().findItem(R.id.btncaidat).setChecked(true);
//                currenFragment = FRAGMENT_GHICHU;
//            }
        }
        else if (id == R.id.donggopykien) {
//            if (FRAGMENT_GOPY != currenFragment) {
//                setTitle("PHẢN HỒI NGƯỜI DÙNG");
//                replaceFragment(new PhanHoiNguoiDung());
//                currenFragment = FRAGMENT_GOPY;
//            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    final private void anhxa() {
        //gọi đường dẫn firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById
        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }


}