package com.example.ebook_pnminh.Setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ebook_pnminh.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Profile extends Fragment {

    private View mView;
    private EditText edtName, edtEmail, edtPhone, edtNewPassword, edtOldPassword;
    private Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.activity_profile, container, false);
        initUI();
        setUserInfo();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
        return mView;
    }

    private void initUI() {
        edtName = mView.findViewById(R.id.et_name);
        edtEmail = mView.findViewById(R.id.et_email);
        edtPhone = mView.findViewById(R.id.et_phone);
        edtNewPassword = mView.findViewById(R.id.et_new_password);
        edtOldPassword = mView.findViewById(R.id.et_old_password);
        btnUpdate = mView.findViewById(R.id.btn_update);
    }

    private void setUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        edtName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhoneNumber());
    }

    private void updateUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String oldPassword = edtOldPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật thông tin người dùng
        boolean infoUpdated = false;

        // Cập nhật tên hiển thị
        if (!name.equals(user.getDisplayName())) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            infoUpdated = true;
        }

        // Cập nhật email
        if (!email.equals(user.getEmail())) {
            user.updateEmail(email).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            infoUpdated = true;
        }

        // Firebase không hỗ trợ cập nhật số điện thoại trực tiếp qua API

        // Cập nhật mật khẩu
        if (!TextUtils.isEmpty(oldPassword) && !TextUtils.isEmpty(newPassword)) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (!TextUtils.isEmpty(oldPassword) || !TextUtils.isEmpty(newPassword)) {
            Toast.makeText(getContext(), "Vui lòng nhập cả mật khẩu cũ và mới", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thông báo cập nhật thành công nếu không có lỗi xảy ra
        if (infoUpdated) {
            Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
