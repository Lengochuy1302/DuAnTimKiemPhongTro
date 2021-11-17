package com.example.duanaeth;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        ImageView anhChinh = this.findViewById(R.id.anhChinh);

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.zoom);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.chay_tu_duoi_len);

        TextView text1 = findViewById(R.id.text1);
        TextView text3 = findViewById(R.id.text3);
        TextView text2 = findViewById(R.id.text2);
        setTextViewColor(text1,
                getResources().getColor(R.color.greentext),
                getResources().getColor(R.color.bluetext));

        setTextViewColor(text2,
                getResources().getColor(R.color.violet),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.red)
                );

        setTextViewColor(text3,
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.violet)
        );

        anhChinh.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                text1.setAnimation(anim1);
                text2.setAnimation(anim2);
                text3.setAnimation(anim1);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.VISIBLE);
                Intent intent = new Intent(IntroActivity.this, DangNhapDangKy.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void setTextViewColor(TextView textView, int...color){
        TextPaint paint  = textView.getPaint();
        float height = paint.measureText(textView.getText().toString());

        Shader shader = new LinearGradient(0, 0, height, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);

    }

    @Override
    protected void onStart() {
        super.onStart();
        clickrequest();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return;
        } else {
            startActivity(new Intent(IntroActivity.this, UpdateProfile.class));
        }
    }

    private void clickrequest() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        TedPermission.with(IntroActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("Nếu bạn không cho phép thì một số chức năng như 'Nhập văn bản bằng giọng nói', 'Upload avatar' không thể sử dụng được!\n \nNếu bạn muốn bật lại nó thì hãy vào phần [Setting] > [Quyền truy cập] > [Bật các quyền truy cập]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .check();
    }

}