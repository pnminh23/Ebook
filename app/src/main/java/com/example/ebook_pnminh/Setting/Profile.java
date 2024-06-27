package com.example.ebook_pnminh.Setting;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.ebook_pnminh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private View mview;
    private ImageView imageAvatar;
    private EditText edtFullName, edtEmail;
    private Button btnUpdate;
    String uid = FirebaseAuth.getInstance().getUid();
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mview = findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(mview, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();
        initListeners();
        setUserInfo();
    }

    private void initUI() {
        imageAvatar = findViewById(R.id.img_avatar);
        edtFullName = findViewById(R.id.et_name);
        edtEmail = findViewById(R.id.et_email);
        btnUpdate = findViewById(R.id.btn_update);
    }

    private void initListeners() {
        imageAvatar.setOnClickListener(view -> onClickReQues());
        btnUpdate.setOnClickListener(view -> {
            updateUserInfo();
        });
    }
    private void updateUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String newFullName = edtFullName.getText().toString().trim();
            if (!newFullName.isEmpty()) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newFullName)
                        // You can add more fields to update like photo URL if needed
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Profile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(Profile.this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
            }
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(uid);
        reference.child("name").setValue(edtFullName.getText().toString().trim());
        reference.child("email").setValue(edtEmail.getText().toString().trim());

    }
    private void setUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Glide.with(this)
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.anh_dai_dien) // Placeholder image while loading
                        .error(R.drawable.anh_dai_dien) // Error image if Glide fails to load
                        .circleCrop()
                        .into(imageAvatar);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                String email = ""+snapshot.child("email").getValue();
                edtFullName.setText(name);
                edtEmail.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            edtFullName.setText(user.getDisplayName());
//            edtEmail.setText(user.getEmail());
//
//            Glide.with(this)
//                    .load(user.getPhotoUrl())
//                    .placeholder(R.drawable.anh_dai_dien) // Placeholder image while loading
//                    .error(R.drawable.anh_dai_dien) // Error image if Glide fails to load
//                    .circleCrop()
//                    .into(imageAvatar);
//        }
    }

    private void onClickReQues() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    setProfileImage(selectedImageUri);
                }
            });

    private void setProfileImage(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .placeholder(R.drawable.anh_dai_dien) // Placeholder image while loading
                .error(R.drawable.anh_dai_dien) // Error image if Glide fails to load
                .circleCrop()
                .into(imageAvatar);

        // You may also want to update the user's profile image on Firebase here
        // Example:
        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
        //         .setPhotoUri(imageUri)
        //         .build();
        // user.updateProfile(profileUpdates)
        //         .addOnCompleteListener(task -> {
        //             if (task.isSuccessful()) {
        //                 Toast.makeText(Profile.this, "Profile image updated", Toast.LENGTH_SHORT).show();
        //             } else {
        //                 Toast.makeText(Profile.this, "Failed to update profile image", Toast.LENGTH_SHORT).show();
        //             }
        //         });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Bạn đã từ chối cấp quyền truy cập vào thư viện ảnh.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}