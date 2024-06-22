package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtEmailForgot;
    private Button btnResetPassword;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        edtEmailForgot = findViewById(R.id.edt_email_forgot);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        tvLogin = findViewById(R.id.tv_login_link);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivcity.class);
                startActivity(intent);
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailForgot.getText().toString().trim();

                if (email.isEmpty()) {
                    edtEmailForgot.setError("Vui lòng nhập email");
                    edtEmailForgot.requestFocus();
                    return;
                }

                // Kiểm tra email tồn tại bằng cách gửi email đặt lại mật khẩu
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Email đặt lại mật khẩu đã được gửi", Toast.LENGTH_SHORT).show();
                                    // Chuyển hướng về trang đăng nhập sau khi email được gửi thành công
                                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivcity.class);
                                    startActivity(intent);
                                    finish(); // Kết thúc Activity hiện tại
                                } else {
                                    String errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại sau.";
                                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        errorMessage = "Email không tồn tại";
                                    } else if (task.getException() != null) {
                                        errorMessage = task.getException().getMessage();
                                    }
                                    Toast.makeText(ForgotPasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
