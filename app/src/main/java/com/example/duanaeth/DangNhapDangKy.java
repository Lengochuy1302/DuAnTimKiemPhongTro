package com.example.duanaeth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanaeth.ArrayAdapter.Device;
import com.example.duanaeth.LayoutChucNang.quenMatKhauActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DangNhapDangKy extends AppCompatActivity {

    TextView btnDangNhap, btnDangKy, btnQuenMK, logo;
    ImageView btnEmailDK, btnQuayLai, btnFacebook, btnFacebook1, btnGoogle, btnGoogle1;
    EditText edtEmailDN, edtEmailDK, edtMatKhauDN, edtMatKhauDK, edtMatKhauDKLai;
    LinearLayout linearLayoutDangKy, linearLayoutDangNhap;
    String namegoogle, avatargoogle, email;

    //Firebase
    public String linkRealTime;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private CallbackManager fbCallbackManager;
    private Animation zoom_out,slidedown, slideup;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap_dang_ky);
        //??nh x???
        anhxa();


        //Anima
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        slidedown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slidedown);
        slideup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideup);
        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(zoom_out);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //???n toolBar
        getSupportActionBar().hide();

        //chuy???n form ????ng k??
        btnEmailDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutDangNhap.startAnimation(slidedown);
                linearLayoutDangNhap.setVisibility(View.GONE);
                linearLayoutDangKy.startAnimation(slideup);
                linearLayoutDangKy.setVisibility(View.VISIBLE);
            }
        });

        //chuy???n form ????ng nh???p
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutDangKy.startAnimation(slidedown);
                linearLayoutDangKy.setVisibility(View.GONE);
                linearLayoutDangNhap.startAnimation(slideup);
                linearLayoutDangNhap.setVisibility(View.VISIBLE);
            }
        });

        //Ch???c n??ng ????ng nh???p
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangNhap();
            }
        });

        //Ch???c n??ng ????ng k??
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKi();
            }
        });

        //LoginFacebook
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpLoginFacebook();
            }
        });
        btnFacebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpLoginFacebook();
            }
        });

        //loginGoogle
        creatRequest();
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btnGoogle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //qu??n mk
        btnQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickQuenMK();
            }
        });

    }


    //h??m qu??n mk
    public void onClickQuenMK() {
        Intent introIntent = new Intent(DangNhapDangKy.this, quenMatKhauActivity.class);
        startActivity(introIntent);
        finishAffinity();
    }


    //h??m login facebook
    private void setUpLoginFacebook() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                SweetAlertDialog pDialog = new SweetAlertDialog(DangNhapDangKy.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();
                    fbCallbackManager = CallbackManager.Factory.create();
                    LoginManager.getInstance().logInWithReadPermissions(DangNhapDangKy.this,
                            Arrays.asList("email", "public_profile"));
                    LoginManager.getInstance().registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            pDialog.dismiss();
                            handleFacebookAccessToken(loginResult.getAccessToken());
                            Intent introIntent = new Intent(DangNhapDangKy.this, UpdateProfile.class);
                            startActivity(introIntent);
                            finishAffinity();
                        }

                        @Override
                        public void onCancel() {
                            pDialog.dismiss();
                            Toast.makeText(DangNhapDangKy.this, "Dang nhap cancel", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            pDialog.dismiss();
                            Toast.makeText(DangNhapDangKy.this, "Dang nhap err", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DangNhapDangKy.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //h??m login b???ng google
    private void creatRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Device device = new Device();
                            String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                            device.setID("ThietBi");
                            device.setTenDevice(iddevice);
                            reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                            reference.child("ThietBi").setValue(device);
                            String phone = user.getPhoneNumber();
                            if (phone == null) {
                                Intent intent = new Intent(getApplicationContext(), UpdateProfile.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), TrangChu.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DangNhapDangKy.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(DangNhapDangKy.this, "Sorry.", Toast.LENGTH_SHORT).show();
            }
        } else {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }



    //h??m ????ng k??
    private void dangKi() {
            String emaildk = edtEmailDK.getText().toString().trim();
            String matkhaudk = edtMatKhauDK.getText().toString().trim();
            String matkhaudklai = edtMatKhauDKLai.getText().toString().trim();

            Boolean checkError = true;
            if(emaildk.isEmpty()) {
                edtEmailDK.setError("Email kh??ng ???????c ????? tr???ng");
                checkError = false;
            }
            else if (!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", emaildk)){
                edtEmailDK.setError("Email sai ?????nh d???ng!");
                checkError = false;
            }
            if(matkhaudklai.isEmpty()){
                edtMatKhauDKLai.setError("Nh???p l???i m???t kh???u kh??ng ???????c b??? tr???ng!");
                checkError = false;
             }
            if(matkhaudk.length()<6){
                edtMatKhauDK.setError("M???t kh???u qu?? y???u c???n ????? 6 k?? t???!");
                checkError = false;
            }
            if(!matkhaudklai.equals(matkhaudk)){
                edtMatKhauDKLai.setError("Nh???p l???i m???t kh???u kh??ng kh???p!");
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
                                    SweetAlertDialog pDialog = new SweetAlertDialog(DangNhapDangKy.this, SweetAlertDialog.PROGRESS_TYPE);
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
                                                Intent introIntent = new Intent(DangNhapDangKy.this, UpdateProfile.class);
                                                startActivity(introIntent);
                                                finishAffinity();
                                            } else {
                                                Device device = new Device();
                                                String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                                device.setID("ThietBi");
                                                device.setTenDevice(iddevice);
                                                reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                                                reference.child("ThietBi").setValue(device);
                                                startActivity(new Intent(DangNhapDangKy.this, UpdateProfile.class));
                                                finishAffinity();
                                            }

                                        }
                                    }, 2000);


                                } else {
                                    btnDangKy.setEnabled(true);
                                    new SweetAlertDialog(DangNhapDangKy.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("T??i kho???n ???? t???n t???i!")
                                            .show();

                                }
                            }
                        });

            }
    }


    //h??m ????ng nh???p
    private void dangNhap() {
            mAuth = FirebaseAuth.getInstance();

            String emailDN = edtEmailDN.getText().toString().trim();
            String mauKhauDN = edtMatKhauDN.getText().toString().trim();

            Boolean checkError = true;
            if(emailDN.isEmpty()){
                edtEmailDN.setError("Email kh??ng ???????c ????? tr???ng!");
                checkError = false;
            }else if(!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", emailDN)){
                edtEmailDN.setError("Email sai ?????nh d???ng!");
                checkError = false;
            }

            if(mauKhauDN.isEmpty()){
                edtMatKhauDN.setError("M???t kh???u kh??ng ???????c ????? tr???ng!");
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
                                    SweetAlertDialog pDialog = new SweetAlertDialog(DangNhapDangKy.this, SweetAlertDialog.PROGRESS_TYPE);
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
                                                Intent introIntent = new Intent(DangNhapDangKy.this, UpdateProfile.class);
                                                startActivity(introIntent);
                                                finishAffinity();
                                            } else {
                                                Device device = new Device();
                                                String iddevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                                device.setID("ThietBi");
                                                device.setTenDevice(iddevice);
                                                reference = FirebaseDatabase.getInstance(linkRealTime).getReference("users").child(user.getUid()).child("DeviceID");
                                                reference.child("ThietBi").setValue(device);
                                                startActivity(new Intent(DangNhapDangKy.this, UpdateProfile.class));
                                                finishAffinity();
                                            }
                                        }
                                    }, 2000);

                                } else {
                                    btnDangNhap.setEnabled(true);
                                    new SweetAlertDialog(DangNhapDangKy.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Sai t??i kho???n ho???c m???t kh???u!")
                                            .show();
                                }
                            }
                        });
            }

    }

    final private void anhxa() {
        //g???i ???????ng d???n firebase
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
        btnGoogle = findViewById(R.id.btnLoginGoogle);
        btnGoogle1 = findViewById(R.id.btnLoginGoogle1);
        btnFacebook1 = findViewById(R.id.btnLoginFacebook1);
        logo = findViewById(R.id.logoapp);
        btnQuenMK = findViewById(R.id.btnQuenMK);
    }
}