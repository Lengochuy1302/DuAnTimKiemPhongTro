package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.duanaeth.ArrayAdapter.PhotoAdapter;
import com.example.duanaeth.DangNhapDangKy;
import com.example.duanaeth.R;
import com.example.duanaeth.SplashScreen.IntroActivity;
import com.example.duanaeth.SplashScreen.onBroadingSrceen;
import com.example.duanaeth.TrangChu;
import com.example.duanaeth.UpdateProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ThemNhaTro extends AppCompatActivity {
    private static final int MAX_LENGTH = 10 ;
    public ImageView btnImg;
    private TextView btnClean, btnHuy;
    private RecyclerView rcvPhoto;
    private PhotoAdapter photoAdapter;
    private DatabaseReference reference;

    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nha_tro);

        //ánh xạ
        anhXa();

        //ẩn toolBar
        getSupportActionBar().hide();

        //set ảnh vào rcv
        setImgRcv();

        //các event click
        eventClick();

    }

    // hàm các event click
    public void eventClick() {
        // click chọn ảnh
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemission();
            }
        });

        //click xóa ảnh
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoAdapter.setDataPhoto(null);
                btnImg.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.GONE);
            }
        });

        //click hủy

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(ThemNhaTro.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Bạn muốn thoát?")
                        .setContentText("Lưu ý: Sau khi bạn thoát mọi dữ liệu bạn vừa nhập sẽ bị xóa! Bạn chắc chắn chứ?")
                        .setCancelText("Hủy")
                        .setConfirmText("Đồng ý")
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
                                Intent introIntent = new Intent(ThemNhaTro.this, TrangChu.class);
                                startActivity(introIntent);
                                finishAffinity();
                            }
                        })
                        .show();




            }
        });
    }

    //hàm lấy ảnh
    private void SendLink(String url) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });


    }

    private void pemission() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                onClickImg();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ThemNhaTro.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(ThemNhaTro.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


    }

    private void onClickImg() {

        TedBottomPicker.with(ThemNhaTro.this)
                .setPeekHeight(1600)
                .showTitle(true)
                .setTitle("Ảnh nhà trọ")
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
                        if (uriList != null && !uriList.isEmpty()) {
//                            btnImg.setVisibility(View.GONE);
                            btnClean.setVisibility(View.VISIBLE);
                            photoAdapter.setDataPhoto(uriList);
                            final StorageReference ImageFolder =  FirebaseStorage.getInstance("gs://thodf-8e9db.appspot.com").getReference();
                            for (uploads=0; uploads < uriList.size(); uploads++) {
                                Uri Image  = uriList.get(uploads);

                                Calendar calendar = Calendar.getInstance();
                                final StorageReference imagename = ImageFolder.child(""+  UUID.randomUUID() +".png");

                                imagename.putFile(uriList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                String url = String.valueOf(uri);
//                                                SendLink(url);
                                            }
                                        });

                                    }
                                });


                            }

                        }
                    }
                });



    }

    //hàm set ảnh vào rcv
    public void setImgRcv() {
        photoAdapter = new PhotoAdapter(ThemNhaTro.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);
    }

    //hàm thêm nhà trọ
    public void setThemNhaTro() {

    }

    //ánh xạ
    public void  anhXa() {
        btnImg = findViewById(R.id.btnSelectImg);
        btnClean = findViewById(R.id.btnCleanImg);
        rcvPhoto = findViewById(R.id.rcv_img);
        btnHuy = findViewById(R.id.btnHuy);
    }
}