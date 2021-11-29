package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanaeth.DangNhapDangKy;
import com.example.duanaeth.R;
import com.example.duanaeth.TrangChu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class doiMatKhauActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword, txtRepassword;
    TextView btnDone, btnQuaylai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        getSupportActionBar().hide();
        init();

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(doiMatKhauActivity.this, TrangChu.class));
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    return;
                }
                String email = user.getEmail();
                Boolean checkError = true;

                if (!email.equals(txtEmail.getText().toString().trim())) {
                    txtEmail.setError("Tài khoản Email không khớp");
                    checkError = false;
                }

                if(!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", txtEmail.getText().toString())){
                    txtEmail.setError("Email sai định dạng!");
                    checkError = false;
                }

                if(!txtRepassword.getText().toString().equals(txtPassword.getText().toString())){
                    txtRepassword.setError("Nhập lại mật khẩu không đúng!");
                    checkError = false;
                }
//
                if (txtRepassword.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty()) {
                    txtRepassword.setError("Không được để trống");
                    txtPassword.setError("Không được để trống");
                    checkError = false;
                }

                if (checkError) {
                    String newPassword = txtPassword.getText().toString().trim();
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        final ProgressDialog progressDialog = ProgressDialog.show(doiMatKhauActivity.this,"Thông Báo","Đang kiểm tra...");
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                Toast.makeText(doiMatKhauActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(doiMatKhauActivity.this, DangNhapDangKy.class));
                                                finishAffinity();
                                            }
                                        },2500);

                                    } else {
                                        Toast.makeText(doiMatKhauActivity.this, "Lỗi! Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
    private void init(){
        txtEmail =  findViewById(R.id.emailCu);
        txtPassword =  findViewById(R.id.matkhauMoi);
        txtRepassword =  findViewById(R.id.nhapLaiMk);
        btnDone = findViewById(R.id.btnXacNhan);
        btnQuaylai =  findViewById(R.id.btnTroLai);
    }
}