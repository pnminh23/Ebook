package com.example.ebook_pnminh;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.example.ebook_pnminh.Adapter.ViewPaperAdapter;
import com.example.ebook_pnminh.Fragment.CategoryFragment;
import com.example.ebook_pnminh.Fragment.HomeFragment;
import com.example.ebook_pnminh.Fragment.LibraryFragment;
import com.example.ebook_pnminh.Fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private BottomNavigationView bottomMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_paper);
        bottomMenu = findViewById(R.id.bottom_nav);
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new CategoryFragment());
        fragmentArrayList.add(new LibraryFragment());
        fragmentArrayList.add(new SettingFragment());
        ViewPaperAdapter adapter = new ViewPaperAdapter(this,fragmentArrayList);

        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position){
                switch (position){
                    case 0:
                        bottomMenu.setSelectedItemId(R.id.menu_home);
                        break;
                    case 1:
                        bottomMenu.setSelectedItemId(R.id.menu_category);
                        break;
                    case 2:
                        bottomMenu.setSelectedItemId(R.id.menu_library);
                        break;
                    case 3:
                        bottomMenu.setSelectedItemId(R.id.menu_setting);
                        break;
                    default:
                        bottomMenu.setSelectedItemId(R.id.menu_home);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int iteamid = menuItem.getItemId();
                if (iteamid==R.id.menu_home){
                    viewPager.setCurrentItem(0);
                }
                if (iteamid==R.id.menu_category){
                    viewPager.setCurrentItem(1);
                }
                if (iteamid==R.id.menu_library){
                    viewPager.setCurrentItem(2);
                }
                if (iteamid==R.id.menu_setting){
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });



    }
}