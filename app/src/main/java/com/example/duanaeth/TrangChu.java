package com.example.duanaeth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.duanaeth.ArrayAdapter.Device;
import com.example.duanaeth.FragmentLayout.NhaTro.NhaTro_Fragment;
import com.example.duanaeth.FragmentLayout.NhaTro.PhongTroCuaToi;
import com.example.duanaeth.FragmentLayout.NhanTin.NhanTin_Fragment;
import com.example.duanaeth.FragmentLayout.PhongGhep.PhongGhep_Fragment;
import com.example.duanaeth.FragmentLayout.Setting.Setting_Fragment;
import com.example.duanaeth.LayoutChucNang.doiMatKhauActivity;
import com.example.duanaeth.LayoutChucNang.thongTinApp;
import com.example.duanaeth.SplashScreen.IntroActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private BottomNavigationView bottomNavigationView;
    private static final  int FRAGMENT_THU = 1;
    private static final  int FRAGMENT_CHI = 2;
    private static final  int FRAGMENT_THONGKE = 3;
    private static final  int FRAGMENT_THONGTIN = 4;
    private static final  int FRAGMENT_DOIMK = 5;
    private static final  int FRAGMENT_GIOITHIEU = 6;
    private static final  int FRAGMENT_GHICHU= 7;
    private static final  int FRAGMENT_GOPY= 8;
    private  int currenFragment = FRAGMENT_THONGKE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        //anh x???
        anhxa();

//        //???n toolBar
//        getSupportActionBar().hide();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set profile
        setprofile();

        //set action bottom
        setBottomNavigationView();

        replaceFragment(new NhaTro_Fragment());
        navigationView.setCheckedItem(R.id.thongke);
        setTitle("DANH S??CH NH?? TR???");
        bottomNavigationView.getMenu().findItem(R.id.btnthongke).setChecked(true);

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        return true;
    }

    //h??m set profile
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

    //Ch???c n??ng bottom
    public void setBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnthunhap:
                        setTitle("DANH S??CH ??? GH??P");
                        PhongGhep_Fragment khoanThu_fragment = new PhongGhep_Fragment();
                        replaceFragment(khoanThu_fragment);
                        navigationView.setCheckedItem(R.id.khoanthu);
                        currenFragment = FRAGMENT_THU;
                        break;

                    case R.id.btnchitieu:
                        setTitle("NH???N TIN");
                        NhanTin_Fragment khoanChi_fragment = new NhanTin_Fragment();
                        replaceFragment(khoanChi_fragment);
                        navigationView.setCheckedItem(R.id.khoanchi);
                        currenFragment = FRAGMENT_CHI;
                        break;

                    case R.id.btnthongke:
                        setTitle("DANH S??CH NH?? TR???");
                        NhaTro_Fragment searchFragment = new NhaTro_Fragment();
                        replaceFragment(searchFragment);
                        navigationView.setCheckedItem(R.id.thongke);
                        currenFragment = FRAGMENT_THONGKE;
                        break;

                    case R.id.btncaidat:
                        setTitle("C??I ?????T");
                        Setting_Fragment ghiChu_fragment = new Setting_Fragment();
                        replaceFragment(ghiChu_fragment);
                        navigationView.setCheckedItem(R.id.ghichu);
                        currenFragment = FRAGMENT_THONGKE;
                        break;
                }

                return true;
            }
        });
    }

    //ch???c n??ng menubar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.chiase:

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(TrangChu.this);
                aBuilder.setMessage("B???n mu???n ????ng xu???t?");
                aBuilder.setPositiveButton("????ng xu???t", new DialogInterface.OnClickListener() {
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


                        final ProgressDialog progressDialog = ProgressDialog.show(TrangChu.this,"Th??ng B??o","??ang ????ng xu???t...");
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
                //n??t kh??ng
                aBuilder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                aBuilder.show();

                return true;
            case R.id.thoat:

                AlertDialog.Builder aBuildera = new AlertDialog.Builder(TrangChu.this);
                aBuildera.setMessage("X??c nh???n tho??t?");
                aBuildera.setPositiveButton("Tho??t", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        aBuildera.show();
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                });
                //n??t kh??ng
                aBuildera.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
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
            if (FRAGMENT_THU != currenFragment) {
                setTitle("DANH S??CH ??? GH??P");
                replaceFragment(new PhongGhep_Fragment());
                bottomNavigationView.getMenu().findItem(R.id.btnthunhap).setChecked(true);
                currenFragment = FRAGMENT_THU;
            }
        } else if (id == R.id.khoanchi) {
            if (FRAGMENT_CHI != currenFragment) {
                setTitle("NH???N TIN");
                replaceFragment(new NhanTin_Fragment());
                bottomNavigationView.getMenu().findItem(R.id.btnchitieu).setChecked(true);
                currenFragment = FRAGMENT_CHI;

            }
        }
        else if (id == R.id.thongke) {
            if (FRAGMENT_THONGKE != currenFragment) {
                setTitle("DANH S??CH NH?? TR???");
                replaceFragment(new NhaTro_Fragment());
                bottomNavigationView.getMenu().findItem(R.id.btnthongke).setChecked(true);
                currenFragment = FRAGMENT_THONGKE;

            }
        }
        else if (id == R.id.gioithieu) {
            final ProgressDialog progressDialog = ProgressDialog.show(TrangChu.this,"Th??ng B??o","??ang t??m ki???m b???n c???p nh???t...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    Toast.makeText(TrangChu.this, "Ch??a c?? b???n c???p nh???t m???i!", Toast.LENGTH_SHORT).show();
                }
            },2500);

        }
        else if (id == R.id.doimatkhau) {
            if (FRAGMENT_DOIMK != currenFragment) {
                setTitle("?????I M???T KH???U");
                Intent introIntent = new Intent(TrangChu.this, doiMatKhauActivity.class);
                startActivity(introIntent);
                currenFragment = FRAGMENT_DOIMK;
            }
        }
        else if (id == R.id.intro) {
            if (FRAGMENT_GIOITHIEU != currenFragment) {
                setTitle("PH??NG TR??? ???? ????NG");
                replaceFragment(new PhongTroCuaToi());
                bottomNavigationView.getMenu().findItem(R.id.btnthongke).setChecked(false);
                currenFragment = FRAGMENT_GIOITHIEU;

            }
        }
        else if (id == R.id.capnhat) {
            if (FRAGMENT_THONGTIN != currenFragment) {
                setTitle("TH??NG TIN NG?????I D??NG");
                navigationView.setCheckedItem(R.id.capnhat);
                Intent introIntent = new Intent(TrangChu.this, UpdateProfile.class);
                startActivity(introIntent);
                currenFragment = FRAGMENT_THONGTIN;
            }
        }

        else if (id == R.id.thongtin) {
            setTitle("GI???I THI???U");
            Intent introIntent = new Intent(TrangChu.this, thongTinApp.class);
            startActivity(introIntent);
        }

        else if (id == R.id.phienban) {
            Toast.makeText(TrangChu.this, "Phi??n b???n v1.0.1 m???i nh???t!", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.logout) {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(TrangChu.this);
            aBuilder.setMessage("B???n mu???n ????ng xu???t?");
            aBuilder.setPositiveButton("????ng xu???t", new DialogInterface.OnClickListener() {
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


                    final ProgressDialog progressDialog = ProgressDialog.show(TrangChu.this,"Th??ng B??o","??ang ????ng xu???t...");
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
            //n??t kh??ng
            aBuilder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            aBuilder.show();
        }
        else if (id == R.id.ghichu) {
            if (FRAGMENT_GHICHU != currenFragment) {
                setTitle("C??I ?????T");
                replaceFragment(new Setting_Fragment());
                bottomNavigationView.getMenu().findItem(R.id.btncaidat).setChecked(true);
                currenFragment = FRAGMENT_GHICHU;
            }
        }
        else if (id == R.id.donggopykien) {
//            if (FRAGMENT_GOPY != currenFragment) {
//                setTitle("PH???N H???I NG?????I D??NG");
//                replaceFragment(new PhanHoiNguoiDung());
//                currenFragment = FRAGMENT_GOPY;
//            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(TrangChu.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Th??ng b??o")
                .setContentText("B???n th???t s??? mu???n tho??t? H??y nh???n ?????ng ?? ????? tho??t!")
                .setCancelText("H???y")
                .setConfirmText("?????ng ??")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                })
                .show();
    }


    final private void anhxa() {
        //g???i ???????ng d???n firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById
        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }


}