package com.example.shopeee.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopeee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText email, password;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        // lưu màn hình khi lần đầu tải ứng dụng
        sharedPreferences = getSharedPreferences("onBoardingScreeen", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firsttime", true);
        if (isFirstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firsttime", false);
            editor.commit();

            Intent intent = new Intent(Registration.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void init() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

    }

    public void SignUp(View view) {
        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Bạn phải điền email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }
        if (userPass.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Registration.this, "Bạn đã tạo thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Registration.this, "Đăng ký chưa thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void SignIn(View view) {
        Intent intent = new Intent(Registration.this, LoginActivity.class);
        startActivity(intent);
    }
}