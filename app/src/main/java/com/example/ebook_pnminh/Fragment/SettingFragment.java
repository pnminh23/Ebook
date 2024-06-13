package com.example.ebook_pnminh.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.ebook_pnminh.LoginActivcity;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.Setting.Profile;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Find profile button and set onClickListener
        RelativeLayout profileButton = view.findViewById(R.id.profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Profile activity
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
                // Do not finish the activity here
            }
        });

        // Find logout button and set onClickListener
        RelativeLayout logoutButton = view.findViewById(R.id.logout_section);
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

        return view;
    }
}
