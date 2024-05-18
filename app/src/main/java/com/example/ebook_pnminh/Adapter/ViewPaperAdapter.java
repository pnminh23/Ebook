package com.example.ebook_pnminh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ebook_pnminh.Fragment.CategoryFragment;
import com.example.ebook_pnminh.Fragment.HomeFragment;
import com.example.ebook_pnminh.Fragment.LibraryFragment;
import com.example.ebook_pnminh.Fragment.SettingFragment;

import java.util.ArrayList;

public class ViewPaperAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> arr;

    public ViewPaperAdapter(@NonNull FragmentActivity frm, ArrayList<Fragment> arr) {
        super(frm);
        this.arr = arr;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arr.get(position);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}
