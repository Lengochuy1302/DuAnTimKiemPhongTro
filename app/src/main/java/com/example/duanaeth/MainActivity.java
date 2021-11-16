package com.example.duanaeth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    TextView btnDangNhap, btnDangKy;
    ImageView btnEmailDK, btnQuayLai, btnFacebook, btnFacebook1;
    EditText edtEmailDN, edtEmailDK, edtMatKhauDN, edtMatKhauDK, edtMatKhauDKLai;
    LinearLayout linearLayoutDangKy, linearLayoutDangNhap;
    LoginButton loginButton;

    //Firebase
    public String linkRealTime;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private CallbackManager fbCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        anhxa();

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //ẩn toolBar
        getSupportActionBar().hide();

        //chuyển form đăng ký
        btnEmailDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutDangKy.setVisibility(View.VISIBLE);
                linearLayoutDangNhap.setVisibility(View.GONE);
            }
        });

        //chuyển form đăng nhập
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutDangKy.setVisibility(View.GONE);
                linearLayoutDangNhap.setVisibility(View.VISIBLE);
            }
        });

        //Chức năng đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangNhap();
            }
        });

        //Chức năng đăng ký
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKi();
            }
        });

        //LoginFacebook
        setUpLoginFacebook();

    }


    //hàm login facebook
    private void setUpLoginFacebook() {
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();
                    fbCallbackManager = CallbackManager.Factory.create();
                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                            Arrays.asList("email", "public_profile"));
                    LoginManager.getInstance().registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            pDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                            handleFacebookAccessToken(loginResult.getAccessToken());
                            Intent introIntent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(introIntent);
                            finishAffinity();
                        }

                        @Override
                        public void onCancel() {
                            pDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Dang nhap cancel", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            pDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Dang nhap err", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("AAAU", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AAAU", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AAAU", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //hàm đăng ký
    private void dangKi() {

            String emaildk = edtEmailDK.getText().toString().trim();
            String matkhaudk = edtMatKhauDK.getText().toString().trim();
            String matkhaudklai = edtMatKhauDKLai.getText().toString().trim();

            Boolean checkError = true;
            if(emaildk.isEmpty()) {
                edtEmailDK.setError("Email không được để trống");
                checkError = false;
            }
            else if (!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", emaildk)){
                edtEmailDK.setError("Email sai định dạng!");
                checkError = false;
            }
            if(matkhaudklai.isEmpty()){
                edtMatKhauDKLai.setError("Nhập lại mật khẩu không được bỏ trống!");
                checkError = false;
             }
            if(matkhaudk.length()<6){
                edtMatKhauDK.setError("Mật khẩu quá yếu cần đủ 6 ký tự!");
                checkError = false;
            }
            if(!matkhaudklai.equals(matkhaudk)){
                edtMatKhauDKLai.setError("Nhập lại mật khẩu không khớp!");
                checkError = false;
            }

            if(checkError){
                btnDangKy.setEnabled(false);
                mAuth.createUserWithEmailAndPassword(emaildk, matkhaudk)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Loading ...");
                                    pDialog.setCancelable(true);
                                    pDialog.show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pDialog.dismiss();
                                            String name = user.getDisplayName();
                                            if (name == null) {
                                                Device device = new Device();
                                                String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                                device.setID("ThietBi");
                                                device.setTenDevice(iddevice);
                                                reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                                                reference.child("ThietBi").setValue(device);
                                                Intent introIntent = new Intent(MainActivity.this, MainActivity2.class);
                                                startActivity(introIntent);
                                                finishAffinity();
                                            } else {
                                                Device device = new Device();
                                                String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                                device.setID("ThietBi");
                                                device.setTenDevice(iddevice);
                                                reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                                                reference.child("ThietBi").setValue(device);
                                                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                                                finishAffinity();
                                            }

                                        }
                                    }, 2000);


                                } else {
                                    btnDangKy.setEnabled(true);
                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Tài khoản đã tồn tại!")
                                            .show();

                                }
                            }
                        });

            }
    }

    //hàm đăng nhập
    private void dangNhap() {
            mAuth = FirebaseAuth.getInstance();

            String emailDN = edtEmailDN.getText().toString().trim();
            String mauKhauDN = edtMatKhauDN.getText().toString().trim();

            Boolean checkError = true;
            if(emailDN.isEmpty()){
                edtEmailDN.setError("Email không được để trống!");
                checkError = false;
            }else if(!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", emailDN)){
                edtEmailDN.setError("Email sai định dạng!");
                checkError = false;
            }

            if(mauKhauDN.isEmpty()){
                edtMatKhauDN.setError("Mật khẩu không được để trống!");
                checkError = false;
            }

            if (checkError == true) {
                btnDangNhap.setEnabled(false);
                mAuth.signInWithEmailAndPassword(emailDN, mauKhauDN)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Loading ...");
                                    pDialog.setCancelable(true);
                                    pDialog.show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pDialog.dismiss();
                                            String name = user.getDisplayName();
                                            if (name == null) {
                                                Device device = new Device();
                                                String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                                device.setID("ThietBi");
                                                device.setTenDevice(iddevice);
                                                reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                                                reference.child("ThietBi").setValue(device);
                                                Intent introIntent = new Intent(MainActivity.this, MainActivity2.class);
                                                startActivity(introIntent);
                                                finishAffinity();
                                            } else {
                                                Device device = new Device();
                                                String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                                device.setID("ThietBi");
                                                device.setTenDevice(iddevice);
                                                reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                                                reference.child("ThietBi").setValue(device);
                                                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                                                finishAffinity();
                                            }
                                        }
                                    }, 2000);

                                } else {
                                    btnDangNhap.setEnabled(true);
                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Sai tài khoản hoặc mật khẩu!")
                                            .show();
                                }
                            }
                        });
            }

    }

    final private void anhxa() {
        //gọi đường dẫn firebase
        linkRealTime = getResources().getString(R.string.link_RealTime_Database);
        //findViewById
        btnDangNhap = findViewById(R.id.btndangnhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnEmailDK = findViewById(R.id.btnDangKyEmail);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        linearLayoutDangNhap = findViewById(R.id.linearLayoutDangNhap);
        linearLayoutDangKy = findViewById(R.id.linearLayoutdangky);
        edtEmailDK = findViewById(R.id.emailDK);
        edtMatKhauDK = findViewById(R.id.matKhauDK);
        edtMatKhauDKLai = findViewById(R.id.matKhauDKLai);
        edtEmailDN = findViewById(R.id.emailDN);
        edtMatKhauDN = findViewById(R.id.matkhauDN);
        btnFacebook = findViewById(R.id.btnLoginFacebook);
//        btnFacebook1 = findViewById(R.id.btnLoginFacebook1);
    }
}