package com.example.ebook_pnminh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ebook_pnminh.Home.DiscoverFragment;
import com.example.ebook_pnminh.Home.NewFragment;
import com.example.ebook_pnminh.Home.PopularFragment;

public class TabAdapter extends FragmentStateAdapter {


    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DiscoverFragment();
            case 1:
                return new PopularFragment();
            case 2:
                return new NewFragment();
            default:
                return new DiscoverFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
