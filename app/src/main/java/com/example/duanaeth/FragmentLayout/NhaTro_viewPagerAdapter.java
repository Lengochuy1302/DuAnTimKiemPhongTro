package com.example.duanaeth.FragmentLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duanaeth.Tab_MapsFragment;


public class NhaTro_viewPagerAdapter extends FragmentStatePagerAdapter {
    int numberTab = 2;


    public NhaTro_viewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab_NhaTro_Fragment tab_khoanThu_fragment = new Tab_NhaTro_Fragment();
                return tab_khoanThu_fragment;
            case 1:
                Tab_MapsFragment tab_loaiThu_fragment = new Tab_MapsFragment();
                return tab_loaiThu_fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberTab;
    }

}