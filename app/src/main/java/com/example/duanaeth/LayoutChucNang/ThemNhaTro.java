package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.duanaeth.ArrayAdapter.PhotoAdapter;
import com.example.duanaeth.R;
import com.example.duanaeth.SplashScreen.onBroadingSrceen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ThemNhaTro extends AppCompatActivity {
    private static final int MAX_LENGTH = 10 ;
    public ImageView btnImg;
    private TextView btnClean;
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

        //ẩn toolBar
        getSupportActionBar().hide();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
        btnImg = findViewById(R.id.btnSelectImg);
        btnClean = findViewById(R.id.btnCleanImg);
        rcvPhoto = findViewById(R.id.rcv_img);

        rcvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemission();
            }
        });

        photoAdapter = new PhotoAdapter(ThemNhaTro.this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemission();
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            photoAdapter.setDataPhoto(null);
                btnImg.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.GONE);
            }
        });
    }

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
                            btnImg.setVisibility(View.GONE);
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
}