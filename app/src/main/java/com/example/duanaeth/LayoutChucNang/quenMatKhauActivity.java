package com.example.duanaeth.LayoutChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanaeth.DangNhapDangKy;
import com.example.duanaeth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class quenMatKhauActivity extends AppCompatActivity {
    private EditText regmail;
    private TextView regmailbtn, back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        getSupportActionBar().hide();

        back = findViewById(R.id.btnTroLaiQMK);
        regmail = findViewById(R.id.emailQMK);
        regmailbtn = findViewById(R.id.btnXacNhanQMK);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quenMatKhauActivity.this, DangNhapDangKy.class));
            }
        });
        getSupportActionBar().hide();
        requesgmail();

    }


    private void requesgmail() {
        regmailbtn.setOnClickListener(v -> {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = regmail.getText().toString().trim();

            if (emailAddress.isEmpty()) {
                regmail.setError("Không được để trống!");
                return;
            }
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(quenMatKhauActivity.this, "Email đặt lại password đã được gửi!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(quenMatKhauActivity.this, DangNhapDangKy.class));
                            }
                        }
                    });

        });


    }
}
