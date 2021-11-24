package com.example.duanaeth.FragmentLayout.NhaTro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.duanaeth.LayoutChucNang.ThemNhaTro;
import com.example.duanaeth.R;
import com.example.duanaeth.SplashScreen.IntroActivity;
import com.example.duanaeth.TrangChu;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tab_NhaTro_Fragment extends Fragment {
    View view;
    FloatingActionButton btnThem;
    GridView rcv;


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

        rcv = view.findViewById(R.id.rcv_LoaiChi);


        List<HashMap<String,Object>> dsmenu = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> itemmenu = new HashMap<>();

        itemmenu.put("ten", "Phòng Trọ Phong Nhi");
        itemmenu.put("hinh", R.drawable.hinh1);
        itemmenu.put("giatien", "700.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 2");
        itemmenu.put("hinh", R.drawable.hinh4);
        itemmenu.put("giatien", "1.500.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 3");
        itemmenu.put("hinh", R.drawable.hinh2);
        itemmenu.put("giatien", "500.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 4");
        itemmenu.put("hinh", R.drawable.hinh3);
        itemmenu.put("giatien", "2.100.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 5");
        itemmenu.put("hinh", R.drawable.hinh1);
        itemmenu.put("giatien", "1.000.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 6");
        itemmenu.put("hinh", R.drawable.hinh2);
        itemmenu.put("giatien", "1.200.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 7");
        itemmenu.put("hinh", R.drawable.hinh4);
        itemmenu.put("giatien", "900.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 8");
        itemmenu.put("hinh", R.drawable.hinh2);
        itemmenu.put("giatien", "900.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 9");
        itemmenu.put("hinh", R.drawable.hinh1);
        itemmenu.put("giatien", "900.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 10");
        itemmenu.put("hinh", R.drawable.hinh3);
        itemmenu.put("giatien", "900.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 11");
        itemmenu.put("hinh", R.drawable.hinh1);
        itemmenu.put("giatien", "900.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);
        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phòng Trọ Phong Nhi 12");
        itemmenu.put("hinh", R.drawable.hinh4);
        itemmenu.put("giatien", "900.000 VND");
        itemmenu.put("diachi", "100 Ngô Gia Tự, Tân An, Tp. Buôn Ma Thuột, Đắk Lắk");
        dsmenu.add(itemmenu);

        String[]from = {"ten", "hinh", "giatien", "diachi"};
        int[]to ={R.id.tenmenu, R.id.hinhmenu, R.id.giatien, R.id.diachi};
        SimpleAdapter myadapter = new SimpleAdapter(getContext(), dsmenu, R.layout.itemsanpham, from, to);
        rcv.setAdapter(myadapter);

        return view;
    }
}