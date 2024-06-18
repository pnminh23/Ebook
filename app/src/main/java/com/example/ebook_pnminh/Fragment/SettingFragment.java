package com.example.ebook_pnminh.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ebook_pnminh.LoginActivcity;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.Setting.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class SettingFragment extends Fragment {

    private Button profileImageButton;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private RelativeLayout profileButton;
    private RelativeLayout logoutButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private ListenerRegistration userListener;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize Views
        profileImageButton = view.findViewById(R.id.profile_image_button);
        userNameTextView = view.findViewById(R.id.tv_user_name);
        userEmailTextView = view.findViewById(R.id.tv_user_email);
        profileButton = view.findViewById(R.id.profile);
        logoutButton = view.findViewById(R.id.logout_section);

        // Set onClickListener for Profile button
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Profile activity
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
                // Do not finish the activity here
            }
        });

        // Set onClickListener for Logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivcity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                startActivity(intent);
                getActivity().finish(); // Close the hosting activity
            }
        });

        // Set onClickListener for Profile image button
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Load user profile data from Firestore
        if (currentUser != null) {
            DocumentReference userRef = db.collection("users").document(currentUser.getUid());
            userListener = userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        // Handle errors
                        Toast.makeText(getContext(), "Error while loading user data.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        // Retrieve user data
                        String userName = documentSnapshot.getString("name");
                        String userEmail = documentSnapshot.getString("email");

                        // Update UI
                        userNameTextView.setText(userName);
                        userEmailTextView.setText(userEmail);
                    }
                }
            });
        }
        RelativeLayout DeleteAccount = view.findViewById(R.id.Delete_Account);
        DeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete user account
                deleteUserAccount(Gravity.CENTER);
            }
        });
        return view;
    }
    //ham Delete
    private void deleteUserAccount(int gravity) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_delete_account);

        Window window = dialog.getWindow();
        if (window == null){
            return ;
        }
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(gravity);


        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        if (Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        Button comfirmButton = dialog.findViewById(R.id.confirm_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
                      dialog.dismiss();
           }
        });
        dialog.show();
    }

    // Open gallery for selecting image
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Now you can do whatever you want with the imageUri, for example load into ImageView using Glide or Picasso
            // Example with Picasso:
            // Picasso.get().load(imageUri).into(profileImageButton);

            // Here, you can also upload the imageUri to Firebase Storage or process it further as per your app's requirements.
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up listener when fragment is destroyed
        if (userListener != null) {
            userListener.remove();
        }
    }
}
