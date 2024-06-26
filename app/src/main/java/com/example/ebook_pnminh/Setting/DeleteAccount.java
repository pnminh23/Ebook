package com.example.ebook_pnminh.Setting;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ebook_pnminh.LoginActivcity;
import com.example.ebook_pnminh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DeleteAccount extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userDatabaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        Button cancelButton = view.findViewById(R.id.cancel_button);
        Button confirmButton = view.findViewById(R.id.confirm_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the fragment
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete user account
                if (currentUser != null) {
                    // First delete user data from Realtime Database
                    userDatabaseRef.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Now delete user account from Firebase Authentication
                            currentUser.delete().addOnCompleteListener(authTask -> {
                                if (authTask.isSuccessful()) {
                                    Toast.makeText(getContext(), "Tài khoản đã được xóa thành công.", Toast.LENGTH_SHORT).show();
                                    // Redirect to login activity
                                    Intent intent = new Intent(getActivity(), LoginActivcity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getContext(), "Không thể xóa tài khoản.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Không thể xóa dữ liệu người dùng.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}
