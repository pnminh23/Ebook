package com.example.ebook_pnminh.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ebook_pnminh.Adapter.TabAdapter;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.SearchActivity;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    private ViewPager2 tabViewPaper;
    private TabLayout tabLayout;
    private TabAdapter adapter;
    private View homeView;
    private TextView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        tabViewPaper = homeView.findViewById(R.id.tab_viewPaper);
        tabLayout = homeView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Khám phá"));
        tabLayout.addTab(tabLayout.newTab().setText("Phổ biến"));
        tabLayout.addTab(tabLayout.newTab().setText("Mới nhất"));
        adapter = new TabAdapter(getChildFragmentManager(),getLifecycle());
        tabViewPaper.setAdapter(adapter);
        search = homeView.findViewById(R.id.tv_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabViewPaper.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabViewPaper.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                super.onPageSelected(position);
            }
        });
        return homeView;


    }
}