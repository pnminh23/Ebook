package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebook_pnminh.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private TextView tvLogin;
    private Button btnSignup;
    private ActivitySignupBinding binding;

    //Khai báo firebase
    //FirebaseAuth là một dịch vụ của Firebase giúp xác thực người dùng trong ứng dụng Android
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tvLogin = findViewById(R.id.tv_Login);
        btnSignup = findViewById(R.id.btn_Signup);
        // init firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedata();

            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivcity.class);
                startActivity(intent);
            }
        });


    }
    private String name = "",email = "",password = "";
    private void validatedata(){
        // lấy dữ liệu
        name = binding.edtName.getText().toString().trim();
        email = binding.edtEmail.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();
        String cpassword = binding.edtConfrim.getText().toString().trim();
        // validate dữ liệu
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(cpassword)){
            Toast.makeText(this,"Vui lòng nhập dầy đủ thông tin",Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,"Email không hợp lệ",Toast.LENGTH_SHORT).show();
        } else if (!password.equals(cpassword)) {
            Toast.makeText(this,"Mật khẩu xác thực chưa chính xác",Toast.LENGTH_SHORT).show();
        }else{
            createUserAccount();
        }
    }

    private void createUserAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                updateUserInfor();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this,"Lỗi: "+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updateUserInfor() {
        long timestamp = System.currentTimeMillis();
        // lấy user id hiện tại, vì người dùng đã đăng kí nên ta có thể nhận ngay bây h
        String uid = firebaseAuth.getUid();
        // cài đặt và thêm dữ liệu vào db
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid",uid);
        hashMap.put("email",email);
        hashMap.put("name",name);
        hashMap.put("password",password);
        hashMap.put("profileImage","");
        hashMap.put("timestamp",timestamp);
        // thêm dữ liệu vào db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(uid).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SignupActivity.this,"Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this,LoginActivcity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        // data failed adding to db
                        Toast.makeText(SignupActivity.this,"Lỗi: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}