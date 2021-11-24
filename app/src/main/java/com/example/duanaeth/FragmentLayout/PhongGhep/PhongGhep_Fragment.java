package com.example.duanaeth.FragmentLayout.PhongGhep;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.duanaeth.LayoutChucNang.ThemNhaTro;
import com.example.duanaeth.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhongGhep_Fragment extends Fragment {
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
        view = inflater.inflate(R.layout.fragment_phong_ghep_, container, false);
        btnThem = view.findViewById(R.id.addBtnOGhep);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThemNhaTro.class));
            }
        });


        rcv = view.findViewById(R.id.rcv_oghep);


        List<HashMap<String,Object>> dsmenu = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> itemmenu = new HashMap<>();

        itemmenu.put("ten", "Hoàng Tuấn Đạt");
        itemmenu.put("hinh", R.drawable.hoangtuandat);
        itemmenu.put("giatien", "Khoảng 700.000 - 900.000 VND");
        itemmenu.put("diachi", "Tân An - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "2/4 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Lê Thùy Linh");
        itemmenu.put("hinh", R.drawable.gai5);
        itemmenu.put("giatien", "Khoảng 1.000.000 - 1.200.000 VND");
        itemmenu.put("diachi", "Tân Lập - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "1/2 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Võ Thị Diễm My");
        itemmenu.put("hinh", R.drawable.anhgai2);
        itemmenu.put("giatien", "Khoảng 800.000 - 900.000 VND");
        itemmenu.put("diachi", "Tân An - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "2/3 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Nguyễn Văn Hoàng");
        itemmenu.put("hinh", R.drawable.trai1);
        itemmenu.put("giatien", "Khoảng 600.000 - 800.000 VND");
        itemmenu.put("diachi", "Tân Lợi - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "2/4 Người");
        dsmenu.add(itemmenu);


        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Nguyễn Trường Giang");
        itemmenu.put("hinh", R.drawable.truonggiang);
        itemmenu.put("giatien", "Khoảng 1.500.000 - 1.700.000 VND");
        itemmenu.put("diachi", "Duy Hòa - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "1/2 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Hoàng Thị Yến");
        itemmenu.put("hinh", R.drawable.anhgai4);
        itemmenu.put("giatien", "Khoảng 650.000 - 800.000 VND");
        itemmenu.put("diachi", "Ea Tam - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "2/3 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Trần Thị Trúc Linh");
        itemmenu.put("hinh", R.drawable.anhgai3);
        itemmenu.put("giatien", "Khoảng 1.000.000 - 1.200.000 VND");
        itemmenu.put("diachi", "Thắng Lợi - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "1/2 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Trần Thanh Tùng");
        itemmenu.put("hinh", R.drawable.trai5);
        itemmenu.put("giatien", "Khoảng 1.800.000 - 2.000.000 VND");
        itemmenu.put("diachi", "Tân An - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "1/2 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Phạm Hoàng Khoa");
        itemmenu.put("hinh", R.drawable.trai4);
        itemmenu.put("giatien", "Khoảng 500.000 - 800.000 VND");
        itemmenu.put("diachi", "Thành Công - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "2/3 Người");
        dsmenu.add(itemmenu);


        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Nguyễn Duy Tài");
        itemmenu.put("hinh", R.drawable.trai3);
        itemmenu.put("giatien", "Khoảng 800.000 - 900.000 VND");
        itemmenu.put("diachi", "Duy Hòa - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "1/2 Người");
        dsmenu.add(itemmenu);

        itemmenu= new HashMap<String, Object>();
        itemmenu.put("ten", "Trần Kim Tuyết");
        itemmenu.put("hinh", R.drawable.gai6);
        itemmenu.put("giatien", "Khoảng 1.000.000 - 1.200.000 VND");
        itemmenu.put("diachi", "Tân An - Buôn Ma Thuột - Đắk Lắk");
        itemmenu.put("soluong", "2/4 Người");
        dsmenu.add(itemmenu);


        String[]from = {"ten", "hinh", "giatien", "diachi", "soluong"};
        int[]to ={R.id.tenmenu, R.id.hinhmenu, R.id.giatien, R.id.diachi, R.id.soluong};
        SimpleAdapter myadapter = new SimpleAdapter(getContext(), dsmenu, R.layout.item_phong_ghep, from, to);
        rcv.setAdapter(myadapter);

        return view;
    }
}