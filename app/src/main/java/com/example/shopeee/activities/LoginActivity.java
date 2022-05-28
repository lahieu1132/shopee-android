package com.example.shopeee.activities;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    EditText  password, email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // anh xa
        init();
    }

    public void init() {
        password = findViewById(R.id.password1);
        email = findViewById(R.id.email1);

        auth = FirebaseAuth.getInstance();
    }

    public void SignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, Registration.class);
        startActivity(intent);
    }

    public void SignIn(View view) {
        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();

        auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "Bạn phải nhập lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}