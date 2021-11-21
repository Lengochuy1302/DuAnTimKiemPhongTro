package com.example.duanaeth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanaeth.LayoutChucNang.readOtpToSms;
import com.example.duanaeth.ArrayAdapter.Users;
import com.example.duanaeth.SplashScreen.IntroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProfile extends AppCompatActivity {

    public String linkRealTime;
    private String linkStorage = "gs://aethhouse.appspot.com";
    TextView btnTiepTuc;
    private DatabaseReference reference;
    private EditText tilUsername, tilHoTen, tilMail, tilPhone, verysdt, tilBirthday;
    private static final int REQUEST_IMAGE_OPEN = 2;
    private ImageView imgImageView;
    private TextView  hoantat, tieptuc;
    private Button guilai;
    private LinearLayout linersendotp;
    private TextView xoatk, ganNgay;
    private String item = "";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String textverificationId;
    FirebaseStorage storage = FirebaseStorage.getInstance(linkStorage);
    final StorageReference storageRef = storage.getReference();
    Calendar calendar = Calendar.getInstance();
    StorageReference mountainsRef = storageRef.child("image"+ calendar.getTimeInMillis() +".png");
    AutoCompleteTextView gioiTinh;
    private ArrayAdapter<String> arrayAdapter;
    private String gioitinh[] = {"Nam", "Nữ", "Khác"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        //ẩn toolBar
        getSupportActionBar().hide();
        //anh xạ
        anhxa();

        //Lấy dữ liệu thông tin từ FireAuth
        showAllUserData();

        //set ngày sinh
        ganNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonngay();
            }
        });

        //tắt button hoàn tất
        hoantat.setEnabled(true);
        requestSMSPermission();
        doccode();
        //set data giới tính
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gioitinh);
        gioiTinh.setAdapter(arrayAdapter);
        gioiTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });

        //lấy ảnh 
        imgImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_IMAGE_OPEN);
            }
        });
        
        //Xóa tài khoản
        findViewById(R.id.xoataikhoan).setOnClickListener(v -> {
            deleteUser();
        });


        //thêm dữ liệu
        themDuLieu();


    }

    //hàm thêm dữ liệu
    public void themDuLieu() {
        btnTiepTuc.setOnClickListener(v -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                return;
            }

            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("Profile");
            String numberphone = tilPhone.getText().toString().trim();
            Calendar calendar = Calendar.getInstance();
            String txtGioiTinh = item.trim();
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            Boolean checkError = true;
            if(tilHoTen.getText().toString().trim().isEmpty()){
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Hãy nhập tên của bạn để chúng tôi biết bạn là ai!!!")
                        .show();
                checkError = false;
            }

            if (txtGioiTinh.isEmpty()) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Xin lỗi! Bạn chưa chọn giới tính!!!")
                        .show();
                checkError = false;
            }

            if(tilBirthday.getText().toString().trim().isEmpty()){
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Bạn chưa nhập ngày sinh!!!")
                        .show();
                checkError = false;
            } else if (tilBirthday.getText().toString().trim().compareTo(currentDate) > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Ngày sinh của bạn phải bé hơn hôm nay!!!")
                        .show();
                checkError = false;
            }
            if(tilPhone.getText().toString().trim().isEmpty()){
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Dường như bạn quên nhập số điện thoại!!!")
                        .show();
                checkError = false;
            } else if(!Pattern.matches("0\\d{3}\\d{3}\\d{3}", numberphone)){
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Số điện thoại sai định dạng!!!")
                        .show();
                checkError = false;
            }

            if(checkError){
                String verifyphone =numberphone.substring(1,10) ;
                tieptuc.setVisibility(View.GONE);
                hoantat.setVisibility(View.VISIBLE);
                linersendotp.setVisibility(View.VISIBLE);
                guilai.isEnabled();
                //get số đt để gửi otp
                onClickVerifyPhone("+84"+verifyphone);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        new CountDownTimer(60000 + 100, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                guilai.setText("00:"+ millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish() {
                                guilai.setText("Gửi lại");
                                guilai.setEnabled(true);
                            }
                        }.start();

                    }
                },1000);

                hoantat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean checkError = true;
                        if(verysdt.getText().toString().trim().isEmpty()){
                            verysdt.setError("Mã OTP không được để trống");
                            checkError = false;

                        }

                        if(checkError) {
                            hoantat.setEnabled(false);
                            String OTPcode = verysdt.getText().toString().trim();
                            onClickSendOTPCode(OTPcode);
                        }
                    }
                });

                guilai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickSendOTPCodeAgain("+84"+verifyphone);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                new CountDownTimer(60000 + 100, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        guilai.setText("00:"+ millisUntilFinished/1000);
                                    }

                                    @Override
                                    public void onFinish() {
                                        guilai.setText("Gửi lại");
                                        guilai.setEnabled(true);
                                    }
                                }.start();

                            }
                        },1000);
                    }
                });
            }
        });
    }

    //hàm gửi mã otp, gửi lại otp và xác minh otp
    private void onClickVerifyPhone(String verifyphone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(verifyphone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                   // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Không thể xác thực!")
                                        .show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                textverificationId = "";
                                textverificationId = verificationId;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void onClickSendOTPCodeAgain(String verifyphone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(verifyphone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                   // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(UpdateProfile.this, "Xác thực không thành công",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                textverificationId = "";
                                textverificationId = verificationId;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void requestSMSPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(UpdateProfile.this, permission_list,56);
        }
    }

    private void onClickSendOTPCode(String otPcode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(textverificationId, otPcode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Đang upload file...");
        pDialog.setCancelable(false);
        pDialog.show();
        String nameUser = tilHoTen.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        Users users = new Users();
        users.setBirthday(tilBirthday.getText().toString().trim());
        users.setNumberphone(tilPhone.getText().toString().trim());
        users.setGioitinh(gioiTinh.getText().toString().trim());
        reference.setValue(users);
        // Get the data from an ImageView as bytes
        imgImageView.setDrawingCacheEnabled(true);
        imgImageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(UpdateProfile.this, "Lỗi!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nameUser)
                                .setPhotoUri(Uri.parse(String.valueOf(uri)))
                                .build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    startActivity(new Intent(UpdateProfile.this, TrangChu.class));
                                                    finishAffinity();
                                                }
                                            },1000);
                                        }
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });
    }

    private void doccode(){
        verysdt = (EditText) findViewById(R.id.nhapotp);
        new readOtpToSms().setEditText(verysdt);
    }

    //hàm onActivityResult set hình
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri full = data.getData();
            ImageView imgv = findViewById(R.id.imgavatar);
            imgv.setImageURI(full);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //hàm xóa tài khoản
    public void deleteUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Xóa tài khoản?")
                .setContentText("Lưu ý: Sau khi xác nhận xóa tài khoản thì bạn sẽ không thể khôi phục lại tài khoản. Bạn chắc chứ?")
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
                        sDialog.dismissWithAnimation();
                        Handler handler = new Handler();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SweetAlertDialog pDialoga = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.PROGRESS_TYPE);
                                            pDialoga.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialoga.setTitleText("Đang hoàn tất...");
                                            pDialoga.setCancelable(false);
                                            pDialoga.show();
                                            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users");
                                            reference.child(user.getUid()).removeValue(new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull  DatabaseReference ref) {

                                                }
                                            });
                                        }
                                    }
                                });
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(UpdateProfile.this, IntroActivity.class));
                                finishAffinity();
                            }
                        },3000);
                    }
                })
                .show();


    }

    //hàm lấy dữ liệu thông tin từ FireAuth
    private void showAllUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        tilHoTen.setText(name);
        tilMail.setText(email);
        if (photoUrl != null) {
            Picasso.get().load(photoUrl).into(imgImageView);
        }

        reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("Profile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String birthdayFromDB = dataSnapshot.child("birthday").getValue(String.class);
                String phoneFromDB = dataSnapshot.child("numberphone").getValue(String.class);
                String gioiTinhFromDB = dataSnapshot.child("gioitinh").getValue(String.class);
                int vitri = 0;

                tilBirthday.setText(birthdayFromDB);
                tilPhone.setText(phoneFromDB);
                if (gioiTinhFromDB.equals("Nam")) {
                    vitri = 0;
                } else if (gioiTinhFromDB.equals("Nữ")) {
                    vitri = 1;
                } else if (gioiTinhFromDB.equals("Khác")) {
                    vitri = 3;
                }
                arrayAdapter = new ArrayAdapter<String>(UpdateProfile.this, android.R.layout.simple_list_item_1, gioitinh);
                gioiTinh.setText(gioiTinhFromDB);
                gioiTinh.setAdapter(arrayAdapter);
                item = String.valueOf(arrayAdapter.getItem(1));



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(UpdateProfile.this, TrangChu.class));
            finishAffinity();
    }

    //hàm set ngày
    private void chonngay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tilBirthday.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam,thang,ngay);
        datePickerDialog.show();
    }

    //Ánh xạ
    final private void anhxa() {
        //gọi đường dẫn firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById
        btnTiepTuc = findViewById(R.id.btn_ok_capNhatActivity);
        xoatk = (TextView) findViewById(R.id.xoataikhoan);
        imgImageView = (ImageView) findViewById(R.id.imgavatar);
        tilHoTen = (EditText)  findViewById(R.id.til_hoTen_capNhatActivity);
        tilMail = findViewById(R.id.til_mail_capNhatActivity);
        tilPhone = (EditText)  findViewById(R.id.til_phone_capNhatActivity);
        tilBirthday = (EditText) findViewById(R.id.til_birtday_capNhatActivity);
        gioiTinh = (AutoCompleteTextView) findViewById(R.id.til_gioitinh_capNhatActivity);
        hoantat = (TextView) findViewById(R.id.hoantat);
        tieptuc = (TextView) findViewById(R.id.btn_ok_capNhatActivity);
        guilai = (Button) findViewById(R.id.guilai);
        verysdt = (EditText) findViewById(R.id.nhapotp);
        linersendotp = (LinearLayout) findViewById(R.id.linersendotp);
        ganNgay = (TextView) findViewById(R.id.ganngay);

    }


}