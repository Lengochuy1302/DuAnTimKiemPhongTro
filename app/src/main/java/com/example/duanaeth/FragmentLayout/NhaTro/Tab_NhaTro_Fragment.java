package com.example.duanaeth.FragmentLayout.NhaTro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanaeth.LayoutChucNang.ThemNhaTro;
import com.example.duanaeth.R;
import com.example.duanaeth.SplashScreen.IntroActivity;
import com.example.duanaeth.TrangChu;
import com.github.clans.fab.FloatingActionButton;

public class Tab_NhaTro_Fragment extends Fragment {
    View view;
    FloatingActionButton btnThem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_nha_tro_tab, container, false);
        btnThem = view.findViewById(R.id.addBtn);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThemNhaTro.class));
            }
        });





        return view;
    }
}