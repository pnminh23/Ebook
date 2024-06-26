package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.ebook_pnminh.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivcity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth and Database Reference
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserReset ");

        setupListeners();
    }

    private void setupListeners() {
        // Handle sign up
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivcity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // Handle forgot password
        binding.tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivcity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Handle login
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateData();

                FirebaseUser user = firebaseAuth.getCurrentUser();



            }
        });
    }

    private void validateData() {
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không chính xác", Toast.LENGTH_SHORT).show();
        } else {
            loginUser(email, password);
        }
    }

    private void loginUser(final String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivcity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            updateUserDatabase(uid, email, password);
                        }
                        Intent intent = new Intent(LoginActivcity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivcity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserDatabase(String uid, String email, String password) {
        DatabaseReference userRef = databaseReference.child(uid);
        userRef.child("last_login").setValue(System.currentTimeMillis())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userRef.child("email").setValue(email);
                        userRef.child("password_reset").setValue(true);
                        userRef.child("new_password").setValue(password); // This line should be avoided in real applications due to security concerns
                        Toast.makeText(LoginActivcity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivcity.this, "Lỗi cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
