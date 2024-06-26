package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import com.example.ebook_pnminh.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivcity extends AppCompatActivity {
    private TextView tvSignup;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private ActivityLoginBinding binding;
    private FirebaseAuth  firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();



        // xử lý khi chưa có tài khoản
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivcity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        // Xử lý khi nhấn vào Quên mật khẩu
        binding.tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivcity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý đăng nhập

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                validatedata();
            }
        });

    }
    private String email ="",password = "";
    private void validatedata() {
        // lấy dữ liệu nhập vào
        email = binding.edtEmail.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();
        // kiểm tra email, password
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,"Email không chính xác",Toast.LENGTH_SHORT).show();
        }else {
            loginuser();
        }
    }

    private void loginuser() {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivcity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivcity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivcity.this,"Lỗi: "+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }
}