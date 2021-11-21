package com.example.duanaeth.FragmentLayout.NhaTro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.duanaeth.R;
import com.google.android.material.tabs.TabLayout;


public class NhaTro_Fragment extends Fragment {

    private ViewPager viewPager;
     private   TabLayout tabLayout;
    View view;
    public NhaTro_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_viewpager_nha_tro, container, false);
        viewPager=view.findViewById(R.id.viewpager_khoanthu);
        tabLayout=view.findViewById(R.id.tablayout_khoanthu);

        tabLayout.addTab(tabLayout.newTab().setText("Danh sách"));
        tabLayout.addTab(tabLayout.newTab().setText("Bản đồ"));

        NhaTro_viewPagerAdapter adapter = new NhaTro_viewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.getTabAt(0).setIcon(R.drawable.cash);
//        tabLayout.getTabAt(1).setIcon(R.drawable.money);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }


}
